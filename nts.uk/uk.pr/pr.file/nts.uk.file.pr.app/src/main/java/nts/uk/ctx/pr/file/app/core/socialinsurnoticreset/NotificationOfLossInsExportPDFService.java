package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.PersonalNumClass;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.ReasonsForLossPensionIns;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationOfLossInsExportPDFService extends ExportService<NotificationOfLossInsExportQuery> {

	@Inject
	private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

	@Inject
	private EmpWelfarePenInsQualiInforRepository empWelfarePenInsQualiInforRepository;

	@Inject
	private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

	@Inject
	private NotificationOfLossInsFileGenerator socialInsurNotiCreateSetFileGenerator;

	@Inject
	private NotificationOfLossInsExRepository socialInsurNotiCreateSetEx;

	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;


	@Override
	protected void handle(ExportServiceContext<NotificationOfLossInsExportQuery> exportServiceContext) {
		String userId = AppContexts.user().userId();
		String cid = AppContexts.user().companyId();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
		NotificationOfLossInsExport socialInsurNotiCreateSet = exportServiceContext.getQuery().getSocialInsurNotiCreateSet();
		SocialInsurNotiCreateSet domain = new SocialInsurNotiCreateSet(userId, cid,
				socialInsurNotiCreateSet.getOfficeInformation(),
				socialInsurNotiCreateSet.getBusinessArrSymbol(),
				socialInsurNotiCreateSet.getOutputOrder(),
				socialInsurNotiCreateSet.getPrintPersonNumber(),
				socialInsurNotiCreateSet.getSubmittedName(),
				socialInsurNotiCreateSet.getInsuredNumber(),
				socialInsurNotiCreateSet.getFdNumber(),
				socialInsurNotiCreateSet.getTextPersonNumber(),
				socialInsurNotiCreateSet.getLineFeedCode(),
				socialInsurNotiCreateSet.getOutputFormat()
		);
		socialInsNotifiCreateSetRegis(domain);
		List<String> empIds = exportServiceContext.getQuery().getEmpIds();
		if(end.before(start)) {
			throw new BusinessException("Msg_812");
		}
		if(!checkHealthInsQualificationInformation(userId) && checkWelfarePenInsQualiInformation(userId)) {
			throw new BusinessException("Msg_37");
		}

		if( socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.DO_NOT_OUTPUT.value) {
			List<InsLossDataExport> healthInsLoss = socialInsurNotiCreateSetEx.getHealthInsLoss(empIds, cid, start, end);
			List<InsLossDataExport> welfPenInsLoss = socialInsurNotiCreateSetEx.getWelfPenInsLoss(empIds, cid, start, end);
			List<InsLossDataExport> overSeventy = welfPenInsLoss.stream().filter(item-> item.getCause() == ReasonsForLossPensionIns.ONLY_PENSION_INSURANCE.value).collect(Collectors.toList());
			List<InsLossDataExport> underSeventy = welfPenInsLoss.stream().filter(item-> item.getCause() != ReasonsForLossPensionIns.ONLY_PENSION_INSURANCE.value).collect(Collectors.toList());
			healthInsLoss.addAll(underSeventy);
			List<SocialInsuranceOffice> socialInsuranceOffice =  socialInsuranceOfficeRepository.findByCid(cid);
			CompanyInfor company = socialInsurNotiCreateSetEx.getCompanyInfor(cid);
			socialInsurNotiCreateSetFileGenerator.generate(exportServiceContext.getGeneratorContext(),
					new LossNotificationInformation(healthInsLoss, overSeventy, socialInsuranceOffice, socialInsurNotiCreateSet, exportServiceContext.getQuery().getReference(), company, null));
		}
	}

	//社会保険届作成設定登録処理
	private void socialInsNotifiCreateSetRegis(SocialInsurNotiCreateSet domain){
		socialInsurNotiCrSetRepository.update(domain);
	}

	//社員健康保険資格情報が存在するかチェックする
	private boolean checkHealthInsQualificationInformation(String userId){
		return emplHealInsurQualifiInforRepository.checkHealInsurQualifiInformation(userId);
	}

	//社員健康保険資格情報が存在するかチェックする
	private boolean checkWelfarePenInsQualiInformation(String userId){
		return empWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInfor(userId);
	}
}
