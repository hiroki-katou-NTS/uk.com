package nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfo;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Column(name = "START_YM")
    public int startYM;

    /**
     * 年月期間の汎用履歴項目.終了日
     */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int endYM;

    @Override
    protected Object getKey() {
        return empRsdtTaxPayeePk;
    }

    public static List<EmployeeResidentTaxPayeeInfo> toDomain(List<QpbmtEmpRsdtTaxPayee> entitys) {
        List<EmployeeResidentTaxPayeeInfo> domains = new ArrayList<>();
        Map<String, List<QpbmtEmpRsdtTaxPayee>> mapEntitys = entitys.stream()
                .collect(Collectors.groupingBy(i -> i.empRsdtTaxPayeePk.sid));
        for (Map.Entry<String, List<QpbmtEmpRsdtTaxPayee>> map : mapEntitys.entrySet()) {
            String sid = map.getKey();
            List<YearMonthHistoryItem> historyItems = new ArrayList<>();
            for (QpbmtEmpRsdtTaxPayee entity : map.getValue()) {
                historyItems.add(new YearMonthHistoryItem(entity.empRsdtTaxPayeePk.histId,
                        new YearMonthPeriod(new YearMonth(entity.startYM), new YearMonth(entity.endYM))));
            }
            domains.add(new EmployeeResidentTaxPayeeInfo(sid, historyItems));
        }
        return domains;
    }

    public static List<QpbmtEmpRsdtTaxPayee> toEntity(List<EmployeeResidentTaxPayeeInfo> domains) {
        List<QpbmtEmpRsdtTaxPayee> entitys = new ArrayList<>();
        for (EmployeeResidentTaxPayeeInfo domain : domains) {
            for (YearMonthHistoryItem histItem : domain.items()) {
                entitys.add(new QpbmtEmpRsdtTaxPayee(new QpbmtEmpRsdtTaxPayeePk(domain.getSid(), histItem.identifier()),
                        histItem.start().v(), histItem.end().v()));
            }
        }
        return entitys;
    }

}
