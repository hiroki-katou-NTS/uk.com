package nts.uk.ctx.hr.notice.ws.report;

import lombok.AllArgsConstructor;

import lombok.Getter;

@Getter
@AllArgsConstructor
public class PsReportEx {
	//個別届出種類ID RPT_LAYOUT_ID
	private int reportClsId;
	//個別届出コード RPT_LAYOUT_CD
	private String reportCode;
	//個別届出名 RPT_LAYOUT_NAME
	private String reportName;
	//個別届出名よみ RPT_LAYOUT_NAME_Y
	private String reportNameYomi;
	//表示順 NO_RANK_ORDER
	private int displayOrder;
	//廃止区分 ABOLITION_ATR
	private int isAbolition;
	//備考 REMARK
	private String remark;
}
