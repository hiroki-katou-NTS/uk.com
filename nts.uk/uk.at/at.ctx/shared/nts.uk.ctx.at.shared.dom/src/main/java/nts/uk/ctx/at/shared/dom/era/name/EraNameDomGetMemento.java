package nts.uk.ctx.at.shared.dom.era.name;

import nts.arc.time.GeneralDate;

/**
 * The Interface EraNameDomGetMemento.
 */
public interface EraNameDomGetMemento {

	/**
	 * Gets the era name id.
	 *
	 * @return the era name id
	 */
	String getEraNameId();

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	GeneralDate getEndDate();

	/**
	 * Gets the era name.
	 *
	 * @return the era name
	 */
	EraName getEraName();

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	GeneralDate getStartDate();

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	SymbolName getSymbol();

	/**
	 * Gets the system type.
	 *
	 * @return the system type
	 */
	SystemType getSystemType();

}
