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
@Table(name = "SEVMT_MAIL_DESTIN_FUNC")
@Getter
@Setter
public class SevmtMailDestinFunc extends ContractUkJpaEntity implements Serializable{

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SevmtMailDestinFuncPK sevmtMailDestinFuncPK;
    
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    @Column(name = "SEND_SET")
    private int sendSet;

    public SevmtMailDestinFunc() {
	}
	public SevmtMailDestinFunc(SevmtMailDestinFuncPK sevmtMailDestinFuncPK) {
		this.sevmtMailDestinFuncPK = sevmtMailDestinFuncPK;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sevmtMailDestinFuncPK != null ? sevmtMailDestinFuncPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SevmtMailDestinFunc)) {
            return false;
        }
        SevmtMailDestinFunc other = (SevmtMailDestinFunc) object;
        if ((this.sevmtMailDestinFuncPK == null && other.sevmtMailDestinFuncPK != null) || (this.sevmtMailDestinFuncPK != null && !this.sevmtMailDestinFuncPK.equals(other.sevmtMailDestinFuncPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.SevmtMailDestinFunc[ sevmtMailDestinFuncPK=" + sevmtMailDestinFuncPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.sevmtMailDestinFuncPK;
	}
    
}
