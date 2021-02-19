package nts.uk.ctx.at.shared.dom.adapter.application.reflect;

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
public class SHAppReflectionSetting extends DomainObject {
	
	/**
	 * 事前申請スケジュール反映
	 */
	private Boolean scheReflectFlg;
	
	/**
	 * 反映時刻優先
	 */
	private SHPriorityTimeReflectAtr priorityTimeReflectFlag;
	
	/**
	 * 外出打刻漏れの箇所へ出勤退勤時刻を反映する
	 */
	private Boolean attendentTimeReflectFlg;
	
	/** 予定と実績を同じに変更する区分 */
	private SHClassifyScheAchieveAtr classScheAchi;
	
	/** 予定時刻の反映時刻優先 */
	private SHApplyTimeSchedulePriority reflecTimeofSche;
	
	public static SHAppReflectionSetting toDomain(Integer scheReflectFlg, 
			Integer priorityTimeReflectFlag, Integer attendentTimeReflectFlg, int classScheAchi, int reflecTimeofSche){
		return new SHAppReflectionSetting(
				scheReflectFlg == 1 ? true : false, 
				EnumAdaptor.valueOf(priorityTimeReflectFlag, SHPriorityTimeReflectAtr.class), 
				attendentTimeReflectFlg == 1 ? true : false,
				EnumAdaptor.valueOf(classScheAchi, SHClassifyScheAchieveAtr.class),
				EnumAdaptor.valueOf(reflecTimeofSche, SHApplyTimeSchedulePriority.class));
	}
	
}
