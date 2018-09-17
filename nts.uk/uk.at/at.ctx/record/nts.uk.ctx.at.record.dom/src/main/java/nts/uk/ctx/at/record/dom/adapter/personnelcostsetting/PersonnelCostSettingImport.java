package nts.uk.ctx.at.record.dom.adapter.personnelcostsetting;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.premiumtime.PremiumTime;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelCostSettingImport {
	
	//No
	private Integer NO;
	
	//勤怠項目ID
	private List<Integer> attendanceItemId;
	
	private DatePeriod period;
	
	
	/**
	 * 割増時間計算
	 * @return
	 */
	public PremiumTime calcPremiumTime(Optional<DailyRecordToAttendanceItemConverter> dailyRecordDto) {
		
		Integer result = this.attendanceItemId.isEmpty() ? 0 : dailyRecordDto.get().convert(this.attendanceItemId).stream()
											.filter(c -> c.value() != null).mapToInt(r -> (int) r.value()).sum();
//		for(Integer id : this.attendanceItemId) {	
//			//該当する勤怠項目を取得
//			Optional<ItemValue> itemValue = dailyRecordDto.get().convert(id);
//			if(itemValue.isPresent()) {
//				if(itemValue.get().getValue()!=null) {
//					result =  result + (Integer) itemValue.get().value();
//				}
//			}
//		}
		
		return new PremiumTime(this.NO,new AttendanceTime(result));
	}
	

}
