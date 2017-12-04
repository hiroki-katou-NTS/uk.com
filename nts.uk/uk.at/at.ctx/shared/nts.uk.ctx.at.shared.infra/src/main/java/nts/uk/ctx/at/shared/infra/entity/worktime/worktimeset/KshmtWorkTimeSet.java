/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.worktimeset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KSHMT_WORK_TIME_SET")
@Getter
@Setter
public class KshmtWorkTimeSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected KshmtWorkTimeSetPK kshmtWorkTimeSetPK;

	@Column(name = "NAME")
	private String name;

	@Column(name = "AB_NAME")
	private String abName;

	@Column(name = "SYMBOL")
	private String symbol;

	@Column(name = "DAILY_WORK_ATR")
	private Integer dailyWorkAtr;

	@Column(name = "WORKTIME_SET_METHOD")
	private Integer worktimeSetMethod;

	@Column(name = "ABOLITION_ATR")
	private Integer abolitionAtr;

	@Column(name = "COLOR")
	private String color;

	@Column(name = "MEMO")
	private String memo;

	@Column(name = "NOTE")
	private String note;

	public KshmtWorkTimeSet() {
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWorkTimeSetPK != null ? kshmtWorkTimeSetPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkTimeSet)) {
			return false;
		}
		KshmtWorkTimeSet other = (KshmtWorkTimeSet) object;
		if ((this.kshmtWorkTimeSetPK == null && other.kshmtWorkTimeSetPK != null)
				|| (this.kshmtWorkTimeSetPK != null && !this.kshmtWorkTimeSetPK.equals(other.kshmtWorkTimeSetPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "javaapplication1.KshmtWorkTimeSet[ kshmtWorkTimeSetPK=" + kshmtWorkTimeSetPK + " ]";
	}

	@Override
	protected Object getKey() {
		return this.kshmtWorkTimeSetPK;
	}

}
