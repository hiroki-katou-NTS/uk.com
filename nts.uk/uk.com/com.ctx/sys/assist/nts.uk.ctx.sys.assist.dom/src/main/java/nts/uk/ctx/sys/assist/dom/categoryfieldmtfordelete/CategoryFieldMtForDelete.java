package nts.uk.ctx.sys.assist.dom.categoryfieldmtfordelete;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * カテゴリ項目マスタ
 */
@AllArgsConstructor
@Getter
public class CategoryFieldMtForDelete extends AggregateRoot {

	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * システム種類
	 */
	private SystemType systemType;

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
	 * 親テーブル日本語名
	 */
	private String parentTblJpName;

	/**
	 * 親テーブル有無
	 */
	private NotUseAtr hasParentTblFlg;

	/**
	 * 親テーブル物理名
	 */
	private String parentTblName;

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

	public CategoryFieldMtForDelete(String categoryId, int systemType, int tableNo, String tableJapanName,
			String tableEnglishName, String timeStopDelete, String clsKeyQuery1, String clsKeyQuery2,
			String clsKeyQuery3, String clsKeyQuery4, String clsKeyQuery5, String clsKeyQuery6, String clsKeyQuery7,
			String clsKeyQuery8, String clsKeyQuery9, String clsKeyQuery10, String defaultCondKeyQuery,
			String fieldKeyQuery1, String fieldKeyQuery2, String fieldKeyQuery3, String fieldKeyQuery4,
			String fieldKeyQuery5, String fieldKeyQuery6, String fieldKeyQuery7, String fieldKeyQuery8,
			String fieldKeyQuery9, String fieldKeyQuery10, String fieldDate1, String fieldDate2, String fieldDate3,
			String fieldDate4, String fieldDate5, String fieldDate6, String fieldDate7, String fieldDate8,
			String fieldDate9, String fieldDate10, String fieldDate11, String fieldDate12, String fieldDate13,
			String fieldDate14, String fieldDate15, String fieldDate16, String fieldDate17, String fieldDate18,
			String fieldDate19, String fieldDate20, String filedKeyUpdate1, String filedKeyUpdate2,
			String filedKeyUpdate3, String filedKeyUpdate4, String filedKeyUpdate5, String filedKeyUpdate6,
			String filedKeyUpdate7, String filedKeyUpdate8, String filedKeyUpdate9, String filedKeyUpdate10,
			String filedKeyUpdate11, String filedKeyUpdate12, String filedKeyUpdate13, String filedKeyUpdate14,
			String filedKeyUpdate15, String filedKeyUpdate16, String filedKeyUpdate17, String filedKeyUpdate18,
			String filedKeyUpdate19, String filedKeyUpdate20, int historyCls, String parentTblJpName,
			int hasParentTblFlg, String parentTblName, String fieldParent1, String fieldParent2, String fieldParent3,
			String fieldParent4, String fieldParent5, String fieldParent6, String fieldParent7, String fieldParent8,
			String fieldParent9, String fieldParent10, String fieldChild1, String fieldChild2, String fieldChild3,
			String fieldChild4, String fieldChild5, String fieldChild6, String fieldChild7, String fieldChild8,
			String fieldChild9, String fieldChild10, String fieldAcqCid, String fieldAcqDateTime,
			String fieldAcqEmployeeId, String fieldAcqEndDate, String fieldAcqStartDate) {
		super();
		this.categoryId = categoryId;
		this.systemType = EnumAdaptor.valueOf(systemType, SystemType.class);
		this.tableNo = tableNo;
		this.tableJapanName = tableJapanName;
		this.tableEnglishName = tableEnglishName;
		this.timeStopDelete = timeStopDelete;
		this.clsKeyQuery1 = clsKeyQuery1;
		this.clsKeyQuery2 = clsKeyQuery2;
		this.clsKeyQuery3 = clsKeyQuery3;
		this.clsKeyQuery4 = clsKeyQuery4;
		this.clsKeyQuery5 = clsKeyQuery5;
		this.clsKeyQuery6 = clsKeyQuery6;
		this.clsKeyQuery7 = clsKeyQuery7;
		this.clsKeyQuery8 = clsKeyQuery8;
		this.clsKeyQuery9 = clsKeyQuery9;
		this.clsKeyQuery10 = clsKeyQuery10;
		this.defaultCondKeyQuery = defaultCondKeyQuery;
		this.fieldKeyQuery1 = fieldKeyQuery1;
		this.fieldKeyQuery2 = fieldKeyQuery2;
		this.fieldKeyQuery3 = fieldKeyQuery3;
		this.fieldKeyQuery4 = fieldKeyQuery4;
		this.fieldKeyQuery5 = fieldKeyQuery5;
		this.fieldKeyQuery6 = fieldKeyQuery6;
		this.fieldKeyQuery7 = fieldKeyQuery7;
		this.fieldKeyQuery8 = fieldKeyQuery8;
		this.fieldKeyQuery9 = fieldKeyQuery9;
		this.fieldKeyQuery10 = fieldKeyQuery10;
		this.fieldDate1 = fieldDate1;
		this.fieldDate2 = fieldDate2;
		this.fieldDate3 = fieldDate3;
		this.fieldDate4 = fieldDate4;
		this.fieldDate5 = fieldDate5;
		this.fieldDate6 = fieldDate6;
		this.fieldDate7 = fieldDate7;
		this.fieldDate8 = fieldDate8;
		this.fieldDate9 = fieldDate9;
		this.fieldDate10 = fieldDate10;
		this.fieldDate11 = fieldDate11;
		this.fieldDate12 = fieldDate12;
		this.fieldDate13 = fieldDate13;
		this.fieldDate14 = fieldDate14;
		this.fieldDate15 = fieldDate15;
		this.fieldDate16 = fieldDate16;
		this.fieldDate17 = fieldDate17;
		this.fieldDate18 = fieldDate18;
		this.fieldDate19 = fieldDate19;
		this.fieldDate20 = fieldDate20;
		this.filedKeyUpdate1 = filedKeyUpdate1;
		this.filedKeyUpdate2 = filedKeyUpdate2;
		this.filedKeyUpdate3 = filedKeyUpdate3;
		this.filedKeyUpdate4 = filedKeyUpdate4;
		this.filedKeyUpdate5 = filedKeyUpdate5;
		this.filedKeyUpdate6 = filedKeyUpdate6;
		this.filedKeyUpdate7 = filedKeyUpdate7;
		this.filedKeyUpdate8 = filedKeyUpdate8;
		this.filedKeyUpdate9 = filedKeyUpdate9;
		this.filedKeyUpdate10 = filedKeyUpdate10;
		this.filedKeyUpdate11 = filedKeyUpdate11;
		this.filedKeyUpdate12 = filedKeyUpdate12;
		this.filedKeyUpdate13 = filedKeyUpdate13;
		this.filedKeyUpdate14 = filedKeyUpdate14;
		this.filedKeyUpdate15 = filedKeyUpdate15;
		this.filedKeyUpdate16 = filedKeyUpdate16;
		this.filedKeyUpdate17 = filedKeyUpdate17;
		this.filedKeyUpdate18 = filedKeyUpdate18;
		this.filedKeyUpdate19 = filedKeyUpdate19;
		this.filedKeyUpdate20 = filedKeyUpdate20;
		this.historyCls = EnumAdaptor.valueOf(historyCls, HistoryDiviSion.class);
		this.parentTblJpName = parentTblJpName;
		this.hasParentTblFlg = EnumAdaptor.valueOf(hasParentTblFlg, NotUseAtr.class);
		this.parentTblName = parentTblName;
		this.fieldParent1 = fieldParent1;
		this.fieldParent2 = fieldParent2;
		this.fieldParent3 = fieldParent3;
		this.fieldParent4 = fieldParent4;
		this.fieldParent5 = fieldParent5;
		this.fieldParent6 = fieldParent6;
		this.fieldParent7 = fieldParent7;
		this.fieldParent8 = fieldParent8;
		this.fieldParent9 = fieldParent9;
		this.fieldParent10 = fieldParent10;
		this.fieldChild1 = fieldChild1;
		this.fieldChild2 = fieldChild2;
		this.fieldChild3 = fieldChild3;
		this.fieldChild4 = fieldChild4;
		this.fieldChild5 = fieldChild5;
		this.fieldChild6 = fieldChild6;
		this.fieldChild7 = fieldChild7;
		this.fieldChild8 = fieldChild8;
		this.fieldChild9 = fieldChild9;
		this.fieldChild10 = fieldChild10;
		this.fieldAcqCid = fieldAcqCid;
		this.fieldAcqDateTime = fieldAcqDateTime;
		this.fieldAcqEmployeeId = fieldAcqEmployeeId;
		this.fieldAcqEndDate = fieldAcqEndDate;
		this.fieldAcqStartDate = fieldAcqStartDate;
	}

}
