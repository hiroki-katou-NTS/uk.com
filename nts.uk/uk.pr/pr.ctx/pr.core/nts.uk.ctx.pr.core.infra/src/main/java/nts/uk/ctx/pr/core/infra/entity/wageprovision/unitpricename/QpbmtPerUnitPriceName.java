package nts.uk.ctx.pr.core.infra.entity.wageprovision.unitpricename;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceName;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与個人単価名称
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PER_UNIT_PRICE_NAME")
public class QpbmtPerUnitPriceName extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtPerUnitPriceNamePk perUnitPriceNamePk;
    
    /**
    * 名称
    */
    @Basic(optional = false)
    @Column(name = "NAME")
    public String name;
    
    /**
    * 廃止区分
    */
    @Basic(optional = false)
    @Column(name = "ABOLITION")
    public int abolition;
    
    /**
    * 略名
    */
    @Basic(optional = false)
    @Column(name = "SHORT_NAME")
    public String shortName;
    
    /**
    * 統合コード
    */
    @Basic(optional = false)
    @Column(name = "INTEGRATION_CODE")
    public String integrationCode;
    
    /**
    * メモ
    */
    @Basic(optional = true)
    @Column(name = "NOTE")
    public String note;
    
    @Override
    protected Object getKey()
    {
        return perUnitPriceNamePk;
    }

    public SalaryPerUnitPriceName toDomain() {
        return new SalaryPerUnitPriceName(this.perUnitPriceNamePk.cid, this.perUnitPriceNamePk.code, this.name, this.abolition, this.shortName, this.integrationCode, this.note);
    }
    public static QpbmtPerUnitPriceName toEntity(SalaryPerUnitPriceName domain) {
        return new QpbmtPerUnitPriceName(new QpbmtPerUnitPriceNamePk(domain.getCid(), domain.getCode().v()),domain.getName().v(), domain.getAbolition().value, domain.getShortName().v(), domain.getIntegrationCode().v(), domain.getNote().map(i->i.v()).orElse(null));
    }

}
