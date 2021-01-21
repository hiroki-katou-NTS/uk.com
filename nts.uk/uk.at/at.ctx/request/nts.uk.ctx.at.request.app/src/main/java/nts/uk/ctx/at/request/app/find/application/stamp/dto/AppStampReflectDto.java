package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.AppStampReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@AllArgsConstructor
@NoArgsConstructor
//打刻申請の反映
public class AppStampReflectDto {
	// 介護時間帯を反映する
	public Integer nurseTime;

	// 休憩時間帯を反映する
	public Integer breakTime;

	// 会社ID
	String companyId;

	// 出退勤を反映する
	public Integer attendence;

	// 外出時間帯を反映する
	public Integer outingHourse;

	// 応援開始、終了を反映する
	public Integer startAndEndSupport;

	// 育児時間帯を反映する
	public Integer parentHours;

	// 臨時出退勤を反映する
	public Integer temporaryAttendence;
	
	public static AppStampReflectDto fromDomain(AppStampReflect appStampReflect) {
		return new AppStampReflectDto(
				appStampReflect.getNurseTime().value,
				appStampReflect.getBreakTime().value,
				appStampReflect.getCompanyId(),
				appStampReflect.getAttendence().value,
				appStampReflect.getOutingHourse().value,
				appStampReflect.getStartAndEndSupport().value,
				appStampReflect.getParentHours().value,
				appStampReflect.getTemporaryAttendence().value);
	}
	
	public AppStampReflect toDomain() {
		return new AppStampReflect(
				EnumAdaptor.valueOf(nurseTime, NotUseAtr.class),
				EnumAdaptor.valueOf(breakTime, NotUseAtr.class),
				companyId,
				EnumAdaptor.valueOf(attendence, NotUseAtr.class),
				EnumAdaptor.valueOf(outingHourse, NotUseAtr.class),
				EnumAdaptor.valueOf(startAndEndSupport, NotUseAtr.class),
				EnumAdaptor.valueOf(parentHours, NotUseAtr.class),
				EnumAdaptor.valueOf(temporaryAttendence, NotUseAtr.class));
	}
	
}
