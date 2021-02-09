package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.weekly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;

/**
 *  週次のアラームチェック条件 Entity
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_WEEK_COND_ALARM_LINK")
public class KfndtWeekCondAlarmLink extends ContractUkJpaEntity {

    @Id
    @Column(name = "ERAL_CHECK_ID")
    public String eralCheckId;

    /* チェック条件コード */
    @Column(name = "AL_CHECK_COND_CATE_CD")
    public boolean ctgCd;

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

    public KfndtWeekCondAlarmLink toDomain(){
        return new KfndtWeekCondAlarmLink();
    }
}
