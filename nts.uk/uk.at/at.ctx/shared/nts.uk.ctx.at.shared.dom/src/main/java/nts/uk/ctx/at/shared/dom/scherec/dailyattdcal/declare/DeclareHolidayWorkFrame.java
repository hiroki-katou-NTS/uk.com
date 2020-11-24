package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 申告休出枠
 * @author shuichi_ishida
 */
@Getter
public class DeclareHolidayWorkFrame {

	/** 休出利用 */
	private NotUseAtr holidayWorkUsage;
	/** 休出深夜利用 */
	private NotUseAtr holidayWorkMnUsage;
	/** 休出 */
	private Optional<HdwkFrameEachHdAtr> holidayWork;
	/** 休出深夜 */
	private Optional<HdwkFrameEachHdAtr> holidayWorkMn;
	
	/**
	 * コンストラクタ
	 */
	public DeclareHolidayWorkFrame(){
		this.holidayWorkUsage = NotUseAtr.NOT_USE;
		this.holidayWorkMnUsage = NotUseAtr.NOT_USE;
		this.holidayWork = Optional.empty();
		this.holidayWorkMn = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param holidayWorkUsage 休出利用
	 * @param holidayWorkMnUsage 休出深夜利用
	 * @param holidayWork 休出
	 * @param holidayWorkMn 休出深夜
	 * @return 申告休出枠
	 */
	public static DeclareHolidayWorkFrame of(
			NotUseAtr holidayWorkUsage,
			NotUseAtr holidayWorkMnUsage,
			HdwkFrameEachHdAtr holidayWork,
			HdwkFrameEachHdAtr holidayWorkMn){

		DeclareHolidayWorkFrame myclass = new DeclareHolidayWorkFrame();
		myclass.holidayWorkUsage = holidayWorkUsage;
		myclass.holidayWorkMnUsage = holidayWorkMnUsage;
		myclass.holidayWork = Optional.ofNullable(holidayWork);
		myclass.holidayWorkMn = Optional.ofNullable(holidayWorkMn);
		return myclass;
	}
	
	/**
	 * ファクトリー (Java型)
	 * @param holidayWorkUsage 休出利用
	 * @param holidayWorkMnUsage 休出深夜利用
	 * @param holidayWork 休出
	 * @param holidayWorkMn 休出深夜
	 * @return 申告休出枠
	 */
	public static DeclareHolidayWorkFrame createFromJavaType(
			int holidayWorkUsage,
			int holidayWorkMnUsage,
			HdwkFrameEachHdAtr holidayWork,
			HdwkFrameEachHdAtr holidayWorkMn){
		
		DeclareHolidayWorkFrame myclass = new DeclareHolidayWorkFrame();
		myclass.holidayWorkUsage = EnumAdaptor.valueOf(holidayWorkUsage, NotUseAtr.class);
		myclass.holidayWorkMnUsage = EnumAdaptor.valueOf(holidayWorkMnUsage, NotUseAtr.class);
		myclass.holidayWork = Optional.ofNullable(holidayWork);
		myclass.holidayWorkMn = Optional.ofNullable(holidayWorkMn);
		return myclass;
	}
}
