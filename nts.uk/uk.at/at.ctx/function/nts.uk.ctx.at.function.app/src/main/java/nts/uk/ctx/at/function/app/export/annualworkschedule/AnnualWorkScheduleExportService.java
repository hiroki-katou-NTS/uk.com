package nts.uk.ctx.at.function.app.export.annualworkschedule;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.PageBreakIndicator;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleGenerator;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AnnualWorkScheduleExportService extends ExportService<AnnualWorkScheduleExportQuery> {
	@Inject
	AnnualWorkScheduleRepository repostory;
	@Inject
	AnnualWorkScheduleGenerator generator;

	@Override
	protected void handle(ExportServiceContext<AnnualWorkScheduleExportQuery> context) {
		String companyId = AppContexts.user().companyId();
		AnnualWorkScheduleExportQuery query = context.getQuery();
		Year fiscalYear = new Year(Integer.parseInt(query.getFiscalYear()));
		YearMonth startYm = YearMonth.parse(query.getStartYearMonth(), DateTimeFormatter.ofPattern("uuuu/MM"));
		YearMonth endYm = YearMonth.parse(query.getEndYearMonth(), DateTimeFormatter.ofPattern("uuuu/MM"));
		List<Employee> employees = query.getEmployees().stream()
				.map(m -> new Employee(m.getEmployeeId(), m.getCode(), m.getName(), m.getWorkplaceName()))
				.collect(Collectors.toList());

		ExportData data = this.repostory.outputProcess(companyId, query.getSetItemsOutputCd(), fiscalYear, startYm,
				endYm, employees, 1);		
		data.setPageBreak(EnumAdaptor.valueOf(query.getBreakPage(), PageBreakIndicator.class));
		// invoke generator
		this.generator.generate(context.getGeneratorContext(), data);
	}
}
