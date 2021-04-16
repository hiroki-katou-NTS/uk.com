package nts.uk.ctx.at.function.dom.alarmworkplace.export;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface AlarmListWorkPlaceGenerator {

    void generateExcelScreen(FileGeneratorContext generatorContext,
                             List<AlarmListExtractResultWorkplaceData> dataSource,
                             String alarmPatternCode, String alarmPattern);

}
