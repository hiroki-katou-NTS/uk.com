package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 特別休暇使用
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveUseNumberExport {

	/**
	 * 使用日数
	 */
	private Optional<Double> useDays;

	/**
	 * 使用時間
	 */
	private Optional<Integer> useTimes;

	public SpecialLeaveUseNumberExport() {
		useDays = Optional.empty();
		useTimes = Optional.empty();
	}

}
