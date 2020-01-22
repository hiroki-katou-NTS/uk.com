package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.LayoutItemType;

/**
 * @author laitv 
 * Domain : 届出の項目
 */
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportItem extends AggregateRoot {

	private String cid; // 会社ID
	private int workId; // 業務ID
	private int reportID; // 届出ID
	private int reportLayoutID; // 個別届出種類ID
	private String reportName; 	// 届出名
	private LayoutItemType layoutItemType; // 項目区分
	private String categoryId;//カテゴリID
	private String ctgCode; // カテゴリコード 
	private String ctgName; // カテゴリ名
	private boolean fixedAtr; // 既定区分
	private String itemId;//項目ID
	private String itemCd; // 項目コード 
	private String itemName; // 項目名
	private int dspOrder; // 表示順
	private int saveDataAtr; // 保存データ型
	private String stringVal; // 文字列
	private BigDecimal intVal; // 数値
	private GeneralDate dateVal;// 日付
	private int layoutDisOrder; // 項目表示順
	private String contractCode; // 契約CD
	private int reflectID; // 反映ID
	
	
	
	public ReportItem(String cid, int workId, int reportID, int reportLayoutID, String reportName, int layoutItemType,
			String categoryId, String ctgCode, String ctgName, boolean fixedAtr, String itemId, String itemCd, 
			String itemName, int dspOrder, int saveDataAtr, String stringVal, BigDecimal intVal, GeneralDate dateVal,
			int layoutDisOrder, String contractCode, int reflectID) {
		super();
		this.cid = cid;
		this.workId = workId;
		this.reportID = reportID;
		this.reportLayoutID = reportLayoutID;
		this.reportName = reportName;
		this.layoutItemType = EnumAdaptor.valueOf(layoutItemType, LayoutItemType.class);
		this.categoryId = categoryId;
		this.ctgCode = ctgCode;
		this.ctgName = ctgName;
		this.fixedAtr = fixedAtr;
		this.itemId = itemId;
		this.itemCd = itemCd;
		this.itemName = itemName;
		this.dspOrder = dspOrder;
		this.saveDataAtr = saveDataAtr;
		this.stringVal = stringVal;
		this.intVal = intVal;
		this.dateVal = dateVal;
		this.layoutDisOrder = layoutDisOrder;
		this.contractCode = contractCode;
		this.reflectID = reflectID;
	}

	public static ReportItem createFromJavaType(String cid, int workId, int reportID, int reportLayoutID,
			String reportName, int layoutItemType, String categoryId , String ctgCode, String ctgName, boolean fixedAtr,String itemId, String itemCd,
			String itemName, int dspOrder, int saveDataAtr, String stringVal, BigDecimal intVal,
			GeneralDate dateVal,int layoutDisOrder, String contractCode, int reflectID) {
		return new ReportItem(cid, workId, reportID, reportLayoutID, reportName, layoutItemType,categoryId, ctgCode, ctgName,
				fixedAtr,itemId, itemCd, itemName, dspOrder, saveDataAtr, stringVal, intVal, dateVal,layoutDisOrder, contractCode, reflectID);}
}
