package nts.uk.ctx.sys.assist.infra.entity.tablelist;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * テーブル一覧
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_TABLE_LIST")
public class SspmtTableList extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public SspmtTableListPk tableListPk;

	/**
	 * カテゴリ名
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_NAME")
	public String categoryName;

	/**
	 * データ復旧処理ID
	 */
	@Basic(optional = true)
	@Column(name = "DATA_RECOVERY_PROCESS_ID")
	public String dataRecoveryProcessId;
	
	/**
	 * テーブル日本語名
	 */
	@Basic(optional = false)
	@Column(name = "TABLE_JAPANESE_NAME")
	public String tableJapaneseName;

	/**
	 * テーブル物理名
	 */
	@Basic(optional = false)
	@Column(name = "TABLE_ENGLISH_NAME")
	public String tableEnglishName;

	/**
	 * 付加取得項目_会社ID
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_ACQ_CID")
	public String fieldAcqCid;

	/**
	 * 付加取得項目_日付
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_ACQ_DATE_TIME")
	public String fieldAcqDateTime;

	/**
	 * 付加取得項目_社員ID
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_ACQ_EMPLOYEE_ID")
	public String fieldAcqEmployeeId;

	/**
	 * 付加取得項目_終了日付
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_ACQ_END_DATE")
	public String fieldAcqEndDate;

	/**
	 * 付加取得項目_開始日付
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_ACQ_START_DATE")
	public String fieldAcqStartDate;

	/**
	 * 保存セットコード
	 */
	@Basic(optional = true)
	@Column(name = "PATTERN_CD")
	public String patternCode;

	/**
	 * 保存セット名称
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_SET_NAME")
	public String saveSetName;

	/**
	 * 保存形態
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_FORM")
	public String saveForm;

	/**
	 * 保存日付From
	 */
	@Basic(optional = true)
	@Column(name = "SAVE_DATE_FROM")
	public String saveDateFrom;

	/**
	 * 保存日付To
	 */
	@Basic(optional = true)
	@Column(name = "SAVE_DATE_TO")
	public String saveDateTo;

	/**
	 * 保存時保存範囲
	 */
	@Basic(optional = false)
	@Column(name = "STORAGE_RANGE_SAVED")
	public int storageRangeSaved;

	/**
	 * 保存期間区分
	 */
	@Basic(optional = false)
	@Column(name = "RETENTION_PERIODCLS")
	public int retentionPeriodCls;

	/**
	 * 内部ファイル名
	 */
	@Basic(optional = false)
	@Column(name = "INTERNAL_FILE_NAME")
	public String internalFileName;

	/**
	 * 別会社区分
	 */
	@Basic(optional = false)
	@Column(name = "ANOTHER_COMCLS")
	public int anotherComCls;

	/**
	 * 参照年
	 */
	@Basic(optional = true)
	@Column(name = "REFERENCE_YEAR")
	public String referenceYear;

	/**
	 * 参照月
	 */
	@Basic(optional = true)
	@Column(name = "REFERENCE_MONTH")
	public String referenceMonth;

	/**
	 * 圧縮ファイル名
	 */
	@Basic(optional = false)
	@Column(name = "COMPRESSED_FILE_NAME")
	public String compressedFileName;

	/**
	 * 子側結合キー1
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_1")
	public String fieldChild1;

	/**
	 * 子側結合キー2
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_2")
	public String fieldChild2;

	/**
	 * 子側結合キー3
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_3")
	public String fieldChild3;

	/**
	 * 子側結合キー4
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_4")
	public String fieldChild4;

	/**
	 * 子側結合キー5
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_5")
	public String fieldChild5;

	/**
	 * 子側結合キー6
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_6")
	public String fieldChild6;

	/**
	 * 子側結合キー7
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_7")
	public String fieldChild7;
	/**
	 * 子側結合キー8
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_8")
	public String fieldChild8;

	/**
	 * 子側結合キー9
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_9")
	public String fieldChild9;

	/**
	 * 子側結合キー10
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_CHILD_10")
	public String fieldChild10;

	/**
	 * 履歴区分
	 */
	@Basic(optional = false)
	@Column(name = "HISTORY_CLS")
	public int historyCls;

	/**
	 * 復旧対象可不可
	 */
	@Basic(optional = true)
	@Column(name = "CAN_NOT_BE_OLD")
	public int canNotBeOld;

	/**
	 * 復旧対象選択
	 */
	@Basic(optional = true)
	@Column(name = "SELECTION_TARGET_FOR_RES")
	public int selectionTargetForRes;

	/**
	 * 抽出キー区分1
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_1")
	public String clsKeyQuery1;

	/**
	 * 抽出キー区分2
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_2")
	public String clsKeyQuery2;

	/**
	 * 抽出キー区分3
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_3")
	public String clsKeyQuery3;

	/**
	 * 抽出キー区分4
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_4")
	public String clsKeyQuery4;

	/**
	 * 抽出キー区分5
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_5")
	public String clsKeyQuery5;

	/**
	 * 抽出キー区分6
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_6")
	public String clsKeyQuery6;

	/**
	 * 抽出キー区分7
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_7")
	public String clsKeyQuery7;

	/**
	 * 抽出キー区分8
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_8")
	public String clsKeyQuery8;

	/**
	 * 抽出キー区分9
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_9")
	public String clsKeyQuery9;

	/**
	 * 抽出キー区分10
	 */
	@Basic(optional = true)
	@Column(name = "CLS_KEY_QUERY_10")
	public String clsKeyQuery10;

	/**
	 * 抽出キー項目1
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_1")
	public String fieldKeyQuery1;

	/**
	 * 抽出キー項目2
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_2")
	public String fieldKeyQuery2;

	/**
	 * 抽出キー項目3
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_3")
	public String fieldKeyQuery3;

	/**
	 * 抽出キー項目4
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_4")
	public String fieldKeyQuery4;

	/**
	 * 抽出キー項目
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_5")
	public String fieldKeyQuery5;

	/**
	 * 抽出キー項目6
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_6")
	public String fieldKeyQuery6;

	/**
	 * 抽出キー項目7
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_7")
	public String fieldKeyQuery7;

	/**
	 * 抽出キー項目8
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_8")
	public String fieldKeyQuery8;

	/**
	 * 抽出キー項目9
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_9")
	public String fieldKeyQuery9;

	/**
	 * 抽出キー項目10
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_KEY_QUERY_10")
	public String fieldKeyQuery10;

	/**
	 * 抽出条件キー固定
	 */
	@Basic(optional = true)
	@Column(name = "DEFAULT_COND_KEY_QUERY")
	public String defaultCondKeyQuery;

	/**
	 * 日付項目1
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_1")
	public String fieldDate1;

	/**
	 * 日付項目2
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_2")
	public String fieldDate2;

	/**
	 * 日付項目3
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_3")
	public String fieldDate3;

	/**
	 * 日付項目4
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_4")
	public String fieldDate4;

	/**
	 * 日付項目5
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_5")
	public String fieldDate5;

	/**
	 * 日付項目6
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_6")
	public String fieldDate6;

	/**
	 * 日付項目7
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_7")
	public String fieldDate7;

	/**
	 * 日付項目8
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_8")
	public String fieldDate8;

	/**
	 * 日付項目9
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_9")
	public String fieldDate9;

	/**
	 * 日付項目10
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_10")
	public String fieldDate10;

	/**
	 * 日付項目11
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_11")
	public String fieldDate11;

	/**
	 * 日付項目12
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_12")
	public String fieldDate12;

	/**
	 * 日付項目13
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_13")
	public String fieldDate13;

	/**
	 * 日付項目14
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_14")
	public String fieldDate14;

	/**
	 * 日付項目15
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_15")
	public String fieldDate15;

	/**
	 * 日付項目16
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_16")
	public String fieldDate16;

	/**
	 * 日付項目17
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_17")
	public String fieldDate17;

	/**
	 * 日付項目18
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_18")
	public String fieldDate18;

	/**
	 * 日付項目19
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_19")
	public String fieldDate19;

	/**
	 * 日付項目20
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_DATE_20")
	public String fieldDate20;

	/**
	 * 更新キー項目1
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_1")
	public String filedKeyUpdate1;

	/**
	 * 更新キー項目2
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_2")
	public String filedKeyUpdate2;

	/**
	 * 更新キー項目3
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_3")
	public String filedKeyUpdate3;

	/**
	 * 更新キー項目4
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_4")
	public String filedKeyUpdate4;

	/**
	 * 更新キー項目5
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_5")
	public String filedKeyUpdate5;

	/**
	 * 更新キー項目6
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_6")
	public String filedKeyUpdate6;

	/**
	 * 更新キー項目7
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_7")
	public String filedKeyUpdate7;

	/**
	 * 更新キー項目8
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_8")
	public String filedKeyUpdate8;

	/**
	 * 更新キー項目9
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_9")
	public String filedKeyUpdate9;

	/**
	 * 更新キー項目10
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_10")
	public String filedKeyUpdate10;

	/**
	 * 更新キー項目11
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_11")
	public String filedKeyUpdate11;

	/**
	 * 更新キー項目12
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_12")
	public String filedKeyUpdate12;

	/**
	 * 更新キー項目13
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_13")
	public String filedKeyUpdate13;

	/**
	 * 更新キー項目14
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_14")
	public String filedKeyUpdate14;

	/**
	 * 更新キー項目15
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_15")
	public String filedKeyUpdate15;

	/**
	 * 更新キー項目16
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_16")
	public String filedKeyUpdate16;

	/**
	 * 更新キー項目17
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_17")
	public String filedKeyUpdate17;

	/**
	 * 更新キー項目18
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_18")
	public String filedKeyUpdate18;

	/**
	 * 更新キー項目19
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_19")
	public String filedKeyUpdate19;

	/**
	 * 更新キー項目20
	 */
	@Basic(optional = true)
	@Column(name = "FILED_KEY_UPDATE_20")
	public String filedKeyUpdate20;

	/**
	 * 画面保存期間
	 */
	@Basic(optional = true)
	@Column(name = "SCREEN_RETENTION_PERIOD")
	public String screenRetentionPeriod;

	/**
	 * 補足説明
	 */
	@Basic(optional = true)
	@Column(name = "SUPPLEMENTARY_EXPLANATION")
	public String supplementaryExplanation;

	/**
	 * 親テーブル日本語名
	 */
	@Basic(optional = true)
	@Column(name = "PARENT_TBL_JP_NAME")
	public String parentTblJpName;

	/**
	 * 親テーブル有無
	 */
	@Basic(optional = false)
	@Column(name = "HAS_PARENT_TBL_FLG")
	public int hasParentTblFlg;

	/**
	 * 親テーブル物理名
	 */
	@Basic(optional = true)
	@Column(name = "PARENT_TBL_NAME")
	public String parentTblName;

	/**
	 * 親側結合キー1
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_1")
	public String fieldParent1;

	/**
	 * 親側結合キー2
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_2")
	public String fieldParent2;

	/**
	 * 親側結合キー3
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_3")
	public String fieldParent3;

	/**
	 * 親側結合キー4
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_4")
	public String fieldParent4;

	/**
	 * 親側結合キー5
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_5")
	public String fieldParent5;

	/**
	 * 親側結合キー6
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_6")
	public String fieldParent6;

	/**
	 * 親側結合キー7
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_7")
	public String fieldParent7;

	/**
	 * 親側結合キー8
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_8")
	public String fieldParent8;

	/**
	 * 親側結合キー9
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_9")
	public String fieldParent9;

	/**
	 * 親側結合キー10
	 */
	@Basic(optional = true)
	@Column(name = "FIELD_PARENT_10")
	public String fieldParent10;

	/**
	 * 調査用保存
	 */
	@Basic(optional = false)
	@Column(name = "SURVEY_PRESERVATION")
	public int surveyPreservation;

	@Override
	protected Object getKey() {
		return tableListPk;
	}

	public TableList toDomain() {
		return new TableList(tableListPk.categoryId, categoryName, dataRecoveryProcessId,
				tableListPk.systemType, tableListPk.dataStorageProcessingId, tableListPk.tableNo, tableJapaneseName, tableEnglishName, fieldAcqCid,
				fieldAcqDateTime, fieldAcqEmployeeId, fieldAcqEndDate, fieldAcqStartDate, patternCode, saveSetName,
				saveForm, saveDateFrom, saveDateTo, storageRangeSaved, retentionPeriodCls, internalFileName,
				anotherComCls, referenceYear, referenceMonth, compressedFileName, fieldChild1, fieldChild2, fieldChild3,
				fieldChild4, fieldChild5, fieldChild6, fieldChild7, fieldChild8, fieldChild9, fieldChild10, historyCls,
				canNotBeOld, selectionTargetForRes, clsKeyQuery1, clsKeyQuery2, clsKeyQuery3, clsKeyQuery4,
				clsKeyQuery5, clsKeyQuery6, clsKeyQuery7, clsKeyQuery8, clsKeyQuery9, clsKeyQuery10, fieldKeyQuery1,
				fieldKeyQuery2, fieldKeyQuery3, fieldKeyQuery4, fieldKeyQuery5, fieldKeyQuery6, fieldKeyQuery7,
				fieldKeyQuery8, fieldKeyQuery9, fieldKeyQuery10, defaultCondKeyQuery, fieldDate1, fieldDate2,
				fieldDate3, fieldDate4, fieldDate5, fieldDate6, fieldDate7, fieldDate8, fieldDate9, fieldDate10,
				fieldDate11, fieldDate12, fieldDate13, fieldDate14, fieldDate15, fieldDate16, fieldDate17, fieldDate18,
				fieldDate19, fieldDate20, filedKeyUpdate1, filedKeyUpdate2, filedKeyUpdate3, filedKeyUpdate4,
				filedKeyUpdate5, filedKeyUpdate6, filedKeyUpdate7, filedKeyUpdate8, filedKeyUpdate9, filedKeyUpdate10,
				filedKeyUpdate11, filedKeyUpdate12, filedKeyUpdate13, filedKeyUpdate14, filedKeyUpdate15,
				filedKeyUpdate16, filedKeyUpdate17, filedKeyUpdate18, filedKeyUpdate19, filedKeyUpdate20,
				screenRetentionPeriod, supplementaryExplanation, parentTblJpName, hasParentTblFlg, parentTblName,
				fieldParent1, fieldParent2, fieldParent3, fieldParent4, fieldParent5, fieldParent6, fieldParent7,
				fieldParent8, fieldParent9, fieldParent10, surveyPreservation);
	}

	public static SspmtTableList toEntity(TableList domain) {
		return new SspmtTableList(
				new SspmtTableListPk(domain.getCategoryId(), domain.getTableNo(), domain.getDataStorageProcessingId(), domain.getSystemType().value),
				domain.getCategoryName(), domain.getDataRecoveryProcessId().orElse(null), domain.getTableJapaneseName(),
				domain.getTableEnglishName(), domain.getFieldAcqCid().orElse(null),
				domain.getFieldAcqDateTime().orElse(null), domain.getFieldAcqEmployeeId().orElse(null),
				domain.getFieldAcqEndDate().orElse(null), domain.getFieldAcqStartDate().orElse(null),
				domain.getPatternCode(), domain.getSaveSetName(), domain.getSaveForm(),
				domain.getSaveDateFrom().orElse(null), domain.getSaveDateTo().orElse(null),
				domain.getStorageRangeSaved().value, domain.getRetentionPeriodCls().value, domain.getInternalFileName(),
				domain.getAnotherComCls().value, domain.getReferenceYear().orElse(null),
				domain.getReferenceMonth().orElse(null), domain.getCompressedFileName(),
				domain.getFieldChild1().orElse(null), domain.getFieldChild2().orElse(null),
				domain.getFieldChild3().orElse(null), domain.getFieldChild4().orElse(null),
				domain.getFieldChild5().orElse(null), domain.getFieldChild6().orElse(null),
				domain.getFieldChild7().orElse(null), domain.getFieldChild8().orElse(null),
				domain.getFieldChild9().orElse(null), domain.getFieldChild10().orElse(null),
				domain.getHistoryCls().value, domain.getCanNotBeOld().orElse(null),
				domain.getSelectionTargetForRes().orElse(null), domain.getClsKeyQuery1().orElse(null),
				domain.getClsKeyQuery2().orElse(null), domain.getClsKeyQuery3().orElse(null),
				domain.getClsKeyQuery4().orElse(null), domain.getClsKeyQuery5().orElse(null),
				domain.getClsKeyQuery6().orElse(null), domain.getClsKeyQuery7().orElse(null),
				domain.getClsKeyQuery8().orElse(null), domain.getClsKeyQuery9().orElse(null),
				domain.getClsKeyQuery10().orElse(null), domain.getFieldKeyQuery1().orElse(null),
				domain.getFieldKeyQuery2().orElse(null), domain.getFieldKeyQuery3().orElse(null),
				domain.getFieldKeyQuery4().orElse(null), domain.getFieldKeyQuery5().orElse(null),
				domain.getFieldKeyQuery6().orElse(null), domain.getFieldKeyQuery7().orElse(null),
				domain.getFieldKeyQuery8().orElse(null), domain.getFieldKeyQuery9().orElse(null),
				domain.getFieldKeyQuery10().orElse(null), domain.getDefaultCondKeyQuery().orElse(null),
				domain.getFieldDate1().orElse(null), domain.getFieldDate2().orElse(null),
				domain.getFieldDate3().orElse(null), domain.getFieldDate4().orElse(null),
				domain.getFieldDate5().orElse(null), domain.getFieldDate6().orElse(null),
				domain.getFieldDate7().orElse(null), domain.getFieldDate8().orElse(null),
				domain.getFieldDate9().orElse(null), domain.getFieldDate10().orElse(null),
				domain.getFieldDate11().orElse(null), domain.getFieldDate12().orElse(null),
				domain.getFieldDate13().orElse(null), domain.getFieldDate14().orElse(null),
				domain.getFieldDate15().orElse(null), domain.getFieldDate16().orElse(null),
				domain.getFieldDate17().orElse(null), domain.getFieldDate18().orElse(null),
				domain.getFieldDate19().orElse(null), domain.getFieldDate20().orElse(null),
				domain.getFiledKeyUpdate1().orElse(null), domain.getFiledKeyUpdate2().orElse(null),
				domain.getFiledKeyUpdate3().orElse(null), domain.getFiledKeyUpdate4().orElse(null),
				domain.getFiledKeyUpdate5().orElse(null), domain.getFiledKeyUpdate6().orElse(null),
				domain.getFiledKeyUpdate7().orElse(null), domain.getFiledKeyUpdate8().orElse(null),
				domain.getFiledKeyUpdate9().orElse(null), domain.getFiledKeyUpdate10().orElse(null),
				domain.getFiledKeyUpdate11().orElse(null), domain.getFiledKeyUpdate12().orElse(null),
				domain.getFiledKeyUpdate13().orElse(null), domain.getFiledKeyUpdate14().orElse(null),
				domain.getFiledKeyUpdate15().orElse(null), domain.getFiledKeyUpdate16().orElse(null),
				domain.getFiledKeyUpdate17().orElse(null), domain.getFiledKeyUpdate18().orElse(null),
				domain.getFiledKeyUpdate19().orElse(null), domain.getFiledKeyUpdate20().orElse(null),
				domain.getScreenRetentionPeriod().orElse(null), domain.getSupplementaryExplanation().orElse(null),
				domain.getParentTblJpName().orElse(null), domain.getHasParentTblFlg().value,
				domain.getParentTblName().orElse(null), domain.getFieldParent1().orElse(null),
				domain.getFieldParent2().orElse(null), domain.getFieldParent3().orElse(null),
				domain.getFieldParent4().orElse(null), domain.getFieldParent5().orElse(null),
				domain.getFieldParent6().orElse(null), domain.getFieldParent7().orElse(null),
				domain.getFieldParent8().orElse(null), domain.getFieldParent9().orElse(null),
				domain.getFieldParent10().orElse(null), domain.getSurveyPreservation().value);
	}
}