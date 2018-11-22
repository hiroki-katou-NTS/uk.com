package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHist;
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
 * 計算式履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_FORMULA_HIST")
public class QpbmtFormulaHist extends UkJpaEntity implements Serializable {
    /**
     * ID
     */
    @EmbeddedId
    public QpbmtFormulaHistPk formulaHistPk;
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
        return formulaHistPk;
    }

    public static List<FormulaHist> toDomain(List<QpbmtFormulaHist> entitys) {
        List<FormulaHist> domains = new ArrayList<>();
        Map<String, List<QpbmtFormulaHist>> mapEntitys = entitys.stream()
                .collect(Collectors.groupingBy(i -> i.formulaHistPk.formulaCode));
        for (Map.Entry<String, List<QpbmtFormulaHist>> map : mapEntitys.entrySet()) {
            String cid = map.getValue().get(0).formulaHistPk.cid;
            String formulaCode = map.getKey();
            List<YearMonthHistoryItem> historyItems = new ArrayList<>();
            for (QpbmtFormulaHist entity : map.getValue()) {
                historyItems.add(new YearMonthHistoryItem(entity.formulaHistPk.histId,
                        new YearMonthPeriod(new YearMonth(entity.startYearMonth), new YearMonth(entity.endYearMonth))));
            }
            domains.add(new FormulaHist(cid, formulaCode, historyItems));
        }
        return domains;
    }

    public static List<QpbmtFormulaHist> toEntity(List<FormulaHist> domains) {
        List<QpbmtFormulaHist> entitys = new ArrayList<>();
        for (FormulaHist domain : domains) {
            for (YearMonthHistoryItem histItem : domain.items()) {
                entitys.add(new QpbmtFormulaHist(new QpbmtFormulaHistPk(domain.getCid(), domain.getFormulaCode().v(),
                        histItem.identifier()), histItem.start().v(), histItem.end().v()));
            }
        }
        return entitys;
    }

}
