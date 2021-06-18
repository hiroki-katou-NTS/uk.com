package nts.uk.ctx.at.request.pub.application.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 申請反映設定
 * @author thanh_nx
 *
 */
@Getter
@AllArgsConstructor
public class AppReflectionSettingExport extends DomainObject {
	
	/**
	 * 事前申請スケジュール反映
	 */
	private Boolean scheReflectFlg;
	
	/**
	 * 反映時刻優先
	 */
	private PriorityTimeReflectAtrExport priorityTimeReflectFlag;
	
	/**
	 * 外出打刻漏れの箇所へ出勤退勤時刻を反映する
	 */
	private Boolean attendentTimeReflectFlg;
	
	/** 予定と実績を同じに変更する区分 */
	private ClassifyScheAchieveAtrExport classScheAchi;
	
	/** 予定時刻の反映時刻優先 */
	private ApplyTimeSchedulePriorityExport reflecTimeofSche;
	
}
