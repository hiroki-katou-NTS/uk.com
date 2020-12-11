package nts.uk.ctx.at.function.app.export.alarmworkplace.alarmlist;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.function.dom.alarmworkplace.export.AlarmListWorkPlaceGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AlarmWorkPlaceExportService extends ExportService<AlarmWorkPlaceExportData> {

	@Inject
	private AlarmListWorkPlaceGenerator generator;

	@Override
	protected void handle(ExportServiceContext<AlarmWorkPlaceExportData> context) {
		AlarmWorkPlaceExportData data = context.getQuery();
		// invoke generator
		this.generator.generateExcelScreen(context.getGeneratorContext(), data.getData(), data.getAlarmCode(),data.getAlarmName());
	}
}
