package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import lombok.Data;

@Data
public class TextResourceHolderDto {
	private final String textToFormat;
	private final String[] params;
	
	public TextResourceHolderDto(String textToFormat, String... params) {
		super();
		this.textToFormat = textToFormat;
		this.params = params;
	}
}
