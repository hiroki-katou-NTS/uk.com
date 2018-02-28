package nts.uk.ctx.exio.app.find.exi.category;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
* 外部受入カテゴリ項目データ
*/
@AllArgsConstructor
@Value
public class ExAcpCtgItemDatDto
{
    
    /**
    * カテゴリID
    */
    private String categoryId;
    
    /**
    * 項目NO
    */
    private int itemNo;
    
    /**
    * 項目名
    */
    private String itemName;
    
    /**
    * アルファベット禁止項目
    */
    private int alphabetProhibitedItem;
    
    /**
    * データ型
    */
    private int dataType;
    
    /**
    * 主キー区分
    */
    private int primaryKeyCls;
    
    /**
    * 小数部単位区分
    */
    private int decimalUnitCls;
    
    /**
    * 小数部桁数
    */
    private String decimalNumberOfDigits;
    
    /**
    * 履歴区分
    */
    private int histCls;
    
    /**
    * 履歴継続区分
    */
    private int histContinuationCls;
    
    /**
    * 必須桁数
    */
    private int requiredNumberOfDigits;
    
    /**
    * 数値範囲開始
    */
    private String numericRangeStart;
    
    /**
    * 数値範囲開始２
    */
    private String numericRangeStart2;
    
    /**
    * 数値範囲終了
    */
    private String numericRangeEnd;
    
    /**
    * 数値範囲終了２
    */
    private String numericRangeEnd2;
    
    /**
    * 特殊区分
    */
    private int specialCls;
    
    /**
    * 表示区分
    */
    private int displayCls;
    
    
    private Long version;
   
}
