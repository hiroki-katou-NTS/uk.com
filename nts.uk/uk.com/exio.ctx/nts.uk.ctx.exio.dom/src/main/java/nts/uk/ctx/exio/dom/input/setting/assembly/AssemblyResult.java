package nts.uk.ctx.exio.dom.input.setting.assembly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.revise.RevisedItemResult;

/**
 * 組み立て結果
 */
@Getter
@AllArgsConstructor
public class AssemblyResult {
	
	/** CSVの受入データ */
	private final DataItemList assemblyItem;
	
	/** 項目の編集結果 */
	private final List<RevisedItemResult> revisedItem;
	
	/**
	 * 不正なデータが存在するか
	 * @return
	 */
	public boolean isIncorrectData() {
		for(int i = 0; i < revisedItem.size(); i++) {
			// 不正なデータを探索
			if(!revisedItem.get(i).isSuccess()) {
				return true;
			}
		}
		return false;
	}
}
