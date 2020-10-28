package nts.uk.file.at.app.export.outputitemsofworkstatustable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.OutPutWorkStatusContent;


public interface DisplayWorkStatusReportGenerator {
    void generate(FileGeneratorContext generatorContext, OutPutWorkStatusContent dataSource);
}
