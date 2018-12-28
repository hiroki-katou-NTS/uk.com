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
    * 給与賞与区分
    */
    @Basic(optional = false)
    @Column(name = "SALARY_BONUS_ATR")
    public int salaryBonusAtr;
    
    /**
    * 期間
    */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 期間
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
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
                                domain.getCategoryAtr().value,
                                domain.getItemNameCd().v(),
                                domain.getEmployeeId(),
                                x.identifier()),
                        domain.getSalaryBonusAtr().value,
                        x.start().v(),
                        x.end().v()
                        )).collect(Collectors.toList());
        return data;
    }
}
