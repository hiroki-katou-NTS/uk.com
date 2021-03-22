package nts.uk.cnv.app.td.command.event.delivery;

import java.util.List;

import lombok.Value;
import nts.uk.cnv.dom.td.event.EventDetail;

/**
 * 納品する
 * @param tableDefinition
 */
@Value
public class DeliveryCommand {
	private String featureId;
	private EventDetail meta;
	private List<String> alterationIds;
}
