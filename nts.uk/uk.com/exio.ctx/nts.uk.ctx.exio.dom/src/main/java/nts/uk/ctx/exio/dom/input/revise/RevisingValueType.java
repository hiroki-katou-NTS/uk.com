package nts.uk.ctx.exio.dom.input.revise;

import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;

/**
 * 
 * 値の編集インターフェース
 *
 */
public interface RevisingValueType {
	public RevisedValueResult revise(CsvItem target) ;

}
