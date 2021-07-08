package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;

/**
 * 日付型編集
 */
@AllArgsConstructor
public class DateRevise implements ReviseValue {
	
	/** 日付形式 */
	private ExternalImportDateFormat dateFormat;
	
	@Override
	public Object revise(String target) {
		//「値」が指定した書式に合致するか判別
		return this.dateFormat.fromString(target);
	}
}
