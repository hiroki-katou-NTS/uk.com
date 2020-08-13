package nts.uk.ctx.at.request.dom.application.common.service.print;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.印刷内容を取得する.勤務変更申請の印刷内容
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PrintContentOfWorkChange {
	
	/**
	 * 勤務変更申請
	 */
	private AppWorkChange appWorkChange;
	
	/**
	 * 勤務変更申請の表示情報
	 */
	private AppWorkChangeDispInfo appWorkChangeDispInfo;
	
}
