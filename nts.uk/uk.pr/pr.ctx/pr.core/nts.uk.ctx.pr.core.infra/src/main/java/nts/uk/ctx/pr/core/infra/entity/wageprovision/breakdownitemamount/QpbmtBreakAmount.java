package nts.uk.ctx.pr.core.infra.entity.wageprovision.breakdownitemamount;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmount;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 給与内訳個人金額履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_BREAK_AMOUNT")
public class QpbmtBreakAmount extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtBreakAmountPk breakAmountPk;

    /**
     * 金額
     */
    @Basic(optional = false)
    @Column(name = "BREAKDOWN_ITEM_AMOUNT")
    public long breakdownItemAmount;

    @Override
    protected Object getKey() {
        return breakAmountPk;
    }

    public static List<QpbmtBreakAmount> toEntity(String cid, String sid, int categoryAtr, String itemNameCode, int salaryBonusAtr, BreakdownAmount domain) {
        return domain.getBreakdownAmountList().stream().map(x ->
                new QpbmtBreakAmount(new QpbmtBreakAmountPk(cid,
                        sid,
                        categoryAtr,
                        itemNameCode,
                        salaryBonusAtr,
                        domain.getHistoryId(),
                        x.getBreakdownItemCode().v()),
                        x.getAmount().v()))
                .collect(Collectors.toList());
    }
}
