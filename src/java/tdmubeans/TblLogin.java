/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdmubeans;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author roman
 */
@Entity
@Table(name = "tbl_login", catalog = "tdmu", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblLogin.findAll", query = "SELECT t FROM TblLogin t"),
    @NamedQuery(name = "TblLogin.findByLoginId", query = "SELECT t FROM TblLogin t WHERE t.loginId = :loginId"),
    @NamedQuery(name = "TblLogin.findByUserId", query = "SELECT t FROM TblLogin t WHERE t.userId = :userId"),
    @NamedQuery(name = "TblLogin.findByLogin", query = "SELECT t FROM TblLogin t WHERE t.login = :login"),
    @NamedQuery(name = "TblLogin.findByPass", query = "SELECT t FROM TblLogin t WHERE t.pass = :pass"),
    @NamedQuery(name = "TblLogin.findByUserAccess", query = "SELECT t FROM TblLogin t WHERE t.userAccess = :userAccess")})
public class TblLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "login_id")
    private Integer loginId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "login")
    private String login;
    @Column(name = "pass")
    private String pass;
    @Column(name = "user_access")
    private Integer userAccess;

    public TblLogin() {
    }

    public TblLogin(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(Integer userAccess) {
        this.userAccess = userAccess;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginId != null ? loginId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblLogin)) {
            return false;
        }
        TblLogin other = (TblLogin) object;
        if ((this.loginId == null && other.loginId != null) || (this.loginId != null && !this.loginId.equals(other.loginId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tdmubeans.TblLogin[ loginId=" + loginId + " ]";
    }
    
}
