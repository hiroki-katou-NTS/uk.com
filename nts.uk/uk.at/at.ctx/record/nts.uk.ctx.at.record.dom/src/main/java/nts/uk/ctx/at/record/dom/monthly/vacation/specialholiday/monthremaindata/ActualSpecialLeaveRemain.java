package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualSpecialLeaveRemain {
	/**
	 * 日数
	 */
	private ActualSpecialLeaveRemainDay days;
	/**
	 * 時間
	 */
	private Optional<SpecialLeavaRemainTime> time;
}
