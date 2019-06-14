package nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaIdentification;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 給与汎用パラメータ識別
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_SAL_GEN_PR_IDENT")
public class QqsmtSalGenParamIdent extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtSalGenParamIdentPk salGenParamIdentPk;
    
    /**
    * 名称
    */
    @Basic(optional = false)
    @Column(name = "PARA_NAME")
    public String name;
    
    /**
    * 属性区分
    */
    @Basic(optional = false)
    @Column(name = "DATATYPE_ATR")
    public int attributeType;
    
    /**
    * 履歴区分
    */
    @Basic(optional = false)
    @Column(name = "HISTORY_ATR")
    public int historyAtr;
    
    /**
    * 補足説明
    */
    @Basic(optional = true)
    @Column(name = "NOTE")
    public String explanation;
    
    @Override
    protected Object getKey()
    {
        return salGenParamIdentPk;
    }

    public SalGenParaIdentification toDomain() {
        return new SalGenParaIdentification(this.salGenParamIdentPk.paraNo, this.salGenParamIdentPk.cid, this.name, this.attributeType, this.historyAtr, this.explanation);
    }
    public static QqsmtSalGenParamIdent toEntity(SalGenParaIdentification domain) {
        return new QqsmtSalGenParamIdent(new QqsmtSalGenParamIdentPk(domain.getParaNo().v(), domain.getCID()),domain.getName().v(),domain.getAttributeType().value, domain.getHistoryAtr().value, domain.getExplanation().get());
    }

}
