/**
 * 
 */
package nts.uk.ctx.at.shared.app.find.shortworktime;

import java.util.Optional;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author danpv
 *
 */
@Setter
public class ShortWorkTimeDto extends PeregDomainDto {

	/**
	 * 短時間勤務期間
	 */
	@PeregItem("IS00101")
	private String periodName;

	/**
	 * 短時間開始日
	 */
	@PeregItem("IS00102")
	private GeneralDate startDate;

	/**
	 * 短時間終了日
	 */
	@PeregItem("IS00103")
	private GeneralDate endDate;

	/**
	 * 短時間勤務区分
	 */
	@PeregItem("IS00104")
	private int childCareAtr;

	/**
	 * 育児時間1
	 */
	@PeregItem("IS00105")
	private String periodName1;

	/**
	 * 育児開始1
	 */
	@PeregItem("IS00106")
	private Integer startTime1;

	/**
	 * 育児終了1
	 */
	@PeregItem("IS00107")
	private Integer endTime1;

	/**
	 * 育児時間2
	 */
	@PeregItem("IS00108")
	private String periodName2;

	/**
	 * 育児開始2
	 */
	@PeregItem("IS00109")
	private Integer startTime2;

	/**
	 * 育児終了2
	 */
	@PeregItem("IS00110")
	private Integer endTime2;

	public ShortWorkTimeDto(String historyId, String periodName, GeneralDate startDate, GeneralDate endDate,
			int childCareAtr, String periodName1, Integer startTime1, Integer endTime1, String periodName2,
			Integer startTime2, Integer endTime2) {
		super(historyId);
		this.periodName = periodName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.childCareAtr = childCareAtr;
		this.periodName1 = periodName1;
		this.startTime1 = startTime1;
		this.endTime1 = endTime1;
		this.periodName2 = periodName2;
		this.startTime2 = startTime2;
		this.endTime2 = endTime2;
	}
	
	public ShortWorkTimeDto(String historyId, String periodName, GeneralDate startDate, GeneralDate endDate,
			int childCareAtr) {
		super(historyId);
		this.periodName = periodName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.childCareAtr = childCareAtr;
	}

	public static ShortWorkTimeDto createShortWorkTimeDto(DateHistoryItem dateHitoryItem,
			ShortWorkTimeHistoryItem shortTimeItem) {
		
		Optional<SChildCareFrame> firstItemOpt = shortTimeItem.getLstTimeSlot().stream().filter(slot -> slot.timeSlot == 1)
				.findFirst();
		
		Optional<SChildCareFrame> secondItemOpt = shortTimeItem.getLstTimeSlot().stream().filter(slot -> slot.timeSlot == 2)
				.findFirst();
		
		ShortWorkTimeDto dto =  new ShortWorkTimeDto(dateHitoryItem.identifier(), null, dateHitoryItem.start(), dateHitoryItem.end(),
				shortTimeItem.getChildCareAtr().value);
		
		if ( firstItemOpt.isPresent()) {
			SChildCareFrame firstItem = firstItemOpt.get();
			dto.setStartTime1(firstItem.getStartTime().v());
			dto.setEndTime1(firstItem.getEndTime().v());
		}
		
		if ( secondItemOpt.isPresent()) {
			SChildCareFrame secondItem = secondItemOpt.get();
			dto.setStartTime2(secondItem.getStartTime().v());
			dto.setEndTime2(secondItem.getEndTime().v());
		}
		return dto;
	}
	
	// for cps013 , param childCareAtr để validate enum
	public static ShortWorkTimeDto createShortWorkTimeDto(DateHistoryItem dateHitoryItem,
			ShortWorkTimeHistoryItem shortTimeItem, Integer childCareAtr) {
		
		Optional<SChildCareFrame> firstItemOpt = shortTimeItem.getLstTimeSlot().stream().filter(slot -> slot.timeSlot == 1)
				.findFirst();
		
		Optional<SChildCareFrame> secondItemOpt = shortTimeItem.getLstTimeSlot().stream().filter(slot -> slot.timeSlot == 2)
				.findFirst();
		
		ShortWorkTimeDto dto =  new ShortWorkTimeDto(dateHitoryItem.identifier(), null, dateHitoryItem.start(), dateHitoryItem.end(),
				childCareAtr);
		
		if ( firstItemOpt.isPresent()) {
			SChildCareFrame firstItem = firstItemOpt.get();
			dto.setStartTime1(firstItem.getStartTime().v());
			dto.setEndTime1(firstItem.getEndTime().v());
		}
		
		if ( secondItemOpt.isPresent()) {
			SChildCareFrame secondItem = secondItemOpt.get();
			dto.setStartTime2(secondItem.getStartTime().v());
			dto.setEndTime2(secondItem.getEndTime().v());
		}
		return dto;
	}

}
