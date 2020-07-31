package nts.uk.ctx.at.request.infra.entity.application.stamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KRQDT_APP_STAMP_NR")
public class KrqdtAppSampNR extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KrqdtAppSampNRPk krqdtAppSampNRPk;
	
	@Column(name="STAMP_ATR")
	public Integer stampAtr;
	
	@Column(name="APP_TIME")
	public Integer appTime;
	
	@Column(name="GO_OUT_ATR")
	public Integer goOutAtr;
	
	
	@Override
	protected Object getKey() {
		
		return krqdtAppSampNRPk;
	}

}
