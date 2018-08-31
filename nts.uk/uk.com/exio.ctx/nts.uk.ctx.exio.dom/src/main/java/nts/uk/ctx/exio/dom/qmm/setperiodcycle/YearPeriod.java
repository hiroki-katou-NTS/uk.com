package nts.uk.ctx.exio.dom.qmm.setperiodcycle;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 
 * @author thanh.tq 年期間
 *
 */
@Getter
public class YearPeriod extends DomainObject {
	/**
	 * 終了年
	 */
	private Optional<Integer> endYear;

	/**
	 * 開始年
	 */
	private Optional<Integer> startYear;

	public YearPeriod(int endYear, int startYear) {
		super();
		this.endYear = Optional.ofNullable(endYear);
		this.startYear = Optional.ofNullable(startYear);
	}
}
