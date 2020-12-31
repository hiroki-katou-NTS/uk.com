package nts.uk.ctx.at.record.dom.adapter.approvalrootstate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Request113Import {
	/**
	 * 承認ルートの状況
	 */
	private List<AppRootStateStatusSprImport> appRootStateStatusLst;
	
	/**
	 * エラーフラグ
	 */
	private boolean errorFlg;
	
	/**
	 * エラーメッセージID
	 */
	private String errorMsgID;
	
	/**
	 * エラー社員IDリスト
	 */
	private List<String> empLst;
}
