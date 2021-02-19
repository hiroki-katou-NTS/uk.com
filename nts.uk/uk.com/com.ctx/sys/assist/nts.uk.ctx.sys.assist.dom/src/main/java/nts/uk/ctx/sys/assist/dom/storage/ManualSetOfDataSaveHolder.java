package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManualSetOfDataSaveHolder {
	private ManualSetOfDataSave domain;
	private String patternCode;
}
