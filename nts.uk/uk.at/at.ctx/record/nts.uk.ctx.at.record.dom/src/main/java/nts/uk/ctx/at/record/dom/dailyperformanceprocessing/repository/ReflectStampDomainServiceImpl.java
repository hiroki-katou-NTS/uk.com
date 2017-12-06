package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectRangeOutput;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
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
	public void reflectStampInfo(String companyID, String employeeID, GeneralDate processingDate) {

		Optional<WorkInfoOfDailyPerformance> workInfoOfDailyPerformance = workInformationRepository.find(employeeID,
				processingDate);

		WorkTypeCode workTypeCode = workInfoOfDailyPerformance.get().getRecordWorkInformation().getWorkTypeCode();

		WorkTimeCode workTimeCode = workInfoOfDailyPerformance.get().getRecordWorkInformation().getWorkTimeCode();

		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = basicScheduleService.checkWorkDay(workTypeCode.v());

		// 終了状態：休日扱い
		if (workStyle == WorkStyle.ONE_DAY_REST) {

		}
		// 終了状態：出勤扱い
		else {
			this.RangeOfStampOneDay(workTimeCode, companyID,
					workInfoOfDailyPerformance.get().getScheduleWorkInformation());
		}

	}

	/*
	 * 1日分の打刻反映範囲を取得
	 */
	private StampReflectRangeOutput RangeOfStampOneDay(WorkTimeCode workTimeCode, String companyID,
			WorkInformation scheduleWorkInformation) {

		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();

		Optional<WorkTimeSetting> workTimeSetting = workTimeSettingRepository.findByCode(companyID, workTimeCode.v());

		if (workTimeSetting.isPresent()) {
			// 打刻反映時間帯を取得する - TODO
			// this step is common of domain from New Wave's team
			// fake data
			List<StampReflectTimezone> stampReflectTimezones = new ArrayList<>();
			if (!stampReflectTimezones.isEmpty()) {
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

}
