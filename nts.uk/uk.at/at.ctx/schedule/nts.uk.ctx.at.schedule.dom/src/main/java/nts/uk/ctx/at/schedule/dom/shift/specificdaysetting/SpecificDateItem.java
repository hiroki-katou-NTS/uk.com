package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 特定日項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.特定日設定.特定日項目
 */
@Getter
@AllArgsConstructor
public class SpecificDateItem extends AggregateRoot {
	
	/** 会社ID **/
	private final String companyId;
	
	/** 特定日項目NO **/
	private final SpecificDateItemNo specificDateItemNo;
	
	/** 名称 **/
	@Setter
	private SpecificName specificName;
	
	/** 使用区分 **/
	@Setter
	private NotUseAtr useAtr;
	
	public static SpecificDateItem createFromJavaType(String companyId, Integer useAtr, Integer specificDateItemNo, String specificName) {
		return new SpecificDateItem(
				companyId,
				new SpecificDateItemNo(specificDateItemNo), 
				new SpecificName(specificName),
				NotUseAtr.valueOf(useAtr));
	}

}
