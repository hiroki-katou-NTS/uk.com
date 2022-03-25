package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting;

import java.util.Optional;

public interface AnyPeriodCorrectionDefaultFormatRepository {
    Optional<AnyPeriodCorrectionDefaultFormat> get(String companyId);

    void insert(AnyPeriodCorrectionDefaultFormat format);

    void update(AnyPeriodCorrectionDefaultFormat format);

    void delete(String companyId);
}
