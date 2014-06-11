/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author RO100051
 */
@Entity
@Table(name = "profiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profiles.findAll", query = "SELECT p FROM Profiles p"),
    @NamedQuery(name = "Profiles.findByUserid", query = "SELECT p FROM Profiles p WHERE p.userid = :userid"),
    @NamedQuery(name = "Profiles.findByLoginName", query = "SELECT p FROM Profiles p WHERE p.loginName = :loginName"),
    @NamedQuery(name = "Profiles.findByCryptpassword", query = "SELECT p FROM Profiles p WHERE p.cryptpassword = :cryptpassword"),
    @NamedQuery(name = "Profiles.findByRealname", query = "SELECT p FROM Profiles p WHERE p.realname = :realname"),
    @NamedQuery(name = "Profiles.findByDisabledtext", query = "SELECT p FROM Profiles p WHERE p.disabledtext = :disabledtext"),
    @NamedQuery(name = "Profiles.findByDisableMail", query = "SELECT p FROM Profiles p WHERE p.disableMail = :disableMail"),
    @NamedQuery(name = "Profiles.findByMybugslink", query = "SELECT p FROM Profiles p WHERE p.mybugslink = :mybugslink"),
    @NamedQuery(name = "Profiles.findByExternId", query = "SELECT p FROM Profiles p WHERE p.externId = :externId"),
    @NamedQuery(name = "Profiles.findByIsEnabled", query = "SELECT p FROM Profiles p WHERE p.isEnabled = :isEnabled")})
public class Profiles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userid")
    private Integer userid;
    @Basic(optional = false)
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "cryptpassword")
    private String cryptpassword;
    @Basic(optional = false)
    @Column(name = "realname")
    private String realname;
    @Basic(optional = false)
    @Column(name = "disabledtext")
    private String disabledtext;
    @Basic(optional = false)
    @Column(name = "disable_mail")
    private short disableMail;
    @Basic(optional = false)
    @Column(name = "mybugslink")
    private short mybugslink;
    @Column(name = "extern_id")
    private String externId;
    @Basic(optional = false)
    @Column(name = "is_enabled")
    private short isEnabled;
    @JoinTable(name = "watch", joinColumns = {
        @JoinColumn(name = "watched", referencedColumnName = "userid")}, inverseJoinColumns = {
        @JoinColumn(name = "watcher", referencedColumnName = "userid")})
    @ManyToMany
    private Collection<Profiles> profilesCollection;
    @ManyToMany(mappedBy = "profilesCollection")
    private Collection<Profiles> profilesCollection1;
    @ManyToMany(mappedBy = "profilesCollection")
    private Collection<Components> componentsCollection;
    @ManyToMany(mappedBy = "profilesCollection")
    private Collection<Bugs> bugsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reporter")
    private Collection<Bugs> bugsCollection1;
    @OneToMany(mappedBy = "qaContact")
    private Collection<Bugs> bugsCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedTo")
    private Collection<Bugs> bugsCollection3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submitterId")
    private Collection<Attachments> attachmentsCollection;
    @OneToMany(mappedBy = "initialqacontact")
    private Collection<Components> componentsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "initialowner")
    private Collection<Components> componentsCollection2;

    public Profiles() {
    }

    public Profiles(Integer userid) {
        this.userid = userid;
    }

    public Profiles(Integer userid, String loginName, String realname, String disabledtext, short disableMail, short mybugslink, short isEnabled) {
        this.userid = userid;
        this.loginName = loginName;
        this.realname = realname;
        this.disabledtext = disabledtext;
        this.disableMail = disableMail;
        this.mybugslink = mybugslink;
        this.isEnabled = isEnabled;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCryptpassword() {
        return cryptpassword;
    }

    public void setCryptpassword(String cryptpassword) {
        this.cryptpassword = cryptpassword;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDisabledtext() {
        return disabledtext;
    }

    public void setDisabledtext(String disabledtext) {
        this.disabledtext = disabledtext;
    }

    public short getDisableMail() {
        return disableMail;
    }

    public void setDisableMail(short disableMail) {
        this.disableMail = disableMail;
    }

    public short getMybugslink() {
        return mybugslink;
    }

    public void setMybugslink(short mybugslink) {
        this.mybugslink = mybugslink;
    }

    public String getExternId() {
        return externId;
    }

    public void setExternId(String externId) {
        this.externId = externId;
    }

    public short getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(short isEnabled) {
        this.isEnabled = isEnabled;
    }

    @XmlTransient
    public Collection<Profiles> getProfilesCollection() {
        return profilesCollection;
    }

    public void setProfilesCollection(Collection<Profiles> profilesCollection) {
        this.profilesCollection = profilesCollection;
    }

    @XmlTransient
    public Collection<Profiles> getProfilesCollection1() {
        return profilesCollection1;
    }

    public void setProfilesCollection1(Collection<Profiles> profilesCollection1) {
        this.profilesCollection1 = profilesCollection1;
    }

    @XmlTransient
    public Collection<Components> getComponentsCollection() {
        return componentsCollection;
    }

    public void setComponentsCollection(Collection<Components> componentsCollection) {
        this.componentsCollection = componentsCollection;
    }

    @XmlTransient
    public Collection<Bugs> getBugsCollection() {
        return bugsCollection;
    }

    public void setBugsCollection(Collection<Bugs> bugsCollection) {
        this.bugsCollection = bugsCollection;
    }

    @XmlTransient
    public Collection<Bugs> getBugsCollection1() {
        return bugsCollection1;
    }

    public void setBugsCollection1(Collection<Bugs> bugsCollection1) {
        this.bugsCollection1 = bugsCollection1;
    }

    @XmlTransient
    public Collection<Bugs> getBugsCollection2() {
        return bugsCollection2;
    }

    public void setBugsCollection2(Collection<Bugs> bugsCollection2) {
        this.bugsCollection2 = bugsCollection2;
    }

    @XmlTransient
    public Collection<Bugs> getBugsCollection3() {
        return bugsCollection3;
    }

    public void setBugsCollection3(Collection<Bugs> bugsCollection3) {
        this.bugsCollection3 = bugsCollection3;
    }

    @XmlTransient
    public Collection<Attachments> getAttachmentsCollection() {
        return attachmentsCollection;
    }

    public void setAttachmentsCollection(Collection<Attachments> attachmentsCollection) {
        this.attachmentsCollection = attachmentsCollection;
    }

    @XmlTransient
    public Collection<Components> getComponentsCollection1() {
        return componentsCollection1;
    }

    public void setComponentsCollection1(Collection<Components> componentsCollection1) {
        this.componentsCollection1 = componentsCollection1;
    }

    @XmlTransient
    public Collection<Components> getComponentsCollection2() {
        return componentsCollection2;
    }

    public void setComponentsCollection2(Collection<Components> componentsCollection2) {
        this.componentsCollection2 = componentsCollection2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profiles)) {
            return false;
        }
        Profiles other = (Profiles) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Profiles[ userid=" + userid + " ]";
    }
    
}
