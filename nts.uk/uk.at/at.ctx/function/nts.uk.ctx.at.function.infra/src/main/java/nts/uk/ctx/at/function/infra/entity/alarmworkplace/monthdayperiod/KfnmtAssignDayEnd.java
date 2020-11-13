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
 * entity : 日数指定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ASSIGN_DAY_END")
public class KfnmtAssignDayEnd extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtAssignDayEndPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

    @Column(name = "ASSIGN_WAY_DAY_END")
    public int endSpecify;

    @Column(name = "NUMOF_DAY")
    public int dayNo;

    @Column(name = "THIS_DAY")
    public boolean curentDay;

    @Column(name = "BEFORE_AFTER_ATR")
    public int previous;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    @OneToOne(mappedBy = "kfnmtAssignDayEnd", orphanRemoval = true)
    public KfnmtWkpCheckCondition checkCondition;

    public EndDate toDomain() {
        EndDate endDate = new EndDate(this.endSpecify);
            endDate.setEndDay(EnumAdaptor.valueOf(previous, PreviousClassification.class),dayNo,curentDay);
        return endDate;
    }

    public void fromEntity(KfnmtAssignDayEnd newEntity) {
        this.contractCode = AppContexts.user().contractCode();
        this.endSpecify = newEntity.endSpecify;
        this.dayNo = newEntity.dayNo;
        this.curentDay = newEntity.curentDay;
        this.previous = newEntity.previous;
    }

    public static KfnmtAssignDayEnd toEntity(EndDate domain, String patternCD, int category) {

        KfnmtAssignDayEnd entity = new KfnmtAssignDayEnd();
        entity.pk = new KfnmtAssignDayEndPk(AppContexts.user().companyId(), patternCD, category);
        entity.contractCode = AppContexts.user().contractCode();
        entity.endSpecify = domain.getEndSpecify().value;
        if (domain.getEndDays().isPresent()) {
            entity.dayNo = domain.getEndDays().get().getDay().v();
            entity.curentDay = domain.getEndDays().get().isMakeToDay();
            entity.previous = domain.getEndDays().get().getDayPrevious().value;
        }
        return entity;
    }

}
