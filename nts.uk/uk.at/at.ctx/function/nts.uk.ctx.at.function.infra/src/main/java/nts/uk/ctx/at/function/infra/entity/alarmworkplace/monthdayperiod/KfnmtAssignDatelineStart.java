package nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;
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
@Table(name = "KFNMT_ASSIGDEADLINE_START")
public class KfnmtAssignDatelineStart extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtAssignDatelineStartPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

    @Column(name = "ASSIGN_WAY_DAY_START")
    public int startSpecify;

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

    @OneToOne(mappedBy = "kfnmtAssignDatelineStart", orphanRemoval = true)
    public KfnmtWkpCheckCondition checkCondition;

    public StartDate toDomain() {
        StartDate startDate = new StartDate(this.startSpecify);
        startDate.setStartMonth(EnumAdaptor.valueOf(previous, PreviousClassification.class), monthNo, curentMonth);
        return startDate;
    }

    public void fromEntity(KfnmtAssignDatelineStart newEntity) {
        this.contractCode = AppContexts.user().contractCode();
        this.startSpecify = newEntity.startSpecify;
        this.monthNo = newEntity.monthNo;
        this.curentMonth = newEntity.curentMonth;
        this.previous = newEntity.previous;
    }

    public static KfnmtAssignDatelineStart toEntity(StartDate domain, String patternCD, int category) {

        KfnmtAssignDatelineStart entity = new KfnmtAssignDatelineStart();
        entity.pk = new KfnmtAssignDatelineStartPk(AppContexts.user().companyId(), patternCD, category);
        entity.contractCode = AppContexts.user().contractCode();
        entity.startSpecify = domain.getStartSpecify().value;
        if (domain.getStrMonth().isPresent()) {
            entity.monthNo = domain.getStrMonth().get().getMonth();
            entity.curentMonth = domain.getStrMonth().get().isCurentMonth();
            entity.previous = domain.getStrMonth().get().getMonthPrevious().value;
        }
        return entity;
    }


}
