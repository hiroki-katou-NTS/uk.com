package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface NotifiOfChangInNameInsPerExFileGenerator {
    void generate(FileGeneratorContext fileContext, List<NotifiOfChangInNameInsPerExportData> data);

}
