package nts.uk.ctx.at.shared.dom.ot.zerotime;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
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
	private int holidayWorkFrameNo;
	
	/** 変更後の法定内休出NO*/
	private int calcOverDayEnd;
	
	/** 変更後の法定外休出NO */
	private int statutoryHd;
	
	/** 変更後の祝日休出NO */
	private int excessHd;
	
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
		return new HdFromHd(companyId, holidayWorkFrameNo,calcOverDayEnd, statutoryHd, excessHd);
	}
	
	public int useFrameNo(HolidayAtr atr) {
		switch(atr) {
		case NON_STATUTORY_HOLIDAYS:
			return statutoryHd;
		case PUBLIC_HOLIDAY:
			return excessHd;
		case STATUTORY_HOLIDAYS:
			return calcOverDayEnd;
		default:
			throw new RuntimeException("unknown HolidayAtr:"+ atr);
		}
	}
}
