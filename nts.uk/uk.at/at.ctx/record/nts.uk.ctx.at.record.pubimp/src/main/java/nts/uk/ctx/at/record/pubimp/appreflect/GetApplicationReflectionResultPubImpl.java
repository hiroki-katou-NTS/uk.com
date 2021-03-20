package nts.uk.ctx.at.record.pubimp.appreflect;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.application.reflect.RCRequestSettingAdapter;
import nts.uk.ctx.at.record.dom.applicationcancel.getreflect.GetApplicationReflectionResult;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.pub.appreflect.GetApplicationReflectionResultPub;
import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHAppReflectionSetting;
import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHApplyTimeSchedulePriority;
import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHClassifyScheAchieveAtr;
import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHPriorityTimeReflectAtr;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.empwork.EmployeeWorkDataSetting;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt.CorrectionAfterTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.GoBackReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeAppRepository;
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
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class GetApplicationReflectionResultPubImpl implements GetApplicationReflectionResultPub {

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;

	@Inject
	private CalculateDailyRecordServiceCenter calculateDailyRecordServiceCenter;

	@Inject
	private RCRequestSettingAdapter requestSettingAdapter;

	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;

	@Inject
	private GoBackReflectRepository goBackReflectRepository;

	@Inject
	private StampAppReflectRepository stampAppReflectRepository;

	@Inject
	private LateEarlyCancelReflectRepository lateEarlyCancelReflectRepository;

	@Inject
	private ReflectWorkChangeAppRepository reflectWorkChangeAppRepository;

	@Inject
	private CorrectionAfterTimeChange correctionAfterTimeChange;
	
	@Inject
	private TimeLeaveAppReflectRepository timeLeaveAppReflectRepository;

	@Override
	public Optional<IntegrationOfDaily> getApp(String companyId, Object application, GeneralDate baseDate,
			Optional<IntegrationOfDaily> dailyData) {
		RequireImpl impl = new RequireImpl(companyId, workTypeRepo, workTimeSettingRepository, workTimeSettingService,
				basicScheduleService, dailyRecordShareFinder, calculateDailyRecordServiceCenter, requestSettingAdapter,
				flexWorkSettingRepository, predetemineTimeSettingRepository, fixedWorkSettingRepository,
				flowWorkSettingRepository, goBackReflectRepository, stampAppReflectRepository,
				lateEarlyCancelReflectRepository, reflectWorkChangeAppRepository, correctionAfterTimeChange,
				timeLeaveAppReflectRepository);
		return GetApplicationReflectionResult.getApp(impl, companyId, (ApplicationShare) application, baseDate,
				dailyData);
	}

	@AllArgsConstructor
	public class RequireImpl implements GetApplicationReflectionResult.Require {

		private final String companyId;

		private final WorkTypeRepository workTypeRepo;

		private final WorkTimeSettingRepository workTimeSettingRepository;

		private final WorkTimeSettingService workTimeSettingService;

		private final BasicScheduleService basicScheduleService;

		private final DailyRecordShareFinder dailyRecordShareFinder;

		private final CalculateDailyRecordServiceCenter calculateDailyRecordServiceCenter;

		private final RCRequestSettingAdapter requestSettingAdapter;

		private final FlexWorkSettingRepository flexWorkSettingRepository;

		private final PredetemineTimeSettingRepository predetemineTimeSettingRepository;

		private final FixedWorkSettingRepository fixedWorkSettingRepository;

		private final FlowWorkSettingRepository flowWorkSettingRepository;

		private final GoBackReflectRepository goBackReflectRepository;

		private final StampAppReflectRepository stampAppReflectRepository;

		private final LateEarlyCancelReflectRepository lateEarlyCancelReflectRepository;

		private final ReflectWorkChangeAppRepository reflectWorkChangeAppRepository;

		private final CorrectionAfterTimeChange correctionAfterTimeChange;
		
		private TimeLeaveAppReflectRepository timeLeaveAppReflectRepository;

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

//		@Override
//		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd,
//				Integer workNo) {
//			return workTimeSettingService.getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
//		}

		@Override
		public Optional<EmployeeWorkDataSetting> getEmpWorkDataSetting(String employeeId) {
			// TODO: data
			return Optional.empty();
		}

		@Override
		public Optional<IntegrationOfDaily> findDaily(String employeeId, GeneralDate date) {
			return dailyRecordShareFinder.find(employeeId, date);
		}

		@Override
		public Optional<SHAppReflectionSetting> getAppReflectionSetting(String companyId,
				ApplicationTypeShare appType) {
			return requestSettingAdapter.getAppReflectionSetting(companyId)
					.map(x -> new SHAppReflectionSetting(x.getScheReflectFlg(),
							EnumAdaptor.valueOf(x.getPriorityTimeReflectFlag().value, SHPriorityTimeReflectAtr.class),
							x.getAttendentTimeReflectFlg(),
							EnumAdaptor.valueOf(x.getClassScheAchi().value, SHClassifyScheAchieveAtr.class),
							EnumAdaptor.valueOf(x.getReflecTimeofSche().value, SHApplyTimeSchedulePriority.class)));
		}

		@Override
		public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<ReflectBusinessTripApp> findReflectBusinessTripApp(String companyId) {
			return Optional.of(new ReflectBusinessTripApp(companyId));
		}

		@Override
		public Optional<ReflectWorkChangeApp> findReflectWorkCg(String companyId) {
			return reflectWorkChangeAppRepository.findByCompanyIdReflect(companyId);
		}

		@Override
		public Optional<GoBackReflect> findReflectGoBack(String companyId) {
			return goBackReflectRepository.findByCompany(companyId);
		}

		@Override
		public Optional<StampAppReflect> findReflectAppStamp(String companyId) {
			return stampAppReflectRepository.findReflectByCompanyId(companyId);
		}

		@Override
		public Optional<LateEarlyCancelReflect> findReflectArrivedLateLeaveEarly(String companyId) {
			return Optional.ofNullable(lateEarlyCancelReflectRepository.getByCompanyId(companyId));
		}

		@Override
		public String getCId() {
			return companyId;
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			return fixedWorkSettingRepository.findByKey(companyId, code.v()).get();
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			return flowWorkSettingRepository.find(companyId, code.v()).get();
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			return flexWorkSettingRepository.find(companyId, code.v()).get();
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, wktmCd.v()).get();
		}

		@Override
		public IntegrationOfDaily correction(String companyId, IntegrationOfDaily domainDaily) {
			return correctionAfterTimeChange.corection(companyId, domainDaily, ScheduleRecordClassifi.RECORD);
		}

		@Override
		public List<IntegrationOfDaily> calculateForRecord(CalculateOption calcOption,
				List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet,
				ExecutionType reCalcAtr) {
			return calculateDailyRecordServiceCenter.calculatePassCompanySetting(calcOption, integrationOfDaily,
					companySet, reCalcAtr);
		}

		@Override
		public Optional<TimeLeaveApplicationReflect> findReflectTimeLeav(String companyId) {
			return timeLeaveAppReflectRepository.findByCompany(companyId);
		}

	}

}
