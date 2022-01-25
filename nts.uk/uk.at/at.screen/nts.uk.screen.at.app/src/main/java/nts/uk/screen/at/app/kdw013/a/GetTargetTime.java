package nts.uk.screen.at.app.kdw013.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourinput.EncouragedTargetApplication;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourinput.OvertimeLeaveEncourageConfirmationService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.CheckShortageFlex;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service.JudgingApplication;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.残業申請・休出時間申請の対象時間を取得する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetTargetTime {
	@Inject
	private WorkInformationRepository workInformationRepository;
	@Inject
	private OuenWorkTimeSheetOfDailyRepo ouenWorkTimeSheetRepo;
	@Inject
	private CheckShortageFlex checkShortageFlex;

	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	@Inject
	private CommonAlgorithm commonAlgorithm;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private ApplicationRepository applicationRepository;

	/**
	 * 
	 * @param sid
	 *            対象社員
	 * @param inputDates
	 *            対象日リスト
	 * @return List<残業休出時間>
	 */
	public List<EncouragedTargetApplication> get(String sId, List<GeneralDate> inputDates) {
		return OvertimeLeaveEncourageConfirmationService
				.check(new OvertimeLeaveEncourageConfirmationServiceRequire1Impl(workInformationRepository,
						ouenWorkTimeSheetRepo, checkShortageFlex, applicationSettingRepository, commonAlgorithm,
						workTypeRepository, applicationRepository), AppContexts.user().companyId(), sId, inputDates);
	}

	@AllArgsConstructor
	private class OvertimeLeaveEncourageConfirmationServiceRequire1Impl
			implements OvertimeLeaveEncourageConfirmationService.Require1 {

		private WorkInformationRepository workInformationRepository;

		private OuenWorkTimeSheetOfDailyRepo ouenWorkTimeSheetRepo;

		private CheckShortageFlex checkShortageFlex;

		private ApplicationSettingRepository applicationSettingRepository;

		private CommonAlgorithm commonAlgorithm;

		private WorkTypeRepository workTypeRepository;

		private ApplicationRepository applicationRepository;

		@Override
		public DatePeriod getPeriod(String employeeId, GeneralDate date) {
			return checkShortageFlex.findClosurePeriod(employeeId, date);
		}

		@Override
		public List<WorkInfoOfDailyPerformance> findByListDate(String employeeId, List<GeneralDate> dates) {
			return this.workInformationRepository.findByListDate(employeeId, dates);
		}

		@Override
		public List<OuenWorkTimeSheetOfDaily> get(String sId, List<GeneralDate> dates) {
			return this.ouenWorkTimeSheetRepo.get(sId, dates);
		}

		@Override
		public Optional<ApplicationTypeShare> toDecide(String cId, String sId, GeneralDate date,
				WorkTypeCode workTypeCode) {
			return JudgingApplication.toDecide(new JudgingApplicationRequireImpl(applicationSettingRepository,
					commonAlgorithm, workTypeRepository, applicationRepository), cId, sId, date, workTypeCode.v());
		}

	}

	@AllArgsConstructor
	private class JudgingApplicationRequireImpl implements JudgingApplication.Require {

		private ApplicationSettingRepository applicationSettingRepository;

		private CommonAlgorithm commonAlgorithm;

		private WorkTypeRepository workTypeRepository;

		private ApplicationRepository applicationRepository;

		@Override
		public Optional<ApplicationSetting> findByCompanyId(String companyId) {
			return this.applicationSettingRepository.findByCompanyId(companyId);
		}

		@Override
		public ApprovalFunctionSet getApprovalFunctionSet(String companyID, String employeeID, GeneralDate date,
				ApplicationTypeShare targetApp) {
			return this.commonAlgorithm.getApprovalFunctionSet(companyID, employeeID, date,
					EnumAdaptor.valueOf(targetApp.value, ApplicationType.class));
		}

		@Override
		public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
			return this.workTypeRepository.findByPK(companyId, workTypeCd);
		}

		@Override
		public List<Application> getAllApplicationByAppTypeAndPrePostAtr(String employeeID, int appType,
				GeneralDate appDate, int prePostAtr) {
			return this.applicationRepository.getAllApplicationByAppTypeAndPrePostAtr(employeeID, appType, appDate,
					prePostAtr);
		}

	}

}
