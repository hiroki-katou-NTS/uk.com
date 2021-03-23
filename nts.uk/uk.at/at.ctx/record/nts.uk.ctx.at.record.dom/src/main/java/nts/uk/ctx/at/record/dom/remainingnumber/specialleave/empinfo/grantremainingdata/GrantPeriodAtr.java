package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * 付与前、付与後の期間区分
 */
@Getter
public enum GrantPeriodAtr {

	/**
	 * 付与前
	 */
	BEFORE_GRANT(1),
	/**
	 * 付与後
	 */
	AFTER_GRANT(2);


	public int value;

	GrantPeriodAtr(int type){
		this.value = type;
	}

	public static GrantPeriodAtr toEnum(int value){
		return EnumAdaptor.valueOf(value, GrantPeriodAtr.class);
	}

}