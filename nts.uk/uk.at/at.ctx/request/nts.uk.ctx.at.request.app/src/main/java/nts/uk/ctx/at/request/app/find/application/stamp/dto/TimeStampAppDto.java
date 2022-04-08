package nts.uk.ctx.at.request.app.find.application.stamp.dto;



import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampApp;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;

@AllArgsConstructor
@NoArgsConstructor
//打刻申請時刻
public class TimeStampAppDto {
//	反映先
	public DestinationTimeAppDto destinationTimeApp;
//	時刻
	public Integer timeOfDay;
//	勤務場所
	public String workLocationCd; 
//	外出理由
	public Integer appStampGoOutAtr;
	/** 勤務職場 */
	public String wkpId; 
	
	public static TimeStampAppDto fromDomain(TimeStampApp timeStampApp) {
		return new TimeStampAppDto(
				DestinationTimeAppDto.fromDomain(timeStampApp.getDestinationTimeApp()),
				timeStampApp.getTimeOfDay().v(),
				timeStampApp.getWorkLocationCd().isPresent() ? timeStampApp.getWorkLocationCd().get().v() : null,
				timeStampApp.getAppStampGoOutAtr().isPresent() ? timeStampApp.getAppStampGoOutAtr().get().value : null,
				timeStampApp.getWorkplaceId().isPresent() ? timeStampApp.getWorkplaceId().get().v() : null);
	}
	
	public TimeStampApp toDomain() {
		return new TimeStampApp(
				destinationTimeApp.toDomain(),
				new TimeWithDayAttr(timeOfDay),
				workLocationCd != null ? Optional.of(new WorkLocationCD(workLocationCd)) : Optional.empty(),
				appStampGoOutAtr != null ? Optional.of(EnumAdaptor.valueOf(appStampGoOutAtr, GoingOutReason.class)) : Optional.empty(),
				StringUtil.isNullOrEmpty(this.wkpId, false) ? Optional.empty() : Optional.of(new WorkplaceId(this.wkpId)));
	}
	
}
