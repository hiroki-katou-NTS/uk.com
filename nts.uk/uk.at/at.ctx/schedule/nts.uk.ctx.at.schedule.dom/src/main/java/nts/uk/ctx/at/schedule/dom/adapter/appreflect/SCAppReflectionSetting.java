package nts.uk.ctx.at.schedule.dom.adapter.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
/**
 * 申請反映設定
 * @author thanh_nx
 *
 */
@Getter
@AllArgsConstructor
public class SCAppReflectionSetting extends DomainObject {
	
	/**
	 * 事前申請スケジュール反映
	 */
	private Boolean scheReflectFlg;
	
	/**
	 * 反映時刻優先
	 */
	private SCPriorityTimeReflectAtr priorityTimeReflectFlag;
	
	/**
	 * 外出打刻漏れの箇所へ出勤退勤時刻を反映する
	 */
	private Boolean attendentTimeReflectFlg;
	
	/** 予定と実績を同じに変更する区分 */
	private SCClassifyScheAchieveAtr classScheAchi;
	
	/** 予定時刻の反映時刻優先 */
	private SCApplyTimeSchedulePriority reflecTimeofSche;
	
	public static SCAppReflectionSetting toDomain(Integer scheReflectFlg, 
			Integer priorityTimeReflectFlag, Integer attendentTimeReflectFlg, int classScheAchi, int reflecTimeofSche){
		return new SCAppReflectionSetting(
				scheReflectFlg == 1 ? true : false, 
				EnumAdaptor.valueOf(priorityTimeReflectFlag, SCPriorityTimeReflectAtr.class), 
				attendentTimeReflectFlg == 1 ? true : false,
				EnumAdaptor.valueOf(classScheAchi, SCClassifyScheAchieveAtr.class),
				EnumAdaptor.valueOf(reflecTimeofSche, SCApplyTimeSchedulePriority.class));
	}
	
}
