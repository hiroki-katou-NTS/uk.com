package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import java.util.Arrays;
import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

/**
 * 正準化の中間結果
 * 履歴の開始日のように、その値自体が正準化によって変更される場合もあり、その場合はbeforeとafterに同じ項目Noで変更前後の値が入る
 */
@Value
public class IntermediateResult {
	
	int rowNo;
	
	/** 正準化によって作られたデータ */
	CanonicalItemList itemsAfterCanonicalize;
	
	/** 編集済みデータ */
	RevisedDataRecord itemsNotCanonicalize;
	
	public static IntermediateResult noChange(RevisedDataRecord revisedData) {
		
		return new IntermediateResult(
				revisedData.getRowNo(),
				new CanonicalItemList(),
				revisedData);
	}
	
	/**
	 * 作る
	 * @param source 全部入り
	 * @param canonicalizedItems 正準化された項目のリスト
	 * @param targetItemNos 正準化対象となる項目Noのリスト
	 * @return
	 */
	public static IntermediateResult create(RevisedDataRecord source, CanonicalItem canonicalizedItem) {
		return create(source, new CanonicalItemList(Arrays.asList(canonicalizedItem)));
	}
	
	/**
	 * 新しくインスタンスを生成する
	 * @param source 全部入り
	 * @param canonicalizedItems 正準化された項目のリスト
	 * @param targetItemNos 正準化対象となる項目Noのリスト
	 * @return
	 */
	public static IntermediateResult create(RevisedDataRecord source, CanonicalItemList canonicalizedItems) {
		return new IntermediateResult(source.getRowNo(), canonicalizedItems, source);
	}
	
	/**
	 * 任意項目の既定値を指定する（受け入れていなければ指定した既定値を埋める）
	 * @param item 既定値
	 * @return
	 */
	public IntermediateResult optionalItem(CanonicalItem item) {
		boolean accepted = this.getItemByNo(item.getItemNo())
				.map(e -> e.getValue()).orElse(null) != null;
		
		if (!accepted) {
			return addCanonicalized(item);
		}
		
		return this;
	}
	
	/**
	 * 現在の保持内容に対して新たに正準化した分を追加する
	 * @param canonicalizedItems
	 * @param targetItemNos
	 * @return
	 */
	public IntermediateResult addCanonicalized(CanonicalItemList canonicalizedItems) {

		val after = new CanonicalItemList();
		after.addAll(itemsAfterCanonicalize);
		after.addAll(canonicalizedItems);
		
		return new IntermediateResult(this.rowNo, after, itemsNotCanonicalize);
	}
	
	/**
	 * 現在の保持内容に対して新たに正準化した分を追加する
	 * @param canonicalizedItem
	 * @param targetItemNos
	 * @return
	 */
	public IntermediateResult addCanonicalized(CanonicalItem canonicalizedItem) {
		return addCanonicalized(new CanonicalItemList().addItem(canonicalizedItem));
	}
	
	/**
	 * 指定した項目のデータを取り出す
	 * afterとbeforeの両方に入っている場合は、afterの値が優先
	 * @param itemNo
	 * @return
	 */
	public Optional<CanonicalItem> getItemByNo(int itemNo) {
		
		return itemsAfterCanonicalize.getItemByNo(itemNo)
				.map(item -> Optional.of(item))
				.orElseGet(() -> (itemsBeforeCanonicalize.getItemByNo(itemNo).map(CanonicalItem::of)))
				.map(item -> Optional.of(item))
				.orElseGet(() -> itemsNotCanonicalize.getItemByNo(itemNo).map(CanonicalItem::of));
	}
	
	/**
	 * 指定した項目を受け入れているか
	 * @param itemNo
	 * @return
	 */
	public boolean isImporting(int itemNo) {
		return getItemByNo(itemNo).map(e -> e.getValue()).orElse(null) != null;
	}
	
	public CanonicalizedDataRecord complete() {
		return new CanonicalizedDataRecord(
				this.rowNo,
				itemsAfterCanonicalize,
				CanonicalItemList.of(itemsBeforeCanonicalize),
				CanonicalItemList.of(itemsNotCanonicalize));
	}
}
