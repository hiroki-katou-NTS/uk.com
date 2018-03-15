package nts.uk.ctx.at.record.app.command.divergence.time.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * Checks if is divergence reason selected.
 *
 * @return true, if is divergence reason selected
 */
@Getter

/**
 * Sets the divergence reason selected.
 *
 * @param divergenceReasonSelected the new divergence reason selected
 */
@Setter

/**
 * Instantiates a new divergence time input method save command.
 *
 * @param divergenceTimeNo the divergence time no
 * @param companyId the company id
 * @param divergenceTimeUseSet the divergence time use set
 * @param divergenceTimeName the divergence time name
 * @param divergenceType the divergence type
 * @param reasonInput the reason input
 * @param reasonSelect the reason select
 * @param divergenceReasonInputed the divergence reason inputed
 * @param divergenceReasonSelected the divergence reason selected
 */
@AllArgsConstructor
public class DivergenceTimeInputMethodSaveCommand {

	/** The divergence time no. */

	private int divergenceTimeNo;

	/** The c id. */

	private String companyId;

	/** The Use classification. */

	private int divergenceTimeUseSet;

	/** The divergence time name. */

	private String divergenceTimeName;

	/** The divergence type. */

	private int divergenceType;

	/** The divergence time error cancel method. */

	private boolean reasonInput;

	/** The reason select. */

	private boolean reasonSelect;

	/** The divergence reason inputed. */

	private boolean divergenceReasonInputed;

	/** The divergence reason selected. */

	private boolean divergenceReasonSelected;

}
