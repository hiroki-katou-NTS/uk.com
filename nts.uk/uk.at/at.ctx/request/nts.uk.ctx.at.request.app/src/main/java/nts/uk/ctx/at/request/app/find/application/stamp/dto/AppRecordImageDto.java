package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCombinationAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakout.GoOutReasonAtr;

@AllArgsConstructor
@NoArgsConstructor
//レコーダイメージ申請
public class AppRecordImageDto extends ApplicationDto{
//	打刻区分
	public Integer appStampCombinationAtr;	
//	申請時刻
	public Integer attendanceTime;
//	外出理由
	public Integer appStampGoOutAtr;
	
	public static AppRecordImageDto fromDomain(AppRecordImage appRecordImage) {
		return new AppRecordImageDto(
				appRecordImage.getAppStampCombinationAtr().value,
				appRecordImage.getAttendanceTime().v(),
				appRecordImage.getAppStampGoOutAtr().isPresent() ? appRecordImage.getAppStampGoOutAtr().get().value : null);
	}
	
	public AppRecordImage toDomain() {
		return new AppRecordImage(
				EnumAdaptor.valueOf(appStampCombinationAtr, AppStampCombinationAtr.class),
				EnumAdaptor.valueOf(attendanceTime, AttendanceTime.class),
				appStampGoOutAtr != null ? Optional.of(EnumAdaptor.valueOf(appStampGoOutAtr, GoOutReasonAtr.class)) : Optional.empty());
	}
}
