package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  スケジュール月次のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_SCHE_COND_MONTH_LINK")
public class KfndtScheCondMonthLink extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KfndtScheCondMonthLinkPk pk;

    @Column(name = "ERAL_CHECK_ID")
    public String eralCheckId;
    
    @OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "AL_CHECK_COND_CATE_CD", referencedColumnName = "CD", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false) })
	public KfnmtAlarmCheckConditionCategory condition;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public KfndtScheCondMonthLink toDomain(){
        return new KfndtScheCondMonthLink();
    }

	public KfndtScheCondMonthLink(KfndtScheCondMonthLinkPk pk, String eralCheckId) {
		super();
		this.pk = pk;
		this.eralCheckId = eralCheckId;
	}
	
	public static KfndtScheCondMonthLink toEntity(String companyId, String erAlCheckId, String ctgCode, int categoryCode, int alarmType) {
		KfndtScheCondMonthLinkPk pk = new KfndtScheCondMonthLinkPk(companyId, ctgCode, categoryCode, alarmType);
    	KfndtScheCondMonthLink entity = new KfndtScheCondMonthLink(pk, erAlCheckId);
    	return entity;
    }
}
