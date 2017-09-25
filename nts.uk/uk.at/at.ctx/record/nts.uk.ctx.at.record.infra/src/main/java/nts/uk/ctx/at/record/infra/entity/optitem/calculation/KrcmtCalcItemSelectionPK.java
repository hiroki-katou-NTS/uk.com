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
 * The Class KrcmtCalcItemSelectionPK.
 */
@Getter
@Setter
@Embeddable
public class KrcmtCalcItemSelectionPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The optional item no. */
	@Column(name = "OPTIONAL_ITEM_NO")
	private String optionalItemNo;

	/** The formula id. */
	@Column(name = "FORMULA_ID")
	private String formulaId;

	/** The attendance item id. */
	@Column(name = "ATTENDANCE_ITEM_ID")
	private String attendanceItemId;

	/**
	 * Instantiates a new krcmt calc item selection PK.
	 */
	public KrcmtCalcItemSelectionPK() {
		super();
	}

	/**
	 * Instantiates a new krcmt calc item selection PK.
	 *
	 * @param cid the cid
	 * @param optionalItemNo the optional item no
	 * @param formulaId the formula id
	 * @param attendanceItemId the attendance item id
	 */
	public KrcmtCalcItemSelectionPK(String cid, String optionalItemNo, String formulaId, String attendanceItemId) {
		this.cid = cid;
		this.optionalItemNo = optionalItemNo;
		this.formulaId = formulaId;
		this.attendanceItemId = attendanceItemId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (optionalItemNo != null ? optionalItemNo.hashCode() : 0);
		hash += (formulaId != null ? formulaId.hashCode() : 0);
		hash += (attendanceItemId != null ? attendanceItemId.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcItemSelectionPK)) {
			return false;
		}
		KrcmtCalcItemSelectionPK other = (KrcmtCalcItemSelectionPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.optionalItemNo == null && other.optionalItemNo != null)
				|| (this.optionalItemNo != null && !this.optionalItemNo.equals(other.optionalItemNo))) {
			return false;
		}
		if ((this.formulaId == null && other.formulaId != null)
				|| (this.formulaId != null && !this.formulaId.equals(other.formulaId))) {
			return false;
		}
		if ((this.attendanceItemId == null && other.attendanceItemId != null)
				|| (this.attendanceItemId != null && !this.attendanceItemId.equals(other.attendanceItemId))) {
			return false;
		}
		return true;
	}

}
