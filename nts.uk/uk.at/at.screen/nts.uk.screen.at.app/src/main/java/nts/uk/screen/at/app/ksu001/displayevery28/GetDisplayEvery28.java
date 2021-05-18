package nts.uk.screen.at.app.ksu001.displayevery28;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.screen.at.app.ksu001.get28dateperiod.ScreenQuery28DayPeriod;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInShift;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInShiftParam_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInShiftResult_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInWorkInfoParam_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInWorkInfoResult_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInWorkInfomation;
/**
 * 28日周期で表示する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正(職場別).メニュー別OCD
 * @author hoangnd
 *
 */

@Stateless
public class GetDisplayEvery28 {

	@Inject
	private ScreenQuery28DayPeriod screenQuery28DayPeriod;
	
	@Inject
	private ChangePeriodInShift changePeriodInShift;
	
	@Inject
	private ChangePeriodInWorkInfomation changePeriodInWorkInfomation;
	
	public DisplayEvery28Dto get(DisplayEvery28Param param) {
		DisplayEvery28Dto output = new DisplayEvery28Dto();
		// 取得する(年月日)
		DatePeriodDto datePeriodDto = screenQuery28DayPeriod.get(param.getDate());
		if (param.getMode() == DisplayEvery28Param.SHIFT_MODE) { // Aa:シフト表示の場合
			ChangePeriodInShiftResult_New changePeriodInShiftResult_New =
					changePeriodInShift.getData_New(
							new ChangePeriodInShiftParam_New(
									datePeriodDto.getStart(),
									datePeriodDto.getEnd(),
									param.getUnit(),
									param.getWorkplaceId(),
									param.getWorkplaceGroupId(),
									param.getSids(),
									param.getListShiftMasterNotNeedGetNew(),
									param.getActualData,
									param.getPersonalCounterOp(),
									param.getWorkplaceCounterOp(),
									param.getDay()
									)
							);
			
			output.setChangePeriodInShiftResult_New(changePeriodInShiftResult_New);
		} else { // Ab:勤務表示、Ac:略名表示の場合
			
			ChangePeriodInWorkInfoResult_New changePeriodInWorkInfoResult_New =
					changePeriodInWorkInfomation.getDataNew(
							new ChangePeriodInWorkInfoParam_New(
									datePeriodDto.getStart(),
									datePeriodDto.getEnd(),
									param.getUnit(),
									param.getWorkplaceId(),
									param.getWorkplaceGroupId(),
									param.getSids(),
									param.getActualData,
									param.getPersonalCounterOp(),
									param.getWorkplaceCounterOp(),
									param.getDay()
									)
							);
			
			output.setChangePeriodInWorkInfoResult_New(changePeriodInWorkInfoResult_New);
		}
		
		
		return output;
		
	}
	
}
