/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.applicable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;

/**
 * The Class ApplicableEmpCondition.
 */
// 適用する雇用条件
@Getter
public class EmpCondition extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	// 任意項目NO
	private OptionalItemNo optItemNo;

	// 雇用条件
	private List<EmploymentCondition> empConditions;

	/**
	 * Instantiates a new applicable emp condition.
	 *
	 * @param memento the memento
	 */
	public EmpCondition(EmpConditionGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.optItemNo = memento.getOptionalItemNo();
		this.empConditions = memento.getEmploymentConditions();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmpConditionSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOptionalItemNo(this.optItemNo);
		memento.setEmpConditions(this.empConditions);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((optItemNo == null) ? 0 : optItemNo.hashCode());
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
		EmpCondition other = (EmpCondition) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (optItemNo == null) {
			if (other.optItemNo != null)
				return false;
		} else if (!optItemNo.equals(other.optItemNo))
			return false;
		return true;
	}
	
	
	/**
	 * 適用する雇用条件
	 * @return
	 */
	public boolean checkEmpCondition(Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt) {
		if(bsEmploymentHistOpt.isPresent()) {
			//Imported「所属雇用履歴」を取得
			String code = bsEmploymentHistOpt.get().getEmploymentCode();
			
			List<String> codeList = new ArrayList<>();
			codeList = this.empConditions.stream().filter(ec -> ec.getEmpApplicableAtr()==EmpApplicableAtr.APPLY).map(ec->ec.getEmpCd()).collect(Collectors.toList());
			if(codeList.contains(code)) {
				return true;
			}
		}
		return false;
	}
	

}
