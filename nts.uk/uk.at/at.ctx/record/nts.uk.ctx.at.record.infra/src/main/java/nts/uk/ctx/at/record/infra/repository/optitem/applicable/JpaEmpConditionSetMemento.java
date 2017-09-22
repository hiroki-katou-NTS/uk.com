/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.applicable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmploymentCondition;
import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpCon;
import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpConPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaEmpConditionSetMemento.
 */
public class JpaEmpConditionSetMemento implements EmpConditionSetMemento {

	/** The company id. */
	private String companyId;

	/** The opt no. */
	private String optNo;

	/** The type values. */
	private List<KrcstApplEmpCon> typeValues;

	/**
	 * Instantiates a new jpa emp condition set memento.
	 *
	 * @param typeValues
	 *            the type values
	 */
	public JpaEmpConditionSetMemento(List<KrcstApplEmpCon> typeValues) {
		this.typeValues = typeValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		this.companyId = comId.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optNo) {
		this.optNo = optNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setEmpConditions(java.util.List)
	 */
	@Override
	public void setEmpConditions(List<EmploymentCondition> empConditions) {
		List<KrcstApplEmpCon> typeValues = new ArrayList<>();

		Map<String, KrcstApplEmpConPK> krcstApplEmpConPKMap = this.typeValues.stream()
				.collect(Collectors.toMap(entity -> entity.getKrcstApplEmpConPK().getEmpCd(),
						KrcstApplEmpCon::getKrcstApplEmpConPK));

		empConditions.stream().forEach(item -> {
			KrcstApplEmpConPK krcstApplEmpConPK = krcstApplEmpConPKMap.get(item.getEmpCd());

			// Check PK exist
			if (krcstApplEmpConPK == null) {
				krcstApplEmpConPK = new KrcstApplEmpConPK();
				krcstApplEmpConPK.setCid(this.companyId);
				krcstApplEmpConPK.setOptionalItemNo(this.optNo);
				krcstApplEmpConPK.setEmpCd(item.getEmpCd());
			}

			KrcstApplEmpCon krcstApplEmpCon = new KrcstApplEmpCon();
			krcstApplEmpCon.setKrcstApplEmpConPK(krcstApplEmpConPK);
			krcstApplEmpCon.setEmpApplAtr(item.getEmpApplicableAtr().value);
			typeValues.add(krcstApplEmpCon);
		});

		this.typeValues = typeValues;
	}

}
