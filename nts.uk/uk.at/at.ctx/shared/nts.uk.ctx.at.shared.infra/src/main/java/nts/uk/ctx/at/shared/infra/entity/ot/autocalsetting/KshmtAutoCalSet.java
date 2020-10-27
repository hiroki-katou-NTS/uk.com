/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcmtCalcSetCom.
 */

@Setter
@Getter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KshmtAutoCalSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The early ot time limit. */
	@Column(name = "EARLY_OT_TIME_LIMIT")
	private int earlyOtTimeLimit;

	/** The early mid ot time limit. */
	@Column(name = "EARLY_MID_OT_TIME_LIMIT")
	private int earlyMidOtTimeLimit;

	/** The normal ot time limit. */
	@Column(name = "NORMAL_OT_TIME_LIMIT")
	private int normalOtTimeLimit;

	/** The normal mid ot time limit. */
	@Column(name = "NORMAL_MID_OT_TIME_LIMIT")
	private int normalMidOtTimeLimit;

	/** The legal ot time limit. */
	@Column(name = "LEGAL_OT_TIME_LIMIT")
	private int legalOtTimeLimit;

	/** The legal mid ot time limit. */
	@Column(name = "LEGAL_MID_OT_TIME_LIMIT")
	private int legalMidOtTimeLimit;

	/** The flex ot time limit. */
	@Column(name = "FLEX_OT_TIME_LIMIT")
	private int flexOtTimeLimit;

	/** The rest time limit. */
	@Column(name = "REST_TIME_LIMIT")
	private int restTimeLimit;

	/** The late night time limit. */
	@Column(name = "LATE_NIGHT_TIME_LIMIT")
	private int lateNightTimeLimit;

	/** The early ot time atr. */
	@Column(name = "EARLY_OT_TIME_ATR")
	private int earlyOtTimeAtr;

	/** The early mid ot time atr. */
	@Column(name = "EARLY_MID_OT_TIME_ATR")
	private int earlyMidOtTimeAtr;

	/** The normal ot time atr. */
	@Column(name = "NORMAL_OT_TIME_ATR")
	private int normalOtTimeAtr;

	/** The normal mid ot time atr. */
	@Column(name = "NORMAL_MID_OT_TIME_ATR")
	private int normalMidOtTimeAtr;

	/** The legal ot time atr. */
	@Column(name = "LEGAL_OT_TIME_ATR")
	private int legalOtTimeAtr;

	/** The legal mid ot time atr. */
	@Column(name = "LEGAL_MID_OT_TIME_ATR")
	private int legalMidOtTimeAtr;

	/** The flex ot time atr. */
	@Column(name = "FLEX_OT_TIME_ATR")
	private int flexOtTimeAtr;

	/** The rest time atr. */
	@Column(name = "REST_TIME_ATR")
	private int restTimeAtr;

	/** The late night time atr. */
	@Column(name = "LATE_NIGHT_TIME_ATR")
	private int lateNightTimeAtr;
	
	/** The raising calc atr. */
	@Column(name = "RAISING_CALC_ATR")
	private int raisingCalcAtr;
	
	/** The specific raising calc atr. */
	@Column(name = "SPECIFIC_RAISING_CALC_ATR")
	private int specificRaisingCalcAtr;
	
	/** The leave early. */
	@Column(name = "LEAVE_EARLY")
	private int leaveEarly;
	
	/** The leave late. */
	@Column(name = "LEAVE_LATE")
	private int leaveLate;
	
	/** The divergence. */
	@Column(name = "DIVERGENCE")
	private int divergence;

	/**
	 * Checks if is leave late.
	 *
	 * @return true, if is leave late
	 */
	public boolean isLeaveLate(){
		return this.leaveLate == 0 ? false : true;
	}
	
	/**
	 * Checks if is leave early.
	 *
	 * @return true, if is leave early
	 */
	public boolean isLeaveEarly(){
		return this.leaveEarly == 0 ? false : true;
	}
	
	/**
	 * Checks if is raising calc atr.
	 *
	 * @return true, if is raising calc atr
	 */
	public boolean isRaisingCalcAtr(){
		return this.raisingCalcAtr == 0 ? false : true;
	}
	
	/**
	 * Checks if is specific raising calc atr.
	 *
	 * @return true, if is specific raising calc atr
	 */
	public boolean isSpecificRaisingCalcAtr(){
		return this.specificRaisingCalcAtr == 0 ? false : true;
	}

	/**
	 * Sets the leave early.
	 *
	 * @param val the new leave early
	 */
	public void setLeaveEarly(boolean val) {
		this.leaveEarly = val ? 1 : 0;
	}

	/**
	 * Sets the leave late.
	 *
	 * @param val the new leave late
	 */
	public void setLeaveLate(boolean val) {
		this.leaveLate = val ? 1 : 0;
	}

	/**
	 * Sets the raising calc atr.
	 *
	 * @param val the new raising calc atr
	 */
	public void setRaisingCalcAtr(boolean val) {
		this.raisingCalcAtr = val ? 1 : 0;
	}

	/**
	 * Sets the specific raising calc atr.
	 *
	 * @param val the new specific raising calc atr
	 */
	public void setSpecificRaisingCalcAtr(boolean val) {
		this.specificRaisingCalcAtr = val ? 1 : 0;
	}
}
