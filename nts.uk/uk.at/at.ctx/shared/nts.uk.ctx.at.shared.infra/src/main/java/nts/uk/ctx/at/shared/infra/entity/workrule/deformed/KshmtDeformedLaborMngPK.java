package nts.uk.ctx.at.shared.infra.entity.workrule.deformed;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the KSHMT_DEFORMED_LABOR_MNG database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KshmtDeformedLaborMngPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name="CID")
	private String cid;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KshmtDeformedLaborMngPK)) {
			return false;
		}
		KshmtDeformedLaborMngPK castOther = (KshmtDeformedLaborMngPK)other;
		return 
			this.cid.equals(castOther.cid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		
		return hash;
	}
}