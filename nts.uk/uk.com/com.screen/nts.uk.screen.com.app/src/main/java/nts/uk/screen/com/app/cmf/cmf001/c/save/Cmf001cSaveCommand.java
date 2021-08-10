package nts.uk.screen.com.app.cmf.cmf001.c.save;

import lombok.Value;
import nts.uk.ctx.exio.app.input.setting.assembly.revise.ReviseItemDto;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

@Value
public class Cmf001cSaveCommand {
	
	String settingCode;
	int itemNo;
	
	String mappingSource;
	String fixedValue;
	ReviseItemDto.RevisingValue revisingValue;
	
	public ExternalImportCode getExternalImportCode() {
		return new ExternalImportCode(settingCode);
	}
	
	public boolean isCsvMapping() {
		return "CSV".equals(mappingSource);
	}
	
	public boolean isFixedValue() {
		return "固定値".equals(mappingSource);
	}
}
