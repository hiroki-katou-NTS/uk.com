package nts.uk.ctx.exio.dom.exo.categoryitemdata;


import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


/**
* 外部出力カテゴリ項目データ
*/

@Getter
public class CtgItemData extends AggregateRoot
{
    
    /**
    * TBL別名
    */
    private String tblAlias;
    
    /**
    * カテゴリID
    */
    private String categoryId;
    
    /**
    * データ型
    */
    private int dataType;
    
    /**
    * テーブル
    */
    private String tableName;
    
    /**
    * フィールド
    */
    private String fieldName;
    
    /**
    * 主キー区分
    */
    private int primarykeyClassfication;
    
    /**
    * 日付区分
    */
    private String dateClassfication;
    
    /**
    * 特殊項目
    */
    private int specialItem;
    
    /**
    * 表示テーブル名
    */
    private String displayTableName;
    
    /**
    * 表示区分
    */
    private int displayClassfication;
    
    /**
    * 項目NO
    */
    private String itemNo;
    
    /**
    * 項目名
    */
    private String itemName;
    
    /**
    * 必須区分
    */
    private int requiredCategory;
    
    /**
    * 検索値コード
    */
    private String searchValueCd;

	public CtgItemData(String tblAlias, int dataType, String tableName, String fieldName,
			int primarykeyClassfication, String dateClassfication, int specialItem, String displayTableName,
			int displayClassfication, String itemName, int requiredCategory, String searchValueCd) {
		super();
		this.tblAlias = tblAlias;
		this.dataType = dataType;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.primarykeyClassfication = primarykeyClassfication;
		this.dateClassfication = dateClassfication;
		this.specialItem = specialItem;
		this.displayTableName = displayTableName;
		this.displayClassfication = displayClassfication;
		this.itemName = itemName;
		this.requiredCategory = requiredCategory;
		this.searchValueCd = searchValueCd;
	}
    
    
}
