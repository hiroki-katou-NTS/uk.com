package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.Data;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧承認登録ver4.作業動作データ
 * @author Doan Duy Hung
 *
 */
@Data
public class WorkMotionData {
	
	/**
	 * 事前申請超過
	 */
	private ApplyActionContent preAppConfirm;
	
	/**
	 * 実績超過
	 */
	private ApplyActionContent actualConfirm;
	
}
