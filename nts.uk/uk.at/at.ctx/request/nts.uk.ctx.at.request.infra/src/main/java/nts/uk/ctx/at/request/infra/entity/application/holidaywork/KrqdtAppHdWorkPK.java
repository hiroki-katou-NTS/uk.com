package nts.uk.ctx.at.request.infra.entity.application.holidaywork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KrqdtAppHdWorkPK implements Serializable {
private static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    private String cid;
    
    @Column(name = "APP_ID")
    private String appId;

    public KrqdtAppHdWorkPK() {
    }

    public KrqdtAppHdWorkPK(String cid, String appId) {
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
}
