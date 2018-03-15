package nts.uk.ctx.at.request.dom.application.appabsence.service.three;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.appabsence.AbsenceWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class AppAbsenceThreeProcessImpl implements AppAbsenceThreeProcess {
	@Inject
	private WorkTypeRepository workTypeRepository;
	// 1.勤務種類を取得する（新規）
	@Override
	public List<AbsenceWorkType> getWorkTypeCodes(List<AppEmploymentSetting> appEmploymentWorkType, String companyID,
			String employeeID, int holidayType, int alldayHalfDay) {
		List<AbsenceWorkType> absenceWorkTypes = new ArrayList<>();
		// アルゴリズム「勤務種類を取得する（詳細）」を実行する(thực hiện xử lý 「勤務種類を取得する（詳細）」)
		List<WorkType> workTypes = this.getWorkTypeDetails(appEmploymentWorkType, companyID, employeeID, holidayType, alldayHalfDay);
		for(WorkType workType : workTypes){
			AbsenceWorkType absenceWorkType = new AbsenceWorkType(workType.getWorkTypeCode().toString(), workType.getWorkTypeCode().toString() +"　　" + workType.getName().toString());
			absenceWorkTypes.add(absenceWorkType);
		}
		// ドメインモデル「休暇申請設定」．「休暇申請未選択の設定」．未選択を表示するをチェックする(kiểm tra domain ドメインモデル「休暇申請設定」．「休暇申請未選択の設定」．未選択を表示する):TODO bên anh chình chưa làm.
		
		return absenceWorkTypes;
	}
	//  2.勤務種類を取得する（詳細）
	@Override
	public List<WorkType> getWorkTypeDetails(List<AppEmploymentSetting> appEmploymentWorkType, String companyID,
			String employeeID,int holidayType,int alldayHalfDay) {
		List<WorkType> result = new ArrayList<>();
		List<String> lstWorkTypeCodes = new ArrayList<>();
		if(!CollectionUtil.isEmpty(appEmploymentWorkType)){
			List<AppEmploymentSetting> appEmploymentWorkTypes = appEmploymentWorkType.stream().filter(x -> x.getHolidayOrPauseType() == holidayType).collect(Collectors.toList());
			if(!CollectionUtil.isEmpty(appEmploymentWorkTypes)){
				if(!CollectionUtil.isEmpty(appEmploymentWorkTypes.get(0).getLstWorkType())){
					for(AppEmployWorkType appEmployWorkType : appEmploymentWorkTypes.get(0).getLstWorkType()){
						if(appEmployWorkType.getWorkTypeCode() != null && !appEmployWorkType.getWorkTypeCode().equals("")){
							lstWorkTypeCodes.add(appEmployWorkType.getWorkTypeCode());
						}
					}
				}
			}
		}
		if(CollectionUtil.isEmpty(lstWorkTypeCodes)){
			// ドメインモデル「勤務種類」を取得(lấy dữ liệu domain 「勤務種類」)
			result = getWorkTypeByHolidayType(companyID,holidayType);
		}
		if(alldayHalfDay == 0){
			// 終日休暇半日休暇区分 = 終日休暇 :TODO
		}else if(alldayHalfDay == 1){
			// 終日休暇半日休暇区分 = 半日休暇: TODO
		}
		return result;
	}
	private List<WorkType> getWorkTypeByHolidayType(String companyID,int holidayType){
		List<WorkType> result = new ArrayList<>();
		//lấy những worktype dạng làm một ngày
		List<Integer> allDayAtrs = new ArrayList<>();
		// lấy những worktype dạng làm nửa ngày
		List<Integer> halfAtrs = new ArrayList<>();
		switch(holidayType){
			// 年次有休 - 年休
			case 0:
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
				break;
			// 代休
			case 1:
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
				break;
			// 欠勤
			case 2:
				// 欠勤
				allDayAtrs.add(5);
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
				// 代休
				halfAtrs.add(6);
				//時間消化休暇
				halfAtrs.add(9);
				break;
			// 特別休暇
			case 3:
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
				break;
			// 積立年休
			case 4:
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
				break;
			// 休日
			case 5:
				
				break;
			// 時間消化
			case 6:
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
				break;
			// 振休	
			case 7: 
				break;
			default:
				break;
		
		}
		result = this.workTypeRepository.findWorkType(companyID, 0,allDayAtrs,halfAtrs);
		return result;
		
	}
}
