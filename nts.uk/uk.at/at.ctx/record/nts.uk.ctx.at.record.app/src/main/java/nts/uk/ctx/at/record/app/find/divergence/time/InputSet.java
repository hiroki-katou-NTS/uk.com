package nts.uk.ctx.at.record.app.find.divergence.time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InputSet {

	/** The select use set. */
	private int selectUseSet;

	/** The cancel err sel reason. */
	private int cancelErrSelReason;

	/**
	 * Convert type.
	 *
	 * @param selectUseSet
	 *            the select use set
	 * @param cancelErrSelReason
	 *            the cancel err sel reason
	 * @return the input set
	 */
	public static InputSet convertType(int selectUseSet, int cancelErrSelReason) {
		return new InputSet(selectUseSet, cancelErrSelReason);
	}
}
