package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCST_DAILY_REC_OPE")
public class KrcstDailyRecOpe extends UkJpaEntity{
	
	@Id
	@Column(name = "CID")
	public String cid;
	
	@Column(name = "OPERATION_COMMENT")
	public String comment;
	
	@Column(name = "SETTING_UNIT")
	public int settingUnit;

	@Override
	protected Object getKey() {
		return this.cid;
	}

	/**
	 * 
	 */
	public KrcstDailyRecOpe() {
		super();
	}

}
