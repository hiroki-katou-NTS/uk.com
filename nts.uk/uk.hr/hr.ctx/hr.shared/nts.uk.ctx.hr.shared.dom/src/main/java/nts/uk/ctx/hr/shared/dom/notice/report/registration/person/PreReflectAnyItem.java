package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreReflectAnyItem extends DomainObject{

	private String histId; // 新規GUIDをセット
	
	private String parentHistId; // (※1)で作成したGUIDをセット
	
	private String cid; // 会社ID
	
	private int reportId; // 届出ID
	
	private int dispOrder; // 表示順
	
	private String categoryId; // カテゴリID
	
	private String categoryCode; // カテゴリコード
	
	private String itemId; // 項目ID
	
	private String itemCode; // 項目コード	
	
	private int saveDataAtr; // 保存データ型
	
	private String stringVal; // 文字列
	
	private BigDecimal intVal; // 数値
	
	private GeneralDate dateVal;// 日付
	
}
