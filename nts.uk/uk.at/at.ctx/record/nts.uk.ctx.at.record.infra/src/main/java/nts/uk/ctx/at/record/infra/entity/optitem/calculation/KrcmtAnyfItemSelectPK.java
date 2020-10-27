/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtAnyfItemSelectPK.
 */
@Getter
@Setter
@Embeddable
public class KrcmtAnyfItemSelectPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The optional item no. */
	@Column(name = "OPTIONAL_ITEM_NO")
	private Integer optionalItemNo;

	/** The formula id. */
	@Column(name = "FORMULA_ID")
	private String formulaId;

	/** The attendance item id. */
	@Column(name = "ATTENDANCE_ITEM_ID")
	private int attendanceItemId;

	/**
	 * Instantiates a new krcmt calc item selection PK.
	 */
	public KrcmtAnyfItemSelectPK() {
		super();
	}

	/**
	 * Instantiates a new krcmt calc item selection PK.
	 *
	 * @param formulaPk the formula pk
	 * @param attendanceItemId the attendance item id
	 */
	public KrcmtAnyfItemSelectPK(KrcmtAnyfPK formulaPk, int attendanceItemId) {
		this.cid = formulaPk.getCid();
		this.optionalItemNo = formulaPk.getOptionalItemNo();
		this.formulaId = formulaPk.getFormulaId();
		this.attendanceItemId = attendanceItemId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attendanceItemId;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((formulaId == null) ? 0 : formulaId.hashCode());
		result = prime * result + ((optionalItemNo == null) ? 0 : optionalItemNo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrcmtAnyfItemSelectPK other = (KrcmtAnyfItemSelectPK) obj;
		if (attendanceItemId != other.attendanceItemId)
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (formulaId == null) {
			if (other.formulaId != null)
				return false;
		} else if (!formulaId.equals(other.formulaId))
			return false;
		if (optionalItemNo == null) {
			if (other.optionalItemNo != null)
				return false;
		} else if (!optionalItemNo.equals(other.optionalItemNo))
			return false;
		return true;
	}

}
