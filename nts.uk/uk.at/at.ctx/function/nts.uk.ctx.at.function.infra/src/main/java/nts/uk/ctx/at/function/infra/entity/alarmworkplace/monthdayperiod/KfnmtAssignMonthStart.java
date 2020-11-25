package nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarmworkplace.month.StartMonth;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtWkpCheckCondition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

/**
 * entity : 月数指定
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KFNMT_ASSIGN_MON_START")
public class KfnmtAssignMonthStart extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtAssignMonthStartPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

    @Column(name = "ASSIGN_WAY_MON_START")
    public int specifyStartMonth;

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

    @OneToOne(mappedBy = "kfnmtAssignMonthStart", orphanRemoval = true)
    public KfnmtWkpCheckCondition checkCondition;

    public StartMonth toDomain() {
        return new StartMonth(specifyStartMonth,
            Optional.of(new MonthNo(EnumAdaptor.valueOf(monthPrevious, PreviousClassification.class), monthNo, curentMonth))
        );
    }

    public void fromEntity(KfnmtAssignMonthStart newEntity) {
        this.contractCode = newEntity.contractCode;
        this.specifyStartMonth = newEntity.specifyStartMonth;
        this.monthNo = newEntity.monthNo;
        this.curentMonth = newEntity.curentMonth;
        this.monthPrevious = newEntity.monthPrevious;
    }

    public static KfnmtAssignMonthStart toEntity(StartMonth domain, String patternCD, int category) {

        KfnmtAssignMonthStart entity = new KfnmtAssignMonthStart();
        entity.pk = new KfnmtAssignMonthStartPk(AppContexts.user().companyId(), patternCD, category);
        entity.contractCode = AppContexts.user().contractCode();
        entity.specifyStartMonth = domain.getSpecifyStartMonth().value;
        if (domain.getStrMonthNo().isPresent()) {
            entity.monthNo = domain.getStrMonthNo().get().getMonthNo();
            entity.curentMonth = domain.getStrMonthNo().get().isCurentMonth();
            entity.monthPrevious = domain.getStrMonthNo().get().getMonthPrevious().value;
        }
        return entity;
    }

}
