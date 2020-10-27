package nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class SevmtMailDestinFuncPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
    private String cid;
    
    @Column(name = "SETTING_ITEM")
    private Integer settingItem;
    
    @Column(name = "FUNCTION_ID")
    private Integer functionId;
    
    public SevmtMailDestinFuncPK() {
    }
    
    public SevmtMailDestinFuncPK(String cid, int settingItem, int functionId) {
        this.cid = cid;
        this.settingItem = settingItem;
        this.functionId = functionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) settingItem;
        hash += (int) functionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SevmtMailDestinFuncPK)) {
            return false;
        }
        SevmtMailDestinFuncPK other = (SevmtMailDestinFuncPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.settingItem != other.settingItem) {
            return false;
        }
        if (this.functionId != other.functionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.SevmtMailDestinFuncPK[ cid=" + cid + ", settingItem=" + settingItem + ", functionId=" + functionId + " ]";
    }
    
}
