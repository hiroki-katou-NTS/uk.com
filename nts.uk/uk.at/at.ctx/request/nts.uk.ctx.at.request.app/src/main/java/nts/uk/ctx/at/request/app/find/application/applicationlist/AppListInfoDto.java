package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationStatus;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppListInfoDto {
	/**
	 * 申請リスト
	 */
	private List<ListOfApplicationDto> appLst;
	
	/**
	 * 申請件数
	 */
	private ApplicationStatus numberOfApp;
	
	/**
	 * 表示行数超
	 */
	private boolean moreThanDispLineNO;
	
	/**
	 * 表示設定
	 */
	private AppLstApprovalLstDispSetDto displaySet;
	
	public static AppListInfoDto fromDomain(AppListInfo appListInfo) {
		return new AppListInfoDto(
				appListInfo.getAppLst().stream().map(x -> ListOfApplicationDto.fromDomain(x)).collect(Collectors.toList()), 
				appListInfo.getNumberOfApp(), 
				appListInfo.isMoreThanDispLineNO(), 
				AppLstApprovalLstDispSetDto.fromDomain(appListInfo.getDisplaySet()));
	}
}
