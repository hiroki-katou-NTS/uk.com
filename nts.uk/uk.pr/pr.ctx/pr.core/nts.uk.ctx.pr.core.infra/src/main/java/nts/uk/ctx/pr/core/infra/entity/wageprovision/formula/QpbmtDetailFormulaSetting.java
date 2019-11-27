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
    
    @Override
    protected Object getKey()
    {
        return detailFormulaSetPk;
    }

    public static Optional<DetailFormulaSetting> toDomain(QpbmtDetailFormulaSetting detailFormulaSet, List<QpbmtDetailCalculationFormula> detailCalculationFormula) {
        return Optional.of(new DetailFormulaSetting(detailFormulaSet.detailFormulaSetPk.formulaCode, detailFormulaSet.detailFormulaSetPk.historyID, detailFormulaSet.referenceMonth, detailCalculationFormula.stream().map(entity -> new DetailCalculationFormula(entity.detailCalculationFormulaPk.elementOrder, entity.formulaElement)).collect(Collectors.toList()), detailFormulaSet.roundingMethod, detailFormulaSet.roundingPosition));
    }

    public static QpbmtDetailFormulaSetting toEntity(String formulaCode, DetailFormulaSetting domain) {
        return new QpbmtDetailFormulaSetting(new QpbmtDetailFormulaSettingPk(AppContexts.user().companyId(), formulaCode, domain.getHistoryId()), domain.getReferenceMonth().value, domain.getRoundingMethod().value, domain.getRoundingPosition().value);
    }
}
