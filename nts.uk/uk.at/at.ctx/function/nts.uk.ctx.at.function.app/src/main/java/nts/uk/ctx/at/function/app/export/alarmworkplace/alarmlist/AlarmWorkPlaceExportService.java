package nts.uk.ctx.at.function.app.export.alarmworkplace.alarmlist;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist.ExtractAlarmListWorkPlaceFinder;
import nts.uk.ctx.at.function.dom.alarmworkplace.export.AlarmListExtractResultWorkplaceData;
import nts.uk.ctx.at.function.dom.alarmworkplace.export.AlarmListWorkPlaceGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AlarmWorkPlaceExportService extends ExportService<AlarmWorkPlaceExportData> {

    @Inject
    private AlarmListWorkPlaceGenerator generator;
    @Inject
    private ExtractAlarmListWorkPlaceFinder extractAlarmListWorkPlaceFinder;

    @Override
    protected void handle(ExportServiceContext<AlarmWorkPlaceExportData> context) {
        AlarmWorkPlaceExportData data = context.getQuery();

        List<AlarmListExtractResultWorkplaceData> results = extractAlarmListWorkPlaceFinder.getExtractResult(data.getProcessId());
        // invoke generator
        this.generator.generateExcelScreen(context.getGeneratorContext(), results, data.getAlarmPatternCode(), data.getAlarmPatternName());
    }
}
