/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class OvertimeSetting.
 */
//時間外超過設定
@Getter
public class OvertimeSetting extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The note. */
	// 備考
	private OvertimeNote note;
	
	/** The breakdown items. */
	// 内訳項目一覧
	private List<OvertimeBRDItem> breakdownItems;
	
	/** The calculation method. */
	// 計算方法
	private OvertimeCalculationMethod calculationMethod;
	
	/** The over times. */
	// 超過時間一覧
	private List<Overtime> overtimes;
	
	/**
	 * Instantiates a new overtime setting.
	 *
	 * @param memento the memento
	 */
	public OvertimeSetting(OvertimeSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.note = memento.getNote();
		this.calculationMethod = memento.getCalculationMethod();
		this.overtimes = memento.getOvertimes();
	}
	

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OvertimeSettingSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setNote(this.note);
		memento.setCalculationMethod(this.calculationMethod);
		memento.setOvertimes(this.overtimes);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OvertimeSetting other = (OvertimeSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

}
