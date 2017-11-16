/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import java.util.List;
import java.util.Objects;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class DailyPattern.
 */
@Getter
public class DailyPattern extends AggregateRoot {

	/** The company id. */
	private CompanyId companyId;

	/** The pattern code. */
	private PatternCode patternCode;

	/** The pattern name. */
	private PatternName patternName;

	/** The list daily pattern val. */
	private List<DailyPatternVal> listDailyPatternVal;

	/**
	 * Instantiates a new daily pattern.
	 *
	 * @param memento
	 *            the memento
	 */
	public DailyPattern(DailyPatternGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.patternCode = memento.getPatternCode();
		this.patternName = memento.getPatternName();
		this.listDailyPatternVal = memento.getListDailyPatternVal();

		// Check validate dailypattern all not setting
		if (CollectionUtil.isEmpty(listDailyPatternVal)) {
			throw new BusinessException("Msg_31");
		}

		this.listDailyPatternVal.stream().filter(Objects::nonNull).forEach(item -> {
			// check validate eap and find msg25
			if (!StringUtil.isNullOrEmpty(item.getWorkTypeSetCd().v(), true)
					&& item.getDays() == null) {
				throw new BusinessException("Msg_25");
			}

			// check validate eap and find msg25
			if (StringUtil.isNullOrEmpty(item.getWorkTypeSetCd().v(), true)
					&& item.getDays() != null) {
				throw new BusinessException("Msg_22");
			}
		});
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DailyPatternSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setPatternCode(this.patternCode);
		memento.setPatternName(this.patternName);
		memento.setListDailyPatternVal(this.listDailyPatternVal);
	}

}
