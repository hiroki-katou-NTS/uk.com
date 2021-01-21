/**
 * 
 */
package nts.uk.screen.at.app.ksu001.processcommon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.JudgeHdSystemOneDayService;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.WorkTypeDto;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.WorkTypeInfomation;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv 
 * ScreenQuery 利用可能な勤務種類リストを取得する
 */
@Stateless
public class GetListWorkTypeAvailable {

	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private JudgeHdSystemOneDayService judgeHdSystemOneDayService;
	
	public List<WorkTypeInfomation> getData() {

		String cid = AppContexts.user().companyId();
		List<WorkTypeInfomation> listWorkTypeInfo = new ArrayList<>();

		// <<Public>> 廃止されていない勤務種類をすべて取得する
		List<WorkType> listWorkType = basicScheduleService.getAllWorkTypeNotAbolished(cid);
		List<WorkTypeDto> listWorkTypeDto = listWorkType.stream().map(mapper -> {
			return new WorkTypeDto(mapper);
		}).collect(Collectors.toList());

		for (int i = 0; i < listWorkTypeDto.size(); i++) {

			// <<Public>> 廃止されていない勤務種類をすべて取得する
			WorkTypeDto workTypeDto = listWorkTypeDto.get(i);
			// 就業時間帯の必須チェック
			SetupType workTimeSetting = basicScheduleService.checkNeededOfWorkTimeSetting(listWorkTypeDto.get(i).getWorkTypeCode());

			// 1日半日出勤・1日休日系の判定 - (Thực hiện thuật toán [Kiểm tra hệ thống đi làm
			// nửa ngày・ nghỉ cả ngày ])
			AttendanceHolidayAttr attHdAtr = judgeHdSystemOneDayService.judgeHdOnDayWorkPer(listWorkTypeDto.get(i).getWorkTypeCode());

			WorkTypeInfomation workTypeInfomation = new WorkTypeInfomation(workTypeDto, workTimeSetting.value, attHdAtr.value);
			listWorkTypeInfo.add(workTypeInfomation);
		}

		return listWorkTypeInfo;
	}
}
