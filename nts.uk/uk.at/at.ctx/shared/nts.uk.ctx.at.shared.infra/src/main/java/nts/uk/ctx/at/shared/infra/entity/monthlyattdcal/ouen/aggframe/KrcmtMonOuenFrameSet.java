package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_MON_OUEN_FRAME_SET")
@AllArgsConstructor
public class KrcmtMonOuenFrameSet extends UkJpaEntity implements Serializable {

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
