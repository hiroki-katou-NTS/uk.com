package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisPosition;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 明細書紐付け履歴（職位）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_COR_HIS_POS")
public class QpbmtStateCorHisPos extends UkJpaEntity implements Serializable
{
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
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;
    
    @Override
    protected Object getKey()
    {
        return stateCorHisPosPk;
    }

    public StateCorrelationHisPosition toDomain(List<YearMonthHistoryItem> history) {
        return new StateCorrelationHisPosition(this.stateCorHisPosPk.cid, history);
    }
    public static QpbmtStateCorHisPos toEntity(String cid,  YearMonthHistoryItem history) {
        return new QpbmtStateCorHisPos(new QpbmtStateCorHisPosPk(cid, history.identifier()),history.start().v(), history.end().v());
    }

}
