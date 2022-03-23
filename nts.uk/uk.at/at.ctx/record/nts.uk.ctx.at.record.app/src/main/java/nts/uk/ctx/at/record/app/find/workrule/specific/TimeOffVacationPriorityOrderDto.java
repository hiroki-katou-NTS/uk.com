package nts.uk.ctx.at.record.app.find.workrule.specific;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Data;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.HolidayPriorityOrder;

/** 時間休暇相殺優先順位 */
@Data
public class TimeOffVacationPriorityOrderDto {

    // 時間休暇相殺優先順位.年休
    private int annualHoliday;
    // 時間休暇相殺優先順位.代休
    private int substituteHoliday;
    // 時間休暇相殺優先順位.60H超休
    private int sixtyHourVacation;
    // 時間休暇相殺優先順位.特別休暇
    private int specialHoliday;
    // 時間休暇相殺優先順位.介護
    private int care;
    // 時間休暇相殺優先順位.子の看護
    private int childCare;
    
    public static TimeOffVacationPriorityOrderDto from(List<HolidayPriorityOrder> orders) {
    	val dto = new TimeOffVacationPriorityOrderDto();
    	for (val order : orders) {
			switch (order) {
			case ANNUAL_HOLIDAY:
				dto.setAnnualHoliday(orders.indexOf(order));
				break;
			case CARE:
				dto.setCare(orders.indexOf(order));
				break;
			case CHILD_CARE:
				dto.setChildCare(orders.indexOf(order));
				break;
			case SIXTYHOUR_HOLIDAY:
				dto.setSixtyHourVacation(orders.indexOf(order));
				break;
			case SPECIAL_HOLIDAY:
				dto.setSpecialHoliday(orders.indexOf(order));
				break;
			case SUB_HOLIDAY:
				dto.setSubstituteHoliday(orders.indexOf(order));
				break;
			default:
				break;
			}
		}
    	
    	return dto;
    }
    
    public List<HolidayPriorityOrder> order() {
    	return Arrays.asList(Pair.of(this.annualHoliday, HolidayPriorityOrder.ANNUAL_HOLIDAY),
							Pair.of(this.care, HolidayPriorityOrder.CARE),
							Pair.of(this.childCare, HolidayPriorityOrder.CHILD_CARE),
							Pair.of(this.sixtyHourVacation, HolidayPriorityOrder.SIXTYHOUR_HOLIDAY),
							Pair.of(this.specialHoliday, HolidayPriorityOrder.SPECIAL_HOLIDAY),
							Pair.of(this.substituteHoliday, HolidayPriorityOrder.SUB_HOLIDAY))
			.stream().sorted((c1, c2) -> c1.getKey().compareTo(c2.getKey()))
			.map(c -> c.getValue()).collect(Collectors.toList());
    }
}
