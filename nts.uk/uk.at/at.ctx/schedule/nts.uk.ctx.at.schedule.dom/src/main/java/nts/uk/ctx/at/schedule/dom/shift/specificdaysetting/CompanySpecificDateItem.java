package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
/**
 * 全社特定日設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.特定日設定.全社特定日設定
 */
@Getter
@Setter
@AllArgsConstructor
public class CompanySpecificDateItem implements DomainAggregate {
	
	/** 会社ID **/
	private final String companyId;
	
	/** 年月日 **/
	private final GeneralDate specificDate;
	
	/** 特定日項目リスト **/
	private OneDaySpecificItem oneDaySpecificItem;
	
}
