package nts.uk.ctx.at.shared.dom.calculation.holiday.time;
/**
 * @author phongtq
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class OverdayCalcHoliday {

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
	
	public static OverdayCalcHoliday createFromJavaType( String companyId, int holidayWorkFrameNo, int calcOverDayEnd, int statutoryHd, int excessHd){
		return new OverdayCalcHoliday(companyId, holidayWorkFrameNo, calcOverDayEnd, statutoryHd, excessHd);
	}
}
