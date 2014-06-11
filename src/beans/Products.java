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
@Table(name = "products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findById", query = "SELECT p FROM Products p WHERE p.id = :id"),
    @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name"),
    @NamedQuery(name = "Products.findByDescription", query = "SELECT p FROM Products p WHERE p.description = :description"),
    @NamedQuery(name = "Products.findByIsactive", query = "SELECT p FROM Products p WHERE p.isactive = :isactive"),
    @NamedQuery(name = "Products.findByDefaultmilestone", query = "SELECT p FROM Products p WHERE p.defaultmilestone = :defaultmilestone"),
    @NamedQuery(name = "Products.findByAllowsUnconfirmed", query = "SELECT p FROM Products p WHERE p.allowsUnconfirmed = :allowsUnconfirmed")})
public class Products implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "defaultmilestone")
    private String defaultmilestone;
    @Basic(optional = false)
    @Column(name = "allows_unconfirmed")
    private short allowsUnconfirmed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Bugs> bugsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Components> componentsCollection;
    @JoinColumn(name = "classification_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Classifications classificationId;

    public Products() {
    }

    public Products(Integer id) {
        this.id = id;
    }

    public Products(Integer id, String name, String description, short isactive, String defaultmilestone, short allowsUnconfirmed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isactive = isactive;
        this.defaultmilestone = defaultmilestone;
        this.allowsUnconfirmed = allowsUnconfirmed;
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

    public String getDefaultmilestone() {
        return defaultmilestone;
    }

    public void setDefaultmilestone(String defaultmilestone) {
        this.defaultmilestone = defaultmilestone;
    }

    public short getAllowsUnconfirmed() {
        return allowsUnconfirmed;
    }

    public void setAllowsUnconfirmed(short allowsUnconfirmed) {
        this.allowsUnconfirmed = allowsUnconfirmed;
    }

    @XmlTransient
    public Collection<Bugs> getBugsCollection() {
        return bugsCollection;
    }

    public void setBugsCollection(Collection<Bugs> bugsCollection) {
        this.bugsCollection = bugsCollection;
    }

    @XmlTransient
    public Collection<Components> getComponentsCollection() {
        return componentsCollection;
    }

    public void setComponentsCollection(Collection<Components> componentsCollection) {
        this.componentsCollection = componentsCollection;
    }

    public Classifications getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Classifications classificationId) {
        this.classificationId = classificationId;
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
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Products[ id=" + id + " ]";
    }
    
}
