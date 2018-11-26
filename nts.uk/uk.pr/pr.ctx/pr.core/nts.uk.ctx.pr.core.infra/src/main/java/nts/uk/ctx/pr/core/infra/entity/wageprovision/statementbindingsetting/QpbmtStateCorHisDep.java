package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 明細書紐付け履歴（部門）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_COR_HIS_DEP")
public class QpbmtStateCorHisDep extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateCorHisDepPk stateCorHisDepPk;

    /**
    * 開始年月
    */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

    /**
     * マスタ基準日
     */
    @Basic(optional = false)
    @Column(name = "BASE_DATE")
    public GeneralDate baseDate;

    /**
     * 給与明細書
     */
    @Basic(optional = true)
    @Column(name = "SALARY")
    public String salaryCode;

    /**
     * 賞与明細書
     */
    @Basic(optional = true)
    @Column(name = "BONUS")
    public String bonusCode;
    
    @Override
    protected Object getKey()
    {
        return stateCorHisDepPk;
    }

    public StateCorreHisDepar toDomain(List<YearMonthHistoryItem> history) {
        return new StateCorreHisDepar(this.stateCorHisDepPk.cid, history);
    }

    public StateLinkSetMaster toDomain() {
        return new StateLinkSetMaster(this.stateCorHisDepPk.hisId,
                new MasterCode(this.stateCorHisDepPk.masterCode),
                this.salaryCode == null ? null : new StatementCode(this.salaryCode),
                this.bonusCode == null ? null : new StatementCode(this.bonusCode));
    }

    public static List<YearMonthHistoryItem> toDomainYearMonth(List<QpbmtStateCorHisDep> entity){

        List<YearMonthHistoryItem> history = new ArrayList<>();
        if(entity == null || entity.isEmpty())
            return history;

        return entity.stream().map(item ->{
            return new YearMonthHistoryItem(
                    item.stateCorHisDepPk.hisId,
                    new YearMonthPeriod(new YearMonth(item.startYearMonth),new YearMonth(item.endYearMonth)));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
    }

    public static Optional<StateLinkSetDate> getBaseDate(List<QpbmtStateCorHisDep> entity){
        if(entity == null || entity.isEmpty())
            return Optional.empty();
        StateLinkSetDate stateLinkSetDate = new StateLinkSetDate(entity.get(0).stateCorHisDepPk.hisId,entity.get(0).baseDate);
        return Optional.of(stateLinkSetDate);
    }
    public static QpbmtStateCorHisDep toEntity(String cid, YearMonthHistoryItem history, String masterCode, GeneralDate baseDate, String salaryLayoutCode, String bonusLayoutCode) {
        return new QpbmtStateCorHisDep(new QpbmtStateCorHisDepPk(cid, history.identifier(),masterCode),
                history.start().v(),
                history.end().v(),
                baseDate,
                salaryLayoutCode,
                bonusLayoutCode);
    }

    public static List<QpbmtStateCorHisDep> toEntity(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate) {
        List<QpbmtStateCorHisDep> listStateCorHisDep = new ArrayList<>();
        if(stateLinkSetMasters == null || stateLinkSetMasters.isEmpty()){
            return listStateCorHisDep;
        }
        listStateCorHisDep = stateLinkSetMasters.stream().map(item -> new QpbmtStateCorHisDep(new QpbmtStateCorHisDepPk(cid,item.getHistoryID(),item.getMasterCode().v()),
                startYearMonth,
                endYearMonth,
                baseDate,
                item.getSalaryCode().isPresent() ? item.getSalaryCode().get().v() : null,
                item.getBonusCode().isPresent() ? item.getBonusCode().get().v(): null))
                .collect(Collectors.toList());
        return listStateCorHisDep;
    }

}
