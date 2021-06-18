package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.weekly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  週次のアラームチェック条件 Entity
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_WEEK_COND_ALARM_LINK")
public class KfndtWeekCondAlarmLink extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    @Column(name = "ERAL_CHECK_ID")
    public KfndtWeekCondAlarmLinkPk pk;

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

    public KfndtWeekCondAlarmLink toDomain(){
        return new KfndtWeekCondAlarmLink();
    }

	public KfndtWeekCondAlarmLink(KfndtWeekCondAlarmLinkPk pk, String eralCheckId) {
		super();
		this.pk = pk;
		this.eralCheckId = eralCheckId;
	} 
	
	public static KfndtWeekCondAlarmLink toEntity(String companyId, String erAlCheckId, String ctgCode, int categoryCode, int alarmType) {
		KfndtWeekCondAlarmLinkPk pk = new KfndtWeekCondAlarmLinkPk(companyId, ctgCode, categoryCode);
		KfndtWeekCondAlarmLink entity = new KfndtWeekCondAlarmLink(pk, erAlCheckId);
    	return entity;
    }
}
