package nts.uk.ctx.at.request.dom.application.applist.service.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppListInitOutput {
	/**
	 * 申請一覧抽出条件
	 */
	private AppListExtractCondition appListExtractCondition;
	
	/**
	 * 申請一覧情報
	 */
	private AppListInfo appListInfo;
}
