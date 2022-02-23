package nts.uk.ctx.at.function.infra.repository.anyperiodcorrection.formatsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormat;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormatRepository;
import nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting.KfnmtAnpFormDefault;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAnyPeriodCorrectionRegulationFormatRepository extends JpaRepository implements AnyPeriodCorrectionDefaultFormatRepository {
    @Override
    public Optional<AnyPeriodCorrectionDefaultFormat> get(String companyId) {
        return this.queryProxy().find(companyId, KfnmtAnpFormDefault.class).map(e -> {
            return new AnyPeriodCorrectionDefaultFormat(e.code);
        });
    }

    @Override
    public void insert(AnyPeriodCorrectionDefaultFormat format) {
        this.commandProxy().insert(new KfnmtAnpFormDefault(AppContexts.user().companyId(), format.getCode().v()));
    }

    @Override
    public void update(AnyPeriodCorrectionDefaultFormat format) {
        this.queryProxy().find(AppContexts.user().companyId(), KfnmtAnpFormDefault.class).ifPresent(e -> {
            e.code = format.getCode().v();
            this.commandProxy().update(e);
        });
    }

    @Override
    public void delete(String companyId) {
        this.commandProxy().remove(KfnmtAnpFormDefault.class, companyId);
    }
}
