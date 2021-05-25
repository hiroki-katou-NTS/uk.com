package nts.uk.screen.at.app.ksu001.extractperiodfeed;


import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInShift;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInShiftParam_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInShiftResult_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInWorkInfoParam_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInWorkInfoResult_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInWorkInfomation;
import nts.uk.screen.at.app.ksu001.getsendingperiod.GetSendingPeriodScreenQuery;

/**
 * 抽出期間送り
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正(職場別).メニュー別OCD
 * @author hoangnd
 *
 */

@Stateless
public class GetExtractPeriodFeed {

	@Inject
	private GetSendingPeriodScreenQuery getSendingPeriodScreenQuery;
	
	@Inject
	private ChangePeriodInShift changePeriodInShift;
	
	@Inject
	private ChangePeriodInWorkInfomation changePeriodInWorkInfomation;
	
	public ExtractPeriodFeedDto get(ExtractPeriodFeedParam param) {
		
		
//		TargetOrgIdenInfor targetOrgIdenInfor = null;
//		if (param.unit == TargetOrganizationUnit.WORKPLACE.value) {
//			targetOrgIdenInfor = new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE,
//					Optional.of(param.workplaceId),
//					Optional.empty());
//		} else {
//			targetOrgIdenInfor = new TargetOrgIdenInfor(
//					TargetOrganizationUnit.WORKPLACE_GROUP,
//					Optional.empty(),
//					Optional.of(param.workplaceGroupId));
//		}
		// 1: <call>
		DatePeriod datePeriod = getSendingPeriodScreenQuery.getSendingPeriod(
				new DatePeriod(param.getStartDate(), param.getEndDate()),
				param.isNextMonth(),
				param.isCycle28Day());
		
		ExtractPeriodFeedDto output = new ExtractPeriodFeedDto();
		// Aa:シフト表示の場合
		if (param.getMode() == ExtractPeriodFeedParam.SHIFT_MODE) {
			
			ChangePeriodInShiftResult_New changePeriodInShiftResult_New = 
					changePeriodInShift.getData_New(new ChangePeriodInShiftParam_New(
							datePeriod.start(),
							datePeriod.end(),
							param.getUnit(),
							param.getWorkplaceId(),
							param.getWorkplaceGroupId(),
							param.getSids(),
							param.getListShiftMasterNotNeedGetNew(),
							param.getActualData,
							param.getPersonalCounterOp(),
							param.getWorkplaceCounterOp(),
							new DateInMonth(param.getDay(), param.isLastDay)
							));
			output.setChangePeriodInShiftResult_New(changePeriodInShiftResult_New);
			
		} else if (param.getMode() == ExtractPeriodFeedParam.WORK_MODE 
				|| param.getMode() == ExtractPeriodFeedParam.ABBREVIATION_MODE) { // Ab:勤務表示、Ac:略名表示の場合
			ChangePeriodInWorkInfoResult_New changePeriodInWorkInfoResult_New = 
					changePeriodInWorkInfomation.getDataNew(new ChangePeriodInWorkInfoParam_New(
							datePeriod.start(),
							datePeriod.end(),
							param.getUnit(),
							param.getWorkplaceId(),
							param.getWorkplaceGroupId(),
							param.getSids(),
							param.getActualData,
							param.getPersonalCounterOp(),
							param.getWorkplaceCounterOp(),
							new DateInMonth(param.getDay(), param.isLastDay)
							));
			output.setChangePeriodInWorkInfoResult_New(changePeriodInWorkInfoResult_New);
		}
		
		return output;
	}
}
