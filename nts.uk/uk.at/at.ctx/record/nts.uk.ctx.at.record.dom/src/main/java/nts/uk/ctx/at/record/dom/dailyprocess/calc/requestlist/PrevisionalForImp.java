package nts.uk.ctx.at.record.dom.dailyprocess.calc.requestlist;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 日別実績の仮計算(申請・スケからの窓口)の引数管理クラス
 * 
 * @author keisuke_hoshina
 *
 */
@Getter
public class PrevisionalForImp {
	//社員ID
	String employeeId;
	//対象年月日
	GeneralDate targetDate;
	//出退勤時刻
	Map<Integer, TimeZone> timeSheets;
	//勤種コード
	WorkTypeCode workTypeCode;
	//就時コード
	WorkTimeCode workTimeCode;
	//休憩時間帯
	List<BreakTimeSheet> breakTimeSheets;
	//外出時間帯
	List<OutingTimeSheet> outingTimeSheets;
	//短時間勤務時間帯
	List<ShortWorkingTimeSheet> shortWorkingTimeSheets;
	
	/**
	 * Constructor 
	 */
	public PrevisionalForImp(String employeeId, GeneralDate targetDate, Map<Integer, TimeZone> timeSheets,
			WorkTypeCode workTypeCode, WorkTimeCode workTimeCode, List<BreakTimeSheet> breakTimeSheets,
			List<OutingTimeSheet> outingTimeSheets, List<ShortWorkingTimeSheet> shortWorkingTimeSheets) {
		super();
		this.employeeId = employeeId;
		this.targetDate = targetDate;
		this.timeSheets = timeSheets;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.breakTimeSheets = breakTimeSheets;
		this.outingTimeSheets = outingTimeSheets;
		this.shortWorkingTimeSheets = shortWorkingTimeSheets;
	}
}
