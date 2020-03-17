package nts.uk.ctx.at.record.infra.entity.stamp.application;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_RECORD_DIS")
public class KrccpStampFunction  extends ContractUkJpaEntity{
	
	@EmbeddedId
    public KrccpStampFunctionPk pk;
	
	/** 使用区分*/
	@Column(name = "RECORD_DISPLAY_ART")
	public int recordDisplayArt;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
																			
									
