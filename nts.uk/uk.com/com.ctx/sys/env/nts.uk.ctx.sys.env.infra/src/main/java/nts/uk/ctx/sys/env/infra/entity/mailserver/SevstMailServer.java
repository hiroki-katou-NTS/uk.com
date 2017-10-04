/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.sys.env.infra.entity.mailserver;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Getter
@Setter
@Entity
@Table(name = "SEVST_MAIL_SERVER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SevstMailServer.findAll", query = "SELECT s FROM SevstMailServer s"),
    @NamedQuery(name = "SevstMailServer.findByInsDate", query = "SELECT s FROM SevstMailServer s WHERE s.insDate = :insDate"),
    @NamedQuery(name = "SevstMailServer.findByInsCcd", query = "SELECT s FROM SevstMailServer s WHERE s.insCcd = :insCcd"),
    @NamedQuery(name = "SevstMailServer.findByInsScd", query = "SELECT s FROM SevstMailServer s WHERE s.insScd = :insScd"),
    @NamedQuery(name = "SevstMailServer.findByInsPg", query = "SELECT s FROM SevstMailServer s WHERE s.insPg = :insPg"),
    @NamedQuery(name = "SevstMailServer.findByUpdDate", query = "SELECT s FROM SevstMailServer s WHERE s.updDate = :updDate"),
    @NamedQuery(name = "SevstMailServer.findByUpdCcd", query = "SELECT s FROM SevstMailServer s WHERE s.updCcd = :updCcd"),
    @NamedQuery(name = "SevstMailServer.findByUpdScd", query = "SELECT s FROM SevstMailServer s WHERE s.updScd = :updScd"),
    @NamedQuery(name = "SevstMailServer.findByUpdPg", query = "SELECT s FROM SevstMailServer s WHERE s.updPg = :updPg"),
    @NamedQuery(name = "SevstMailServer.findByExclusVer", query = "SELECT s FROM SevstMailServer s WHERE s.exclusVer = :exclusVer"),
    @NamedQuery(name = "SevstMailServer.findByCid", query = "SELECT s FROM SevstMailServer s WHERE s.cid = :cid"),
    @NamedQuery(name = "SevstMailServer.findByUseAuth", query = "SELECT s FROM SevstMailServer s WHERE s.useAuth = :useAuth"),
    @NamedQuery(name = "SevstMailServer.findByEncryptMethod", query = "SELECT s FROM SevstMailServer s WHERE s.encryptMethod = :encryptMethod"),
    @NamedQuery(name = "SevstMailServer.findByAuthMethod", query = "SELECT s FROM SevstMailServer s WHERE s.authMethod = :authMethod"),
    @NamedQuery(name = "SevstMailServer.findByEmailAuth", query = "SELECT s FROM SevstMailServer s WHERE s.emailAuth = :emailAuth"),
    @NamedQuery(name = "SevstMailServer.findByPassword", query = "SELECT s FROM SevstMailServer s WHERE s.password = :password"),
    @NamedQuery(name = "SevstMailServer.findBySmtpIpVer", query = "SELECT s FROM SevstMailServer s WHERE s.smtpIpVer = :smtpIpVer"),
    @NamedQuery(name = "SevstMailServer.findBySmtpServer", query = "SELECT s FROM SevstMailServer s WHERE s.smtpServer = :smtpServer"),
    @NamedQuery(name = "SevstMailServer.findByStmtTimeOut", query = "SELECT s FROM SevstMailServer s WHERE s.stmtTimeOut = :stmtTimeOut"),
    @NamedQuery(name = "SevstMailServer.findByStmtPort", query = "SELECT s FROM SevstMailServer s WHERE s.stmtPort = :stmtPort"),
    @NamedQuery(name = "SevstMailServer.findByPopIpVer", query = "SELECT s FROM SevstMailServer s WHERE s.popIpVer = :popIpVer"),
    @NamedQuery(name = "SevstMailServer.findByPopUse", query = "SELECT s FROM SevstMailServer s WHERE s.popUse = :popUse"),
    @NamedQuery(name = "SevstMailServer.findByPopServer", query = "SELECT s FROM SevstMailServer s WHERE s.popServer = :popServer"),
    @NamedQuery(name = "SevstMailServer.findByPopTimeOut", query = "SELECT s FROM SevstMailServer s WHERE s.popTimeOut = :popTimeOut"),
    @NamedQuery(name = "SevstMailServer.findByPopPort", query = "SELECT s FROM SevstMailServer s WHERE s.popPort = :popPort"),
    @NamedQuery(name = "SevstMailServer.findByImapIpVer", query = "SELECT s FROM SevstMailServer s WHERE s.imapIpVer = :imapIpVer"),
    @NamedQuery(name = "SevstMailServer.findByImapUse", query = "SELECT s FROM SevstMailServer s WHERE s.imapUse = :imapUse"),
    @NamedQuery(name = "SevstMailServer.findByImapServer", query = "SELECT s FROM SevstMailServer s WHERE s.imapServer = :imapServer"),
    @NamedQuery(name = "SevstMailServer.findByImapTimeOut", query = "SELECT s FROM SevstMailServer s WHERE s.imapTimeOut = :imapTimeOut"),
    @NamedQuery(name = "SevstMailServer.findByImapPort", query = "SELECT s FROM SevstMailServer s WHERE s.imapPort = :imapPort")})
public class SevstMailServer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "INS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;
    @Size(max = 4)
    @Column(name = "INS_CCD")
    private String insCcd;
    @Size(max = 12)
    @Column(name = "INS_SCD")
    private String insScd;
    @Size(max = 14)
    @Column(name = "INS_PG")
    private String insPg;
    @Column(name = "UPD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    @Size(max = 4)
    @Column(name = "UPD_CCD")
    private String updCcd;
    @Size(max = 12)
    @Column(name = "UPD_SCD")
    private String updScd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "UPD_PG")
    private String updPg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USE_AUTH")
    private short useAuth;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENCRYPT_METHOD")
    private short encryptMethod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUTH_METHOD")
    private short authMethod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "EMAIL_AUTH")
    private String emailAuth;
    @Size(max = 25)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SMTP_IP_VER")
    private short smtpIpVer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SMTP_SERVER")
    private String smtpServer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STMT_TIME_OUT")
    private short stmtTimeOut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STMT_PORT")
    private int stmtPort;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POP_IP_VER")
    private short popIpVer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POP_USE")
    private short popUse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "POP_SERVER")
    private String popServer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POP_TIME_OUT")
    private short popTimeOut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POP_PORT")
    private int popPort;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMAP_IP_VER")
    private short imapIpVer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMAP_USE")
    private short imapUse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "IMAP_SERVER")
    private String imapServer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMAP_TIME_OUT")
    private short imapTimeOut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMAP_PORT")
    private int imapPort;

    public SevstMailServer() {
    }

    public SevstMailServer(String cid) {
        this.cid = cid;
    }

    public SevstMailServer(String cid, String updPg, int exclusVer, short useAuth, short encryptMethod, short authMethod, String emailAuth, short smtpIpVer, String smtpServer, short stmtTimeOut, int stmtPort, short popIpVer, short popUse, String popServer, short popTimeOut, int popPort, short imapIpVer, short imapUse, String imapServer, short imapTimeOut, int imapPort) {
        this.cid = cid;
        this.updPg = updPg;
        this.exclusVer = exclusVer;
        this.useAuth = useAuth;
        this.encryptMethod = encryptMethod;
        this.authMethod = authMethod;
        this.emailAuth = emailAuth;
        this.smtpIpVer = smtpIpVer;
        this.smtpServer = smtpServer;
        this.stmtTimeOut = stmtTimeOut;
        this.stmtPort = stmtPort;
        this.popIpVer = popIpVer;
        this.popUse = popUse;
        this.popServer = popServer;
        this.popTimeOut = popTimeOut;
        this.popPort = popPort;
        this.imapIpVer = imapIpVer;
        this.imapUse = imapUse;
        this.imapServer = imapServer;
        this.imapTimeOut = imapTimeOut;
        this.imapPort = imapPort;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SevstMailServer)) {
            return false;
        }
        SevstMailServer other = (SevstMailServer) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SevstMailServer[ cid=" + cid + " ]";
    }
    
}
