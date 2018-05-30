package nts.uk.ctx.sys.log.infra.entity.datacorrectionlog;

import javax.persistence.EmbeddedId;
import javax.persistence.OneToOne;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

public class SrcdtVarcharRawValue extends UkJpaEntity {

	@EmbeddedId
	SrcdtVarcharRawValuePk pk;

	@OneToOne(mappedBy = "rawVarcharValueBefore")
	SrcdtDataCorrectionLog beforeLog;
	
	@OneToOne(mappedBy = "rawVarcharValueAfter")
	SrcdtDataCorrectionLog afterLog;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}
