package nts.uk.ctx.at.request.dom.application.appabsence.service.three;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.AbsenceWorkType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.UseAtr;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.TargetWorkTypeByApp;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

@Stateless
public class AppAbsenceThreeProcessImpl implements AppAbsenceThreeProcess {
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private HolidayApplicationSettingRepository hdRep;
	
	// 1.勤務種類を取得する（新規）
	@Override
	public List<AbsenceWorkType> getWorkTypeCodes(List<AppEmploymentSetting> appEmploymentWorkType, String companyID,
			String employeeID, HolidayAppType holidayType, int alldayHalfDay, boolean displayHalfDayValue,Optional<HolidayApplicationSetting> hdAppSet) {
		List<AbsenceWorkType> absenceWorkTypes = new ArrayList<>();
		// アルゴリズム「勤務種類を取得する（詳細）」を実行する(thực hiện xử lý 「勤務種類を取得する（詳細）」)
		List<WorkType> workTypes = new ArrayList<WorkType>();
//		List<WorkType> workTypes = this
//		        .getWorkTypeDetails(appEmploymentWorkType.stream().findFirst().orElse(null), companyID, holidayType, alldayHalfDay,
//		                displayHalfDayValue);
		// ドメインモデル「休暇申請設定」．「休暇申請未選択の設定」．未選択を表示するをチェックする(kiểm tra domain ドメインモデル「休暇申請設定」．「休暇申請未選択の設定」．未選択を表示する): bên anh chình chưa làm.
		if(hdAppSet.isPresent()) { //&& hdAppSet.get().getDisplayUnselect().value == 1 ? true : false){//#102295
			AbsenceWorkType absenceWorkType = new AbsenceWorkType("", "未選択");
			absenceWorkTypes.add(absenceWorkType);
		}
		for(WorkType workType : workTypes){
			AbsenceWorkType absenceWorkType = new AbsenceWorkType(workType.getWorkTypeCode().toString(), workType.getWorkTypeCode().toString() +"　　" + workType.getName().toString());
			absenceWorkTypes.add(absenceWorkType);
		}
		
		//Collections.sort(absenceWorkTypes, Comparator.comparing(AbsenceWorkType :: getWorkTypeCode));
		return absenceWorkTypes.stream().distinct().collect(Collectors.toList());
	}
	//  2.勤務種類を取得する（詳細）
	@Override
	public List<WorkType> getWorkTypeDetails(String companyID,HolidayAppType holidayType,Optional<List<TargetWorkTypeByApp>> targetWorkTypes) {
		// INPUT．「休暇申請対象勤務種類」を確認する
		List<WorkType> workTypes = new ArrayList<WorkType>();
		if (!targetWorkTypes.isPresent()) {
		    workTypes = getWorkTypeByHolidayType(companyID,holidayType.value);
		} else {
		    if (CollectionUtil.isEmpty(targetWorkTypes.get())) {
		        workTypes = getWorkTypeByHolidayType(companyID,holidayType.value);
		    } else {
		        for (TargetWorkTypeByApp wt : targetWorkTypes.get()) {
		            if (wt.getAppType().equals(ApplicationType.ABSENCE_APPLICATION) && wt.getOpHolidayAppType().get().equals(holidayType)) {
		                if (!CollectionUtil.isEmpty(wt.getWorkTypeLst())) {
		                    workTypes.addAll(workTypeRepository.findNotDeprecatedByListCode(companyID, wt.getWorkTypeLst()));
		                } else {
		                    workTypes = getWorkTypeByHolidayType(companyID,holidayType.value);
		                }
		            } else {
		                continue;
		            }
		        }
		    }
		}
		
		// 勤務種類コード(ASC)でソートする
		//Sắp xếp theo disorder;
        List<WorkType> disOrderList = workTypes.stream().filter(w -> w.getDispOrder() != null)
              .sorted(Comparator.comparing(WorkType::getDispOrder)).collect(Collectors.toList());

        List<WorkType> wkTypeCDList = workTypes.stream().filter(w -> w.getDispOrder() == null)
              .sorted(Comparator.comparing(WorkType::getWorkTypeCode)).collect(Collectors.toList());

        disOrderList.addAll(wkTypeCDList);

        workTypes = disOrderList;
		
		return workTypes;
	}
	private List<WorkType> getWorkTypeByHolidayType(String companyID,int holidayType){
		List<WorkType> result = new ArrayList<>();
		//lấy những worktype dạng làm một ngày
		List<Integer> allDayAtrs = new ArrayList<>();
		// lấy những worktype dạng làm nửa ngày
		List<Integer> halfAtrs = new ArrayList<>();
		Integer morningAndAfternoon = null;
		switch(holidayType){
			// 年次有休 - 年休
			case 0:
				morningAndAfternoon = 2;
				// 年休
				allDayAtrs.add(2);
				
				// 出勤
				halfAtrs.add(0);
				// 振出
				halfAtrs.add(7);
				// 代休
				halfAtrs.add(6);
				// 休日
				halfAtrs.add(1);
				// 振休
				halfAtrs.add(8);
				// 特別休暇
				halfAtrs.add(4);
				// 欠勤
				halfAtrs.add(5);
				//時間消化休暇
				halfAtrs.add(9);
				// 積立年休
				halfAtrs.add(3);
				break;
			// 代休
			case 1:
				morningAndAfternoon = 6;
				// 代休
				allDayAtrs.add(6);
				
				// 出勤
				halfAtrs.add(0);
				// 振出
				halfAtrs.add(7);
				// 年休
				halfAtrs.add(2);
				// 休日
				halfAtrs.add(1);
				// 振休
				halfAtrs.add(8);
				// 特別休暇
				halfAtrs.add(4);
				// 欠勤
				halfAtrs.add(5);
				//時間消化休暇
				halfAtrs.add(9);
				//積立年休
				halfAtrs.add(3);
				break;
			// 欠勤
			case 2:
				morningAndAfternoon = 5;
				// 欠勤
				allDayAtrs.add(5);
				
				
				// 出勤
				halfAtrs.add(0);
				// 振出
				halfAtrs.add(7);
				// 年休
				halfAtrs.add(2);
				// 休日
				halfAtrs.add(1);
				// 振休
				halfAtrs.add(8);
				// 特別休暇
				halfAtrs.add(4);
				// 代休
				halfAtrs.add(6);
				//時間消化休暇
				halfAtrs.add(9);
				//欠勤
				halfAtrs.add(5);
				//積立年休
				halfAtrs.add(3);
				break;
			// 特別休暇
			case 3:
				morningAndAfternoon = 4;
				//特別休暇
				allDayAtrs.add(4);
				
				// 出勤
				halfAtrs.add(0);
				// 振出
				halfAtrs.add(7);
				// 年休
				halfAtrs.add(2);
				// 休日
				halfAtrs.add(1);
				// 振休
				halfAtrs.add(8);
				// 欠勤
				halfAtrs.add(5);
				// 代休
				halfAtrs.add(6);
				//時間消化休暇
				halfAtrs.add(9);
				//特別休暇
				halfAtrs.add(4);
				//積立年休
				halfAtrs.add(3);
				break;
			// 積立年休
			case 4:
				morningAndAfternoon = 3;
				//積立年休
				allDayAtrs.add(3);
				
				// 出勤
				halfAtrs.add(0);
				// 振出
				halfAtrs.add(7);
				// 休日
				halfAtrs.add(1);
				// 振休
				halfAtrs.add(8);
				//年休
				halfAtrs.add(2);
				//特別休暇
				halfAtrs.add(4);
				//欠勤
				halfAtrs.add(5);
				//代休
				halfAtrs.add(6);
				//時間消化休暇
				halfAtrs.add(9);
				break;
			// 休日
			case 5:
				morningAndAfternoon = 1;
				//休日
				allDayAtrs.add(1);
				// 振休
				halfAtrs.add(8);
				//出勤
				halfAtrs.add(0);
				//振出
				halfAtrs.add(7);
				//年休
				halfAtrs.add(2);
				//欠勤
				halfAtrs.add(5);
				//代休
				halfAtrs.add(6);
				//時間消化休暇
				halfAtrs.add(9);
				//特別休暇
				halfAtrs.add(4);
				//積立年休
				halfAtrs.add(3);
				break;
			// 時間消化
			case 6:
				morningAndAfternoon = 9;
				//時間消化休暇
				allDayAtrs.add(9);
				
				// 出勤
				halfAtrs.add(0);
				// 振出
				halfAtrs.add(7);
				// 年休
				halfAtrs.add(2);
				// 休日
				halfAtrs.add(1);
				// 振休
				halfAtrs.add(8);
				//特別休暇
				halfAtrs.add(4);
				// 欠勤
				halfAtrs.add(5);
				// 代休
				halfAtrs.add(6);
				//積立年休
				halfAtrs.add(3);
				break;
			// 振休	
			case 7:
				morningAndAfternoon = 8;
				// 振休
				allDayAtrs.add(8);
				
				// 出勤
				halfAtrs.add(0);
				// 年休
				halfAtrs.add(2);
				// 休日
				halfAtrs.add(1);
				//特別休暇
				halfAtrs.add(4);
				// 欠勤
				halfAtrs.add(5);
				// 代休
				halfAtrs.add(6);
				//積立年休
				halfAtrs.add(3);
				//時間消化休暇
				halfAtrs.add(9);
				break;
			default:
				break;
		
		}
		result = this.workTypeRepository.findWorkTypeForAppHolidayAppType(companyID,allDayAtrs,halfAtrs,halfAtrs,morningAndAfternoon,morningAndAfternoon);
		return result;
		
	}
	private int convertHolidayType(int holiday){
		int result = 2;
		switch(holiday){
		case 0:
			// 年休
			result =  2;
			break;
		case 1:
			// 代休
			result = 6;
			break;
		case 2:
			// 欠勤
			result = 5;
			break;
		case 3:
			// 特別休暇
			result = 4;
			break;
		case 4:
			//積立年休
			result = 3;
			break;
		case 5:
			// 休日
			result = 1;
			break;
		case 6:
			//時間消化休暇
			result = 9;
			break;
		case 7:
			// 振休
			result = 8;
			break;
		default:
			break;
		}
		return result;
	}
}
