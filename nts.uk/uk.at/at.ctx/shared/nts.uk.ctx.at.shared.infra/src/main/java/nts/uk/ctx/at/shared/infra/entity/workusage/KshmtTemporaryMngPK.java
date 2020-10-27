package nts.uk.ctx.at.shared.infra.entity.workusage;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
//import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the KSHMT_TEMPORARY_MNG database table.
 * 
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KshmtTemporaryMngPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name="CID")
	private String cid;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KshmtTemporaryMngPK)) {
			return false;
		}
		KshmtTemporaryMngPK castOther = (KshmtTemporaryMngPK)other;
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