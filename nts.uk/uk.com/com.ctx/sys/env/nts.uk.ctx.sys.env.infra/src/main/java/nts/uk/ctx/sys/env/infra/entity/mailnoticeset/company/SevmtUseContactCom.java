package nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "SEVMT_USE_CONTACT_COM")
@Getter
@Setter
public class SevmtUseContactCom extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SevmtUseContactComPK sevmtUseContactComPK;
    
    @Column(name = "EXCLUS_VER")
    private int exclusVer;

    @Column(name = "SELF_EDIT")
    private int selfEdit;
    
    @Column(name = "USE_MAIL_SET")
    private Integer useMailSet;

    public SevmtUseContactCom() {
    }
    
    public SevmtUseContactCom(SevmtUseContactComPK sevmtUseContactComPK) {
        this.sevmtUseContactComPK = sevmtUseContactComPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sevmtUseContactComPK != null ? sevmtUseContactComPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SevmtUseContactCom)) {
            return false;
        }
        SevmtUseContactCom other = (SevmtUseContactCom) object;
        if ((this.sevmtUseContactComPK == null && other.sevmtUseContactComPK != null) || (this.sevmtUseContactComPK != null && !this.sevmtUseContactComPK.equals(other.sevmtUseContactComPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.SevmtUseContactCom[ sevmtUseContactComPK=" + sevmtUseContactComPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.sevmtUseContactComPK;
	}
    
}
