package nts.uk.ctx.at.request.dom.application.applist.service.content;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * refactor 5
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.パラメータ.振休振出申請紐付け
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class ComplementLeaveAppLink {
	/**
	 * 振休振出フラグ
	 */
	private Integer complementLeaveFlg;
	
	/**
	 * 紐付け申請ID
	 */
	private String linkAppID;
	
	/**
	 * 紐付け申請日
	 */
	private GeneralDate linkAppDate;
}
