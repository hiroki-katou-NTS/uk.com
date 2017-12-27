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
	private BreakoutFrameNo breakFrameNo;
	
	/** 変更後の法定内休出NO*/
	private BreakoutFrameNo legalHdNo;
	
	/** 変更後の法定外休出NO */
	private BreakoutFrameNo nonLegalHdNo;
	
	/** 変更後の祝日休出NO */
	private BreakoutFrameNo nonLegalPublicHdNo;
	
	/**
	 * Create from Java Type of Hd From Hd
	 * @param companyId
	 * @param holidayWorkFrameNo
	 * @param calcOverDayEnd
	 * @param statutoryHd
	 * @param excessHd
	 * @return
	 */
	public static HdFromHd createFromJavaType( String companyId, int breakFrameNo, int legalHdNo, int nonLegalHdNo, int nonLegalPublicHdNo){
		return new HdFromHd(companyId, new BreakoutFrameNo(breakFrameNo), new BreakoutFrameNo(legalHdNo), new BreakoutFrameNo(nonLegalHdNo), new BreakoutFrameNo(nonLegalPublicHdNo));
	}
}
