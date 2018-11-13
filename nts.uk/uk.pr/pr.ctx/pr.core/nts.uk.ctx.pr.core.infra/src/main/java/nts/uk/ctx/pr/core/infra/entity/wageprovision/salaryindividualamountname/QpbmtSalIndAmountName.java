package nts.uk.ctx.pr.core.infra.entity.wageprovision.salaryindividualamountname;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountName;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 給与個人別金額名称
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SAL_IND_AMOUNT_NAME")
public class QpbmtSalIndAmountName extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtSalIndAmountNamePk salIndAmountNamePk;


    /**
     * 個人金額名称
     */
    @Basic(optional = false)
    @Column(name = "INDIVIDUAL_PRICE_NAME")
    public String individualPriceName;

    @Override
    protected Object getKey() {
        return salIndAmountNamePk;
    }

    public SalIndAmountName toDomain() {
        return new SalIndAmountName(this.salIndAmountNamePk.cid, this.salIndAmountNamePk.individualPriceCode, this.salIndAmountNamePk.cateIndicator, this.individualPriceName);
    }

    public static QpbmtSalIndAmountName toEntity(SalIndAmountName domain) {
        return new QpbmtSalIndAmountName(new QpbmtSalIndAmountNamePk(domain.getCId(), domain.getIndividualPriceCode().v(),domain.getCateIndicator().value),  domain.getIndividualPriceName().v());
    }

}
