/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmedregbeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author roman
 */
@Entity
@XmlRootElement
public class SumStatDetail  implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
  //@Column(name = "id")
    private Integer id;
   // @Column(name = "vizit_date")
    @Temporal(TemporalType.DATE)
    private Date vizitDate;
  //  @Column(name = "patient_count")
    private Integer patientCount;


    public SumStatDetail() {
    }

    public SumStatDetail(Integer id) {
        this.id = id;
    }
    
    public SumStatDetail(Date vizitDate, Long patientCount) {
        this.vizitDate = vizitDate;
        this.patientCount = Integer.valueOf(patientCount.toString());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVizitDate() {
        return vizitDate;
    }

    public void setVizitDate(Date vizitDate) {
        this.vizitDate = vizitDate;
    }

    public Integer getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(Integer patientCount) {
        this.patientCount = patientCount;
    }


    
}