package nts.uk.ctx.hr.notice.dom.report;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * domain 個別届出の登録項目
 * @author lanlt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPersonalReportItem extends AggregateRoot{
	//会社ID
	private String companyId;
	//個別届出種類ID
	private int pReportClsId;
	//個別届出コード
	private String pReportCode;
	//個別届出名
	private String pReportName;
	//項目区分
	private int itemType;
	//カテゴリコード
	private String categoryCd;	
	//カテゴリ名
	private String categoryName;	
	//契約コード
	private String contractCd;	
	//既定区分
	private boolean fixedAtr;
	//項目コード
	private String itemCd;
	//項目名
	private String itemName;
	//表示順
	private int displayOrder;
	//Optional 廃止区分
	private Optional<Boolean> isAbolition;
	//帳票差し込みの項目名称
	//trích từ skype lần này chưa dùng nhé 本属性は将来発注機能にて使用予定です。今回の機能では不使用なので追加はその時にお願いします。
	private String itemNameFormMerge;
	//反映ID
	private int reflectionId;
	
	private String categoryId;
	
	private String itemId;
	
	//表示順
	private int layoutOrder;
}
