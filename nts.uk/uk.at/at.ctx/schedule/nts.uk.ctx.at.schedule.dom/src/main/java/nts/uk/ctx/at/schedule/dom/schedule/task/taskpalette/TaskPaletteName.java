package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 作業パレット名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業パレット.作業パレット名称
 * @author dan_pv
 *
 */
@StringMaxLength(10)
public class TaskPaletteName extends StringPrimitiveValue<TaskPaletteName>{

	private static final long serialVersionUID = -6424811880380547242L;

	public TaskPaletteName(String rawValue) {
		super(rawValue);
	}

}
