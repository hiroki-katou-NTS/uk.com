package nts.uk.ctx.at.schedule.dom.shift.pattern.export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 実装：所定労働日数を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetPredWorkingDaysImpl implements GetPredWorkingDays {

	/** 月間勤務就業設定 */
	@Inject
	private WorkMonthlySettingRepository workMonthlySetRepo;
	/** 勤務種類 */
	@Inject
	private WorkTypeRepository workTypeRepo;

	/** 指定期間の所定労働日数を取得する(大塚用) */
	@Override
	public AttendanceDaysMonth byPeriod(DatePeriod period) {
		return this.byPeriod(period, new HashMap<>());
	}

	/** 指定期間の所定労働日数を取得する(大塚用) */
	@Override
	public AttendanceDaysMonth byPeriod(DatePeriod period, Map<String, WorkType> workTypeMap) {
		val require = new GetPredWorkingDaysImpl.Require() {

			@Override
			public List<WorkMonthlySetting> findByStartEndDate(String companyId, String monthlyPatternCode,
					GeneralDate startDate, GeneralDate endDate) {
				return workMonthlySetRepo.findByStartEndDate(companyId, monthlyPatternCode, startDate, endDate);
			}
			@Override
			public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
				return workTypeRepo.findByPK(companyId, workTypeCd);
			}
		};
		return byPeriodRequire(require, period, workTypeMap);
	}
	@Override
	public AttendanceDaysMonth byPeriodRequire(Require require, DatePeriod period, Map<String, WorkType> workTypeMap) {
		double result = 0.0;

		// 勤務種類マップ　初期設定
		Map<String, WorkType> _workTypeMap = new HashMap<>();
		_workTypeMap.putAll(workTypeMap);

		// 「月間勤務就業設定」を取得する
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();
		List<WorkMonthlySetting> workMonthlySetList = require.findByStartEndDate(
				companyId, "001", period.start(), period.end().addDays(1));

		for (WorkMonthlySetting workMonthlySet : workMonthlySetList) {

			// 「勤務種類」を取得する
			if (workMonthlySet.getWorkInformation().getWorkTypeCode() == null) continue;
			String workTypeCode = workMonthlySet.getWorkInformation().getWorkTypeCode().v();
			if (!_workTypeMap.containsKey(workTypeCode)) {
				Optional<WorkType> workTypeOpt = require.findByPK(companyId, workTypeCode);
				if (workTypeOpt.isPresent()) {
					_workTypeMap.put(workTypeCode, workTypeOpt.get());
				}
				else {
					_workTypeMap.put(workTypeCode, null);
				}
			}
			WorkType workType = _workTypeMap.get(workTypeCode);
			if (workType == null) continue;

			// 勤務種類から出勤日区分を取得する
			switch (workType.chechAttendanceDay()) {
				case FULL_TIME:		// 1日出勤
					result += 1.0;
					break;
				case HALF_TIME_AM:	// 半日出勤(午前)
				case HALF_TIME_PM:	// 半日出勤(午後)
					result += 0.5;
					break;
				default:
					break;
			}
		}

		return new AttendanceDaysMonth(result);
	}

	public static interface Require{
//		this.workMonthlySetRepo.findByStartEndDate(companyId, "001", period.start(), period.end().addDays(1));
		List<WorkMonthlySetting> findByStartEndDate(String companyId, String monthlyPatternCode,GeneralDate startDate, GeneralDate endDate);
//		this.workTypeRepo.findByPK(companyId, workTypeCode);
		Optional<WorkType> findByPK(String companyId, String workTypeCd);
	}
}
