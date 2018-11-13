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
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
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
        List<YearMonthHistoryItem> period = entites.stream().map(v -> new YearMonthHistoryItem(v.empSalPriHisPk.historyId, new YearMonthPeriod(new YearMonth(v.startYearMonth), new YearMonth(v.endYearMonth)))).collect(Collectors.toList());
        return new EmployeeSalaryUnitPriceHistory(personalUnitPriceCode, employeeId, period);
    }

    public static List<EmployeeSalaryUnitPriceHistory> toDomains(List<QpbmtEmpSalPriHis> entites) {
        List<EmployeeSalaryUnitPriceHistory> domains=new ArrayList<>();
        List<YearMonthHistoryItem> arrHis=new ArrayList<>();

        for (int i = 1; i < entites.size()-1; i++) {
            String perValUnitCode = entites.get(0).empSalPriHisPk.personalUnitPriceCode;
            String employeeId = entites.get(i).empSalPriHisPk.employeeId;
            YearMonthHistoryItem yearMonthHistoryItem=new YearMonthHistoryItem(entites.get(i).empSalPriHisPk.employeeId,new YearMonthPeriod(new YearMonth(entites.get(i).startYearMonth),new YearMonth(entites.get(i).endYearMonth)));
            arrHis.add(yearMonthHistoryItem);
            if(employeeId.equals(entites.get(i+1).empSalPriHisPk.employeeId)){
                domains.add(new EmployeeSalaryUnitPriceHistory(perValUnitCode,employeeId,arrHis));
                arrHis=new ArrayList<>();
            }
        }
        return domains;
    }


    public static List<QpbmtEmpSalPriHis> toEntity(EmployeeSalaryUnitPriceHistory domain) {
        val employeeId = domain.getEmployeeID();
        val personalUnitPriceCode = domain.getPersonalUnitPriceCode();
        return domain.items().stream().map(item -> new QpbmtEmpSalPriHis(
                new QpbmtEmpSalPriHisPk(personalUnitPriceCode.v(), employeeId, item.identifier()),
                item.start().v(),
                item.end().v(),
                new BigDecimal(0)
        )).collect(Collectors.toList());
    }

}
