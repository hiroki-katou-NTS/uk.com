package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 作業パレットの表示情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業パレット.作業パレットの表示情報
 * @author dan_pv
 *
 */
@Value
public class TaskPaletteDisplayInfo implements DomainValue{
	
	/**
	 * 名称
	 */
	private final TaskPaletteName name;
	
	/**
	 * 備考
	 */
	private final Optional<TaskPaletteRemark> remark;

}
