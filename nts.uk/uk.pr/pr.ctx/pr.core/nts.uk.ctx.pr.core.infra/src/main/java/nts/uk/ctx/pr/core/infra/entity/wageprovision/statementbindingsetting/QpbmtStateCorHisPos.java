package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisPo;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（職位）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_COR_HIS_POS")
public class QpbmtStateCorHisPos extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateCorHisPosPk stateCorHisPosPk;

    
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
     * マスタ基準日
     */
    @Basic(optional = false)
    @Column(name = "JOB_BASE_DATE")
    public GeneralDate baseDate;

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
        return stateCorHisPosPk;
    }

    public StateCorreHisPo toDomain(List<YearMonthHistoryItem> history) {
        return new StateCorreHisPo(this.stateCorHisPosPk.cid,history);
    }

    public StateLinkSetMaster toDomain() {
        return new StateLinkSetMaster(this.stateCorHisPosPk.hisId,
                this.stateCorHisPosPk.masterCode,
                this.salaryCode,
                this.bonusCode);
    }
    public static Optional<StateLinkSetDate> toBaseDate(Object[] resultQuery){
        GeneralDate date = GeneralDate.fromString(resultQuery[1].toString(), "yyyy-MM-dd");
        StateLinkSetDate stateLinkSetDate = new StateLinkSetDate(resultQuery[0].toString(), date);
        return Optional.of(stateLinkSetDate);
    }
    public static List<YearMonthHistoryItem> toDomainYearMonth(List<QpbmtStateCorHisPos> entity){

        List<YearMonthHistoryItem> history = new ArrayList<>();
        if(entity == null || entity.isEmpty())
            return history;

        return entity.stream().map(item ->{
            return new YearMonthHistoryItem(
                    item.stateCorHisPosPk.hisId,
                    new YearMonthPeriod(new YearMonth(item.startYearMonth),new YearMonth(item.endYearMonth)));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
    }

    public static List<QpbmtStateCorHisPos> toEntity(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate) {
        List<QpbmtStateCorHisPos> listStateCorHisPos = new ArrayList<>();
        if(stateLinkSetMasters == null || stateLinkSetMasters.isEmpty()){
            return listStateCorHisPos;
        }
        listStateCorHisPos = stateLinkSetMasters.stream().map(item -> new QpbmtStateCorHisPos(new QpbmtStateCorHisPosPk(cid,item.getHistoryID(),item.getMasterCode().v()),
                startYearMonth,
                endYearMonth,
                baseDate,
                item.getSalaryCode().isPresent() ? item.getSalaryCode().get().v() : null,
                item.getBonusCode().isPresent() ? item.getBonusCode().get().v() : null))
                .collect(Collectors.toList());
        return listStateCorHisPos;
    }

    public static List<StateLinkSetMaster> toDomainSetting(List<QpbmtStateCorHisPos> entitys) {
        return entitys.stream().map(QpbmtStateCorHisPos::toDomain).collect(Collectors.toList());
    }

}
