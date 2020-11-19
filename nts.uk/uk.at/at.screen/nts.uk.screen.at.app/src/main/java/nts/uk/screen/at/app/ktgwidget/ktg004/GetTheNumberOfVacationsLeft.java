package nts.uk.screen.at.app.ktgwidget.ktg004;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.AnnualLeaveRemainingNumberImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetAdapter;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.NursingMode;
import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.ShNursingLeaveSettingPub;

@Stateless
public class GetTheNumberOfVacationsLeft {

	@Inject
	private OptionalWidgetAdapter optionalWidgetAdapter; 

	@Inject
	private RecordDomRequireService requireService;
	
	@Inject
	private ShNursingLeaveSettingPub shNursingLeaveSettingPub;
	
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	/**
	 * @name 15.年休残数表示  
	 * @param employeeId 社員ID
	 * @param datePeriod 基準日　（システム日付）
	 * @return
	 */
	public AnnualLeaveRemainingNumberImport annualLeaveResidualNumberIndication(String employeeId, GeneralDate date) {
		//※「基準日時点の年休残数を取得する」はRequestList198
		return optionalWidgetAdapter.getReferDateAnnualLeaveRemainNumber(employeeId, date).getAnnualLeaveRemainNumberImport();
	}
	
	/**
	 * @name 16.積立年休残数表示
	 * @param employeeId 社員ID
	 * @param datePeriod 基準日　　（基準日終了日）
	 * @return
	 */
	public Double numberOfAccumulatedAnnualLeave(String employeeId, GeneralDate date) {
		//※「基準日時点の積立年休残数を取得する」はRequestList201
		return optionalWidgetAdapter.getNumberOfReservedYearsRemain(employeeId, date).getRemainingDays();
	}
	
	/**
	 * @name 18.代休残数表示
	 * @param employeeId 社員ID
	 * @param datePeriod 基準日
	 * @return
	 */
	public Double remainingNumberOfSubstituteHolidays(String employeeId, GeneralDate date) {
		//RQ#505　BreakDayOffMngInPeriodQuery.getBreakDayOffMngRemain
		return BreakDayOffMngInPeriodQuery.getBreakDayOffMngRemain(
				requireService.createRequire(), new CacheCarrier(), employeeId, date);
	}
	
	/**
	 * @name 19.振休残数表示
	 * @param employeeId 社員ID
	 * @param datePeriod 基準日
	 * @return
	 */
	public Double vibrationResidualNumberRepresentation(String employeeId, GeneralDate date) {
		//RQ#506 AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain
		return AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain(
				requireService.createRequire(), new CacheCarrier(), employeeId, date).getRemainDays();
	}
	
	/**
	 * @name 21.子の看護休暇残数表示
	 * @param employeeId 社員ID
	 * @param datePeriod 基準日
	 * @return
	 */
	public Double remainingNumberOfChildNursingLeave(String cid, String employeeId, DatePeriod datePeriod) {
		//※「期間内の子看護残を集計する」はRequestList206
		return shNursingLeaveSettingPub.aggrChildNursingRemainPeriod(cid, employeeId, datePeriod, NursingMode.Other).getPreGrantStatement().getResidual();
	}
	
	/**
	 * @name 22.介護休暇残数表示
	 * @param employeeId 社員ID
	 * @param datePeriod 基準日
	 * @return
	 */
	public Double remainingNumberOfNursingLeave(String cid, String employeeId, DatePeriod datePeriod) {
		//※「期間内の介護残を集計する」はRequestList207
		return shNursingLeaveSettingPub.aggrNursingRemainPeriod(cid, employeeId, datePeriod.start(), datePeriod.end(), NursingMode.Other).getPreGrantStatement().getResidual();
	}
	
	/**
	 * @name 23.特休残数表示
	 * @param employeeId 社員ID
	 * @param datePeriod 基準日
	 * @return
	 */
	public List<SpecialHolidaysRemainingDto> remnantRepresentation(String cid, String employeeId, DatePeriod datePeriod) {
		
		List<SpecialHolidaysRemainingDto> result = new ArrayList<>();
		
		List<SpecialHoliday> specialHolidays = specialHolidayRepository.findByCompanyId(cid);
		for (SpecialHoliday specialHoliday : specialHolidays) {
			//get request list 208 rồi trả về
			//・上書きフラグ ← falseを渡してください(muto)
			//・上書き用の暫定管理データ ← 空（null or Empty）で渡してください
			ComplileInPeriodOfSpecialLeaveParam param = new ComplileInPeriodOfSpecialLeaveParam(cid, employeeId,
					new DatePeriod(datePeriod.start(),datePeriod.end()),
					false,
					GeneralDate.today(),
					specialHoliday.getSpecialHolidayCode().v(),
					false, false,
					new ArrayList<>(), new ArrayList<>(), Optional.empty());
			InPeriodOfSpecialLeave inPeriodOfSpecialLeave = SpecialLeaveManagementService
					.complileInPeriodOfSpecialLeave(requireService.createRequire(), new CacheCarrier(), param)
					.getAggSpecialLeaveResult();
			
			result.add(new SpecialHolidaysRemainingDto(
					new RemainingDaysAndTimeDto(inPeriodOfSpecialLeave.getRemainDays().getGrantDetailBefore().getRemainDays(), new AttendanceTime(0)),
					specialHoliday.getSpecialHolidayCode().v(), 
					specialHoliday.getSpecialHolidayName().v()));				
		}
		return result;
	}
}
