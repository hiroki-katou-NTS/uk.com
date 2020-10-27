package nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class SevmtUseContactComPK implements Serializable {

	private static final long serialVersionUID = 1L;
   
	@Column(name = "CID")
    private String cid;
    
    @Column(name = "SETTING_ITEM")
    private int settingItem;

    public SevmtUseContactComPK() {
    }
    
    public SevmtUseContactComPK(String cid, int settingItem) {
        this.cid = cid;
        this.settingItem = settingItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) settingItem;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SevmtUseContactComPK)) {
            return false;
        }
        SevmtUseContactComPK other = (SevmtUseContactComPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.settingItem != other.settingItem) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.SevmtUseContactComPK[ cid=" + cid + ", settingItem=" + settingItem + " ]";
    }
    
}
