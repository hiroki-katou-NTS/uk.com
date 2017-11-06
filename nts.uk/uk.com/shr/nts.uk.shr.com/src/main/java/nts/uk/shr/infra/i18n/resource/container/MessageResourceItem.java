package nts.uk.shr.infra.i18n.resource.container;

import lombok.RequiredArgsConstructor;
import nts.uk.shr.infra.i18n.resource.I18NResourceType;

@RequiredArgsConstructor
public class MessageResourceItem implements I18NResourceItem {

	private final String messageId;
	private final String content;
	
	@Override
	public String identifier() {
		return this.messageId;
	}
	
	@Override
	public String content() {
		return this.content;
	}

	@Override
	public I18NResourceType resourceType() {
		return I18NResourceType.MESSAGE;
	}
}
