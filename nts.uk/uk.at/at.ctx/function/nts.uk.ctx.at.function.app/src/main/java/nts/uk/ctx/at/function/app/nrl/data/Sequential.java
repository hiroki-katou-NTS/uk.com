package nts.uk.ctx.at.function.app.nrl.data;

import java.util.List;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.response.MeanCarryable;

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
