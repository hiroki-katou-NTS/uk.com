package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHist;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 計算式履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_FORMULA_HIST")
public class QpbmtFormulaHist extends UkJpaEntity implements Serializable {
    /**
     * ID
     */
    @EmbeddedId
    public QpbmtFormulaHistPk formulaHistPk;
    /**
     * 開始日
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;

    /**
     * 終了日
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

    @Override
    protected Object getKey() {
        return formulaHistPk;
    }

   /* public static List<FormulaHist> toDomain(List<QpbmtFormulaHist> entity) {

        return new FormulaHist(this.formulaHistPk.cid, this.formulaHistPk.formulaCode, this.formulaName, this.settingMethod, this.nestedAtr);
    }
    public static QpbmtFormula toEntity(Formula domain) {
        return new QpbmtFormula(new QpbmtFormulaPk(domain.getCompanyId(), domain.getFormulaCode().v()),domain.getFormulaName().v(), domain.getSettingMethod().value, domain.getNestedAtr().map(i->i.value).orElse(null));
    }*/
}
