package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHist;
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
 * 賃金テーブル履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WAGE_TABLE_HIST")
public class QpbmtWageTableHist extends UkJpaEntity implements Serializable {
    /**
     * ID
     */
    @EmbeddedId
    public QpbmtWageTableHistPk wageTblHistPk;
    /**
     * 開始日
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;

    /**
     * 終了日
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

    @Override
    protected Object getKey() {
        return wageTblHistPk;
    }

    public static List<WageTableHist> toDomain(List<QpbmtWageTableHist> entitys) {
        List<WageTableHist> domains = new ArrayList<>();
        Map<String, List<QpbmtWageTableHist>> mapEntitys = entitys.stream()
                .collect(Collectors.groupingBy(i -> i.wageTblHistPk.wageTblCode));
        for (Map.Entry<String, List<QpbmtWageTableHist>> map : mapEntitys.entrySet()) {
            String cid = map.getValue().get(0).wageTblHistPk.cid;
            String formulaCode = map.getKey();
            List<YearMonthHistoryItem> historyItems = new ArrayList<>();
            for (QpbmtWageTableHist entity : map.getValue()) {
                historyItems.add(new YearMonthHistoryItem(entity.wageTblHistPk.histId,
                        new YearMonthPeriod(new YearMonth(entity.startYearMonth), new YearMonth(entity.endYearMonth))));
            }
            domains.add(new WageTableHist(cid, formulaCode, historyItems));
        }
        return domains;
    }

    public static List<QpbmtWageTableHist> toEntity(List<WageTableHist> domains) {
        List<QpbmtWageTableHist> entitys = new ArrayList<>();
        for (WageTableHist domain : domains) {
            for (YearMonthHistoryItem histItem : domain.items()) {
                entitys.add(new QpbmtWageTableHist(new QpbmtWageTableHistPk(domain.getCid(), domain.getWageTableCode().v(),
                        histItem.identifier()), histItem.start().v(), histItem.end().v()));
            }
        }
        return entitys;
    }

}
