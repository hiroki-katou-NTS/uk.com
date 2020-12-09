package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck;

import java.util.List;

public interface MasterCheckFixedExtractItemRepository {
	
	List<MasterCheckFixedExtractItem> getAllFixedMasterCheckItem();
	/**
	 * No一覧からマスタチェックの固有抽出項目を取得 
	 * @param itemNo
	 * @return
	 */
	List<MasterCheckFixedExtractItem> getFixedMasterCheckByNo(List<Integer> lstItemNo);
}
