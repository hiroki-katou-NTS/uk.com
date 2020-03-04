package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicFormulaSetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* かんたん計算式設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_BASIC_FORMULA_SET")
public class QpbmtBasicFormulaSetting extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtBasicFormulaSettingPk basicFormulaSetPk;

    /**
     * マスタ分岐利用
     */
    @Basic(optional = false)
    @Column(name = "MASTER_BRANCH_USE")
    public int masterBranchUse;

    /**
    * 使用マスタ
    */
    @Basic(optional = true)
    @Column(name = "MASTER_USE")
    public Integer masterUse;
    
    @Override
    protected Object getKey()
    {
        return basicFormulaSetPk;
    }

    public BasicFormulaSetting toDomain() {
        return new BasicFormulaSetting(this.basicFormulaSetPk.formulaCode, this.basicFormulaSetPk.historyID, this.masterBranchUse, this.masterUse);
    }
    public static QpbmtBasicFormulaSetting toEntity(BasicFormulaSetting domain) {
        QpbmtBasicFormulaSetting entity = new QpbmtBasicFormulaSetting(new QpbmtBasicFormulaSettingPk(AppContexts.user().companyId(), domain.getFormulaCode().v(), domain.getHistoryID()), domain.getMasterBranchUse().value, domain.getMasterUse().map(i->i.value).orElse(null));
        if (entity.masterBranchUse == 0) entity.masterUse = null;
        return entity;
    }
}
