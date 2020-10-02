package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInitOutput;

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
	private AppListExtractConditionDto appListExtractCondition;
	
	/**
	 * 申請一覧情報
	 */
	private AppListInfoDto appListInfo;
	
	public static AppListInitDto fromDomain(AppListInitOutput appListInitOutput) {
		return new AppListInitDto(
				AppListExtractConditionDto.fromDomain(appListInitOutput.getAppListExtractCondition()), 
				AppListInfoDto.fromDomain(appListInitOutput.getAppListInfo()));
	}
	
}
