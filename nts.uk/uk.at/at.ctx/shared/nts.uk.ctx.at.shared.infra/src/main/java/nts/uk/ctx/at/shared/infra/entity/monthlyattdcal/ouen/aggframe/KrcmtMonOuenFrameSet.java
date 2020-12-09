package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_MON_SUP_FRAME_UNT")
@AllArgsConstructor
public class KrcmtMonOuenFrameSet extends ContractUkJpaEntity implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;
	
	/** 集計枠単位 */
	@Column(name = "FRAME_UNIT")
	public int frameUnit;
	
	@Override
	protected Object getKey() {
		return cid;
	}

}
