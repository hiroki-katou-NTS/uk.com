package nts.uk.ctx.sys.assist.app.find.categoryfieldmt;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.HistoryDiviSion;

/**
* カテゴリ項目マスタ
*/
@AllArgsConstructor
@Value
public class CategoryFieldMtDto
{
    
    /**
    * カテゴリID
    */
    private String categoryId;
    
    /**
    * テーブルNo
    */
    private int tableNo;
    
    /**
    * テーブル日本語名
    */
    private String tableJapanName;
    
    /**
    * テーブル物理名
    */
    private String tableEnglishName;
    
   
    /**
    * 補正区分
    */
    private int correctClasscification;
    
    
    /**
    * 置き換え列
    */
    private String replaceColumn;
    
    /**
    * 削除禁止期間
    */
    private int timeStopDelete;
    
    /**
    * 抽出キー区分1
    */
    private String clsKeyQuery1;
    
    /**
    * 抽出キー区分2
    */
    private String clsKeyQuery2;
    
    /**
    * 抽出キー区分3
    */
    private String clsKeyQuery3;
    
    /**
    * 抽出キー区分4
    */
    private String clsKeyQuery4;
    
    /**
    * 抽出キー区分5
    */
    private String clsKeyQuery5;
    
    /**
    * 抽出キー区分6
    */
    private String clsKeyQuery6;
    
    /**
    * 抽出キー区分7
    */
    private String clsKeyQuery7;
    
    /**
    * 抽出キー区分8
    */
    private String clsKeyQuery8;
    
    /**
    * 抽出キー区分9
    */
    private String clsKeyQuery9;
    
    /**
    * 抽出キー区分10
    */
    private String clsKeyQuery10;
    
    /**
    * 抽出キー条件固定
    */
    private String defaultCondKeyQuery;
    
    /**
    * 抽出キー項目1
    */
    private String fieldKeyQuery1;
    
    /**
    * 抽出キー項目2
    */
    private String fieldKeyQuery2;
    
    /**
    * 抽出キー項目3
    */
    private String fieldKeyQuery3;
    
    /**
    * 抽出キー項目4
    */
    private String fieldKeyQuery4;
    
    /**
    * 抽出キー項目5
    */
    private String fieldKeyQuery5;
    
    /**
    * 抽出キー項目6
    */
    private String fieldKeyQuery6;
    
    /**
    * 抽出キー項目7
    */
    private String fieldKeyQuery7;
    
    /**
    * 抽出キー項目8
    */
    private String fieldKeyQuery8;
    
    /**
    * 抽出キー項目9
    */
    private String fieldKeyQuery9;
    
    /**
    * 抽出キー項目10
    */
    private String fieldKeyQuery10;
    
    /**
    * 日付項目1
    */
    private String fieldDate1;
    
    /**
    * 日付項目2
    */
    private String fieldDate2;
    
    /**
    * 日付項目3
    */
    private String fieldDate3;
    
    /**
    * 日付項目4
    */
    private String fieldDate4;
    
    /**
    * 日付項目5
    */
    private String fieldDate5;
    
    /**
    * 日付項目6
    */
    private String fieldDate6;
    
    /**
    * 日付項目7
    */
    private String fieldDate7;
    
    /**
    * 日付項目8
    */
    private String fieldDate8;
    
    /**
    * 日付項目9
    */
    private String fieldDate9;
    
    /**
    * 日付項目10
    */
    private String fieldDate10;
    
    /**
    * 日付項目11
    */
    private String fieldDate11;
    
    /**
    * 日付項目12
    */
    private String fieldDate12;
    
    /**
    * 日付項目13
    */
    private String fieldDate13;
    
    /**
    * 日付項目14
    */
    private String fieldDate14;
    
    /**
    * 日付項目15
    */
    private String fieldDate15;
    
    /**
    * 日付項目16
    */
    private String fieldDate16;
    
    /**
    * 日付項目17
    */
    private String fieldDate17;
    
    /**
    * 日付項目18
    */
    private String fieldDate18;
    
    /**
    * 日付項目19
    */
    private String fieldDate19;
    
    /**
    * 日付項目20
    */
    private String fieldDate20;
    
    /**
    * 更新キー項目1
    */
    private String filedKeyUpdate1;
    
    /**
    * 更新キー項目2
    */
    private String filedKeyUpdate2;
    
    /**
    * 更新キー項目3
    */
    private String filedKeyUpdate3;
    
    /**
    * 更新キー項目4
    */
    private String filedKeyUpdate4;
    
    /**
    * 更新キー項目5
    */
    private String filedKeyUpdate5;
    
    /**
    * 更新キー項目6
    */
    private String filedKeyUpdate6;
    
    /**
    * 更新キー項目7
    */
    private String filedKeyUpdate7;
    
    /**
    * 更新キー項目8
    */
    private String filedKeyUpdate8;
    
    /**
    * 更新キー項目9
    */
    private String filedKeyUpdate9;
    
    /**
    * 更新キー項目10
    */
    private String filedKeyUpdate10;
    
    /**
    * 更新キー項目11
    */
    private String filedKeyUpdate11;
    
    /**
    * 更新キー項目12
    */
    private String filedKeyUpdate12;
    
    /**
    * 更新キー項目13
    */
    private String filedKeyUpdate13;
    
    /**
    * 更新キー項目14
    */
    private String filedKeyUpdate14;
    
    /**
    * 更新キー項目15
    */
    private String filedKeyUpdate15;
    
    /**
    * 更新キー項目16
    */
    private String filedKeyUpdate16;
    
    /**
    * 更新キー項目17
    */
    private String filedKeyUpdate17;
    
    /**
    * 更新キー項目18
    */
    private String filedKeyUpdate18;
    
    /**
    * 更新キー項目19
    */
    private String filedKeyUpdate19;
    
    /**
    * 更新キー項目20
    */
    private String filedKeyUpdate20;
    
    /**
    * 履歴区分
    */
    private HistoryDiviSion historyDivision;
    
    
    /*public static CategoryFieldMtDto fromDomain(CategoryFieldMt domain)
    {
        return new CategoryFieldMtDto(domain.getCategoryId(), domain.getTableNo(), domain.getTableJapanName(), domain.getTableEnglishName(), domain.getCorrectClasscification(), domain.getReplaceColumn(), domain.getTimeStopDelete(), domain.getClsKeyQuery1(), domain.getClsKeyQuery2(), domain.getClsKeyQuery3(), domain.getClsKeyQuery4(), domain.getClsKeyQuery5(), domain.getClsKeyQuery6(), domain.getClsKeyQuery7(), domain.getClsKeyQuery8(), domain.getClsKeyQuery9(), domain.getClsKeyQuery10(), domain.getDefaultCondKeyQuery(), domain.getFieldKeyQuery1(), domain.getFieldKeyQuery2(), domain.getFieldKeyQuery3(), domain.getFieldKeyQuery4(), domain.getFieldKeyQuery5(), domain.getFieldKeyQuery6(), domain.getFieldKeyQuery7(), domain.getFieldKeyQuery8(), domain.getFieldKeyQuery9(), domain.getFieldKeyQuery10(), domain.getFieldDate1(), domain.getFieldDate2(), domain.getFieldDate3(), domain.getFieldDate4(), domain.getFieldDate5(), domain.getFieldDate6(), domain.getFieldDate7(), domain.getFieldDate8(), domain.getFieldDate9(), domain.getFieldDate10(), domain.getFieldDate11(), domain.getFieldDate12(), domain.getFieldDate13(), domain.getFieldDate14(), domain.getFieldDate15(), domain.getFieldDate16(), domain.getFieldDate17(), domain.getFieldDate18(), domain.getFieldDate19(), domain.getFieldDate20(), domain.getFiledKeyUpdate1(), domain.getFiledKeyUpdate2(), domain.getFiledKeyUpdate3(), domain.getFiledKeyUpdate4(), domain.getFiledKeyUpdate5(), domain.getFiledKeyUpdate6(), domain.getFiledKeyUpdate7(), domain.getFiledKeyUpdate8(), domain.getFiledKeyUpdate9(), domain.getFiledKeyUpdate10(), domain.getFiledKeyUpdate11(), domain.getFiledKeyUpdate12(), domain.getFiledKeyUpdate13(), domain.getFiledKeyUpdate14(), domain.getFiledKeyUpdate15(), domain.getFiledKeyUpdate16(), domain.getFiledKeyUpdate17(), domain.getFiledKeyUpdate18(), domain.getFiledKeyUpdate19(), domain.getFiledKeyUpdate20(), domain.getHistoryDivision());
    }*/
    
}
