package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 休日取得数管理
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.休日管理.休日の扱い.休日取得数管理
 * @author tutk
 *
 */
@Value	
public class HolidayNumberManagement {
	/**
	 * 法定外休日を休日取得数に加える
	 */
	private NotUseAtr addNonstatutoryHolidays;
	
	/**
	 * 期間
	 */
	private DatePeriod period;
	
	/**
	 * 休日日数
	 */
	private FourWeekDays holidayDays;
	
	/**
	 * [C-1] 作成する
	 * @param holidayAcqManaPeriod 休日取得の管理期間
	 * @param addNonstatutoryHolidays 法定外休日を休日取得数に加える
	 * @return
	 */
	public static HolidayNumberManagement create(HolidayAcqManaPeriod holidayAcqManaPeriod,NotUseAtr addNonstatutoryHolidays) {
		return new HolidayNumberManagement(addNonstatutoryHolidays, holidayAcqManaPeriod.getPeriod(),
				holidayAcqManaPeriod.getHolidayDays());
	}

	/**
	 * [1] 休日日数をカウントする
	 * @param require
	 * @param mapWorkInformation
	 * @return
	 */
	public int countNumberHolidays(Require require,Map<GeneralDate,WorkInformation> mapWorkInformation) {
		List<WorkType> listWorkInformation = new ArrayList<>();
		mapWorkInformation.forEach((k,v) -> {
			if(this.period.contains(k)) {
				require.findByPK(v.getWorkTypeCode().v()).ifPresent(item ->{
					listWorkInformation.add(item);
				});
			}
		});
		return listWorkInformation.stream().filter(c-> isHoliday(c)).collect(Collectors.toList()).size();
		
	}
	
	/**
	 * [2] 休日日数の過不足を判定する
	 * @param require
	 * @param mapWorkInformation
	 * @return
	 */
	public int redundantMissingHoliday(Require require,Map<GeneralDate,WorkInformation> mapWorkInformation) {
		return this.holidayDays.v().intValue() - countNumberHolidays(require, mapWorkInformation);
	}
	
	/**
	 * [prv-1] 休日であるか
	 * @param workType
	 * @return
	 */
	private boolean isHoliday(WorkType workType) {
		if(workType.getDailyWork().getOneDay() == WorkTypeClassification.Holiday ) {
			
			//勤務種類.1日の勤務.休日設定.休日区分 == 法定内休日
			List<WorkTypeSet> listWorkTypeSet = workType.getWorkTypeSetList().stream().filter(ts -> ts.getWorkAtr().equals(WorkAtr.OneDay)).collect(Collectors.toList());
			boolean checkHolidayAtr = false;
			if(!listWorkTypeSet.isEmpty() && listWorkTypeSet.get(0).getHolidayAtr() == HolidayAtr.STATUTORY_HOLIDAYS) {
				checkHolidayAtr = true;
			}
			
			if(this.getAddNonstatutoryHolidays() == NotUseAtr.USE || checkHolidayAtr ) {
				return true;
			}
		}
		return false;
	}
	
	public static interface Require {
		/**
		 * [R-1] 勤務種類を取得する
		 * 
		 * @param workTypeCd
		 * @return
		 */
		Optional<WorkType> findByPK(String workTypeCd);
	}

}
