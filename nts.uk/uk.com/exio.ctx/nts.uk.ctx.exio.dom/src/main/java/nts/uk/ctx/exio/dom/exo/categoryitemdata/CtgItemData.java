package nts.uk.ctx.exio.dom.exo.categoryitemdata;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItemNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
    private CategoryCd categoryId;
    
    /**
    * データ型
    */
    private DataType dataType;
    
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
    private NotUseAtr primarykeyClassfication;
    
    /**
    * 日付区分
    */
    private String dateClassfication;
    
    /**
    * 特殊項目
    */
    private SpecialItem specialItem;
    
    /**
    * 表示テーブル名
    */
    private String displayTableName;
    
    /**
    * 表示区分
    */
    private NotUseAtr displayClassfication;
    
    /**
    * 項目NO
    */
    private CategoryItemNo itemNo;
    
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

	public CtgItemData(String tblAlias, int categoryId, int dataType, String tableName, String fieldName,
			int primarykeyClassfication, String dateClassfication, int specialItem, String displayTableName,
			int displayClassfication, int itemNo, String itemName, int requiredCategory, String searchValueCd) {
		super();
		this.tblAlias = tblAlias;
		this.dataType = EnumAdaptor.valueOf(dataType, DataType.class);
		this.categoryId = EnumAdaptor.valueOf(categoryId, CategoryCd.class);
		this.dataType = EnumAdaptor.valueOf(dataType, DataType.class);
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.primarykeyClassfication = EnumAdaptor.valueOf(primarykeyClassfication, NotUseAtr.class);
		this.dateClassfication = dateClassfication;
		this.specialItem = EnumAdaptor.valueOf(specialItem, SpecialItem.class);
		this.displayTableName = displayTableName;
		this.displayClassfication = EnumAdaptor.valueOf(displayClassfication, NotUseAtr.class);
		this.itemNo = EnumAdaptor.valueOf(itemNo, CategoryItemNo.class);
		this.itemName = itemName;
		this.requiredCategory = requiredCategory;
		this.searchValueCd = searchValueCd;
	}

}
