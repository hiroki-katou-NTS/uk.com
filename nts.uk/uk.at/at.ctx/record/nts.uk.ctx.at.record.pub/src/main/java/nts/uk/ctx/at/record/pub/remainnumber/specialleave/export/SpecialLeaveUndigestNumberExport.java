package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 特休未消化数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveUndigestNumberExport {

	/**
	 * 日数
	 */
	protected Double days;

	/**
	 * 時間
	 */
	protected Optional<Integer> minutes;

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveUndigestNumberExport() {
		this.days = 0.0;
		this.minutes = Optional.empty();
	}
}
