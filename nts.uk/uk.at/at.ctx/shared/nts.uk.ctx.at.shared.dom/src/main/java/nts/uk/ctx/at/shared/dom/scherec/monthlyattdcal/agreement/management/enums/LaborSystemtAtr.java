package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums;

/**
 * ３６協定労働制
 * @author nampt
 *
 */
public enum LaborSystemtAtr {
	
	/** 一般労働制 */
	GENERAL_LABOR_SYSTEM(0),
	/** 変形労働時間制 */
	DEFORMATION_WORKING_TIME_SYSTEM(1);
	
	public final int value;
	
	private LaborSystemtAtr(int type) {
		this.value = type;
	}

	
}
