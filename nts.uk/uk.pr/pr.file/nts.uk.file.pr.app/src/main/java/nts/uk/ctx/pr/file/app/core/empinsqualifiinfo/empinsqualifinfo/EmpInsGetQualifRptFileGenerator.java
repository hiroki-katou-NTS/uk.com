package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface EmpInsGetQualifRptFileGenerator {
    void generate(FileGeneratorContext fileContext, List<EmpInsGetQualifReport> data);
}
