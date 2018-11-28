package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StatementCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

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
        return stateCorHisIndiPk;
    }

    public StateCorreHisIndivi toDomain(List<YearMonthHistoryItem> history ) {
        return new StateCorreHisIndivi(this.stateCorHisIndiPk.empId ,history);
    }

    public StateLinkSetIndivi toDomain() {
        return new StateLinkSetIndivi(this.stateCorHisIndiPk.hisId, new StatementCode(this.salaryCode), new StatementCode(this.bonusCode));
    }
    public static QpbmtStateCorHisIndi toEntity(String empID,  YearMonthHistoryItem history, String salaryLayoutCode, String bonusLayoutCode) {
        return new QpbmtStateCorHisIndi(new QpbmtStateCorHisIndiPk(empID, history.identifier()), history.start().v(), history.end().v(),salaryLayoutCode,bonusLayoutCode);
    }

}
