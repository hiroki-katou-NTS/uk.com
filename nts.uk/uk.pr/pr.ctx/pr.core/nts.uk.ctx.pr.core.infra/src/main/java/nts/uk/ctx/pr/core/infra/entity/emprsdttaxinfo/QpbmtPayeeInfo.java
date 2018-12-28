package nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.PayeeInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 納付先情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PAYEE_INFO")
public class QpbmtPayeeInfo extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtPayeeInfoPk payeeInfoPk;

    /**
     * 住民税納付先
     */
    @Basic(optional = false)
    @Column(name = "RESIDENT_TAX_PAYEE_CD")
    public String residentTaxPayeeCd;

    @Override
    protected Object getKey() {
        return payeeInfoPk;
    }

    public PayeeInfo toDomain() {
        return new PayeeInfo(this.payeeInfoPk.histId, this.residentTaxPayeeCd);
    }

    public static QpbmtPayeeInfo toEntity(PayeeInfo domain) {
        return new QpbmtPayeeInfo(new QpbmtPayeeInfoPk(domain.getHistId()), domain.getResidentTaxPayeeCd().v());
    }

}
