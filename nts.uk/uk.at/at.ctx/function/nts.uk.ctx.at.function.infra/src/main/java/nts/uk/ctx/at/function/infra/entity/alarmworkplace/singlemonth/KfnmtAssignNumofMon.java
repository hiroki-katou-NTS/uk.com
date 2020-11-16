package nts.uk.ctx.at.function.infra.entity.alarmworkplace.singlemonth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth.SingleMonth;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtWkpCheckCondition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * entity : 単月
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ASSIGN_NUMOF_MON")
public class KfnmtAssignNumofMon extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtAssignNumofMonPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

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

    @OneToOne(mappedBy = "kfnmtAssignNumofMon", orphanRemoval = true, fetch=FetchType.LAZY)
    public KfnmtWkpCheckCondition checkCondition;

    public SingleMonth toDomain() {
        return new SingleMonth(EnumAdaptor.valueOf(monthPrevious, PreviousClassification.class), monthNo, curentMonth);
    }

    public void fromEntity(KfnmtAssignNumofMon newEntity) {
        this.contractCode = AppContexts.user().contractCode();
        this.monthNo = newEntity.monthNo;
        this.curentMonth = newEntity.curentMonth;
        this.monthPrevious = newEntity.monthPrevious;
    }

    public static KfnmtAssignNumofMon toEntity(SingleMonth domain, String patternCD, int category) {

        KfnmtAssignNumofMon entity = new KfnmtAssignNumofMon();
        entity.pk = new KfnmtAssignNumofMonPk(AppContexts.user().companyId(), patternCD, category);
        entity.contractCode = AppContexts.user().contractCode();
        entity.monthNo = domain.getMonthNo();
        entity.curentMonth = domain.isCurentMonth();
        entity.monthPrevious = domain.getMonthPrevious().value;
        return entity;
    }
}
