package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author loivt
 */
@Embeddable
public class KrqdtAppOvertimePK_Old implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    private String cid;
    
    @Column(name = "APP_ID")
    private String appId;

    public KrqdtAppOvertimePK_Old() {
    }

    public KrqdtAppOvertimePK_Old(String cid, String appId) {
        this.cid = cid;
        this.appId = appId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (appId != null ? appId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrqdtAppOvertimePK_Old)) {
            return false;
        }
        KrqdtAppOvertimePK_Old other = (KrqdtAppOvertimePK_Old) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.appId == null && other.appId != null) || (this.appId != null && !this.appId.equals(other.appId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KrqdtAppOvertime.KrqdtAppOvertimePK[ cid=" + cid + ", appId=" + appId + " ]";
    }
    
}