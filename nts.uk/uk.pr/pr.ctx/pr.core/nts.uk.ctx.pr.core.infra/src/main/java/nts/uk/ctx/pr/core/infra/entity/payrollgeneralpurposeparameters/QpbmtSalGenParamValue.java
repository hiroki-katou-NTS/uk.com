package nts.uk.ctx.pr.core.infra.entity.payrollgeneralpurposeparameters;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValue;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

/**
* 給与汎用給与汎用パラメータ識別パラメータ値
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SAL_GEN_PARAM_VALUE")
public class QpbmtSalGenParamValue extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSalGenParamValuePk salGenParamValuePk;
    
    /**
    * 有効区分
    */
    @Basic(optional = false)
    @Column(name = "AVAILABLE_ATR")
    public int availableAtr;
    
    /**
    * 値（数値）
    */
    @Basic(optional = true)
    @Column(name = "NUMBER_VALUE")
    public String numberValue;
    
    /**
    * 値（文字）
    */
    @Basic(optional = true)
    @Column(name = "CHARACTER_VALUE")
    public String characterValue;
    
    /**
    * 値（時間）
    */
    @Basic(optional = true)
    @Column(name = "TIME_VALUE")
    public Integer timeValue;
    
    /**
    * 対象区分
    */
    @Basic(optional = true)
    @Column(name = "TARGET_ATR")
    public Integer targetAtr;
    /**
     * 選択肢
     */
    @Basic(optional = true)
    @Column(name = "SELECTION")
    public Integer selection;
    
    @Override
    protected Object getKey()
    {
        return salGenParamValuePk;
    }

    public SalGenParaValue toDomain() {
        return new SalGenParaValue(this.salGenParamValuePk.hisId, this.selection, this.availableAtr, this.numberValue, this.characterValue, this.timeValue, this.targetAtr);
    }
    public static QpbmtSalGenParamValue toEntity(SalGenParaValue domain) {
        return new QpbmtSalGenParamValue(new QpbmtSalGenParamValuePk(domain.getHistoryId()),
                domain.getAvailableAtr().value,
                domain.getNumValue().map(i->i.v()).orElse(null),
                domain.getCharValue().map(i->i.v()).orElse(null),
                domain.getTimeValue().map(i -> {
                    return i.v();
                }).orElse(Optional.of(0).get()), domain.getTargetAtr().map(i->i.value).orElse(null),
                domain.getSelection().get());
    }

}
