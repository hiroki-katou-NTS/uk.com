package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;



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

	public static WorkScheDisplaySetting calcuInitDisplayPeriod() {
		//$基準日 = 年月日#今日()		
		GeneralDate baseDate = GeneralDate.today();
		/**	if @初期表示期間の月 == 翌月																						
			$基準日 = $基準日.月を足す(1)
		 */	
		return null;
		
	}

	public WorkScheDisplaySetting(String companyID, InitDispMonth initDispMonth, OneMonth endDay) {
		super();
		this.companyID = companyID;
		this.initDispMonth = initDispMonth;
		this.endDay = endDay;
	}

	
	

}
