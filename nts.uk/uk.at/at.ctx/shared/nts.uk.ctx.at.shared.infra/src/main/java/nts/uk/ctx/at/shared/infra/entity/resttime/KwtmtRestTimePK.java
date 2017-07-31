package nts.uk.ctx.at.shared.infra.entity.resttime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class KwtmtRestTimePK implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "SIFT_CD")
    private String siftCd;

    public KwtmtRestTimePK() {
    }

    public KwtmtRestTimePK(String cid, String siftCd) {
        this.cid = cid;
        this.siftCd = siftCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (siftCd != null ? siftCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KwtmtRestTimePK)) {
            return false;
        }
        KwtmtRestTimePK other = (KwtmtRestTimePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.siftCd == null && other.siftCd != null) || (this.siftCd != null && !this.siftCd.equals(other.siftCd))) {
            return false;
        }
        return true;
    }
}
