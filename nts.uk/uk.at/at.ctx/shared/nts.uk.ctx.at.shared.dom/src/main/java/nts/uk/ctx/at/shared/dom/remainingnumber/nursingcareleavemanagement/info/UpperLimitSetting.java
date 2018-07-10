package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UpperLimitSetting {
	
	//家族情報を参照
	FAMILY_INFO(1),
	
	//個人情報を参照（毎年利用）
	PER_INFO_EVERY_YEAR(2),
	
	//個人情報を参照（本年度のみ利用）
	PER_INFO_FISCAL_YEAR(3);
	
	public final int value;
	
}
