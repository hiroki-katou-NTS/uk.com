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
 * The Class KshmtHd60hCom.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_HD60H_COM")
public class KshmtHd60hCom extends KshstSixtyHourVacationSetting implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;
	
	public KshmtHd60hCom() {
		super();
	}

	public KshmtHd60hCom(String cid) {
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
		if (!(object instanceof KshmtHd60hCom)) {
			return false;
		}
		KshmtHd60hCom other = (KshmtHd60hCom) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KshmtHd60hCom[ cid=" + cid + " ]";
	}

	@Override
	protected Object getKey() {
		return this.cid;
	}

}
