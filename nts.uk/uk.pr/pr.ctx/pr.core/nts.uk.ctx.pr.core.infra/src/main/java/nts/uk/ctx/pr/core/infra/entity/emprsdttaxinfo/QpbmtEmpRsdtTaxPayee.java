package nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.GenericHistYMPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 社員住民税納付先情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_RSDT_TAX_PAYEE")
public class QpbmtEmpRsdtTaxPayee extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpRsdtTaxPayeePk empRsdtTaxPayeePk;

    /**
     * 年月期間の汎用履歴項目.開始日
     */
    @Basic(optional = false)
    @Column(name = "START_DATE")
    public int startDate;

    /**
     * 年月期間の汎用履歴項目.終了日
     */
    @Basic(optional = false)
    @Column(name = "END_DATE")
    public int endDate;

    @Override
    protected Object getKey() {
        return empRsdtTaxPayeePk;
    }

    public EmployeeResidentTaxPayeeInfo toDomain() {
        return new EmployeeResidentTaxPayeeInfo(this.empRsdtTaxPayeePk.sid, this.empRsdtTaxPayeePk.histId,
                this.startDate, this.endDate);
    }

    public static QpbmtEmpRsdtTaxPayee toEntity(EmployeeResidentTaxPayeeInfo domain) {
        GenericHistYMPeriod histItem = domain.getHistoryItem();
        return new QpbmtEmpRsdtTaxPayee(new QpbmtEmpRsdtTaxPayeePk(domain.getSid(), histItem.getHistoryID()),
                histItem.getPeriodYearMonth().start().v(), histItem.getPeriodYearMonth().end().v());
    }

}
