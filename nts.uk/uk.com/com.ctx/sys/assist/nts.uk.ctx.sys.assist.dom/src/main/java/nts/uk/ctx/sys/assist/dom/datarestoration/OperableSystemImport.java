package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperableSystemImport {
	// オフィスヘルパー
	private boolean officeHelper;
	// 人事システム
	private boolean humanResource;
	// 勤怠システム
	private boolean attendance;
	// 給与システム
	private boolean salary;
}
