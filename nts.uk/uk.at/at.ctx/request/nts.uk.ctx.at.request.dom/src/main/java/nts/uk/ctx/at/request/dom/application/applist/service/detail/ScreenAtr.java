package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScreenAtr {
	/** 0:全て */
	CMM045(0,"申請一覧・承認一覧"),
	/** 1:自分の申請 */
	KAF018(1,"承認状況の照会 "),
	/** 3: 部下の申請 */
	KDL030(2,"申請メール送信ダイアログ");
	
	public int value;
	
	public String nameId;
}
