package nts.uk.file.com.app.approvalmanagement.workroot;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetReferenceEmployeesDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService.Require;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApprovalPhaseExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApprovalSettingInformationExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApproverExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.PersonApprovalRootPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApproversExportFileQueryImpl implements ApproversExportFileQuery {
	
	@Inject
	private ApproversExportGenerator generator;

	@Inject
	private CompanyRepository companyRepository;

	@Inject
	private EmployeeInformationPub employeeInformationPub;

	@Inject
	private SyEmployeePub syEmployeePub;

	@Inject
	private PersonApprovalRootPub personApprovalRootPub;

	@Inject
	private WorkplaceExportService workplaceExportService;

	@Inject
	private RequireService requireService;

	@Override
	public void handle(ExportServiceContext<ApproversQuery> context, ApproversQuery query) {
		ApproversExportDataSource dataSource = this.getExportData(query);
		this.generator.generate(context.getGeneratorContext(), dataSource);
	}

	/**
	 * UKDesign.UniversalK.??????.CMM_???????????????????????????.CMM030_????????????????????????.G???????????????????????????.???????????????OCD.????????????????????????????????????
	 */
	@Override
	public ApproversExportDataSource getExportData(ApproversQuery query) {
		String contractCd = AppContexts.user().contractCode();
		String cid = AppContexts.user().companyId();
		Require require = this.requireService.createRequire();
		// ????????????(Require, ?????????)
		List<String> sids = GetReferenceEmployeesDomainService.get(require, query.getBaseDate());
		// <<Public>> ??????????????????????????????
		EmployeeInformationQueryDto empInfoQuery = new EmployeeInformationQueryDto(sids, query.getBaseDate(), true,
				false, true, true, false, false);
		List<EmployeeInformationExport> employeeInfos = this.employeeInformationPub.find(empInfoQuery);
		// [No.560]??????ID?????????????????????????????????????????????
		List<String> workplaceIds = employeeInfos.stream().map(data -> data.getWorkplace().getWorkplaceId())
				.collect(Collectors.toList());
		List<WorkplaceInforParam> workplaces = this.workplaceExportService.getWorkplaceInforFromWkpIds(cid,
				workplaceIds, query.getBaseDate());
		// ????????????(??????ID, ??????ID, ?????????, ??????????????????)
		List<ApprovalSettingInformationExport> settingInfos = this.personApprovalRootPub.findApproverList(cid, sids,
				query.getBaseDate(), SystemAtr.WORK.value);
		// [List<?????????????????????>=NULL]
		if (settingInfos.isEmpty()) {
			throw new BusinessException("Msg_3300");
		}
		// [RQ600]??????ID???List?????????????????????????????????????????????????????????????????????
		List<String> targetSids = settingInfos.stream().map(ApprovalSettingInformationExport::getApprovalPhases)
				.flatMap(List::stream).map(ApprovalPhaseExport::getApprover).flatMap(List::stream)
				.map(ApproverExport::getEmployeeId).distinct().collect(Collectors.toList());
		List<ResultRequest600Export> employees = this.syEmployeePub.getEmpInfoLstBySids(targetSids, null, false, false);
		// get(????????????????????????????????????????????????ID)
		Optional<Company> optCompany = this.companyRepository.findAllByListCid(contractCd, Arrays.asList(cid)).stream()
				.findFirst();
		return new ApproversExportDataSource(query.getBaseDate(), sids, employeeInfos, workplaces, settingInfos,
				employees, optCompany);
	}
}
