package nts.uk.cnv.app.td.command.event.accept;

import java.util.List;

import lombok.Value;
import nts.uk.cnv.dom.td.event.EventMetaData;

/**
 * 検収する
 * @param tableDefinition
 */
@Value
public class AcceptCommand {
	private String featureId;
	private EventMetaData meta;
	private List<String> alterationIds;
}
