package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveOverNumber;

/**
 * 特別休暇上限超過消滅数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class SpecialLeaveOverNumber extends LeaveOverNumber{

//	
//	// 日数
//	public DayNumberOver numberOverDays;
//	// 時間
//	public Optional<TimeOver> timeOver;
//	
//	private SpecialLeaveOverNumber(Double days, Integer minutes) {
//		this.numberOverDays = new DayNumberOver(days == null? 0.0d: days.doubleValue());
//		this.timeOver = minutes != null ? Optional.of(new TimeOver(minutes)) : Optional.empty();
//	}
//
//	public static SpecialLeaveOverNumber createFromJavaType(Double days, Integer minutes) {
//		return new SpecialLeaveOverNumber(days, minutes);
//	}
	
	public SpecialLeaveOverNumber(Double days, Integer minutes) {
		super(days, minutes);
	}
	public static SpecialLeaveOverNumber createFromJavaType(Double days, Integer minutes) {
		return new SpecialLeaveOverNumber(days, minutes);
	}

}
