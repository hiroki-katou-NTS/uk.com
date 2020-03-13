package nts.uk.ctx.at.request.app.find.setting.company.request.appreflect;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.AppReflectionSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.ApplyTimeSchedulePriority;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.ClassifyScheAchieveAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.PriorityTimeReflectAtr;

public class AppReflectionSettingDto {
	/**
	 * 事前申請スケジュール反映
	 */
	public Boolean scheReflectFlg;
	
	/**
	 * 反映時刻優先
	 */
	public int priorityTimeReflectFlag;
	
	/**
	 * 外出打刻漏れの箇所へ出勤退勤時刻を反映する
	 */
	public Boolean attendentTimeReflectFlg;
	
	/** 予定と実績を同じに変更する区分 */
	public int classScheAchi;
	
	/** 予定時刻の反映時刻優先 */
	public int reflecTimeofSche;
	
	public static AppReflectionSettingDto fromDomain(AppReflectionSetting appReflectionSetting) {
		AppReflectionSettingDto appReflectionSettingDto = new AppReflectionSettingDto();
		appReflectionSettingDto.scheReflectFlg = appReflectionSetting.getScheReflectFlg();
		appReflectionSettingDto.priorityTimeReflectFlag = appReflectionSetting.getPriorityTimeReflectFlag().value;
		appReflectionSettingDto.attendentTimeReflectFlg = appReflectionSetting.getAttendentTimeReflectFlg();
		appReflectionSettingDto.classScheAchi = appReflectionSetting.getClassScheAchi().value;
		appReflectionSettingDto.reflecTimeofSche = appReflectionSetting.getReflecTimeofSche().value;
		return appReflectionSettingDto;
	}
	
	public AppReflectionSetting toDomain() {
		return new AppReflectionSetting(
				scheReflectFlg, 
				EnumAdaptor.valueOf(priorityTimeReflectFlag, PriorityTimeReflectAtr.class), 
				attendentTimeReflectFlg, 
				EnumAdaptor.valueOf(classScheAchi, ClassifyScheAchieveAtr.class), 
				EnumAdaptor.valueOf(reflecTimeofSche, ApplyTimeSchedulePriority.class));
	}
}
