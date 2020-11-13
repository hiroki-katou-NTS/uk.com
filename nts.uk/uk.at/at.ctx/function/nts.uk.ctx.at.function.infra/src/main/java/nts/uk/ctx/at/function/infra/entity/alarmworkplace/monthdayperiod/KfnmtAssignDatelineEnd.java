package nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtWkpCheckCondition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * entity : 締め日指定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ASSIGDEADLINE_END")
public class KfnmtAssignDatelineEnd extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtAssignDatelineEndPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

    @Column(name = "ASSIGN_WAY_DAY_END")
    public int endSpecify;

    @Column(name = "NUMOF_MON")
    public int monthNo;

    @Column(name = "THIS_MON")
    public boolean curentMonth;

    @Column(name = "BEFORE_AFTER_ATR")
    public int previous;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    //class month

    @OneToOne(mappedBy = "kfnmtAssignDatelineEnd", orphanRemoval = true)
    public KfnmtWkpCheckCondition checkCondition;

    public EndDate toDomain() {
        EndDate endDate = new EndDate(this.endSpecify);
        endDate.setEndMonth(EnumAdaptor.valueOf(previous, PreviousClassification.class), monthNo,curentMonth);
        return endDate;
    }

    public void fromEntity(KfnmtAssignDatelineEnd newEntity) {
        this.contractCode = AppContexts.user().contractCode();
        this.endSpecify = newEntity.endSpecify;
        this.monthNo = newEntity.monthNo;
        this.curentMonth = newEntity.curentMonth;
        this.previous = newEntity.previous;
    }

    public static KfnmtAssignDatelineEnd toEntity(EndDate domain, String patternCD, int category) {

        KfnmtAssignDatelineEnd entity = new KfnmtAssignDatelineEnd();
        entity.pk = new KfnmtAssignDatelineEndPk(AppContexts.user().companyId(), patternCD, category);
        entity.contractCode = AppContexts.user().contractCode();
        entity.endSpecify = domain.getEndSpecify().value;
        if (domain.getEndMonth().isPresent()) {
            entity.monthNo = domain.getEndMonth().get().getMonth();
            entity.curentMonth = domain.getEndMonth().get().isCurentMonth();
            entity.previous = domain.getEndMonth().get().getMonthPrevious().value;
        }
        return entity;
    }


}
