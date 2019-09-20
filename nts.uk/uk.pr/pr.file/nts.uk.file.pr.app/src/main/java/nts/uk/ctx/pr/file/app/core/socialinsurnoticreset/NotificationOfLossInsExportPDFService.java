package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.PersonalNumClass;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.ReasonsForLossPensionIns;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationOfLossInsExportPDFService extends ExportService<NotificationOfLossInsExportQuery> {

	@Inject
	private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

	@Inject
	private NotificationOfLossInsFileGenerator socialInsurNotiCreateSetFileGenerator;

	@Inject
	private NotificationOfLossInsExRepository socialInsurNotiCreateSetEx;

	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	@Inject
	private EmployeeInfoAdapter employeeInfoAdapter;

	@Override
	protected void handle(ExportServiceContext<NotificationOfLossInsExportQuery> exportServiceContext) {
		String userId = AppContexts.user().userId();
		String cid = AppContexts.user().companyId();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
		List<EmployeeInfoEx>  employee = employeeInfoAdapter.findBySIds(exportServiceContext.getQuery().getEmpIds());
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
		List<InsLossDataExport> healthInsLoss = socialInsurNotiCreateSetEx.getHealthInsLoss(empIds, cid, start, end);
		List<InsLossDataExport> welfPenInsLoss = socialInsurNotiCreateSetEx.getWelfPenInsLoss(empIds, cid, start, end);
		if(healthInsLoss.isEmpty() && welfPenInsLoss.isEmpty()) {
			throw new BusinessException("Msg_37");
		}

		if( socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.DO_NOT_OUTPUT.value) {
			List<InsLossDataExport> overSeventy = welfPenInsLoss.stream().filter(item-> item.getCause() == ReasonsForLossPensionIns.ONLY_PENSION_INSURANCE.value).collect(Collectors.toList());
			List<InsLossDataExport> underSeventy = welfPenInsLoss.stream().filter(item-> item.getCause() != ReasonsForLossPensionIns.ONLY_PENSION_INSURANCE.value).collect(Collectors.toList());
			healthInsLoss.addAll(underSeventy);
			healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getEmpCd));
			CompanyInfor company = socialInsurNotiCreateSetEx.getCompanyInfor(cid);
			socialInsurNotiCreateSetFileGenerator.generate(exportServiceContext.getGeneratorContext(),
					new LossNotificationInformation(healthInsLoss, overSeventy,null, domain, exportServiceContext.getQuery().getReference(), company, null));
		}
	}

	//社会保険届作成設定登録処理
	private void socialInsNotifiCreateSetRegis(SocialInsurNotiCreateSet domain){
		socialInsurNotiCrSetRepository.update(domain);
	}
}
