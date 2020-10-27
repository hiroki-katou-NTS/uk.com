package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 対象の特別休暇枠
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_FRAME_HDSP")
public class KshmtHdspFrameHdsp extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshmtHdspFrameHdspPK pk;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
