package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.WorkInformation;
/**
 * DS: シフトマスタを更新する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.シフトマスタ.シフトマスタを更新する
 * @author tutk
 *
 */
public class UpdateShiftMasterService {
	/**
	 * 更新する
	 * @param requireWorkInfo
	 * @param require
	 * @param shiftMaterCode コード
	 * @param displayInfor 表示情報
	 * @param workInformation 勤務情報
	 * @param importCode 取り込みコード
	 * @return
	 */
	public static AtomTask updateShiftMater(Require require
			, String shiftMaterCode, ShiftMasterDisInfor displayInfor
			, WorkInformation workInformation, ShiftMasterImportCode importCode) {
		// 1:get(会社ID, コード):Optional<シフトマスタ>
		val shiftMaterOpt = require.getByShiftMaterCd(shiftMaterCode); //truyen cid tư app
		val shiftMaster = shiftMaterOpt.get();
		
		if(!shiftMaster.getImportCode().equals(importCode) && require.checkDuplicateImportCode(importCode)) {
			throw new BusinessException("Msg_2163");
		}
		
		// 2: 変更する(シフトマスタの表示情報, 勤務情報)
		shiftMaster.change(displayInfor, importCode, workInformation);
		// エラーチェックする
		shiftMaster.checkError(require);
		Optional<ShiftMaster> shiftMaterByWorkTypeAndWorkTime = require.getByWorkTypeAndWorkTime(//truyen cid tư app
				workInformation.getWorkTypeCode().v(),
				workInformation.getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null));
		if (shiftMaterByWorkTypeAndWorkTime.isPresent()
				&& !shiftMaterByWorkTypeAndWorkTime.get().getShiftMasterCode().v().equals(shiftMaterCode)) {
			throw new BusinessException("Msg_1610");
		}
		// 3:persist
		return AtomTask.of(() -> {
			require.update(shiftMaster);
		});
	}

	public static interface Require extends ShiftMaster.Require {
		/**
		 * 取得する
		 * @param shiftMaterCode シフトマスタコード
		 * @return
		 */
		Optional<ShiftMaster> getByShiftMaterCd(String shiftMaterCode);
		
		/**
		 * 勤務情報で取得する
		 * @param workTypeCd 勤務種類コード
		 * @param workTimeCd 就業時間帯コード
		 * @return
		 */
		Optional<ShiftMaster> getByWorkTypeAndWorkTime(String workTypeCd, String workTimeCd);

		/**
		 * 登録する
		 * @param shiftMater シフトマスタ
		 */
		void update(ShiftMaster shiftMater);
		
		/**
		 * 取り込みコードが重複しているか
		 * @param importCode
		 * @return
		 */
		boolean checkDuplicateImportCode(ShiftMasterImportCode importCode);
	}
}
