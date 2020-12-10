package nts.uk.ctx.at.request.dom.application.appabsence.service.four;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

@Stateless
public class AppAbsenceFourProcessImpl implements AppAbsenceFourProcess{
	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public boolean getDisplayControlWorkingHours(Optional<String> workTypeCd, VacationApplicationReflect vacationAppReflect, String companyID) {
	    // 就業時間帯表示フラグ = false（初期化）
	    // (WorkTimeDisplayFlag = false (khởi tạo))
		boolean changeWorkHourFlg = false;
		
		// 選択する勤務種類をチェックする(kiểm tra workType đã chọn)
//		if(workTypeCd != null && !workTypeCd.equals("-1")){
//			if(hdAppSet.isPresent()){
//				if(hdAppSet.get().getWrkHours().equals(WorkUse.NOT_USE)){
//					return false;
//				}else if(hdAppSet.get().getWrkHours().equals(WorkUse.USE)){
//					return true;
//				}else if(hdAppSet.get().getWrkHours().equals(WorkUse.USE_ONLY_HALF_HD)){
//					// lấy những worktype dạng làm nửa ngày
//					List<Integer> halfAtrs = new ArrayList<>();
//					// 出勤
//					halfAtrs.add(0);
//					// 振出
//					halfAtrs.add(7);
//					List<String> workTypeCodes = new ArrayList<>();
//					workTypeCodes.add(workTypeCd);
//					List<WorkType> workTypes = this.workTypeRepository
//							.findWorkTypeForHalfDay(companyID, halfAtrs, workTypeCodes).stream()
//							.sorted(Comparator.comparing(WorkType::getWorkTypeCode)).collect(Collectors.toList());
//					if(!CollectionUtil.isEmpty(workTypes)){
//						return true;
//					}
//				}
//			}
//		}
		if (workTypeCd.isPresent()) {
		    if (vacationAppReflect.getWorkAttendanceReflect().getReflectWorkHour().equals(ReflectWorkHourCondition.REFLECT)) {
		        changeWorkHourFlg = true;
		    } else if (vacationAppReflect.getWorkAttendanceReflect().getReflectWorkHour().equals(ReflectWorkHourCondition.REFLECT_IF_HALF_DAY)) {
		        Optional<WorkType> workTypeOpt = workTypeRepository.findByPK(companyID, workTypeCd.get());
		        if (workTypeOpt.isPresent()) {
		            if (workTypeOpt.get().getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.MonringAndAfternoon)) {
		                if (workTypeOpt.get().getDailyWork().getMorning().equals(WorkTypeClassification.Attendance) 
                                    || workTypeOpt.get().getDailyWork().getMorning().equals(WorkTypeClassification.Shooting)
                                    || workTypeOpt.get().getDailyWork().getAfternoon().equals(WorkTypeClassification.Attendance)
                                    || workTypeOpt.get().getDailyWork().getAfternoon().equals(WorkTypeClassification.Shooting)) {
		                    changeWorkHourFlg = true;
		                }
		            }
		        }
		    } else {
		        changeWorkHourFlg = false;
		    }
		}
		
		return changeWorkHourFlg;
	}

}
