package nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SalaryPerUnitExportService extends ExportService<SalaryPreUnitExportQuery> {

    @Inject
    private SalaryPerUnitFileGenerator mSalaryPerUnitFileGenerator;

    @Inject
    private SalaryPerUnitPriceRepository mSalaryPerUnitPriceRepository;

    @Override
    protected void handle(ExportServiceContext<SalaryPreUnitExportQuery> exportServiceContext) {
        List<SalaryPerUnitPrice> mSalaryPerUnitPrices =  mSalaryPerUnitPriceRepository.getAllSalaryPerUnitPrice()
                .stream()
                .sorted(Comparator.comparing(o -> o.getSalaryPerUnitPriceName().getCode()))
                .collect(Collectors.toList());
        mSalaryPerUnitFileGenerator.generate(exportServiceContext.getGeneratorContext(),mSalaryPerUnitPrices
                .stream()
                .map(e -> {
                    return new SalaryPerUnitSetExportData(
                            e.getSalaryPerUnitPriceName().getCid(),
                            e.getSalaryPerUnitPriceName().getCode(),
                            e.getSalaryPerUnitPriceName().getName(),
                            e.getSalaryPerUnitPriceName().getAbolition(),
                            e.getSalaryPerUnitPriceName().getShortName(),
                            e.getSalaryPerUnitPriceName().getIntegrationCode(),
                            e.getSalaryPerUnitPriceName().getNote(),
                            e.getSalaryPerUnitPriceSetting().getUnitPriceType(),
                            e.getSalaryPerUnitPriceSetting().getFixedWage().getSettingAtr(),
                            e.getSalaryPerUnitPriceSetting().getFixedWage().getEveryoneEqualSet(),
                            e.getSalaryPerUnitPriceSetting().getFixedWage().getPerSalaryContractType()
                    );
                })
        .collect(Collectors.toList())
        );
    }
}