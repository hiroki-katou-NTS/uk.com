package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ApproverRemandImport {
	//フェーズ番号＝処理中のフェーズ番号
	private int phaseOrder;
	//社員ID　＝承認者の社員ID　または代行者の社員ID
	private String sID;
	//代行者FLG　＝（代行者の場合＝true）
	private boolean isAgent;
}
