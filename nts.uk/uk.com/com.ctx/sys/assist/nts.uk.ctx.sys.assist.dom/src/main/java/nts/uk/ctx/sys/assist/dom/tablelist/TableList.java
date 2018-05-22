package nts.uk.ctx.sys.assist.dom.tablelist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.HistoryDiviSion;

/**
 * テーブル一覧
 */
@AllArgsConstructor
@Getter
public class TableList extends AggregateRoot {
	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * カテゴリ名
	 */
	private String categoryName;

	/**
	 * データ保存処理ID
	 */
	private String dataStorageProcessingId;

	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * テーブルNo
	 */
	private int tableNo;

	/**
	 * テーブル日本語名
	 */
	private String tableJapaneseName;

	/**
	 * テーブル物理名
	 */
	private String tablePhysicalName;

	/**
	 * 付加取得項目_会社ID
	 */
	private String acquisitionItemCompanyId;

	/**
	 * 付加取得項目_日付
	 */
	private String acquisitionItemDate;

	/**
	 * 付加取得項目_社員ID
	 */
	private String acquisitionItemEmployeeId;

	/**
	 * 付加取得項目_終了日付
	 */
	private String acquisitionItemEndDate;

	/**
	 * 付加取得項目_開始日付
	 */
	private String acquisitionItemStartDate;

	/**
	 * 保存セットコード
	 */
	private String saveSetCode;

	/**
	 * 保存セット名称
	 */
	private String saveSetName;

	/**
	 * 保存ファイル名
	 */
	private String saveFileName;

	/**
	 * 保存形態
	 */
	private String saveForm;

	/**
	 * 保存日付From
	 */
	private String saveDateFrom;

	/**
	 * 保存日付To
	 */
	private String saveDateTo;

	/**
	 * 保存時保存範囲
	 */
	private String storageRangeSaved;

	/**
	 * 保存期間区分
	 */
	private String retentionPeriodCls;

	/**
	 * 内部ファイル名
	 */
	private String internalFileName;

	/**
	 * 別会社区分
	 */
	private String anotherComCls;

	/**
	 * 参照年
	 */
	private String referenceYear;

	/**
	 * 参照月
	 */
	private String referenceMonth;

	/**
	 * 圧縮ファイル名
	 */
	private String compressedFileName;

	/**
	 * 子側結合キー1
	 */
	private String childSideKey1;

	/**
	 * 子側結合キー2
	 */
	private String childSideKey2;

	/**
	 * 子側結合キー3
	 */
	private String childSideKey3;

	/**
	 * 子側結合キー4
	 */
	private String childSideKey4;

	/**
	 * 子側結合キー5
	 */
	private String childSideKey5;

	/**
	 * 子側結合キー6
	 */
	private String childSideKey6;

	/**
	 * 子側結合キー7
	 */
	private String childSideKey7;

	/**
	 * 子側結合キー8
	 */
	private String childSideKey8;

	/**
	 * 子側結合キー9
	 */
	private String childSideKey9;

	/**
	 * 子側結合キー10
	 */
	private String childSideKey10;

	/**
	 * 履歴区分
	 */
	private HistoryDiviSion historyCls;

	/**
	 * 復旧対象可不可
	 */
	private String canNotBeOld;

	/**
	 * 復旧対象選択
	 */
	private String selectionTargetForRes;

	/**
	 * 抽出キー区分1
	 */
	private String extractKeyCls1;

	/**
	 * 抽出キー区分2
	 */
	private String extractKeyCls2;

	/**
	 * 抽出キー区分3
	 */
	private String extractKeyCls3;

	/**
	 * 抽出キー区分4
	 */
	private String extractKeyCls4;

	/**
	 * 抽出キー区分5
	 */
	private String extractKeyCls5;

	/**
	 * 抽出キー区分6
	 */
	private String extractKeyCls6;

	/**
	 * 抽出キー区分7
	 */
	private String extractKeyCls7;

	/**
	 * 抽出キー区分8
	 */
	private String extractKeyCls8;

	/**
	 * 抽出キー区分9
	 */
	private String extractKeyCls9;

	/**
	 * 抽出キー区分10
	 */
	private String extractKeyCls10;

	/**
	 * 抽出キー項目1
	 */
	private String extractKeyItem1;

	/**
	 * 抽出キー項目2
	 */
	private String extractKeyItem2;

	/**
	 * 抽出キー項目3
	 */
	private String extractKeyItem3;

	/**
	 * 抽出キー項目4
	 */
	private String extractKeyItem4;

	/**
	 * 抽出キー項目5
	 */
	private String extractKeyItem5;

	/**
	 * 抽出キー項目6
	 */
	private String extractKeyItem6;

	/**
	 * 抽出キー項目7
	 */
	private String extractKeyItem7;

	/**
	 * 抽出キー項目8
	 */
	private String extractKeyItem8;

	/**
	 * 抽出キー項目9
	 */
	private String extractKeyItem9;

	/**
	 * 抽出キー項目10
	 */
	private String extractKeyItem10;

	/**
	 * 抽出条件キー固定
	 */
	private String extractCondKeyFix;

	/**
	 * 日付項目1
	 */
	private String dateItem1;

	/**
	 * 日付項目
	 */
	private String dateItem2;

	/**
	 * 日付項目3
	 */
	private String dateItem3;

	/**
	 * 日付項目4
	 */
	private String dateItem4;

	/**
	 * 日付項目5
	 */
	private String dateItem5;

	/**
	 * 日付項目6
	 */
	private String dateItem6;

	/**
	 * 日付項目7
	 */
	private String dateItem7;

	/**
	 * 日付項目8
	 */
	private String dateItem8;

	/**
	 * 日付項目9
	 */
	private String dateItem9;

	/**
	 * 日付項目10
	 */
	private String dateItem10;

	/**
	 * 日付項目11
	 */
	private String dateItem11;

	/**
	 * 日付項目12
	 */
	private String dateItem12;

	/**
	 * 日付項目13
	 */
	private String dateItem13;

	/**
	 * 日付項目14
	 */
	private String dateItem14;

	/**
	 * 日付項目15
	 */
	private String dateItem15;

	/**
	 * 日付項目16
	 */
	private String dateItem16;

	/**
	 * 日付項目17
	 */
	private String dateItem17;

	/**
	 * 日付項目18
	 */
	private String dateItem18;
	/**
	 * 日付項目19
	 */
	private String dateItem19;

	/**
	 * 日付項目20
	 */
	private String dateItem20;

	/**
	 * 更新キー項目1
	 */
	private String updateKeyItem1;

	/**
	 * 更新キー項目2
	 */
	private String updateKeyItem2;

	/**
	 * 更新キー項目3
	 */
	private String updateKeyItem3;

	/**
	 * 更新キー項目4
	 */
	private String updateKeyItem4;

	/**
	 * 更新キー項目5
	 */
	private String updateKeyItem5;

	/**
	 * 更新キー項目6
	 */
	private String updateKeyItem6;

	/**
	 * 更新キー項目7
	 */
	private String updateKeyItem7;

	/**
	 * 更新キー項目8
	 */
	private String updateKeyItem8;

	/**
	 * 更新キー項目9
	 */
	private String updateKeyItem9;

	/**
	 * 更新キー項目10
	 */
	private String updateKeyItem10;

	/**
	 * 更新キー項目11
	 */
	private String updateKeyItem11;

	/**
	 * 更新キー項目12
	 */
	private String updateKeyItem12;

	/**
	 * 更新キー項目13
	 */
	private String updateKeyItem13;

	/**
	 * 更新キー項目14
	 */
	private String updateKeyItem14;

	/**
	 * 更新キー項目15
	 */
	private String updateKeyItem15;

	/**
	 * 更新キー項目16
	 */
	private String updateKeyItem16;

	/**
	 * 更新キー項目17
	 */
	private String updateKeyItem17;

	/**
	 * 更新キー項目18
	 */
	private String updateKeyItem18;

	/**
	 * 更新キー項目19
	 */
	private String updateKeyItem19;

	/**
	 * 更新キー項目20
	 */
	private String updateKeyItem20;

	/**
	 * 画面保存期間
	 */
	private String screenRetentionPeriod;

	/**
	 * 補足説明
	 */
	private String supplementaryExplanation;

	/**
	 * 親テーブル日本語名
	 */
	private String parentTableJapaneName;

	/**
	 * 親テーブル有無
	 */
	private String withParentTable;

	/**
	 * 親テーブル物理名
	 */
	private String parentTablePhysicalName;

	/**
	 * 親側結合キー1
	 */
	private String parentSidedKey1;

	/**
	 * 親側結合キー2
	 */
	private String parentSidedKey2;

	/**
	 * 親側結合キー3
	 */
	private String parentSidedKey3;

	/**
	 * 親側結合キー4
	 */
	private String parentSidedKey4;

	/**
	 * 親側結合キー5
	 */
	private String parentSidedKey5;

	/**
	 * 親側結合キー6
	 */
	private String parentSidedKey6;

	/**
	 * 親側結合キー7
	 */
	private String parentSidedKey7;

	/**
	 * 親側結合キー8
	 */
	private String parentSidedKey8;

	/**
	 * 親側結合キー9
	 */
	private String parentSidedKey9;

	/**
	 * 親側結合キー10
	 */
	private String parentSidedKey10;

	/**
	 * 調査用保存
	 */
	private String surveyPreservation;
	
	
	

}
