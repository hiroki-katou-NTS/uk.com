/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.dom.company;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class Company extends AggregateRoot {

	/** The company code. */
	// 会社コード
	private CompanyCode companyCode;

	/** The company code. */
	// 会社名
	private CompanyName companyName;

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The start month. */
	// 期首月
	private StartMonth startMonth;

	/** The Abolition */
	// 廃止区分
	private IsAbolition isAbolition;

	/** システム利用設定 - System Use Setting */
	private SystemUseSetting systemUseSetting;

	/**
	 * Instantiates a new company.
	 *
	 * @param memento
	 *            the memento
	 */
	public Company(CompanyGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.companyName = memento.getCompanyName();
		this.companyId = memento.getCompanyId();
		this.startMonth = memento.getStartMonth();
	}
	
	

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompanySetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setCompanyName(this.companyName);
		memento.setCompanyId(this.companyId);
		memento.setStartMonth(this.startMonth);
	}

	/**
	 * Instantiates a new company.
	 * 
	 * @param companyCode
	 * @param companyName
	 * @param companyId
	 * @param isAbolition
	 */
	public Company(CompanyCode companyCode, CompanyName companyName, CompanyId companyId, IsAbolition isAbolition,
			SystemUseSetting systemUseSetting) {
		super();
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.companyId = companyId;
		this.isAbolition = isAbolition;
		this.systemUseSetting = systemUseSetting;
	}

	public static Company createFromJavaType(String companyCode, String companyName, String companyId, int isAbolition,
			int personSystem, int employmentSystem, int payrollSystem) {
		return new Company(new CompanyCode(companyCode), 
				           new CompanyName(companyName), 
				           new CompanyId(companyId),
				           EnumAdaptor.valueOf(isAbolition, IsAbolition.class),
				           new SystemUseSetting(EnumAdaptor.valueOf(personSystem, SystemUseClassification.class),
						   EnumAdaptor.valueOf(employmentSystem, SystemUseClassification.class),
						   EnumAdaptor.valueOf(payrollSystem, SystemUseClassification.class)));
	}

	public Company() {
		super();
	}
}
