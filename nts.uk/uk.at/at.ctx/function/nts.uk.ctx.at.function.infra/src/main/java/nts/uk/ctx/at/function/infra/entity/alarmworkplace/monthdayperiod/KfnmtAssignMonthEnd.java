package nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractFromStartMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyEndMonth;
import nts.uk.ctx.at.function.dom.alarmworkplace.month.EndMonth;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtWkpCheckCondition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

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
        return new EndMonth(specifyEndMonth,
            Optional.of(new MonthNo(EnumAdaptor.valueOf(monthPrevious, PreviousClassification.class), monthNo, curentMonth))
        );
    }

    public void fromEntity(KfnmtAssignMonthEnd newEntity) {
        this.contractCode = AppContexts.user().contractCode();
        this.specifyEndMonth = newEntity.specifyEndMonth;
        this.monthNo = newEntity.monthNo;
        this.curentMonth = newEntity.curentMonth;
        this.monthPrevious = newEntity.monthPrevious;
    }

    public static KfnmtAssignMonthEnd toEntity(EndMonth domain, String patternCD, int category) {

        KfnmtAssignMonthEnd entity = new KfnmtAssignMonthEnd();
        entity.pk = new KfnmtAssignMonthEndPk(AppContexts.user().companyId(), patternCD, category);
        entity.contractCode = AppContexts.user().contractCode();
        entity.specifyEndMonth = domain.getSpecifyEndMonth().value;

        if (domain.getEndMonthNo().isPresent()) {
            entity.monthNo = domain.getEndMonthNo().get().getMonthNo();
            entity.curentMonth = domain.getEndMonthNo().get().isCurentMonth();
            entity.monthPrevious = domain.getEndMonthNo().get().getMonthPrevious().value;
        }
        return entity;
    }

}
