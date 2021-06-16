package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.carryForwarddata;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  公休繰越データ
 * @author hayata_maekawa
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSHDT_HDPUB_REM")
public class KshdtHdpubRem extends ContractUkJpaEntity {
	
	
	/* 主キー */
	@EmbeddedId
	public KshdtHdpubRemPK pk;
	
	@Column(name = "DEADLINE")
	public GeneralDate deadline;
	
	@Column(name = "REGISTER_TYPE")
    public int registerType;
	
	@Column(name = "CARRIEDFORWARD")
    public double carriedforward;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
