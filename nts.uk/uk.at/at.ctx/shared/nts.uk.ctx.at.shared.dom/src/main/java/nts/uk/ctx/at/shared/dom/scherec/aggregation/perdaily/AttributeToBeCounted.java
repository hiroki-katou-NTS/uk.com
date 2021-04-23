package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import lombok.Value;

/**
 * カウントされる属性
 * @author kumiko_otake
 *
 * @param <T>
 */
@Value
public class AttributeToBeCounted<T> {

	/** カウントされる属性 **/
	private final T attribute;
	/** カウント対象か **/
	private final boolean isIncluded;

}
