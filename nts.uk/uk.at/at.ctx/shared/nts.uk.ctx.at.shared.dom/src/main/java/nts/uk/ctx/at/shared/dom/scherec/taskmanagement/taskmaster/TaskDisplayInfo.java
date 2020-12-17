package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.color.ColorCode;

/**
 * 作業表示情報
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class TaskDisplayInfo {
	
	/** 名称　 */
	private TaskName taskName;
	
	/** 各名　*/
	private TaskAbName taskAbName;
	
	/** 作業色　*/
	private Optional<ColorCode> color;
	
	/** 備考　*/
	private Optional<TaskNote> taskNote;

}
