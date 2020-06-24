package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.DateInMonth;

/**
 * 勤務予定の表示設定
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * @author HieuLT
 *
 */
@AllArgsConstructor
public class WorkScheDisplaySetting implements DomainAggregate {
	@Getter
	/** 会社ID **/ 
	private final String companyID;
	
	@Getter
	/** 初期表示の月 */
	private InitDispMonth initDispMonth;
	
	@Getter
	/** 初期表示期間の終了日 : 一ヶ月間 **/
	private DateInMonth endDay;

	


}
