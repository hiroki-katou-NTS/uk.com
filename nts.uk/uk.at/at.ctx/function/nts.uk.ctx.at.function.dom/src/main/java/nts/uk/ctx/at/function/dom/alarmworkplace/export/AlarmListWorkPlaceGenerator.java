package nts.uk.ctx.at.function.dom.alarmworkplace.export;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.dto.AlarmListExtractResultWorkplaceDto;

import java.util.List;

public interface AlarmListWorkPlaceGenerator {

	 void generateExcelScreen(FileGeneratorContext generatorContext, List<AlarmListExtractResultWorkplaceData> dataSource,String alarmCode,String alarmName);

}
