/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmedregcontrollers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;
import org.zkoss.zul.Window;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.Datebox;
import jmedregbeans.Medoffice;
import jmedregbeans.Statdetail;
import jmedregbeans.SumStatDetail;
import jmedregbeans.Officestat;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.*;

/**
 *
 * @author roman
 */
public class ZkChartWinController extends Window {

    //wisual controls
    private Listbox myofficeListBox;
    private Listbox tmpStatTypeListBox;
    private Listbox tmpStatPeriodListBox;
    //   private Groupbox tmpDateGroup;
    private Datebox tmpStartDateBox;
    private Datebox tmpEndDateBox;
    private Flashchart mainchart;
    //data objects
    private Integer LoggedUserId = null;//user not logged
    private EntityManagerFactory emf;
    List<Medoffice> medOffices = new ArrayList();
    List<Statdetail> statDetail = new ArrayList();
    List<SumStatDetail> sumStatDetail = new ArrayList();
    List<Officestat> officeStatInfo = new ArrayList();

    //init controller
    public ZkChartWinController() {
        Session myzkSession = Sessions.getCurrent(false);
        if (myzkSession != null) {
            LoggedUserId = (Integer) myzkSession.getAttribute("currLoggedUserId");
            if (LoggedUserId != null) {//user logged!
                emf = Persistence.createEntityManagerFactory("JMedRegServerZK02PU");
                medOffices = readMedOffices();
                officeStatInfo = readOfficeStatInfo(null);
            } else {//user not logged!
                Executions.sendRedirect("index.zul");//redirect to login
            }
        } else {//user not logged!
            Executions.sendRedirect("index.zul");//redirect to login
        }
    }

    //get medOffice list to display
    public List getMedOffices() {
        return medOffices;
    }

    //get statInfo list to display
    public List getOfficeStatInfo() {
        return officeStatInfo;
    }

    //Retreive list of the medOffice's from database
    private List<Medoffice> readMedOffices() {
        //     List<Medoffice> tmpMedOffice = null;
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select o from Medoffice as o");
        List<Medoffice> tmpMedOffice = q.getResultList();
        em.close();
        return tmpMedOffice;
    }

    //Retreive list of the statInfo's from database
    private List<Officestat> readOfficeStatInfo(Set<Listitem> myListItem) {
        EntityManager em = emf.createEntityManager();
        Query q;
        //List<Officestat> tmpOfficeStatInfo = new ArrayList();
        List<Officestat> tmpOfficeStatInfo = null;
        if (myListItem == null) {//default - get statinfo for the all offices
            q = em.createQuery("select sinfo from Officestat as sinfo where sinfo.patientTotal>0");
            tmpOfficeStatInfo = q.getResultList();
        } else if ((myListItem.isEmpty()) != true) {//get statinfo only for selected offices
            List<Integer> tmpmedOfficesId = new ArrayList();
            for (Listitem tmpListItem : myListItem) {//porcess all selected listitems
                tmpmedOfficesId.add(((Medoffice) tmpListItem.getValue()).getId());   //extract medoffice entities id's from lisiitem 
                //        Medoffice tmpoffice = ((Medoffice) tmpListItem.getValue());//extract medoffice entities from lisiitem
            }
            q = em.createQuery("select sinfo from Officestat as sinfo where (sinfo.officeId.id IN :medofficess)and(sinfo.patientTotal>0)");
            q.setParameter("medofficess", tmpmedOfficesId);
            tmpOfficeStatInfo = q.getResultList();
        }
        em.close();
        return tmpOfficeStatInfo;
    }

    //return last day when patient vizit was registered
    private Date getLastStatdetailDate() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Date> q = em.createQuery("select max(stdet.vizitDate) from Statdetail as stdet", Date.class);
        Date tmpMaxDate = q.getSingleResult();
        em.close();
        return tmpMaxDate;
    }

    //return first day when patient vizit was registered
    private Date getFirstStatdetailDate() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Date> q = em.createQuery("select min(stdet.vizitDate) from Statdetail as stdet", Date.class);
        Date tmpMinDate = q.getSingleResult();
        em.close();
        return tmpMinDate;
    }

    private List<SumStatDetail> getStatDetailTotal(Date startDate, Date endDate) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<SumStatDetail> q = em.createQuery("select new jmedregbeans.SumStatDetail(stdet.vizitDate, sum(stdet.patientCount)) from Statdetail as stdet Where (stdet.vizitDate BETWEEN :startdate AND :enddate) group by stdet.vizitDate", SumStatDetail.class);
        q.setParameter("startdate", startDate, TemporalType.DATE);
        q.setParameter("enddate", endDate, TemporalType.DATE);
        List<SumStatDetail> tmpStatDetail = q.getResultList();
        em.close();
        return tmpStatDetail;
    }

    private List<Statdetail> getStatDetailByOffice2(List<Integer> tmpOffice, Date startDate, Date endDate) {
        //List<Statdetail> tmpStatDetail = null;
        List<Statdetail> tmpStatDetail = new ArrayList();
        if (!tmpOffice.isEmpty()) {
            EntityManager em = emf.createEntityManager();
            //Query q = em.createQuery("select stdet from Statdetail as stdet Where (stdet.officeId= :myofficeid) AND (stdet.vizitDate BETWEEN :startdate AND :enddate)");
            Query q = em.createQuery("select stdet from Statdetail as stdet Where (stdet.officeId.id IN :myofficeid) AND (stdet.vizitDate BETWEEN :startdate AND :enddate) order by stdet.vizitDate");
            q.setParameter("myofficeid", tmpOffice);
            q.setParameter("startdate", startDate, TemporalType.DATE);
            q.setParameter("enddate", endDate, TemporalType.DATE);
            tmpStatDetail = q.getResultList();
            em.close();
        }
        return tmpStatDetail;
    }

    //choose mode of the display detail statistic
    public void onStatModeSelect() throws InterruptedException {
        if (tmpStatTypeListBox.getSelectedIndex() == 0) {//total
            myofficeListBox.setVisible(false);
            doRefreshChartByOffice();
        } else {                                        //by offices
            myofficeListBox.setVisible(true);
            doRefreshChartByOffice();
        }
    }

    //choose time interval for statistic
    public void onStatPeriodSelect() throws InterruptedException {
        if (tmpStatPeriodListBox.getSelectedIndex() == 0) {//automatically show statistic for the all times data
            tmpStartDateBox.setValue(getFirstStatdetailDate());
            tmpEndDateBox.setValue(getLastStatdetailDate());
            tmpStartDateBox.setDisabled(true);
            tmpEndDateBox.setDisabled(true);
            doRefreshChartByOffice();
        } else {                                            //allow user to choose time interval manually
            tmpStartDateBox.setDisabled(false);
            tmpEndDateBox.setDisabled(false);
        }
    }

    //choose office to display detail statistic
    public void onOfficeSelect() throws InterruptedException {
        //     tmptextbox.setValue(Integer.toString(myofficeListBox.getSelectedCount())); 
        doRefreshChartByOffice();
    }

    public void onStartDateChange() throws InterruptedException {
        doRefreshChartByOffice();
    }

    public void onEndDateChange() throws InterruptedException {
        doRefreshChartByOffice();
    }

    //setup chart with statistic by offices
    private void doRefreshChartByOffice() throws InterruptedException {
        SimpleCategoryModel tmpmodel = null;
        //get start and end dates
        Date tmpStartDate = tmpStartDateBox.getValue();
        Date tmpEndDate = tmpEndDateBox.getValue();
        //correct date order validation!
        if (tmpEndDate.compareTo(tmpStartDate) > 0) {
            //create chart data model
            if (tmpStatTypeListBox.getSelectedIndex() == 0) {//total statistic
                tmpmodel = getTotalStatisitc(tmpStartDate, tmpEndDate);
                officeStatInfo = readOfficeStatInfo(null);//refresh statinfo for all offices
            } else {                                        //statistic by selected offices    
                //get selection from listbox
                Set tmpset = myofficeListBox.getSelectedItems();
                tmpmodel = getStatisitcBySelOffices2(tmpset, tmpStartDate, tmpEndDate);
                officeStatInfo = readOfficeStatInfo(tmpset);//refresh statinfo for selected offices
            }
            //set chart
            mainchart.setType("line");
            mainchart.setModel(tmpmodel);
        } else { //error!
            Messagebox.show("Дата кінця інтервалу є перед датою початку!", "Помилка", Messagebox.OK, Messagebox.ERROR);
            mainchart.setModel(tmpmodel);
        }
    }

    //implement create chart data model for statistic by offices-CORRECT!!!!
    private SimpleCategoryModel getStatisitcBySelOffices2(Set<Listitem> myListItem, Date tmpStartDate, Date tmpEndDate) {
        SimpleCategoryModel tmpmodel = new SimpleCategoryModel();//new chart model
        List<Integer> tmpMedOfficesId = new ArrayList();
        List<Statdetail> tmpStatDetailByOffices = new ArrayList();
        for (Listitem tmpListItem : myListItem) {//porcess all selected listitems
            tmpMedOfficesId.add(((Medoffice) tmpListItem.getValue()).getId());   //extract medoffice entities id's from lisiitem 
        }
        tmpStatDetailByOffices = getStatDetailByOffice2(tmpMedOfficesId, tmpStartDate, tmpEndDate);
        for (Iterator<Statdetail> it = tmpStatDetailByOffices.iterator(); it.hasNext();) {//retreiv detailed statisitc        
            Statdetail tmpstatinfo = it.next();//next statistic record                
            String tmpDate = new SimpleDateFormat("dd/MM/yyyy").format(tmpstatinfo.getVizitDate());//format date!
            tmpmodel.setValue(tmpstatinfo.getOfficeId().getTown(), tmpDate, tmpstatinfo.getPatientCount());//add statistic value to chart model        
        }
        return tmpmodel;
    }

    //implement create chart data model for total statistic
    private SimpleCategoryModel getTotalStatisitc(Date tmpStartDate, Date tmpEndDate) {
        SimpleCategoryModel tmpmodel = new SimpleCategoryModel();//new chart model
        for (Iterator<SumStatDetail> it = getStatDetailTotal(tmpStartDate, tmpEndDate).iterator(); it.hasNext();) {//retreiv detailed statisitc
            SumStatDetail tmpstatinfo = it.next();//next statistic record
            String tmpDate = new SimpleDateFormat("dd/MM/yyyy").format(tmpstatinfo.getVizitDate());//format date!
            tmpmodel.setValue("Всього пацієнтів", tmpDate, tmpstatinfo.getPatientCount());//add statistic value to chart model
        }
        return tmpmodel;
    }

    public void onCreate() throws ParseException, InterruptedException {
        //wire controls
        tmpStatTypeListBox = (Listbox) this.getFellow("stattype");
        tmpStatPeriodListBox = (Listbox) this.getFellow("statperiod");
        myofficeListBox = (Listbox) this.getFellow("officeslistbox");
        mainchart = (Flashchart) this.getFellow("mychartid");
        tmpStartDateBox = (Datebox) this.getFellow("startdb");
        tmpEndDateBox = (Datebox) this.getFellow("enddb");
        //default
        tmpStatTypeListBox.setSelectedIndex(0);//default - show SUMMARY (ALL) statistic
        myofficeListBox.setVisible(false);
        tmpStatPeriodListBox.setSelectedIndex(1);//default - show LIMITED time interval only
        Date tmpStartDate = new SimpleDateFormat("yyyy/MM/dd").parse("2012/01/01");
        tmpStartDateBox.setValue(tmpStartDate);
        tmpEndDateBox.setValue(new Date());//now
        //chart options
        //mainchart.setYaxis("Кількість пацієнтів");//TODO:not work
        //mainchart.setChartStyle("font-weight=bold");//TODO:not work
        mainchart.setChartStyle("legend-display=bottom");
        //myofficeListBox.setCheckmark(true);
        //myofficeListBox.getItemAtIndex(0).setSelected(true);//TODO:not work
        //myofficeListBox.setSelectedIndex(0);//TODO:not work
        //myofficeListBox.setSelectedIndex(1);//TODO:not work
        //fill chart with default data
        doRefreshChartByOffice();
    }

    @Override
    public void onClose() {
        emf.close();
        Session myzkSession = Sessions.getCurrent(false);//connect to current session
        if (myzkSession != null) {
            myzkSession.invalidate();//destroy session object
        }
    }
}
