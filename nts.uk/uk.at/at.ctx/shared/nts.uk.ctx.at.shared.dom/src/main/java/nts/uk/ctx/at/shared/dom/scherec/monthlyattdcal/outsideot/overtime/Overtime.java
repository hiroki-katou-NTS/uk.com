/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

/**
 * The Class Overtime.
 */
// 超過時間
@Getter
public class Overtime extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The super holiday 60 H occurs. */
	// 60H超休が発生する
	private boolean superHoliday60HOccurs;
	
	/** The use classification. */
	// 使用区分
	private UseClassification useClassification;
	
	/** The name. */
	//名称
	private OvertimeName name;

	/** The overtime. */
	// 超過時間
	private OvertimeValue overtime;
	
	/** The overtime no. */
	// 超過時間NO
	private OvertimeNo overtimeNo;
	
	
	/**
	 * Instantiates a new overtime.
	 */
	public Overtime(OvertimeGetMemento memento) {
		this.superHoliday60HOccurs = memento.getSuperHoliday60HOccurs();
		this.useClassification = memento.getUseClassification();
		this.name = memento.getName();
		this.overtime = memento.getOvertime();
		this.overtimeNo = memento.getOvertimeNo();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OvertimeSetMemento memento){
		memento.setSuperHoliday60HOccurs(this.superHoliday60HOccurs);
		memento.setUseClassification(this.useClassification);
		memento.setName(this.name);
		memento.setOvertime(this.overtime);
		memento.setOvertimeNo(this.overtimeNo);
	}
	
	/**
	 * Checks if is use class.
	 *
	 * @return the boolean
	 */
	public Boolean isUseClass() {
		return this.useClassification == UseClassification.UseClass_Use;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((overtimeNo == null) ? 0 : overtimeNo.hashCode());
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
		Overtime other = (Overtime) obj;
		if (overtimeNo != other.overtimeNo)
			return false;
		return true;
	}

}
