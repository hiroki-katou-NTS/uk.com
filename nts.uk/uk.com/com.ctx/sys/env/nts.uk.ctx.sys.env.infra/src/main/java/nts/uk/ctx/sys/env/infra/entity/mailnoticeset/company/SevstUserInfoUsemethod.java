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
@Table(name = "SEVST_USER_INFO_USEMETHOD")
@Getter
@Setter
public class SevstUserInfoUsemethod extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SevstUserInfoUsemethodPK sevstUserInfoUsemethodPK;
    
    @Column(name = "EXCLUS_VER")
    private int exclusVer;

    @Column(name = "SELF_EDIT")
    private int selfEdit;
    
    @Column(name = "USE_MAIL_SET")
    private Integer useMailSet;

    public SevstUserInfoUsemethod() {
    }
    
    public SevstUserInfoUsemethod(SevstUserInfoUsemethodPK sevstUserInfoUsemethodPK) {
        this.sevstUserInfoUsemethodPK = sevstUserInfoUsemethodPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sevstUserInfoUsemethodPK != null ? sevstUserInfoUsemethodPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SevstUserInfoUsemethod)) {
            return false;
        }
        SevstUserInfoUsemethod other = (SevstUserInfoUsemethod) object;
        if ((this.sevstUserInfoUsemethodPK == null && other.sevstUserInfoUsemethodPK != null) || (this.sevstUserInfoUsemethodPK != null && !this.sevstUserInfoUsemethodPK.equals(other.sevstUserInfoUsemethodPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.SevstUserInfoUsemethod[ sevstUserInfoUsemethodPK=" + sevstUserInfoUsemethodPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.sevstUserInfoUsemethodPK;
	}
    
}
