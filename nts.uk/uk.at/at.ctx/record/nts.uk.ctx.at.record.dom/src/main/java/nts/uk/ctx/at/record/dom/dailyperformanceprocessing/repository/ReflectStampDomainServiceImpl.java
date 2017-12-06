package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectRangeOutput;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTimeCode;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTypeCode;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

@Stateless
public class ReflectStampDomainServiceImpl implements ReflectStampDomainService {

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Override
	public void reflectStampInfo(String companyID, String employeeID, GeneralDate processingDate, WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {

		WorkTypeCode workTypeCode = workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTypeCode();

		WorkTimeCode workTimeCode = workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTimeCode();

		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = basicScheduleService.checkWorkDay(workTypeCode.v());

		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();
		// 終了状態：休日扱い
		// 休日系の打刻範囲を取得する
		if (workStyle == WorkStyle.ONE_DAY_REST) {
			this.HolidayStampRange(workTimeCode, workTypeCode, companyID, workInfoOfDailyPerformance);
		}
		// 終了状態：出勤扱い
		// 出勤系の打刻範囲を取得する
		else {
			stampReflectRangeOutput = this.RangeOfStampOneDay(workTimeCode, companyID,
					workInfoOfDailyPerformance);
		}
		
		stampReflectRangeOutput.getGoOut();

	}

	/*
	 * 1日分の打刻反映範囲を取得
	 */
	private StampReflectRangeOutput RangeOfStampOneDay(WorkTimeCode workTimeCode, String companyID,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {

		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();


		// ドメインモデル「就業時間帯の設定」を取得
		Optional<WorkTimeSetting> workTimeSetting = workTimeSettingRepository.findByCode(companyID, workTimeCode.v());

		if (workTimeSetting.isPresent()) {
			// 打刻反映時間帯を取得する - TODO
			// this step is common of domain from New Wave's team
			// fake data
			List<StampReflectTimezone> stampReflectTimezones = new ArrayList<>();
			if (!stampReflectTimezones.isEmpty()) {
				stampReflectRangeOutput.setLstStampReflectTimezone(stampReflectTimezones);
				// new list for copy data
				List<StampReflectTimezone> stampReflectTimezoneList = new ArrayList<>();
				stampReflectTimezones.forEach(stamp -> {
//					StampReflectTimezone stampReflectTimezone = new StampReflectTimezone();
					// TODO - not setter? - add setter or create new class
					
					
					stampReflectRangeOutput.setLstStampReflectTimezone(stampReflectTimezoneList);
				});
			}
		}

		return stampReflectRangeOutput;
	}
	
	/*
	 * 休日系の打刻範囲を取得する
	 */
	private StampReflectRangeOutput HolidayStampRange(WorkTimeCode workTimeCode, WorkTypeCode workTypeCode,String companyID,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance){
		
		if (workTimeCode.equals(null)) {
			// use workTypeCode
			
		}
		
		StampReflectRangeOutput stampReflectRangeOutput = this.RangeOfStampOneDay(workTimeCode, companyID, workInfoOfDailyPerformance);
		
		// 前々日の打刻反映範囲を取得
		this.TwoDayBeforeStamp(workInfoOfDailyPerformance);
		
		
		return stampReflectRangeOutput;
	}
	
	/*
	 * 前々日の打刻反映範囲を取得
	 */
	private int TwoDayBeforeStamp(WorkInfoOfDailyPerformance workInfoOfDailyPerformance){
		
		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = basicScheduleService.checkWorkDay(workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTypeCode().v());
		
		if (workStyle != WorkStyle.ONE_DAY_REST) {
			// 前々日の就業時間帯コードを取得
			
		}
		
		return 0;
	}
	
//	public 

}
