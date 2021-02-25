package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationStatus;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Data
public class AppListInfoCmd {
	/**
	 * 申請リスト
	 */
	private List<ListOfApplicationCmd> appLst;
	
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
	private AppLstApprovalLstDispSetCmd displaySet;
	
	// AnhNM add to domain
	public AppListInfo toDomain() {
		AppListInfo domain = new AppListInfo();
		List<ListOfApplication> domainLst = this.appLst.stream().map(x -> x.toDomain()).collect(Collectors.toList());
		
		domain.setAppLst(domainLst);
		domain.setNumberOfApp(numberOfApp);
		domain.setMoreThanDispLineNO(moreThanDispLineNO);
		domain.setDisplaySet(displaySet.toDomain());
		
		return domain;
	}
}
