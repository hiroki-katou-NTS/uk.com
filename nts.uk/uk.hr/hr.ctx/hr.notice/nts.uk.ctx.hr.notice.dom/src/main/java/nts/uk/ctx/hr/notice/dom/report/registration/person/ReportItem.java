/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.LayoutItemType;

/**
 * @author laitv 
 * Domain : 届出の項目
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportItem extends AggregateRoot {

	String cid; // 会社ID
	int workId; // 業務ID
	int reportID; // 届出ID
	int reportLayoutID; // 個別届出種類ID
	String reportName; // 項目区分
	LayoutItemType layoutItemType; // 項目区分
	String ctgCode; // カテゴリコード
	String ctgName; // カテゴリ名
	boolean fixedAtr; // 既定区分
	String itemCd; // 項目コード
	String itemName; // 項目名
	int dspOrder; // 表示順
	int saveDataAtr; // 保存データ型
	String stringVal; // 文字列
	BigDecimal intVal; // 数値
	GeneralDateTime dateVal;// 日付
	int reflectID; // 反映ID

	public ReportItem(String cid, int workId, int reportID, int reportLayoutID, String reportName, int layoutItemType,
			String ctgCode, String ctgName, boolean fixedAtr, String itemCd, String itemName, int dspOrder,
			int saveDataAtr, String stringVal, BigDecimal intVal, GeneralDateTime dateVal, int reflectID) {
		super();
		this.cid = cid;
		this.workId = workId;
		this.reportID = reportID;
		this.reportLayoutID = reportLayoutID;
		this.reportName = reportName;
		this.layoutItemType = EnumAdaptor.valueOf(layoutItemType, LayoutItemType.class);
		this.ctgCode = ctgCode;
		this.ctgName = ctgName;
		this.fixedAtr = fixedAtr;
		this.itemCd = itemCd;
		this.itemName = itemName;
		this.dspOrder = dspOrder;
		this.saveDataAtr = saveDataAtr;
		this.stringVal = stringVal;
		this.intVal = intVal;
		this.dateVal = dateVal;
		this.reflectID = reflectID;
	}

	public static ReportItem createFromJavaType(String cid, int workId, int reportID, int reportLayoutID,
			String reportName, int layoutItemType, String ctgCode, String ctgName, boolean fixedAtr, String itemCd,
			String itemName, int dspOrder, int saveDataAtr, String stringVal, BigDecimal intVal,
			GeneralDateTime dateVal, int reflectID) {
		return new ReportItem(cid, workId, reportID, reportLayoutID, reportName, layoutItemType, ctgCode, ctgName,
				fixedAtr, itemCd, itemName, dspOrder, saveDataAtr, stringVal, intVal, dateVal, reflectID);}
}
