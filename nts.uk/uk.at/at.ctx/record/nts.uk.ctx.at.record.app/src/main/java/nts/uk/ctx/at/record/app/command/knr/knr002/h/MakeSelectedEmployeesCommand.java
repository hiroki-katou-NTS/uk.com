package nts.uk.ctx.at.record.app.command.knr.knr002.h;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author xuannt
 *
 */
@Value
public class MakeSelectedEmployeesCommand {
	//	就業情報端末コード
	private String terminalCode;
	//	選択した社員ID<List>
	private List<String> selectedEmployeesID;
}
