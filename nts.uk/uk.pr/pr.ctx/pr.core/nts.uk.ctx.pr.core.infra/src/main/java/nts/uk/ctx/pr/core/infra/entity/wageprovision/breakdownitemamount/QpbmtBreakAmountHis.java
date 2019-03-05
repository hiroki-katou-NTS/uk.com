package nts.uk.ctx.pr.core.infra.entity.wageprovision.breakdownitemamount;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountHis;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* 給与内訳個人金額履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_BREAK_AMOUNT_HIS")
public class QpbmtBreakAmountHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtBreakAmountHisPk breakAmountHisPk;


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
    
    @Override
    protected Object getKey()
    {
        return breakAmountHisPk;
    }

    public static List<QpbmtBreakAmountHis> toEntity(BreakdownAmountHis domain) {
        List<QpbmtBreakAmountHis> data =  domain.getPeriod().stream().map
                (x -> new QpbmtBreakAmountHis(
                        new QpbmtBreakAmountHisPk(
                                domain.getCid(),
                                domain.getEmployeeId(),
                                domain.getCategoryAtr().value,
                                domain.getItemNameCd().v(),
                                domain.getSalaryBonusAtr().value,
                                x.identifier()),
                        x.start().v(),
                        x.end().v()
                        )).collect(Collectors.toList());
        return data;
    }
}
