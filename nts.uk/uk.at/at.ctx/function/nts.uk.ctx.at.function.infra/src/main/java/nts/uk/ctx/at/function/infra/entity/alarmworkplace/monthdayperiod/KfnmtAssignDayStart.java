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
 * entity : 日数指定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ASSIGN_DAY_START")
public class KfnmtAssignDayStart extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtAssignDayStartPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

    @Column(name = "ASSIGN_WAY_DAY_START")
    public int startSpecify;

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

    @OneToOne(mappedBy = "kfnmtAssignDayStart", orphanRemoval = true)
    public KfnmtWkpCheckCondition checkCondition;

    public StartDate toDomain() {
        StartDate startDate = new StartDate(this.startSpecify);
        startDate.setStartDay(EnumAdaptor.valueOf(previous, PreviousClassification.class), dayNo, curentDay);
        return startDate;
    }

    public void fromEntity(KfnmtAssignDayStart newEntity) {
        this.contractCode = AppContexts.user().contractCode();
        this.startSpecify = newEntity.startSpecify;
        this.dayNo = newEntity.dayNo;
        this.curentDay = newEntity.curentDay;
        this.previous = newEntity.previous;
    }

    public static KfnmtAssignDayStart toEntity(StartDate domain, String patternCD, int category) {

        KfnmtAssignDayStart entity = new KfnmtAssignDayStart();
        entity.pk = new KfnmtAssignDayStartPk(AppContexts.user().companyId(), patternCD, category);
        entity.contractCode = AppContexts.user().contractCode();
        entity.startSpecify = domain.getStartSpecify().value;
        if (domain.getStrDays().isPresent()) {
            entity.dayNo = domain.getStrDays().get().getDay().v();
            entity.curentDay = domain.getStrDays().get().isMakeToDay();
            entity.previous = domain.getStrDays().get().getDayPrevious().value;
        }
        return entity;
    }

}
