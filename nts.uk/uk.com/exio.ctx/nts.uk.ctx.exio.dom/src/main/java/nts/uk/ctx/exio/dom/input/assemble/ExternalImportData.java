package nts.uk.ctx.exio.dom.input.assemble;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.input.DataItemList;

/**
 * 1行分の受入データ
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExternalImportData {
	
	/** 1行分のデータ */
	private DataItemList oneRecordData;
	
	public void addItemList(DataItemList itemList) {
		this.oneRecordData.addItemList(itemList);
	}
	
}
