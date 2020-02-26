package nts.uk.ctx.hr.notice.dom.report;
/**
 * domain 個別届出種類
 * @author lanlt
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Code_AlphaNumeric_3;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_20;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalReportClassification  extends AggregateRoot {
	//会社ID
	private String companyId;
	
	//個別届出種類ID
	private Integer workId;
	
	//個別届出種類ID
	private int pReportClsId;
	//個別届出コード
	private Code_AlphaNumeric_3 pReportCode;
	//個別届出名
	private String_Any_20 pReportName;
	//個別届出名よみ
	private String_Any_20 pReportNameYomi;
	//表示順
	private int displayOrder;
	//廃止区分
	private boolean isAbolition;
	//届出種類
	private ReportType reportType;
	//備考
	private String_Any_20 remark;
	//メモ
	private String_Any_20 memo;
	//メッセージ
	private String_Any_20 message;
	//帳票印刷
	private boolean formReport;
	//代行届出可
	private boolean agentReportIsCan;
	//下位序列承認無 - them vao ngay 2020.02.18
	private boolean noRankOrder;
	
	
	public static PersonalReportClassification createFromJavaType(String cid, int pReportClsId,
			String pReportCode, String pReportName, String pReportNameReadWay,
			int displayOrder, boolean isAbolition, Integer reportType, 
			String remark, String memo, String message, 
			boolean formReport, boolean agentReportIsCan) {
		return new PersonalReportClassification(cid, null, pReportClsId, 
				new Code_AlphaNumeric_3(pReportCode),
				new String_Any_20(pReportName),
				new String_Any_20(pReportNameReadWay),
				displayOrder, isAbolition,
				reportType == null? null: EnumAdaptor.valueOf(reportType.intValue(), ReportType.class),
				new String_Any_20(remark),
				new String_Any_20(memo),
				new String_Any_20(message),
				formReport, agentReportIsCan, false);
	}
}
