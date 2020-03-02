package nts.uk.ctx.hr.develop.dom.guidance;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*object data for 操作ガイドの取得*/
@AllArgsConstructor
@Getter
public class IGuidanceExportDto {

	private boolean displayMessage;
	
	private Integer numberLinesInMessage;
	
	private String contentMessage;
}
