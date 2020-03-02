package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.MasterCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisSala;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StatementCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 明細書紐付け履歴（給与分類）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_COR_HIS_SAL")
public class QpbmtStateCorHisSal extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtStateCorHisSalPk stateCorHisSalPk;

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
    protected Object getKey() {
        return stateCorHisSalPk;
    }

    public StateCorreHisSala toDomain(List<YearMonthHistoryItem> history) {
        return new StateCorreHisSala(this.stateCorHisSalPk.cid, history);
    }

    public StateLinkSetMaster toDomain() {
        return new StateLinkSetMaster(this.stateCorHisSalPk.hisId,
                this.stateCorHisSalPk.masterCode,
                this.salaryCode,
                this.bonusCode);
    }

    public static List<YearMonthHistoryItem> toDomainYearMonth(List<QpbmtStateCorHisSal> entity){

        List<YearMonthHistoryItem> history = new ArrayList<>();
        if(entity == null || entity.isEmpty())
            return history;

        return entity.stream().map(item ->{
            return new YearMonthHistoryItem(
                    item.stateCorHisSalPk.hisId,
                    new YearMonthPeriod(new YearMonth(item.startYearMonth),new YearMonth(item.endYearMonth)));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
    }

    public static List<QpbmtStateCorHisSal> toEntity(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth) {
        List<QpbmtStateCorHisSal> listStateCorHisSal = new ArrayList<>();
        if (stateLinkSetMasters == null || stateLinkSetMasters.isEmpty()) {
            return listStateCorHisSal;
        }
        listStateCorHisSal = stateLinkSetMasters.stream().map(item -> new QpbmtStateCorHisSal(new QpbmtStateCorHisSalPk(cid, item.getHistoryID(), item.getMasterCode().v()),
                startYearMonth,
                endYearMonth,
                item.getSalaryCode().isPresent() ? item.getSalaryCode().get().v() : null,
                item.getBonusCode().isPresent() ? item.getBonusCode().get().v() : null))
                .collect(Collectors.toList());
        return listStateCorHisSal;
    }

    public static List<StateLinkSetMaster> toDomainSetting(List<QpbmtStateCorHisSal> entitys) {
        return entitys.stream().map(QpbmtStateCorHisSal::toDomain).collect(Collectors.toList());
    }

}
