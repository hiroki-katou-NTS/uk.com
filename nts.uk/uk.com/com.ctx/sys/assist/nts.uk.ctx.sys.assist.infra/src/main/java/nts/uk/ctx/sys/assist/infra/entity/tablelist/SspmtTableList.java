package nts.uk.ctx.sys.assist.infra.entity.tablelist;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.HistoryDiviSion;
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
	 * データ保存処理ID
	 */
	@Basic(optional = false)
	@Column(name = "DATA_STORAGE_PROCESSING_ID")
	public String dataStorageProcessingId;

	/**
	 * データ復旧処理ID
	 */
	@Basic(optional = false)
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
	@Column(name = "TABLE_PHYSICAL_NAME")
	public String tablePhysicalName;

	/**
	 * 付加取得項目_会社ID
	 */
	@Basic(optional = false)
	@Column(name = "ACQUISITION_ITEM_COMPANYID")
	public String acquisitionItemCompanyId;

	/**
	 * 付加取得項目_日付
	 */
	@Basic(optional = false)
	@Column(name = "ACQUISITION_ITEM_DATE")
	public String acquisitionItemDate;

	/**
	 * 付加取得項目_社員ID
	 */
	@Basic(optional = false)
	@Column(name = "ACQUISITION_ITEM_EMPLOYEE_ID")
	public String acquisitionItemEmployeeId;

	/**
	 * 付加取得項目_終了日付
	 */
	@Basic(optional = false)
	@Column(name = "ACQUISITION_ITEM_END_DATE")
	public String acquisitionItemEndDate;

	/**
	 * 付加取得項目_開始日付
	 */
	@Basic(optional = false)
	@Column(name = "ACQUISITION_ITEM_START_DATE")
	public String acquisitionItemStartDate;

	/**
	 * 保存セットコード
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_SET_CODE")
	public String saveSetCode;

	/**
	 * 保存セット名称
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_SET_NAME")
	public String saveSetName;

	/**
	 * 保存ファイル名
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_FILE_NAME")
	public String saveFileName;

	/**
	 * 保存形態
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_FORM")
	public String saveForm;

	/**
	 * 保存日付From
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_DATE_FROM")
	public String saveDateFrom;

	/**
	 * 保存日付To
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_DATE_TO")
	public String saveDateTo;

	/**
	 * 保存時保存範囲
	 */
	@Basic(optional = false)
	@Column(name = "STORAGE_RANGE_SAVED")
	public String storageRangeSaved;

	/**
	 * 保存期間区分
	 */
	@Basic(optional = false)
	@Column(name = "RETENTION_PERIODCLS")
	public String retentionPeriodCls;

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
	public String anotherComCls;

	/**
	 * 参照年
	 */
	@Basic(optional = false)
	@Column(name = "REFERENCE_YEAR")
	public String referenceYear;

	/**
	 * 参照月
	 */
	@Basic(optional = false)
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
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_1")
	public String childSideKey1;

	/**
	 * 子側結合キー2
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_2")
	public String childSideKey2;

	/**
	 * 子側結合キー3
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_3")
	public String childSideKey3;

	/**
	 * 子側結合キー4
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_4")
	public String childSideKey4;

	/**
	 * 子側結合キー5
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_5")
	public String childSideKey5;

	/**
	 * 子側結合キー6
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_6")
	public String childSideKey6;

	/**
	 * 子側結合キー7
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_7")
	public String childSideKey7;
	/**
	 * 子側結合キー8
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_8")
	public String childSideKey8;

	/**
	 * 子側結合キー9
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_9")
	public String childSideKey9;

	/**
	 * 子側結合キー10
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_SIDE_KEY_10")
	public String childSideKey10;

	/**
	 * 履歴区分
	 */
	@Basic(optional = false)
	@Column(name = "HISTORY_CLS")
	public int historyCls;

	/**
	 * 復旧対象可不可
	 */
	@Basic(optional = false)
	@Column(name = "CAN_NOT_BE_OLD")
	public String canNotBeOld;

	/**
	 * 復旧対象選択
	 */
	@Basic(optional = false)
	@Column(name = "SELECTION_TARGET_FORRES")
	public String selectionTargetForRes;

	/**
	 * 抽出キー区分1
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_1")
	public String extractKeyCls1;

	/**
	 * 抽出キー区分2
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_2")
	public String extractKeyCls2;

	/**
	 * 抽出キー区分3
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_3")
	public String extractKeyCls3;

	/**
	 * 抽出キー区分4
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_4")
	public String extractKeyCls4;

	/**
	 * 抽出キー区分5
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_5")
	public String extractKeyCls5;

	/**
	 * 抽出キー区分6
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_6")
	public String extractKeyCls6;

	/**
	 * 抽出キー区分7
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_7")
	public String extractKeyCls7;

	/**
	 * 抽出キー区分8
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_8")
	public String extractKeyCls8;

	/**
	 * 抽出キー区分9
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_9")
	public String extractKeyCls9;

	/**
	 * 抽出キー区分10
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_CLS_10")
	public String extractKeyCls10;

	/**
	 * 抽出キー項目1
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_1")
	public String extractKeyItem1;

	/**
	 * 抽出キー項目2
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_2")
	public String extractKeyItem2;

	/**
	 * 抽出キー項目3
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_3")
	public String extractKeyItem3;

	/**
	 * 抽出キー項目4
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_4")
	public String extractKeyItem4;

	/**
	 * 抽出キー項目
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_5")
	public String extractKeyItem5;

	/**
	 * 抽出キー項目6
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_6")
	public String extractKeyItem6;

	/**
	 * 抽出キー項目7
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_7")
	public String extractKeyItem7;

	/**
	 * 抽出キー項目8
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_8")
	public String extractKeyItem8;

	/**
	 * 抽出キー項目9
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_9")
	public String extractKeyItem9;

	/**
	 * 抽出キー項目10
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACTKEY_ITEM_10")
	public String extractKeyItem10;

	/**
	 * 抽出条件キー固定
	 */
	@Basic(optional = false)
	@Column(name = "EXTRACT_COND_KEY_FIX")
	public String extractCondKeyFix;

	/**
	 * 日付項目1
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_1")
	public String dateItem1;

	/**
	 * 日付項目2
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_2")
	public String dateItem2;

	/**
	 * 日付項目3
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_3")
	public String dateItem3;

	/**
	 * 日付項目4
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_4")
	public String dateItem4;

	/**
	 * 日付項目5
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_5")
	public String dateItem5;

	/**
	 * 日付項目6
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_6")
	public String dateItem6;

	/**
	 * 日付項目7
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_7")
	public String dateItem7;

	/**
	 * 日付項目8
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_8")
	public String dateItem8;

	/**
	 * 日付項目9
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_9")
	public String dateItem9;

	/**
	 * 日付項目10
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_10")
	public String dateItem10;

	/**
	 * 日付項目11
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_11")
	public String dateItem11;

	/**
	 * 日付項目12
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_12")
	public String dateItem12;

	/**
	 * 日付項目13
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_13")
	public String dateItem13;

	/**
	 * 日付項目14
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_14")
	public String dateItem14;

	/**
	 * 日付項目15
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_15")
	public String dateItem15;

	/**
	 * 日付項目16
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_16")
	public String dateItem16;

	/**
	 * 日付項目17
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_17")
	public String dateItem17;

	/**
	 * 日付項目18
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_18")
	public String dateItem18;

	/**
	 * 日付項目19
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_19")
	public String dateItem19;

	/**
	 * 日付項目20
	 */
	@Basic(optional = false)
	@Column(name = "DATE_ITEM_20")
	public String dateItem20;

	/**
	 * 更新キー項目1
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_1")
	public String updateKeyItem1;

	/**
	 * 更新キー項目2
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_2")
	public String updateKeyItem2;

	/**
	 * 更新キー項目3
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_3")
	public String updateKeyItem3;

	/**
	 * 更新キー項目4
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_4")
	public String updateKeyItem4;

	/**
	 * 更新キー項目5
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_5")
	public String updateKeyItem5;

	/**
	 * 更新キー項目6
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_6")
	public String updateKeyItem6;

	/**
	 * 更新キー項目7
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_7")
	public String updateKeyItem7;

	/**
	 * 更新キー項目8
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_8")
	public String updateKeyItem8;

	/**
	 * 更新キー項目9
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_9")
	public String updateKeyItem9;

	/**
	 * 更新キー項目10
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_10")
	public String updateKeyItem10;

	/**
	 * 更新キー項目11
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_11")
	public String updateKeyItem11;

	/**
	 * 更新キー項目12
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_12")
	public String updateKeyItem12;

	/**
	 * 更新キー項目13
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_13")
	public String updateKeyItem13;

	/**
	 * 更新キー項目14
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_14")
	public String updateKeyItem14;

	/**
	 * 更新キー項目15
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_15")
	public String updateKeyItem15;

	/**
	 * 更新キー項目16
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_16")
	public String updateKeyItem16;

	/**
	 * 更新キー項目17
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_17")
	public String updateKeyItem17;

	/**
	 * 更新キー項目18
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_18")
	public String updateKeyItem18;

	/**
	 * 更新キー項目19
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_19")
	public String updateKeyItem19;

	/**
	 * 更新キー項目20
	 */
	@Basic(optional = false)
	@Column(name = "UPDATE_KEY_ITEM_20")
	public String updateKeyItem20;

	/**
	 * 画面保存期間
	 */
	@Basic(optional = false)
	@Column(name = "SCREEN_RETENTION_PERIOD")
	public String screenRetentionPeriod;

	/**
	 * 補足説明
	 */
	@Basic(optional = false)
	@Column(name = "SUPPLEMENTARY_EXPLANATION")
	public String supplementaryExplanation;

	/**
	 * 親テーブル日本語名
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_TABLE_JAPANE_NAME")
	public String parentTableJapaneName;

	/**
	 * 親テーブル有無
	 */
	@Basic(optional = false)
	@Column(name = "WITH_PARENT_TABLE")
	public String withParentTable;

	/**
	 * 親テーブル物理名
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_TABLE_PHYSICAL_NAME")
	public String parentTablePhysicalName;

	/**
	 * 親側結合キー1
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_1")
	public String parentSidedKey1;

	/**
	 * 親側結合キー2
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_2")
	public String parentSidedKey2;

	/**
	 * 親側結合キー3
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_3")
	public String parentSidedKey3;

	/**
	 * 親側結合キー4
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_4")
	public String parentSidedKey4;

	/**
	 * 親側結合キー5
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_5")
	public String parentSidedKey5;

	/**
	 * 親側結合キー6
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_6")
	public String parentSidedKey6;

	/**
	 * 親側結合キー7
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_7")
	public String parentSidedKey7;

	/**
	 * 親側結合キー8
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_8")
	public String parentSidedKey8;

	/**
	 * 親側結合キー9
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_9")
	public String parentSidedKey9;

	/**
	 * 親側結合キー10
	 */
	@Basic(optional = false)
	@Column(name = "PARENT_SIDED_KEY_10")
	public String parentSidedKey10;

	/**
	 * 調査用保存
	 */
	@Basic(optional = false)
	@Column(name = "SURVEY_PRESERVATION")
	public String surveyPreservation;

	@Override
	protected Object getKey() {
		return tableListPk;
	}

	public TableList toDomain() {
		return new TableList(tableListPk.categoryId, categoryName,
				dataStorageProcessingId, dataRecoveryProcessId,
				tableListPk.tableNo, tableJapaneseName, tablePhysicalName,
				acquisitionItemCompanyId, acquisitionItemDate, acquisitionItemEmployeeId, acquisitionItemEndDate, acquisitionItemStartDate,
				saveSetCode, saveSetName, saveFileName, saveForm, saveDateFrom, saveDateTo,
				storageRangeSaved,
				retentionPeriodCls, internalFileName, anotherComCls,
				referenceYear, referenceMonth, compressedFileName,
				childSideKey1, childSideKey2, childSideKey3, childSideKey4, childSideKey5, childSideKey6, childSideKey7,
				childSideKey8, childSideKey9, childSideKey10,
				EnumAdaptor.valueOf(this.historyCls, HistoryDiviSion.class), canNotBeOld, selectionTargetForRes,
				extractKeyCls1, extractKeyCls2, extractKeyCls3, extractKeyCls4, extractKeyCls5, extractKeyCls6,
				extractKeyCls7, extractKeyCls8, extractKeyCls9, extractKeyCls10,
				extractKeyItem1, extractKeyItem2, extractKeyItem3, extractKeyItem4, extractKeyItem5, extractKeyItem6, extractKeyItem7, extractKeyItem8,
				extractKeyItem9, extractKeyItem10,
				extractCondKeyFix,
				dateItem1, dateItem2, dateItem3, dateItem4,dateItem5, dateItem6, dateItem7, dateItem8,
				dateItem9, dateItem10, dateItem11, dateItem12, dateItem13,
				dateItem14, dateItem15, dateItem16, dateItem17, dateItem18, dateItem19, dateItem20,
				updateKeyItem1, updateKeyItem2, updateKeyItem3, updateKeyItem4, updateKeyItem5, updateKeyItem6, updateKeyItem7,
				updateKeyItem8, updateKeyItem9, updateKeyItem10, updateKeyItem11, updateKeyItem12, updateKeyItem13,
				updateKeyItem14, updateKeyItem15, updateKeyItem16, updateKeyItem17, updateKeyItem18, updateKeyItem19, updateKeyItem20,
				screenRetentionPeriod, supplementaryExplanation,
				parentTableJapaneName, withParentTable, parentTablePhysicalName, 
				parentSidedKey1, parentSidedKey2, parentSidedKey3, parentSidedKey4, parentSidedKey5,
				parentSidedKey6, parentSidedKey7, parentSidedKey8, parentSidedKey9,parentSidedKey10,
				surveyPreservation);
	}

	public static SspmtTableList toEntity(TableList domain) {
		return new SspmtTableList(new SspmtTableListPk(domain.getCategoryId(), domain.getTableNo()),
				domain.getCategoryName(), domain.getDataStorageProcessingId(), domain.getDataRecoveryProcessId(),
				domain.getTableJapaneseName(), domain.getParentTablePhysicalName(),
				domain.getAcquisitionItemCompanyId(), domain.getAcquisitionItemDate(),
				domain.getAcquisitionItemEmployeeId(), domain.getAcquisitionItemEndDate(),
				domain.getAcquisitionItemStartDate(), domain.getSaveSetCode(), domain.getSaveSetName(),
				domain.getSaveFileName(), domain.getSaveForm(), domain.getSaveDateFrom(), domain.getSaveDateTo(),
				domain.getStorageRangeSaved(), domain.getRetentionPeriodCls(), domain.getInternalFileName(),
				domain.getAnotherComCls(), domain.getReferenceYear(), domain.getReferenceMonth(),
				domain.getCompressedFileName(), domain.getChildSideKey1(), domain.getChildSideKey2(),
				domain.getChildSideKey3(), domain.getChildSideKey4(), domain.getChildSideKey5(),
				domain.getChildSideKey6(), domain.getChildSideKey7(), domain.getChildSideKey8(),
				domain.getChildSideKey9(), domain.getChildSideKey10(), domain.getHistoryCls().value, domain.getCanNotBeOld(),
				domain.getSelectionTargetForRes(), domain.getExtractKeyCls1(), domain.getExtractKeyCls2(),
				domain.getExtractKeyCls3(), domain.getExtractKeyCls4(), domain.getExtractKeyCls5(),
				domain.getExtractKeyCls6(), domain.getExtractKeyCls7(), domain.getExtractKeyCls8(),
				domain.getExtractKeyCls9(), domain.getExtractKeyCls10(), domain.getExtractKeyItem1(),
				domain.getExtractKeyItem2(), domain.getExtractKeyItem3(), domain.getExtractKeyItem4(),
				domain.getExtractKeyItem5(), domain.getExtractKeyItem6(), domain.getExtractKeyItem7(),
				domain.getExtractKeyItem8(), domain.getExtractKeyItem9(), domain.getExtractKeyItem10(),
				domain.getExtractCondKeyFix(), domain.getDateItem1(), domain.getDateItem2(), domain.getDateItem3(),
				domain.getDateItem4(), domain.getDateItem5(), domain.getDateItem6(), domain.getDateItem7(),
				domain.getDateItem8(), domain.getDateItem9(), domain.getDateItem10(), domain.getDateItem11(),
				domain.getDateItem12(), domain.getDateItem13(), domain.getDateItem14(), domain.getDateItem15(),
				domain.getDateItem16(), domain.getDateItem17(), domain.getDateItem18(), domain.getDateItem19(),
				domain.getDateItem20(), domain.getUpdateKeyItem1(), domain.getUpdateKeyItem2(),
				domain.getUpdateKeyItem3(), domain.getUpdateKeyItem4(), domain.getUpdateKeyItem5(),
				domain.getUpdateKeyItem6(), domain.getUpdateKeyItem7(), domain.getUpdateKeyItem8(),
				domain.getUpdateKeyItem9(), domain.getUpdateKeyItem10(), domain.getUpdateKeyItem11(),
				domain.getUpdateKeyItem12(), domain.getUpdateKeyItem13(), domain.getUpdateKeyItem14(),
				domain.getUpdateKeyItem15(), domain.getUpdateKeyItem16(), domain.getUpdateKeyItem17(),
				domain.getUpdateKeyItem18(), domain.getUpdateKeyItem19(), domain.getUpdateKeyItem20(),
				domain.getScreenRetentionPeriod(), domain.getSupplementaryExplanation(),
				domain.getParentTableJapaneName(), domain.getWithParentTable(), domain.getParentTablePhysicalName(),
				domain.getParentSidedKey1(), domain.getParentSidedKey2(), domain.getParentSidedKey3(),
				domain.getParentSidedKey4(), domain.getParentSidedKey5(), domain.getParentSidedKey6(),
				domain.getParentSidedKey7(), domain.getParentSidedKey8(), domain.getParentSidedKey9(),
				domain.getParentSidedKey10(), domain.getSurveyPreservation());
	}

}
