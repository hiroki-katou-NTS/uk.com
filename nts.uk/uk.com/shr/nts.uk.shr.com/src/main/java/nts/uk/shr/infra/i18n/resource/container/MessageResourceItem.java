package nts.uk.shr.infra.i18n.resource.container;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MessageResourceItem implements I18NResourceItem {

	private final String systemId;
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
}
