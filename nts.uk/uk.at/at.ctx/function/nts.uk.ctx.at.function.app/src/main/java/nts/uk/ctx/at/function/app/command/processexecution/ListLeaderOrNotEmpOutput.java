package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ListLeaderOrNotEmpOutput {
	// ・社員ID（異動者、勤務種別変更者のみ）（List）
	List<String> leaderEmpIdList;
	// ・社員ID（異動者、勤務種別変更者のみ）（List）
	List<String> noLeaderEmpIdList;
}
