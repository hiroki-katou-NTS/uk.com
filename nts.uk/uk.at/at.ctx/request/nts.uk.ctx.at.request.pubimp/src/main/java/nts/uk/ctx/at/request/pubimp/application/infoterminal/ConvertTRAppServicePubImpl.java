package nts.uk.ctx.at.request.pubimp.application.infoterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.common.adapter.employeemanage.EmployeeManageRQAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.infoterminal.EmpInfoTerminalAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.infoterminal.TimeRecordReqSettingAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.stamp.StampCardAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.StampCard;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTerRQ;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPgAlTrAdapter;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppLateReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppStampReceptionData.AppStampBuilder;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkChangeReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.service.ConvertTimeRecordApplicationService;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImageRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppLateReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppStampReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppWorkChangeReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.ApplicationReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.ConvertTRAppServicePub;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class ConvertTRAppServicePubImpl implements ConvertTRAppServicePub {

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private AppRecordImageRepository appRecordImageRepository;

	@Inject
	private AppWorkChangeRepository appWorkChangeRepository;

	@Inject
	private ArrivedLateLeaveEarlyRepository arrivedLateLeaveEarlyRepository;

	@Inject
	private CommonAlgorithm commonAlgorithm;

	@Inject
	private ApplicationApprovalService applicationApprovalService;

	@Inject
	private EmpInfoTerminalAdapter empInfoTerminalAdapter;

	@Inject
	private TimeRecordReqSettingAdapter timeRecordReqSettingAdapter;

	@Inject
	private StampCardAdapter stampCardAdapter;
	
	@Inject
	private TopPgAlTrAdapter topPgAlTrAdapter;

	@Inject
	private EmployeeManageRQAdapter employeeManageRQAdapter;

	@Override
	public <T extends ApplicationReceptionDataExport> Optional<AtomTask> converData(Integer empInfoTerCode,
			String contractCode, T recept) {
		RequireImpl impl = new RequireImpl(workTypeRepository, workingConditionItemRepository, applicationRepository,
				appRecordImageRepository, appWorkChangeRepository, arrivedLateLeaveEarlyRepository, commonAlgorithm,
				applicationApprovalService, empInfoTerminalAdapter, timeRecordReqSettingAdapter, stampCardAdapter,
				topPgAlTrAdapter, employeeManageRQAdapter);

		return ConvertTimeRecordApplicationService.converData(impl, empInfoTerCode, contractCode, covertTo(recept));
	}

	public ApplicationReceptionData covertTo(ApplicationReceptionDataExport data) {

		ApplicationReceptionData appDom = new ApplicationReceptionData(data.getIdNumber(),
				data.getApplicationCategory(), data.getYmd(), data.getTime());
		ApplicationCategoryPub cate = ApplicationCategoryPub.valueStringOf(data.getApplicationCategory());
		switch (cate) {

		// 打刻申請
		case STAMP:
			AppStampReceptionDataExport appStampData = (AppStampReceptionDataExport) data;
			return new AppStampBuilder(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(), data.getTime(),
					appStampData.getGoOutCategory(), appStampData.getTypeBeforeAfter())
							.appTime(appStampData.getAppTime()).appYMD(appStampData.getAppYMD())
							.stampType(appStampData.getStampType()).reason(appStampData.getReason()).build();

		// 残業申請
		case OVERTIME:
			// AppOverTime
			return null;

		// 休暇申請
		case VACATION:
			// AppAbsence
			return null;

		// 勤務変更申請
		case WORK_CHANGE:
			AppWorkChangeReceptionDataExport appWorkData = (AppWorkChangeReceptionDataExport) data;
			return new AppWorkChangeReceptionData(appDom, appWorkData.getTypeBeforeAfter(), appWorkData.getStartDate(),
					appWorkData.getEndDate(), appWorkData.getWorkTime(), appWorkData.getReason());

		// 休日出勤時間申請
		case WORK_HOLIDAY:
			return null;

		// 遅刻早退取消申請
		case LATE:
			AppLateReceptionDataExport appLateData = (AppLateReceptionDataExport) data;
			return new AppLateReceptionData(appDom, appLateData.getTypeBeforeAfter(), appLateData.getAppYMD(),
					appLateData.getReasonLeave(), appLateData.getReason());

		// 時間年休申請
		case ANNUAL:
			// TODO: chua co domain
			return null;

		default:
			return null;
		}
	}

	@AllArgsConstructor
	public class RequireImpl implements ConvertTimeRecordApplicationService.Require {

		private final WorkTypeRepository workTypeRepository;

		private final WorkingConditionItemRepository workingConditionItemRepository;

		private final ApplicationRepository applicationRepository;

		private final AppRecordImageRepository appRecordImageRepository;

		private final AppWorkChangeRepository appWorkChangeRepository;

		private final ArrivedLateLeaveEarlyRepository arrivedLateLeaveEarlyRepository;

		private final CommonAlgorithm commonAlgorithm;

		private final ApplicationApprovalService applicationApprovalService;

		private final EmpInfoTerminalAdapter empInfoTerminalAdapter;

		private final TimeRecordReqSettingAdapter timeRecordReqSettingAdapter;

		private final StampCardAdapter stampCardAdapter;

		private final TopPgAlTrAdapter topPgAlTrAdapter;

		private final EmployeeManageRQAdapter employeeManageRQAdapter;

		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(Integer empInfoTerCode, String contractCode) {
			return empInfoTerminalAdapter.getEmpInfoTerminal(empInfoTerCode, contractCode);
		}

		@Override
		public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
			return workTypeRepository.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
			return workingConditionItemRepository.getBySidAndStandardDate(employeeId, baseDate);
		}

		@Override
		public List<Application> getApplication(PrePostAtr prePostAtr, GeneralDateTime inputDate, GeneralDate appDate,
				ApplicationType appType, String employeeID) {
			return this.applicationRepository.getApplication(prePostAtr, inputDate, appDate, appType, employeeID);
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber) {
			return stampCardAdapter.getByCardNoAndContractCode(contractCode, stampNumber);
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(Integer empInfoTerCode, String contractCode) {
			return timeRecordReqSettingAdapter.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

		@Override
		public void insert(AppRecordImage appStamp) {
			this.appRecordImageRepository.addStamp(appStamp);
		}

		@Override
		public void insert(AppOverTime appOverTime) {
			// TODO Auto-generated method stub

		}

		@Override
		public void insert(AppAbsence appAbsence) {
			// TODO Auto-generated method stub

		}

		@Override
		public void insert(AppWorkChange appWorkChange) {
			this.appWorkChangeRepository.add(appWorkChange);
		}

		@Override
		public void insert(AppHolidayWork appHolidayWork) {
			// TODO Auto-generated method stub

		}

		@Override
		public void insert(String cid, ArrivedLateLeaveEarly lateOrLeaveEarly) {
			this.arrivedLateLeaveEarlyRepository.registerLateLeaveEarly(cid, lateOrLeaveEarly.getApplication(),
					new ArrivedLateLeaveEarlyInfoOutput(new ArrayList<>(), null, null, Optional.empty(),
							Optional.of(lateOrLeaveEarly)));

		}

		@Override
		public void insert() {
			// TODO: 時間年休申請を作る

		}

		@Override
		public ApprovalRootContentImport_New getApprovalRoot(String cid, String employeeID, EmploymentRootAtr rootAtr,
				ApplicationType appType, GeneralDate appDate) {
			return commonAlgorithm.getApprovalRoot(cid, employeeID, rootAtr, appType, appDate);
		}

		@Override
		public void insertApp(Application application, List<ApprovalPhaseStateImport_New> listApprovalPhaseState) {
			applicationApprovalService.insertApp(application, listApprovalPhaseState);
		}

		@Override
		public void insertLogAll(TopPageAlarmEmpInfoTerRQ alEmpInfo) {
			topPgAlTrAdapter.insertLogAll(alEmpInfo);
		}

		@Override
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
			return employeeManageRQAdapter.getListEmpID(companyID, referenceDate);
		}

	}
}
