package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.パラメータ.申請表示順
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ApplicationDisplayOrder {
	// 申請者順
	APPLICANT_ORDER(0, "申請者順"),
	
	// 申請日付順
	APP_DATE_ORDER(1, "申請日付順"),
	
	// 入力日付順
	INPUT_DATE_ORDER(2, "入力日付順");
	
	public final int value;
	
	public final String name;
}
