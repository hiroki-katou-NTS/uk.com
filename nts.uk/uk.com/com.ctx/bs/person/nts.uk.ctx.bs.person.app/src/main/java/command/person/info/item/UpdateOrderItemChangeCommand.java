package command.person.info.item;

import java.util.List;

import lombok.Value;

@Value
public class UpdateOrderItemChangeCommand {

	private List<OrderItemChange> orderItemList;
}
