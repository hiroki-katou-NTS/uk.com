package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailCalculationFormula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 詳細計算式
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_DETAIL_FORMULA_SET")
public class QpbmtDetailFormulaSetting extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtDetailFormulaSettingPk detailFormulaSetPk;
    
    /**
    * 参照月
    */
    @Basic(optional = false)
    @Column(name = "REFERENCE_MONTH")
    public int referenceMonth;
    
    /**
    * 端数処理(詳細計算式)
    */
    @Basic(optional = false)
    @Column(name = "ROUNDING_METHOD")
    public int roundingMethod;
    
    /**
    * 端数処理位置
    */
    @Basic(optional = false)
    @Column(name = "ROUNDING_POSITION")
    public int roundingPosition;
    
    /**
    * 計算式要素
    */
    @Basic(optional = false)
    @Column(name = "FORMULA_ELEMENT")
    public String formulaElement;
    
    @Override
    protected Object getKey()
    {
        return detailFormulaSetPk;
    }

    public static Optional<DetailFormulaSetting> toDomain(List<QpbmtDetailFormulaSetting> entities) {
        if (entities.isEmpty()) return Optional.empty();
        return Optional.of(new DetailFormulaSetting(entities.get(0).detailFormulaSetPk.historyID, entities.get(0).referenceMonth, entities.stream().map(entity -> new DetailCalculationFormula(entity.detailFormulaSetPk.elementOrder, entity.formulaElement)).collect(Collectors.toList()), entities.get(0).roundingMethod, entities.get(0).roundingPosition));
    }

    public static List<QpbmtDetailFormulaSetting> toEntity(DetailFormulaSetting domain) {
        // check if no detail calculation
        // it will never become true as design but check to prevent from insert abnormal data
        if (domain.getDetailCalculationFormula().isEmpty()){
            I18NText errorText = I18NText.main("Data is invalid").build();
            throw new BusinessException(errorText);
        }
        return domain.getDetailCalculationFormula().stream().map(item -> new QpbmtDetailFormulaSetting(new QpbmtDetailFormulaSettingPk(domain.getHistoryId(), item.getElementOrder()), domain.getReferenceMonth().value, domain.getRoundingMethod().value, domain.getRoundingPosition().value, item.getFormulaElement().v())).collect(Collectors.toList());
    }
}
