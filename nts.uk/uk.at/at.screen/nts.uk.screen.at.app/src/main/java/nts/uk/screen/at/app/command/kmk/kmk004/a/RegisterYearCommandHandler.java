package nts.uk.screen.at.app.command.kmk.kmk004.a;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_労働時間と日数の設定（旧）.2次_KMK004_基準時間の登録.Q：年別設定の編集（通常勤務、変形労働）.アルゴリズム.登録ボタン押下時処理.更新登録処理.Q：更新登録処理
 */
public class RegisterYearCommandHandler extends CommandHandler<RegisterYearCommand> {

	@Override
	protected void handle(CommandHandlerContext<RegisterYearCommand> context) {
		RegisterYearCommand cmd = context.getCommand();
		if (cmd.isUpdateMode()) {
			// 更新モードの場合
			UpdateYearProcess.updateProcess(cmd);
		} else {
			// 新規モードの場合
			AddNewYearProcess.addNewProcess(cmd);
		}
	}

}
