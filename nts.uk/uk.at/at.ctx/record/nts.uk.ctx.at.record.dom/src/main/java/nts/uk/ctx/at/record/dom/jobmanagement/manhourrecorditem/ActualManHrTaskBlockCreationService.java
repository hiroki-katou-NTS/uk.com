package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeGroup;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.工数実績項目.工数実績作業ブロックを作成する
 * DS: 工数実績作業ブロックを作成する
 * 
 * @author tutt
 *
 */
public class ActualManHrTaskBlockCreationService {

	/**
	 * 	■Public
	 * [1] 作成する
	 * 	説明:工数実績作業詳細データと日別実績の作業時間帯グループから工数実績作業ブロックを作成する		
	 * @param require Require
	 * @param sId 社員ID 
	 * @param date 年月日	
	 * @param taskDetails 工数実績項目リスト	
	 * @return 日別実績の工数実績作業
	 */
	public static DailyActualManHrActualTask create(Require require, String sId, GeneralDate date,
			List<ManHrTaskDetail> taskDetails) {
		
		// $作業ブロックリスト = List.Empty
		DailyActualManHrActualTask result = new DailyActualManHrActualTask(date, new ArrayList<ManHrPerformanceTaskBlock>());
		
		// $作業時間帯グループ = require.作業時間帯グループを取得する(社員ID,年月日)
		
		
		// $作業時間帯グループ = require.作業時間帯グループを取得する(社員ID,年月日)	
		//Optional<TaskTimeGroup> timeGroupOpt = 
		
		//if not $作業時間帯グループ.isEmpty
		

		return result;
	}

	// ■Public
	// ■Require
	public static interface Require {

		// [R-1] 作業時間帯グループを取得する
		// 日別実績の作業時間帯グループRepository.Get(社員ID,年月日)
		Optional<TaskTimeGroup> get(String sID, GeneralDate date);
	}
}
