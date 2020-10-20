package nts.uk.ctx.at.record.pubimp.appreflect;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.ConvertDailyRecordToAd;
import nts.uk.ctx.at.record.dom.adapter.application.reflect.RequestSettingAdapter;
import nts.uk.ctx.at.record.dom.applicationcancel.ReflectApplicationWorkRecord;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectleavingwork.CheckRangeReflectLeavingWork;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.CheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.OutputCheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.TemporarilyReflectStampDailyAttd;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.pub.appreflect.ReflectApplicationWorkRecordPub;
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
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.empwork.EmployeeWorkDataSetting;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ICorrectionAttendanceRule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeReflectFromWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ReflectApplicationWorkRecordPubImpl implements ReflectApplicationWorkRecordPub {

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private ICorrectionAttendanceRule correctionAttendanceRule;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private TimeReflectFromWorkinfo timeReflectFromWorkinfo;

	@Inject
	private CheckRangeReflectAttd checkRangeReflectAttd;

	@Inject
	private CheckRangeReflectLeavingWork checkRangeReflectLeavingWork;

	@Inject
	private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;

	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;

	@Inject
	private ConvertDailyRecordToAd convertDailyRecordToAd;

	@Inject
	private CalculateDailyRecordServiceCenter calculateDailyRecordServiceCenter;

	@Inject
	private DailyRecordAdUpService dailyRecordAdUpService;

	@Inject
	private RequestSettingAdapter requestSettingAdapter;

	@Override
	public Pair<ReflectStatusResultShare, Optional<AtomTask>> process(Object application, GeneralDate date,
			ReflectStatusResultShare reflectStatus) {
		RequireImpl impl = new RequireImpl(AppContexts.user().companyId(), AppContexts.user().contractCode(),
				stampCardRepository, correctionAttendanceRule, workTypeRepo, workTimeSettingRepository,
				workTimeSettingService, basicScheduleService, timeReflectFromWorkinfo, checkRangeReflectAttd,
				checkRangeReflectLeavingWork, temporarilyReflectStampDailyAttd, dailyRecordShareFinder,
				convertDailyRecordToAd, calculateDailyRecordServiceCenter, dailyRecordAdUpService,
				requestSettingAdapter);
		return ReflectApplicationWorkRecord.process(impl, (ApplicationShare) application, date, reflectStatus);
	}

	@AllArgsConstructor
	public class RequireImpl implements ReflectApplicationWorkRecord.Require {

		private final String companyId;

		private final String contractCode;

		private final StampCardRepository stampCardRepository;

		private final ICorrectionAttendanceRule correctionAttendanceRule;

		private final WorkTypeRepository workTypeRepo;

		private final WorkTimeSettingRepository workTimeSettingRepository;

		private final WorkTimeSettingService workTimeSettingService;

		private final BasicScheduleService basicScheduleService;

		private final TimeReflectFromWorkinfo timeReflectFromWorkinfo;

		private final CheckRangeReflectAttd checkRangeReflectAttd;

		private final CheckRangeReflectLeavingWork checkRangeReflectLeavingWork;

		private final TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;

		private final DailyRecordShareFinder dailyRecordShareFinder;

		private final ConvertDailyRecordToAd convertDailyRecordToAd;

		private final CalculateDailyRecordServiceCenter calculateDailyRecordServiceCenter;

		private final DailyRecordAdUpService dailyRecordAdUpService;

		private final RequestSettingAdapter requestSettingAdapter;

		@Override
		public List<StampCard> getLstStampCardBySidAndContractCd(String sid) {
			return stampCardRepository.getLstStampCardBySidAndContractCd(contractCode, sid);
		}

		@Override
		public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {
			return correctionAttendanceRule.process(domainDaily, changeAtt);
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
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
		public OutputTimeReflectForWorkinfo getTimeReflect(String employeeId, GeneralDate ymd,
				WorkInfoOfDailyAttendance workInformation) {
			return timeReflectFromWorkinfo.get(companyId, employeeId, ymd, workInformation);
		}

		@Override
		public OutputCheckRangeReflectAttd checkRangeReflectAttd(Stamp stamp,
				StampReflectRangeOutput stampReflectRangeOutput, IntegrationOfDaily integrationOfDaily) {
			return checkRangeReflectAttd.checkRangeReflectAttd(stamp, stampReflectRangeOutput, integrationOfDaily);
		}

		@Override
		public OutputCheckRangeReflectAttd checkRangeReflectOut(Stamp stamp, StampReflectRangeOutput s,
				IntegrationOfDaily integrationOfDaily) {
			return checkRangeReflectLeavingWork.checkRangeReflectAttd(stamp, s, integrationOfDaily);
		}

		@Override
		public List<ErrorMessageInfo> reflectStamp(Stamp stamp, StampReflectRangeOutput stampReflectRangeOutput,
				IntegrationOfDaily integrationOfDaily) {
			return temporarilyReflectStampDailyAttd.reflectStamp(stamp, stampReflectRangeOutput, integrationOfDaily);
		}

		@Override
		public Optional<EmployeeWorkDataSetting> getEmpWorkDataSetting(String employeeId) {
			// TODO: Auto-generated method stub
			return null;
		}

		@Override
		public IntegrationOfDaily findDaily(String employeeId, GeneralDate date) {
			return dailyRecordShareFinder.find(employeeId, date);
		}

		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter() {
			return convertDailyRecordToAd.createDailyConverter();
		}

		@Override
		public List<IntegrationOfDaily> calculateForSchedule(CalculateOption calcOption,
				List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet) {
			return calculateDailyRecordServiceCenter.calculateForSchedule(calcOption, integrationOfDaily, companySet);
		}

		@Override
		public void addAllDomain(IntegrationOfDaily domain) {
			dailyRecordAdUpService.addAllDomain(domain);
		}

		@Override
		public Optional<ReflectWorkChangeApplication> findReflectWorkCg(String companyId) {
			// TODO Auto-generated method stub
			return null;
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

		@Override
		public String getCId() {
			return companyId;
		}

	}
}
