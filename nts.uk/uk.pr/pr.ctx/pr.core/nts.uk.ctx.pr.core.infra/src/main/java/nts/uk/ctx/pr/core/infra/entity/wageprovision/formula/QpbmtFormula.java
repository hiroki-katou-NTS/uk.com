package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 計算式
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_FORMULA")
public class QpbmtFormula extends UkJpaEntity implements Serializable
{
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
        return new QpbmtFormula(new QpbmtFormulaPk(domain.getCompanyId(), domain.getFormulaCode().v()),domain.getFormulaName().v(), domain.getSettingMethod().value, domain.getNestedAtr().map(i->i.value).orElse(null));
    }

}
