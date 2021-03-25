package nts.uk.cnv.app.td.command.event.accept;

import lombok.Value;

/**
 * 検収する
 * @param tableDefinition
 */
@Value
public class AcceptCommand {
	private String deliveryEventId;
	private String userName;
}
