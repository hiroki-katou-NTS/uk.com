/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;

/**
 * The Class OvertimeNameLang.
 */
// 超過時間の他言語表示名

@Getter
public class OvertimeNameLang extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The name. */
	// 名称
	private OvertimeName name;
	
	/** The language id. */
	// 言語ID
	private LanguageId languageId;
	
	/** The overtime no. */
	// 超過時間NO
	private OvertimeNo overtimeNo;
	
	/**
	 * Instantiates a new overtime lang name.
	 *
	 * @param memento the memento
	 */
	public OvertimeNameLang(OvertimeNameLangGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.name = memento.getName();
		this.languageId = memento.getLanguageId();
		this.overtimeNo = memento.getOvertimeNo();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OvertimeNameLangSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setName(this.name);
		memento.setLanguageId(this.languageId);
		memento.setOvertimeNo(this.overtimeNo);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((languageId == null) ? 0 : languageId.hashCode());
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
		OvertimeNameLang other = (OvertimeNameLang) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (languageId == null) {
			if (other.languageId != null)
				return false;
		} else if (!languageId.equals(other.languageId))
			return false;
		if (overtimeNo != other.overtimeNo)
			return false;
		return true;
	}
	
	
	
}
