package nts.uk.ctx.at.schedule.dom.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ScheRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.createremain.subtransfer.SubsTransferProcessMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByScheData;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.CreateWorkRecordScheduleRemain;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateDailyRecordServiceCenterNew;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 暫定データを作成する為の勤務予定を取得する
 */
@Stateless
public class RemainCreateInforByScheDataImpl implements RemainCreateInforByScheData {

	@Inject
	private WorkScheduleRepository workScheRepo;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
	@Inject
	private DailyRecordConverter dailyRecordConverter;
	@Inject
	private CalculateDailyRecordServiceCenterNew calculateDailyRecordServiceCenter;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;
	@Inject
	private WorkTypeRepository workTypeRepo;

	@Override
	public List<ScheRemainCreateInfor> createRemainInforNew(String cid, String sid, List<GeneralDate> dates) {

		DatePeriod period = new DatePeriod(dates.stream().min(Comparator.comparing(GeneralDate::date)).get(),
				dates.stream().max(Comparator.comparing(GeneralDate::date)).get());
		// 勤務予定を取得する
		List<WorkSchedule> sches = this.workScheRepo.getListBySid(sid, period);
		// (Imported)「残数作成元の勤務予定を取得する」
		// 残数作成元情報を返す
		// 勤務予定から日別勤怠(Work)を作成する
		RequireImpl impl = new RequireImpl(cid);
		return CreateWorkRecordScheduleRemain
				.createRemain(impl, cid, sches.stream().map(x -> toDailyDomain(x)).collect(Collectors.toList()),
						SubsTransferProcessMode.DAILY)
				.stream().map(x -> ScheRemainCreateInfor.toScheRemain(x)).collect(Collectors.toList());
	}

	// 勤務予定から日別勤怠(Work)を作成する
	private IntegrationOfDaily toDailyDomain(WorkSchedule workSchedule) {
		return new IntegrationOfDaily(workSchedule.getEmployeeID(), workSchedule.getYmd(), workSchedule.getWorkInfo(),
				CalAttrOfDailyAttd.createAllCalculate(), workSchedule.getAffInfo(), Optional.empty(), new ArrayList<>(),
				workSchedule.getOutingTime(), workSchedule.getLstBreakTime(), workSchedule.getOptAttendanceTime(),
				workSchedule.getOptTimeLeaving(), workSchedule.getOptSortTimeWork(), Optional.empty(), Optional.empty(),
				Optional.empty(), workSchedule.getLstEditState(), Optional.empty(), new ArrayList<>(),
				Optional.empty());
	}

	@AllArgsConstructor
	public class RequireImpl implements CreateWorkRecordScheduleRemain.Require {
		String companyId;

		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, workTimeCode.v());
		}

		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter() {
			return dailyRecordConverter.createDailyConverter();
		}

		@Override
		public List<IntegrationOfDaily> calculateForRecord(CalculateOption calcOption,
				List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet,
				ExecutionType reCalcAtr) {
			return calculateDailyRecordServiceCenter.calculatePassCompanySetting(calcOption, integrationOfDaily, reCalcAtr);
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}

		@Override
		public Optional<CompensatoryLeaveComSetting> compensatoryLeaveComSetting(String companyId) {
			return Optional.ofNullable(compensLeaveComSetRepository.find(companyId));
		}

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSettingRepository.findByKey(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSettingRepository.find(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSettingRepository.find(companyId, workTimeCode.v());
		}

		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}

	}
}
