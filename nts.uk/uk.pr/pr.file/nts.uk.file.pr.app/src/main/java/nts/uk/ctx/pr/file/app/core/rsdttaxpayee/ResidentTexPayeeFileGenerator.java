package nts.uk.ctx.pr.file.app.core.rsdttaxpayee;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;


public interface ResidentTexPayeeFileGenerator {
    void generate(FileGeneratorContext fileContext, List<ResidentTexPayeeExportData> data);
}