package nts.uk.ctx.at.record.dom.standardtime.enums;

import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 *Enum : ３６協定労働制
 */
public enum LaborSystemtAtr {
	
	/*
	 * ３６協定労働制
	 */
	//0: 一般労働制
	GENERAL_LABOR_SYSTEM(0),
	//1: 変形労働時間制
	DEFORMATION_WORKING_TIME_SYSTEM(1);
	
	public final int value;
	
	private LaborSystemtAtr(int type) {
		this.value = type;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static LaborSystemtAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (LaborSystemtAtr val : LaborSystemtAtr.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

	/**
	 * GENERAL_LABOR_SYSTEM
	 *
	 */
	public boolean isGeneralLaborSystem(){
		return this.equals(GENERAL_LABOR_SYSTEM);
	}


	/**
	 * DEFORMATION_WORKING_TIME_SYSTEM(1);
	 *
	 */
	public boolean isDefformationWorrkingTimeSystem(){
		return this.equals(DEFORMATION_WORKING_TIME_SYSTEM);
	}

}
