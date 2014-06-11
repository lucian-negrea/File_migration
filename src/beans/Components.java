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
import javax.persistence.ManyToOne;
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
@Table(name = "components")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Components.findAll", query = "SELECT c FROM Components c"),
    @NamedQuery(name = "Components.findById", query = "SELECT c FROM Components c WHERE c.id = :id"),
    @NamedQuery(name = "Components.findByName", query = "SELECT c FROM Components c WHERE c.name = :name"),
    @NamedQuery(name = "Components.findByDescription", query = "SELECT c FROM Components c WHERE c.description = :description"),
    @NamedQuery(name = "Components.findByIsactive", query = "SELECT c FROM Components c WHERE c.isactive = :isactive"),
    @NamedQuery(name = "Components.findByOrganisation", query = "SELECT c FROM Components c WHERE c.organisation = :organisation")})
public class Components implements Serializable {
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
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "isactive")
    private short isactive;
    @Column(name = "organisation")
    private String organisation;
    @JoinTable(name = "component_cc", joinColumns = {
        @JoinColumn(name = "component_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "userid")})
    @ManyToMany
    private Collection<Profiles> profilesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componentId")
    private Collection<Bugs> bugsCollection;
    @JoinColumn(name = "initialqacontact", referencedColumnName = "userid")
    @ManyToOne
    private Profiles initialqacontact;
    @JoinColumn(name = "initialowner", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    private Profiles initialowner;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productId;

    public Components() {
    }

    public Components(Integer id) {
        this.id = id;
    }

    public Components(Integer id, String name, String description, short isactive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isactive = isactive;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getIsactive() {
        return isactive;
    }

    public void setIsactive(short isactive) {
        this.isactive = isactive;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    @XmlTransient
    public Collection<Profiles> getProfilesCollection() {
        return profilesCollection;
    }

    public void setProfilesCollection(Collection<Profiles> profilesCollection) {
        this.profilesCollection = profilesCollection;
    }

    @XmlTransient
    public Collection<Bugs> getBugsCollection() {
        return bugsCollection;
    }

    public void setBugsCollection(Collection<Bugs> bugsCollection) {
        this.bugsCollection = bugsCollection;
    }

    public Profiles getInitialqacontact() {
        return initialqacontact;
    }

    public void setInitialqacontact(Profiles initialqacontact) {
        this.initialqacontact = initialqacontact;
    }

    public Profiles getInitialowner() {
        return initialowner;
    }

    public void setInitialowner(Profiles initialowner) {
        this.initialowner = initialowner;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
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
        if (!(object instanceof Components)) {
            return false;
        }
        Components other = (Components) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Components[ id=" + id + " ]";
    }
    
}
