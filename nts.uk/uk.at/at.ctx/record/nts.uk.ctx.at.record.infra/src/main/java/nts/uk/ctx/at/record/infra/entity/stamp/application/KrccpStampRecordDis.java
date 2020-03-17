package nts.uk.ctx.at.record.infra.entity.stamp.application;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_RECORD_DIS")
public class KrccpStampRecordDis  extends ContractUkJpaEntity{
	
	@EmbeddedId
    public KrccpStampRecordDisPk pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
																			
