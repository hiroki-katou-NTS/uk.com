package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 職場選択を利用できる権限
 * @author chungnt
 *
 */
@Entity
@NoArgsConstructor
@Table(name = "KRCMT_STAMP_WKP_SELECT_ROLE")
public class KrcmtStampWkpSelectRole extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtStampWkpSelectRolePk pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}