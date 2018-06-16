package nts.uk.ctx.sys.assist.dom.recoverystorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.EmployeeDataReInfoImport;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataReEmployeeAdapter;
import nts.uk.ctx.sys.assist.dom.datarestoration.Target;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.FileUtil;
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

	public static final String STORAGE_RANGE_SAVED = "0";

	public static final String COLUMN_NAME_CID = "CID";

	public void RecoveryStorage(String dataRecoveryProcessId) {

		Boolean check = true;
		Optional<PerformDataRecovery> performRecoveries = performDataRecoveryRepository
				.getPerformDatRecoverById(dataRecoveryProcessId);
		String uploadId = performRecoveries.get().getUploadfileId();
		List<Category> listCategory = categoryRepository.findById(dataRecoveryProcessId, "1");

		// update OperatingCondition
		dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, 0);

		// 処理対象のカテゴリを処理する

		for (int i = 0; i < listCategory.size(); i++) {

			List<TableListByCategory> tableListByCategory = new ArrayList<>();
			List<TableList> tables = performDataRecoveryRepository
					.getByStorageRangeSaved(listCategory.get(i).getCategoryId().v(), STORAGE_RANGE_SAVED);

			TableListByCategory tableCategory = new TableListByCategory(listCategory.get(i).getCategoryId().v(),
					tables);
			tableListByCategory.add(tableCategory);
			int index = 0;

			// カテゴリ単位の復旧
			exCurrentCategory(tableListByCategory, uploadId, dataRecoveryProcessId);

			// のカテゴリカウントをカウントアップ
			if (check) {
				index++;
				dataRecoveryMngRepository.updateTotalNumOfProcesses(dataRecoveryProcessId, index);
			} else {
				check = false;
			}

		}

		if (check) {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, 3);
		} else {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, 1);
		}
	}

	public boolean recoveryDataByEmployee(String dataRecoveryProcessId, String employeeCode, String employeeId,
			List<DataRecoveryTable> targetDataByCate) {

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
				return false;
			}

		}

		// Data current đối tượng [カレント対象データ]
		for (DataRecoveryTable dataRecoveryTable : targetDataByCate) {
			
			Optional<TableList> tableList = performDataRecoveryRepository
					.getByInternal(dataRecoveryTable.getFileNameCsv(), dataRecoveryProcessId);
			// check date [日付処理の設定] 
			
			
			
			

			
			Boolean whereCid = false;
			Boolean whereEmId = false;
			if (tableList.isPresent()) {
				
				// 履歴区分の判別する - check phân loại lịch sử	
				String tableName = tableList.get().getTableEnglishName();
				if (tableList.get().getHistoryCls().value == 1) {
					deleteEmployeeHistory(tableList,employeeId, tableName, whereCid, whereEmId);
				}
			}

			// sort target data by date - TO DO

			// 対象社員の日付順の処理
			this.crudDataByTable(dataRecoveryTable.getDataRecovery(), employeeId, employeeCode, dataRecoveryProcessId,
					dataRecoveryTable.getFileNameCsv());
			
			// phân biệt DELETE/INSERT error và Setting error
			

		}

		return true;
	}

	@SuppressWarnings("unused")
	public int crudDataByTable(List<List<String>> targetDataTable, String employeeId, String employeeCode,
			String dataRecoveryProcessId, String fileNameCsv) {
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
		cidTable = targetDataHeader.get(0);
		String cidCurrent = AppContexts.user().companyId();
		if (!cidTable.equals(cidCurrent)) {
			cidCurrent = cidTable;
		}

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

		// Xác định phân loại lịch sử - to do

		Optional<PerformDataRecovery> performDataRecovery = performDataRecoveryRepository
				.getPerformDatRecoverById(dataRecoveryProcessId);
		if (performDataRecovery.isPresent() && performDataRecovery.get().getRecoveryMethod().value == 1) {

			// to do

		}

		// update Status domain - to do

		// tìm kiếm data by employee

		Optional<TableList> tableList = performDataRecoveryRepository.getByInternal(fileNameCsv, dataRecoveryProcessId);
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
			if (dataRow.get(1).equals(employeeId)) {
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

				int count = performDataRecoveryRepository.countDataExitTableByVKeyUp(filedWhere, TABLE_NAME);

				if (count == 2) {
					// error return false
				} else if (count == 1) {
					// delete data
					performDataRecoveryRepository.deleteDataExitTableByVkey(filedWhere, TABLE_NAME);
				}

				Map<String, String> dataInsertDB = new HashMap<>();

				for (int j = 0; j < targetDataHeader.size(); j++) {
					if (targetDataHeader.get(j).equals(COLUMN_NAME_CID)) {
						dataInsertDB.put(targetDataHeader.get(j), cidCurrent);
					} else {
						dataInsertDB.put(targetDataHeader.get(j), dataRow.get(j));
					}
				}
				// insert data
				performDataRecoveryRepository.insertDataTable(dataInsertDB, TABLE_NAME);
			}
		}

		return 0;
	}

	public void deleteEmployeeHistory(Optional<TableList> tableList, String employeeId, String tableName, Boolean whereCid, Boolean whereEmId) {
		// Delete history
		
		if (tableList.get().getClsKeyQuery1().equals("0") || tableList.get().getClsKeyQuery2().equals("0")
				|| tableList.get().getClsKeyQuery3().equals("0")
				|| tableList.get().getClsKeyQuery4().equals("0")
				|| tableList.get().getClsKeyQuery5().equals("0")
				|| tableList.get().getClsKeyQuery6().equals("0")
				|| tableList.get().getClsKeyQuery7().equals("0")
				|| tableList.get().getClsKeyQuery8().equals("0")
				|| tableList.get().getClsKeyQuery9().equals("0")
				|| tableList.get().getClsKeyQuery10().equals("0")) {
			whereCid = true;
		}
		if (tableList.get().getClsKeyQuery1().equals("5") || tableList.get().getClsKeyQuery2().equals("5")
				|| tableList.get().getClsKeyQuery3().equals("5")
				|| tableList.get().getClsKeyQuery4().equals("5")
				|| tableList.get().getClsKeyQuery5().equals("5")
				|| tableList.get().getClsKeyQuery6().equals("5")
				|| tableList.get().getClsKeyQuery7().equals("5")
				|| tableList.get().getClsKeyQuery8().equals("5")
				|| tableList.get().getClsKeyQuery9().equals("5")
				|| tableList.get().getClsKeyQuery10().equals("5")) {
			whereEmId = true;
		}
		
		String cidCurrent = AppContexts.user().companyId();
		performDataRecoveryRepository.deleteEmployeeHis(tableName, whereCid, whereEmId, cidCurrent, employeeId);
	}

	public void exCurrentCategory(List<TableListByCategory> tableListByCategory, String uploadId,
			String dataRecoveryProcessId) {

		Boolean check = false;

		// カテゴリの中の社員単位の処理
		exCurrentTable(tableListByCategory, dataRecoveryProcessId, uploadId);

		if (check) {
			// の処理対象社員コードをクリアする
			dataRecoveryMngRepository.updateProcessTargetEmpCode(dataRecoveryProcessId, null);

			// カテゴリの中の日付単位の処理 - TO DO

		} else {

			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId, 1);
		}

	}

	public void exCurrentTable(List<TableListByCategory> tableListByCategory, String dataRecoveryProcessId,
			String uploadId) {

		Boolean check = false;
		List<DataRecoveryTable> targetDataByCate = new ArrayList<>();
		for (TableListByCategory currentTableByCategory : tableListByCategory) {

			// カテゴリ単位の復旧
			if (currentTableByCategory.getTables().size() > 0) {

				// -- Get List data từ file CSV
				// Create [対象データ] TargetData
				Set<String> hashId = new HashSet<>();
				for (int j = 0; j < currentTableByCategory.getTables().size(); j++) {
					List<List<String>> dataRecovery = FileUtil
							.getAllRecord(currentTableByCategory.getTables().get(j).getInternalFileName(), uploadId, 3);

					// -- Tổng hợp ID Nhân viên duy nhất từ List Data
					for (List<String> dataRow : dataRecovery) {
						hashId.add(dataRow.get(1));
					}

					DataRecoveryTable targetData = new DataRecoveryTable(dataRecovery,
							currentTableByCategory.getTables().get(j).getInternalFileName());
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
					this.recoveryDataByEmployee(dataRecoveryProcessId, employeeDataMngInfoImport.getEmployeeCode(),
							employeeDataMngInfoImport.getEmployeeId(), targetDataByCate);

					// phan biet error - TO DO

					if (check) {
						// update trạng thái dataRecoveryMngRepository - TO
						// DO
					} else {
						// error - TO DO
					}

				}

			}

		}
	}
	
	
	public List<String> settingDate(List<String> rowData, Optional<TableList> tableList) {
		
		// 「テーブル一覧」の抽出キー区から日付項目を設定する
		List<String> checkKeyQuery = new ArrayList<>();
		List<String> resultsSetting = new ArrayList<>();
		Integer timeStore = null ;
		int count6 =0, count7 =0, count8 =0;
		if(tableList.isPresent()) {
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
			if(keyQuery.equals("6")) {
				count6 ++;
			} else if (keyQuery.equals("7")) {
				count7 ++;
			} else if (keyQuery.equals("8")) {
				count8 ++;
			}
		}
		
		// không date
		if(count6 == 0 && count7 == 0 && count8 == 0) {
			resultsSetting.add("-9");
		} else if (count6 != 0 && count7 == 0 && count8 == 0) {
			// năm hoặc phạm vi năm
			resultsSetting.add("6");
			if(count6 == 2) {
				resultsSetting.add("6");
			}
		} else if (count6 == 0 && count7 != 0 && count8 == 0) {
			// tháng năm hoặc là phạm vi tháng năm
			resultsSetting.add("7");
			if(count6 == 2) {
				resultsSetting.add("7");
			}
		} else if (count6 == 0 && count7 == 0 && count8 != 0) {
			// ngày tháng năm hoặc phạm vi ngày tháng năm
			resultsSetting.add("8");
			if(count6 == 2) {
				resultsSetting.add("8");
			}
		}
		
		// 保存期間区分と日付設定を判別
		if(timeStore == 0 && !resultsSetting.get(0).equals("-9") || timeStore == 1 && !resultsSetting.get(0).equals("6")
				|| timeStore == 2 && !resultsSetting.get(0).equals("7") || timeStore == 2 && !resultsSetting.get(0).equals("8")) {
			resultsSetting.clear();
			return resultsSetting;
		}
		
		return resultsSetting;
		
	}
	
}
