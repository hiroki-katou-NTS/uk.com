package nts.uk.ctx.at.shared.dom.worktype.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JudgeHdSystemOneDayService {
	@Inject
	private WorkTypeRepository wkTypeRepo;
	/**
	 * 1日休暇系か判定する
	 * @author hoatt
	 * @param 勤務種類コード-workTypeCode
	 * @return
	 */
	public boolean judgeHdSysOneDay(String workTypeCode){
		String companyID = AppContexts.user().companyId();
		//ドメインモデル「勤務種類」を取得する-(Lấy domain 「WorkType」)
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, workTypeCode);
		if(!wkTypeOpt.isPresent()){
			return false;
		}
		List<WorkTypeClassification> lstHdAtr = new ArrayList<>();//年休、積立休暇、特別休暇の場合
		lstHdAtr.add(WorkTypeClassification.AnnualHoliday);
		lstHdAtr.add(WorkTypeClassification.SpecialHoliday);
		lstHdAtr.add(WorkTypeClassification.YearlyReserved);
		//勤務区分をチェックする-(Check "WorkClassification" )
		//1日の勤務
		DailyWork dailyWork = wkTypeOpt.get().getDailyWork();
		if(dailyWork.getWorkTypeUnit().equals(WorkTypeUnit.OneDay)){//1日の場合(t/h 1Day)
			//勤務種類の分類を判定する-(Kiểm tra ClassificationOfDutyType)
			if(lstHdAtr.contains(dailyWork.getOneDay())){//年休、積立休暇、特別休暇の場合(t/h là  Holiday、YearlyReserved、SpecialHoliday)
				//「True：1日休暇系」を返す-(Return True)
				return true;
			}
			//その以外
			//「False：1日休暇系でない」を返す-(Return False)
			return false;
		}else{//午前と午後の場合(t/h MorningAndAfternoon)
			//アルゴリズム「1日半日出勤・1日休日系の判定」を実施する-(Thực hiện thuật toán [Kiểm tra hệ thống đi làm nửa ngày・ nghỉ cả ngày ])
			AttendanceHolidayAttr attHdAtr = this.judgeHdOnDayWorkPer(workTypeCode);
			if(!attHdAtr.equals(AttendanceHolidayAttr.HOLIDAY)){//1日休日系でない(ko phải)
				//「False：1日休暇系でない」を返す-(Return False)
				return false;
			}
			//1日休日系(nghỉ cả ngày)
			//午前と午後の勤務種類の分類を判定-(Kiểm tra ClassificationOfDutyType của morning và afternoon)
			if(lstHdAtr.contains(dailyWork.getMorning()) && lstHdAtr.contains(dailyWork.getAfternoon())){//「年休、積立年休、特別休暇」が設定されている
				//「True：1日休暇系」を返す-(Return True)
				return true;
			}
			//「年休、積立年休、特別休暇」が設定されていない
			//「False：1日休暇系でない」を返す-(Return False)
			return false;
		}
	}
	/**
	 * 1日半日出勤・1日休日系の判定
	 * @author hoatt
	 * @param 勤務種類コード-workTypeCode
	 * @return 出勤休日区分
	 */
	public AttendanceHolidayAttr judgeHdOnDayWorkPer(String workTypeCode){
		String companyID = AppContexts.user().companyId();
		//ドメイン「勤務種類」を取得する-(lấy domain 「WorkType」)
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, workTypeCode);
		//勤務区分チェック-(check WorkClassification)
		//1日の勤務
		DailyWork dailyWork = wkTypeOpt.get().getDailyWork();
		List<WorkTypeClassification> lstHdCheck = new ArrayList<>();
		lstHdCheck.add(WorkTypeClassification.Holiday);
		lstHdCheck.add(WorkTypeClassification.Pause);
		lstHdCheck.add(WorkTypeClassification.AnnualHoliday);
		lstHdCheck.add(WorkTypeClassification.YearlyReserved);
		lstHdCheck.add(WorkTypeClassification.SpecialHoliday);
		lstHdCheck.add(WorkTypeClassification.TimeDigestVacation);
		lstHdCheck.add(WorkTypeClassification.SubstituteHoliday);
		lstHdCheck.add(WorkTypeClassification.Absence);
		lstHdCheck.add(WorkTypeClassification.ContinuousWork);
		lstHdCheck.add(WorkTypeClassification.LeaveOfAbsence);
		lstHdCheck.add(WorkTypeClassification.Closure);
		if(dailyWork.getWorkTypeUnit().equals(WorkTypeUnit.OneDay)){//1日(1Day)
			//1日の勤務種類の分類チェック-(Check [ClassificationOfDutyType] của 1 ngày)
			if(lstHdCheck.contains(dailyWork.getOneDay())){//【休日として扱う勤務種類の分類】に含まれている (chứa "Holiday - 休日")
				//1日休日系を出勤休日区分に格納する-(Lưu trữ HOLIDAY vào "AttendanceHolidayAttr")
				return AttendanceHolidayAttr.HOLIDAY;
			}
			//【休日として扱う勤務種類の分類】に含まれていない (Không bao gồm Holiday-休日)
			//1日出勤系を出勤休日区分に格納する-(Lưu trữ FULL_TIME vào "AttendanceHolidayAttr")
			return AttendanceHolidayAttr.FULL_TIME;
		}else{//午前と午後 (Sáng và chiều)
			//午前の勤務種類の分類チェック (Check "ClassificationOfDutyType"  sáng)
			if(lstHdCheck.contains(dailyWork.getMorning())){//【休日として扱う勤務種類の分類】に含まれている (bao gồm trong Holiday-休日)
				//午後の勤務種類の分類チェック-Check "ClassificationOfDutyType"   của buổi chiều
				return lstHdCheck.contains(dailyWork.getAfternoon()) ? //【休日として扱う勤務種類の分類】に含まれている -> 1日休日系を出勤休日区分に格納する
						AttendanceHolidayAttr.HOLIDAY : AttendanceHolidayAttr.AFTERNOON;//【休日として扱う勤務種類の分類】に含まれていない -> 午後出勤系を出勤休日区分に格納する
			}else{//【休日として扱う勤務種類の分類】に含まれていない( không bao gồm Holiday - 休日)
				//午後の勤務種類の分類チェック-Check "ClassificationOfDutyType"  của buổi chiều
				return lstHdCheck.contains(dailyWork.getAfternoon()) ? //【休日として扱う勤務種類の分類】に含まれている -> 午前出勤系を出勤休日区分に格納する
						AttendanceHolidayAttr.MORNING : AttendanceHolidayAttr.FULL_TIME;//【休日として扱う勤務種類の分類】に含まれていない -> 1日出勤系を出勤休日区分に格納する
			}
		}
	}
}
