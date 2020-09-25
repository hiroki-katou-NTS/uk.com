package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.OneMonth;
import nts.arc.time.calendar.period.DatePeriod;



/**
 * 勤務予定の表示設定
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * @author HieuLT
 *
 */

public class WorkScheDisplaySetting implements DomainAggregate {
	@Getter
	/** 会社ID **/ 
	private final String companyID;
	
	@Getter
	/** 初期表示の月 */
	private InitDispMonth initDispMonth;
	
	@Getter
	/** 初期表示期間の終了日 : 一ヶ月間 **/
	private OneMonth endDay;

	// [1] 初期表示期間を求める
	public DatePeriod calcuInitDisplayPeriod() {
		//$基準日 = 年月日#今日()		
		GeneralDate baseDate = GeneralDate.today();
		// 	if @初期表示期間の月 == 翌月																						
		if (this.initDispMonth == InitDispMonth.NEXT_MONTH) {
			//$基準日 = $基準日.月を足す(1)
			baseDate = baseDate.addMonths(1);
		}
		// return @初期表示期間の終了日.年月日に対応する期間($基準日)
		return this.endDay.periodOf(baseDate);
	}
	//	[C-0] 勤務予定の表示設定 (会社ID, 初期表示の月, 一ヶ月間)		
	public WorkScheDisplaySetting(String companyID, InitDispMonth initDispMonth, OneMonth endDay) {
		super();
		this.companyID = companyID;
		this.initDispMonth = initDispMonth;
		this.endDay = endDay;
	}

	
	

}
