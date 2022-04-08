package nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.columnwidthsetting;

import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting.AnyPeriodCorrectionGridColumnWidthRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * グリッド列幅を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AnyPeriodCorrectionGridColumnWidthQuery {
    @Inject
    private AnyPeriodCorrectionGridColumnWidthRepository repo;

    public List<AnyPeriodCorrectionColumnWidthDto> getGridColWidths() {
        return repo.get(AppContexts.user().employeeId()).map(setting -> {
            return setting.getColumnWidths().stream().map(i -> new AnyPeriodCorrectionColumnWidthDto(i.getAttendanceItemId(), i.getColumnWidth())).collect(Collectors.toList());
        }).orElse(Collections.emptyList());
    }
}
