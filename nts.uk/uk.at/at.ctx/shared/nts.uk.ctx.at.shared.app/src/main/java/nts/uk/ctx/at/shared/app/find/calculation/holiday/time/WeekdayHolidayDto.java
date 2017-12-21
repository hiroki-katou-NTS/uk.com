package nts.uk.ctx.at.shared.app.find.calculation.holiday.time;
/**
 * @author phongtq
 * 平日から休日の0時跨ぎ設定
 */
import lombok.Data;

@Data
public class WeekdayHolidayDto {
	/** 変更前の残業枠NO */
	private int overTimeFrameNo;

	/** 変更後の残業枠NO */
	private int legalHdNo;

	/** 変更後の法定外休出NO */
	private int nonLegalHdNo;

	/** 変更後の祝日休出NO */
	private int nonLegalPublicHdNo;
	
}
