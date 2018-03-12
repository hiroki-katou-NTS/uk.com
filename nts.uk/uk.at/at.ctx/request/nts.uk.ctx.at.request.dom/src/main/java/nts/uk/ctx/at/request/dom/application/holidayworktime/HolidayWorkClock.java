package nts.uk.ctx.at.request.dom.application.holidayworktime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.holidayworktime.primitivevalue.HolidayAppPrimitiveTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayWorkClock {
	
	/**
	 * 勤務開始時刻
	 */
	private HolidayAppPrimitiveTime startTime;
	
	/**
	 * 勤務終了時刻
	 */
	private HolidayAppPrimitiveTime endTime;
	
	/**
	 * 直行区分
	 */
	private GoBackAtr goAtr;
	
	/**
	 * 直帰区分
	 */
	private GoBackAtr backAtr;
	
	public HolidayWorkClock(Integer startTime, Integer endTime,int goAtr,int backAtr){
		this.startTime = (startTime == null) ? null : new HolidayAppPrimitiveTime(startTime);
		this.endTime = (endTime == null) ? null : new HolidayAppPrimitiveTime(endTime);
		this.goAtr = EnumAdaptor.valueOf(goAtr, GoBackAtr.class);
		this.backAtr = EnumAdaptor.valueOf(backAtr, GoBackAtr.class);
	}
	
	
	public static HolidayWorkClock validateTime(Integer startTime, Integer endTime,int goAtr,int backAtr){
		if(startTime >= endTime){
			throw new BusinessException("Msg_307");
		}
		return new HolidayWorkClock(new HolidayAppPrimitiveTime(startTime), new HolidayAppPrimitiveTime(endTime),EnumAdaptor.valueOf(goAtr, GoBackAtr.class),EnumAdaptor.valueOf(backAtr, GoBackAtr.class));
	}
	public HolidayWorkClock(Integer startTime, Integer endTime){
		this.startTime = new HolidayAppPrimitiveTime(startTime);
		this.endTime = new HolidayAppPrimitiveTime(endTime);
	}
	public static HolidayWorkClock validateTime2(Integer startTime, Integer endTime,int goAtr,int backAtr){
		if(startTime != null && endTime != null){
			if(startTime >= endTime){
				throw new BusinessException("Msg_307");
			}
			return new HolidayWorkClock(new HolidayAppPrimitiveTime(startTime), new HolidayAppPrimitiveTime(endTime),EnumAdaptor.valueOf(goAtr, GoBackAtr.class),EnumAdaptor.valueOf(backAtr, GoBackAtr.class));
		}else{
			return new HolidayWorkClock(startTime, endTime, goAtr, backAtr);
		}
		
	}
}
