package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CarryForwardDayTimes {
	/**
	 * 繰越日数
	 */
	private double carryForwardDays;
	/**
	 * 繰越時間
	 */
	private int carryForwardTime;
}
