/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.CategoryDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.CategoryDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionCsvRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.DelType;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeesDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.FileName;
import nts.uk.ctx.sys.assist.dom.deletedata.ManagementDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManagementDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.OperatingCondition;
import nts.uk.ctx.sys.assist.dom.deletedata.Result;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultState;
import nts.uk.ctx.sys.assist.dom.deletedata.SaveStatus;
import nts.uk.ctx.sys.assist.dom.deletedata.TableDeletionDataCsv;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

/**
 * @author hiep.th
 *
 */
@Stateless
public class ManualSetDeletionService extends ExportService<Object>{
	private static final String MSG_START_DEL_LOG = "CMF005_213";
	private static final String MSG_END_NORMAL_DEL_LOG = "CMF005_214";
	private static final String MSG_END_ABNORMAL_DEL_LOG = "CMF005_215";
	private static final String MSG_END_TERMINATE_DEL_LOG = "CMF005_216";
	private static final String MSG_DEL_ERROR_LOG = "CMF005_223";

	private static final List<String> LST_NAME_ID_HEADER_TABLE_FILE = Arrays.asList("CMF003_500", "CMF003_501",
			"CMF003_502", "CMF003_503", "CMF003_504", "CMF003_505", "CMF003_506", "CMF003_507", "CMF003_508",
			"CMF003_509", "CMF003_510", "CMF003_511", "CMF003_512", "CMF003_513", "CMF003_514", "CMF003_515",
			"CMF003_516", "CMF003_517", "CMF003_585", "CMF003_586", "CMF003_587", "CMF003_588", "CMF003_589",
			"CMF003_590", "CMF003_591", "CMF003_592", "CMF003_593", "CMF003_594", "CMF003_595", "CMF003_596",
			"CMF003_597", "CMF003_598", "CMF003_599", "CMF003_600", "CMF003_601", "CMF003_602", "CMF003_603",
			"CMF003_604", "CMF003_605", "CMF003_606", "CMF003_607", "CMF003_608", "CMF003_609", "CMF003_610",
			"CMF003_611", "CMF003_612", "CMF003_518", "CMF003_519", "CMF003_520", "CMF003_521", "CMF003_522",
			"CMF003_523", "CMF003_524", "CMF003_525", "CMF003_526", "CMF003_527", "CMF003_528", "CMF003_529",
			"CMF003_530", "CMF003_531", "CMF003_532", "CMF003_533", "CMF003_534", "CMF003_535", "CMF003_536",
			"CMF003_537", "CMF003_538", "CMF003_539", "CMF003_540", "CMF003_541", "CMF003_542", "CMF003_543",
			"CMF003_544", "CMF003_545", "CMF003_546", "CMF003_547", "CMF003_548", "CMF003_549", "CMF003_550",
			"CMF003_551", "CMF003_552", "CMF003_553", "CMF003_554", "CMF003_555", "CMF003_556", "CMF003_557",
			"CMF003_558", "CMF003_559", "CMF003_560", "CMF003_561", "CMF003_562", "CMF003_563", "CMF003_564",
			"CMF003_565", "CMF003_566", "CMF003_567", "CMF003_568", "CMF003_569", "CMF003_570", "CMF003_571",
			"CMF003_572", "CMF003_573", "CMF003_574", "CMF003_575", "CMF003_576", "CMF003_577", "CMF003_578",
			"CMF003_579", "CMF003_580", "CMF003_581", "CMF003_582", "CMF003_583", "CMF003_584");

	private static final List<String> LST_NAME_ID_HEADER_EMPLOYEE_FILE = Arrays.asList("SID", "SCD", "BUSINESS_NAME");
	private static final List<String> LST_NAME_HEADER_FIX_DATA_FILE = Arrays.asList("CMF003_620", "CMF003_621", "CMF003_622",
			"CMF003_623", "CMF003_624");

	private static final String TABLE_NAME_FILE = "保存対象テーブル一覧";
	private static final String EMPLOYEE_NAME_FILE = "対象社員";
	private static final String FILE_EXTENSION = ".csv";
	private static final String ZIP_FILE_EXTENSION = ".zip";

	@Inject
	private ManagementDeletionRepository repoManagementDel;
	@Inject
	private EmployeesDeletionRepository repoEmployeesDel;
	@Inject
	private CategoryDeletionRepository repoCategoryDel;
//	@Inject
//	private ManualSetDeletionRepository repoManualSetDel;
	@Inject
	private CategoryRepository repoCategory;
	@Inject
	private ResultDeletionRepository repoResultDel;
	@Inject
	private ResultLogDeletionRepository repoResultLogDel;
	@Inject
	private DataDeletionCsvRepository repoCsv;
	@Inject
	private ApplicationTemporaryFileFactory applicationTemporaryFileFactory;
	@Inject
	private CSVReportGenerator generator;
	
	@Override
	protected void handle(ExportServiceContext<Object> context) {
		ManualSetDeletion domain = (ManualSetDeletion) context.getQuery();
//		String delId = domain.getDelId();
		serverManualDelProcessing(domain, context.getGeneratorContext());
	}

	/**
	 * 
	 * @param delId
	 * @param generatorContext
	 */
	public void serverManualDelProcessing(ManualSetDeletion domain, FileGeneratorContext generatorContext) {
		// ドメインモデル「データ削除の手動設定」を読み込む
//		Optional<ManualSetDeletion> manualSetDeletion = repoManualSetDel.getManualSetDeletionById(delId);
		if (domain != null) {
			String delId = domain.getDelId();
//			ManualSetDeletion domain = manualSetDeletion.get();

			// ドメインモデル「データ削除の保存結果」へ追加する
			saveStartResultDel(domain);

			// ドメインモデル「データ削除の結果ログ」へ追加
			saveStartResultLogDel(domain);

			// ドメインモデル「データ削除動作管理」を更新する
			saveStartManagementDel(domain);

			// アルゴリズム「サーバデータ削除テーブル取得」を実行する
			List<Category> categories = getDataDelAgth(delId);
			List<EmployeeDeletion> employeeDeletions = repoEmployeesDel.getEmployeesDeletionListById(delId);
			List<TableDeletionDataCsv> tableDeletionDataCsvs = getTableDeletionData(delId);

			Result resultSave = null;
			if (domain.isSaveBeforeDeleteFlg()) {
				// アルゴリズム「サーバデータ削除保存」を実行する
				resultSave = saveDataDelAgth(generatorContext, delId, domain, tableDeletionDataCsvs, employeeDeletions,
						categories);
				ResultState state = resultSave.getState();
				if (state != ResultState.NORMAL_END) {
					//ドメインモデル「データ削除の結果ログ」へ追加する
					saveEndLogResult(domain, state);
					//ドメインモデル「データ削除の保存結果」を更新する
					saveEndResultDel(domain, resultSave);
					//ドメインモデル「データ削除動作管理」を更新する
					saveEndManagementDel(delId, state);
					
					return;
				}
			}

			// アルゴリズム「サーバデータ削除実行」を実行する
			ResultState resultDelState = deleteDataAgth(delId, domain, tableDeletionDataCsvs, employeeDeletions, categories);
			Result resultDel = new Result(resultDelState, null, null);
			if (resultDelState != ResultState.NORMAL_END) {
				//圧縮ファイルが作成されている場合、圧縮ファイルを削除する
				if (resultSave != null && resultSave.getFile() != null) {
					resultSave.getFile().delete();
				}
			}
			else {
				if (resultSave != null) {
					resultDel.setFile(resultSave.getFile());
					resultDel.setFileId(resultSave.getFileId());
				}
			}
			
			//ドメインモデル「データ削除の結果ログ」へ追加する
			saveEndLogResult(domain, resultDel.getState());
			//ドメインモデル「データ削除の保存結果」を更新する
			saveEndResultDel(domain, resultDel);
			//ドメインモデル「データ削除動作管理」を更新する
			saveEndManagementDel(delId, resultDelState);
		} else {
    		throw new RuntimeException("Don't get setting of deletion.");
		}
	}
	
	

	/**
	 * ドメインモデル「データ削除の保存結果」へ追加する save the starting result of deletion
	 * 
	 * @param domain
	 *            ManualSetDeletion
	 */
	private void saveStartResultDel(ManualSetDeletion domain) {
		GeneralDateTime startDateTimeDel = GeneralDateTime.now();
		int delType = DelType.MANUAL.value;
		int fileSize = 0;
		int numberEmployees = 0;
		ResultDeletion resultDomain = ResultDeletion.createFromJavatype(domain.getDelId(), domain.getCompanyId(),
				domain.getDelName().v(), delType, domain.isSaveBeforeDeleteFlg(), null, numberEmployees,
				domain.getSystemType(), domain.getSId(), SaveStatus.SUCCESS.value, startDateTimeDel, null, null, null,
				fileSize);
		repoResultDel.add(resultDomain);
	}

	/**
	 * ドメインモデル「データ削除の結果ログ」へ追加 save the starting result log of deletion
	 * 
	 * @param domain
	 */
	private void saveStartResultLogDel(ManualSetDeletion domain) {
		GeneralDateTime logTime = GeneralDateTime.now();
		ResultLogDeletion resultLogDomain = ResultLogDeletion.createFromJavatype(0, domain.getDelId(),
				domain.getCompanyId(), logTime, TextResource.localize(MSG_START_DEL_LOG), null, null, null);
		repoResultLogDel.add(resultLogDomain);
	}

	/**
	 * ドメインモデル「データ削除動作管理」を更新する
	 * 
	 * @param domain
	 */
	private void saveStartManagementDel(ManualSetDeletion domain) {
		int totalCategoryCount = 0;
		int categoryCount = 0;
		int errorCount = 0;
		OperatingCondition operatingCondition = OperatingCondition.INPREPARATION;
		boolean isInterruptedFlg = false;
		ManagementDeletion managementDomain = ManagementDeletion.createFromJavatype(domain.getDelId(), isInterruptedFlg,
				totalCategoryCount, categoryCount, errorCount, operatingCondition.value);
		repoManagementDel.add(managementDomain);
	}

	
	
	/**
	 * ドメインモデル「データ削除動作管理」を更新する
	 * @param domain
	 * @param state
	 */
	private void saveEndManagementDel(String delId, ResultState state) {
		OperatingCondition operatingCondition = OperatingCondition.DONE;
		
		if (state == ResultState.ABNORMAL_END) {
			operatingCondition = OperatingCondition.ABNORMAL_TERMINATION;
		}
		else if (state == ResultState.TERMINATE) {
			operatingCondition = OperatingCondition.INTERRUPTION_END;
		}
		
		repoManagementDel.updateOperationCond(delId, operatingCondition);
	}
	/**
	 * ドメインモデル「データ削除の結果ログ」へ追加する
	 */
	private void saveEndLogResult(ManualSetDeletion domain, ResultState state) {
		String msgId = MSG_END_NORMAL_DEL_LOG;
		if (state == ResultState.ABNORMAL_END) {
			msgId = MSG_END_ABNORMAL_DEL_LOG;
		} else if (state == ResultState.TERMINATE) {
			msgId = MSG_END_TERMINATE_DEL_LOG;
		}
		GeneralDateTime logTime = GeneralDateTime.now();
		int seqId = repoResultLogDel.getMaxSeqId(domain.getDelId()) + 1;
		ResultLogDeletion resultLogDomain = ResultLogDeletion.createFromJavatype(seqId, domain.getDelId(),
				domain.getCompanyId(), logTime, TextResource.localize(msgId), null, null, null);
		repoResultLogDel.add(resultLogDomain);
	}
	
	/**
	 * ドメインモデル「データ削除の保存結果」を更新する
	 * Update domain model 「データ削除の保存結果」
	 */
	private void saveEndResultDel(ManualSetDeletion domain, Result result) {
		Optional<ResultDeletion> optResultDel = repoResultDel.getResultDeletionById(domain.getDelId());
		if (optResultDel.isPresent()) {
			String fileName = null;
			int fileSize = 0;
			String fileId = null;
			
			ResultState state = result.getState();
			SaveStatus status = SaveStatus.SUCCESS;
			if (state == ResultState.ABNORMAL_END) {
				status = SaveStatus.FAILURE;
			}
			else if (state == ResultState.TERMINATE) {
				status = SaveStatus.INTERRUPTION;
			}
			GeneralDateTime endDateTimeDel = GeneralDateTime.now();
			File file = result.getFile();
			if (file != null) {
				fileName = file.getName();
				fileSize = (int)file.length();
				fileId = result.getFileId();
			}
			
			ResultDeletion resultDel = optResultDel.get();
			resultDel.setStatus(status);
			resultDel.setEndDateTimeDel(endDateTimeDel);
			resultDel.setFileName(new FileName(fileName));
			resultDel.setFileSize(fileSize);
			resultDel.setFileId(fileId);
			repoResultDel.update(resultDel);
		}
	}

	/**
	 * アルゴリズム「サーバデータ削除テーブル取得」を実行する
	 */
	private List<Category> getDataDelAgth(String delId) {
		// Get list category from
		List<CategoryDeletion> categoryDeletions = repoCategoryDel.getCategoryDeletionListById(delId);
		List<String> categoryIds = categoryDeletions.stream().map(x -> {
			return x.getCategoryId();
		}).collect(Collectors.toList());
		List<Category> categorys = repoCategory.getCategoryByListId(categoryIds);
		// List<CategoryFieldMt> categoryFieldMts =
		// repoCateField.getCategoryFieldMtByListId(categoryIds);

		// update domain 「データ保存動作管理」 Data operation management
		int totalCount = categorys.size();
		repoManagementDel.updateTotalCatCount(delId, totalCount);

		return categorys;
	}

	/**
	 * アルゴリズム「サーバデータ削除保存」を実行する save data deletion to file
	 * 
	 * @param generatorContext
	 * @param delId
	 * @param domain
	 * @param employeeDeletions
	 * @param categories
	 */
	private Result saveDataDelAgth(FileGeneratorContext generatorContext, String delId, ManualSetDeletion domain,
			List<TableDeletionDataCsv> tableDeletionDataCsvs, List<EmployeeDeletion> employeeDeletions,
			List<Category> categories) {
	
		File file = null;
		// アルゴリズム「サーバデータ削除保存ファイル」を実行する
		ResultState resultSaving = saveDataDelToCsvAgth(generatorContext, tableDeletionDataCsvs, delId, domain,
				employeeDeletions, categories);
		if (resultSaving == ResultState.NORMAL_END) {
			// アルゴリズム「サーバデータ削除保存圧縮」を実行する
			file = zipFolderDataAgth(generatorContext, domain);
			if (file == null) {
				return new Result(ResultState.ABNORMAL_END, null, null);
			}
			else {
				String fileId = generatorContext.getTaskId();
				return new Result(ResultState.NORMAL_END, file, fileId);
			}
		} else {
			return new Result(resultSaving, null, null);
		}
	}

	/**
	 * アルゴリズム「サーバデータ削除保存ファイル」を実行する
	 */
	private ResultState saveDataDelToCsvAgth(FileGeneratorContext generatorContext,
			List<TableDeletionDataCsv> tableDeletionDataCsvs, String delId, ManualSetDeletion domain,
			List<EmployeeDeletion> employeeDeletions, List<Category> categories) {
		try {
			// Update domain model 「データ削除動作管理」
			repoManagementDel.updateCatCountAnCond(delId, 0, OperatingCondition.SAVING);

			// テーブル一覧の内容をCSVファイルに書き出す Add Table to CSV
			generalTableDeletionToCsv(generatorContext, tableDeletionDataCsvs);

			// 対象社員の内容をCSVファイルに暗号化して書き出す
			generalEmployeesToCsv(generatorContext, delId);

			Map<String, List<TableDeletionDataCsv>> mapCatWithDatas = mapCatWithDataDel(tableDeletionDataCsvs);
			if (mapCatWithDatas != null) {
				int categoryCount = 0;
				for (Category category : categories) {
					categoryCount++;
					
					// ドメインモデル「データ削除動作管理」を更新する
					repoManagementDel.updateCatCount(delId, categoryCount);

					// ドメインモデル「データ削除動作管理.中断するしない」を確認
					Optional<ManagementDeletion> maOptional = repoManagementDel.getManagementDeletionById(delId);
					if (maOptional.isPresent()) {
						if (maOptional.get().isInterruptedFlg()) {
							return ResultState.TERMINATE;
						}
					} else {
						return ResultState.ABNORMAL_END;
					}
					
					String categoryId = category.getCategoryId().v();
					List<TableDeletionDataCsv> catDatas = mapCatWithDatas.get(categoryId);
					if (catDatas != null) {
						for (TableDeletionDataCsv tableDataDel : catDatas) {
							// アルゴリズム「サーバデータ削除テーブルデータ書出」を実行する
							ResultState generalResult = generalDataForCategoryToCsv(generatorContext, domain,
									employeeDeletions, categories, tableDataDel);
							if (generalResult == ResultState.ABNORMAL_END) {
								ManagementDeletion managementDeletion = maOptional.get();
								int errorCount = managementDeletion.getErrorCount();
								managementDeletion.setErrorCount(errorCount + 1);
								repoManagementDel.update(managementDeletion);
//								return ResultState.ABNORMAL_END;
							}
						}
					}
				}
			}
			else {
				return ResultState.ABNORMAL_END;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.ABNORMAL_END;
		}

		return ResultState.NORMAL_END;
	}
	
	/**
	 * 
	 * @param categories
	 * @param tableDeletionDataCsvs
	 * @return
	 */
	private Map<String, List<TableDeletionDataCsv>> mapCatWithDataDel(List<TableDeletionDataCsv> tableDeletionDataCsvs) {
		Map<String, List<TableDeletionDataCsv>> mapCatWithDatas = new HashMap<>();
		
		for (TableDeletionDataCsv tableDataDel : tableDeletionDataCsvs) {
			String categoryId = tableDataDel.getCategoryId();
			
			List<TableDeletionDataCsv> catDatas = mapCatWithDatas.get(categoryId);
			if (catDatas == null) {
				catDatas = new ArrayList<>();
				catDatas.add(tableDataDel);
				mapCatWithDatas.put(categoryId, catDatas);
			}
			else {
				catDatas.add(tableDataDel);
			}
		}
		
		return mapCatWithDatas;
	}

	/**
	 * アルゴリズム「サーバデータ削除テーブルデータ書出」を実行する
	 */
	private ResultState generalDataForCategoryToCsv(FileGeneratorContext generatorContext, ManualSetDeletion domain,
			List<EmployeeDeletion> employeeDeletions, List<Category> categories, TableDeletionDataCsv tableDataDel) {
		try {
			String nameFile = tableDataDel.getCompanyId() + tableDataDel.getCategoryName()
					+ tableDataDel.getTableJapanName() + FILE_EXTENSION;
			List<List<String>> dataRecords = repoCsv.getDataForEachCaegory(tableDataDel, employeeDeletions);
			List<String> columNames = repoCsv.getColumnName(tableDataDel.getTableEnglishName());
			List<String> header = getHeaderForDataFile(columNames);

			List<Map<String, Object>> dataSourceCsv = new ArrayList<>();
			for (List<String> record : dataRecords) {
				Map<String, Object> rowCsv = new HashMap<>();
				int i = 0;
				for (String columnName : header) {
					if (record.size() > i) {
						rowCsv.put(columnName, record.get(i));
					}
					else {
						rowCsv.put(columnName, "");
					}
					i++;
				}
				dataSourceCsv.add(rowCsv);
			}

			CSVFileData fileData = new CSVFileData(nameFile, header, dataSourceCsv);
			generator.generate(generatorContext, fileData);

		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.ABNORMAL_END;
		}
		return ResultState.NORMAL_END;
	}

	/**
	 * アルゴリズム「サーバデータ削除保存圧縮」を実行する
	 */
	private File zipFolderDataAgth(FileGeneratorContext generatorContext, ManualSetDeletion domain) {
		// Update domain model 「データ削除動作管理」
		repoManagementDel.updateOperationCond(domain.getDelId(), OperatingCondition.COMPRESSING);

		ApplicationTemporaryFilesContainer applicationTemporaryFilesContainer = applicationTemporaryFileFactory
				.createContainer();
		
		// ファイル圧縮実行
		boolean isExistCompressPassFlg = domain.isExistCompressPassFlg();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String executeDate = sdf.format(new Date()); 
		String nameFile = domain.getCompanyId()+"_" + domain.getDelName() + "_" + executeDate + ZIP_FILE_EXTENSION;
		
		Path compressedFile = null;
		if (!isExistCompressPassFlg) {
			compressedFile = applicationTemporaryFilesContainer.zipWithName(generatorContext, nameFile);
		} else {
			String password = domain.getPasswordCompressFileEncrypt().get().v();
			compressedFile = applicationTemporaryFilesContainer.zipWithName(generatorContext, nameFile,
					new String(Base64.getDecoder().decode(password)));
		}
		
		applicationTemporaryFilesContainer.removeContainer();
		
		if (compressedFile != null) {
			return compressedFile.toFile();
		}
		return null;
	}
	
	/**
	 * 
	 * @param delId
	 * @return
	 */
	private List<TableDeletionDataCsv> getTableDeletionData(String delId) {
		List<TableDeletionDataCsv> dataDeletionCsvs = repoCsv.getTableDelDataCsvById(delId);
		return dataDeletionCsvs;
	}

	/**
	 * 
	 * @param generatorContext
	 * @param dataDeletionCsvs
	 * @return
	 */
	private CSVFileData generalTableDeletionToCsv(FileGeneratorContext generatorContext,
			List<TableDeletionDataCsv> dataDeletionCsvs) {
		List<String> headerCsv = this.getHeaderForTableDelFile();
		// Get data from Manual Setting table
		List<Map<String, Object>> dataSourceCsv = new ArrayList<>();
		for (TableDeletionDataCsv dataTarget : dataDeletionCsvs) {
			Map<String, Object> rowCsv = new HashMap<>();
			rowCsv.put(headerCsv.get(0), dataTarget.getDelId());
			rowCsv.put(headerCsv.get(1), dataTarget.getDelType());
			rowCsv.put(headerCsv.get(2), dataTarget.getDelCode());
			rowCsv.put(headerCsv.get(3), dataTarget.getDelName());
			rowCsv.put(headerCsv.get(4), dataTarget.getSupplementExplanation());
			rowCsv.put(headerCsv.get(5), dataTarget.getCategoryId());
			rowCsv.put(headerCsv.get(6), dataTarget.getCategoryName());
			rowCsv.put(headerCsv.get(7), dataTarget.getTimeStore());
			rowCsv.put(headerCsv.get(8), dataTarget.getRecoveryStorageRange());
			// CMF003_509,CMF003_510, CMF003_511 missing
			rowCsv.put(headerCsv.get(12), dataTarget.getSaveForInvest());
			rowCsv.put(headerCsv.get(13), dataTarget.getOtherCompanyCls());
			// CMF003_514 missing
			rowCsv.put(headerCsv.get(15), dataTarget.getTableJapanName());
			rowCsv.put(headerCsv.get(16), dataTarget.getTableEnglishName());
			rowCsv.put(headerCsv.get(17), dataTarget.getHistoryCls());

			rowCsv.put(headerCsv.get(85), dataTarget.getHasParentTblFlg());
			rowCsv.put(headerCsv.get(86), dataTarget.getParentTblJapanName());
			rowCsv.put(headerCsv.get(87), dataTarget.getParentTblName());
			rowCsv.put(headerCsv.get(88), dataTarget.getFieldParent1());
			rowCsv.put(headerCsv.get(89), dataTarget.getFieldParent2());
			rowCsv.put(headerCsv.get(90), dataTarget.getFieldParent3());
			rowCsv.put(headerCsv.get(91), dataTarget.getFieldParent4());
			rowCsv.put(headerCsv.get(92), dataTarget.getFieldParent5());
			rowCsv.put(headerCsv.get(93), dataTarget.getFieldParent6());
			rowCsv.put(headerCsv.get(94), dataTarget.getFieldParent7());
			rowCsv.put(headerCsv.get(95), dataTarget.getFieldParent8());
			rowCsv.put(headerCsv.get(96), dataTarget.getFieldParent9());
			rowCsv.put(headerCsv.get(97), dataTarget.getFieldParent10());
			rowCsv.put(headerCsv.get(98), dataTarget.getFieldChild1());
			rowCsv.put(headerCsv.get(99), dataTarget.getFieldChild2());
			rowCsv.put(headerCsv.get(100), dataTarget.getFieldChild3());
			rowCsv.put(headerCsv.get(101), dataTarget.getFieldChild4());
			rowCsv.put(headerCsv.get(102), dataTarget.getFieldChild5());
			rowCsv.put(headerCsv.get(103), dataTarget.getFieldChild6());
			rowCsv.put(headerCsv.get(104), dataTarget.getFieldChild7());
			rowCsv.put(headerCsv.get(105), dataTarget.getFieldChild8());
			rowCsv.put(headerCsv.get(106), dataTarget.getFieldChild9());
			rowCsv.put(headerCsv.get(107), dataTarget.getFieldChild10());
			rowCsv.put(headerCsv.get(108), dataTarget.getFieldAcqCid());
			rowCsv.put(headerCsv.get(109), dataTarget.getFieldAcqEmployeeId());
			rowCsv.put(headerCsv.get(110), dataTarget.getFieldAcqDateTime());
			rowCsv.put(headerCsv.get(111), dataTarget.getFieldAcqStartDate());
			rowCsv.put(headerCsv.get(112), dataTarget.getFieldAcqEndDate());

			rowCsv.put(headerCsv.get(18), dataTarget.getDefaultCondKeyQuery());
			rowCsv.put(headerCsv.get(19), dataTarget.getFieldKeyQuery1());
			rowCsv.put(headerCsv.get(20), dataTarget.getFieldKeyQuery2());
			rowCsv.put(headerCsv.get(21), dataTarget.getFieldKeyQuery3());
			rowCsv.put(headerCsv.get(22), dataTarget.getFieldKeyQuery4());
			rowCsv.put(headerCsv.get(23), dataTarget.getFieldKeyQuery5());
			rowCsv.put(headerCsv.get(24), dataTarget.getFieldKeyQuery6());
			rowCsv.put(headerCsv.get(25), dataTarget.getFieldKeyQuery7());
			rowCsv.put(headerCsv.get(26), dataTarget.getFieldKeyQuery8());
			rowCsv.put(headerCsv.get(27), dataTarget.getFieldKeyQuery9());
			rowCsv.put(headerCsv.get(28), dataTarget.getFieldKeyQuery10());

			rowCsv.put(headerCsv.get(29), dataTarget.getClsKeyQuery1());
			rowCsv.put(headerCsv.get(30), dataTarget.getClsKeyQuery2());
			rowCsv.put(headerCsv.get(31), dataTarget.getClsKeyQuery3());
			rowCsv.put(headerCsv.get(32), dataTarget.getClsKeyQuery4());
			rowCsv.put(headerCsv.get(33), dataTarget.getClsKeyQuery5());
			rowCsv.put(headerCsv.get(34), dataTarget.getClsKeyQuery6());
			rowCsv.put(headerCsv.get(35), dataTarget.getClsKeyQuery7());
			rowCsv.put(headerCsv.get(36), dataTarget.getClsKeyQuery8());
			rowCsv.put(headerCsv.get(37), dataTarget.getClsKeyQuery9());
			rowCsv.put(headerCsv.get(38), dataTarget.getClsKeyQuery10());

			rowCsv.put(headerCsv.get(39), dataTarget.getFiledKeyUpdate1());
			rowCsv.put(headerCsv.get(40), dataTarget.getFiledKeyUpdate2());
			rowCsv.put(headerCsv.get(41), dataTarget.getFiledKeyUpdate3());
			rowCsv.put(headerCsv.get(42), dataTarget.getFiledKeyUpdate4());
			rowCsv.put(headerCsv.get(43), dataTarget.getFiledKeyUpdate5());
			rowCsv.put(headerCsv.get(44), dataTarget.getFiledKeyUpdate6());
			rowCsv.put(headerCsv.get(45), dataTarget.getFiledKeyUpdate7());
			rowCsv.put(headerCsv.get(46), dataTarget.getFiledKeyUpdate8());
			rowCsv.put(headerCsv.get(47), dataTarget.getFiledKeyUpdate9());
			rowCsv.put(headerCsv.get(48), dataTarget.getFiledKeyUpdate10());
			rowCsv.put(headerCsv.get(49), dataTarget.getFiledKeyUpdate11());
			rowCsv.put(headerCsv.get(50), dataTarget.getFiledKeyUpdate12());
			rowCsv.put(headerCsv.get(51), dataTarget.getFiledKeyUpdate13());
			rowCsv.put(headerCsv.get(52), dataTarget.getFiledKeyUpdate14());
			rowCsv.put(headerCsv.get(53), dataTarget.getFiledKeyUpdate15());
			rowCsv.put(headerCsv.get(54), dataTarget.getFiledKeyUpdate16());
			rowCsv.put(headerCsv.get(55), dataTarget.getFiledKeyUpdate17());
			rowCsv.put(headerCsv.get(56), dataTarget.getFiledKeyUpdate18());
			rowCsv.put(headerCsv.get(57), dataTarget.getFiledKeyUpdate19());
			rowCsv.put(headerCsv.get(58), dataTarget.getFiledKeyUpdate20());

			rowCsv.put(headerCsv.get(59), dataTarget.getFieldDate1());
			rowCsv.put(headerCsv.get(60), dataTarget.getFieldDate2());
			rowCsv.put(headerCsv.get(61), dataTarget.getFieldDate3());
			rowCsv.put(headerCsv.get(62), dataTarget.getFieldDate4());
			rowCsv.put(headerCsv.get(63), dataTarget.getFieldDate5());
			rowCsv.put(headerCsv.get(64), dataTarget.getFieldDate6());
			rowCsv.put(headerCsv.get(65), dataTarget.getFieldDate7());
			rowCsv.put(headerCsv.get(66), dataTarget.getFieldDate8());
			rowCsv.put(headerCsv.get(67), dataTarget.getFieldDate9());
			rowCsv.put(headerCsv.get(68), dataTarget.getFieldDate10());
			rowCsv.put(headerCsv.get(69), dataTarget.getFieldDate11());
			rowCsv.put(headerCsv.get(70), dataTarget.getFieldDate12());
			rowCsv.put(headerCsv.get(71), dataTarget.getFieldDate13());
			rowCsv.put(headerCsv.get(72), dataTarget.getFieldDate14());
			rowCsv.put(headerCsv.get(73), dataTarget.getFieldDate15());
			rowCsv.put(headerCsv.get(74), dataTarget.getFieldDate16());
			rowCsv.put(headerCsv.get(75), dataTarget.getFieldDate17());
			rowCsv.put(headerCsv.get(76), dataTarget.getFieldDate18());
			rowCsv.put(headerCsv.get(77), dataTarget.getFieldDate19());

			rowCsv.put(headerCsv.get(78), dataTarget.getFieldDate20());
			// set time of deletion
			setFromToTimeDel(rowCsv, headerCsv, dataTarget);

			// set name file
			setNameFileDel(rowCsv, headerCsv, dataTarget);

			dataSourceCsv.add(rowCsv);
		}

		CSVFileData fileData = new CSVFileData(TABLE_NAME_FILE + FILE_EXTENSION, headerCsv, dataSourceCsv);
		generator.generate(generatorContext, fileData);
		return fileData;
	}

	/**
	 * set deletion time into row csv
	 * 
	 * @param rowCsv
	 * @param dataTarget
	 */
	private void setFromToTimeDel(Map<String, Object> rowCsv, List<String> headerCsv, TableDeletionDataCsv dataTarget) {
		int timeStore = dataTarget.getTimeStore();
		switch (timeStore) {
		case 0:
			// fulltime
			break;
		case 1:
			// daily
			rowCsv.put(headerCsv.get(79), dataTarget.getStartDateOfDaily());
			rowCsv.put(headerCsv.get(80), dataTarget.getEndDateOfDaily());
			break;
		case 2:
			// monthly
			rowCsv.put(headerCsv.get(79), dataTarget.getStartMonthOfMonthly());
			rowCsv.put(headerCsv.get(80), dataTarget.getEndMonthOfMonthly());
			break;
		case 3:
			// annual
			rowCsv.put(headerCsv.get(79), dataTarget.getStartYearOfMonthly());
			rowCsv.put(headerCsv.get(80), dataTarget.getEndYearOfMonthly());
			break;
		default:
			break;
		}
	}

	/**
	 * set name file deletion into row csv
	 * 
	 * @param rowCsv
	 * @param dataTarget
	 */
	private void setNameFileDel(Map<String, Object> rowCsv, List<String> headerCsv, TableDeletionDataCsv dataTarget) {
		String fileCompressName = dataTarget.getCompanyId() + dataTarget.getDelName();
		String fileData = dataTarget.getCompanyId() + dataTarget.getCategoryName() + dataTarget.getTableJapanName();
		rowCsv.put(headerCsv.get(81), fileCompressName);
		rowCsv.put(headerCsv.get(82), fileData);
	}

	/**
	 * 
	 * @param generatorContext
	 * @param delId
	 * @return
	 */
	private CSVFileData generalEmployeesToCsv(FileGeneratorContext generatorContext, String delId) {
		List<EmployeeDeletion> employeeDeletions = repoEmployeesDel.getEmployeesDeletionListById(delId);
		// Add Table to CSV2
		List<String> headerCsv2 = this.getHeaderForEmployeeFile();
		List<Map<String, Object>> dataSourceCsv2 = new ArrayList<>();
		for (EmployeeDeletion employeeDeletion : employeeDeletions) {
			Map<String, Object> rowCsv2 = new HashMap<>();
			rowCsv2.put(headerCsv2.get(0), employeeDeletion.getEmployeeId());
			rowCsv2.put(headerCsv2.get(1), employeeDeletion.getEmployeeCode());
			rowCsv2.put(headerCsv2.get(2), Base64.getEncoder().encodeToString(employeeDeletion.getBusinessName().v().getBytes()));
//			rowCsv2.put(headerCsv2.get(2), CommonKeyCrypt.encrypt(employeeDeletion.getBusinessName().v()));  << error Illegal key size
			dataSourceCsv2.add(rowCsv2);
		}

		CSVFileData fileData = new CSVFileData(EMPLOYEE_NAME_FILE + FILE_EXTENSION, headerCsv2, dataSourceCsv2);
		generator.generate(generatorContext, fileData);

		return fileData;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getHeaderForTableDelFile() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_TABLE_FILE) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

	/**
	 * get header for employee file
	 * 
	 * @return
	 */
	private List<String> getHeaderForEmployeeFile() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_EMPLOYEE_FILE) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

	/**
	 * get header for data file
	 * 
	 * @return
	 */
	private List<String> getHeaderForDataFile(List<String> columNames) {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_HEADER_FIX_DATA_FILE) {
			lstHeader.add(TextResource.localize(nameId));
		}

		for (String nameColumn : columNames) {
			lstHeader.add(nameColumn);
		}
		return lstHeader;
	}

	/**
	 * アルゴリズム「サーバデータ削除実行」を実行する execute the deletion data
	 * 
	 * @param delId
	 */
	private ResultState deleteDataAgth(String delId, ManualSetDeletion domain,
			List<TableDeletionDataCsv> tableDeletionDataCsvs, List<EmployeeDeletion> employeeDeletions,
			List<Category> categories) {

		try {
			// Update domain model 「データ削除動作管理」
			repoManagementDel.updateCatCountAnCond(delId, 0, OperatingCondition.DELETING);

			Map<String, List<TableDeletionDataCsv>> mapCatWithDatas = mapCatWithDataDel(tableDeletionDataCsvs);
			if (mapCatWithDatas != null) {
				int categoryCount = 0;
				for (Category category : categories) {
					categoryCount++;

					// ドメインモデル「データ削除動作管理」を更新する
					repoManagementDel.updateCatCount(delId, categoryCount);

					// ドメインモデル「データ削除動作管理.中断するしない」を確認
					Optional<ManagementDeletion> maOptional = repoManagementDel.getManagementDeletionById(delId);
					if (maOptional.isPresent()) {
						if (maOptional.get().isInterruptedFlg()) {
							return ResultState.TERMINATE;
						}
					} else {
						return ResultState.ABNORMAL_END;
					}

					String categoryId = category.getCategoryId().v();
					List<TableDeletionDataCsv> catDatas = mapCatWithDatas.get(categoryId);
					if (catDatas != null) {
						List<TableDeletionDataCsv> parentTables = new ArrayList<>();
						List<TableDeletionDataCsv> childTables = new ArrayList<>();
						
						for (TableDeletionDataCsv tableDataDel : catDatas) {
							if (tableDataDel.hasParentTblFlg()) {
								childTables.add(tableDataDel);
							} else {
								parentTables.add(tableDataDel);
							}
						}
						
						//delete child
						for (TableDeletionDataCsv tableDataDel : childTables) {
							// アルゴリズム「サーバデータ削除実行カテゴリ」を実行する
							String msgError = deleteDataForCategory(tableDataDel, employeeDeletions);
							if (msgError != null) {
								ManagementDeletion managementDeletion = maOptional.get();
								int errorCount = managementDeletion.getErrorCount();
								managementDeletion.setErrorCount(errorCount + 1);
								repoManagementDel.update(managementDeletion);
								// ドメインモデル「データ削除の結果ログ」を追加する
								saveErrorLogResult(domain, msgError);
//								return ResultState.ABNORMAL_END;
							}
						}
						
						//delete parent
						for (TableDeletionDataCsv tableDataDel : parentTables) {
							// アルゴリズム「サーバデータ削除実行カテゴリ」を実行する
							String msgError = deleteDataForCategory(tableDataDel, employeeDeletions);
							if (msgError != null) {
								ManagementDeletion managementDeletion = maOptional.get();
								int errorCount = managementDeletion.getErrorCount();
								managementDeletion.setErrorCount(errorCount + 1);
								repoManagementDel.update(managementDeletion);
								// ドメインモデル「データ削除の結果ログ」を追加する
								saveErrorLogResult(domain, msgError);
//								return ResultState.ABNORMAL_END;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			saveErrorLogResult(domain, e.getMessage());
			return ResultState.ABNORMAL_END;
		}

		return ResultState.NORMAL_END;
	}

	/**
	 * アルゴリズム「サーバデータ削除実行カテゴリ」を実行する
	 */
	private String deleteDataForCategory(TableDeletionDataCsv tableDataDel,
			List<EmployeeDeletion> employeeDeletions) {
		try {
			repoCsv.deleteData(tableDataDel, employeeDeletions);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
	}
	
	
	/**
	 * ドメインモデル「データ削除の結果ログ」を追加する
	 */
	private void saveErrorLogResult(ManualSetDeletion domain, String msgError) {
		String msgId = MSG_DEL_ERROR_LOG;
		GeneralDateTime logTime = GeneralDateTime.now();
		int seqId = repoResultLogDel.getMaxSeqId(domain.getDelId()) + 1;
		ResultLogDeletion resultLogDomain = ResultLogDeletion.createFromJavatype(seqId, domain.getDelId(),
				domain.getCompanyId(), logTime, TextResource.localize(msgId), msgError, null, null);
		repoResultLogDel.add(resultLogDomain);
	}
}
