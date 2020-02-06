package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.MasterCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisEm;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
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
 * 明細書紐付け履歴（雇用）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_COR_HIS_EMP")
public class QpbmtStateCorHisEmp extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtStateCorHisEmpPk stateCorHisEmpPk;

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
        return stateCorHisEmpPk;
    }

    public StateCorreHisEm toDomain(List<YearMonthHistoryItem> history) {
        return new StateCorreHisEm(this.stateCorHisEmpPk.cid, history);
    }

    public StateLinkSetMaster toDomain() {
        return new StateLinkSetMaster(this.stateCorHisEmpPk.hisId,
                this.stateCorHisEmpPk.masterCode,
                this.salaryCode,
                this.bonusCode);
    }

    public static List<YearMonthHistoryItem> toDomainYearMonth(List<QpbmtStateCorHisEmp> entity){

        List<YearMonthHistoryItem> history = new ArrayList<>();
        if(entity == null || entity.isEmpty())
            return history;

        return entity.stream().map(item ->{
            return new YearMonthHistoryItem(
                    item.stateCorHisEmpPk.hisId,
                    new YearMonthPeriod(new YearMonth(item.startYearMonth),new YearMonth(item.endYearMonth)));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
    }



    public static List<QpbmtStateCorHisEmp> toEntity(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth) {
        List<QpbmtStateCorHisEmp> listStateCorHisEmp = new ArrayList<>();
        if (stateLinkSetMasters == null || stateLinkSetMasters.isEmpty()) {
            return listStateCorHisEmp;
        }
        listStateCorHisEmp = stateLinkSetMasters.stream().map(item -> new QpbmtStateCorHisEmp(new QpbmtStateCorHisEmpPk(cid, item.getHistoryID(), item.getMasterCode().v()),
                startYearMonth, endYearMonth,
                item.getSalaryCode().isPresent() ? item.getSalaryCode().get().v() : null,
                item.getBonusCode().isPresent() ? item.getBonusCode().get().v() : null))
                .collect(Collectors.toList());
        return listStateCorHisEmp;
    }

    public static List<StateLinkSetMaster> toDomainSetting(List<QpbmtStateCorHisEmp> entitys) {
        return entitys.stream().map(QpbmtStateCorHisEmp::toDomain).collect(Collectors.toList());
    }

}
