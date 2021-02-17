package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 作業パレット備考
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業パレット.作業パレット備考
 * @author dan_pv
 *
 */
@StringMaxLength(200)
public class TaskPaletteRemark extends StringPrimitiveValue<TaskPaletteRemark>{

	private static final long serialVersionUID = -571966232832279013L;

	public TaskPaletteRemark(String rawValue) {
		super(rawValue);
	}

}
