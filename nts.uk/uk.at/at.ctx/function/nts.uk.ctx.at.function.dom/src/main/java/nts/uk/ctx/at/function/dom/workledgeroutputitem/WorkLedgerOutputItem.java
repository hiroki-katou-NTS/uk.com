package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;

/**
 * AggregateRoot: 勤務台帳の出力項目
 * @author khai.dh
 */
@AllArgsConstructor
@Getter
public class WorkLedgerOutputItem extends AggregateRoot{
	// ID: GUID
	private final String id;

    // コード: 出力項目設定コード
    private OutputItemSettingCode code;

    // 名称: 出力項目設定名称
    private OutputItemSettingName name;

    // 定型自由区分: 帳票共通の設定区分
	private SettingClassificationCommon standardFreeClassification;

    // 社員ID: 社員ID
    private String employeeId;

	// 印刷する勤怠項目リスト: 印刷する勤怠項目
    private List<Integer> outputItemList;

    // [C-0] 年間勤務台帳の出力設定を作成する

	/**
	 * [1]　定型選択の重複をチェックする
	 * @param require 	@Require
	 * @param code 		コード
	 */
    boolean checkDuplicateStandardSelection(Require require, OutputItemSettingCode code){
        return require.standardCheck(code);
    }

	/**
	 * [2]　自由設定の重複をチェックする
	 * @param require 		@Require
	 * @param code 			コード
	 * @param employeeId	社員ID
	 */
    boolean checkDuplicateFreeSettings(Require require, OutputItemSettingCode code, String employeeId){
        return require.freeCheck(code, employeeId);
    }

    public interface Require{
		/**
		 * [R-1]　定型をチェックする
		 * 勤務台帳の出力項目Repository. exist(コード、ログイン会社ID)
		 */
		boolean standardCheck(OutputItemSettingCode code);

		/**
		 * [R-2]  自由をチェックする
		 * 勤務台帳の出力項目Repository. exist(コード、ログイン会社ID、ログイン社員ID)
		 */
        boolean freeCheck(OutputItemSettingCode code, String employeeId);
    }
}
