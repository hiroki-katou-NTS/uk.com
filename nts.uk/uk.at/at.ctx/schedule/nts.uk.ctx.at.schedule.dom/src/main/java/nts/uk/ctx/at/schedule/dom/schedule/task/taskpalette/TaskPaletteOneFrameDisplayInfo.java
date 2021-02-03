package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskAbName;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskName;

/**
 * 作業パレットの一枠分の表示情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業パレット.作業パレットの一枠分の表示情報
 * @author dan_pv
 *
 */
@Value
public class TaskPaletteOneFrameDisplayInfo {
	
	/**
	 * 作業コード
	 */
	private final TaskCode taskCode;
	
	/**
	 * 作業状態
	 */
	private final TaskStatus taskStatus;
	
	/**
	 * 作業名称
	 */
	private final Optional<TaskName> taskName;

	/**
	 * 作業略名
	 */
	private final Optional<TaskAbName> taskAbName;
	
	
	/**
	 * 使用可能作業を作る
	 * @param taskCode 作業コード
	 * @param taskName 作業名称
	 * @param taskAbName 作業略名
	 * @return
	 */
	public static TaskPaletteOneFrameDisplayInfo createWithCanUseType(
			TaskCode taskCode,
			TaskName taskName,
			TaskAbName taskAbName
			) {
		return new TaskPaletteOneFrameDisplayInfo(taskCode, TaskStatus.CanUse, Optional.of( taskName), Optional.of(taskAbName));
	}
	
	/**
	 * マスタ未登録を作る
	 * @param taskCode 作業コード
	 * @return
	 */
	public static TaskPaletteOneFrameDisplayInfo createWithNotYetRegisteredType( TaskCode taskCode ) {
		return new TaskPaletteOneFrameDisplayInfo(taskCode, TaskStatus.NotYetRegistered, Optional.empty(), Optional.empty());
	}
	
	/**
	 * 期限切れ作業を作る
	 * @param taskCode 作業コード
	 * @return
	 */
	public static TaskPaletteOneFrameDisplayInfo createWithExpiredType( TaskCode taskCode ) {
		return new TaskPaletteOneFrameDisplayInfo(taskCode, TaskStatus.Expired, Optional.empty(), Optional.empty());
	}
}
