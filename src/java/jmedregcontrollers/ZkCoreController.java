/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmedregcontrollers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author roman
 */
public class ZkCoreController extends GenericForwardComposer {
     
 /* 
    public zkcorecontroller() {

    }    
*/
    public void doBeforeComposeChildren(Component comp) throws Exception {
        super.doBeforeComposeChildren(comp);
        // EntityManagerFactory emf = Persistence.createEntityManagerFactory("JMedRegServerZK01PU");
        //  myMedofficeJpaController(emf);
        //    comp.setAttribute("medoffices", myMedofficeJpaController.findMedofficeEntities());
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //   comp.setAttribute("whatever", prepareWhatever());
    }
}
