/**
 * 
 */
package nts.uk.screen.at.app.ksu001.processcommon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.JudgeHdSystemOneDayService;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.WorkTypeDto;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.WorkTypeInfomation;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author laitv 
 * ScreenQuery 利用可能な勤務種類リストを取得する
 * Path : UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).個人別と共通の処理.利用可能な勤務種類リストを取得する
 */
@Stateless
public class GetListWorkTypeAvailable {

	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private JudgeHdSystemOneDayService judgeHdSystemOneDayService;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
//	@Inject
//	private ScheFunctionControl scheFunctionControl;
	
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
	
	
	public List<WorkTypeInfomation> getData_New() { // ・List<勤務種類, 必須任意不要区分, 出勤休日区分>

		String cid = AppContexts.user().companyId();
		List<WorkTypeDto> workTypeDtos;
		// 1 取得する() // waitting 3si
		Optional<ScheFunctionControl> scheFunctionControl = Optional.empty();
		
		// 2: スケジュール修正の機能制御.表示可能勤務種類制御
		if (scheFunctionControl.map(x -> x.getDisplayWorkTypeControl() == NotUseAtr.USE).orElse(false)) {
			
			// //2.1: <<Public>> 廃止されていない勤務種類をすべて取得する
			workTypeDtos = basicScheduleService.getAllWorkTypeNotAbolished(cid) 
								.stream() // loop
								.map(x -> new WorkTypeDto(x))
								.collect(Collectors.toList());
										
		} else {
			
			// //2.2: <<Public>> 指定した勤務種類をすべて取得する
			workTypeDtos = this.workTypeRepo.getPossibleWorkTypeAndOrder(
					cid, 
					scheFunctionControl.map(x -> x.getDisplayableWorkTypeCodeList())
									.orElse(Collections.emptyList())
									.stream()
									.map(x -> x.v())
									.collect(Collectors.toList())
					)
			.stream()
			.filter(wk -> wk.getAbolishAtr() == NotUseAtr.NOT_USE.value)
			.map(x -> new WorkTypeDto(x.getWorkTypeCode(), x.getName(), x.getAbbreviationName(), x.getMemo()))
			.collect(Collectors.toList());
			
		}
		// loop：勤務種類 in 勤務種類リスト
		
		return workTypeDtos
				.stream()
				.map(x -> this.convertOutput(x))
				.collect(Collectors.toList());
		
	}
	
	private WorkTypeInfomation convertOutput(WorkTypeDto x) {
		// 就業時間帯の必須チェック
		SetupType workTimeSetting = 
				basicScheduleService.checkNeededOfWorkTimeSetting(x.getWorkTypeCode());
	
		// 1日半日出勤・1日休日系の判定 - (Thực hiện thuật toán [Kiểm tra hệ thống đi làm
		// nửa ngày・ nghỉ cả ngày ])
		AttendanceHolidayAttr attHdAtr = judgeHdSystemOneDayService.judgeHdOnDayWorkPer(x.getWorkTypeCode());
	
		return new WorkTypeInfomation(x, workTimeSetting.value, attHdAtr.value);
	}
}
