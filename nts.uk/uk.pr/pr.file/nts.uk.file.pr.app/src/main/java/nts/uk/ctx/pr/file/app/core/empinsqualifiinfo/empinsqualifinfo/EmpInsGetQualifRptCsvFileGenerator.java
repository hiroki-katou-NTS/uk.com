package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface EmpInsGetQualifRptCsvFileGenerator {
    void generate(FileGeneratorContext fileContext, ExportDataCsv data);
}
