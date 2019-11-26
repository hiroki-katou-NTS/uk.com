package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SociInsuStanDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 社会保険基準年月日
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SOCI_INSU_STAN_DATE")
public class QpbmtSociInsuStanDate extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSociInsuStanDatePk sociInsuStanDatePk;
    
    /**
    * 基準月
    */
    @Basic(optional = false)
    @Column(name = "BASE_MONTH")
    public int baseMonth;
    
    /**
    * 基準年
    */
    @Basic(optional = false)
    @Column(name = "BASE_YEAR")
    public int baseYear;
    
    /**
    * 基準日
    */
    @Basic(optional = false)
    @Column(name = "REFE_DATE")
    public int refeDate;
    
    @Override
    protected Object getKey()
    {
        return sociInsuStanDatePk;
    }

    public SociInsuStanDate toDomain() {
        return new SociInsuStanDate(this.baseMonth, this.baseYear, this.refeDate);
    }
    public static QpbmtSociInsuStanDate toEntity(SociInsuStanDate domain) {
        return new QpbmtSociInsuStanDate(new QpbmtSociInsuStanDatePk(), domain.getSociInsuBaseMonth().value, domain.getSociInsuBaseYear().value, domain.getSociInsuRefeDate().value);
    }

}
