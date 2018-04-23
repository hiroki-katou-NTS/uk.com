package nts.uk.ctx.at.function.app.find.holidaysremaining.report;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.function.app.find.holidaysremaining.HdRemainManageFinder;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;

@Stateless
public class HolidaysRemainingReportHandler extends ExportService<HolidaysRemainingReportQuery> {

	@Inject
	private HolidaysRemainingReportGenerator reportGenerator;
	@Inject
	private HdRemainManageFinder hdFinder;
	
	@Override
	protected void handle(ExportServiceContext<HolidaysRemainingReportQuery> context) {
		// todo get data here
		HolidaysRemainingReportQuery query = context.getQuery();
		Optional<HolidaysRemainingManagement> hdManagement = hdFinder.findByCode(query.getHolidayRemainingOutputCondition().getOutputItemSettingCode());
		if (hdManagement.isPresent()){
			this.reportGenerator.generate(context.getGeneratorContext(), hdManagement.get());
		}
	}
}