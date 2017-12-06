/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KSHMT_WORK_TIME_SHEET_SET")
@Getter
@Setter
public class KshmtWorkTimeSheetSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected KshmtWorkTimeSheetSetPK kshmtWorkTimeSheetSetPK;

	@Column(name = "USE_ATR")
	private Integer useAtr;

	@Column(name = "START_TIME")
	private Integer startTime;

	@Column(name = "END_TIME")
	private Integer endTime;

	public KshmtWorkTimeSheetSet() {
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWorkTimeSheetSetPK != null ? kshmtWorkTimeSheetSetPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkTimeSheetSet)) {
			return false;
		}
		KshmtWorkTimeSheetSet other = (KshmtWorkTimeSheetSet) object;
		if ((this.kshmtWorkTimeSheetSetPK == null && other.kshmtWorkTimeSheetSetPK != null)
				|| (this.kshmtWorkTimeSheetSetPK != null
						&& !this.kshmtWorkTimeSheetSetPK.equals(other.kshmtWorkTimeSheetSetPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "javaapplication1.KshmtWorkTimeSheetSet[ kshmtWorkTimeSheetSetPK=" + kshmtWorkTimeSheetSetPK + " ]";
	}

	@Override
	protected Object getKey() {
		return this.kshmtWorkTimeSheetSetPK;
	}

}
