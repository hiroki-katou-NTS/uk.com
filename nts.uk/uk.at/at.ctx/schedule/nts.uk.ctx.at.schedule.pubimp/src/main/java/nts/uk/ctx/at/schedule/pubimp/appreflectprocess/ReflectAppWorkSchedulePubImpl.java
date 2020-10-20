package nts.uk.ctx.at.schedule.pubimp.appreflectprocess;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.RequestSettingAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.SCAppReflectionSetting;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.ReflectApplicationWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ReflectApplicationWorkSchedulePub;
import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHAppReflectionSetting;
import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHApplyTimeSchedulePriority;
import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHClassifyScheAchieveAtr;
import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHPriorityTimeReflectAtr;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.ApplicationReflectHistory;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.ReflectGoBackDirectly;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.lateleaveearly.ReflectArrivedLateLeaveEarly;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectWorkChangeApplication;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateDailyRecordServiceCenterNew;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.CorrectionAttendanceRule;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ReflectAppWorkSchedulePubImpl implements ReflectApplicationWorkSchedulePub {

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private BasicScheduleService service;

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private WorkScheduleRepository workScheduleRepository;

	@Inject
	private DailyRecordConverter convertDailyRecordToAd;

	@Inject
	private CorrectionAttendanceRule correctionAttendanceRule;

	@Inject
	private CalculateDailyRecordServiceCenterNew calculateDailyRecordServiceCenterNew;

	@Inject
	private RequestSettingAdapter requestSettingAdapter;

	@Override
	public Pair<Object, AtomTask> process(Object application, GeneralDate date, Object reflectStatus) {
		String companyId = AppContexts.user().companyId();
		RequireImpl impl = new RequireImpl(companyId, workTypeRepo, workTimeSettingRepository, service,
				workTimeSettingService, basicScheduleService, workScheduleRepository, convertDailyRecordToAd,
				correctionAttendanceRule, calculateDailyRecordServiceCenterNew, requestSettingAdapter);
		Pair<ReflectStatusResultShare, AtomTask> result = ReflectApplicationWorkSchedule.process(impl, companyId, 
				(ApplicationShare) application, date, (ReflectStatusResultShare) reflectStatus);
		return Pair.of(result.getLeft(), result.getRight());
	}

	@AllArgsConstructor
	public class RequireImpl implements ReflectApplicationWorkSchedule.Require {

		private final String companyId;

		private final WorkTypeRepository workTypeRepo;

		private final WorkTimeSettingRepository workTimeSettingRepository;

		private final BasicScheduleService service;

		private final WorkTimeSettingService workTimeSettingService;

		private final BasicScheduleService basicScheduleService;

		private final WorkScheduleRepository workScheduleRepository;

		private final DailyRecordConverter convertDailyRecordToAd;

		private final CorrectionAttendanceRule correctionAttendanceRule;

		private final CalculateDailyRecordServiceCenterNew calculateDailyRecordServiceCenterNew;

		private final RequestSettingAdapter requestSettingAdapter;

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return service.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd,
				Integer workNo) {
			return workTimeSettingService.getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}

		@Override
		public void insertAppReflectHist(ApplicationReflectHistory hist) {
			// TODO Auto-generated method stub

		}

		@Override
		public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
			return workScheduleRepository.get(employeeID, ymd);
		}

		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter() {
			return convertDailyRecordToAd.createDailyConverter();
		}

		@Override
		public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {
			return correctionAttendanceRule.process(domainDaily, changeAtt);
		}

		@Override
		public void insertSchedule(WorkSchedule workSchedule) {
			workScheduleRepository.insert(workSchedule);
		}

		@Override
		public List<IntegrationOfDaily> calculateForSchedule(CalculateOption calcOption,
				List<IntegrationOfDaily> integrationOfDaily) {
			return calculateDailyRecordServiceCenterNew.calculateForSchedule(calcOption, integrationOfDaily);
		}

		@Override
		public Optional<ReflectWorkChangeApplication> findReflectWorkCg(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<SCAppReflectionSetting> getAppReflectionSettingSc(String companyId,
				ApplicationTypeShare appType) {
			return requestSettingAdapter.getAppReflectionSetting(companyId, appType);
		}

		@Override
		public Optional<SHAppReflectionSetting> getAppReflectionSetting(String companyId,
				ApplicationTypeShare appType) {
			return requestSettingAdapter.getAppReflectionSetting(companyId, appType)
					.map(x -> new SHAppReflectionSetting(x.getScheReflectFlg(),
							EnumAdaptor.valueOf(x.getPriorityTimeReflectFlag().value, SHPriorityTimeReflectAtr.class),
							x.getAttendentTimeReflectFlg(),
							EnumAdaptor.valueOf(x.getClassScheAchi().value, SHClassifyScheAchieveAtr.class),
							EnumAdaptor.valueOf(x.getReflecTimeofSche().value, SHApplyTimeSchedulePriority.class)));
		}

		@Override
		public Optional<ReflectGoBackDirectly> findReflectGoBack(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<ReflectAppStamp> findReflectAppStamp(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<ReflectArrivedLateLeaveEarly> findReflectArrivedLateLeaveEarly(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<ReflectBusinessTripApp> findReflectBusinessTripApp(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
