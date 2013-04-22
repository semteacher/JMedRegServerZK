/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmedregbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author roman
 */
@Entity
@Table(name = "medoffice", catalog = "jmedregstat", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medoffice.findAll", query = "SELECT m FROM Medoffice m"),
    @NamedQuery(name = "Medoffice.findById", query = "SELECT m FROM Medoffice m WHERE m.id = :id"),
    @NamedQuery(name = "Medoffice.findByName", query = "SELECT m FROM Medoffice m WHERE m.name = :name"),
    @NamedQuery(name = "Medoffice.findByTown", query = "SELECT m FROM Medoffice m WHERE m.town = :town"),
    @NamedQuery(name = "Medoffice.findByAddress", query = "SELECT m FROM Medoffice m WHERE m.address = :address"),
    @NamedQuery(name = "Medoffice.findByDatefound", query = "SELECT m FROM Medoffice m WHERE m.datefound = :datefound"),
    @NamedQuery(name = "Medoffice.findByDateclose", query = "SELECT m FROM Medoffice m WHERE m.dateclose = :dateclose"),
    @NamedQuery(name = "Medoffice.findByUsername", query = "SELECT m FROM Medoffice m WHERE m.username = :username"),
    @NamedQuery(name = "Medoffice.findByUserpass", query = "SELECT m FROM Medoffice m WHERE m.userpass = :userpass")})
public class Medoffice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "town")
    private String town;
    @Column(name = "address")
    private String address;
    @Column(name = "datefound")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datefound;
    @Column(name = "dateclose")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateclose;
    @Lob
    @Column(name = "notes")
    private String notes;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "userpass")
    private String userpass;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "officeId")
//    private List<Statdetail> statdetailList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "officeId")
//    private List<Officestat> officestatList;

    public Medoffice() {
    }

    public Medoffice(Integer id) {
        this.id = id;
    }

    public Medoffice(Integer id, String name, String town, String username, String userpass) {
        this.id = id;
        this.name = name;
        this.town = town;
        this.username = username;
        this.userpass = userpass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDatefound() {
        return datefound;
    }

    public void setDatefound(Date datefound) {
        this.datefound = datefound;
    }

    public Date getDateclose() {
        return dateclose;
    }

    public void setDateclose(Date dateclose) {
        this.dateclose = dateclose;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

 //   @XmlTransient
 //   public List<Statdetail> getStatdetailList() {
 //       return statdetailList;
 //   }

 //   public void setStatdetailList(List<Statdetail> statdetailList) {
 //       this.statdetailList = statdetailList;
 //   }

 //   @XmlTransient
 //   public List<Officestat> getOfficestatList() {
 //       return officestatList;
 //   }

 //   public void setOfficestatList(List<Officestat> officestatList) {
 //       this.officestatList = officestatList;
 //   }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medoffice)) {
            return false;
        }
        Medoffice other = (Medoffice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jmedregbeans.Medoffice[ id=" + id + " ]";
    }
    
}
