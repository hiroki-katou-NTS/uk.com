package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * @author laitv 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportItemCommand {

	 String cid; // 会社ID
	 int workId; // 業務ID
	 int reportID; // 届出ID
	 int reportLayoutID; // 個別届出種類ID
	 String reportName; 	// 届出名
	 int layoutItemType; // 項目区分
	 String ctgCode; // カテゴリコード
	 String ctgName; // カテゴリ名
	 boolean fixedAtr; // 既定区分
	 String itemCd; // 項目コード
	 String itemName; // 項目名
	 int dspOrder; // 表示順
	 int saveDataAtr; // 保存データ型
	 String stringVal; // 文字列
	 BigDecimal intVal; // 数値
	 GeneralDate dateVal;// 日付
	 int reflectID; // 反映ID
}
