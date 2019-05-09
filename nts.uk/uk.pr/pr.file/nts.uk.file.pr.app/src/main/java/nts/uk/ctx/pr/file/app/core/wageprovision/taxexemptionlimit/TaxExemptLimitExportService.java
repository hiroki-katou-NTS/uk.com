package nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimit;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimitRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TaxExemptLimitExportService extends ExportService<TaxExemptLimitExportQuery> {

    @Inject
    private TaxExemptLimitFileGenerator mTaxExemptLimitFileGenerator;

    @Inject
    private TaxExemptionLimitRepository mTaxExemptionLimitRepository;

    @Override
    protected void handle(ExportServiceContext<TaxExemptLimitExportQuery> exportServiceContext) {
        List<TaxExemptionLimit> mTaxExemptionLimits = mTaxExemptionLimitRepository.getTaxExemptLimitByCompanyId(AppContexts.user().companyId())
                .stream()
                .sorted(Comparator.comparing(TaxExemptionLimit::getTaxFreeAmountCode))
                .collect(Collectors.toList());
        mTaxExemptLimitFileGenerator.generate(exportServiceContext.getGeneratorContext(),
                mTaxExemptionLimits.stream()
                        .map(e -> {
                            return new TaxExemptLimitSetExportData(e.getCid(), e.getTaxFreeAmountCode(), e.getTaxExemptionName(), e.getTaxExemption());
                        })
                        .collect(Collectors.toList())
        );
    }
}
