package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppListInitDto {
	
	/**
	 * 申請一覧抽出条件
	 */
	private AppListExtractConditionDto appListExtractConditionDto;
	
	/**
	 * 申請一覧情報
	 */
	private AppListInfoDto appListInfoDto;
	
}
