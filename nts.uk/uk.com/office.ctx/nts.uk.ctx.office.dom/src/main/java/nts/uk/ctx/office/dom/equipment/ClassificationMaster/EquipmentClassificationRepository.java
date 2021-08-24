package nts.uk.ctx.office.dom.equipment.ClassificationMaster;

import java.util.List;

/**
 * 設備分類Repository
 * @author NWS-DungDV
 *
 */
public interface EquipmentClassificationRepository {
	
	/**
	 * [1] Insert(設備分類)
	 * @param domain 設備分類
	 */
	void insert(EquipmentClassification domain);
	
	/**
	 * [2] Update(設備分類)
	 * @param domain 設備分類
	 */
	void update(EquipmentClassification domain);
	
	/**
	 * [3] Delete(契約コード, 設備分類コード)
	 * @param contractCd 契約コード
	 * @param equipmentClsCd 設備分類コード
	 */
	void delete(String contractCd, String equipmentClsCd);
	
	/**
	 * [4] Get*
	 * 全ての設備分類を取得する
	 * @param contractCd 契約コード
	 * @return 設備分類リスト
	 */
	List<EquipmentClassification> getAll(String contractCd);

	/**
	 * [5] Get
	 * 指定する設備分類コードの設備分類を取得する
	 * @param contractCd 契約コード
	 * @param clsCd コード
	 * @return 設備分類リスト
	 */
	List<EquipmentClassification> getByClassificationCode(String contractCd, String clsCd);
}
