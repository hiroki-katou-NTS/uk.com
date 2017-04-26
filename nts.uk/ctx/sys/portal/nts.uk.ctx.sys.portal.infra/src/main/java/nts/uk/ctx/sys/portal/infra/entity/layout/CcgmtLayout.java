package nts.uk.ctx.sys.portal.infra.entity.layout;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author LamDT
 */
@Getter
@Setter
@Entity
@Table(name = "CCGMT_LAYOUT")
public class CcgmtLayout extends UkJpaEntity {

	int pgType;
	
	@Override
	protected Object getKey() {
		return null;
	}

}
