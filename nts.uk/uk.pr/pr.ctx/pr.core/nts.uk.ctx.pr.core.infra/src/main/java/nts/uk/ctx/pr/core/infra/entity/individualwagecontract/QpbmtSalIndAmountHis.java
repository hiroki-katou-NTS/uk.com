package nts.uk.ctx.pr.core.infra.entity.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 給与個人別金額履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SAL_IND_AMOUNT_HIS")
public class QpbmtSalIndAmountHis extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtSalIndAmountHisPk salIndAmountHisPk;

    /**
     * カテゴリ区分
     */
    @Basic(optional = false)
    @Column(name = "CATE_INDICATOR")
    public int cateIndicator;

    /**
     * 期間
     */
    @Basic(optional = false)
    @Column(name = "PERIOD_START_YM")
    public Integer periodStartYm;

    /**
     * 期間
     */
    @Basic(optional = false)
    @Column(name = "PERIOD_END_YM")
    public Integer periodEndYm;

    /**
     * 給与賞与区分
     */
    @Basic(optional = false)
    @Column(name = "SAL_BONUS_CATE")
    public int salBonusCate;

    @Override
    protected Object getKey() {
        return salIndAmountHisPk;
    }

    public SalIndAmountHis toDomain(List<QpbmtSalIndAmountHis> entity) {
        val perValCode = entity.get(0).salIndAmountHisPk.perValCode;
        val empId = entity.get(0).salIndAmountHisPk.empId;
        val cateIndicator = entity.get(0).cateIndicator;
        List<GenericHistYMPeriod> period = entity.stream().map(item -> new GenericHistYMPeriod(item.salIndAmountHisPk.historyId, item.periodStartYm, item.periodEndYm)).collect(Collectors.toList());
        val salBonusCate = entity.get(0).salBonusCate;
        return new SalIndAmountHis(perValCode, empId, cateIndicator, period, salBonusCate);
    }

    public static QpbmtSalIndAmountHis toEntity(SalIndAmountHis domain) {
        return new QpbmtSalIndAmountHis(
                new QpbmtSalIndAmountHisPk(
                        domain.getPeriod().get(0).getHistoryID(),
                        domain.getPerValCode(),
                        domain.getEmpId()
                ),
                domain.getCateIndicator().value,
                domain.getPeriod().get(0).getPeriodYearMonth().start().v(),
                domain.getPeriod().get(0).getPeriodYearMonth().end().v(),
                domain.getSalBonusCate().value);
    }

}
