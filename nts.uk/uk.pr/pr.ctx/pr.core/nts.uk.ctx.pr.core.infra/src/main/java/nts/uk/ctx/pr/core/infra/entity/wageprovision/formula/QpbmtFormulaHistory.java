package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaCode;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * かんたん計算式
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_FORMULA_HISTORY")
public class QpbmtFormulaHistory extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtFormulaHistoryPk formulaHistoryPk;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public int startMonth;

    /**
     * 計算式コード
     */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int endMonth;

    @Override
    protected Object getKey()
    {
        return formulaHistoryPk;
    }

    public static Optional<FormulaHistory> toDomain(List<QpbmtFormulaHistory> entities) {
        if (entities.isEmpty()) return Optional.empty();
        return Optional.of(new FormulaHistory(entities.get(0).formulaHistoryPk.cid, new FormulaCode(entities.get(0).formulaHistoryPk.formulaCode), entities.stream().map(entity -> new YearMonthHistoryItem(entity.formulaHistoryPk.historyID,  new YearMonthPeriod(new YearMonth(entity.startMonth), new YearMonth(entity.endMonth)))).collect(Collectors.toList())));
    }
    public static List<QpbmtFormulaHistory> toEntity(FormulaHistory domain) {
        return domain.getHistory().stream().map(item -> new QpbmtFormulaHistory(new QpbmtFormulaHistoryPk(AppContexts.user().companyId(), domain.getFormulaCode().v(), item.identifier()), item.start().v(), item.end().v())).collect(Collectors.toList());
    }

    public static List<FormulaHistory> toDomainFromList(List<QpbmtFormulaHistory> entities) {
        List<FormulaHistory> domains = new ArrayList<>();
        Map<String, List<QpbmtFormulaHistory>> mapEntities = entities.stream()
                .collect(Collectors.groupingBy(i -> i.formulaHistoryPk.formulaCode));
        for (Map.Entry<String, List<QpbmtFormulaHistory>> map : mapEntities.entrySet()) {
            String cid = map.getValue().get(0).formulaHistoryPk.cid;
            String formulaCode = map.getKey();
            List<YearMonthHistoryItem> historyItems = new ArrayList<>();
            for (QpbmtFormulaHistory entity : map.getValue()) {
                historyItems.add(new YearMonthHistoryItem(entity.formulaHistoryPk.historyID,
                        new YearMonthPeriod(new YearMonth(entity.startMonth), new YearMonth(entity.endMonth))));
            }
            domains.add(new FormulaHistory(cid, new FormulaCode(formulaCode), historyItems));
        }
        return domains;
    }
}
