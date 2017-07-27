package nts.uk.ctx.at.shared.dom.order;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;

/**
 * 
 * @author sonnh1
 *
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends AggregateRoot {
	private SiftCode siftCode;
	private int order;
}
