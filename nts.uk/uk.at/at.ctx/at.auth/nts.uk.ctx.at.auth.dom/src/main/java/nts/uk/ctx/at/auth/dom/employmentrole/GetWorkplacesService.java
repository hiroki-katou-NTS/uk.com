package nts.uk.ctx.at.auth.dom.employmentrole;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.ClosureInformation;

/**
 * 指定締めと同じ締めの所属社員がいる職場を取得する
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.権限管理.就業ロール.指定締めと同じ締めの所属社員がいる職場を取得する
 * @author Laitv
 *
 */
public class GetWorkplacesService {
	
	/**
	 * [1] 取得する
	 * require @Require 
	 * 締めID 締めID  closureId
	 * 職場リスト List<職場ID>  workplaceIds
	 * 基準日 年月日  baseDate
	 *  output 職場リスト List<職場ID>
	 */
	public static List<String> get(Require require, Integer closureId, List<String> workplaceIds, GeneralDate baseDate) {
		
		List<String> workplaceIdsResult = new ArrayList<String>();
		workplaceIds.forEach(workplaceId -> {
			
			if (designatedWorkplaceHaveSameEmp(require, closureId, workplaceId, baseDate)) {
				workplaceIdsResult.add(workplaceId);
			}
		});
		
		return workplaceIdsResult;
	}
	
	/**
	 * [prv-1] 指定職場に同じ締めの社員があるか
	 * @param require
	 * @param closureId
	 * @param workplaceId
	 * @param baseDate
	 * @return
	 */
	private static Boolean designatedWorkplaceHaveSameEmp(Require require, Integer closureId, String workplaceId, GeneralDate baseDate) {
		
		// List<所属職場履歴項目> $職場履歴リスト = require.所属社員を取得する(職場ID, 基準日)
		List<AffWorkplaceHistoryItemImport> listWorkplaceHistoryItem = require.getAffiliatedEmployees(workplaceId, baseDate);
		if (listWorkplaceHistoryItem.isEmpty()) {
			return false;
		}
		
		// 	Set<社員ID> $社員リスト = $職場履歴リスト：map $.社員ID
		Set<String> sids = listWorkplaceHistoryItem.stream().map(item -> item.getEmployeeId()).collect(Collectors.toSet());
		
		//List＜社員ID、締めID＞  $対象リスト = require.社員の締めを取得する($社員リスト, 基準日)
		List<ClosureInformation> listClosureInformation = require.getProcessCloseCorrespondToEmps(sids.stream().collect(Collectors.toList()), baseDate);
		if (listClosureInformation.isEmpty()) {
			return false;
		}
		
		Boolean result = listClosureInformation.stream().anyMatch(item -> item.getClosureID() == closureId);
		return result;
	}

	
	public static interface Require {
		
		/**
		 * [R-1] 所属社員を取得する
		 * @param workplaceId 職場ID
		 * @param baseDate 基準
		 * @return
		 */
		List<AffWorkplaceHistoryItemImport> getAffiliatedEmployees(String workplaceId, GeneralDate baseDate);
		
		/**
		 * [R-2] 社員の締めを取得する
		 * @param sIds List＜社員ID＞
		 * @param baseDate 基準日
		 * @return
		 */
		List<ClosureInformation> getProcessCloseCorrespondToEmps( List<String> sIds, GeneralDate baseDate);
		
	}
}
