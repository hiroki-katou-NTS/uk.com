package nts.uk.ctx.at.record.dom.divergence.time;

import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * Checks if is reason selected.
 *
 * @return true, if is reason selected
 */
@Getter

/**
 * Sets the reason selected.
 *
 * @param reasonSelected
 *            the new reason selected
 */
@Setter
public class DivergenceTimeErrorCancelMethod {

	/** The reason inputed. */
	private boolean reasonInputed;

	/** The reason selected. */
	private boolean reasonSelected;

	/**
	 * Instantiates a new divergence time error cancel method.
	 *
	 * @param reasonInputed
	 *            the reason inputed
	 * @param reasonSelected
	 *            the reason selected
	 */
	public DivergenceTimeErrorCancelMethod(int reasonInputed, int reasonSelected) {
		if (reasonInputed == 0)
			this.reasonInputed = false;
		else
			this.reasonInputed = true;

		if (reasonSelected == 0)
			this.reasonSelected = false;
		else
			this.reasonSelected = true;

	}

	public DivergenceTimeErrorCancelMethod() {

	}

}
