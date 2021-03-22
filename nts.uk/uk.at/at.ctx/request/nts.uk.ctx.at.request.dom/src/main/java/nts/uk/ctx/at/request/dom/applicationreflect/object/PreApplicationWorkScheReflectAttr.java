package nts.uk.ctx.at.request.dom.applicationreflect.object;

import lombok.AllArgsConstructor;

/** 事前申請勤務予定反映区分 */
@AllArgsConstructor
public enum PreApplicationWorkScheReflectAttr {
	/** しない */
	NOT_REFLECT(0),
	/** する */
	REFLECT(1),
	/** スナップショット作成後は反映する*/
	REFLECT_AFTER_CREATE_SNAPSHOT(2);
	
	public int value;
}
