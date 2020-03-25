package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.SpecAbsenceDispInfo;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventDto;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.DateSpecHdRelationOutput;

@AllArgsConstructor
@NoArgsConstructor
public class SpecAbsenceDispInfoDto {
	
	/**
	 * 事象に応じた特休フラグ
	 */
	public boolean specHdForEventFlag;
	
	/**
	 * 事象に対する特別休暇
	 */
	public SpecialHolidayEventDto specHdEvent;
	
	/**
	 * 特休枠NO
	 */
	public Integer frameNo;
	
	/**
	 * 上限日数
	 */
	public Integer maxDay;
	
	/**
	 * 喪主加算日数
	 */
	public Integer dayOfRela;
	
	/**
	 * 続柄毎の上限日数リスト
	 */
	public List<DateSpecHdRelationOutput> dateSpecHdRelationLst;
	
	public static SpecAbsenceDispInfoDto fromDomain(SpecAbsenceDispInfo specAbsenceDispInfo) {
		SpecAbsenceDispInfoDto result = new SpecAbsenceDispInfoDto();
		result.specHdForEventFlag = specAbsenceDispInfo.isSpecHdForEventFlag();
		result.specHdEvent = specAbsenceDispInfo.getSpecHdEvent().map(x -> SpecialHolidayEventDto.fromDomain(x)).orElse(null);
		result.frameNo = specAbsenceDispInfo.getFrameNo().orElse(null);
		result.maxDay = specAbsenceDispInfo.getMaxDay().orElse(null);
		result.dayOfRela = specAbsenceDispInfo.getDayOfRela().orElse(null);
		result.dateSpecHdRelationLst = specAbsenceDispInfo.getDateSpecHdRelationLst().orElse(null);
		return result;
	}
	
	public SpecAbsenceDispInfo toDomain() {
		SpecAbsenceDispInfo result = new SpecAbsenceDispInfo();
		result.setSpecHdForEventFlag(specHdForEventFlag);
		result.setSpecHdEvent(specHdEvent == null ? Optional.empty() : Optional.of(specHdEvent.toDomain()));
		result.setFrameNo(Optional.of(frameNo));
		result.setMaxDay(Optional.of(maxDay));
		result.setDayOfRela(Optional.of(dayOfRela));
		result.setDateSpecHdRelationLst(Optional.of(dateSpecHdRelationLst));
		return result;
	}
	
}
