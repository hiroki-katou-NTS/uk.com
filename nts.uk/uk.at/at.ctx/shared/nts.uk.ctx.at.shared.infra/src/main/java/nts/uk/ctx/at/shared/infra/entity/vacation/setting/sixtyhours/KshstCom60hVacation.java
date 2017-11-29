/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstCom60hVacation.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_COM_60H_VACATION")
public class KshstCom60hVacation extends KshstSixtyHourVacationSetting implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;
	
	public KshstCom60hVacation() {
		super();
	}

	public KshstCom60hVacation(String cid) {
		this.cid = cid;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstCom60hVacation)) {
			return false;
		}
		KshstCom60hVacation other = (KshstCom60hVacation) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KshstCom60hVacation[ cid=" + cid + " ]";
	}

	@Override
	protected Object getKey() {
		return this.cid;
	}

}
