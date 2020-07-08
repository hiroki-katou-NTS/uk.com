package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.TimeZoneWithWorkNoDto;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
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
	private String opWorkTypeCD;
	
	/**
	 * 就業時間帯コード
	 */
	private String opWorkTimeCD;
	
	/**
	 * 勤務時間帯
	 */
	private List<TimeZoneWithWorkNoDto> timeZoneWithWorkNoLst;
	
	
	public static AppWorkChangeDto fromDomain(AppWorkChange appWorkChange) {
		AppWorkChangeDto appWorkChange_NewDto =  new AppWorkChangeDto();
		appWorkChange_NewDto.setStraightGo(appWorkChange.getStraightGo().value);
		appWorkChange_NewDto.setStraightBack(appWorkChange.getStraightBack().value);
		appWorkChange_NewDto.setOpWorkTypeCD(appWorkChange.getOpWorkTypeCD().isPresent() ? appWorkChange.getOpWorkTypeCD().get().v() : null );
		appWorkChange_NewDto.setOpWorkTimeCD(appWorkChange.getOpWorkTimeCD().isPresent() ? appWorkChange.getOpWorkTimeCD().get().v(): null );
		
		appWorkChange_NewDto.setTimeZoneWithWorkNoLst(appWorkChange.getTimeZoneWithWorkNoLst().stream().map(item -> TimeZoneWithWorkNoDto.fromDomain(item)).collect(Collectors.toList()));
		return appWorkChange_NewDto;
	}
	public AppWorkChange toDomain() {
		return new AppWorkChange(
				NotUseAtr.valueOf(this.straightGo),
				NotUseAtr.valueOf(this.straightBack),
				Optional.ofNullable(new WorkTypeCode(this.opWorkTypeCD)),
				Optional.ofNullable(new WorkTimeCode(this.opWorkTimeCD)),
				timeZoneWithWorkNoLst.stream().map(item -> item.toDomain()).collect(Collectors.toList()),
				null);
	}
}
