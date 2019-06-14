package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisCom;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetCom;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StatementCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 明細書紐付け履歴（会社）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_COR_HIS_COM")
public class QpbmtStateCorHisCom extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtStateCorHisComPk stateCorHisComPk;

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
        return stateCorHisComPk;
    }

    public StateCorreHisCom toDomain(List<YearMonthHistoryItem> history) {
        return new StateCorreHisCom(this.stateCorHisComPk.cid, history);
    }

    public StateLinkSetCom toDomain() {
        return new StateLinkSetCom(this.stateCorHisComPk.hisId,
                this.salaryCode == null ? null : new StatementCode(this.salaryCode),
                this.bonusCode == null ? null : new StatementCode(this.bonusCode));
    }

    public static QpbmtStateCorHisCom toEntity(String cid, YearMonthHistoryItem history, String salaryLayoutCode, String bonusLayoutCode) {
        return new QpbmtStateCorHisCom(new QpbmtStateCorHisComPk(cid, history.identifier()),
                history.start().v(), history.end().v(),
                salaryLayoutCode == null ? null : salaryLayoutCode,
                bonusLayoutCode == null ? null : bonusLayoutCode);
    }
}
