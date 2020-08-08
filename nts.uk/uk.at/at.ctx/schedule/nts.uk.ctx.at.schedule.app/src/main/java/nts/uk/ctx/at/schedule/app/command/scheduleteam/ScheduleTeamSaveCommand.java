package nts.uk.ctx.at.schedule.app.command.scheduleteam;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author quytb
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTeamSaveCommand {
	/** チームコード */
	private String code;
	
	/** チーム名称 */
	private String name;
	
	/** チーム備考 */
	private String note;
	
	/** 職場グループID */
	private String workplaceGroupId;
	
	/** 社員リスト */
	private List<String> employeeIds;
}
