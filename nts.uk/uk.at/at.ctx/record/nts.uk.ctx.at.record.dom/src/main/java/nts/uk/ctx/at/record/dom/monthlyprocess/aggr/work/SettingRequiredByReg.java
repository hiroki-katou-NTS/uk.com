package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriod;

/**
 * 通常勤務が必要とする設定
 * @author shuichu_ishida
 */
@Getter
public class SettingRequiredByReg {

	/** 通常勤務の法定内集計設定 */
	@Setter
	private RegularWorkTimeAggrSet regularAggrSet;
	/** 月次集計の法定内振替順設定 */
	@Setter
	private LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet;
	/** 残業枠の役割 */
	private Map<Integer, RoleOvertimeWork> roleOverTimeFrameMap;
	/** 休出枠の役割 */
	private Map<Integer, RoleOfOpenPeriod> roleHolidayWorkFrameMap;
	/** 自動的に除く残業枠 */
	private List<RoleOvertimeWork> autoExceptOverTimeFrames;
	/** 自動的に除く休出枠 */
	private List<RoleOfOpenPeriod> autoExceptHolidayWorkFrames;
	/** 休暇加算時間設定 */
	private Map<String, AggregateRoot> holidayAdditionMap;
	/** 週間法定労働時間 */
	@Setter
	private AttendanceTimeMonth statutoryWorkingTimeWeek;
	/** 月間法定労働時間 */
	@Setter
	private AttendanceTimeMonth statutoryWorkingTimeMonth;

	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public SettingRequiredByReg(String companyId){
		
		this.regularAggrSet = null;
		this.legalTransferOrderSet = new LegalTransferOrderSetOfAggrMonthly(companyId);
		this.roleOverTimeFrameMap = new HashMap<>();
		this.roleHolidayWorkFrameMap = new HashMap<>();
		this.autoExceptOverTimeFrames = new ArrayList<>();
		this.autoExceptHolidayWorkFrames = new ArrayList<>();
		this.holidayAdditionMap = new HashMap<>();
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.statutoryWorkingTimeWeek = new AttendanceTimeMonth(0);
	}
}
