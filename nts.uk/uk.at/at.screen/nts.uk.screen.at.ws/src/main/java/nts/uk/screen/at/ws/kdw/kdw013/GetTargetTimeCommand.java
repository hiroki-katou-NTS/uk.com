package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class GetTargetTimeCommand {
	/** 対象者 */
	private String employeeId;
	/** 変更対象日 */
	private List<GeneralDate> changedDates;
}
