package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The persistent class for the KRCMT_DVGC_ATTENDANCE database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DVGC_ATTENDANCE")
public class KrcmtDvgcAttendance extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KrcmtDvgcAttendancePK id;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}