package nts.uk.ctx.sys.assist.dom.recoverystorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.category.StorageRangeSaved;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataReEmployeeAdapter;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryOperatingCondition;
import nts.uk.ctx.sys.assist.dom.datarestoration.EmployeeDataReInfoImport;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.Target;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.CsvFileUtil;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RecoveryStorageService {

	@Inject
	private PerformDataRecoveryRepository performDataRecoveryRepository;

	@Inject
	private CategoryRepository categoryRepository;

	@Inject
	private DataRecoveryMngRepository dataRecoveryMngRepository;

	@Inject
	private DataReEmployeeAdapter empDataMngRepo;

	public static final String SELECTION_TARGET_FOR_RES = "1";

	private static final Logger LOGGER = LoggerFactory.getLogger(RecoveryStorageService.class);

	public void recoveryStorage(String dataRecoveryProcessId) throws ParseException {

		int errorCodeFinal = 0;
		Optional<PerformDataRecovery> performRecoveries = performDataRecoveryRepository
				.getPerformDatRecoverById(dataRecoveryProcessId);
		String uploadId = performRecoveries.get().getUploadfileId();
		List<Category> listCategory = categoryRepository.findById(dataRecoveryProcessId, SELECTION_TARGET_FOR_RES);

		// update OperatingCondition
		dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, 0);

		// 処理対象のカテゴリを処理する

		for (int i = 0; i < listCategory.size(); i++) {

			List<TableList> tableUse = performDataRecoveryRepository
					.getByStorageRangeSaved(listCategory.get(i).getCategoryId().v(), StorageRangeSaved.EARCH_EMP.value);
			List<TableList> tableNotUse = performDataRecoveryRepository
					.getByStorageRangeSaved(listCategory.get(i).getCategoryId().v(), StorageRangeSaved.ALL_EMP.value);

			TableListByCategory tableListByCategory = new TableListByCategory(listCategory.get(i).getCategoryId().v(),
					tableUse);
			TableListByCategory tableNotUseCategory = new TableListByCategory(listCategory.get(i).getCategoryId().v(),
					tableNotUse);

			int index = 0;
			int errorCode;
			// カテゴリ単位の復旧
			errorCode = exCurrentCategory(tableListByCategory, tableNotUseCategory, uploadId, dataRecoveryProcessId);

			// のカテゴリカウントをカウントアップ
			errorCodeFinal = errorCode;
			if (errorCode == DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS.value) {
				index++;
				dataRecoveryMngRepository.updateTotalNumOfProcesses(dataRecoveryProcessId, index);
			} else if (errorCode == DataRecoveryOperatingCondition.INTERRUPTION_END.value) {
				break;
			} else if (errorCode == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value) {
				break;
			}

		}

		if (errorCodeFinal == DataRecoveryOperatingCondition.INTERRUPTION_END.value) {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,
					DataRecoveryOperatingCondition.INTERRUPTION_END.value);
		} else if (errorCodeFinal == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value) {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,
					DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value);
		} else {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,
					DataRecoveryOperatingCondition.DONE.value);
		}
	}

	@Transactional
	public int recoveryDataByEmployee(String dataRecoveryProcessId, String employeeCode, String employeeId,
			List<DataRecoveryTable> targetDataByCate) throws ParseException {

		int errorCode = 0;
		Optional<PerformDataRecovery> performDataRecovery = performDataRecoveryRepository
				.getPerformDatRecoverById(dataRecoveryProcessId);

		// Xác định phương pháp phục hồi [復旧方法]
		if (performDataRecovery.isPresent() && performDataRecovery.get().getRecoveryMethod().value == 1) {
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

			if (tableList.isPresent()) {

				// 履歴区分の判別する - check phân loại lịch sử
				if (tableList.get().getHistoryCls().value == 1) {
					deleteEmployeeHistory(tableList, employeeId, true);
				}
			}

			// sort target data by date - TO DO

			// 対象社員の日付順の処理
			errorCode = crudDataByTable(dataRecoveryTable.getDataRecovery(), employeeId, employeeCode,
					dataRecoveryProcessId, dataRecoveryTable.getFileNameCsv(), tableList, performDataRecovery,
					resultsSetting, true);

			// phân biệt DELETE/INSERT error và Setting error TO - DO
			if (errorCode == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value) {

				throw new RollBackException("113", "RollBack transaction");
				// roll back
				// Count up số lượng error
			}

		}

		return errorCode;
	}

	public int crudDataByTable(List<List<String>> targetDataTable, String employeeId, String employeeCode,
			String dataRecoveryProcessId, String fileNameCsv, Optional<TableList> tableList,
			Optional<PerformDataRecovery> performDataRecovery, List<String> resultsSetting, Boolean tableUse)
			throws ParseException {
		int errorCode = 0;
		Integer indexUpdate1 = null;
		Integer indexUpdate2 = null;
		Integer indexUpdate3 = null;
		Integer indexUpdate4 = null;
		Integer indexUpdate5 = null;
		Integer indexUpdate6 = null;
		Integer indexUpdate7 = null;
		Integer indexUpdate8 = null;
		Integer indexUpdate9 = null;
		Integer indexUpdate10 = null;
		Integer indexUpdate11 = null;
		Integer indexUpdate12 = null;
		Integer indexUpdate13 = null;
		Integer indexUpdate14 = null;
		Integer indexUpdate15 = null;
		Integer indexUpdate16 = null;
		Integer indexUpdate17 = null;
		Integer indexUpdate18 = null;
		Integer indexUpdate19 = null;
		Integer indexUpdate20 = null;
		String cidTable = null;
		List<String> targetDataHeader = targetDataTable.get(0);

		String FILED_KEY_UPDATE_1 = null;
		String FILED_KEY_UPDATE_2 = null;
		String FILED_KEY_UPDATE_3 = null;
		String FILED_KEY_UPDATE_4 = null;
		String FILED_KEY_UPDATE_5 = null;
		String FILED_KEY_UPDATE_6 = null;
		String FILED_KEY_UPDATE_7 = null;
		String FILED_KEY_UPDATE_8 = null;
		String FILED_KEY_UPDATE_9 = null;
		String FILED_KEY_UPDATE_10 = null;
		String FILED_KEY_UPDATE_11 = null;
		String FILED_KEY_UPDATE_12 = null;
		String FILED_KEY_UPDATE_13 = null;
		String FILED_KEY_UPDATE_14 = null;
		String FILED_KEY_UPDATE_15 = null;
		String FILED_KEY_UPDATE_16 = null;
		String FILED_KEY_UPDATE_17 = null;
		String FILED_KEY_UPDATE_18 = null;
		String FILED_KEY_UPDATE_19 = null;
		String FILED_KEY_UPDATE_20 = null;

		String V_FILED_KEY_UPDATE_1 = null;
		String V_FILED_KEY_UPDATE_2 = null;
		String V_FILED_KEY_UPDATE_3 = null;
		String V_FILED_KEY_UPDATE_4 = null;
		String V_FILED_KEY_UPDATE_5 = null;
		String V_FILED_KEY_UPDATE_6 = null;
		String V_FILED_KEY_UPDATE_7 = null;
		String V_FILED_KEY_UPDATE_8 = null;
		String V_FILED_KEY_UPDATE_9 = null;
		String V_FILED_KEY_UPDATE_10 = null;
		String V_FILED_KEY_UPDATE_11 = null;
		String V_FILED_KEY_UPDATE_12 = null;
		String V_FILED_KEY_UPDATE_13 = null;
		String V_FILED_KEY_UPDATE_14 = null;
		String V_FILED_KEY_UPDATE_15 = null;
		String V_FILED_KEY_UPDATE_16 = null;
		String V_FILED_KEY_UPDATE_17 = null;
		String V_FILED_KEY_UPDATE_18 = null;
		String V_FILED_KEY_UPDATE_19 = null;
		String V_FILED_KEY_UPDATE_20 = null;

		String TABLE_NAME = null;

		// tìm kiếm data by employee

		if (tableList.isPresent()) {

			TABLE_NAME = tableList.get().getTableEnglishName();
			FILED_KEY_UPDATE_1 = tableList.get().getFiledKeyUpdate1();

			if (FILED_KEY_UPDATE_1 != null) {
				indexUpdate1 = targetDataHeader.indexOf(FILED_KEY_UPDATE_1);
			}
			FILED_KEY_UPDATE_2 = tableList.get().getFiledKeyUpdate2();
			if (FILED_KEY_UPDATE_2 != null) {
				indexUpdate2 = targetDataHeader.indexOf(FILED_KEY_UPDATE_2);
			}
			FILED_KEY_UPDATE_3 = tableList.get().getFiledKeyUpdate3();
			if (FILED_KEY_UPDATE_3 != null) {
				indexUpdate3 = targetDataHeader.indexOf(FILED_KEY_UPDATE_3);
			}

			FILED_KEY_UPDATE_4 = tableList.get().getFiledKeyUpdate4();
			if (FILED_KEY_UPDATE_4 != null) {
				indexUpdate4 = targetDataHeader.indexOf(FILED_KEY_UPDATE_4);
			}

			FILED_KEY_UPDATE_5 = tableList.get().getFiledKeyUpdate5();
			if (FILED_KEY_UPDATE_5 != null) {
				indexUpdate5 = targetDataHeader.indexOf(FILED_KEY_UPDATE_5);
			}

			FILED_KEY_UPDATE_6 = tableList.get().getFiledKeyUpdate6();
			if (FILED_KEY_UPDATE_6 != null) {
				indexUpdate6 = targetDataHeader.indexOf(FILED_KEY_UPDATE_6);
			}

			FILED_KEY_UPDATE_7 = tableList.get().getFiledKeyUpdate7();
			if (FILED_KEY_UPDATE_7 != null) {
				indexUpdate7 = targetDataHeader.indexOf(FILED_KEY_UPDATE_7);
			}

			FILED_KEY_UPDATE_8 = tableList.get().getFiledKeyUpdate8();
			if (FILED_KEY_UPDATE_8 != null) {
				indexUpdate8 = targetDataHeader.indexOf(FILED_KEY_UPDATE_8);
			}

			FILED_KEY_UPDATE_9 = tableList.get().getFiledKeyUpdate9();
			if (FILED_KEY_UPDATE_9 != null) {
				indexUpdate9 = targetDataHeader.indexOf(FILED_KEY_UPDATE_9);
			}

			FILED_KEY_UPDATE_10 = tableList.get().getFiledKeyUpdate10();
			if (FILED_KEY_UPDATE_10 != null) {
				indexUpdate10 = targetDataHeader.indexOf(FILED_KEY_UPDATE_10);
			}

			FILED_KEY_UPDATE_11 = tableList.get().getFiledKeyUpdate11();
			if (FILED_KEY_UPDATE_11 != null) {
				indexUpdate11 = targetDataHeader.indexOf(FILED_KEY_UPDATE_11);
			}

			FILED_KEY_UPDATE_12 = tableList.get().getFiledKeyUpdate12();
			if (FILED_KEY_UPDATE_12 != null) {
				indexUpdate12 = targetDataHeader.indexOf(FILED_KEY_UPDATE_12);
			}

			FILED_KEY_UPDATE_13 = tableList.get().getFiledKeyUpdate13();
			if (FILED_KEY_UPDATE_13 != null) {
				indexUpdate13 = targetDataHeader.indexOf(FILED_KEY_UPDATE_13);
			}

			FILED_KEY_UPDATE_14 = tableList.get().getFiledKeyUpdate14();
			if (FILED_KEY_UPDATE_14 != null)
				indexUpdate14 = targetDataHeader.indexOf(FILED_KEY_UPDATE_14);
			FILED_KEY_UPDATE_15 = tableList.get().getFiledKeyUpdate15();
			if (FILED_KEY_UPDATE_15 != null)
				indexUpdate15 = targetDataHeader.indexOf(FILED_KEY_UPDATE_15);
			FILED_KEY_UPDATE_16 = tableList.get().getFiledKeyUpdate16();
			if (FILED_KEY_UPDATE_16 != null)
				indexUpdate16 = targetDataHeader.indexOf(FILED_KEY_UPDATE_16);
			FILED_KEY_UPDATE_17 = tableList.get().getFiledKeyUpdate17();
			if (FILED_KEY_UPDATE_17 != null)
				indexUpdate17 = targetDataHeader.indexOf(FILED_KEY_UPDATE_17);
			FILED_KEY_UPDATE_18 = tableList.get().getFiledKeyUpdate18();
			if (FILED_KEY_UPDATE_18 != null)
				indexUpdate18 = targetDataHeader.indexOf(FILED_KEY_UPDATE_18);
			FILED_KEY_UPDATE_19 = tableList.get().getFiledKeyUpdate19();
			if (FILED_KEY_UPDATE_19 != null)
				indexUpdate19 = targetDataHeader.indexOf(FILED_KEY_UPDATE_19);
			FILED_KEY_UPDATE_20 = tableList.get().getFiledKeyUpdate20();
			if (FILED_KEY_UPDATE_20 != null)
				indexUpdate20 = targetDataHeader.indexOf(FILED_KEY_UPDATE_20);
		}

		for (int i = 1; i < targetDataTable.size(); i++) {

			Map<String, String> filedWhere = new HashMap<>();
			List<String> dataRow = targetDataTable.get(i);
			// データベース復旧処理
			if (employeeId != null && dataRow.get(1).equals(employeeId)) {
				// 履歴区分を判別する - Phân loại lịch sử
				if ((tableList.get().getHistoryCls().value == 0 && tableUse) || !tableUse) {

					// 復旧方法 - Phương pháp phục hồi
					if (performDataRecovery.isPresent() && performDataRecovery.get().getRecoveryMethod().value == 1) {

						// 保存期間区分を判別 - Phân loại khoảng thời gian save
						if (tableList.get().getRetentionPeriodCls().value != 0) {
							if (!checkSettingDate(resultsSetting, tableList, dataRow)) {
								if (tableUse) {
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
					if (date != null)
						dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, date);
				}

				// update date phục hồi Domain
				if (resultsSetting.get(0).equals("6")) {
					String date = dataRow.get(2);
					if (date != null)
						dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, date.substring(0, 3));
				} else if (resultsSetting.get(0).equals("7")) {
					String date = dataRow.get(2);
					if (date != null)
						dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, date.substring(0, 6));
				} else if (resultsSetting.get(0).equals("8")) {
					String date = dataRow.get(2);
					if (date != null)
						dataRecoveryMngRepository.updateRecoveryDate(dataRecoveryProcessId, date);
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
				} else if (count == 1) {
					// delete data
					try {
						performDataRecoveryRepository.deleteDataExitTableByVkey(filedWhere, TABLE_NAME, namePhysicalCid,
								cidCurrent);
					} catch (Exception ex) {
						LOGGER.error("Failed delete data : " + TABLE_NAME);
						LOGGER.error(ex.toString());
						throw new RollBackException("113", "rollBack transaction");
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
				try {
					performDataRecoveryRepository.insertDataTable(dataInsertDb, TABLE_NAME);
				} catch (Exception ex) {
					LOGGER.error("Failed insert data: " + TABLE_NAME);
					LOGGER.error(ex.toString());
					throw new RollBackException("113", "rollBack transaction");
				}

			}
		}

		return errorCode;
	}

	public String findNamePhysicalCid(Optional<TableList> tableList) {

		String namePhysical = null;
		if (tableList.isPresent()) {

			if (tableList.get().getClsKeyQuery1().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate1();
			} else if (tableList.get().getClsKeyQuery2().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate2();
			} else if (tableList.get().getClsKeyQuery3().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate3();
			} else if (tableList.get().getClsKeyQuery4().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate4();
			} else if (tableList.get().getClsKeyQuery5().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate5();
			} else if (tableList.get().getClsKeyQuery6().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate6();
			} else if (tableList.get().getClsKeyQuery7().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate7();
			} else if (tableList.get().getClsKeyQuery8().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate8();
			} else if (tableList.get().getClsKeyQuery9().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate9();
			} else if (tableList.get().getClsKeyQuery10().equals("0")) {
				namePhysical = tableList.get().getFiledKeyUpdate10();
			}
		}
		return namePhysical;
	}

	public void deleteEmployeeHistory(Optional<TableList> tableList, String employeeId, Boolean tableNotUse) {
		// Delete history

		String whereCid = null;
		String whereSid = null;
		if (tableList.get().getClsKeyQuery1().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate1();
		} else if (tableList.get().getClsKeyQuery1().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate1();
		}

		if (tableList.get().getClsKeyQuery2().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate2();
		} else if (tableList.get().getClsKeyQuery2().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate2();
		}

		if (tableList.get().getClsKeyQuery3().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate3();
		} else if (tableList.get().getClsKeyQuery3().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate3();
		}

		if (tableList.get().getClsKeyQuery4().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate4();
		} else if (tableList.get().getClsKeyQuery4().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate4();
		}

		if (tableList.get().getClsKeyQuery5().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate5();
		} else if (tableList.get().getClsKeyQuery5().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate5();
		}

		if (tableList.get().getClsKeyQuery6().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate6();
		} else if (tableList.get().getClsKeyQuery6().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate6();
		}

		if (tableList.get().getClsKeyQuery7().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate7();
		} else if (tableList.get().getClsKeyQuery7().equals("5") && !tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate7();
		}
		if (tableList.get().getClsKeyQuery8().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate8();
		} else if (tableList.get().getClsKeyQuery8().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate8();
		}

		if (tableList.get().getClsKeyQuery9().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate9();
		} else if (tableList.get().getClsKeyQuery9().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate9();
		}

		if (tableList.get().getClsKeyQuery10().equals("0")) {
			whereCid = tableList.get().getFiledKeyUpdate10();
		} else if (tableList.get().getClsKeyQuery10().equals("5") && tableNotUse) {
			whereSid = tableList.get().getFiledKeyUpdate10();
		}

		String cidCurrent = AppContexts.user().companyId();
		String tableName = tableList.get().getTableEnglishName();
		try {
			performDataRecoveryRepository.deleteEmployeeHis(tableName, whereCid, whereSid, cidCurrent, employeeId);
		} catch (Exception ex) {
			LOGGER.error("Failed delete data employee : " + tableName);
			LOGGER.error(ex.toString());
			throw new RollBackException("113", "rollBack transaction");
		}

	}

	public int exCurrentCategory(TableListByCategory tableListByCategory, TableListByCategory tableNotUseByCategory,
			String uploadId, String dataRecoveryProcessId) throws ParseException {

		int errorCode;
		// カテゴリの中の社員単位の処理
		errorCode = exTableUse(tableListByCategory, dataRecoveryProcessId, uploadId);

		if (errorCode == DataRecoveryOperatingCondition.FILE_READING_IN_PROGRESS.value) {
			// の処理対象社員コードをクリアする
			dataRecoveryMngRepository.updateProcessTargetEmpCode(dataRecoveryProcessId, null);

			// カテゴリの中の日付単位の処理
			errorCode = exTableNotUse(tableNotUseByCategory, dataRecoveryProcessId, uploadId);

		}

		if (errorCode == DataRecoveryOperatingCondition.INTERRUPTION_END.value) {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,
					DataRecoveryOperatingCondition.INTERRUPTION_END.value);
		} else if (errorCode == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value) {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,
					DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value);
		}
		return errorCode;
	}

	public int exTableNotUse(TableListByCategory tableNotUseByCategory, String dataRecoveryProcessId, String uploadId)
			throws ParseException {

		int errorCode = 0;
		if (tableNotUseByCategory.getTables().size() != 0) {

			// テーブル一覧のカレントの1行分の項目を取得する

			for (int i = 0; i < tableNotUseByCategory.getTables().size(); i++) {

				// Get trạng thái domain データ復旧動作管理
				Optional<DataRecoveryMng> dataRecoveryMng = dataRecoveryMngRepository
						.getDataRecoveryMngById(dataRecoveryProcessId);
				if (dataRecoveryMng.isPresent() && dataRecoveryMng.get().getOperatingCondition().value == 1) {
					errorCode = DataRecoveryOperatingCondition.INTERRUPTION_END.value;
					break;
				}

				List<List<String>> targetDataRecovery = CsvFileUtil
						.getAllRecord(tableNotUseByCategory.getTables().get(i).getInternalFileName(), uploadId);

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
		List<DataRecoveryTable> targetDataByCate = new ArrayList<>();

		// カテゴリ単位の復旧
		if (tableListByCategory.getTables().size() > 0) {

			// -- Get List data từ file CSV
			// Create [対象データ] TargetData
			Set<String> hashId = new HashSet<>();
			for (int j = 0; j < tableListByCategory.getTables().size(); j++) {
				List<List<String>> dataRecovery = CsvFileUtil
						.getAllRecord(tableListByCategory.getTables().get(j).getInternalFileName(), uploadId);

				// -- Tổng hợp ID Nhân viên duy nhất từ List Data
				for (List<String> dataRow : dataRecovery) {
					hashId.add(dataRow.get(1));
				}

				DataRecoveryTable targetData = new DataRecoveryTable(dataRecovery,
						tableListByCategory.getTables().get(j).getInternalFileName());
				targetDataByCate.add(targetData);
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
				errorCode = this.recoveryDataByEmployee(dataRecoveryProcessId,
						employeeDataMngInfoImport.getEmployeeCode(), employeeDataMngInfoImport.getEmployeeId(),
						targetDataByCate);

				// phan biet error
				if (errorCode == DataRecoveryOperatingCondition.ABNORMAL_TERMINATION.value) {
					return errorCode;
				}

				Optional<DataRecoveryMng> dataRecovery = dataRecoveryMngRepository
						.getDataRecoveryMngById(dataRecoveryProcessId);
				if (dataRecovery.isPresent() && dataRecovery.get().getOperatingCondition().value == 1) {
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
			checkKeyQuery.add(tableList.get().getClsKeyQuery1());
			checkKeyQuery.add(tableList.get().getClsKeyQuery2());
			checkKeyQuery.add(tableList.get().getClsKeyQuery3());
			checkKeyQuery.add(tableList.get().getClsKeyQuery4());
			checkKeyQuery.add(tableList.get().getClsKeyQuery5());
			checkKeyQuery.add(tableList.get().getClsKeyQuery6());
			checkKeyQuery.add(tableList.get().getClsKeyQuery7());
			checkKeyQuery.add(tableList.get().getClsKeyQuery8());
			checkKeyQuery.add(tableList.get().getClsKeyQuery9());
			checkKeyQuery.add(tableList.get().getClsKeyQuery10());
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
			if (count6 == 2) {
				resultsSetting.add("7");
			}
		} else if (count6 == 0 && count7 == 0 && count8 != 0) {
			// ngày tháng năm hoặc phạm vi ngày tháng năm
			resultsSetting.add("8");
			if (count6 == 2) {
				resultsSetting.add("8");
			}
		}

		// 保存期間区分と日付設定を判別
		if (timeStore == 0 && !resultsSetting.get(0).equals("-9")
				|| timeStore == 1 && !resultsSetting.get(0).equals("6")
				|| timeStore == 2 && !resultsSetting.get(0).equals("7")
				|| timeStore == 2 && !resultsSetting.get(0).equals("8")) {
			resultsSetting.clear();
			return resultsSetting;
		}

		return resultsSetting;

	}

	public Boolean checkSettingDate(List<String> resultsSetting, Optional<TableList> tableList, List<String> dataRow)
			throws ParseException {
		if (!resultsSetting.isEmpty()) {
			Integer year_Form_Table = tableList.get().getSaveDateFrom().year();
			Integer month_From_Table = tableList.get().getSaveDateFrom().month();
			Integer year_To_Table = tableList.get().getSaveDateTo().year();
			Integer month_To_Table = tableList.get().getSaveDateTo().month();
			String H_Date_Csv = null;
			if (resultsSetting.size() == 1) {
				H_Date_Csv = dataRow.get(2);
			} else if (resultsSetting.size() == 2) {
				H_Date_Csv = dataRow.get(3);
			}
			Date Date_Csv = new SimpleDateFormat("yyyy-MM-dd").parse(H_Date_Csv);
			Integer Y_Date_Csv = Date_Csv.getYear();
			Integer M_Date_Csv = Date_Csv.getMonth();
			if (resultsSetting.get(0).equals("6")) {
				if (Y_Date_Csv < year_Form_Table || Y_Date_Csv > year_To_Table) {
					return false;
				}
			} else if (resultsSetting.get(0).equals("7")) {
				if (year_Form_Table > Y_Date_Csv || (year_Form_Table == Y_Date_Csv && M_Date_Csv < month_From_Table)
						|| year_To_Table < Y_Date_Csv
						|| (year_Form_Table == Y_Date_Csv && M_Date_Csv > month_To_Table)) {
					return false;
				}

			} else if (resultsSetting.get(0).equals("8")) {
				if (Date_Csv.after(tableList.get().getSaveDateFrom().date())
						|| Date_Csv.before(tableList.get().getSaveDateFrom().date())) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}

	}

	public int exDataTabeRangeDate(String fileNameCsv, List<List<String>> targetDataRecovery,
			Optional<TableList> tableList, String dataRecoveryProcessId) throws ParseException {

		// アルゴリズム「日付処理の設定」を実行し日付設定を取得する
		List<String> resultsSetting = new ArrayList<>();
		resultsSetting = this.settingDate(tableList);

		int errorCode = 0;

		if (!resultsSetting.isEmpty()) {

			if (tableList.isPresent()) {

				// 履歴区分の判別する - check phân loại lịch sử
				if (tableList.get().getHistoryCls().value == 1) {
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
