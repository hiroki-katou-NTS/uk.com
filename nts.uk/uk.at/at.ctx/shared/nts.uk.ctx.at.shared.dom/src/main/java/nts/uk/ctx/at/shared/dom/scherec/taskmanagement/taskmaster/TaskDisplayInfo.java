package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.color.ColorCode;

/**
 * 作業表示情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業表示情報
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class TaskDisplayInfo {
	
	/** 名称　 */
	private TaskName taskName;
	
	/** 略名　*/
	private TaskAbName taskAbName;
	
	/** 作業色　*/
	private Optional<ColorCode> color;
	
	/** 備考　*/
	private Optional<TaskNote> taskNote;

}
