package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_DETAIL_CAL_FORMULA")
public class QpbmtDetailCalculationFormula {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtDetailCalculationFormulaPk detailCalculationFormulaPk;

    /**
     * 計算式要素
     */
    @Basic(optional = false)
    @Column(name = "FORMULA_ELEMENT")
    public String formulaElement;

    public static List<QpbmtDetailCalculationFormula> toEntity(String formulaCode, DetailFormulaSetting domain) {
        return domain.getDetailCalculationFormula().stream().map(item -> {
            return new QpbmtDetailCalculationFormula(new QpbmtDetailCalculationFormulaPk(AppContexts.user().companyId(), formulaCode, domain.getHistoryId(), item.getElementOrder()), item.getFormulaElement().v());
        }).collect(Collectors.toList());
    }
}
