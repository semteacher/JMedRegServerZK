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
@Table(name = "statdetail", catalog = "jmedregstat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Statdetail.findAll", query = "SELECT s FROM Statdetail s"),
    @NamedQuery(name = "Statdetail.findById", query = "SELECT s FROM Statdetail s WHERE s.id = :id"),
    @NamedQuery(name = "Statdetail.findByVizitDate", query = "SELECT s FROM Statdetail s WHERE s.vizitDate = :vizitDate"),
    @NamedQuery(name = "Statdetail.findByPatientCount", query = "SELECT s FROM Statdetail s WHERE s.patientCount = :patientCount")})
public class Statdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "vizit_date")
    @Temporal(TemporalType.DATE)
    private Date vizitDate;
    @Column(name = "patient_count")
    private Integer patientCount;
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Medoffice officeId;

    public Statdetail() {
    }

    public Statdetail(Integer id) {
        this.id = id;
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

    public Medoffice getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Medoffice officeId) {
        this.officeId = officeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statdetail)) {
            return false;
        }
        Statdetail other = (Statdetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jmedregbeans.Statdetail[ id=" + id + " ]";
    }
    
}
