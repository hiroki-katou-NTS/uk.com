package nts.uk.ctx.at.request.pubimp.application.approvalstatus;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service.JudgingApplication;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.pub.application.approvalstatus.JudgingApplicationByWorkTypePub;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.Export.勤務種類から促す申請を判断する.勤務種類から促す申請を判断する
 * <<Public>>
 * 
 * @author tutt
 *
 */
@Stateless
public class JudgingApplicationByWorkTypePubImpl implements JudgingApplicationByWorkTypePub {

	@Inject
	private ApplicationSettingRepository applicationSettingRepository;

	@Inject
	private CommonAlgorithm commonAlgorithm;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private ApplicationRepository applicationRepository;

	@Override
	public Optional<Integer> judgingApp(String cid, String sId, GeneralDate date, String workTypeCode) {

		Optional<ApplicationTypeShare> opt = JudgingApplication
				.toDecide(new JudgingApplicationRequireImpl(applicationSettingRepository, commonAlgorithm,
						workTypeRepository, applicationRepository), cid, sId, date, workTypeCode);

		if (opt.isPresent()) {
			return Optional.of(opt.get().value);
		}
		return Optional.empty();
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
