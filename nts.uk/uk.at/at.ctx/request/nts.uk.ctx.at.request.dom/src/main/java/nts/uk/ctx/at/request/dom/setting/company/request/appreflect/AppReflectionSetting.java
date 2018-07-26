package nts.uk.ctx.at.request.dom.setting.company.request.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
/**
 * 申請反映設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppReflectionSetting extends DomainObject {
	
	/**
	 * 事前申請スケジュール反映
	 */
	private Boolean scheReflectFlg;
	
	/**
	 * 反映時刻優先
	 */
	private PriorityTimeReflectAtr priorityTimeReflectFlag;
	
	/**
	 * 外出打刻漏れの箇所へ出勤退勤時刻を反映する
	 */
	private Boolean attendentTimeReflectFlg;
	
	/** 予定と実績を同じに変更する区分 */
	private ClassifyScheAchieveAtr classScheAchi;
	
	/** 予定時刻の反映時刻優先 */
	private ApplyTimeSchedulePriority reflecTimeofSche;
	
	public static AppReflectionSetting toDomain(Integer scheReflectFlg, 
			Integer priorityTimeReflectFlag, Integer attendentTimeReflectFlg, int classScheAchi, int reflecTimeofSche){
		return new AppReflectionSetting(
				scheReflectFlg == 1 ? true : false, 
				EnumAdaptor.valueOf(priorityTimeReflectFlag, PriorityTimeReflectAtr.class), 
				attendentTimeReflectFlg == 1 ? true : false,
				EnumAdaptor.valueOf(classScheAchi, ClassifyScheAchieveAtr.class),
				EnumAdaptor.valueOf(reflecTimeofSche, ApplyTimeSchedulePriority.class));
	}
	
}
