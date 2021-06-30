package nts.uk.screen.at.app.query.kdp.kdp003.q;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplaceInforExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP003_打刻入力(氏名選択).Q:メッセージ登録.メニュー別OCD.打刻入力のお知らせの職場を取得する
 * @author tutt
 *
 */
@Stateless
public class GetWorkplaceByStampNotice {
	
	@Inject
	private WorkplacePub workplacePub;
	
	/**
	 * 打刻入力のお知らせの職場名称を取得する
	 * @param wkpIds 職場IDs
	 * @return ＜List＞対象職場
	 */
	public List<WorkplaceInforExport> getWkpNameByWkpId(List<String> wkpIds) {
		
		String cid = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
				
		// [No.560]職場IDから職場の情報をすべて取得する
		List<WorkplaceInforExport> listWorkPlaceInfoExport = workplacePub.getWorkplaceInforByWkpIds(cid, wkpIds, baseDate);
		
		return listWorkPlaceInfoExport;
	}
}
