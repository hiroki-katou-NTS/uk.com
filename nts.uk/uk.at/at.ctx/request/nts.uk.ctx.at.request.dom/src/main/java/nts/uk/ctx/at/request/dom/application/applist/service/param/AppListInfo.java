package nts.uk.ctx.at.request.dom.application.applist.service.param;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationStatus;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.パラメータ.申請一覧情報
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class AppListInfo {
	
	/**
	 * 申請リスト
	 */
	private List<ListOfApplication> appLst;
	
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
	private AppLstApprovalLstDispSet displaySet;
	
	public AppListInfo() {
		this.appLst = new ArrayList<>();
		this.numberOfApp = new ApplicationStatus();
		this.moreThanDispLineNO = false;
		this.displaySet = new AppLstApprovalLstDispSet();
	}
	
}
