package nts.uk.ctx.at.shared.infra.entity.ot.zerotime;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * @author phongtq
 * 平日から休日の0時跨ぎ設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtCalcDOvdWtohPK implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** 変更前の残業枠NO */
	@Column(name = "OVERTIME_FRAME_NO")
	public BigDecimal overworkFrameNo;
}
