package nts.uk.ctx.at.function.infra.entity.alarmworkplace.singlemonth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtWkpCheckCondition;
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

    //class SingleMonth

    @OneToOne(mappedBy = "kfnmtAssignNumofMon", orphanRemoval = true)
    public KfnmtWkpCheckCondition checkCondition;

}
