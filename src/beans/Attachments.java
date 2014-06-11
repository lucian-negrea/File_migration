/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RO100051
 */
@Entity
@Table(name = "attachments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachments.findAll", query = "SELECT a FROM Attachments a"),
    @NamedQuery(name = "Attachments.findByAttachId", query = "SELECT a FROM Attachments a WHERE a.attachId = :attachId"),
    @NamedQuery(name = "Attachments.findByCreationTs", query = "SELECT a FROM Attachments a WHERE a.creationTs = :creationTs"),
    @NamedQuery(name = "Attachments.findByModificationTime", query = "SELECT a FROM Attachments a WHERE a.modificationTime = :modificationTime"),
    @NamedQuery(name = "Attachments.findByDescription", query = "SELECT a FROM Attachments a WHERE a.description = :description"),
    @NamedQuery(name = "Attachments.findByMimetype", query = "SELECT a FROM Attachments a WHERE a.mimetype = :mimetype"),
    @NamedQuery(name = "Attachments.findByIspatch", query = "SELECT a FROM Attachments a WHERE a.ispatch = :ispatch"),
    @NamedQuery(name = "Attachments.findByBugIdFileName", query = "SELECT a FROM Attachments a WHERE a.bugId = :bugId AND a.filename = :filename"),
    @NamedQuery(name = "Attachments.findByFilename", query = "SELECT a FROM Attachments a WHERE a.filename = :filename"),
    @NamedQuery(name = "Attachments.findByIsobsolete", query = "SELECT a FROM Attachments a WHERE a.isobsolete = :isobsolete"),
    @NamedQuery(name = "Attachments.findByIsprivate", query = "SELECT a FROM Attachments a WHERE a.isprivate = :isprivate")})
public class Attachments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "attach_id")
    private Integer attachId;
    @Basic(optional = false)
    @Column(name = "creation_ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTs;
    @Basic(optional = false)
    @Column(name = "modification_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationTime;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "mimetype")
    private String mimetype;
    @Basic(optional = false)
    @Column(name = "ispatch")
    private short ispatch;
    @Basic(optional = false)
    @Column(name = "filename")
    private String filename;
    @Basic(optional = false)
    @Column(name = "isobsolete")
    private short isobsolete;
    @Basic(optional = false)
    @Column(name = "isprivate")
    private short isprivate;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "attachments")
    private AttachData attachData;
    @JoinColumn(name = "submitter_id", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    private Profiles submitterId;
    @JoinColumn(name = "bug_id", referencedColumnName = "bug_id")
    @ManyToOne(optional = false)
    private Bugs bugId;

    public Attachments() {
    }

    public Attachments(Integer attachId) {
        this.attachId = attachId;
    }

    public Attachments(Integer attachId, Date creationTs, Date modificationTime, String description, String mimetype, short ispatch, String filename, short isobsolete, short isprivate) {
        this.attachId = attachId;
        this.creationTs = creationTs;
        this.modificationTime = modificationTime;
        this.description = description;
        this.mimetype = mimetype;
        this.ispatch = ispatch;
        this.filename = filename;
        this.isobsolete = isobsolete;
        this.isprivate = isprivate;
    }

    public Integer getAttachId() {
        return attachId;
    }

    public void setAttachId(Integer attachId) {
        this.attachId = attachId;
    }

    public Date getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(Date creationTs) {
        this.creationTs = creationTs;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public short getIspatch() {
        return ispatch;
    }

    public void setIspatch(short ispatch) {
        this.ispatch = ispatch;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public short getIsobsolete() {
        return isobsolete;
    }

    public void setIsobsolete(short isobsolete) {
        this.isobsolete = isobsolete;
    }

    public short getIsprivate() {
        return isprivate;
    }

    public void setIsprivate(short isprivate) {
        this.isprivate = isprivate;
    }

    public AttachData getAttachData() {
        return attachData;
    }

    public void setAttachData(AttachData attachData) {
        this.attachData = attachData;
    }

    public Profiles getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Profiles submitterId) {
        this.submitterId = submitterId;
    }

    public Bugs getBugId() {
        return bugId;
    }

    public void setBugId(Bugs bugId) {
        this.bugId = bugId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attachId != null ? attachId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachments)) {
            return false;
        }
        Attachments other = (Attachments) object;
        if ((this.attachId == null && other.attachId != null) || (this.attachId != null && !this.attachId.equals(other.attachId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + attachId;
    }
    
}
