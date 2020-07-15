package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class KrcmtMonOuenFramePK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String cid;

	/** 集計枠 */
	@Column(name = "FRAME_NO")
	public int frameNo;

}
