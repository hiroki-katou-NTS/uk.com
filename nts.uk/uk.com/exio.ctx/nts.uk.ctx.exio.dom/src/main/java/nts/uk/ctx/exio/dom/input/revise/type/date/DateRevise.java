package nts.uk.ctx.exio.dom.input.revise.type.date;

import nts.uk.ctx.exio.dom.input.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;

/**
 * 日付型編集
 */
public class DateRevise implements ReviseValue {
	
	/** 日付形式 */
	private ExternalImportDateFormat dateFormat;
	
	@Override
	public RevisedValueResult revise(String target) {
		//「値」が指定した書式に合致するか判別
		return RevisedValueResult.succeeded(this.dateFormat.fromString(target));
	}
}
