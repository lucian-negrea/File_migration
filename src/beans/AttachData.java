/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RO100051
 */
@Entity
@Table(name = "attach_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttachData.findAll", query = "SELECT a FROM AttachData a"),
    @NamedQuery(name = "AttachData.findById", query = "SELECT a FROM AttachData a WHERE a.id = :id")})
public class AttachData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "thedata")
    private byte[] thedata;
    @JoinColumn(name = "id", referencedColumnName = "attach_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Attachments attachments;

    public AttachData() {
    }

    public AttachData(Integer id) {
        this.id = id;
    }

    public AttachData(Integer id, byte[] thedata) {
        this.id = id;
        this.thedata = thedata;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getThedata() {
        return thedata;
    }

    public void setThedata(byte[] thedata) {
        this.thedata = thedata;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
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
        if (!(object instanceof AttachData)) {
            return false;
        }
        AttachData other = (AttachData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + id;
    }
    
}
