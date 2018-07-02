package nts.uk.ctx.sys.assist.dom.recoverystorage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.category.StorageRangeSaved;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.HistoryDiviSion;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataReEmployeeAdapter;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryOperatingCondition;
import nts.uk.ctx.sys.assist.dom.datarestoration.EmployeeDataReInfoImport;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.RecoveryMethod;
import nts.uk.ctx.sys.assist.dom.datarestoration.Target;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.CsvFileUtil;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RecoveryStorageService {
	@Resource
	private SessionContext scContext;

	@Inject
	private PerformDataRecoveryRepository performDataRecoveryRepository;

	@Inject
	private CategoryRepository categoryRepository;

	@Inject
	private DataRecoveryMngRepository dataRecoveryMngRepository;

	@Inject
	private DataReEmployeeAdapter empDataMngRepo;

	private RecoveryStorageService self;

	@PostConstruct
	public void init() {
		this.self = scContext.getBusinessObject(RecoveryStorageService.class);
	}

	public static final int SELECTION_TARGET_FOR_RES = 1;

	public static final String SQL_EXCEPTION = "113";

	public static final String SETTING_EXCEPTION = "5";

	public static final Integer HEADER_CSV = 0;

	public static final Integer INDEX_SID = 1;

	private static final Logger LOGGER = LoggerFactory.getLogger(RecoveryStorageService.class);

	public void recoveryStorage(String dataRecoveryProcessId) throws ParseException {

		int errorCode = 0;
		Optional<PerformDataRecovery> performRecoveries = performDataRecoveryRepository
				.getPerformDatRecoverById(dataRecoveryProcessId);
		String uploadId = performRecoveries.get().getUploadfileId();
		List<Category> listCategory = categoryRepository.findById(dataRecoveryProcessId, SELECTION_TARGET_FOR_RES);

		// update OperatingCondition
		dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,
				DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS.value);

		// 処理対象のカテゴリを処理する
		int index = 0;
		for (int i = 0; i < listCategory.size(); i++) {

			List<TableList> tableUse = performDataRecoveryRepository.getByStorageRangeSaved(
					listCategory.get(i).getCategoryId().v(), dataRecoveryProcessId, StorageRangeSaved.EARCH_EMP.value);
			List<TableList> tableNotUse = performDataRecoveryRepository.getByStorageRangeSaved(
					listCategory.get(i).getCategoryId().v(), dataRecoveryProcessId, StorageRangeSaved.ALL_EMP.value);

			TableListByCategory tableListByCategory = new TableListByCategory(listCategory.get(i).getCategoryId().v(),
					tableUse);
			TableListByCategory tableNotUseCategory = new TableListByCategory(listCategory.get(i).getCategoryId().v(),
					tableNotUse);

			// カテゴリ単位の復旧
			errorCode = exCurrentCategory(tableListByCategory, tableNotUseCategory, uploadId, dataRecoveryProcessId);

			// のカテゴリカウントをカウントアップ

			if (errorCode == DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS.value) {
				index++;
				dataRecoveryMngRepository.updateCategoryCnt(dataRecoveryProcessId, index);
			} else {
				break;
			}

		}
		if(errorCode == DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS.value) {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,DataRecoveryOperatingCondition.DONE.value);
		} else {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, errorCode);
		}
		
		
	}

	@Transactional(value = TxType.REQUIRES_NEW, rollbackOn = { Exception.class })
	public int recoveryDataByEmployee(String dataRecoveryProcessId, String employeeCode, String employeeId,
			List<DataRecoveryTable> targetDataByCate) throws Exception {

		int errorCode = 0;
		Optional<PerformDataRecovery> performDataRecovery = performDataRecoveryRepository
				.getPerformDatRecoverById(dataRecoveryProcessId);

		// Xác định phương pháp phục hồi [復旧方法]
		if (performDataRecovery.isPresent()
				&& performDataRecovery.get().getRecoveryMethod().value == RecoveryMethod.RESTORE_SELECTED_RANGE.value) {
			// check employeeId in Target of PreformDataRecovery
			List<Target> listTarget = performDataRecoveryRepository.findByDataRecoveryId(dataRecoveryProcessId);
			Optional<Target> isExist = listTarget.stream().filter(x -> {
				return employeeId.equals(x.getSid());
			}).findFirst();
			if (!isExist.isPresent()) {
				errorCode = DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS.value;
				return errorCode;
			}

		}

		// Data current đối tượng [カレント対象データ]
		for (DataRecoveryTable dataRecoveryTable : targetDataByCate) {

			Optional<TableList> tableList = performDataRecoveryRepository
					.getByInternal(dataRecoveryTable.getFileNameCsv(), dataRecoveryProcessId);
			// check date [日付処理の設定]

			List<String> resultsSetting = new ArrayList<>();
			resultsSetting = this.settingDate(tableList);
			if (resultsSetting.isEmpty()) {
				errorCode = DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value;
				return errorCode;
			}

			// 履歴区分の判別する - check phân loại lịch sử
			if (tableList.isPresent() && tableList.get().getHistoryCls().value == HistoryDiviSion.HAVE_HISTORY.value) {
				try {
					deleteEmployeeHistory(tableList, employeeId, true);
				} catch (Exception e) {
					LOGGER.error("SQL error rollBack transaction");
					throw new Exception(SQL_EXCEPTION);
				}
			}
			
			try {
				// 対象社員の日付順の処理
				errorCode = crudDataByTable(dataRecoveryTable.getDataRecovery(), employeeId, employeeCode,
						dataRecoveryProcessId, dataRecoveryTable.getFileNameCsv(), tableList, performDataRecovery,
						resultsSetting, true);
			} catch (Exception e) {
				LOGGER.error("SQL error rollBack transaction");
				throw new Exception(SQL_EXCEPTION);

			}

			// define DELETE/INSERT error and Setting error
			if (errorCode == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value) {
				LOGGER.error("Setting error rollBack transaction");
				throw new Exception(SETTING_EXCEPTION);

			}

		}
		return errorCode;
	}

	public int crudDataByTable(List<List<String>> targetDataTable, String employeeId, String employeeCode,
			String dataRecoveryProcessId, String fileNameCsv, Optional<TableList> tableList,
			Optional<PerformDataRecovery> performDataRecovery, List<String> resultsSetting, Boolean tableUse)
			throws ParseException {
		
		int errorCode = 0;
		List<String> targetDataHeader = targetDataTable.get(HEADER_CSV);
		Integer indexUpdate1 = null, indexUpdate2 = null,indexUpdate3 = null,indexUpdate4 = null,indexUpdate5 = null,indexUpdate6 = null;
		Integer indexUpdate7 = null, indexUpdate8 = null, indexUpdate9 = null, indexUpdate10 = null, indexUpdate11 = null, indexUpdate12 = null;
		Integer indexUpdate13 = null, indexUpdate14 = null, indexUpdate15 = null, indexUpdate16 = null, indexUpdate17 = null, indexUpdate18 = null, indexUpdate19 = null, indexUpdate20 = null;
		String cidTable = null;
		String FILED_KEY_UPDATE_1 = null, FILED_KEY_UPDATE_2 = null, FILED_KEY_UPDATE_3 = null, FILED_KEY_UPDATE_4 = null, FILED_KEY_UPDATE_5 = null, FILED_KEY_UPDATE_6 = null;
		String FILED_KEY_UPDATE_7 = null, FILED_KEY_UPDATE_8 = null, FILED_KEY_UPDATE_9 = null, FILED_KEY_UPDATE_10 = null, FILED_KEY_UPDATE_11 = null, FILED_KEY_UPDATE_12 = null;
		String FILED_KEY_UPDATE_13 = null, FILED_KEY_UPDATE_14 = null, FILED_KEY_UPDATE_15 = null, FILED_KEY_UPDATE_16 = null, FILED_KEY_UPDATE_17 = null, FILED_KEY_UPDATE_18 = null, FILED_KEY_UPDATE_19 = null, FILED_KEY_UPDATE_20 = null;
		String V_FILED_KEY_UPDATE_1 = null, V_FILED_KEY_UPDATE_2 = null, V_FILED_KEY_UPDATE_3 = null, V_FILED_KEY_UPDATE_4 = null, V_FILED_KEY_UPDATE_5 = null;
		String V_FILED_KEY_UPDATE_6 = null, V_FILED_KEY_UPDATE_7 = null, V_FILED_KEY_UPDATE_8 = null, V_FILED_KEY_UPDATE_9 = null, V_FILED_KEY_UPDATE_10 = null;
		String V_FILED_KEY_UPDATE_11 = null, V_FILED_KEY_UPDATE_12 = null, V_FILED_KEY_UPDATE_13 = null, V_FILED_KEY_UPDATE_14 = null, V_FILED_KEY_UPDATE_15 = null;
		String V_FILED_KEY_UPDATE_16 = null, V_FILED_KEY_UPDATE_17 = null, V_FILED_KEY_UPDATE_18 = null, V_FILED_KEY_UPDATE_19 = null, V_FILED_KEY_UPDATE_20 = null;
		String TABLE_NAME = null;

		// search data by employee

		if (tableList.isPresent()) {

			TABLE_NAME = tableList.get().getTableEnglishName();
			FILED_KEY_UPDATE_1 = tableList.get().getFiledKeyUpdate1().get();

			if (FILED_KEY_UPDATE_1 != null && !FILED_KEY_UPDATE_1.isEmpty()) {
				indexUpdate1 = targetDataHeader.indexOf(FILED_KEY_UPDATE_1);
			}
			FILED_KEY_UPDATE_2 = tableList.get().getFiledKeyUpdate2().get();
			if (FILED_KEY_UPDATE_2 != null && !FILED_KEY_UPDATE_2.isEmpty()) {
				indexUpdate2 = targetDataHeader.indexOf(FILED_KEY_UPDATE_2);
			}
			FILED_KEY_UPDATE_3 = tableList.get().getFiledKeyUpdate3().get();
			if (FILED_KEY_UPDATE_3 != null && !FILED_KEY_UPDATE_3.isEmpty()) {
				indexUpdate3 = targetDataHeader.indexOf(FILED_KEY_UPDATE_3);
			}

			FILED_KEY_UPDATE_4 = tableList.get().getFiledKeyUpdate4().get();
			if (FILED_KEY_UPDATE_4 != null && !FILED_KEY_UPDATE_4.isEmpty()) {
				indexUpdate4 = targetDataHeader.indexOf(FILED_KEY_UPDATE_4);
			}

			FILED_KEY_UPDATE_5 = tableList.get().getFiledKeyUpdate5().get();
			if (FILED_KEY_UPDATE_5 != null && !FILED_KEY_UPDATE_5.isEmpty()) {
				indexUpdate5 = targetDataHeader.indexOf(FILED_KEY_UPDATE_5);
			}

			FILED_KEY_UPDATE_6 = tableList.get().getFiledKeyUpdate6().get();
			if (FILED_KEY_UPDATE_6 != null && !FILED_KEY_UPDATE_6.isEmpty()) {
				indexUpdate6 = targetDataHeader.indexOf(FILED_KEY_UPDATE_6);
			}

			FILED_KEY_UPDATE_7 = tableList.get().getFiledKeyUpdate7().get();
			if (FILED_KEY_UPDATE_7 != null && !FILED_KEY_UPDATE_7.isEmpty()) {
				indexUpdate7 = targetDataHeader.indexOf(FILED_KEY_UPDATE_7);
			}

			FILED_KEY_UPDATE_8 = tableList.get().getFiledKeyUpdate8().get();
			if (FILED_KEY_UPDATE_8 != null && !FILED_KEY_UPDATE_8.isEmpty()) {
				indexUpdate8 = targetDataHeader.indexOf(FILED_KEY_UPDATE_8);
			}

			FILED_KEY_UPDATE_9 = tableList.get().getFiledKeyUpdate9().get();
			if (FILED_KEY_UPDATE_9 != null && !FILED_KEY_UPDATE_9.isEmpty()) {
				indexUpdate9 = targetDataHeader.indexOf(FILED_KEY_UPDATE_9);
			}

			FILED_KEY_UPDATE_10 = tableList.get().getFiledKeyUpdate10().get();
			if (FILED_KEY_UPDATE_10 != null && !FILED_KEY_UPDATE_10.isEmpty()) {
				indexUpdate10 = targetDataHeader.indexOf(FILED_KEY_UPDATE_10);
			}

			FILED_KEY_UPDATE_11 = tableList.get().getFiledKeyUpdate11().get();
			if (FILED_KEY_UPDATE_11 != null && !FILED_KEY_UPDATE_11.isEmpty()) {
				indexUpdate11 = targetDataHeader.indexOf(FILED_KEY_UPDATE_11);
			}

			FILED_KEY_UPDATE_12 = tableList.get().getFiledKeyUpdate12().get();
			if (FILED_KEY_UPDATE_12 != null && !FILED_KEY_UPDATE_12.isEmpty()) {
				indexUpdate12 = targetDataHeader.indexOf(FILED_KEY_UPDATE_12);
			}

			FILED_KEY_UPDATE_13 = tableList.get().getFiledKeyUpdate13().get();
			if (FILED_KEY_UPDATE_13 != null && !FILED_KEY_UPDATE_13.isEmpty()) {
				indexUpdate13 = targetDataHeader.indexOf(FILED_KEY_UPDATE_13);
			}

			FILED_KEY_UPDATE_14 = tableList.get().getFiledKeyUpdate14().get();
			if (FILED_KEY_UPDATE_14 != null && !FILED_KEY_UPDATE_14.isEmpty())
				indexUpdate14 = targetDataHeader.indexOf(FILED_KEY_UPDATE_14);
			FILED_KEY_UPDATE_15 = tableList.get().getFiledKeyUpdate15().get();
			if (FILED_KEY_UPDATE_15 != null && !FILED_KEY_UPDATE_15.isEmpty())
				indexUpdate15 = targetDataHeader.indexOf(FILED_KEY_UPDATE_15);
			FILED_KEY_UPDATE_16 = tableList.get().getFiledKeyUpdate16().get();
			if (FILED_KEY_UPDATE_16 != null && !FILED_KEY_UPDATE_16.isEmpty())
				indexUpdate16 = targetDataHeader.indexOf(FILED_KEY_UPDATE_16);
			FILED_KEY_UPDATE_17 = tableList.get().getFiledKeyUpdate17().get();
			if (FILED_KEY_UPDATE_17 != null && !FILED_KEY_UPDATE_17.isEmpty())
				indexUpdate17 = targetDataHeader.indexOf(FILED_KEY_UPDATE_17);
			FILED_KEY_UPDATE_18 = tableList.get().getFiledKeyUpdate18().get();
			if (FILED_KEY_UPDATE_18 != null && !FILED_KEY_UPDATE_18.isEmpty())
				indexUpdate18 = targetDataHeader.indexOf(FILED_KEY_UPDATE_18);
			FILED_KEY_UPDATE_19 = tableList.get().getFiledKeyUpdate19().get();
			if (FILED_KEY_UPDATE_19 != null && !FILED_KEY_UPDATE_19.isEmpty())
				indexUpdate19 = targetDataHeader.indexOf(FILED_KEY_UPDATE_19);
			FILED_KEY_UPDATE_20 = tableList.get().getFiledKeyUpdate20().get();
			if (FILED_KEY_UPDATE_20 != null && !FILED_KEY_UPDATE_20.isEmpty())
				indexUpdate20 = targetDataHeader.indexOf(FILED_KEY_UPDATE_20);
		}
		List<List<String>> dataTableCus = new ArrayList<>();
		for (List<String> list : targetDataTable) {
			dataTableCus.add(list);
		}
		dataTableCus.remove(0);
		Collections.sort(dataTableCus, new Comparator<List<String>>() {
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");// or your
			@Override
			public int compare(List<String> o1, List<String> o2) {
				try {
					if (o1.get(2) == null || o1.get(2).isEmpty()) {
						return (o2.get(2) == null) ? 0 : -1;
					}
					if (o2.get(2) == null || o2.get(2).isEmpty()) {
						return 1;
					}
					return f.parse(o1.get(2)).compareTo(f.parse(o2.get(2)));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		
		for (int i = 0; i < dataTableCus.size(); i++) {
			
			Map<String, String> filedWhere = new HashMap<>();
			List<String> dataRow = dataTableCus.get(i);
			// データベース復旧処理
			if ((employeeId == null) || (employeeId != null && dataRow.get(INDEX_SID).equals(employeeId))) {
				// 履歴区分を判別する - Phân loại lịch sử
				if ((tableList.get().getHistoryCls().value == HistoryDiviSion.NO_HISTORY.value && tableUse) || !tableUse) {

					// 復旧方法 - Phương pháp phục hồi
					if (performDataRecovery.isPresent() && performDataRecovery.get().getRecoveryMethod().value == RecoveryMethod.RESTORE_SELECTED_RANGE.value) {

						// 保存期間区分を判別 - Phân loại khoảng thời gian save
						if (tableList.get().getRetentionPeriodCls().value != TimeStore.FULL_TIME.value) {
							if (!checkSettingDate(resultsSetting, tableList, dataRow)) {
								if(!tableUse) {
									errorCode = DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value;
									return errorCode;
								} else {
									continue;
								}
								
							}
						} else {

							// update date phục hồi Domain
							dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, null);

						}
					}

				} else {

					// update date phục hồi Domain
					String date = dataRow.get(2);
					if (date != null && !date.isEmpty())
						dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, date.substring(0, 10));
				}
				
				// update date phục hồi Domain
				String date = null;
				if(resultsSetting.size() == 1) {
					date = dataRow.get(2);
				} else if(resultsSetting.size() == 2){
					date = dataRow.get(3);
				}
				if (resultsSetting.get(0).equals("6")) {
					if (date != null && !date.isEmpty())
						dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, date.substring(0, 3));
				} else if (resultsSetting.get(0).equals("7")) {
					if (date != null && !date.isEmpty())
						dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, date.substring(0, 6));
				} else if (resultsSetting.get(0).equals("8")) {
					if (date != null && !date.isEmpty())
						dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, date.substring(0, 10));
				}
				
				if (indexUpdate1 != null) {
					V_FILED_KEY_UPDATE_1 = dataRow.get(indexUpdate1);
					filedWhere.put(FILED_KEY_UPDATE_1, V_FILED_KEY_UPDATE_1);
				}

				if (indexUpdate2 != null) {
					V_FILED_KEY_UPDATE_2 = dataRow.get(indexUpdate2);
					filedWhere.put(FILED_KEY_UPDATE_2, V_FILED_KEY_UPDATE_2);
				}

				if (indexUpdate3 != null) {
					V_FILED_KEY_UPDATE_3 = dataRow.get(indexUpdate3);
					filedWhere.put(FILED_KEY_UPDATE_3, V_FILED_KEY_UPDATE_3);
				}

				if (indexUpdate4 != null) {
					V_FILED_KEY_UPDATE_4 = dataRow.get(indexUpdate4);
					filedWhere.put(FILED_KEY_UPDATE_4, V_FILED_KEY_UPDATE_4);
				}

				if (indexUpdate5 != null) {
					V_FILED_KEY_UPDATE_5 = dataRow.get(indexUpdate5);
					filedWhere.put(FILED_KEY_UPDATE_5, V_FILED_KEY_UPDATE_5);
				}

				if (indexUpdate6 != null) {
					V_FILED_KEY_UPDATE_6 = dataRow.get(indexUpdate6);
					filedWhere.put(FILED_KEY_UPDATE_6, V_FILED_KEY_UPDATE_6);
				}

				if (indexUpdate7 != null) {
					V_FILED_KEY_UPDATE_7 = dataRow.get(indexUpdate7);
					filedWhere.put(FILED_KEY_UPDATE_7, V_FILED_KEY_UPDATE_7);
				}

				if (indexUpdate8 != null) {
					V_FILED_KEY_UPDATE_8 = dataRow.get(indexUpdate8);
					filedWhere.put(FILED_KEY_UPDATE_8, V_FILED_KEY_UPDATE_8);
				}

				if (indexUpdate9 != null) {
					V_FILED_KEY_UPDATE_9 = dataRow.get(indexUpdate9);
					filedWhere.put(FILED_KEY_UPDATE_9, V_FILED_KEY_UPDATE_9);
				}

				if (indexUpdate10 != null) {
					V_FILED_KEY_UPDATE_10 = dataRow.get(indexUpdate10);
					filedWhere.put(FILED_KEY_UPDATE_10, V_FILED_KEY_UPDATE_10);
				}

				if (indexUpdate11 != null) {
					V_FILED_KEY_UPDATE_11 = dataRow.get(indexUpdate11);
					filedWhere.put(FILED_KEY_UPDATE_11, V_FILED_KEY_UPDATE_11);
				}

				if (indexUpdate12 != null) {
					V_FILED_KEY_UPDATE_12 = dataRow.get(indexUpdate12);
					filedWhere.put(FILED_KEY_UPDATE_12, V_FILED_KEY_UPDATE_12);
				}

				if (indexUpdate13 != null) {
					V_FILED_KEY_UPDATE_13 = dataRow.get(indexUpdate13);
					filedWhere.put(FILED_KEY_UPDATE_13, V_FILED_KEY_UPDATE_13);
				}

				if (indexUpdate14 != null) {
					V_FILED_KEY_UPDATE_14 = dataRow.get(indexUpdate14);
					filedWhere.put(FILED_KEY_UPDATE_14, V_FILED_KEY_UPDATE_14);
				}

				if (indexUpdate15 != null) {
					V_FILED_KEY_UPDATE_15 = dataRow.get(indexUpdate15);
					filedWhere.put(FILED_KEY_UPDATE_15, V_FILED_KEY_UPDATE_15);
				}

				if (indexUpdate16 != null) {
					V_FILED_KEY_UPDATE_16 = dataRow.get(indexUpdate16);
					filedWhere.put(FILED_KEY_UPDATE_16, V_FILED_KEY_UPDATE_16);
				}

				if (indexUpdate17 != null) {
					V_FILED_KEY_UPDATE_17 = dataRow.get(indexUpdate17);
					filedWhere.put(FILED_KEY_UPDATE_17, V_FILED_KEY_UPDATE_17);
				}

				if (indexUpdate18 != null) {
					V_FILED_KEY_UPDATE_18 = dataRow.get(indexUpdate18);
					filedWhere.put(FILED_KEY_UPDATE_18, V_FILED_KEY_UPDATE_18);
				}

				if (indexUpdate19 != null) {
					V_FILED_KEY_UPDATE_19 = dataRow.get(indexUpdate19);
					filedWhere.put(FILED_KEY_UPDATE_19, V_FILED_KEY_UPDATE_19);
				}

				if (indexUpdate20 != null) {
					V_FILED_KEY_UPDATE_20 = dataRow.get(indexUpdate20);
					filedWhere.put(FILED_KEY_UPDATE_20, V_FILED_KEY_UPDATE_20);
				}

				// 対象データの会社IDをパラメータの会社IDに入れ替える - Thay thế CID
				cidTable = dataRow.get(0);
				String cidCurrent = AppContexts.user().companyId();
				if (!cidTable.equals(cidCurrent)) {
					cidCurrent = cidTable;
				}

				// 既存データの検索
				String namePhysicalCid = findNamePhysicalCid(tableList);
				int count = performDataRecoveryRepository.countDataExitTableByVKeyUp(filedWhere, TABLE_NAME,
						namePhysicalCid, cidCurrent);

				if (count == 2) {
					if (tableUse) {
						errorCode = DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value;
						return errorCode;
					} else {
						continue;
					}
				} else if (count == 1 && tableUse) {
					performDataRecoveryRepository.deleteDataExitTableByVkey(filedWhere, TABLE_NAME, namePhysicalCid,
							cidCurrent);

				} else if (count == 1 && !tableUse) {
					try {
						performDataRecoveryRepository.deleteDataExitTableByVkey(filedWhere, TABLE_NAME, namePhysicalCid,
								cidCurrent);
					} catch (Exception e) {
						LOGGER.info("Error delete data for table not use" + TABLE_NAME);
					}

				}

				int indexCidOfCsv = targetDataHeader.indexOf(namePhysicalCid);
				HashMap<String, String> dataInsertDb = new HashMap<>();
				for (int j = 5; j < dataRow.size(); j++) {
					if (j == indexCidOfCsv) {
						dataInsertDb.put(targetDataHeader.get(j), cidCurrent);
					} else {
						dataInsertDb.put(targetDataHeader.get(j), dataRow.get(j));
					}
				}
				// insert data
				if (tableUse) {
					performDataRecoveryRepository.insertDataTable(dataInsertDb, TABLE_NAME);
				} else {
					try {
						performDataRecoveryRepository.insertDataTable(dataInsertDb, TABLE_NAME);
					} catch (Exception e) {
						LOGGER.info("Error insert data for table not use" + TABLE_NAME);
					}

				}
			}
		}

		return errorCode;
	}

	public String findNamePhysicalCid(Optional<TableList> tableList) {

		String namePhysical = null;
		if (tableList.isPresent()) {

			if (tableList.get().getClsKeyQuery1().get() != null
					&& tableList.get().getClsKeyQuery1().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate1().get();
			} else if (tableList.get().getClsKeyQuery2().get() != null
					&& tableList.get().getClsKeyQuery2().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate2().get();
			} else if (tableList.get().getClsKeyQuery3().get() != null
					&& tableList.get().getClsKeyQuery3().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate3().get();
			} else if (tableList.get().getClsKeyQuery4().get() != null
					&& tableList.get().getClsKeyQuery4().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate4().get();
			} else if (tableList.get().getClsKeyQuery5().get() != null
					&& tableList.get().getClsKeyQuery5().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate5().get();
			} else if (tableList.get().getClsKeyQuery6().get() != null
					&& tableList.get().getClsKeyQuery6().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate6().get();
			} else if (tableList.get().getClsKeyQuery7().get() != null
					&& tableList.get().getClsKeyQuery7().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate7().get();
			} else if (tableList.get().getClsKeyQuery8().get() != null
					&& tableList.get().getClsKeyQuery8().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate8().get();
			} else if (tableList.get().getClsKeyQuery9().get() != null
					&& tableList.get().getClsKeyQuery9().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate9().get();
			} else if (tableList.get().getClsKeyQuery10().get() != null
					&& tableList.get().getClsKeyQuery10().get().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate10().get();
			}
		}
		return namePhysical;
	}

	public void deleteEmployeeHistory(Optional<TableList> tableList, String employeeId, Boolean tableNotUse) {
		// Delete history

		String whereCid = null;
		String whereSid = null;
		if (tableList.get().getClsKeyQuery1().get() != null && tableList.get().getClsKeyQuery1().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate1().get();
		} else if (tableList.get().getClsKeyQuery1().get() != null
				&& tableList.get().getClsKeyQuery1().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate1().get();
		}

		if (tableList.get().getClsKeyQuery2().get() != null && tableList.get().getClsKeyQuery2().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate2().get();
		} else if (tableList.get().getClsKeyQuery2().get() != null
				&& tableList.get().getClsKeyQuery2().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate2().get();
		}

		if (tableList.get().getClsKeyQuery3().get() != null && tableList.get().getClsKeyQuery3().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate3().get();
		} else if (tableList.get().getClsKeyQuery3().get() != null
				&& tableList.get().getClsKeyQuery3().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate3().get();
		}

		if (tableList.get().getClsKeyQuery4().get() != null && tableList.get().getClsKeyQuery4().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate4().get();
		} else if (tableList.get().getClsKeyQuery4().get() != null
				&& tableList.get().getClsKeyQuery4().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate4().get();
		}

		if (tableList.get().getClsKeyQuery5().get() != null && tableList.get().getClsKeyQuery5().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate5().get();
		} else if (tableList.get().getClsKeyQuery5().get() != null
				&& tableList.get().getClsKeyQuery5().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate5().get();
		}

		if (tableList.get().getClsKeyQuery6().get() != null && tableList.get().getClsKeyQuery6().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate6().get();
		} else if (tableList.get().getClsKeyQuery6().get() != null
				&& tableList.get().getClsKeyQuery6().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate6().get();
		}

		if (tableList.get().getClsKeyQuery7().get() != null && tableList.get().getClsKeyQuery7().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate7().get();
		} else if (tableList.get().getClsKeyQuery7().get() != null
				&& tableList.get().getClsKeyQuery7().get().equals("5") && !tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate7().get();
		}
		if (tableList.get().getClsKeyQuery8().get() != null && tableList.get().getClsKeyQuery8().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate8().get();
		} else if (tableList.get().getClsKeyQuery8().get() != null
				&& tableList.get().getClsKeyQuery8().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate8().get();
		}

		if (tableList.get().getClsKeyQuery9().get() != null && tableList.get().getClsKeyQuery9().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate9().get();
		} else if (tableList.get().getClsKeyQuery9().get() != null
				&& tableList.get().getClsKeyQuery9().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate9().get();
		}

		if (tableList.get().getClsKeyQuery10().get() != null && tableList.get().getClsKeyQuery10().get().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate10().get();
		} else if (tableList.get().getClsKeyQuery10().get() != null
				&& tableList.get().getClsKeyQuery10().get().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate10().get();
		}

		String cidCurrent = AppContexts.user().companyId();
		String tableName = tableList.get().getTableEnglishName();

		performDataRecoveryRepository.deleteEmployeeHis(tableName, whereCid, whereSid, cidCurrent, employeeId);

	}

	public int exCurrentCategory(TableListByCategory tableListByCategory, TableListByCategory tableNotUseByCategory,
			String uploadId, String dataRecoveryProcessId) throws ParseException {

		int errorCode = 0;
		// カテゴリの中の社員単位の処理
		errorCode = exTableUse(tableListByCategory, dataRecoveryProcessId, uploadId);

		if (errorCode == DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS.value) {
			// の処理対象社員コードをクリアする
			dataRecoveryMngRepository.updateProcessTargetEmpCode(dataRecoveryProcessId, null);

			// カテゴリの中の日付単位の処理
			errorCode = exTableNotUse(tableNotUseByCategory, dataRecoveryProcessId, uploadId);

		}

		dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, errorCode);

		return errorCode;
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public int exTableNotUse(TableListByCategory tableNotUseByCategory, String dataRecoveryProcessId, String uploadId)
			throws ParseException {

		int errorCode = 0;
		if (tableNotUseByCategory.getTables().size() != 0) {

			// テーブル一覧のカレントの1行分の項目を取得する

			for (int i = 0; i < tableNotUseByCategory.getTables().size(); i++) {

				// Get trạng thái domain データ復旧動作管理
				Optional<DataRecoveryMng> dataRecoveryMng = dataRecoveryMngRepository
						.getDataRecoveryMngById(dataRecoveryProcessId);
				if (dataRecoveryMng.isPresent() && dataRecoveryMng.get().getOperatingCondition().value == DataRecoveryOperatingCondition.INTERRUPTION_END.value) {
					return DataRecoveryOperatingCondition.INTERRUPTION_END.value; 	
				}

				List<List<String>> targetDataRecovery = CsvFileUtil.getAllRecord(uploadId,
						tableNotUseByCategory.getTables().get(i).getInternalFileName());
				
				// sort date TO-DO
				
				// 期間別データ処理
				Optional<TableList> tableList = performDataRecoveryRepository.getByInternal(
						tableNotUseByCategory.getTables().get(i).getInternalFileName(), dataRecoveryProcessId);
				errorCode = exDataTabeRangeDate(tableNotUseByCategory.getTables().get(i).getInternalFileName(),
						targetDataRecovery, tableList, dataRecoveryProcessId);

				// Xác định trạng thái error
				if (errorCode == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value) {
					break;
				}

			}

		}

		return errorCode;
	}

	public int exTableUse(TableListByCategory tableListByCategory, String dataRecoveryProcessId, String uploadId)
			throws ParseException {

		int errorCode = 0;
		int numberEmError = 0;
		List<DataRecoveryTable> targetDataByCate = new ArrayList<>();

		// カテゴリ単位の復旧
		if (tableListByCategory.getTables().size() > 0) {

			// -- Get List data từ file CSV
			// Create [対象データ] TargetData
			Set<String> hashId = new HashSet<>();
			for (int j = 0; j < tableListByCategory.getTables().size(); j++) {
				List<List<String>> dataRecovery = CsvFileUtil.getAllRecord(uploadId,
						tableListByCategory.getTables().get(j).getInternalFileName());

				// -- Tổng hợp ID Nhân viên duy nhất từ List Data
				for (int i = 1; i < dataRecovery.size(); i++) {
					if (!dataRecovery.get(i).get(INDEX_SID).isEmpty())
						hashId.add(dataRecovery.get(i).get(INDEX_SID));
				}

				DataRecoveryTable targetData = new DataRecoveryTable(dataRecovery,
						tableListByCategory.getTables().get(j).getInternalFileName());
				if (dataRecovery.size() > 1) {
					targetDataByCate.add(targetData);
				}
			}

			// 対象社員コード＿ID
			List<EmployeeDataReInfoImport> employeeInfos = new ArrayList<>();
			Iterator<String> it = hashId.iterator();
			while (it.hasNext()) {
				Optional<EmployeeDataReInfoImport> employeeInfo = empDataMngRepo.getSdataMngInfo(it.next());
				employeeInfo.ifPresent(x -> {
					employeeInfos.add(x);
				});
			}

			// Foreach 対象社員コード＿ID
			for (EmployeeDataReInfoImport employeeDataMngInfoImport : employeeInfos) {

				// Update current employeeCode
				dataRecoveryMngRepository.updateProcessTargetEmpCode(dataRecoveryProcessId,
						employeeDataMngInfoImport.getEmployeeCode());

				// 対象社員データ処理
				try {
					errorCode = self.recoveryDataByEmployee(dataRecoveryProcessId,
							employeeDataMngInfoImport.getEmployeeCode(), employeeDataMngInfoImport.getEmployeeId(),
							targetDataByCate);
				} catch (Exception e) {
					errorCode = Integer.valueOf(e.getMessage());
				}

				if (errorCode == Integer.valueOf(SETTING_EXCEPTION)) {
					numberEmError++;
					dataRecoveryMngRepository.updateErrorCount(dataRecoveryProcessId, numberEmError);
					break;
				} else if (errorCode == Integer.valueOf(SQL_EXCEPTION)) {
					numberEmError++;
					dataRecoveryMngRepository.updateErrorCount(dataRecoveryProcessId, numberEmError);
					continue;
				}

				// check interruption [中断]
				Optional<DataRecoveryMng> dataRecovery = dataRecoveryMngRepository
						.getDataRecoveryMngById(dataRecoveryProcessId);
				if (dataRecovery.isPresent() && dataRecovery.get()
						.getOperatingCondition().value == DataRecoveryOperatingCondition.INTERRUPTION_END.value) {
					errorCode = DataRecoveryOperatingCondition.INTERRUPTION_END.value;
					return errorCode;
				}

			}

		}
		return errorCode;
	}

	public List<String> settingDate(Optional<TableList> tableList) {

		// 「テーブル一覧」の抽出キー区から日付項目を設定する
		List<String> checkKeyQuery = new ArrayList<>();
		List<String> resultsSetting = new ArrayList<>();
		Integer timeStore = null;
		int count6 = 0, count7 = 0, count8 = 0;
		if (tableList.isPresent()) {
			checkKeyQuery.add(tableList.get().getClsKeyQuery1().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery2().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery3().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery4().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery5().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery6().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery7().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery8().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery9().get());
			checkKeyQuery.add(tableList.get().getClsKeyQuery10().get());
			timeStore = tableList.get().getRetentionPeriodCls().value;
		}
		for (String keyQuery : checkKeyQuery) {
			if (keyQuery.equals("6")) {
				count6++;
			} else if (keyQuery.equals("7")) {
				count7++;
			} else if (keyQuery.equals("8")) {
				count8++;
			}
		}

		// không date
		if (count6 == 0 && count7 == 0 && count8 == 0) {
			resultsSetting.add("-9");
		} else if (count6 != 0 && count7 == 0 && count8 == 0) {
			// năm hoặc phạm vi năm
			resultsSetting.add("6");
			if (count6 == 2) {
				resultsSetting.add("6");
			}
		} else if (count6 == 0 && count7 != 0 && count8 == 0) {
			// tháng năm hoặc là phạm vi tháng năm
			resultsSetting.add("7");
			if (count7 == 2) {
				resultsSetting.add("7");
			}
		} else if (count6 == 0 && count7 == 0 && count8 != 0) {
			// ngày tháng năm hoặc phạm vi ngày tháng năm
			resultsSetting.add("8");
			if (count8 == 2) {
				resultsSetting.add("8");
			}
		}

		// 保存期間区分と日付設定を判別
		if (timeStore == null || timeStore == 0 && !resultsSetting.isEmpty() && !resultsSetting.get(0).equals("-9")
				|| timeStore == 1 && !resultsSetting.isEmpty() && !resultsSetting.get(0).equals("6")
				|| timeStore == 2 && !resultsSetting.isEmpty() && !resultsSetting.get(0).equals("7")
				|| timeStore == 3 && !resultsSetting.isEmpty() && !resultsSetting.get(0).equals("8")) {
			resultsSetting.clear();
			return resultsSetting;
		}

		return resultsSetting;

	}

	public Boolean checkSettingDate(List<String> resultsSetting, Optional<TableList> tableList, List<String> dataRow)
			throws ParseException {
		if (!resultsSetting.isEmpty()) {

			String H_Date_Csv = null;
			if (resultsSetting.size() == 1) {
				H_Date_Csv = dataRow.get(2);
			} else if (resultsSetting.size() == 2) {
				H_Date_Csv = dataRow.get(3);
			}
			if (H_Date_Csv.isEmpty() || H_Date_Csv == null)
				return false;
			Date Date_Csv = new SimpleDateFormat("yyyy-MM-dd").parse(H_Date_Csv);
			Integer Y_Date_Csv = Integer.parseInt(H_Date_Csv.substring(0, 4));
			if (resultsSetting.get(0).equals("6")) {
				if (Y_Date_Csv < Integer.parseInt(tableList.get().getSaveDateFrom().get().substring(0, 3))
						|| Y_Date_Csv > Integer
								.parseInt(tableList.get().getSaveDateTo().get().substring(0, 3))) {
					return false;
				}
			} else if (resultsSetting.get(0).equals("7")) {
				Integer M_Date_Csv = Integer.parseInt(H_Date_Csv.substring(5, 7));
				if (Integer.parseInt(tableList.get().getSaveDateFrom().get().substring(0, 3)) > Y_Date_Csv
						|| (Integer
								.parseInt(tableList.get().getSaveDateFrom().get().substring(0, 3)) == Y_Date_Csv
								&& M_Date_Csv < Integer
										.parseInt(tableList.get().getSaveDateFrom().get().substring(4)))
						|| Integer.parseInt(tableList.get().getSaveDateTo().get().substring(0, 3)) < Y_Date_Csv
						|| (Integer.parseInt(tableList.get().getSaveDateTo().get().substring(0, 3)) == Y_Date_Csv
								&& M_Date_Csv > Integer
										.parseInt(tableList.get().getSaveDateTo().get().substring(4)))) {
					return false;
				}

			} else if (resultsSetting.get(0).equals("8")) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				String from = tableList.get().getSaveDateFrom().get();
				Date Date_From_Table = formatter.parse(from);
				String to = tableList.get().getSaveDateTo().get();
				Date Date_To_Table = formatter.parse(to);
				if (!Date_Csv.after(Date_From_Table) || !Date_Csv.before(Date_To_Table)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public int exDataTabeRangeDate(String fileNameCsv, List<List<String>> targetDataRecovery,
			Optional<TableList> tableList, String dataRecoveryProcessId) throws ParseException {

		// アルゴリズム「日付処理の設定」を実行し日付設定を取得する
		List<String> resultsSetting = new ArrayList<>();
		resultsSetting = this.settingDate(tableList);

		int errorCode = 0;

		if (!resultsSetting.isEmpty()) {

			if (tableList.isPresent()) {

				// 履歴区分の判別する - check phân loại lịch sử
				if (tableList.get().getHistoryCls().value == HistoryDiviSion.HAVE_HISTORY.value) {
					deleteEmployeeHistory(tableList, null, false);
				}

				Optional<PerformDataRecovery> performDataRecovery = performDataRecoveryRepository
						.getPerformDatRecoverById(dataRecoveryProcessId);

				errorCode = this.crudDataByTable(targetDataRecovery, null, null, dataRecoveryProcessId, fileNameCsv,
						tableList, performDataRecovery, resultsSetting, false);

			}
		} else {
			errorCode = DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value;
			return errorCode;
		}
		return errorCode;

	}

}
