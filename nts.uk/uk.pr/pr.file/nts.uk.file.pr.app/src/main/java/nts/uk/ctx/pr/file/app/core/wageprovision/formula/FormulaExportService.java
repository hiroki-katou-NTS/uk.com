package nts.uk.ctx.pr.file.app.core.wageprovision.formula;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.SysClassificationAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.SysDepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.MasterUseDto;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class FormulaExportService extends ExportService<FormulaExportQuery> {

	@Inject
	private FormulaExRepository formulaExRepository;

	@Inject
	private FormulaFileGenerator formulaFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Inject
	private CurrProcessDateRepository currProcessDateRepository;

	@Inject
	private SetDaySupportRepository setDaySupportRepository;

	@Inject
	private SysEmploymentAdapter sysEmploymentAdapter;

	@Inject
	private SyJobTitleAdapter syJobTitleAdapter;

	@Inject
	private SysDepartmentAdapter sysDepartmentAdapter;

	@Inject
	private SysClassificationAdapter sysClassificationAdapter;


	@Override
	protected void handle(ExportServiceContext<FormulaExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> formulaInfor = new ArrayList<>();
		List<Object[]> formulaDetails = new ArrayList<>();
		List<Object[]> targetItems = new ArrayList<>();
		GeneralDate baseDate = getReferenceDate();
		formulaInfor = formulaExRepository.getFormulaInfor(cid, exportServiceContext.getQuery().startDate, baseDate);
		formulaDetails = formulaExRepository.getDetailFormula(cid, exportServiceContext.getQuery().startDate );
		targetItems = formulaExRepository.getBaseAmountTargetItem(cid, exportServiceContext.getQuery().startDate);
		List<MasterUseDto> employments = sysEmploymentAdapter.findAll(AppContexts.user().companyId()).stream().map(
				item -> new MasterUseDto(item.getCode(), item.getName())).collect(Collectors.toList());
		List<MasterUseDto> departments = sysDepartmentAdapter.getDepartmentByCompanyIdAndBaseDate(AppContexts.user().companyId(), baseDate).stream().map(
				item -> new MasterUseDto(item.getDepartmentCode(), item.getDepartmentName())).collect(Collectors.toList());
		List<MasterUseDto> cls = sysClassificationAdapter.getClassificationByCompanyId(AppContexts.user().companyId()).stream().map(
				item -> new MasterUseDto(item.getClassificationCode(), item.getClassificationName())).collect(Collectors.toList());
		List<MasterUseDto> jobs = syJobTitleAdapter.findAll(AppContexts.user().companyId(), baseDate).stream().map(item ->
				new MasterUseDto(item.getJobTitleCode(), item.getJobTitleName())).collect(Collectors.toList());
		FormulaExportData data = new FormulaExportData(formulaInfor, formulaDetails , targetItems , companyName, exportServiceContext.getQuery().startDate, employments, departments,cls, jobs);
		formulaFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}

	private GeneralDate getReferenceDate() {
		GeneralDate [] generalDate = {GeneralDate.today()};
		currProcessDateRepository.getCurrProcessDateByIdAndProcessCateNo(AppContexts.user().companyId(), 1).ifPresent(processDate -> {
			setDaySupportRepository.getSetDaySupportByIdAndProcessDate(AppContexts.user().companyId(), 1, processDate.getGiveCurrTreatYear().v()).ifPresent(setDaySupport -> {
				generalDate[0] = setDaySupport.getEmpExtraRefeDate();
			});
		});
		return generalDate[0];
	}
}
