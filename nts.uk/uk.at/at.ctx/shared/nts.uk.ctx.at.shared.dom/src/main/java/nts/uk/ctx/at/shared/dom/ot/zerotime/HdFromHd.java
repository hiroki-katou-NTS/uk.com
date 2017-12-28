package nts.uk.ctx.at.shared.dom.ot.zerotime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakoutFrameNo;
/**
 * @author phongtq
 * 休日から休日への0時跨ぎ設定
 */
@AllArgsConstructor
@Getter
public class HdFromHd extends DomainObject{

	/** 会社ID */
	private String companyId;

	/**変更前の休出枠NO*/
	private BreakoutFrameNo holidayWorkFrameNo;
	
	/** 変更後の法定内休出NO*/
	private BreakoutFrameNo calcOverDayEnd;
	
	/** 変更後の法定外休出NO */
	private BreakoutFrameNo statutoryHd;
	
	/** 変更後の祝日休出NO */
	private BreakoutFrameNo excessHd;
	
	/**
	 * Create from Java Type of Hd From Hd
	 * @param companyId
	 * @param holidayWorkFrameNo
	 * @param calcOverDayEnd
	 * @param statutoryHd
	 * @param excessHd
	 * @return
	 */
	public static HdFromHd createFromJavaType( String companyId, int holidayWorkFrameNo, int calcOverDayEnd, int statutoryHd, int excessHd){
		return new HdFromHd(companyId, new BreakoutFrameNo(holidayWorkFrameNo), new BreakoutFrameNo(calcOverDayEnd), new BreakoutFrameNo(statutoryHd), new BreakoutFrameNo(excessHd));
	}
}
