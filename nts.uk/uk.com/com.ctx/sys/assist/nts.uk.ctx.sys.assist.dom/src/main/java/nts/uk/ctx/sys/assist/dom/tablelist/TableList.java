package nts.uk.ctx.sys.assist.dom.tablelist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.assist.dom.category.RecoverFormCompanyOther;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.HistoryDiviSion;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
	private String tableEnglishName;

	/**
	 * 付加取得項目_会社ID
	 */
	private String fieldAcqCid;

	/**
	 * 付加取得項目_日付
	 */
	private String fieldAcqDateTime;

	/**
	 * 付加取得項目_社員ID
	 */
	private String fieldAcqEmployeeId;

	/**
	 * 付加取得項目_終了日付
	 */
	private String fieldAcqEndDate;

	/**
	 * 付加取得項目_開始日付
	 */
	private String fieldAcqStartDate;

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
	private TimeStore retentionPeriodCls;

	/**
	 * 内部ファイル名
	 */
	private String internalFileName;

	/**
	 * 別会社区分
	 */
	private RecoverFormCompanyOther anotherComCls;

	/**
	 * 参照年
	 */
	private ReferenceYear referenceYear;

	/**
	 * 参照月
	 */
	private ReferenceMonth referenceMonth;

	/**
	 * 圧縮ファイル名
	 */
	private String compressedFileName;

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
	 * 抽出条件キー固定
	 */
	private String extractCondKeyFix;

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
	private String parenttbljpname;

	/**
	 * 親テーブル有無
	 */
	private String withParentTable;

	/**
	 * 親テーブル物理名
	 */
	private String parenttblname;

	/**
	 * 親側結合キー1
	 */
	private String parentfield1;

	/**
	 * 親側結合キー2
	 */
	private String parentfield2;

	/**
	 * 親側結合キー3
	 */
	private String parentfield3;

	/**
	 * 親側結合キー4
	 */
	private String parentfield4;

	/**
	 * 親側結合キー5
	 */
	private String parentfield5;

	/**
	 * 親側結合キー6
	 */
	private String parentfield6;

	/**
	 * 親側結合キー7
	 */
	private String parentfield7;

	/**
	 * 親側結合キー8
	 */
	private String parentfield8;

	/**
	 * 親側結合キー9
	 */
	private String parentfield9;

	/**
	 * 親側結合キー10
	 */
	private String parentfield10;

	/**
	 * 調査用保存
	 */
	private NotUseAtr surveyPreservation;
	
	
	

}
