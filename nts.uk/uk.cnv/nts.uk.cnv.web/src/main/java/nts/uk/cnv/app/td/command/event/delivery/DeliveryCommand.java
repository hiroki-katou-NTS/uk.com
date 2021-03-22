package nts.uk.cnv.app.td.command.event.delivery;

import java.util.List;

import lombok.Value;

/**
 * 納品する
 * @param tableDefinition
 */
@Value
public class DeliveryCommand {
	private String featureId;
	private String name;
	private String userName;
	private List<String> alterationIds;
}
