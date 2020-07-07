package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.TimeZoneWithWorkNoDto;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppWorkChangeDto {
	/**
	 * 直行区分
	 */
	private int straightGo;
	
	/**
	 * 直帰区分
	 */
	private int straightBack;
	
	/**
	 * 勤務種類コード
	 */
	private WorkTypeCode opWorkTypeCD;
	
	/**
	 * 就業時間帯コード
	 */
	private WorkTimeCode opWorkTimeCD;
	
	/**
	 * 勤務時間帯
	 */
	private List<TimeZoneWithWorkNoDto> timeZoneWithWorkNoLst;
	
	
	public static AppWorkChangeDto fromDomain(AppWorkChange appWorkChange) {
		AppWorkChangeDto appWorkChange_NewDto =  new AppWorkChangeDto();
		appWorkChange_NewDto.setStraightGo(appWorkChange.getStraightGo().value);
		appWorkChange_NewDto.setStraightBack(appWorkChange.getStraightBack().value);
		appWorkChange_NewDto.setOpWorkTypeCD(appWorkChange.getOpWorkTypeCD().isPresent() ? appWorkChange.getOpWorkTypeCD().get() : null );
		appWorkChange_NewDto.setOpWorkTimeCD(appWorkChange.getOpWorkTimeCD().isPresent() ? appWorkChange.getOpWorkTimeCD().get(): null );
		
		appWorkChange_NewDto.setTimeZoneWithWorkNoLst(appWorkChange.getTimeZoneWithWorkNoLst().stream().map(item -> TimeZoneWithWorkNoDto.fromDomain(item)).collect(Collectors.toList()));
		return appWorkChange_NewDto;
	}
}
