package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 休日区分別休出枠
 * @author shuichi_ishida
 */
@Getter
public class HdwkFrameEachHdAtr {

	/** 法定内 */
	private HolidayWorkFrameNo statutory;
	/** 法定外 */
	private HolidayWorkFrameNo notStatutory;
	/** 法定外祝日 */
	private HolidayWorkFrameNo notStatHoliday;
	
	/**
	 * コンストラクタ
	 */
	public HdwkFrameEachHdAtr(){
		this.statutory = new HolidayWorkFrameNo(1);
		this.notStatutory = new HolidayWorkFrameNo(1);
		this.notStatHoliday = new HolidayWorkFrameNo(1);
	}
	
	/**
	 * ファクトリー
	 * @param statutory 法定内
	 * @param notStatutory 法定外
	 * @param notStatHoliday 法定外休出
	 * @return 休日区分別休出枠
	 */
	public static HdwkFrameEachHdAtr of(
			HolidayWorkFrameNo statutory,
			HolidayWorkFrameNo notStatutory,
			HolidayWorkFrameNo notStatHoliday){
		
		HdwkFrameEachHdAtr myclass = new HdwkFrameEachHdAtr();
		myclass.statutory = statutory;
		myclass.notStatutory = notStatutory;
		myclass.notStatHoliday = notStatHoliday;
		return myclass;
	}
	
	/**
	 * ファクトリー (Java型)
	 * @param statutory 法定内
	 * @param notStatutory 法定外
	 * @param notStatHoliday 法定外休出
	 * @return 休日区分別休出枠
	 */
	public static HdwkFrameEachHdAtr createFromJavaType(
			Integer statutory,
			Integer notStatutory,
			Integer notStatHoliday){
		
		HdwkFrameEachHdAtr myclass = new HdwkFrameEachHdAtr();
		if (statutory == null || notStatutory == null || notStatHoliday == null) return null;
		myclass.statutory = new HolidayWorkFrameNo(statutory);
		myclass.notStatutory = new HolidayWorkFrameNo(notStatutory);
		myclass.notStatHoliday = new HolidayWorkFrameNo(notStatHoliday);
		return myclass;
	}
}
