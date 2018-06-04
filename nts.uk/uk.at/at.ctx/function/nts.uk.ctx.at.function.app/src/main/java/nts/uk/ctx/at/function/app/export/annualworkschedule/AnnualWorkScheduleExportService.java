package nts.uk.ctx.at.function.app.export.annualworkschedule;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.PageBreakIndicator;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleGenerator;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
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

		YearMonth startYm = YearMonth.parse(query.getStartYearMonth(), DateTimeFormatter.ofPattern("uuuu/MM"));
		YearMonth endYm = YearMonth.parse(query.getEndYearMonth(), DateTimeFormatter.ofPattern("uuuu/MM"));
		//対象期間をチェックする
		if (startYm.until(endYm, ChronoUnit.MONTHS) + 1 > 12) throw new BusinessException("Msg_883");
		//出力対象の社員をチェックする
		if (query.getEmployees() == null || query.getEmployees().isEmpty()) throw new BusinessException("Msg_884");

		ExportData data = this.repostory.getData(companyId, query.getSetItemsOutputCd(),
				startYm, endYm,
				query.getEmployees().stream().map(m -> new Employee(m.getEmployeeId(), m.getCode(),
						m.getName(), m.getWorkplaceName()))
				.collect(Collectors.toList()));

		data.setPageBreak(EnumAdaptor.valueOf(query.getBreakPage(), PageBreakIndicator.class));
		// invoke generator
		this.generator.generate(context.getGeneratorContext(), data);
	}
}
