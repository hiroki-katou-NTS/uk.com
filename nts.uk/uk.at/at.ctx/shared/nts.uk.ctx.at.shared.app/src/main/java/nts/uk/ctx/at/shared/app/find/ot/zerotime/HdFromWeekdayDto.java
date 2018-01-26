package nts.uk.ctx.at.shared.app.find.ot.zerotime;

import java.math.BigDecimal;

/**
 * @author phongtq
 * 休日から平日への0時跨ぎ設定
 */

import lombok.Data;

@Data
public class HdFromWeekdayDto {

	/** 変更前の休出枠NO */
	private int holidayWorkFrameNo;

	/** 変更後の残業枠NO */
	private BigDecimal overWorkNo;

}
