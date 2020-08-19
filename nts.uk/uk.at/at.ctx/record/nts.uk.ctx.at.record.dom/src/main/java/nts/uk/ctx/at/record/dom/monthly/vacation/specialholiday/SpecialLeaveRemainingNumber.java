package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 特別休暇残数
public class SpecialLeaveRemainingNumber {

	/** 合計残日数 */
	public DayNumberOfRemain dayNumberOfRemain;
	
	/** 合計残時間 */
	public Optional<TimeOfRemain> timeOfRemain;
	
	/** 明細 */
	private List<SpecialLeaveRemainingDetail> details;
	
	
	private SpecialLeaveRemainingNumber(BigDecimal days, Integer minutes) {
		this.dayNumberOfRemain = new DayNumberOfRemain(days== null? 0.0d: days.doubleValue());
		this.timeOfRemain = minutes != null ? Optional.of(new TimeOfRemain(minutes)) : Optional.empty();
	}

	public static SpecialLeaveRemainingNumber createFromJavaType(BigDecimal days, Integer minutes) {
		return new SpecialLeaveRemainingNumber(days, minutes);
	}
	private SpecialLeaveRemainingNumber(Double days, Integer minutes) {
		this.dayNumberOfRemain = new DayNumberOfRemain(days);
		this.timeOfRemain = minutes != null ? Optional.of(new TimeOfRemain(minutes)) : Optional.empty();
	}

	public static SpecialLeaveRemainingNumber createFromJavaType(Double days, Integer minutes) {
		return new SpecialLeaveRemainingNumber(days, minutes);
	}
	
	/**
	 * 明細をクリア（要素数を０にする）
	 */
	public void clearDetails(){
		details = new ArrayList<SpecialLeaveRemainingDetail>();
	}

}
