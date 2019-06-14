package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_DETAIL_CAL_FORMULA")
public class QpbmtDetailCalculationFormula extends UkJpaEntity implements Serializable {
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

    public static List<QpbmtDetailCalculationFormula> toEntity(DetailFormulaSetting domain) {
        return domain.getDetailCalculationFormula().stream().map(item -> {
            return new QpbmtDetailCalculationFormula(new QpbmtDetailCalculationFormulaPk(AppContexts.user().companyId(), domain.getFormulaCode().v(), domain.getHistoryId(), item.getElementOrder()), item.getFormulaElement().v());
        }).collect(Collectors.toList());
    }

    @Override
    protected Object getKey() {
        return detailCalculationFormulaPk;
    }
}
