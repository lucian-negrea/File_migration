/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author RO100051
 */
@Entity
@Table(name = "bugs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bugs.findAll", query = "SELECT b FROM Bugs b"),
    @NamedQuery(name = "Bugs.findByBugId", query = "SELECT b FROM Bugs b WHERE b.bugId = :bugId"),
    @NamedQuery(name = "Bugs.findByBugFileLoc", query = "SELECT b FROM Bugs b WHERE b.bugFileLoc = :bugFileLoc"),
    @NamedQuery(name = "Bugs.findByBugSeverity", query = "SELECT b FROM Bugs b WHERE b.bugSeverity = :bugSeverity"),
    @NamedQuery(name = "Bugs.findByBugStatus", query = "SELECT b FROM Bugs b WHERE b.bugStatus = :bugStatus"),
    @NamedQuery(name = "Bugs.findByCreationTs", query = "SELECT b FROM Bugs b WHERE b.creationTs = :creationTs"),
    @NamedQuery(name = "Bugs.findByDeltaTs", query = "SELECT b FROM Bugs b WHERE b.deltaTs = :deltaTs"),
    @NamedQuery(name = "Bugs.findByShortDesc", query = "SELECT b FROM Bugs b WHERE b.shortDesc = :shortDesc"),
    @NamedQuery(name = "Bugs.findByOpSys", query = "SELECT b FROM Bugs b WHERE b.opSys = :opSys"),
    @NamedQuery(name = "Bugs.findByPriority", query = "SELECT b FROM Bugs b WHERE b.priority = :priority"),
    @NamedQuery(name = "Bugs.findByRepPlatform", query = "SELECT b FROM Bugs b WHERE b.repPlatform = :repPlatform"),
    @NamedQuery(name = "Bugs.findByVersion", query = "SELECT b FROM Bugs b WHERE b.version = :version"),
    @NamedQuery(name = "Bugs.findByResolution", query = "SELECT b FROM Bugs b WHERE b.resolution = :resolution"),
    @NamedQuery(name = "Bugs.findByTargetMilestone", query = "SELECT b FROM Bugs b WHERE b.targetMilestone = :targetMilestone"),
    @NamedQuery(name = "Bugs.findByStatusWhiteboard", query = "SELECT b FROM Bugs b WHERE b.statusWhiteboard = :statusWhiteboard"),
    @NamedQuery(name = "Bugs.findByLastdiffed", query = "SELECT b FROM Bugs b WHERE b.lastdiffed = :lastdiffed"),
    @NamedQuery(name = "Bugs.findByEverconfirmed", query = "SELECT b FROM Bugs b WHERE b.everconfirmed = :everconfirmed"),
    @NamedQuery(name = "Bugs.findByReporterAccessible", query = "SELECT b FROM Bugs b WHERE b.reporterAccessible = :reporterAccessible"),
    @NamedQuery(name = "Bugs.findByCclistAccessible", query = "SELECT b FROM Bugs b WHERE b.cclistAccessible = :cclistAccessible"),
    @NamedQuery(name = "Bugs.findByEstimatedTime", query = "SELECT b FROM Bugs b WHERE b.estimatedTime = :estimatedTime"),
    @NamedQuery(name = "Bugs.findByRemainingTime", query = "SELECT b FROM Bugs b WHERE b.remainingTime = :remainingTime"),
    @NamedQuery(name = "Bugs.findByDeadline", query = "SELECT b FROM Bugs b WHERE b.deadline = :deadline"),
    @NamedQuery(name = "Bugs.findByAlias", query = "SELECT b FROM Bugs b WHERE b.alias = :alias"),
    @NamedQuery(name = "Bugs.findByCfComponentVersion", query = "SELECT b FROM Bugs b WHERE b.cfComponentVersion = :cfComponentVersion"),
    @NamedQuery(name = "Bugs.findByCfSchedreleaseno", query = "SELECT b FROM Bugs b WHERE b.cfSchedreleaseno = :cfSchedreleaseno"),
    @NamedQuery(name = "Bugs.findByCfCusa", query = "SELECT b FROM Bugs b WHERE b.cfCusa = :cfCusa"),
    @NamedQuery(name = "Bugs.findByCfCusb", query = "SELECT b FROM Bugs b WHERE b.cfCusb = :cfCusb"),
    @NamedQuery(name = "Bugs.findByCfCusc", query = "SELECT b FROM Bugs b WHERE b.cfCusc = :cfCusc"),
    @NamedQuery(name = "Bugs.findByCfCusd", query = "SELECT b FROM Bugs b WHERE b.cfCusd = :cfCusd"),
    @NamedQuery(name = "Bugs.findByCfCuse", query = "SELECT b FROM Bugs b WHERE b.cfCuse = :cfCuse"),
    @NamedQuery(name = "Bugs.findByCfVerdt", query = "SELECT b FROM Bugs b WHERE b.cfVerdt = :cfVerdt"),
    @NamedQuery(name = "Bugs.findByCfRejdt", query = "SELECT b FROM Bugs b WHERE b.cfRejdt = :cfRejdt"),
    @NamedQuery(name = "Bugs.findByCfFixdt", query = "SELECT b FROM Bugs b WHERE b.cfFixdt = :cfFixdt"),
    @NamedQuery(name = "Bugs.findByCfRootcause", query = "SELECT b FROM Bugs b WHERE b.cfRootcause = :cfRootcause"),
    @NamedQuery(name = "Bugs.findByCfProject", query = "SELECT b FROM Bugs b WHERE b.cfProject = :cfProject"),
    @NamedQuery(name = "Bugs.findByCfProtLayer", query = "SELECT b FROM Bugs b WHERE b.cfProtLayer = :cfProtLayer"),
    @NamedQuery(name = "Bugs.findByCfDept", query = "SELECT b FROM Bugs b WHERE b.cfDept = :cfDept"),
    @NamedQuery(name = "Bugs.findByCfBilboUrl", query = "SELECT b FROM Bugs b WHERE b.cfBilboUrl = :cfBilboUrl"),
    @NamedQuery(name = "Bugs.findByCfReqfixdt", query = "SELECT b FROM Bugs b WHERE b.cfReqfixdt = :cfReqfixdt"),
    @NamedQuery(name = "Bugs.findByCfCusaGrade", query = "SELECT b FROM Bugs b WHERE b.cfCusaGrade = :cfCusaGrade"),
    @NamedQuery(name = "Bugs.findByCfCusaTicket", query = "SELECT b FROM Bugs b WHERE b.cfCusaTicket = :cfCusaTicket"),
    @NamedQuery(name = "Bugs.findByCfCusbGrade", query = "SELECT b FROM Bugs b WHERE b.cfCusbGrade = :cfCusbGrade"),
    @NamedQuery(name = "Bugs.findByCfCusbTicket", query = "SELECT b FROM Bugs b WHERE b.cfCusbTicket = :cfCusbTicket"),
    @NamedQuery(name = "Bugs.findByCfCuscGrade", query = "SELECT b FROM Bugs b WHERE b.cfCuscGrade = :cfCuscGrade"),
    @NamedQuery(name = "Bugs.findByCfCuscTicket", query = "SELECT b FROM Bugs b WHERE b.cfCuscTicket = :cfCuscTicket"),
    @NamedQuery(name = "Bugs.findByCfCusdGrade", query = "SELECT b FROM Bugs b WHERE b.cfCusdGrade = :cfCusdGrade"),
    @NamedQuery(name = "Bugs.findByCfCusdTicket", query = "SELECT b FROM Bugs b WHERE b.cfCusdTicket = :cfCusdTicket"),
    @NamedQuery(name = "Bugs.findByCfCuseGrade", query = "SELECT b FROM Bugs b WHERE b.cfCuseGrade = :cfCuseGrade"),
    @NamedQuery(name = "Bugs.findByCfCuseTicket", query = "SELECT b FROM Bugs b WHERE b.cfCuseTicket = :cfCuseTicket")})
public class Bugs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bug_id")
    private Integer bugId;
    @Basic(optional = false)
    @Column(name = "bug_file_loc")
    private String bugFileLoc;
    @Basic(optional = false)
    @Column(name = "bug_severity")
    private String bugSeverity;
    @Basic(optional = false)
    @Column(name = "bug_status")
    private String bugStatus;
    @Column(name = "creation_ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTs;
    @Basic(optional = false)
    @Column(name = "delta_ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deltaTs;
    @Basic(optional = false)
    @Column(name = "short_desc")
    private String shortDesc;
    @Basic(optional = false)
    @Column(name = "op_sys")
    private String opSys;
    @Basic(optional = false)
    @Column(name = "priority")
    private String priority;
    @Basic(optional = false)
    @Column(name = "rep_platform")
    private String repPlatform;
    @Basic(optional = false)
    @Column(name = "version")
    private String version;
    @Basic(optional = false)
    @Column(name = "resolution")
    private String resolution;
    @Basic(optional = false)
    @Column(name = "target_milestone")
    private String targetMilestone;
    @Basic(optional = false)
    @Column(name = "status_whiteboard")
    private String statusWhiteboard;
    @Column(name = "lastdiffed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastdiffed;
    @Basic(optional = false)
    @Column(name = "everconfirmed")
    private short everconfirmed;
    @Basic(optional = false)
    @Column(name = "reporter_accessible")
    private short reporterAccessible;
    @Basic(optional = false)
    @Column(name = "cclist_accessible")
    private short cclistAccessible;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "estimated_time")
    private BigDecimal estimatedTime;
    @Basic(optional = false)
    @Column(name = "remaining_time")
    private BigDecimal remainingTime;
    @Column(name = "deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    @Column(name = "alias")
    private String alias;
    @Basic(optional = false)
    @Column(name = "cf_component_version")
    private String cfComponentVersion;
    @Basic(optional = false)
    @Column(name = "cf_schedreleaseno")
    private String cfSchedreleaseno;
    @Basic(optional = false)
    @Column(name = "cf_cusa")
    private String cfCusa;
    @Basic(optional = false)
    @Column(name = "cf_cusb")
    private String cfCusb;
    @Basic(optional = false)
    @Column(name = "cf_cusc")
    private String cfCusc;
    @Basic(optional = false)
    @Column(name = "cf_cusd")
    private String cfCusd;
    @Basic(optional = false)
    @Column(name = "cf_cuse")
    private String cfCuse;
    @Column(name = "cf_verdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cfVerdt;
    @Column(name = "cf_rejdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cfRejdt;
    @Column(name = "cf_fixdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cfFixdt;
    @Basic(optional = false)
    @Column(name = "cf_rootcause")
    private String cfRootcause;
    @Basic(optional = false)
    @Column(name = "cf_project")
    private String cfProject;
    @Basic(optional = false)
    @Column(name = "cf_prot_layer")
    private String cfProtLayer;
    @Basic(optional = false)
    @Column(name = "cf_dept")
    private String cfDept;
    @Basic(optional = false)
    @Column(name = "cf_bilbo_url")
    private String cfBilboUrl;
    @Column(name = "cf_reqfixdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cfReqfixdt;
    @Basic(optional = false)
    @Column(name = "cf_cusa_grade")
    private String cfCusaGrade;
    @Basic(optional = false)
    @Column(name = "cf_cusa_ticket")
    private String cfCusaTicket;
    @Basic(optional = false)
    @Column(name = "cf_cusb_grade")
    private String cfCusbGrade;
    @Basic(optional = false)
    @Column(name = "cf_cusb_ticket")
    private String cfCusbTicket;
    @Basic(optional = false)
    @Column(name = "cf_cusc_grade")
    private String cfCuscGrade;
    @Basic(optional = false)
    @Column(name = "cf_cusc_ticket")
    private String cfCuscTicket;
    @Basic(optional = false)
    @Column(name = "cf_cusd_grade")
    private String cfCusdGrade;
    @Basic(optional = false)
    @Column(name = "cf_cusd_ticket")
    private String cfCusdTicket;
    @Basic(optional = false)
    @Column(name = "cf_cuse_grade")
    private String cfCuseGrade;
    @Basic(optional = false)
    @Column(name = "cf_cuse_ticket")
    private String cfCuseTicket;
    @JoinTable(name = "dependencies", joinColumns = {
        @JoinColumn(name = "blocked", referencedColumnName = "bug_id")}, inverseJoinColumns = {
        @JoinColumn(name = "dependson", referencedColumnName = "bug_id")})
    @ManyToMany
    private Collection<Bugs> bugsCollection;
    @ManyToMany(mappedBy = "bugsCollection")
    private Collection<Bugs> bugsCollection1;
    @JoinTable(name = "cc", joinColumns = {
        @JoinColumn(name = "bug_id", referencedColumnName = "bug_id")}, inverseJoinColumns = {
        @JoinColumn(name = "who", referencedColumnName = "userid")})
    @ManyToMany
    private Collection<Profiles> profilesCollection;
    @JoinColumn(name = "reporter", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    private Profiles reporter;
    @JoinColumn(name = "qa_contact", referencedColumnName = "userid")
    @ManyToOne
    private Profiles qaContact;
    @JoinColumn(name = "assigned_to", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    private Profiles assignedTo;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productId;
    @JoinColumn(name = "component_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Components componentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bugId")
    private Collection<Attachments> attachmentsCollection;

    public Bugs() {
    }

    public Bugs(Integer bugId) {
        this.bugId = bugId;
    }

    public Bugs(Integer bugId, String bugFileLoc, String bugSeverity, String bugStatus, Date deltaTs, String shortDesc, String opSys, String priority, String repPlatform, String version, String resolution, String targetMilestone, String statusWhiteboard, short everconfirmed, short reporterAccessible, short cclistAccessible, BigDecimal estimatedTime, BigDecimal remainingTime, String cfComponentVersion, String cfSchedreleaseno, String cfCusa, String cfCusb, String cfCusc, String cfCusd, String cfCuse, String cfRootcause, String cfProject, String cfProtLayer, String cfDept, String cfBilboUrl, String cfCusaGrade, String cfCusaTicket, String cfCusbGrade, String cfCusbTicket, String cfCuscGrade, String cfCuscTicket, String cfCusdGrade, String cfCusdTicket, String cfCuseGrade, String cfCuseTicket) {
        this.bugId = bugId;
        this.bugFileLoc = bugFileLoc;
        this.bugSeverity = bugSeverity;
        this.bugStatus = bugStatus;
        this.deltaTs = deltaTs;
        this.shortDesc = shortDesc;
        this.opSys = opSys;
        this.priority = priority;
        this.repPlatform = repPlatform;
        this.version = version;
        this.resolution = resolution;
        this.targetMilestone = targetMilestone;
        this.statusWhiteboard = statusWhiteboard;
        this.everconfirmed = everconfirmed;
        this.reporterAccessible = reporterAccessible;
        this.cclistAccessible = cclistAccessible;
        this.estimatedTime = estimatedTime;
        this.remainingTime = remainingTime;
        this.cfComponentVersion = cfComponentVersion;
        this.cfSchedreleaseno = cfSchedreleaseno;
        this.cfCusa = cfCusa;
        this.cfCusb = cfCusb;
        this.cfCusc = cfCusc;
        this.cfCusd = cfCusd;
        this.cfCuse = cfCuse;
        this.cfRootcause = cfRootcause;
        this.cfProject = cfProject;
        this.cfProtLayer = cfProtLayer;
        this.cfDept = cfDept;
        this.cfBilboUrl = cfBilboUrl;
        this.cfCusaGrade = cfCusaGrade;
        this.cfCusaTicket = cfCusaTicket;
        this.cfCusbGrade = cfCusbGrade;
        this.cfCusbTicket = cfCusbTicket;
        this.cfCuscGrade = cfCuscGrade;
        this.cfCuscTicket = cfCuscTicket;
        this.cfCusdGrade = cfCusdGrade;
        this.cfCusdTicket = cfCusdTicket;
        this.cfCuseGrade = cfCuseGrade;
        this.cfCuseTicket = cfCuseTicket;
    }

    public Integer getBugId() {
        return bugId;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public String getBugFileLoc() {
        return bugFileLoc;
    }

    public void setBugFileLoc(String bugFileLoc) {
        this.bugFileLoc = bugFileLoc;
    }

    public String getBugSeverity() {
        return bugSeverity;
    }

    public void setBugSeverity(String bugSeverity) {
        this.bugSeverity = bugSeverity;
    }

    public String getBugStatus() {
        return bugStatus;
    }

    public void setBugStatus(String bugStatus) {
        this.bugStatus = bugStatus;
    }

    public Date getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(Date creationTs) {
        this.creationTs = creationTs;
    }

    public Date getDeltaTs() {
        return deltaTs;
    }

    public void setDeltaTs(Date deltaTs) {
        this.deltaTs = deltaTs;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getOpSys() {
        return opSys;
    }

    public void setOpSys(String opSys) {
        this.opSys = opSys;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRepPlatform() {
        return repPlatform;
    }

    public void setRepPlatform(String repPlatform) {
        this.repPlatform = repPlatform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getTargetMilestone() {
        return targetMilestone;
    }

    public void setTargetMilestone(String targetMilestone) {
        this.targetMilestone = targetMilestone;
    }

    public String getStatusWhiteboard() {
        return statusWhiteboard;
    }

    public void setStatusWhiteboard(String statusWhiteboard) {
        this.statusWhiteboard = statusWhiteboard;
    }

    public Date getLastdiffed() {
        return lastdiffed;
    }

    public void setLastdiffed(Date lastdiffed) {
        this.lastdiffed = lastdiffed;
    }

    public short getEverconfirmed() {
        return everconfirmed;
    }

    public void setEverconfirmed(short everconfirmed) {
        this.everconfirmed = everconfirmed;
    }

    public short getReporterAccessible() {
        return reporterAccessible;
    }

    public void setReporterAccessible(short reporterAccessible) {
        this.reporterAccessible = reporterAccessible;
    }

    public short getCclistAccessible() {
        return cclistAccessible;
    }

    public void setCclistAccessible(short cclistAccessible) {
        this.cclistAccessible = cclistAccessible;
    }

    public BigDecimal getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(BigDecimal estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public BigDecimal getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(BigDecimal remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCfComponentVersion() {
        return cfComponentVersion;
    }

    public void setCfComponentVersion(String cfComponentVersion) {
        this.cfComponentVersion = cfComponentVersion;
    }

    public String getCfSchedreleaseno() {
        return cfSchedreleaseno;
    }

    public void setCfSchedreleaseno(String cfSchedreleaseno) {
        this.cfSchedreleaseno = cfSchedreleaseno;
    }

    public String getCfCusa() {
        return cfCusa;
    }

    public void setCfCusa(String cfCusa) {
        this.cfCusa = cfCusa;
    }

    public String getCfCusb() {
        return cfCusb;
    }

    public void setCfCusb(String cfCusb) {
        this.cfCusb = cfCusb;
    }

    public String getCfCusc() {
        return cfCusc;
    }

    public void setCfCusc(String cfCusc) {
        this.cfCusc = cfCusc;
    }

    public String getCfCusd() {
        return cfCusd;
    }

    public void setCfCusd(String cfCusd) {
        this.cfCusd = cfCusd;
    }

    public String getCfCuse() {
        return cfCuse;
    }

    public void setCfCuse(String cfCuse) {
        this.cfCuse = cfCuse;
    }

    public Date getCfVerdt() {
        return cfVerdt;
    }

    public void setCfVerdt(Date cfVerdt) {
        this.cfVerdt = cfVerdt;
    }

    public Date getCfRejdt() {
        return cfRejdt;
    }

    public void setCfRejdt(Date cfRejdt) {
        this.cfRejdt = cfRejdt;
    }

    public Date getCfFixdt() {
        return cfFixdt;
    }

    public void setCfFixdt(Date cfFixdt) {
        this.cfFixdt = cfFixdt;
    }

    public String getCfRootcause() {
        return cfRootcause;
    }

    public void setCfRootcause(String cfRootcause) {
        this.cfRootcause = cfRootcause;
    }

    public String getCfProject() {
        return cfProject;
    }

    public void setCfProject(String cfProject) {
        this.cfProject = cfProject;
    }

    public String getCfProtLayer() {
        return cfProtLayer;
    }

    public void setCfProtLayer(String cfProtLayer) {
        this.cfProtLayer = cfProtLayer;
    }

    public String getCfDept() {
        return cfDept;
    }

    public void setCfDept(String cfDept) {
        this.cfDept = cfDept;
    }

    public String getCfBilboUrl() {
        return cfBilboUrl;
    }

    public void setCfBilboUrl(String cfBilboUrl) {
        this.cfBilboUrl = cfBilboUrl;
    }

    public Date getCfReqfixdt() {
        return cfReqfixdt;
    }

    public void setCfReqfixdt(Date cfReqfixdt) {
        this.cfReqfixdt = cfReqfixdt;
    }

    public String getCfCusaGrade() {
        return cfCusaGrade;
    }

    public void setCfCusaGrade(String cfCusaGrade) {
        this.cfCusaGrade = cfCusaGrade;
    }

    public String getCfCusaTicket() {
        return cfCusaTicket;
    }

    public void setCfCusaTicket(String cfCusaTicket) {
        this.cfCusaTicket = cfCusaTicket;
    }

    public String getCfCusbGrade() {
        return cfCusbGrade;
    }

    public void setCfCusbGrade(String cfCusbGrade) {
        this.cfCusbGrade = cfCusbGrade;
    }

    public String getCfCusbTicket() {
        return cfCusbTicket;
    }

    public void setCfCusbTicket(String cfCusbTicket) {
        this.cfCusbTicket = cfCusbTicket;
    }

    public String getCfCuscGrade() {
        return cfCuscGrade;
    }

    public void setCfCuscGrade(String cfCuscGrade) {
        this.cfCuscGrade = cfCuscGrade;
    }

    public String getCfCuscTicket() {
        return cfCuscTicket;
    }

    public void setCfCuscTicket(String cfCuscTicket) {
        this.cfCuscTicket = cfCuscTicket;
    }

    public String getCfCusdGrade() {
        return cfCusdGrade;
    }

    public void setCfCusdGrade(String cfCusdGrade) {
        this.cfCusdGrade = cfCusdGrade;
    }

    public String getCfCusdTicket() {
        return cfCusdTicket;
    }

    public void setCfCusdTicket(String cfCusdTicket) {
        this.cfCusdTicket = cfCusdTicket;
    }

    public String getCfCuseGrade() {
        return cfCuseGrade;
    }

    public void setCfCuseGrade(String cfCuseGrade) {
        this.cfCuseGrade = cfCuseGrade;
    }

    public String getCfCuseTicket() {
        return cfCuseTicket;
    }

    public void setCfCuseTicket(String cfCuseTicket) {
        this.cfCuseTicket = cfCuseTicket;
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
    public Collection<Profiles> getProfilesCollection() {
        return profilesCollection;
    }

    public void setProfilesCollection(Collection<Profiles> profilesCollection) {
        this.profilesCollection = profilesCollection;
    }

    public Profiles getReporter() {
        return reporter;
    }

    public void setReporter(Profiles reporter) {
        this.reporter = reporter;
    }

    public Profiles getQaContact() {
        return qaContact;
    }

    public void setQaContact(Profiles qaContact) {
        this.qaContact = qaContact;
    }

    public Profiles getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Profiles assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public Components getComponentId() {
        return componentId;
    }

    public void setComponentId(Components componentId) {
        this.componentId = componentId;
    }

    @XmlTransient
    public Collection<Attachments> getAttachmentsCollection() {
        return attachmentsCollection;
    }

    public void setAttachmentsCollection(Collection<Attachments> attachmentsCollection) {
        this.attachmentsCollection = attachmentsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bugId != null ? bugId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bugs)) {
            return false;
        }
        Bugs other = (Bugs) object;
        if ((this.bugId == null && other.bugId != null) || (this.bugId != null && !this.bugId.equals(other.bugId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+bugId;
    }
    
}
