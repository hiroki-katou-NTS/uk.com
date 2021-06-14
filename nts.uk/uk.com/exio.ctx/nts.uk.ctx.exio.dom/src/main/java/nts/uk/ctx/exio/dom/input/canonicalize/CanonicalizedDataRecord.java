package nts.uk.ctx.exio.dom.input.canonicalize;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

/**
 * 正準化データ1行分
 */
@Value
public class CanonicalizedDataRecord {

	/** 受入行番号 */
	int rowNo;

	/** 正準化したデータ */
	DataItemList itemsAfterCanonicalize;
	
	/** 正準化対象の正準化前データ */
	DataItemList itemsBeforeCanonicalize;
	
	/** 正準化対象ではないデータ */
	DataItemList itemsNotCanonicalize;
	
	public static CanonicalizedDataRecord noChange(RevisedDataRecord revisedData) {
		
		return new CanonicalizedDataRecord(
				revisedData.getRowNo(),
				new DataItemList(),
				new DataItemList(),
				new DataItemList(revisedData.getItems()));
	}
}
