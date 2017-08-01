/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


/**
 * The Class CompanyBasicWork. 全社基本勤務設定
 */
@Getter
public class CompanyBasicWork extends AggregateRoot {

	/** The company id. */
	private String companyId;

	/** The basic work setting. */
	private List<BasicWorkSetting> basicWorkSetting;

	/**
	 * Instantiates a new company basic work.
	 *
	 * @param memento the memento
	 */
	public CompanyBasicWork(CompanyBasicWorkGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.basicWorkSetting = memento.getBasicWorkSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompanyBasicWorkSetMemento memento) {
		memento.setBasicWorkSetting(this.basicWorkSetting);
		memento.setCompanyId(this.companyId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basicWorkSetting == null) ? 0 : basicWorkSetting.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyBasicWork other = (CompanyBasicWork) obj;
		if (basicWorkSetting == null) {
			if (other.basicWorkSetting != null)
				return false;
		} else if (!basicWorkSetting.equals(other.basicWorkSetting))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
}
