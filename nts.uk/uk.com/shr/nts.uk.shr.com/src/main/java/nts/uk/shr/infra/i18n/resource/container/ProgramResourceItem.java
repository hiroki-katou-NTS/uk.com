package nts.uk.shr.infra.i18n.resource.container;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProgramResourceItem implements I18NResourceItem {

	private final String programId;
	private final String serialNumber;
	private final String content;

	@Override
	public String identifier() {
		return this.programId + "_" + this.serialNumber;
	}
	
	@Override
	public String content() {
		return this.content;
	}
}
