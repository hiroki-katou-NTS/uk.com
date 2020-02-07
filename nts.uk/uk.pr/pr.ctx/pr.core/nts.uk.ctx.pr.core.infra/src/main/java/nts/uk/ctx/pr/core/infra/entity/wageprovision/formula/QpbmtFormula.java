package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 計算式
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_FORMULA")
public class QpbmtFormula extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtFormulaPk formulaPk;
    /**
    * 計算式名
    */
    @Basic(optional = false)
    @Column(name = "FORMULA_NAME")
    public String formulaName;
    
    /**
    * 計算式の設定方法
    */
    @Basic(optional = false)
    @Column(name = "SETTING_METHOD")
    public int settingMethod;
    
    /**
    * 入れ子利用区分
    */
    @Basic(optional = true)
    @Column(name = "NESTED_ATR")
    public Integer nestedAtr;
    
    @Override
    protected Object getKey()
    {
        return formulaPk;
    }

    public Formula toDomain() {
        return new Formula(this.formulaPk.cid, this.formulaPk.formulaCode, this.formulaName, this.settingMethod, this.nestedAtr);
    }
    public static QpbmtFormula toEntity(Formula domain) {
        return new QpbmtFormula(new QpbmtFormulaPk(AppContexts.user().companyId(), domain.getFormulaCode().v()), domain.getFormulaName().v(), domain.getSettingMethod().value, domain.getNestedAtr().map(i->i.value).orElse(null));
    }

    public QpbmtFormula(QpbmtFormulaPk formulaPk) {
        this.formulaPk = formulaPk;
    }

}
