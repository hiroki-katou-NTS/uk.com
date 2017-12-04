package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.List;

import lombok.Value;

@Value
public class UpdateOrderItemChangeCommand {

	private String categoryId;

	private List<OrderItemChange> orderItemList;
}
