package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;
/**
 * @author phongtq
 * 休日から休日への0時跨ぎ設定
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshstOverDayHdSetPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
	
	/**変更前の休出枠NO*/
	@Column(name = "HOLIDAY_WORK_FRAME_NO")
	public int holidayWorkFrameNo;
}

