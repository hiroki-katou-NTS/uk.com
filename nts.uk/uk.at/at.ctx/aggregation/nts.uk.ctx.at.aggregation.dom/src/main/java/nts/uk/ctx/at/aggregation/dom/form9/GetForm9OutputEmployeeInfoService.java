package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 様式９の出力社員情報を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の出力社員情報を取得する
 * @author lan_lt
 *
 */
public class GetForm9OutputEmployeeInfoService {

	/**
	 * 取得する
	 * @param require
	 * @param workplaceGroupId 職場グループID
	 * @param ymd 年月日
	 * @return
	 */
	public static Form9OutputEmployeeInfoList get(Require require, String workplaceGroupId, DatePeriod period) {
		
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(workplaceGroupId);
		List<String> employeeIds = GetEmpCanReferService.getByOrg(require, require.getLoginEmployeeId(), 
										GeneralDate.today(), period, targetOrgIdenInfor);
				
		List<String> sortedEmployeeIds = SortByForm9Service.sort(require, GeneralDate.today(), employeeIds);

		List<Form9OutputEmployeeInfo> employeeInfoResults = sortedEmployeeIds.stream()
				.map(sid -> Form9OutputEmployeeInfo.create(require, GeneralDate.today(), sid))
				.flatMap(OptionalUtil::stream)
				.collect(Collectors.toList());

		return new Form9OutputEmployeeInfoList(employeeInfoResults);
	}


	public static interface Require extends		GetEmpCanReferService.Require
											,	SortByForm9Service.Require
											,	Form9OutputEmployeeInfo.Require{
		/** ログイン社員IDを取得する */
		String getLoginEmployeeId();

	}
}
