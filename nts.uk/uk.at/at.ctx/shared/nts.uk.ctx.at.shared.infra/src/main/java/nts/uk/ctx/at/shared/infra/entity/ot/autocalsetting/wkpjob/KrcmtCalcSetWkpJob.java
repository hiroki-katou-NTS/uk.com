/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.KshmtAutoCalSet;

/**
 * The Class KrcmtCalcSetWkpJob.
 */
@Setter
@Getter
@Entity
@Table(name = "KRCMT_CALC_SET_WKP_JOB")
public class KrcmtCalcSetWkpJob extends KshmtAutoCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt auto wkp job cal PK. */
	@EmbeddedId
	protected KrcmtCalcSetWkpJobPK krcmtCalcSetWkpJobPK;

	/**
	 * Instantiates a new kshmt auto wkp job cal.
	 */
	public KrcmtCalcSetWkpJob() {
		super();
	}

	/**
	 * Instantiates a new kshmt auto wkp job cal.
	 *
	 * @param krcmtCalcSetWkpJobPK
	 *            the kshmt auto wkp job cal PK
	 */
	public KrcmtCalcSetWkpJob(KrcmtCalcSetWkpJobPK krcmtCalcSetWkpJobPK) {
		this.krcmtCalcSetWkpJobPK = krcmtCalcSetWkpJobPK;
	}

	/**
	 * Instantiates a new kshmt auto wkp job cal.
	 *
	 * @param cid
	 *            the cid
	 * @param wpkid
	 *            the wpkid
	 * @param jobid
	 *            the jobid
	 */
	public KrcmtCalcSetWkpJob(String cid, String wpkid, String jobid) {
		this.krcmtCalcSetWkpJobPK = new KrcmtCalcSetWkpJobPK(cid, wpkid, jobid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtCalcSetWkpJobPK != null ? krcmtCalcSetWkpJobPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcSetWkpJob)) {
			return false;
		}
		KrcmtCalcSetWkpJob other = (KrcmtCalcSetWkpJob) object;
		if ((this.krcmtCalcSetWkpJobPK == null && other.krcmtCalcSetWkpJobPK != null)
				|| (this.krcmtCalcSetWkpJobPK != null
						&& !this.krcmtCalcSetWkpJobPK.equals(other.krcmtCalcSetWkpJobPK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.krcmtCalcSetWkpJobPK;
	}

}
