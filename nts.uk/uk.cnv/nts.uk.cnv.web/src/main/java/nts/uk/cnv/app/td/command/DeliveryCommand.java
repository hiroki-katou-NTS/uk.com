package nts.uk.cnv.app.td.command;

import java.util.List;

import lombok.Value;
import nts.uk.cnv.dom.td.event.EventMetaData;

/**
 * 納品する
 * @param tableDefinition
 */
@Value
public class DeliveryCommand {
	private String featureId;
	private EventMetaData meta;
	private List<String> alterationIds;
}
