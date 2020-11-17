package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

import nts.uk.shr.com.i18n.TextResource;

/**
 * フレックス不足時の繰越設定
 * @author shuichi_ishida
 */
public enum CarryforwardSetInShortageFlex {
	/** 当月積算 */
	CURRENT_MONTH_INTEGRATION(0, TextResource.localize("当月清算")),
	/** 翌月繰越 */
	NEXT_MONTH_CARRYFORWARD(1, TextResource.localize("翌月繰越"));

	/** The Constant values. */
	private final static CarryforwardSetInShortageFlex[] values = CarryforwardSetInShortageFlex.values();
	
	public int value;
	
	public String nameId;
	private CarryforwardSetInShortageFlex(int value ,String nameId){
		this.nameId = nameId;
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the method
	 */
	public static CarryforwardSetInShortageFlex valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CarryforwardSetInShortageFlex val : CarryforwardSetInShortageFlex.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
