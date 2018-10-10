package nts.uk.ctx.pr.shared.infra.entity.payrollgeneralpurposeparameters;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaIdentification;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与汎用パラメータ識別
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SAL_GEN_PARAM_IDENT")
public class QpbmtSalGenParamIdent extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSalGenParamIdentPk salGenParamIdentPk;
    
    /**
    * 名称
    */
    @Basic(optional = false)
    @Column(name = "NAME")
    public String name;
    
    /**
    * 属性区分
    */
    @Basic(optional = false)
    @Column(name = "ATTRIBUTE_TYPE")
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
    @Column(name = "EXPLANATION")
    public String explanation;
    
    @Override
    protected Object getKey()
    {
        return salGenParamIdentPk;
    }

    public SalGenParaIdentification toDomain() {
        return new SalGenParaIdentification(this.salGenParamIdentPk.paraNo, this.salGenParamIdentPk.cid, this.name, this.attributeType, this.historyAtr, this.explanation);
    }
    public static QpbmtSalGenParamIdent toEntity(SalGenParaIdentification domain) {
        return new QpbmtSalGenParamIdent(new QpbmtSalGenParamIdentPk(domain.getParaNo(), domain.getCID()),domain.getName(),domain.getAttributeType().value, domain.getHistoryAtr().value, domain.getExplanation().get());
    }

}
