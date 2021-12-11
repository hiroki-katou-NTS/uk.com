package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * 付与前、付与後の区分
 */
@Getter
public enum GrantBeforeAfterAtr {

	/**
	 * 付与前
	 */
	BEFORE_GRANT(1),
	/**
	 * 付与後
	 */
	AFTER_GRANT(2);


	public int value;

	GrantBeforeAfterAtr(int type){
		this.value = type;
	}

	public static GrantBeforeAfterAtr toEnum(int value){
		return EnumAdaptor.valueOf(value, GrantBeforeAfterAtr.class);
	}

}