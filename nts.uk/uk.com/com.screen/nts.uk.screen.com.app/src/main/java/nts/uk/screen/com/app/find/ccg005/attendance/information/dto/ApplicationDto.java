package nts.uk.screen.com.app.find.ccg005.attendance.information.dto;

import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

@Builder
@Data
public class ApplicationDto {
	
	/**
	 * 申請種類
	 */
	private Integer appType;
	
	/**
	 * 申請者
	 */
	private String sid;
	
	//他の種類
	private Integer otherType;
	
	public static ApplicationDto toDto(Application domain, Map<String, Integer> mapAppIdAndOTAttr) {
		//① 申請種類 = 打刻申請の場合
		if(domain.getAppType() == ApplicationType.STAMP_APPLICATION) {
			return ApplicationDto.builder()
					.appType(domain.getAppType().value)
					.sid(domain.getEmployeeID())
					.otherType(domain.getOpStampRequestMode().map(item -> item.value).orElse(null))
					.build();
		//②申請種類 = 残業申請
		} else if (domain.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
			//（残業申請.ID　＝　申請.ID）
			 Integer type = Optional.ofNullable(mapAppIdAndOTAttr.get(domain.getAppID())).orElse(null);
			return ApplicationDto.builder()
					.appType(domain.getAppType().value)
					.sid(domain.getEmployeeID())
					.otherType(type)
					.build();
		} else {
			return ApplicationDto.builder()
					.appType(domain.getAppType().value)
					.sid(domain.getEmployeeID())
					.otherType(null)
					.build();
		}
	}
}
