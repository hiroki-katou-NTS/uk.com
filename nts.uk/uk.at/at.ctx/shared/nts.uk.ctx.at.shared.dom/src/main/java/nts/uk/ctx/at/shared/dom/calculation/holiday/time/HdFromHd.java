package nts.uk.ctx.at.shared.dom.calculation.holiday.time;
/**
 * @author phongtq
 * 休日から休日への0時跨ぎ設定
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
@AllArgsConstructor
@Getter
public class HdFromHd extends DomainObject{

	/** 会社ID */
	private String companyId;

	/**変更前の休出枠NO*/
	private int overtimeFrameNo;
	
	/** 変更後の法定内休出NO*/
	private int legalHdNo;
	
	/** 変更後の法定外休出NO */
	private int nonLegalHdNo;
	
	/** 変更後の祝日休出NO */
	private int nonLegalPublicHdNo;
	
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
		return new HdFromHd(companyId, holidayWorkFrameNo, calcOverDayEnd, statutoryHd, excessHd);
	}
}
