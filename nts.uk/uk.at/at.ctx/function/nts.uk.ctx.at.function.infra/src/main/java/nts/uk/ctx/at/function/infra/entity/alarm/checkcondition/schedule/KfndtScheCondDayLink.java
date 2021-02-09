package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;

/**
 * スケジュール日次のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_SCHE_COND_DAY_LINK")
public class KfndtScheCondDayLink extends ContractUkJpaEntity {

    @Id
    @Column(name = "ERAL_CHECK_ID")
    public String eralCheckId;

    /* チェック条件コード */
    @Column(name = "AL_CHECK_COND_CATE_CD")
    public boolean ctgCd;

    /* カテゴリ */
    @Column(name = "CATEGORY")
    public int ctg;

    @Override
    protected Object getKey() {
        return this.eralCheckId;
    }

    public KfndtScheCondDayLink toDomain(){
        return new KfndtScheCondDayLink();
    }
}
