package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import java.util.Map;
import java.util.Optional;

import lombok.Value;

/**
 * 作業パレット
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業パレット.作業パレット
 * @author dan_pv
 *
 */
@Value
public class TaskPalette {

	/**
	 * ページ
	 */
	private final int page;
	
	/**
	 * 名称
	 */
	private final TaskPaletteName name;
	
	/**
	 * 作業パレットの作業
	 */
	private final Map<Integer, TaskPaletteOneFrameDisplayInfo> tasks;
	
	/**
	 * 備考
	 */
	private final Optional<TaskPaletteRemark> remark;
	
}
