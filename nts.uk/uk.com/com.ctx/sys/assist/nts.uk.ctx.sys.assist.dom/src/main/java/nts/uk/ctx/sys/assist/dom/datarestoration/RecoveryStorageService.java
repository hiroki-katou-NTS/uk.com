package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.category.StorageRangeSaved;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.HistoryDiviSion;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.CsvFileUtil;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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

	@Inject
	private DataRecoveryResultRepository dataRecoveryResultRepository;

	private RecoveryStorageService self;

	@PostConstruct
	public void init() {
		this.self = scContext.getBusinessObject(RecoveryStorageService.class);
	}

	public static final String DATE_FORMAT = "yyyyMMdd";

	public static final int SELECTION_TARGET_FOR_RES = 1;

	public static final String SQL_EXCEPTION = "113";

	public static final String SETTING_EXCEPTION = "5";

	public static final String INDEX_CID_CSV = "0";

	public static final String INDEX_SID_CSV = "5";

	public static final String NONE_DATE = "9";

	public static final String YEAR = "6";

	public static final String YEAR_MONTH = "7";

	public static final String YEAR_MONTH_DAY = "8";

	public static final String GET_CLS_KEY_QUERY = "getClsKeyQuery";

	public static final String GET_FILED_KEY_UPDATE = "getFiledKeyUpdate";
	
	// fix bug #125405
	public static final String GET_FILED_KEY_QUERY = "getFieldKeyQuery";

	public static final String INDEX_HEADER = "indexUpdate";

	public static final Integer HEADER_CSV = 0;

	public static final Integer INDEX_SID = 1;

	public static final Integer INDEX_H_DATE = 2;

	public static final Integer INDEX_H_START_DATE = 3;
	
	public static Integer NUMBER_ERROR = 0;


	private static final Logger LOGGER = LoggerFactory.getLogger(RecoveryStorageService.class);

	private static final Map<String, Integer> datetimeRange = new HashMap<String, Integer>();
	static {
		datetimeRange.put(YEAR_MONTH_DAY, 10);
		datetimeRange.put(YEAR_MONTH, 7);
		datetimeRange.put(YEAR, 4);
	}

	public void recoveryStorage(String dataRecoveryProcessId) throws ParseException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		NUMBER_ERROR = 0;
		Optional<PerformDataRecovery> performRecoveries = performDataRecoveryRepository
				.getPerformDatRecoverById(dataRecoveryProcessId);
		String uploadId = performRecoveries.get().getUploadfileId();
		List<Category> listCategory = categoryRepository.findById(dataRecoveryProcessId, SELECTION_TARGET_FOR_RES);

		Optional<DataRecoveryMng> dataRecoveryMng = dataRecoveryMngRepository
				.getDataRecoveryMngById(dataRecoveryProcessId);
		if (dataRecoveryMng.isPresent() && dataRecoveryMng.get().getSuspendedState() == NotUseAtr.USE) {
			dataRecoveryResultRepository.updateEndDateTimeExecutionResult(dataRecoveryProcessId,
					DataRecoveryOperatingCondition.INTERRUPTION_END);
			  performDataRecoveryRepository.remove(dataRecoveryProcessId);
			  performDataRecoveryRepository.deleteTableListByDataStorageProcessingId(dataRecoveryProcessId);
			return;
		}

		// update OperatingCondition
		dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,
				DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS);

		DataRecoveryOperatingCondition condition = DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS;
		int numberCateSucess = 0;
		
		// 処理対象のカテゴリを処理する
		for (Category category : listCategory) {

			List<TableList> tableUse = performDataRecoveryRepository.getByStorageRangeSaved(
					category.getCategoryId().v(), dataRecoveryProcessId, StorageRangeSaved.EARCH_EMP);
			List<TableList> tableNotUse = performDataRecoveryRepository.getByStorageRangeSaved(
					category.getCategoryId().v(), dataRecoveryProcessId, StorageRangeSaved.ALL_EMP);

			TableListByCategory tableListByCategory = new TableListByCategory(category.getCategoryId().v(), tableUse);
			TableListByCategory tableNotUseCategory = new TableListByCategory(category.getCategoryId().v(),
					tableNotUse);

			// カテゴリ単位の復旧
			condition = exCurrentCategory(tableListByCategory, tableNotUseCategory, uploadId, dataRecoveryProcessId);

			// のカテゴリカウントをカウントアップ

			if (condition != DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS) {
				break;
			}
			numberCateSucess++;
			dataRecoveryMngRepository.updateCategoryCnt(dataRecoveryProcessId, numberCateSucess);
		}

		if (condition == DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS) {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,
					DataRecoveryOperatingCondition.DONE);
			dataRecoveryResultRepository.updateEndDateTimeExecutionResult(dataRecoveryProcessId,
					DataRecoveryOperatingCondition.DONE);
		} else {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, condition);
			dataRecoveryResultRepository.updateEndDateTimeExecutionResult(dataRecoveryProcessId, condition);
		}

		 performDataRecoveryRepository.deleteTableListByDataStorageProcessingId(dataRecoveryProcessId);
		 performDataRecoveryRepository.remove(dataRecoveryProcessId);
		 

	}

	@Transactional(value = TxType.REQUIRES_NEW, rollbackOn = Exception.class)
	public DataRecoveryOperatingCondition recoveryDataByEmployee(String dataRecoveryProcessId, String employeeCode,
			String employeeId, List<DataRecoveryTable> targetDataByCate, List<Target> listTarget) throws Exception {
		
		DataRecoveryOperatingCondition condition = DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS;
		Optional<PerformDataRecovery> performDataRecovery = performDataRecoveryRepository
				.getPerformDatRecoverById(dataRecoveryProcessId);

		// Check recovery method [復旧方法]
		if (performDataRecovery.isPresent()
				&& performDataRecovery.get().getRecoveryMethod() == RecoveryMethod.RESTORE_SELECTED_RANGE) {

			Optional<Target> existEmployee = listTarget.stream().filter(x -> {
				return employeeId.equals(x.getSid());
			}).findFirst();
			if (!existEmployee.isPresent()) {
				return DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS;
			}

		}

		// current target Data [カレント対象データ]

		for (DataRecoveryTable dataRecoveryTable : targetDataByCate) {

			Optional<TableList> tableList = performDataRecoveryRepository
					.getByInternal(dataRecoveryTable.getFileNameCsv(), dataRecoveryProcessId);
			// check date [日付処理の設定]

			List<String> resultsSetting = new ArrayList<>();
			resultsSetting = this.settingDate(tableList);
			if (resultsSetting.isEmpty()) {
				LOGGER.error("Setting error rollBack transaction");
				throw new Exception(SETTING_EXCEPTION);
			}
			
			// 履歴区分の判別する - check history division
			if (tableList.isPresent() && tableList.get().getHistoryCls() == HistoryDiviSion.HAVE_HISTORY) {
				try {
					deleteEmployeeHistory(tableList, true, employeeId);
				} catch (Exception e) {
					LOGGER.error("SQL error rollBack transaction");
					throw new Exception(SQL_EXCEPTION);
				}
			}

			try {
				// 対象社員の日付順の処理
				condition = crudDataByTable(dataRecoveryTable.getDataRecovery(), employeeId, employeeCode,
						dataRecoveryProcessId, tableList, performDataRecovery, resultsSetting, true);
			} catch (Exception e) {
				// DELETE/INSERT error
				LOGGER.error("SQL error rollBack transaction");
				throw new Exception(SQL_EXCEPTION);
			}

			// Setting error
			if (condition == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION) {
				LOGGER.error("Setting error rollBack transaction");
				throw new Exception(SETTING_EXCEPTION);
			}

		}
		return condition;
	}

	public DataRecoveryOperatingCondition crudDataByTable(List<List<String>> targetDataTable, String employeeId,
			String employeeCode, String dataRecoveryProcessId, Optional<TableList> tableList,
			Optional<PerformDataRecovery> performDataRecovery, List<String> resultsSetting, Boolean tableUse)
			throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		DataRecoveryOperatingCondition condition = DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS;
		int indexCidOfCsv = 0;
		List<String> targetDataHeader = targetDataTable.get(HEADER_CSV);
		String V_FILED_KEY_UPDATE = null, TABLE_NAME = null, h_Date_Csv = null, dateSub = "";

		HashMap<Integer, String> indexAndFiled = new HashMap<>();	
		// search data by employee

		if (tableList.isPresent()) {
			TABLE_NAME = tableList.get().getTableEnglishName();
			indexAndFiled = indexMapFiledCsv(targetDataHeader, tableList);
		}

		List<List<String>> dataTableNotHeader = new ArrayList<>();

		// sort date of targetData
		dataTableNotHeader = sortByDate(targetDataTable);
		for (List<String> dataRow : dataTableNotHeader) {
			Map<String, String> filedWhere = new HashMap<>();
			if (resultsSetting.size() == 1) {
				h_Date_Csv = dataRow.get(INDEX_H_DATE);
			} else if (resultsSetting.size() == 2) {
				h_Date_Csv = dataRow.get(INDEX_H_START_DATE);
			}

			// データベース復旧処理
			if ((tableUse && employeeId != null && !dataRow.get(INDEX_SID).equals(employeeId))) {
				continue;
			}

			// 履歴区分を判別する - check history division
			if (((tableUse && tableList.get().getHistoryCls() == HistoryDiviSion.NO_HISTORY) || !tableUse)
					&& (performDataRecovery.isPresent()
							&& performDataRecovery.get().getRecoveryMethod() == RecoveryMethod.RESTORE_SELECTED_RANGE
							&& tableList.get().getRetentionPeriodCls() != TimeStore.FULL_TIME
							&& !checkSettingDate(resultsSetting, tableList, dataRow, h_Date_Csv))) {
				continue;
			}

			// update recovery date for have history, save range none, year,
			// year/month, year/month/day
			if (!h_Date_Csv.isEmpty()) {
				if (tableList.get().getHistoryCls() == HistoryDiviSion.HAVE_HISTORY && tableUse)
					dateSub = dateTimeCutter(YEAR_MONTH_DAY, h_Date_Csv).orElse("");
				if (tableList.get().getRetentionPeriodCls() == TimeStore.FULL_TIME) {
					dateSub = "";
				}

				dateSub = dateTimeCutter(resultsSetting.get(0), h_Date_Csv).orElse("");
			} else {
				dateSub = "";
			}

			dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, dateSub);

			// create filed where for query
			for (Map.Entry<Integer, String> entry : indexAndFiled.entrySet()) {
				V_FILED_KEY_UPDATE = dataRow.get(entry.getKey());
				filedWhere.put(entry.getValue(), V_FILED_KEY_UPDATE);
			}

			// 対象データの会社IDをパラメータの会社IDに入れ替える - swap CID
			String cidCurrent = AppContexts.user().companyId();

			// 既存データの検索
			String namePhysicalCid = findNamePhysicalCid(tableList);
			int count;
			if(tableUse) {
				count = performDataRecoveryRepository.countDataTransactionExitTableByVKeyUp(filedWhere, TABLE_NAME,
						namePhysicalCid, cidCurrent);
			} else {
				count = performDataRecoveryRepository.countDataExitTableByVKeyUp(filedWhere, TABLE_NAME,
						namePhysicalCid, cidCurrent);
			}
			
			if (count > 1 && tableUse) {
				return DataRecoveryOperatingCondition.ABNORMAL_TERMINATION;
			} else if (count > 1 && !tableUse) {
				continue;
			}

			indexCidOfCsv = targetDataHeader.indexOf(namePhysicalCid);
			HashMap<String, String> dataInsertDb = new HashMap<>();
			for (int j = 5; j < dataRow.size(); j++) {
				dataInsertDb.put(targetDataHeader.get(j), j == indexCidOfCsv ? cidCurrent : dataRow.get(j));
			}
			List<String> columnNotNull = checkTypeColumn(TABLE_NAME);
			// insert delete data
			if(tableUse) {
				crudRowTransaction(count, filedWhere, TABLE_NAME, namePhysicalCid, cidCurrent, dataInsertDb, columnNotNull);
			} else {
				crudRow(count, filedWhere, TABLE_NAME, namePhysicalCid, cidCurrent, dataInsertDb, columnNotNull);
			}

		}
		return condition;
	}
	
	public List<String> checkTypeColumn(String TABLE_NAME) {
		List<String> data = performDataRecoveryRepository.getTypeColumnNotNull(TABLE_NAME);
		return data;
	}
	
	public void crudRow(int count, Map<String, String> filedWhere, String TABLE_NAME, String namePhysicalCid,
			String cidCurrent, HashMap<String, String> dataInsertDb, List<String> columnNotNull) {
		try {
			if(count == 1) {
				performDataRecoveryRepository.deleteDataExitTableByVkey(filedWhere, TABLE_NAME, namePhysicalCid,
						cidCurrent);
			}
			performDataRecoveryRepository.insertDataTable(dataInsertDb, TABLE_NAME,columnNotNull);
		} catch (Exception e) {
			LOGGER.info("Error delete data for table " + TABLE_NAME);
		}

	}

	public void crudRowTransaction(int count, Map<String, String> filedWhere, String TABLE_NAME, String namePhysicalCid,
			String cidCurrent, HashMap<String, String> dataInsertDb, List<String> columnNotNull) {
		try {
			if(count == 1) {
				performDataRecoveryRepository.deleteTransactionDataExitTableByVkey(filedWhere, TABLE_NAME, namePhysicalCid,
						cidCurrent);
			}
			performDataRecoveryRepository.insertTransactionDataTable(dataInsertDb, TABLE_NAME,columnNotNull);
		} catch (Exception e) {
			LOGGER.info("Error delete data for table " + TABLE_NAME);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public String findNamePhysicalCid(Optional<TableList> tableList) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		String namePhysical = null;
		if (tableList.isPresent()) {

			Optional<Object> keyQuery = Optional.empty();
			Optional<Object> filedKey = Optional.empty();
			for (int i = 1; i < 11; i++) {
				Method m1 = TableList.class.getMethod(GET_CLS_KEY_QUERY + i);
				keyQuery = (Optional<Object>) m1.invoke(tableList.get());
				Method m2 = TableList.class.getMethod(GET_FILED_KEY_QUERY + i);
				filedKey = (Optional<Object>) m2.invoke(tableList.get());
				if (keyQuery.isPresent()) {
					if (keyQuery.get().equals(INDEX_CID_CSV)) {
						namePhysical = (String) filedKey.get();
					}
				}
			}

		}
		return namePhysical;
	}

	@SuppressWarnings("unchecked")
	public void deleteEmployeeHistory(Optional<TableList> tableList, Boolean tableNotUse, String employeeId)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (!tableList.isPresent()) {
			return;
		}
		// Delete history

		String[] whereCid = { "" };
		String[] whereSid = { "" };

		//
		Optional<Object> keyQuery;
		Optional<Object> filedKey;
		for (int i = 1; i < 11; i++) {
			Method m1 = TableList.class.getMethod(GET_CLS_KEY_QUERY + i);
			keyQuery = (Optional<Object>) m1.invoke(tableList.get());
			Method m2 = TableList.class.getMethod(GET_FILED_KEY_QUERY + i);
			filedKey = (Optional<Object>) m2.invoke(tableList.get());
			if (keyQuery.isPresent()) {
				if (keyQuery.get().equals(INDEX_CID_CSV)) {
					whereCid[0] = (String) filedKey.get();
				} else if (keyQuery.get().equals(INDEX_SID_CSV) && tableNotUse) {
					whereSid[0] = (String) filedKey.get();
				}
			}
		}

		String cidCurrent = AppContexts.user().companyId();
		String tableName = tableList.get().getTableEnglishName();
		if(tableNotUse) {
			performDataRecoveryRepository.deleteTransactionEmployeeHis(tableName, whereCid[0], whereSid[0], cidCurrent, employeeId);
		} else {
			performDataRecoveryRepository.deleteEmployeeHis(tableName, whereCid[0], whereSid[0], cidCurrent, employeeId);
		}
	}

	public DataRecoveryOperatingCondition exCurrentCategory(TableListByCategory tableListByCategory,
			TableListByCategory tableNotUseByCategory, String uploadId, String dataRecoveryProcessId)
			throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		DataRecoveryOperatingCondition condition = DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS;
		// カテゴリの中の社員単位の処理
		condition = exTableUse(tableListByCategory, dataRecoveryProcessId, uploadId);

		if (condition == DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS) {
			// の処理対象社員コードをクリアする
			dataRecoveryMngRepository.updateProcessTargetEmpCode(dataRecoveryProcessId, null);

			// カテゴリの中の日付単位の処理
			condition = exTableNotUse(tableNotUseByCategory, dataRecoveryProcessId, uploadId);

		}

		dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, condition);
		return condition;
	}

	public DataRecoveryOperatingCondition exTableNotUse(TableListByCategory tableNotUseByCategory,
			String dataRecoveryProcessId, String uploadId) throws ParseException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		DataRecoveryOperatingCondition condition = DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS;
		if (tableNotUseByCategory.getTables().size() == 0) {
			return condition;
		}

		// テーブル一覧のカレントの1行分の項目を取得する
		for (TableList tableList : tableNotUseByCategory.getTables()) {

			// Get trạng thái domain データ復旧動作管理
			Optional<DataRecoveryMng> dataRecoveryMng = dataRecoveryMngRepository
					.getDataRecoveryMngById(dataRecoveryProcessId);
			if (dataRecoveryMng.isPresent() && dataRecoveryMng.get().getSuspendedState() == NotUseAtr.USE) {
				return DataRecoveryOperatingCondition.INTERRUPTION_END;
			}

			List<List<String>> targetDataRecovery = CsvFileUtil.getAllRecord(uploadId, tableList.getInternalFileName());

			// 期間別データ処理
			// Optional<TableList> tableList =
			// performDataRecoveryRepository.getByInternal(a.getInternalFileName(),
			// dataRecoveryProcessId);
			condition = exDataTabeRangeDate(targetDataRecovery, Optional.of(tableList), dataRecoveryProcessId);

			// Xác định trạng thái error
			if (condition == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION) {
				return condition;
			}

		}

		return condition;
	}

	public DataRecoveryOperatingCondition exTableUse(TableListByCategory tableListByCategory,
			String dataRecoveryProcessId, String uploadId) throws ParseException {

		DataRecoveryOperatingCondition condition = DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS;
		String errorCode = "";
		List<DataRecoveryTable> targetDataByCate = new ArrayList<>();

		// カテゴリ単位の復旧
		if (tableListByCategory.getTables().size() > 0) {
			// -- Get List data from CSV file

			// Create [対象データ] TargetData
			Set<String> hashId = new HashSet<>();
			for (int j = 0; j < tableListByCategory.getTables().size(); j++) {
				List<List<String>> dataRecovery = CsvFileUtil.getAllRecord(uploadId,
						tableListByCategory.getTables().get(j).getInternalFileName());

				// -- Tổng hợp ID Nhân viên duy nhất từ List Data
				for (int i = 1; i < dataRecovery.size(); i++) {
					if (!dataRecovery.get(i).get(1).isEmpty())
						hashId.add(dataRecovery.get(i).get(1));
				}
				if (dataRecovery.size() > 1) {
					DataRecoveryTable targetData = new DataRecoveryTable(dataRecovery,
							tableListByCategory.getTables().get(j).getInternalFileName());
					targetDataByCate.add(targetData);
				}
			}

			// 対象社員コード＿ID
			List<EmployeeDataReInfoImport> employeeInfos = empDataMngRepo
					.findByIdsEmployee(new ArrayList<String>(hashId));

			// check employeeId in Target of PreformDataRecovery
			List<Target> listTarget = performDataRecoveryRepository.findByDataRecoveryId(dataRecoveryProcessId);

			// Foreach 対象社員コード＿ID
			for (EmployeeDataReInfoImport employeeDataMngInfoImport : employeeInfos) {

				// Update current employeeCode
				dataRecoveryMngRepository.updateProcessTargetEmpCode(dataRecoveryProcessId,
						employeeDataMngInfoImport.getEmployeeCode());

				// 対象社員データ処理

				try {
					condition = self.recoveryDataByEmployee(dataRecoveryProcessId,
							employeeDataMngInfoImport.getEmployeeCode(), employeeDataMngInfoImport.getEmployeeId(),
							targetDataByCate, listTarget);
				} catch (Exception e) {
					errorCode = e.getMessage();
					NUMBER_ERROR++;
					dataRecoveryMngRepository.updateErrorCount(dataRecoveryProcessId, NUMBER_ERROR);
				}

				if (errorCode.equals(SETTING_EXCEPTION)) {
					return DataRecoveryOperatingCondition.ABNORMAL_TERMINATION;
				}

				// check interruption [中断]
				Optional<DataRecoveryMng> dataRecovery = dataRecoveryMngRepository
						.getDataRecoveryMngById(dataRecoveryProcessId);
				if (dataRecovery.isPresent() && dataRecovery.get().getSuspendedState() == NotUseAtr.USE) {
					return DataRecoveryOperatingCondition.INTERRUPTION_END;
				}
			}
		}
		return condition;
	}

	@SuppressWarnings("unchecked")
	public List<String> settingDate(Optional<TableList> tableList) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 「テーブル一覧」の抽出キー区から日付項目を設定する
		List<String> checkKeyQuery = new ArrayList<>();
		List<String> resultsSetting = new ArrayList<>();
		TimeStore timeStore = null;
		int countY = 0, countYm = 0, countYmd = 0;

		Optional<Object> keyQuery = Optional.empty();

		if (tableList.isPresent()) {
			for (int i = 1; i < 11; i++) {
				Method m1 = TableList.class.getMethod(GET_CLS_KEY_QUERY + i);
				keyQuery = (Optional<Object>) m1.invoke(tableList.get());
				if (keyQuery.isPresent() && !((String) keyQuery.get()).isEmpty()) {
					checkKeyQuery.add((String) keyQuery.get());
				}
			}

			timeStore = tableList.get().getRetentionPeriodCls();
		}
		for (String currentKeyQuery : checkKeyQuery) {
			switch (currentKeyQuery) {
			case YEAR:
				countY++;
				break;
			case YEAR_MONTH:
				countYm++;
				break;
			case YEAR_MONTH_DAY:
				countYmd++;
				break;
			}
		}

		// không date
		if (countY == 0 && countYm == 0 && countYmd == 0) {
			resultsSetting.add(NONE_DATE);
		} else if (countY != 0 && countYm == 0 && countYmd == 0) {
			// năm hoặc phạm vi năm
			resultsSetting.add(YEAR);
			if (countY == 2) {
				resultsSetting.add(YEAR);
			}
		} else if (countY == 0 && countYm != 0 && countYmd == 0) {
			// tháng năm hoặc là phạm vi tháng năm
			resultsSetting.add(YEAR_MONTH);
			if (countYm == 2) {
				resultsSetting.add(YEAR_MONTH);
			}
		} else if (countY == 0 && countYm == 0 && countYmd != 0) {
			// ngày tháng năm hoặc phạm vi ngày tháng năm
			resultsSetting.add(YEAR_MONTH_DAY);
			if (countYmd == 2) {
				resultsSetting.add(YEAR_MONTH_DAY);
			}
		}

		// 保存期間区分と日付設定を判別
		if (timeStore == null
				|| timeStore == TimeStore.FULL_TIME && !resultsSetting.isEmpty()
						&& !resultsSetting.get(0).equals(NONE_DATE)
				|| timeStore == TimeStore.ANNUAL && !resultsSetting.isEmpty() && !resultsSetting.get(0).equals(YEAR)
				|| timeStore == TimeStore.MONTHLY && !resultsSetting.isEmpty()
						&& !resultsSetting.get(0).equals(YEAR_MONTH)
				|| timeStore == TimeStore.DAILY && !resultsSetting.isEmpty()
						&& !resultsSetting.get(0).equals(YEAR_MONTH_DAY)) {
			resultsSetting.clear();
			return resultsSetting;
		}

		return resultsSetting;

	}

	public Boolean checkSettingDate(List<String> resultsSetting, Optional<TableList> tableList, List<String> dataRow,
			String h_Date_Csv) throws ParseException {

		if (StringUtil.isNullOrEmpty(h_Date_Csv, true)) {
			return false;
		}

		GeneralDate hDateCsv = stringToGenaralDate(h_Date_Csv);
		GeneralDate dateFrom = stringToGenaralDate(tableList.get().getSaveDateFrom().get());
		GeneralDate dateTo = stringToGenaralDate(tableList.get().getSaveDateTo().get());

		if (YEAR.equals(resultsSetting.get(0))
				&& (hDateCsv.year() < dateFrom.year() || hDateCsv.year() > dateTo.year())) {
			return false;
		} else if (YEAR_MONTH.equals(resultsSetting.get(0))) {
			if ((dateFrom.year() > hDateCsv.year()
					|| (dateFrom.year() == hDateCsv.year() && hDateCsv.month() < dateFrom.month()))
					|| (dateTo.year() < hDateCsv.year()
							|| (dateTo.year() == hDateCsv.year() && hDateCsv.month() > dateTo.month()))) {
				return false;
			}
		} else if (YEAR_MONTH_DAY.equals(resultsSetting.get(0))
				&& (dateFrom.after(hDateCsv) || dateTo.before(hDateCsv))) {
			return false;
		}
		return true;
	}

	public DataRecoveryOperatingCondition exDataTabeRangeDate(List<List<String>> targetDataRecovery,
			Optional<TableList> tableList, String dataRecoveryProcessId) throws ParseException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// アルゴリズム「日付処理の設定」を実行し日付設定を取得する
		List<String> resultsSetting = new ArrayList<>();
		resultsSetting = this.settingDate(tableList);

		DataRecoveryOperatingCondition condition = DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS;
		if (resultsSetting.isEmpty()) {
			return DataRecoveryOperatingCondition.ABNORMAL_TERMINATION;
		}

		if (tableList.isPresent()) {

			// 履歴区分の判別する - check phân loại lịch sử
			if (tableList.get().getHistoryCls() == HistoryDiviSion.HAVE_HISTORY) {
				try {
					deleteEmployeeHistory(tableList, false, null);
				} catch (Exception e) {
					LOGGER.info("Delete data of employee have history error");
				}
			}

			Optional<PerformDataRecovery> performDataRecovery = performDataRecoveryRepository
					.getPerformDatRecoverById(dataRecoveryProcessId);

			condition = this.crudDataByTable(targetDataRecovery, null, null, dataRecoveryProcessId, tableList,
					performDataRecovery, resultsSetting, false);

		}

		return condition;

	}

	private List<List<String>> sortByDate(List<List<String>> targetDataTable) {
		List<List<String>> dataTableCus = new ArrayList<>();
		for (List<String> list : targetDataTable) {
			dataTableCus.add(list);
		}
		dataTableCus.remove(0);
		Collections.sort(dataTableCus, (o1, o2) -> {
			if (StringUtil.isNullOrEmpty(o1.get(2), true)) {
				return StringUtil.isNullOrEmpty(o2.get(2), true) ? 0 : -1;
			}
			if (StringUtil.isNullOrEmpty(o2.get(2), true)) {
				return 1;
			}
			return o1.get(2).compareTo(o2.get(2));
		});
		return dataTableCus;
	}

	@SuppressWarnings("unchecked")
	private HashMap<Integer, String> indexMapFiledCsv(List<String> targetDataHeader, Optional<TableList> tableList)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		HashMap<Integer, String> indexfiledUpdate = new HashMap<>();
		String[] whereCid = { "" };
		Integer index = null;
		Optional<Object> filedKey = Optional.empty();
		for (int i = 1; i < 21; i++) {
			Method m2 = TableList.class.getMethod(GET_FILED_KEY_UPDATE + i);
			filedKey = (Optional<Object>) m2.invoke(tableList.get());
			if (filedKey.isPresent()) {
				whereCid[0] = (String) filedKey.get();
				if (!whereCid[0].isEmpty()) {
					index = targetDataHeader.indexOf((String) filedKey.get());
					indexfiledUpdate.put(index, whereCid[0]);
				}
			}
		}

		return indexfiledUpdate;
	}

	/**
	 * Cut String by type
	 * 
	 * @param type
	 *            RangeType
	 * @param datetime
	 * @return Optional<String>
	 */
	private Optional<String> dateTimeCutter(String type, String datetime) {
		return datetimeRange.containsKey(type)
				? Optional.of(stringToGenaralDate(datetime).toString().substring(0, datetimeRange.get(type)))
				: Optional.empty();
	}

	/**
	 * Convert String to GeneralDate
	 * 
	 * @param datetime
	 * @return GeneralDate
	 */
	private GeneralDate stringToGenaralDate(String datetime) {
		if (StringUtil.isNullOrEmpty(datetime, true)) {
			return null;
		}
		if (datetime.replaceAll("[^\\d.]", "").length() == 6) {
			datetime = datetime + "/01";
		} else if (datetime.replaceAll("[^\\d.]", "").length() == 4) {
			datetime = datetime + "/01/01";
		}
		return GeneralDate.fromString(datetime.replaceAll("[^\\d.]", "").substring(0, 8), DATE_FORMAT);
	}
}
