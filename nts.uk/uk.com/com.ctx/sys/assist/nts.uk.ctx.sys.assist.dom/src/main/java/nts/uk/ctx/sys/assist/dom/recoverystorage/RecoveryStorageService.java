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
	
	
	

	public static final String STORAGE_RANGE_SAVED = "Employee_unit";

	public void RecoveryStorage(String dataRecoveryProcessId) {
		
		Boolean check = true;
		Optional<PerformDataRecovery> performRecoveries = performDataRecoveryRepository.getPerformDatRecoverById(dataRecoveryProcessId);
		
		List<Category> listCategory = categoryRepository.findById(dataRecoveryProcessId, "1");
		List<TableListByCategory> tableListByCategory = new ArrayList<>();
		for (int i = 0; i < listCategory.size(); i++) {
			List<TableList> tables = performDataRecoveryRepository.getByStorageRangeSaved(listCategory.get(i).getCategoryId().v(), STORAGE_RANGE_SAVED);
			TableListByCategory tableCategory = new TableListByCategory(listCategory.get(i).getCategoryId().v(), tables);
			tableListByCategory.add(tableCategory);
		}
		
		// update OperatingCondition
		dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,0);
		
		// 
		for (TableListByCategory tableCategory : tableListByCategory) {
			if(tableCategory.getTables().size() > 0) {
				
			// -- Get List data từ file CSV
				//to do
				List<DataRecoveryTable> targetDataByCate = new ArrayList<>();
				
			// -- Tổng hợp ID Nhân viên duy nhất từ List Data 
				
				Set<String> hashId = new HashSet<>();
				List<EmployeeDataReInfoImport> employeeInfos =  new ArrayList<>();
				Iterator<String> it = hashId.iterator();
				while(it.hasNext()) {
					Optional<EmployeeDataReInfoImport> employeeInfo = empDataMngRepo.getSdataMngInfo(it.next());
					employeeInfo.ifPresent(x->{
						employeeInfos.add(x);
					});
			     }
				
				
				for (EmployeeDataReInfoImport employeeDataMngInfoImport : employeeInfos) {
					
					dataRecoveryMngRepository.updateProcessTargetEmpCode(dataRecoveryProcessId, employeeDataMngInfoImport.getEmployeeCode());
					
					if(this.recoveryDataByEmployee(dataRecoveryProcessId, employeeDataMngInfoImport.getEmployeeCode(),employeeDataMngInfoImport.getEmployeeId(), targetDataByCate)) {
						
						
						
						
					}
					
				}
				
			} else {
				// error
				// to do
			}
			dataRecoveryMngRepository.updateTotalNumOfProcesses(dataRecoveryProcessId, 0);
		}
		
		
		if(check) {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,3);
		} else {
			dataRecoveryMngRepository.updateByOperatingCondition(dataRecoveryProcessId,1);
		}
	}
	
	
	
	public boolean recoveryDataByEmployee(String dataRecoveryProcessId, String employeeCode, String employeeId, List<DataRecoveryTable> targetDataByCate) {
		
		Optional<PerformDataRecovery> performDataRecovery = performDataRecoveryRepository.getPerformDatRecoverById(dataRecoveryProcessId);
		if(performDataRecovery.isPresent() && performDataRecovery.get().getRecoveryMethod().value == 1) {
			// check employeeId in Target of PreformDataRecovery
			List<Target> listTarget = performDataRecoveryRepository.findByDataRecoveryId(dataRecoveryProcessId);
			Optional<Target> isExist = listTarget.stream().filter(x -> {
				return employeeId.equals(x.getSid());
			}).findFirst();
			if(!isExist.isPresent()) {
				return false;
			}
			
		}
		
		for (DataRecoveryTable dataRecoveryTable : targetDataByCate) {
			
						// check date - to do
			
						// check phân loại lịch sử - to do
						
						// xoa phân loại lịch sử - to do
						
						// check date
			
		}
		
		
		
		
		return true;
	}
	
	
	
	public void crudDataByTable(List<List<String>> targetDataTable, String employeeId, String employeeCode, String dataRecoveryProcessId, String fileNameCsv) {
		
		
		// Xác định phân loại lịch sử - to do
		
		Optional<PerformDataRecovery> performDataRecovery = performDataRecoveryRepository.getPerformDatRecoverById(dataRecoveryProcessId);
		if(performDataRecovery.isPresent() && performDataRecovery.get().getRecoveryMethod().value == 1) {
			
			// to do
			
		}
		
		
		// update Status domain - to do
		
		// count Data in file csv

		if (countDataByCsv(targetDataTable, employeeId, employeeCode, dataRecoveryProcessId, fileNameCsv) == 0) {

		} else if (countDataByCsv(targetDataTable, employeeId, employeeCode, dataRecoveryProcessId,
				fileNameCsv) == 1) {

		} else if (countDataByCsv(targetDataTable,employeeId, employeeCode, dataRecoveryProcessId,
				fileNameCsv) == 2) {

		}

	}
	
	@SuppressWarnings("unused")
	public int countDataByCsv (List<List<String>> targetDataTable, String employeeId, String employeeCode, String dataRecoveryProcessId, String fileNameCsv) {
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
		if(!cidTable.equals(cidCurrent)) {
			cidTable = cidCurrent;
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
		
		// tìm kiếm data by employee
		
		Optional<TableList> tableList = performDataRecoveryRepository.getByInternal(fileNameCsv, dataRecoveryProcessId);
		if(tableList.isPresent()) {
			 
			 TABLE_NAME = tableList.get().getTableEnglishName();
			 FILED_KEY_UPDATE_1 = tableList.get().getFiledKeyUpdate1();
			 
			 if(FILED_KEY_UPDATE_1 != null) {
				 indexUpdate1 = targetDataHeader.indexOf(FILED_KEY_UPDATE_1);
			 }
			 FILED_KEY_UPDATE_2 = tableList.get().getFiledKeyUpdate2();
			 if(FILED_KEY_UPDATE_2 != null) {
				 indexUpdate2 = targetDataHeader.indexOf(FILED_KEY_UPDATE_2);
			 }
			 FILED_KEY_UPDATE_3 = tableList.get().getFiledKeyUpdate3();
			 if(FILED_KEY_UPDATE_3 != null) {
				 indexUpdate3 = targetDataHeader.indexOf(FILED_KEY_UPDATE_3);
			 }
				
			 FILED_KEY_UPDATE_4 = tableList.get().getFiledKeyUpdate4();
			 if(FILED_KEY_UPDATE_4 != null) {
				 indexUpdate4 = targetDataHeader.indexOf(FILED_KEY_UPDATE_4);
			 }
				 
			 FILED_KEY_UPDATE_5 = tableList.get().getFiledKeyUpdate5();
			 if(FILED_KEY_UPDATE_5 != null) {
				 indexUpdate5 = targetDataHeader.indexOf(FILED_KEY_UPDATE_5);
			 }
				 
			 FILED_KEY_UPDATE_6 = tableList.get().getFiledKeyUpdate6();
			 if(FILED_KEY_UPDATE_6 != null) {
				 indexUpdate6 = targetDataHeader.indexOf(FILED_KEY_UPDATE_6);
			 }
				 
			 FILED_KEY_UPDATE_7 = tableList.get().getFiledKeyUpdate7();
			 if(FILED_KEY_UPDATE_7 != null) {
				 indexUpdate7 = targetDataHeader.indexOf(FILED_KEY_UPDATE_7);
			 }
				 
			 FILED_KEY_UPDATE_8 = tableList.get().getFiledKeyUpdate8();
			 if(FILED_KEY_UPDATE_8 != null) {
				 indexUpdate8 = targetDataHeader.indexOf(FILED_KEY_UPDATE_8);
			 }
				 
			 FILED_KEY_UPDATE_9 = tableList.get().getFiledKeyUpdate9();
			 if(FILED_KEY_UPDATE_9 != null) {
				 indexUpdate9 = targetDataHeader.indexOf(FILED_KEY_UPDATE_9);
			 }
				 
			 FILED_KEY_UPDATE_10 = tableList.get().getFiledKeyUpdate10();
			 if(FILED_KEY_UPDATE_10 != null) {
				 indexUpdate10 = targetDataHeader.indexOf(FILED_KEY_UPDATE_10);
			 }
				 
			 FILED_KEY_UPDATE_11 = tableList.get().getFiledKeyUpdate11();
			 if(FILED_KEY_UPDATE_11 != null) {
				 indexUpdate11 = targetDataHeader.indexOf(FILED_KEY_UPDATE_11);
			 }
				 
			 FILED_KEY_UPDATE_12 = tableList.get().getFiledKeyUpdate12();
			 if(FILED_KEY_UPDATE_12 != null) {
				 indexUpdate12 = targetDataHeader.indexOf(FILED_KEY_UPDATE_12);
			 }
				 
			 FILED_KEY_UPDATE_13 = tableList.get().getFiledKeyUpdate13();
			 if(FILED_KEY_UPDATE_13 != null) {
				 indexUpdate13 = targetDataHeader.indexOf(FILED_KEY_UPDATE_13);
			 }
				 
			 FILED_KEY_UPDATE_14 = tableList.get().getFiledKeyUpdate14();
			 if(FILED_KEY_UPDATE_14 != null)
				 indexUpdate14 = targetDataHeader.indexOf(FILED_KEY_UPDATE_14);
			 FILED_KEY_UPDATE_15 = tableList.get().getFiledKeyUpdate15();
			 if(FILED_KEY_UPDATE_15 != null)
				 indexUpdate15 = targetDataHeader.indexOf(FILED_KEY_UPDATE_15);
			 FILED_KEY_UPDATE_16 = tableList.get().getFiledKeyUpdate16();
			 if(FILED_KEY_UPDATE_16 != null)
				 indexUpdate16 = targetDataHeader.indexOf(FILED_KEY_UPDATE_16);
			 FILED_KEY_UPDATE_17 = tableList.get().getFiledKeyUpdate17();
			 if(FILED_KEY_UPDATE_17 != null)
				 indexUpdate17 = targetDataHeader.indexOf(FILED_KEY_UPDATE_17);
			 FILED_KEY_UPDATE_18 = tableList.get().getFiledKeyUpdate18();
			 if(FILED_KEY_UPDATE_18 != null)
				 indexUpdate18 = targetDataHeader.indexOf(FILED_KEY_UPDATE_18);
			 FILED_KEY_UPDATE_19 = tableList.get().getFiledKeyUpdate19();
			 if(FILED_KEY_UPDATE_19 != null)
				 indexUpdate19 = targetDataHeader.indexOf(FILED_KEY_UPDATE_19);
			 FILED_KEY_UPDATE_20 = tableList.get().getFiledKeyUpdate20();
			 if(FILED_KEY_UPDATE_20 != null)
				 indexUpdate20 = targetDataHeader.indexOf(FILED_KEY_UPDATE_20);
		}
		
		for (int i = 1; i < targetDataTable.size(); i++) {
			
			Map<String, String> filedWhere = new HashMap<>();
			List<String> dataRow = targetDataTable.get(i);
			if(indexUpdate1 != null) {
				V_FILED_KEY_UPDATE_1 = dataRow.get(indexUpdate1);
				filedWhere.put(FILED_KEY_UPDATE_1, V_FILED_KEY_UPDATE_1);
			}
				
			if(indexUpdate2 != null) {
				V_FILED_KEY_UPDATE_2 = dataRow.get(indexUpdate2);
				filedWhere.put(FILED_KEY_UPDATE_2, V_FILED_KEY_UPDATE_2);
			}
				
			if(indexUpdate3 != null) {
				V_FILED_KEY_UPDATE_3 = dataRow.get(indexUpdate3);
				filedWhere.put(FILED_KEY_UPDATE_3, V_FILED_KEY_UPDATE_3);
			}
				
			if(indexUpdate4 != null) {
				V_FILED_KEY_UPDATE_4 = dataRow.get(indexUpdate4);
				filedWhere.put(FILED_KEY_UPDATE_4, V_FILED_KEY_UPDATE_4);
			}
				
			if(indexUpdate5 != null) {
				V_FILED_KEY_UPDATE_5 = dataRow.get(indexUpdate5);
				filedWhere.put(FILED_KEY_UPDATE_5, V_FILED_KEY_UPDATE_5);
			}
				
			if(indexUpdate6 != null) {
				V_FILED_KEY_UPDATE_6 = dataRow.get(indexUpdate6);
				filedWhere.put(FILED_KEY_UPDATE_6, V_FILED_KEY_UPDATE_6);
			}
				
			if(indexUpdate7 != null) {
				V_FILED_KEY_UPDATE_7 = dataRow.get(indexUpdate7);
				filedWhere.put(FILED_KEY_UPDATE_7, V_FILED_KEY_UPDATE_7);
			}
				
			if(indexUpdate8 != null) {
				V_FILED_KEY_UPDATE_8 = dataRow.get(indexUpdate8);
				filedWhere.put(FILED_KEY_UPDATE_8, V_FILED_KEY_UPDATE_8);
			}
				
			if(indexUpdate9 != null) {
				V_FILED_KEY_UPDATE_9 = dataRow.get(indexUpdate9);
				filedWhere.put(FILED_KEY_UPDATE_9, V_FILED_KEY_UPDATE_9);
			}
				
			if(indexUpdate10 != null) {
				V_FILED_KEY_UPDATE_10 = dataRow.get(indexUpdate10);
				filedWhere.put(FILED_KEY_UPDATE_10, V_FILED_KEY_UPDATE_10);
			}
				
			if(indexUpdate11 != null) {
				V_FILED_KEY_UPDATE_11 = dataRow.get(indexUpdate11);
				filedWhere.put(FILED_KEY_UPDATE_11, V_FILED_KEY_UPDATE_11);
			}
				
			if(indexUpdate12 != null) {
				V_FILED_KEY_UPDATE_12 = dataRow.get(indexUpdate12);
				filedWhere.put(FILED_KEY_UPDATE_12, V_FILED_KEY_UPDATE_12);
			}
				
			if(indexUpdate13 != null) {
				V_FILED_KEY_UPDATE_13 = dataRow.get(indexUpdate13);
				filedWhere.put(FILED_KEY_UPDATE_13, V_FILED_KEY_UPDATE_13);
			}
				
			if(indexUpdate14 != null) {
				V_FILED_KEY_UPDATE_14 = dataRow.get(indexUpdate14);
				filedWhere.put(FILED_KEY_UPDATE_14, V_FILED_KEY_UPDATE_14);
			}
				
			if(indexUpdate15 != null) {
				V_FILED_KEY_UPDATE_15 = dataRow.get(indexUpdate15);
				filedWhere.put(FILED_KEY_UPDATE_15, V_FILED_KEY_UPDATE_15);
			}
				
			if(indexUpdate16 != null) {
				V_FILED_KEY_UPDATE_16 = dataRow.get(indexUpdate16);
				filedWhere.put(FILED_KEY_UPDATE_16, V_FILED_KEY_UPDATE_16);
			}
				
			if(indexUpdate17 != null) {
				V_FILED_KEY_UPDATE_17 = dataRow.get(indexUpdate17);
				filedWhere.put(FILED_KEY_UPDATE_17, V_FILED_KEY_UPDATE_17);
			}
				
			if(indexUpdate18 != null) {
				V_FILED_KEY_UPDATE_18 = dataRow.get(indexUpdate18);
				filedWhere.put(FILED_KEY_UPDATE_18, V_FILED_KEY_UPDATE_18);
			}
				
			if(indexUpdate19 != null) {
				V_FILED_KEY_UPDATE_19 = dataRow.get(indexUpdate19);
				filedWhere.put(FILED_KEY_UPDATE_19, V_FILED_KEY_UPDATE_19);
			}
				
			if(indexUpdate20 != null) {
				V_FILED_KEY_UPDATE_20 = dataRow.get(indexUpdate20);
				filedWhere.put(FILED_KEY_UPDATE_20, V_FILED_KEY_UPDATE_20);
			}
				
			int count = performDataRecoveryRepository.countDataExitTableByVKeyUp(filedWhere,TABLE_NAME);
			
			if(count == 2) {
				// error return false
			} else if (count == 1) {
				// delete data
				performDataRecoveryRepository.deleteDataExitTableByVkey(filedWhere, TABLE_NAME);
			}
			
			Map<String, String> dataInsertDB = new HashMap<>();
			
			for (int j = 0; j < targetDataHeader.size(); j++) {
				dataInsertDB.put(targetDataHeader.get(j), dataRow.get(j));
			}
			
			performDataRecoveryRepository.insertDataTable(dataInsertDB, TABLE_NAME);
			
		}
		
		
		return 0;
	}
	
}
