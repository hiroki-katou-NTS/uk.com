package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHisExportAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHistoryExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.AffDepartHistoryAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.WkpConfigAtTimeExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.EmploymentHisExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.IEmploymentHistoryAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInfoAverAdapter;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInformationImport;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryCategory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryCategoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service.ConfirmPersonSetStatusService;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ConOfIndiviSetSttFinder {
    @Inject
    private StateUseUnitSetRepository stateUseUnitSetRepo;

    @Inject
    private StateCorreHisIndiviRepository stateCorreHistIndiviRepo;

    @Inject
    private AffDepartHistoryAdapter affDepHistAdapter;

    @Inject
    private StateCorreHisDeparRepository stateCorreHistDepRepo;

    @Inject
    private DepartmentAdapter departmentAdapter;

    @Inject
    private StateCorreHisComRepository stateCorreHistComRepo;

    @Inject
    private StatementLayoutRepository statementLayoutRepo;

    @Inject
    private IEmploymentHistoryAdapter employmentHistoryAdapter;

    @Inject
    private StateCorreHisEmRepository mStateCorreHisEmRepository;

    @Inject
    private ClassificationHisExportAdapter clsHistExportAdapter;

    @Inject
    private StateCorreHisClsRepository stateCorreHistClsRepo;

    @Inject
    private StateCorreHisPoRepository stateCorreHistPoRepo;

    @Inject
    private JobTitleInfoAdapter jobTitleInfoAdapter;

    @Inject
    private EmploySalaryClassHistoryRepository empSalClassHistRepo;

    @Inject
    private StateCorreHisSalaRepository stateCorreHistSalRepo;

    @Inject
    private EmploySalaryCategoryRepository empSalCtgRepo;

    @Inject
    private EmployeeInfoAverAdapter empInfoAverAdapter;

    @Inject
    private SalaryClassificationInformationRepository salClsInfoRepo;







}
