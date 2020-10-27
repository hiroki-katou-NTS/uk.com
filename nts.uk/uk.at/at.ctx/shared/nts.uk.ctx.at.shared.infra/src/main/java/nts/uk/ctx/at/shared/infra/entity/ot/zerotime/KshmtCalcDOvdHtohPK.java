package nts.uk.ctx.at.shared.infra.entity.ot.zerotime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * @author phongtq
 * 休日から休日への0時跨ぎ設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtCalcDOvdHtohPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
	
	/**変更前の休出枠NO*/
	@Column(name = "BREAK_FRAME_NO")
	public int holidayWorkFrameNo;
}

