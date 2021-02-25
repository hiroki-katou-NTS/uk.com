package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime;
import java.math.BigDecimal;

/**
 * @author phongtq
 * 休日から平日への0時跨ぎ設定
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class HdFromWeekday extends DomainObject{
	/** 会社ID */
	private String companyId;

	/** 変更前の休出枠NO */
	private int holidayWorkFrameNo;

	/** 変更後の残業枠NO */
	private BigDecimal overWorkNo;

	/**
	 * Create from Java Type of Hd From Weekday
	 * @param companyId
	 * @param hdFrameNo
	 * @param overtimeFrameNo
	 * @return
	 */
	public static HdFromWeekday createFromJavaType(String companyId, int holidayWorkFrameNo, BigDecimal overWorkNo) {
		return new HdFromWeekday(companyId, holidayWorkFrameNo, overWorkNo);
	}
}
