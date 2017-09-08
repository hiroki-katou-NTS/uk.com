/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.breakdown.language;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.overtime.language.LanguageId;

/**
 * The Class OvertimeLangBRDItem.
 */
// 時間外超過の内訳項目の他言語表示名
@Getter
public class OvertimeLangBRDItem extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private CompanyId companyId;


	/** The name. */
	// 名称
	private BreakdownItemName name;
	
	/** The language id. */
	// 言語ID
	private LanguageId languageId;
	
	/** The breakdown item no. */
	// 超過時間NO
	private BreakdownItemNo breakdownItemNo;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breakdownItemNo == null) ? 0 : breakdownItemNo.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((languageId == null) ? 0 : languageId.hashCode());
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
		OvertimeLangBRDItem other = (OvertimeLangBRDItem) obj;
		if (breakdownItemNo != other.breakdownItemNo)
			return false;
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
		return true;
	}
	
	
}
