package nts.uk.cnv.app.td.command.event.order;

import java.util.List;

import lombok.Value;

/**
 * 発注する
 * @param tableDefinition
 */
@Value
public class OrderCommand {
	private String featureId;
	private String name;
	private String userName;
	private List<String> alterationIds;
}
