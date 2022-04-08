package nts.uk.file.com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.AppTypeName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmpUnregisterInput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.EmployeeUnregisterApprovalRoot;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.query.model.workplace.WorkplaceAdapter;
import nts.uk.query.model.workplace.WorkplaceInfoImport;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * CMM018 - L
 * 未登録個人kリスト
 * @author hoatt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EmployeeUnregisterOutputExportService extends ExportService<EmpUnregisterInput> {
	@Inject
	private EmployeeUnregisterApprovalRoot empUnregister;

	@Inject
	private CompanyAdapter company;

    @Inject
    private WorkplaceAdapter workplaceAdapter;

	@Inject
	private EmployeeUnregisterOutputGenerator employgenerator;

    @Inject
    private EmployeeInformationRepository employeeInformationRepo;

	@Override
	protected void handle(ExportServiceContext<EmpUnregisterInput> context) {
		String companyId = AppContexts.user().companyId();

		// get query parameters
		GeneralDate baseDate = context.getQuery().getBaseDate();
		int sysAtr = context.getQuery().getSysAtr();
		List<AppTypeName> lstName = context.getQuery().getLstAppName();
		List<Integer> lstNotice = new ArrayList<>();
		List<String> lstEvent = new ArrayList<>();
		if(sysAtr == SystemAtr.HUMAN_RESOURCES.value) {
			lstNotice = this.lstNotice(lstName);
			lstEvent = this.lstEvent(lstName);
		}
		// create data source
		List<EmployeeUnregisterOutput> items = empUnregister.lstEmployeeUnregister(
				companyId,
				baseDate,
				sysAtr,
				lstNotice,
				lstEvent,
				lstName
		).stream().filter(i -> i != null && i.getEmployeeId() != null).collect(Collectors.toList());

		if (CollectionUtil.isEmpty(items)) {
			throw new BusinessException("Msg_1765");
		}

		// 取得した承認ルート未登録をエクセル出力する

        // 社員の情報を取得する
		List<String> employeeIds = items.stream().map(EmployeeUnregisterOutput::getEmployeeId).distinct().collect(Collectors.toList());
        EmployeeInformationQuery employeeInformationQuery = EmployeeInformationQuery.builder()
                .employeeIds(employeeIds)
                .referenceDate(baseDate)
                .toGetWorkplace(true)
                .toGetDepartment(false)
                .toGetPosition(false)
                .toGetEmployment(false)
                .toGetClassification(false)
                .toGetEmploymentCls(false)
                .build();
        List<EmployeeInformation> employeeInfors = employeeInformationRepo.find(employeeInformationQuery);

        // Temporaryから職場ID一意で抽出する（Distinct)
        List<String> workplaceIds = items.stream().filter(i -> i.getWorkplaceId().isPresent())
                .map(i -> i.getWorkplaceId().get()).distinct().collect(Collectors.toList());
		// [No.560]職場IDから職場の情報をすべて取得する
        List<WorkplaceInfoImport> workplaceInfos = workplaceAdapter.getWorkplaceInfoByWkpIds(companyId, workplaceIds, baseDate);

        EmployeeUnregisterOutputDataSoure dataSource = new EmployeeUnregisterOutputDataSoure(
        		baseDate,
				company.getCurrentCompany().get().getCompanyName(),
				items,
				employeeInfors,
				workplaceInfos
		);

		// generate file
		this.employgenerator.generate(context.getGeneratorContext(), dataSource);
	}

	private List<Integer> lstNotice(List<AppTypeName> lstName){
		List<Integer> lstResult = new ArrayList<>();
		for(AppTypeName app : lstName) {
			if(app.getEmpRAtr() == EmploymentRootAtr.NOTICE.value) {
				lstResult.add(Integer.valueOf(app.getValue()));
			}
		}
		return lstResult;
	}

	private List<String> lstEvent(List<AppTypeName> lstName){
		List<String> lstResult = new ArrayList<>();
		for(AppTypeName app : lstName) {
			if(app.getEmpRAtr() == EmploymentRootAtr.BUS_EVENT.value) {
				lstResult.add(app.getValue());
			}
		}
		return lstResult;
	}
}
