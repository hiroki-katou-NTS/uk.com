package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

/**
 * 
 * @author chungnt
 *
 */

public class StampCardEditingHelper {

	public static StampCardEditing getDefault() {
		return new StampCardEditing("000-0000000001",
				new StampCardDigitNumber(5),
				StampCardEditMethod.PreviousSpace);
	}
	
}
