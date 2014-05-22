/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmedregcontrollers;

import javax.persistence.*;
import java.security.MessageDigest;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.danter.google.auth.GoogleAuthHelper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Semenets A.V.
 */
public class ZkLoginWinController extends Window {

    //wisual controls
    private Textbox loginBox;
    private Textbox paswdBox;
    private Label loginResLabel;
    //data objects
    private EntityManagerFactory emf;
    
    private final GoogleAuthHelper helper = new GoogleAuthHelper();
    //init controller
    public ZkLoginWinController() throws IOException {
        setUserIdToSession(null);
        emf = Persistence.createEntityManagerFactory("JMedRegServerMySqlPU");//create entity manager factory
        
        
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        Session myzkSession = Sessions.getCurrent(true); //get session
        
	if ( request.getParameter("code") == null
					|| request.getParameter("state") == null) {

				/*
				 * initial visit to the page
				 */
				//out.println("<a href='" + helper.buildLoginUrl()
				//		+ "'>log in with google</a>");
						
				/*
				 * set the secure state token in session to be able to track what we sent to google
				 */
				myzkSession.setAttribute("state", helper.getStateToken());

			} else if (request.getParameter("code") != null && request.getParameter("state") != null
					&& request.getParameter("state").equals(myzkSession.getAttribute("state"))) {

				myzkSession.removeAttribute("state");

				///out.println("<pre>");
				/*
				 * Executes after google redirects to the callback url.
				 * Please note that the state request parameter is for convenience to differentiate
				 * between authentication methods (ex. facebook oauth, google oauth, twitter, in-house).
				 * 
				 * GoogleAuthHelper()#getUserInfoJson(String) method returns a String containing
				 * the json representation of the authenticated user's information. 
				 * At this point you should parse and persist the info.
				 */

				///out.println(helper.getUserInfoJson(request.getParameter("code")));

				///out.println("</pre>");
//                                loginResLabel.setStyle("color:black");
//                                loginResLabel.setValue(helper.getUserInfoJson(request.getParameter("code")));
                                setUserIdToSession(8888);//set new user logged_id to session
                                Executions.sendRedirect("chart.zul");//successfull login
			}         
    }
    
    //write user's login_id to session attribute
    public void setUserIdToSession(Integer tmpLogedUserId) {
        Session myzkSession = Sessions.getCurrent(true); //get session
        myzkSession.setAttribute("currLoggedUserId", tmpLogedUserId); //set session attribute    
    }    

    //Retreive selected loginID from the tbl_login of the TDMU database 
    private Integer checkUserPassword(String tmpLogin, String tmpPassword) {
        Integer tmploginId = null; //default - not logged!
        String tmpPasswdEncrypted = JCrypt.crypt(md5(tmpLogin), md5(tmpPassword));//encrypt password!
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select lg.loginId from TblLogin as lg where (lg.login= :tmpLoginParam) and (lg.pass= :tmpPassParam)");
        q.setParameter("tmpLoginParam", tmpLogin);
        q.setParameter("tmpPassParam", tmpPasswdEncrypted);
        try {
            tmploginId = (Integer) q.getSingleResult();
        } catch (Exception e) {
        }
        em.close();
        return tmploginId;
    }

    //Marceniyk - get string md5 hash
    private static String md5(String text) {
        String md5_ = null;
        try {
            StringBuffer code = new StringBuffer();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte bytes[] = text.getBytes();
            byte digest[] = messageDigest.digest(bytes);
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }
            md5_ = code.toString();
        } catch (Exception e) {
            System.out.println("Couldn't encrypt by MD5" + e.getMessage());
        }
        return md5_;
    }

    //process login button click 
    public void loginBtnClick() {
        Integer tmpLoggedUserId = checkUserPassword(loginBox.getValue(), paswdBox.getValue());
        if (tmpLoggedUserId != null) {
            loginResLabel.setStyle("color:black");
            loginResLabel.setValue("Дякуємо за авторизацію. Відбувається перенаправлення...");
            setUserIdToSession(tmpLoggedUserId);//set new user logged_id to session
            Executions.sendRedirect("chart.zul");//successfull login
        } else {
            loginResLabel.setStyle("color:red");
            loginResLabel.setValue("Неправильний логін або пароль!");
        }
    }
    
    public void loginGoogleBtnClick() {
   
//        Integer tmpLoggedUserId = checkUserPassword(loginBox.getValue(), paswdBox.getValue());
//        if (tmpLoggedUserId != null) {
//            loginResLabel.setStyle("color:black");
//            loginResLabel.setValue("Дякуємо за авторизацію. Відбувається перенаправлення...");
//            setUserIdToSession(tmpLoggedUserId);//set new user logged_id to session
            Executions.sendRedirect(helper.buildLoginUrl());//successfull login
//        } else {
//            loginResLabel.setStyle("color:red");
//            loginResLabel.setValue("Неправильний логін або пароль!");
//        }
    }    

    //init visualisation
    public void onCreate() {
        //wire controls
        loginBox = (Textbox) this.getFellow("userlogin");
        paswdBox = (Textbox) this.getFellow("userpasswd");
        loginResLabel = (Label) this.getFellow("loginResMsg");
        //default
        loginBox.setFocus(true);
    }

    @Override
    public void onClose() {
        emf.close();
    }
}
