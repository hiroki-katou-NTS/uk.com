package nts.uk.ctx.at.shared.app.find.ot.zerotime;
import java.math.BigDecimal;

/**
 * @author phongtq
 * 平日から休日の0時跨ぎ設定
 */
import lombok.Data;

/**
 * 
 * @author phongtq
 *
 */

@Data
public class WeekdayHolidayDto {
	/** 変更前の残業枠NO */
	private BigDecimal overworkFrameNo;

	/** 変更後の残業枠NO */
	private int weekdayNo;

	/** 変更後の法定外休出NO */
	private int excessHolidayNo;

	/** 変更後の祝日休出NO */
	private int excessSphdNo;
	
}
