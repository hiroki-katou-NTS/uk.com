package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_DAI_PERFORMANCE_FUN")
public class KrcmtDaiPerformanceFun extends UkJpaEntity{
	
	public KrcmtDaiPerformanceFunPk pk;

	@Column(name = "DISPLAY_NAME_OF_FUNCTION")
	public String displayNameOfFunction;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
}
