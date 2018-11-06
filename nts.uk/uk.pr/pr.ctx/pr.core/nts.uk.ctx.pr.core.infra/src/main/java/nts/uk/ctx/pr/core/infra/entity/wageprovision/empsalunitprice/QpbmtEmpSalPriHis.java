package nts.uk.ctx.pr.core.infra.entity.wageprovision.empsalunitprice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.val;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistory;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.GenericHistYMPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 社員給与単価履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_SAL_PRI_HIS")
public class QpbmtEmpSalPriHis extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpSalPriHisPk empSalPriHisPk;

    /**
     * 年月開始
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;

    /**
     * 年月終了
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

    /**
     * 個人単価
     */
    @Basic(optional = false)
    @Column(name = "INDVIDUAL_UNIT_PRICE")
    public BigDecimal indvidualUnitPrice;

    @Override
    protected Object getKey() {
        return empSalPriHisPk;
    }

    public static EmployeeSalaryUnitPriceHistory toDomain(List<QpbmtEmpSalPriHis> entites) {
        val personalUnitPriceCode = entites.get(0).empSalPriHisPk.personalUnitPriceCode;
        val employeeId = entites.get(0).empSalPriHisPk.employeeId;
        List<GenericHistYMPeriod> period = entites.stream().map(v -> new GenericHistYMPeriod(v.empSalPriHisPk.historyId, v.startYearMonth, v.endYearMonth, v.indvidualUnitPrice)).collect(Collectors.toList());
        return new EmployeeSalaryUnitPriceHistory(personalUnitPriceCode, employeeId, period);
    }

    public static List<QpbmtEmpSalPriHis> toEntity(EmployeeSalaryUnitPriceHistory domain) {
        List<QpbmtEmpSalPriHis> qpbmtEmpSalPriHis = new ArrayList<>();
        val employeeId = domain.getEmployeeID();
        val personalUnitPriceCode = domain.getPersonalUnitPriceCode();
        return domain.getPeriod().stream().map(v ->
                new QpbmtEmpSalPriHis(
                        new QpbmtEmpSalPriHisPk(personalUnitPriceCode.v(), employeeId, v.getHistoryID()),
                        v.getPeriodYearMonth().getStartYearMonth(),
                        v.getPeriodYearMonth().getEndYearMonth(),
                        v.getIndividualUnitPrice().v())).collect(Collectors.toList());
    }

}
