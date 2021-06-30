package nts.uk.screen.at.app.command.kdp.kdp003.m;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * 打刻入力の職場選択用リストを取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP003_打刻入力(氏名選択).M:応援職場選択.メニューOCD.打刻入力の職場選択用リストを取得する.打刻入力の職場選択用リストを取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class SelectingWorkplacesForStampInput {

	@Inject
	private WorkplacePub workplacePub;
	
	@Inject
	private WorkplaceExportService workplaceService;

	public SelectingWorkplacesForStampInputDto get(SelectingWorkplacesForStampInputParam param) {

		SelectingWorkplacesForStampInputDto result = new SelectingWorkplacesForStampInputDto();

		GeneralDate baseDate = GeneralDate.today();
		String companyId = AppContexts.user().companyId();

		// 1. call：[RQ30]社員所属職場履歴を取得
		Optional<SWkpHistExport> sWkpHistExport = workplacePub.findBySidNew("", param.sid, baseDate);

		if (sWkpHistExport.isPresent()) {
			SWkpHistExport export = sWkpHistExport.get();

			result.sWkpHistExport.setWkpDisplayName(export.getWkpDisplayName());
			result.sWkpHistExport.setWorkplaceCode(export.getWorkplaceCode());
			result.sWkpHistExport.setWorkplaceId(export.getWorkplaceId());
			result.sWkpHistExport.setWorkplaceName(export.getWorkplaceName());
		}

		// 2: call:[No.560]職場IDから職場の情報をすべて取得する
		List<WorkplaceInforParam> listWorkplaceInfo = workplaceService.getWorkplaceInforFromWkpIds(companyId,
				param.workPlaceIds, baseDate);
		
		List<WorkPlaceInfo> infos = listWorkplaceInfo.stream().map(m  -> {
			return new WorkPlaceInfo(
					m.getWorkplaceId(), 
					m.getHierarchyCode(), 
					m.getWorkplaceCode(), 
					m.getWorkplaceName(), 
					m.getDisplayName(), 
					m.getGenericName(), 
					m.getExternalCode());
		}).collect(Collectors.toList());

		result.setWorkPlaceInfo(infos);

		return result;
	}
}
