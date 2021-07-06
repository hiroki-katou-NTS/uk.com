package nts.uk.ctx.exio.dom.input.canonicalize;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.Value;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.exio.dom.input.DataItem;
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
	
	/**
	 * 指定された項目NoのDataItemを返す(beforeよりもafter優先)
	 * @return
	 */
	public Optional<DataItem> getItemByNo(int itemNo) {
		
		Stream<DataItemList> lists = Stream.of(
				itemsAfterCanonicalize,  // NotにはSIDなどにNULLが入っているので、Notより前にしないとダメ
				itemsNotCanonicalize,
				itemsBeforeCanonicalize);
		
		return lists
				.map(l -> l.getItemByNo(itemNo))
				.filter(opt -> opt.isPresent())
				.flatMap(OptionalUtil::stream)
				.findFirst();
	}
}
