package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;

/**
 * スケジュール日次のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_SCHE_COND_DAY_LINK")
public class KfndtScheCondDayLink extends UkJpaEntity {

    @Id
    @Column(name = "ERAL_CHECK_ID")
    public String eralCheckId;

    /* チェック条件コード */
    @Column(name = "AL_CHECK_COND_CATE_CD")
    public String ctgCd;

    /* カテゴリ */
    @Column(name = "CATEGORY")
    public int ctg;
    @OneToOne
    @JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
            @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
            @JoinColumn(name = "AL_CHECK_COND_CATE_CD", referencedColumnName = "CD", insertable = false, updatable = false) })
    public KfnmtAlarmCheckConditionCategory condition;

    @Override
    protected Object getKey() {
        return this.eralCheckId;
    }

    public KfndtScheCondDayLink toDomain(){
        return new KfndtScheCondDayLink();
    }
}
