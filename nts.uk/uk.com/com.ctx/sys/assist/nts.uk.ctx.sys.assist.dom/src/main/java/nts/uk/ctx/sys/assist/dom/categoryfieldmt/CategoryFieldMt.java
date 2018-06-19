package nts.uk.ctx.sys.assist.dom.categoryfieldmt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* カテゴリ項目マスタ
*/
@AllArgsConstructor
@Getter
public class CategoryFieldMt extends AggregateRoot
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
    * 削除禁止期間
    */
    private String timeStopDelete;
    
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
    private HistoryDiviSion historyCls;
 
    /**
     * するしない区分
     */
    private boolean hasParentTblFlg;
    
    /**
     * テーブル物理名
     */
    private String parentTblName;
    
    /**
     * テーブル日本語名
     */
    private String parentTblJapanName;
    
    /**
     * 付加取得項目_日付
     */
    private String fieldAcqDateTime;
    
    /**
     * 付加取得項目_終了日付
     */
    private String fieldAcqEndDate;
    
    /**
     * 付加取得項目_社員ID
     */
    private String fieldAcqEmployeeId;
    
    /**
     * 付加取得項目_開始日付
     */
    private String fieldAcqStartDate;
    
    /**
     * 付加取得項目_会社ID
     */
    private String fieldAcqCid;
    
    /**
     * 親側結合キー1
     */
    private String fieldParent1;
    
    /**
     * 親側結合キー2
     */
    private String fieldParent2;
    
    /**
     * 親側結合キー3
     */
    private String fieldParent3;
    
    /**
     * 親側結合キー4
     */
    private String fieldParent4;
    
    /**
     * 親側結合キー5
     */
    private String fieldParent5;
    
    /**
     * 親側結合キー6
     */
    private String fieldParent6;
    
    /**
     * 親側結合キー7
     */
    private String fieldParent7;
    
    /**
     * 親側結合キー8
     */
    private String fieldParent8;
    
    /**
     * 親側結合キー9
     */
    private String fieldParent9;
    
    /**
     * 親側結合キー10
     */
    private String fieldParent10;
    
    /**
     * 子側結合キー1
     */
    private String fieldChild1;
    
    /**
     * 子側結合キー2
     */
    private String fieldChild2;
    
    /**
     * 子側結合キー3
     */
    private String fieldChild3;
    
    /**
     * 子側結合キー4
     */
    private String fieldChild4;
    
    /**
     * 子側結合キー5
     */
    private String fieldChild5;
    
    /**
     * 子側結合キー6
     */
    private String fieldChild6;
    
    /**
     * 子側結合キー7
     */
    private String fieldChild7;
    
    /**
     * 子側結合キー8
     */
    private String fieldChild8;
    
    /**
     * 子側結合キー9
     */
    private String fieldChild9;
    
    /**
     * 子側結合キー10
     */
    private String fieldChild10;
}
