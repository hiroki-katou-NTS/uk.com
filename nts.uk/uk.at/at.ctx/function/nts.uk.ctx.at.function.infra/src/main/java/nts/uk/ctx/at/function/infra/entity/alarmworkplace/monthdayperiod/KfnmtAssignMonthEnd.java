package nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.EndMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractFromStartMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyEndMonth;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtWkpCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * entity : 月数指定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ASSIGN_MON_END")
public class KfnmtAssignMonthEnd extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtAssignMonthEndPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

    @Column(name = "ASSIGN_WAY_MON_END")
    public int specifyEndMonth;

    @Column(name = "NUMOF_MON")
    public int monthNo;

    @Column(name = "THIS_MON")
    public boolean curentMonth;

    @Column(name = "BEFORE_AFTER_ATR")
    public int monthPrevious;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    @OneToOne(mappedBy = "kfnmtAssignMonthEnd", orphanRemoval = true)
    public KfnmtWkpCheckCondition checkCondition;

    public EndMonth toDomain() {

        EndMonth endMonth = new EndMonth(EnumAdaptor.valueOf(specifyEndMonth, SpecifyEndMonth.class),
            EnumAdaptor.valueOf(1, ExtractFromStartMonth.class),
            new MonthNo(EnumAdaptor.valueOf(monthPrevious, PreviousClassification.class),monthNo,curentMonth));

        return endMonth;

    }

}
