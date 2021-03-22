package nts.uk.cnv.app.td.command.event.accept;

import java.util.List;

import lombok.Value;
import nts.uk.cnv.dom.td.event.EventDetail;

/**
 * 検収する
 * @param tableDefinition
 */
@Value
public class AcceptCommand {
	private String deliveryEventId;
	private EventDetail meta;
//	private List<String> alterationIds;
}
