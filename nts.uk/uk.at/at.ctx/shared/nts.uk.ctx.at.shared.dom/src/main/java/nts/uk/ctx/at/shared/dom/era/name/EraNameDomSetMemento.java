package nts.uk.ctx.at.shared.dom.era.name;

import nts.arc.time.GeneralDate;

/**
 * The Interface EraNameDomSetMemento.
 */
public interface EraNameDomSetMemento {
	
	/**
	 * Sets the era name id.
	 *
	 * @param eraNameId the new era name id
	 */
	void setEraNameId(String eraNameId);

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	void setEndDate(GeneralDate endDate);

	/**
	 * Sets the era name.
	 *
	 * @param eraName the new era name
	 */
	void setEraName(EraName eraName);

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	void setStartDate(GeneralDate startDate);

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	void setSymbol(SymbolName symbol);

	/**
	 * Sets the system type.
	 *
	 * @param systemType the new system type
	 */
	void setSystemType(SystemType systemType);

}
