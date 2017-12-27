package nts.uk.ctx.at.shared.dom.ot.zerotime;
import java.math.BigDecimal;

/**
 * @author phongtq
 * 休日から平日への0時跨ぎ設定
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakoutFrameNo;
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
	private BreakoutFrameNo hdFrameNo;

	/** 変更後の残業枠NO */
	private OvertimeWorkFrameNo overtimeFrameNo;

	/**
	 * Create from Java Type of Hd From Weekday
	 * @param companyId
	 * @param hdFrameNo
	 * @param overtimeFrameNo
	 * @return
	 */
	public static HdFromWeekday createFromJavaType(String companyId, int hdFrameNo, BigDecimal overtimeFrameNo) {
		return new HdFromWeekday(companyId, new BreakoutFrameNo(hdFrameNo), new OvertimeWorkFrameNo(overtimeFrameNo));
	}
}
