package nts.uk.shr.com.system.config.internal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "CISCT_SYSTEM_CONFIG")
public class CisctSystemConfig extends ContractUkJpaEntity {

	@Id
	@Column(name = "CONFIG_NAME")
	public String name;

	@Column(name = "CONFIG_VALUE")
	public String value;
	
	@Override
	protected Object getKey() {
		return this.name;
	}
}
