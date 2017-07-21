/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


/**
 * The Class ClassificationBasicWork. 分類基本勤務設定
 */
@Getter
public class ClassificationBasicWork extends AggregateRoot {

	/** The company id. */
	private CompanyId companyId;

	/** The classification code. */
	private ClassificationCode classificationCode;

	/** The basic work setting. */
	private List<BasicWorkSetting> basicWorkSetting;

	/**
	 * Instantiates a new classification basic work.
	 *
	 * @param memento the memento
	 */
	public ClassificationBasicWork(ClassifiBasicWorkGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.classificationCode = memento.getClassificationCode();
		this.basicWorkSetting = memento.getBasicWorkSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ClassifiBasicWorkSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setClassificationCode(this.classificationCode);
		memento.setBasicWorkSetting(this.basicWorkSetting);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basicWorkSetting == null) ? 0 : basicWorkSetting.hashCode());
		result = prime * result
				+ ((classificationCode == null) ? 0 : classificationCode.hashCode());
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
		ClassificationBasicWork other = (ClassificationBasicWork) obj;
		if (basicWorkSetting == null) {
			if (other.basicWorkSetting != null)
				return false;
		} else if (!basicWorkSetting.equals(other.basicWorkSetting))
			return false;
		if (classificationCode == null) {
			if (other.classificationCode != null)
				return false;
		} else if (!classificationCode.equals(other.classificationCode))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
	
}
