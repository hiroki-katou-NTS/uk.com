package nts.uk.ctx.office.dom.equipment;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.equipment.information.EquipmentCode;
import nts.uk.ctx.office.dom.equipment.information.EquipmentInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.利用日が有効期限内かチェックする
 */
public class CheckValidUseDateDomainService {

	/**
	 * [1] 有効期限内か
	 * 
	 * @param require
	 * @param cid			会社ID
	 * @param equipmentCode	設備コード
	 * @param useDate		利用日
	 * @return
	 */
	public static boolean validate(Require require, String cid, EquipmentCode equipmentCode, GeneralDate useDate) {
		// $設備情報　＝　require.設備情報を取得する(ログイン会社ID,設備コード)
		Optional<EquipmentInformation> optEquipmentInfo = require.getEquipmentInfo(cid, equipmentCode.v());
		// if　$設備情報.isEmpty()　||　$設備情報.有効期限内か(利用日)　==　false
		if (!optEquipmentInfo.isPresent() || !optEquipmentInfo.get().isValid(useDate)) {
			return false;
		}
		return true;
	}

	public static interface Require {

		// 設備情報Repository.Get(会社ID,設備コード)
		Optional<EquipmentInformation> getEquipmentInfo(String cid, String equipmentCode);
	}
}
