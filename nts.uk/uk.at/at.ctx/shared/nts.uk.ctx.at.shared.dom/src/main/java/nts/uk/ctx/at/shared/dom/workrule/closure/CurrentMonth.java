/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;

/**
 * The Class CurrentMonth.
 */
// 当月
@Getter
public class CurrentMonth extends DomainObject {

	/** The processing date. */
	// 処理年月
	private YearMonth processingYm;
	
	/** The closure classification */
	// 締め日変更区分
	@Setter
	private Optional<ClosureClassification> closureClassification = Optional.empty();

	/**
	 * Instantiates a new current month.
	 *
	 * @param value the value
	 */
	public CurrentMonth(Integer value) {
		this.processingYm = YearMonth.of(value);
	}
	
	/**
	 * Next month.
	 * 当月を1ヵ月分進める
	 */
	public void nextMonth() {
		this.processingYm = this.processingYm.nextMonth();
	}
}
