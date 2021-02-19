package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class Request533Import {
	/*
	 * 承認ルートの状況
	 */
	private List<AppRootSttMonthImport> appRootSttMonthImportLst;
	
	/*
	 * エラーフラグ
	 */
	private boolean errorFlg;
	
	/*
	 * エラーメッセージID
	 */
	private String errorMsgID;
	
	/*
	 * エラー社員IDリスト
	 */
	private List<String> errorEmpLst;
}
