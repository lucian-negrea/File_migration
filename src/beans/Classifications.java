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
@Table(name = "classifications")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classifications.findAll", query = "SELECT c FROM Classifications c"),
    @NamedQuery(name = "Classifications.findById", query = "SELECT c FROM Classifications c WHERE c.id = :id"),
    @NamedQuery(name = "Classifications.findByName", query = "SELECT c FROM Classifications c WHERE c.name = :name"),
    @NamedQuery(name = "Classifications.findByDescription", query = "SELECT c FROM Classifications c WHERE c.description = :description"),
    @NamedQuery(name = "Classifications.findBySortkey", query = "SELECT c FROM Classifications c WHERE c.sortkey = :sortkey")})
public class Classifications implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "sortkey")
    private int sortkey;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classificationId")
    private Collection<Products> productsCollection;

    public Classifications() {
    }

    public Classifications(Integer id) {
        this.id = id;
    }

    public Classifications(Integer id, String name, int sortkey) {
        this.id = id;
        this.name = name;
        this.sortkey = sortkey;
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

    public int getSortkey() {
        return sortkey;
    }

    public void setSortkey(int sortkey) {
        this.sortkey = sortkey;
    }

    @XmlTransient
    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
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
        if (!(object instanceof Classifications)) {
            return false;
        }
        Classifications other = (Classifications) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Classifications[ id=" + id + " ]";
    }
    
}
