package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.WorkInformation;

/**
 * DS : シフトマスタを作る
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.シフトマスタ.シフトマスタを作る
 * @author tutk
 *
 */
public class MakeShiftMasterService {

	/**
	 * シフトを作る
	 * @param requireWorkInfo
	 * @param require
	 * @param companyId 会社ID
	 * @param shiftMaterCode コード
	 * @param workTypeCd 勤種コード
	 * @param workTimeCd 就時コード
	 * @param displayInfor 表示情報
	 * @param importCode 取り込みコード
	 * @return
	 */
	public static AtomTask makeShiftMater(WorkInformation.Require requireWorkInfo,
			Require require, String companyId, String shiftMaterCode, String workTypeCd, Optional<String> workTimeCd,
			ShiftMasterDisInfor displayInfor,
			ShiftMasterImportCode importCode) {
		String workTimeCdNew = workTimeCd.isPresent() ? workTimeCd.get() : null;
		
		if(require.checkExistsByCode(companyId, shiftMaterCode)) {
			throw new BusinessException("Msg_3");
		}
		
		if(require.checkDuplicateImportCode(companyId, importCode)) {
			throw new BusinessException("Msg_2163");
		}
		// 1:作る(会社ID, シフトマスタコード, シフトマスタの表示情報, 勤務種類コード, 就業時間帯コード)
		ShiftMaster shiftMater = new ShiftMaster(companyId, new ShiftMasterCode(shiftMaterCode), displayInfor, workTypeCd,
				workTimeCdNew, importCode);
		// 2:エラー状態をチェックする
		shiftMater.checkError(requireWorkInfo);

		if (require.checkExists(companyId, workTypeCd, workTimeCdNew)) {
			throw new BusinessException("Msg_1610");
		}
		// 3:persist
		return AtomTask.of(() -> {
			require.insert(shiftMater, workTypeCd, workTimeCdNew);
		});
	}

	public static interface Require {
		/**
		 * 既に登録されているか
		 * @param companyId 会社ID
		 * @param workTypeCd 勤務種類コード
		 * @param workTimeCd 就業時間帯コード
		 * @return
		 */
		boolean checkExists(String companyId, String workTypeCd, String workTimeCd);
		/**
		 * コードが重複しているか
		 * @param companyId 会社ID
		 * @param shiftMasterCd シフトマスタコード
		 * @return
		 */
		boolean checkExistsByCode(String companyId, String shiftMasterCd);
		/**
		 * 登録する
		 * @param shiftMater シフトマスタコード
		 * @param workTypeCd 勤務種類コード
		 * @param workTimeCd 就業時間帯コード
		 */
		void insert(ShiftMaster shiftMater, String workTypeCd, String workTimeCd);
		
		/**
		 * 取り込みコードが重複しているか
		 * @param companyId
		 * @param importCode
		 * @return
		 */
		boolean checkDuplicateImportCode(String companyId, ShiftMasterImportCode importCode);
	}
	
}
