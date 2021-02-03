package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  スケジュール月次のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KSCDT_SCHE_COND_MONTH_LINK")
public class KscdtScheCondMonthLink extends ContractUkJpaEntity {

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

    public KscdtScheCondMonthLink toDomain(){
        return new KscdtScheCondMonthLink();
    }
}
