package nts.uk.ctx.exio.app.command.exo.categoryitemdata;

import lombok.Value;

@Value
public class CtgItemDataCommand
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
    
    private Long version;

}
