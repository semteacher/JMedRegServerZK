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
@Table(name = "officestat", catalog = "jmedregstat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Officestat.findAll", query = "SELECT o FROM Officestat o"),
    @NamedQuery(name = "Officestat.findById", query = "SELECT o FROM Officestat o WHERE o.id = :id"),
    @NamedQuery(name = "Officestat.findByStatDate", query = "SELECT o FROM Officestat o WHERE o.statDate = :statDate"),
    @NamedQuery(name = "Officestat.findByStaffFio", query = "SELECT o FROM Officestat o WHERE o.staffFio = :staffFio"),
    @NamedQuery(name = "Officestat.findByPatientTotal", query = "SELECT o FROM Officestat o WHERE o.patientTotal = :patientTotal")})
public class Officestat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "stat_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statDate;
    @Column(name = "staff_fio")
    private String staffFio;
    @Column(name = "patient_total")
    private Integer patientTotal;
    @Lob
    @Column(name = "upd_record")
    private String updRecord;
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Medoffice officeId;

    public Officestat() {
    }

    public Officestat(Integer id) {
        this.id = id;
    }

    public Officestat(Integer id, Date statDate) {
        this.id = id;
        this.statDate = statDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public String getStaffFio() {
        return staffFio;
    }

    public void setStaffFio(String staffFio) {
        this.staffFio = staffFio;
    }

    public Integer getPatientTotal() {
        return patientTotal;
    }

    public void setPatientTotal(Integer patientTotal) {
        this.patientTotal = patientTotal;
    }

    public String getUpdRecord() {
        return updRecord;
    }

    public void setUpdRecord(String updRecord) {
        this.updRecord = updRecord;
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
        if (!(object instanceof Officestat)) {
            return false;
        }
        Officestat other = (Officestat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jmedregbeans.Officestat[ id=" + id + " ]";
    }
    
}
