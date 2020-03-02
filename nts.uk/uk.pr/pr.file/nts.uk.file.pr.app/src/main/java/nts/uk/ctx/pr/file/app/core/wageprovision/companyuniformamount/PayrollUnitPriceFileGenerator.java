package nts.uk.ctx.pr.file.app.core.wageprovision.companyuniformamount;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;


public interface PayrollUnitPriceFileGenerator {
    void generate(FileGeneratorContext fileContext, List<Object[]> exportData, String companyName);
}
