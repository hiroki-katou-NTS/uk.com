package nts.uk.ctx.at.shared.app.command.worktime.workplace;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 職場割り当て就業時間帯を登録する
 */
@Getter
@Setter
public class RegisterWorkTimeWorkplaceCommand {

	/**
	 * 職場ID
	 */
	private String workplaceId;

	/**
	 * 利用就業時間帯
	 */
	private List<String> workTimeCodes;

}
