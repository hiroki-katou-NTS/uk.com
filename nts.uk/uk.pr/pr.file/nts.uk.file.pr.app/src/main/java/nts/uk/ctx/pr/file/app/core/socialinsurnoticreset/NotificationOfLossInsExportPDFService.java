package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.ReasonsForLossPensionIns;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter personExportAdapter;

	@Inject
	private CompanyInforAdapter companyAdapter;

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
		List<InsLossDataExport> healthInsLoss = socialInsurNotiCreateSetEx.getHealthInsLoss(empIds, cid, start, end);
		if(healthInsLoss.isEmpty()) {
			throw new BusinessException("Msg_37");
		}
        List<String> emplds = healthInsLoss.stream().map(InsLossDataExport::getEmpId).collect(Collectors.toList());
        List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(emplds);
        List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
        healthInsLoss.forEach(item -> {
            PersonExport p = InsLossDataExport.getPersonInfor(employeeInfoList, personList, item.getEmpId());
            item.setPersonName(p.getPersonNameGroup().getPersonName().getFullName());
            item.setPersonNameKana(p.getPersonNameGroup().getPersonName().getFullNameKana());
            item.setOldName(p.getPersonNameGroup().getTodokedeFullName().getFullName());
            item.setOldNameKana(p.getPersonNameGroup().getTodokedeFullName().getFullNameKana());
            item.setBirthDay(p.getBirthDate().toString("yyyy-MM-dd"));
        });
        if(domain.getOutputOrder() == SocialInsurOutOrder.EMPLOYEE_KANA_ORDER) {
			healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getPersonNameKana)).collect(Collectors.toList());
		}
		if(domain.getOutputOrder() == SocialInsurOutOrder.HEAL_INSUR_NUMBER_ORDER) {
			healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getHealInsNumber)).collect(Collectors.toList());
		}
		if(domain.getOutputOrder() == SocialInsurOutOrder.WELF_AREPEN_NUMBER_ORDER) {
			healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getWelfPenNumber)).collect(Collectors.toList());
		}
		if(domain.getOutputOrder() == SocialInsurOutOrder.HEAL_INSUR_NUMBER_UNION_ORDER) {
			healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getHealInsUnionNumber)).collect(Collectors.toList());
		}
		if(domain.getOutputOrder() == SocialInsurOutOrder.ORDER_BY_FUND) {
			healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getMemberNumber)).collect(Collectors.toList());
		}
		if(domain.getOutputOrder() == SocialInsurOutOrder.INSURED_PER_NUMBER_ORDER) {
			if(domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM) {
				healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getHealInsNumber)).collect(Collectors.toList());
			}
			if(domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER) {
				healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getWelfPenNumber)).collect(Collectors.toList());
			}
			if(domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION) {
				healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getHealInsUnionNumber)).collect(Collectors.toList());
			}
			if(domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER) {
				healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getMemberNumber)).collect(Collectors.toList());
			}
		}

		List<InsLossDataExport> overSeventy = healthInsLoss.stream().filter(item -> (item.getCause2() != null && item.getCause2() == ReasonsForLossPensionIns.ONLY_PENSION_INSURANCE.value) ).collect(Collectors.toList());
		List<InsLossDataExport> underSeventy = healthInsLoss.stream().filter(item -> (item.getCause2() != null && item.getCause2() != ReasonsForLossPensionIns.ONLY_PENSION_INSURANCE.value) || item.getCause2() == null).collect(Collectors.toList());
		CompanyInfor company = companyAdapter.getCompanyNotAbolitionByCid(cid);
		socialInsurNotiCreateSetFileGenerator.generate(exportServiceContext.getGeneratorContext(),
				new LossNotificationInformation(underSeventy, overSeventy, null, domain, exportServiceContext.getQuery().getReference(), company, null));

	}

	//社会保険届作成設定登録処理
	private void socialInsNotifiCreateSetRegis(SocialInsurNotiCreateSet domain){
		socialInsurNotiCrSetRepository.update(domain);
	}
}
