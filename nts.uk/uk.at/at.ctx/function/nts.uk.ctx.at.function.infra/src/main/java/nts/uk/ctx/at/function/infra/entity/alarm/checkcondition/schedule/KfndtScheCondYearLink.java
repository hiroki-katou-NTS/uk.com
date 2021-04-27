package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * スケジュール年間のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_SCHE_COND_YEAR_LINK")
public class KfndtScheCondYearLink extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KfndtScheCondMonthYearPk pk;


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

    public KfndtScheCondYearLink toDomain(){
        return new KfndtScheCondYearLink();
    }
    
    public KfndtScheCondYearLink(KfndtScheCondMonthYearPk pk, String eralCheckId) {
		super();
		this.pk = pk;
		this.eralCheckId = eralCheckId;
	}
	
	public static KfndtScheCondYearLink toEntity(String companyId, String erAlCheckId, String ctgCode, int categoryCode, int alarmType) {
		KfndtScheCondMonthYearPk pk = new KfndtScheCondMonthYearPk(companyId, ctgCode, categoryCode);
		KfndtScheCondYearLink entity = new KfndtScheCondYearLink(pk, erAlCheckId);
    	return entity;
    }
}
