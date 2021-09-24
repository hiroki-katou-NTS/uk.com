package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.工数実績項目.日別勤怠(Work)から工数実績項目に変換する.工数実績作業詳細
 * @author tutt
 *
 */
@Getter
@AllArgsConstructor
public class ManHrTaskDetail {
	
	/** 作業項目値*/
	private List<TaskItemValue> taskItemValue;
	
	/** 応援勤務枠No*/
	private SupportFrameNo supNo;
}
