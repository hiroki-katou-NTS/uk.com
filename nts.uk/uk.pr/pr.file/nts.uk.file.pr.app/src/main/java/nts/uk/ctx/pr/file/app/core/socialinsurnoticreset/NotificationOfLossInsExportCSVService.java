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
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationOfLossInsExportCSVService extends ExportService<NotificationOfLossInsExportQuery> {

	@Inject
	private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

	@Inject
	private EmpWelfarePenInsQualiInforRepository empWelfarePenInsQualiInforRepository;

	@Inject
	private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

	@Inject
	private NotificationOfLossInsCSVFileGenerator notificationOfLossInsCSVFileGenerator;

	@Inject
	private NotificationOfLossInsExRepository socialInsurNotiCreateSetEx;

	@Inject
	private SocialInsurancePrefectureInformationRepository socialInsuranceInfor;

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
		CompanyInfor company = companyAdapter.getCompanyNotAbolitionByCid(cid);
		NotificationOfLossInsExport socialInsurNotiCreateSet = exportServiceContext.getQuery().getSocialInsurNotiCreateSet();
        List<String> empIds = exportServiceContext.getQuery().getEmpIds();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
		SocialInsurNotiCreateSet domain = new SocialInsurNotiCreateSet(userId,cid,
				socialInsurNotiCreateSet.getOfficeInformation(),
				socialInsurNotiCreateSet.getBusinessArrSymbol(),
				socialInsurNotiCreateSet.getOutputOrder(),
				socialInsurNotiCreateSet.getPrintPersonNumber(),
				socialInsurNotiCreateSet.getSubmittedName(),
				socialInsurNotiCreateSet.getInsuredNumber(),
				socialInsurNotiCreateSet.getFdNumber(),
				socialInsurNotiCreateSet.getTextPersonNumber(),
                socialInsurNotiCreateSet.getOutputFormat(),
				socialInsurNotiCreateSet.getLineFeedCode()
		);
		socialInsNotifiCreateSetRegis(domain);
		if(end.before(start)) {
			throw new BusinessException("Msg_812");
		}
		if(domain.getInsuredNumber() == InsurPersonNumDivision.DO_NOT_OUPUT) {
			throw new BusinessException("MsgQ_174", "QSI013_A222_24");
		}
		if(domain.getOfficeInformation() == BusinessDivision.DO_NOT_OUTPUT || domain.getOfficeInformation() == BusinessDivision.DO_NOT_OUTPUT_BUSINESS) {
			throw new BusinessException("MsgQ_174", "QSI013_A222_30");
		}
		if(!domain.getFdNumber().isPresent()) {
			throw new BusinessException("MsgQ_5", "QSI013_A222_53");
		}
        boolean empWelfarePenInsQualiInfor = empWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforEnd(start, end, empIds);
        boolean emplHealInsurQualifiInfor= emplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforEndDate(start, end, empIds);
		if(domain.getOutputFormat().get() == OutputFormatClass.PEN_OFFICE && empWelfarePenInsQualiInfor && emplHealInsurQualifiInfor) {
		    throw new BusinessException("Msg_37");
		}
		if(domain.getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO && emplHealInsurQualifiInfor) {
		    throw new BusinessException("Msg_37");
        }
        if(domain.getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN && empWelfarePenInsQualiInfor) {
            throw new BusinessException("Msg_37");
        }
        if(domain.getOutputFormat().get() == OutputFormatClass.PEN_OFFICE){
			List<SocialInsurancePrefectureInformation> infor  = socialInsuranceInfor.findByHistory();
			List<InsLossDataExport> healthInsLoss = socialInsurNotiCreateSetEx.getHealthInsLoss(empIds, cid, start, end);
			if(healthInsLoss.isEmpty()) {
				throw new BusinessException("Msg_37");
			}
			List<String> emplds = healthInsLoss.stream().map(InsLossDataExport::getEmpId).collect(Collectors.toList());
			List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(emplds);
			List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
			healthInsLoss.forEach(item -> {
				if (!item.getEndDate().isEmpty() && item.getEndDate().equals(item.getEndDate2())) {
					item.setCaInsurance2(item.getCaInsurance());
					item.setNumRecoved2(item.getNumRecoved());
					item.setOtherReason2(item.getOtherReason());
				}
				PersonExport p = InsLossDataExport.getPersonInfor(employeeInfoList, personList, item.getEmpId());
				item.setPersonName(p.getPersonNameGroup().getPersonName().getFullName());
				item.setOldName(p.getPersonNameGroup().getTodokedeFullName().getFullName());
				item.setPersonNameKana(p.getPersonNameGroup().getPersonName().getFullNameKana());
				item.setOldNameKana(p.getPersonNameGroup().getTodokedeFullName().getFullNameKana());
				item.setBirthDay(p.getBirthDate().toString("yyyy-MM-dd"));
			});
			healthInsLoss = this.order(domain, healthInsLoss);
			notificationOfLossInsCSVFileGenerator.generate(exportServiceContext.getGeneratorContext(),
					new LossNotificationInformation(healthInsLoss, null,null, domain, exportServiceContext.getQuery().getReference(), company, infor));
        }

        if(domain.getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
			List<SocialInsurancePrefectureInformation> infor  = socialInsuranceInfor.findByHistory();
			List<InsLossDataExport> healthInsAssociationData = socialInsurNotiCreateSetEx.getHealthInsLoss(empIds, cid, start, end);
			if(healthInsAssociationData.isEmpty()) {
				throw new BusinessException("Msg_37");
			}
			List<String> emplds = healthInsAssociationData.stream().map(InsLossDataExport::getEmpId).collect(Collectors.toList());
			List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(emplds);
			List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
			healthInsAssociationData.forEach(item -> {
				PersonExport p = InsLossDataExport.getPersonInfor(employeeInfoList, personList, item.getEmpId());
				item.setPersonName(p.getPersonNameGroup().getPersonName().getFullName());
				item.setOldName(p.getPersonNameGroup().getTodokedeFullName().getFullName());
				item.setPersonNameKana(p.getPersonNameGroup().getPersonName().getFullNameKana());
				item.setOldNameKana(p.getPersonNameGroup().getTodokedeFullName().getFullNameKana());
				item.setBirthDay(p.getBirthDate().toString("yyyy-MM-dd"));
			});
			healthInsAssociationData = this.order(domain, healthInsAssociationData);
			notificationOfLossInsCSVFileGenerator.generate(exportServiceContext.getGeneratorContext(),
					new LossNotificationInformation(healthInsAssociationData, null, null, domain, exportServiceContext.getQuery().getReference(), company, infor));
		}

		if(domain.getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {

			List<SocialInsurancePrefectureInformation> infor  = socialInsuranceInfor.findByHistory();
			List<PensFundSubmissData> pensFundSubmissData = socialInsurNotiCreateSetEx.getHealthInsAssociation(empIds, cid, start, end);
			if(pensFundSubmissData.isEmpty()) {
				throw new BusinessException("Msg_37");
			}
			List<String> emplds = pensFundSubmissData.stream().map(PensFundSubmissData::getEmpId).collect(Collectors.toList());
			List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(emplds);
			List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
			pensFundSubmissData.forEach(item -> {
				PersonExport p = InsLossDataExport.getPersonInfor(employeeInfoList, personList, item.getEmpId());
				item.setPersonNameKana(p.getPersonNameGroup().getPersonName().getFullNameKana());
				item.setOldNameKana(p.getPersonNameGroup().getTodokedeFullName().getFullNameKana());
				item.setPersonName(p.getPersonNameGroup().getPersonName().getFullName());
				item.setOldName(p.getPersonNameGroup().getTodokedeFullName().getFullName());
				item.setBirthDay(p.getBirthDate().toString("yyyy-MM-dd"));
			});
            if(domain.getOutputOrder() == SocialInsurOutOrder.EMPLOYEE_KANA_ORDER) {
                pensFundSubmissData = pensFundSubmissData.stream().sorted(Comparator.comparing(PensFundSubmissData::getPersonNameKana)).collect(Collectors.toList());
            }
            if(domain.getOutputOrder() == SocialInsurOutOrder.HEAL_INSUR_NUMBER_ORDER) {
                pensFundSubmissData = pensFundSubmissData.stream().sorted(Comparator.comparing(PensFundSubmissData::getHealInsNumber)).collect(Collectors.toList());
            }
            if(domain.getOutputOrder() == SocialInsurOutOrder.WELF_AREPEN_NUMBER_ORDER) {
                pensFundSubmissData = pensFundSubmissData.stream().sorted(Comparator.comparing(PensFundSubmissData::getWelNumber)).collect(Collectors.toList());
            }
            if(domain.getOutputOrder() == SocialInsurOutOrder.HEAL_INSUR_NUMBER_UNION_ORDER) {
                pensFundSubmissData = pensFundSubmissData.stream().sorted(Comparator.comparing(PensFundSubmissData::getHealInsUnionNumber)).collect(Collectors.toList());
            }
            if(domain.getOutputOrder() == SocialInsurOutOrder.ORDER_BY_FUND) {
                pensFundSubmissData = pensFundSubmissData.stream().sorted(Comparator.comparing(PensFundSubmissData::getMemberNumber)).collect(Collectors.toList());
            }
			notificationOfLossInsCSVFileGenerator.generate(exportServiceContext.getGeneratorContext(),
					new LossNotificationInformation(null, null, pensFundSubmissData,domain, exportServiceContext.getQuery().getReference(), company, infor));
		}

    }

	//社会保険届作成設定登録処理
	private void socialInsNotifiCreateSetRegis(SocialInsurNotiCreateSet domain){
		socialInsurNotiCrSetRepository.update(domain);
	}

	private List<InsLossDataExport> order(SocialInsurNotiCreateSet order, List<InsLossDataExport> healthInsLoss){
		if(order.getOutputOrder() == SocialInsurOutOrder.EMPLOYEE_KANA_ORDER) {
			return healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getPersonNameKana).thenComparing(InsLossDataExport::getEmpCd)).collect(Collectors.toList());
		}
		if(order.getOutputOrder() == SocialInsurOutOrder.HEAL_INSUR_NUMBER_ORDER) {
			return healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getHealInsNumber)).collect(Collectors.toList());
		}
		if(order.getOutputOrder() == SocialInsurOutOrder.WELF_AREPEN_NUMBER_ORDER) {
			return healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getWelfPenNumber)).collect(Collectors.toList());
		}
		if(order.getOutputOrder() == SocialInsurOutOrder.HEAL_INSUR_NUMBER_UNION_ORDER) {
			return healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getHealInsUnionNumber)).collect(Collectors.toList());
		}
		if(order.getOutputOrder() == SocialInsurOutOrder.ORDER_BY_FUND) {
			return healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(InsLossDataExport::getMemberNumber)).collect(Collectors.toList());
		}
		if(order.getOutputOrder() == SocialInsurOutOrder.INSURED_PER_NUMBER_ORDER) {
			healthInsLoss = healthInsLoss.stream().sorted(Comparator.comparing(InsLossDataExport::getOfficeCd).thenComparing(order.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? InsLossDataExport::getHealInsNumber : InsLossDataExport::getWelfPenNumber)
			).collect(Collectors.toList());
		}
		return healthInsLoss;
	}

}
