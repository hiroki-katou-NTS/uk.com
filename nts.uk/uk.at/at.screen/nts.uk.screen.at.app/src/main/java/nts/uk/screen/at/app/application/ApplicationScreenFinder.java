package nts.uk.screen.at.app.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.content.AppContentService;
import nts.uk.ctx.at.request.dom.application.applist.service.content.AppTypeMapProgramID;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameExport;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameQuery;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuPub;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApplicationScreenFinder {
	
	@Inject
	private AppContentService appContentService;
	
	@Inject
	private StandardMenuPub standardMenuPub;
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧の申請名称を取得する.申請一覧の申請名称を取得する
	 * @return
	 */
	public List<ListOfAppTypes> getAppNameInAppList() {
		String companyID = AppContexts.user().companyId();
		List<ListOfAppTypes> result = new ArrayList<>();
		// アルゴリズム「申請一覧申請種類のプログラムID」を実行する
		List<AppTypeMapProgramID> appTypeMapProgramIDLst = appContentService.getListProgramIDOfAppType();
		for(AppTypeMapProgramID item : appTypeMapProgramIDLst) {
			result.add(new ListOfAppTypes(
					item.getAppType(), 
					Strings.EMPTY, 
					false, 
					Optional.of(item.getProgramID()), 
					item.getApplicationTypeDisplay() == null ? Optional.empty() : Optional.of(item.getApplicationTypeDisplay()), 
					Optional.of("A")));
		}
		// アルゴリズム「メニューの表示名を取得する」を実行する
		List<StandardMenuNameQuery> param = result.stream().map(x -> new StandardMenuNameQuery(
					x.getOpProgramID().orElse(""), 
					x.getOpString().orElse(""), 
					x.getOpApplicationTypeDisplay().map(y -> y.name)))
				.collect(Collectors.toList());
		List<StandardMenuNameExport> standardMenuNameExportLst = standardMenuPub.getMenuDisplayName(companyID, param);
		for(ListOfAppTypes item : result) {
			Optional<StandardMenuNameExport> opStandardMenuNameExport = standardMenuNameExportLst.stream().filter(x -> {
				boolean condition = x.getProgramId().equals(item.getOpProgramID().get()) && x.getScreenId().equals(item.getOpString().get());
				if(item.getOpApplicationTypeDisplay().isPresent()) {
					condition = condition && item.getOpApplicationTypeDisplay().get().name.equals(x.getQueryString());
				}
				return condition;
			}).findAny();
			if(opStandardMenuNameExport.isPresent()) {
				item.setAppName(opStandardMenuNameExport.get().getDisplayName());
			}
		}
		return result;
	}
}
