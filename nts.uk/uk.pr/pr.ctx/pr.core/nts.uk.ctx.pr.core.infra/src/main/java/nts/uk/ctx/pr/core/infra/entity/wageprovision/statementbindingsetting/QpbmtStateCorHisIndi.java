package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StatementCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（個人）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_COR_HIS_INDI")
public class QpbmtStateCorHisIndi extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateCorHisIndiPk stateCorHisIndiPk;
    
    /**
    * 開始年月
    */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public int startYearMonth;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int endYearMonth;

    /**
     * 給与明細書
     */
    @Basic(optional = true)
    @Column(name = "S_STATEMENT_CD")
    public String salaryCode;

    /**
     * 賞与明細書
     */
    @Basic(optional = true)
    @Column(name = "B_STATEMENT_CD")
    public String bonusCode;
    
    @Override
    protected Object getKey()
    {
        return stateCorHisIndiPk;
    }

    public StateCorreHisIndivi toDomain(List<YearMonthHistoryItem> history ) {
        return new StateCorreHisIndivi(this.stateCorHisIndiPk.empId ,history);
    }

    public StateLinkSetIndivi toDomain() {
        return new StateLinkSetIndivi(this.stateCorHisIndiPk.hisId, this.salaryCode, this.bonusCode);
    }
    public static QpbmtStateCorHisIndi toEntity(String empID,  YearMonthHistoryItem history, String salaryLayoutCode, String bonusLayoutCode) {
        return new QpbmtStateCorHisIndi(new QpbmtStateCorHisIndiPk(empID, history.identifier()),
                history.start().v(),
                history.end().v(),
                salaryLayoutCode == null ? null : salaryLayoutCode,
                bonusLayoutCode == null? null : bonusLayoutCode);
    }


    public static List<StateCorreHisIndivi> toDomainHistory(List<QpbmtStateCorHisIndi> entitys) {
        List<StateCorreHisIndivi> domains = new ArrayList<>();
        Map<String, List<QpbmtStateCorHisIndi>> mapEntitys = entitys.stream()
                .collect(Collectors.groupingBy(i -> i.stateCorHisIndiPk.empId));
        for (Map.Entry<String, List<QpbmtStateCorHisIndi>> map : mapEntitys.entrySet()) {
            String sid = map.getKey();
            List<YearMonthHistoryItem> historyItems = new ArrayList<>();
            for (QpbmtStateCorHisIndi entity : map.getValue()) {
                historyItems.add(new YearMonthHistoryItem(entity.stateCorHisIndiPk.hisId,
                        new YearMonthPeriod(new YearMonth(entity.startYearMonth), new YearMonth(entity.endYearMonth))));
            }
            domains.add(new StateCorreHisIndivi(sid, historyItems));
        }
        return domains;
    }

    public static List<StateLinkSetIndivi> toDomainSetting(List<QpbmtStateCorHisIndi> entitys) {
        return entitys.stream().map(QpbmtStateCorHisIndi::toDomain).collect(Collectors.toList());
    }
}
