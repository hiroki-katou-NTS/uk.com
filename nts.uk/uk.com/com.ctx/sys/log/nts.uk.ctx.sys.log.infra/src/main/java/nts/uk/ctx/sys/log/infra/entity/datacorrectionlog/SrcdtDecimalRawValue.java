package nts.uk.ctx.sys.log.infra.entity.datacorrectionlog;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "SRCDT_DECIMAL_RAW_VALUE")
@NoArgsConstructor
@AllArgsConstructor
public class SrcdtDecimalRawValue extends UkJpaEntity {

	@EmbeddedId
	SrcdtDecimalRawValuePk pk;

	@OneToOne(mappedBy = "rawDecimalValueBefore")
	SrcdtDataCorrectionLog beforeLog;

	@OneToOne(mappedBy = "rawDecimalValueAfter")
	SrcdtDataCorrectionLog afterLog;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
