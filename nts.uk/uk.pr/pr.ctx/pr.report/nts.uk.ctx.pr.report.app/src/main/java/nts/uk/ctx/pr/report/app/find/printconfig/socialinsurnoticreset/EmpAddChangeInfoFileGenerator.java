package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface EmpAddChangeInfoFileGenerator {
    void generate(FileGeneratorContext fileContext, EmpAddChangeInformation data);
}
