package nts.uk.nrl.data;

import java.util.List;

import nts.uk.nrl.Command;
import nts.uk.nrl.data.ItemSequence.MapItem;
import nts.uk.nrl.response.MeanCarryable;

/**
 * Sequential.
 * 
 * @author manhnd
 *
 * @param <T>
 */
public interface Sequential<T extends MeanCarryable> {

	/**
	 * Plot.
	 * @param items
	 * @param command
	 * @return
	 */
	public T plot(List<MapItem> items, Command command);
}
