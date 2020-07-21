package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.14-3.詳細画面の初期モード
 * @author Doan Duy Hung
 *
 */
public interface InitMode {
	
	/**
	 * 14-3.詳細画面の初期モード
	 * @param user 利用者
	 * @param reflectPerState ステータス
	 * @return 画面モード(表示/編集)
	 */
	public OutputMode getDetailScreenInitMode(User user, Integer reflectPerState);

}
