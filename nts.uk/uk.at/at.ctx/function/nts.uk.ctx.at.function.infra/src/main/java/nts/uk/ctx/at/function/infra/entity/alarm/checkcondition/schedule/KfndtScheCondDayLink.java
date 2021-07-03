package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * スケジュール日次のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_SCHE_COND_DAY_LINK")
public class KfndtScheCondDayLink extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KfndtScheCondDayLinkPk pk;

    @Column(name = "ERAL_CHECK_ID")
    public String eralCheckId;
    
    @OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "AL_CHECK_COND_CATE_CD", referencedColumnName = "CD", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false) })
	public KfnmtAlarmCheckConditionCategory condition;
    
    public KfndtScheCondDayLink(KfndtScheCondDayLinkPk pk, String eralCheckId) {
    	this.pk = pk;
    	this.eralCheckId = eralCheckId;
    }
    
    @Override
    protected Object getKey() {
        return this.pk;
    }

    public KfndtScheCondDayLink toDomain(){
        return new KfndtScheCondDayLink();
    }
    
    public static KfndtScheCondDayLink toEntity(String companyId, String erAlCheckId, String ctgCode, int categoryCode, int alarmType) {
    	KfndtScheCondDayLinkPk pk = new KfndtScheCondDayLinkPk(companyId, ctgCode, categoryCode, alarmType);
    	KfndtScheCondDayLink entity = new KfndtScheCondDayLink(pk, erAlCheckId);
    	return entity;
    }
}
