package nts.uk.ctx.at.request.dom.application.applist.service.param;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.パラメータ.振休振出申請紐付け
 * @author Doan Duy Hung
 *
 */
@Getter
public class ComplementLeaveAppConnect {
	
	/**
	 * 振休振出フラグ
	 */
	private int complementLeaveFlg;
	
	/**
	 * 紐付け申請ID
	 */
	private int peggingAppID;
	
	/**
	 * 紐付け申請日
	 */
	private GeneralDate peggingAppDate;
}
