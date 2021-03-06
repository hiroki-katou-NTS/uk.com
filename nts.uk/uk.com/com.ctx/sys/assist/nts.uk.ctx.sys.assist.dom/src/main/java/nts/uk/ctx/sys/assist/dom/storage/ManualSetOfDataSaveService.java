/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.category.StorageRangeSaved;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMtRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;
import nts.uk.shr.infra.file.csv.CsvReportWriter;

/**
 * @author nam.lh
 *
 */
@Stateless
public class ManualSetOfDataSaveService extends ExportService<Object> {

	private static final List<String> LST_NAME_ID_HEADER_TABLE = Arrays.asList("CMF003_500", "CMF003_642", "CMF003_501",
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
			"CMF003_579", "CMF003_580", "CMF003_581", "CMF003_582", "CMF003_613", "CMF003_583", "CMF003_584");

	private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV2 = Arrays.asList("SID", "SCD", "BUSINESS_NAME");

	private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV3 = Arrays.asList("CMF003_620", "CMF003_621",
			"CMF003_622", "CMF003_623", "CMF003_624");

	private static final String CSV_EXTENSION = ".csv";
	private static final String ZIP_EXTENSION = ".zip";
	private static final String FILE_NAME_CSV1 = "??????????????????????????????";
	private static final String FILE_NAME_CSV2 = "????????????";
	private static final int NUM_OF_TABLE_EACH_PROCESS = 100;
	private static final String EMPLOYEE_CD = "5";
	@Inject
	private ResultOfSavingRepository repoResultSaving;
	@Inject
	private DataStorageMngRepository repoDataSto;
	@Inject
	private CategoryRepository repoCategory;
	@Inject
	private CategoryFieldMtRepository repoCateField;
	@Inject
	private TargetCategoryRepository repoTargetCat;
	@Inject
	private TargetEmployeesRepository repoTargetEmp;
	@Inject
	private CSVReportGenerator generator;
	@Inject
	private TableListRepository repoTableList;
	@Inject
	private ApplicationTemporaryFileFactory applicationTemporaryFileFactory;

	@Override
	protected void handle(ExportServiceContext<Object> context) {
		ManualSetOfDataSaveHolder holder = (ManualSetOfDataSaveHolder) context.getQuery();
		serverManualSaveProcessing(holder.getDomain(), holder.getPatternCode(), context.getGeneratorContext());
	}

	public void serverManualSaveProcessing(ManualSetOfDataSave manualSetting, String patternCode,
			FileGeneratorContext generatorContext) {
		// ????????????????????????????????????????????????????????????????????????
		String storeProcessingId = manualSetting.getStoreProcessingId();
		String practitioner = manualSetting.getPractitioner();
		try {
			String cid = manualSetting.getCid();
			int saveForm = manualSetting.getSaveType().value;
			String saveName = manualSetting.getSaveSetName().v();
			GeneralDateTime saveStartDatetime = GeneralDateTime.now();

			long fileSize = 0;
			String saveFileName = null;
			GeneralDateTime saveEndDatetime = null;
			int deletedFiles = 0;
			String compressedPassword = manualSetting.getCompressedPassword() != null ? 
					manualSetting.getCompressedPassword().v() : null;
			int targetNumberPeople = 0;
			int saveStatus = 0;
			String fileId = null;
			String ipAddress = AppContexts.requestedWebApi().getRequestIpAddress();
			String pcName = AppContexts.requestedWebApi().getRequestPcName();
			String account = AppContexts.windowsAccount().getUserName();
			LoginInfo loginInfo = new LoginInfo(ipAddress, pcName, account);
			List<ResultLogSaving> listResultLogSavings = new ArrayList<ResultLogSaving>();
			int logNumber = 0;
			GeneralDateTime logTime = GeneralDateTime.now();
			String logContent = TextResource.localize("CMF003_625");
			String errorEmployeeId = "";
			GeneralDate errorDate = null;
			String errorContent = "";
			String contractCd = AppContexts.user().contractCode();
			listResultLogSavings.add(ResultLogSaving.createFromJavatype(logNumber, contractCd, storeProcessingId, cid,
					logTime, logContent, errorEmployeeId, errorDate, errorContent));
			ResultOfSaving data = new ResultOfSaving(storeProcessingId, cid, fileSize, patternCode, saveFileName,
					saveName, saveForm, saveEndDatetime, saveStartDatetime, deletedFiles, compressedPassword,
					practitioner, listResultLogSavings, targetNumberPeople, saveStatus, 1, fileId, loginInfo);
			repoResultSaving.add(data);

			// ??????????????????????????????????????????????????????
			List<TargetEmployees> targetEmployees = repoTargetEmp.getTargetEmployeesListById(storeProcessingId);
			targetEmployees.sort(Comparator.comparing(TargetEmployees::getScd));
			// ???????????????????????????????????????????????????????????????????????????
			StringBuffer outCompressedFileName = new StringBuffer();
			ResultState resultState = selectTargetTable(storeProcessingId, manualSetting, outCompressedFileName,
					patternCode);

			if (resultState == ResultState.ABNORMAL_END) {
				evaluateAbnormalEnd(storeProcessingId, targetEmployees.size());
				return;
			} else if (resultState == ResultState.INTERRUPTION) {
				evaluateInterruption(storeProcessingId, targetEmployees.size());
				return;
			}

			// ?????????????????????????????????????????????????????????????????????
			List<String> categoryIds = manualSetting.getCategory().stream().map(TargetCategory::getCategoryId)
					.distinct().collect(Collectors.toList());
//			if (!repoDataSto.update(storeProcessingId, categoryIds.size(), 0,
//					OperatingCondition.SAVING)) {
//				return;
//			}

			// ?????????????????????????????????????????????????????????
			ErrorResultHolder result = 
					saveTargetData(storeProcessingId, generatorContext, manualSetting, targetEmployees);

			// ?????????????????????
			switch (result.getResultState()) {
			case NORMAL_END:
				evaluateNormalEnd(storeProcessingId, generatorContext, manualSetting, targetEmployees.size(),
						outCompressedFileName.toString());
				break;

			case ABNORMAL_END:
				evaluateAbnormalEndForException(result.getSid(), storeProcessingId, targetEmployees.size(), result.getException());
				break;

			case INTERRUPTION:
				evaluateInterruption(storeProcessingId, targetEmployees.size());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			generatorContext.dispose();
			evaluateAbnormalEndForException(practitioner, storeProcessingId, 0, e);
		}
	}

	private ResultState selectTargetTable(String storeProcessingId, ManualSetOfDataSave optManualSetting,
			StringBuffer outCompressedFileName, String patternCode) {
		// Get list category from
		List<TargetCategory> targetCategories = repoTargetCat.getTargetCategoryListById(storeProcessingId);
		List<String> categoryIds = targetCategories.stream().map(x -> {
			return x.getCategoryId();
		}).collect(Collectors.toList());

		List<Category> categorys = repoCategory.getCategoryByListId(categoryIds);
		List<CategoryFieldMt> categoryFieldMts = targetCategories.stream()
				.map(c -> repoCateField.findByCategoryIdAndSystemType(c.getCategoryId(), c.getSystemType().value))
				.flatMap(List::stream).distinct().collect(Collectors.toList());
		int countCategoryFieldMts = categoryFieldMts.stream()
				.collect(Collectors.groupingBy(CategoryFieldMt::getCategoryId)).keySet().size();
		if (categoryIds.size() != countCategoryFieldMts) {
//			return ResultState.ABNORMAL_END;
			repoDataSto.update(storeProcessingId, countCategoryFieldMts, 0, OperatingCondition.SAVING);
		} else
			repoDataSto.update(storeProcessingId, categoryIds.size(), 0, OperatingCondition.SAVING);

		String cId = optManualSetting.getCid();

		// B42
		String datetimenow = GeneralDateTime.now().toString("yyyyMMddHHmmss");
		SaveSetName savename = optManualSetting.getSaveSetName();
		String compressedFileName = cId + savename.toString() + datetimenow;
		outCompressedFileName.setLength(0);
		outCompressedFileName = outCompressedFileName.append(compressedFileName);
		for (CategoryFieldMt categoryFieldMt : categoryFieldMts) {

			String categoryName = "";
			int systemType = categoryFieldMt.getSystemType().value;
			int storageRangeSaved = 0;
			TimeStore retentionPeriodCls = null;
			int anotherComCls = 0;
			String screenRetentionPeriod = null;
			String saveDateFrom = null;
			String saveDateTo = null;
			Optional<Category> category = categorys.stream()
					.filter(c -> c.getCategoryId().v().equals(categoryFieldMt.getCategoryId())).findFirst();
			if (category.isPresent()) {
				storageRangeSaved = category.get().getStorageRangeSaved().value;
				// CR #102535
				if (category.get().getStorageRangeSaved().value == StorageRangeSaved.EARCH_EMP.value) {
					List<String> clsKeyQuerys = Arrays.asList(categoryFieldMt.getClsKeyQuery1(),
							categoryFieldMt.getClsKeyQuery2(), categoryFieldMt.getClsKeyQuery3(),
							categoryFieldMt.getClsKeyQuery4(), categoryFieldMt.getClsKeyQuery5(),
							categoryFieldMt.getClsKeyQuery6(), categoryFieldMt.getClsKeyQuery7(),
							categoryFieldMt.getClsKeyQuery8(), categoryFieldMt.getClsKeyQuery9(),
							categoryFieldMt.getClsKeyQuery10());
					if (!clsKeyQuerys.contains(EMPLOYEE_CD)) {
						storageRangeSaved = StorageRangeSaved.ALL_EMP.value;
					}
				}

				categoryName = category.get().getCategoryName().v();
				retentionPeriodCls = category.get().getTimeStore();
				anotherComCls = category.get().getOtherCompanyCls() != null ? category.get().getOtherCompanyCls().value
						: null;

				switch (retentionPeriodCls) {
				case DAILY:
					saveDateFrom = optManualSetting.getDaySaveStartDate() != null
							? optManualSetting.getDaySaveStartDate().toString()
							: null;
					saveDateTo = optManualSetting.getDaySaveEndDate() != null
							? optManualSetting.getDaySaveEndDate().toString()
							: null;
					screenRetentionPeriod = saveDateFrom + "???" + saveDateTo;
					break;
				case MONTHLY:
					saveDateFrom = optManualSetting.getMonthSaveStartDate() != null
							? optManualSetting.getMonthSaveStartDate().replace('-', '/')
							: null;
					saveDateTo = optManualSetting.getMonthSaveEndDate() != null
							? optManualSetting.getMonthSaveEndDate().replace('-', '/')
							: null;
					screenRetentionPeriod = saveDateFrom + "???" + saveDateTo;
					break;
				case ANNUAL:
					if (optManualSetting.getStartYear().isPresent()) {
						saveDateFrom = optManualSetting.getStartYear().map(y -> y.v().toString()).orElse(null);
					}
					if (optManualSetting.getEndYear().isPresent()) {
						saveDateTo = optManualSetting.getEndYear().map(y -> y.v().toString()).orElse(null);
					}
					screenRetentionPeriod = saveDateFrom + "???" + saveDateTo;
					break;
				default:
					break;
				}

			}
			String internalFileName = cId + categoryName + categoryFieldMt.getTableJapanName();

			TableList listtable = new TableList(categoryFieldMt.getCategoryId(), categoryName, null, systemType,
					storeProcessingId, categoryFieldMt.getTableNo(), categoryFieldMt.getTableJapanName(),
					categoryFieldMt.getTableEnglishName(), categoryFieldMt.getFieldAcqCid(),
					categoryFieldMt.getFieldAcqDateTime(), categoryFieldMt.getFieldAcqEmployeeId(),
					categoryFieldMt.getFieldAcqEndDate(), categoryFieldMt.getFieldAcqStartDate(), patternCode,
					optManualSetting.getSaveSetName().toString(), "1", saveDateFrom, saveDateTo, storageRangeSaved,
					retentionPeriodCls != null ? retentionPeriodCls.value : null, internalFileName, anotherComCls, null,
					null, compressedFileName, categoryFieldMt.getFieldChild1(), categoryFieldMt.getFieldChild2(),
					categoryFieldMt.getFieldChild3(), categoryFieldMt.getFieldChild4(),
					categoryFieldMt.getFieldChild5(), categoryFieldMt.getFieldChild6(),
					categoryFieldMt.getFieldChild7(), categoryFieldMt.getFieldChild8(),
					categoryFieldMt.getFieldChild9(), categoryFieldMt.getFieldChild10(),
					categoryFieldMt.getHistoryCls() != null ? categoryFieldMt.getHistoryCls().value : null, 1, 1,
					categoryFieldMt.getClsKeyQuery1(), categoryFieldMt.getClsKeyQuery2(),
					categoryFieldMt.getClsKeyQuery3(), categoryFieldMt.getClsKeyQuery4(),
					categoryFieldMt.getClsKeyQuery5(), categoryFieldMt.getClsKeyQuery6(),
					categoryFieldMt.getClsKeyQuery7(), categoryFieldMt.getClsKeyQuery8(),
					categoryFieldMt.getClsKeyQuery9(), categoryFieldMt.getClsKeyQuery10(),
					categoryFieldMt.getFieldKeyQuery1(), categoryFieldMt.getFieldKeyQuery2(),
					categoryFieldMt.getFieldKeyQuery3(), categoryFieldMt.getFieldKeyQuery4(),
					categoryFieldMt.getFieldKeyQuery5(), categoryFieldMt.getFieldKeyQuery6(),
					categoryFieldMt.getFieldKeyQuery7(), categoryFieldMt.getFieldKeyQuery8(),
					categoryFieldMt.getFieldKeyQuery9(), categoryFieldMt.getFieldKeyQuery10(),
					categoryFieldMt.getDefaultCondKeyQuery(), categoryFieldMt.getFieldDate1(),
					categoryFieldMt.getFieldDate2(), categoryFieldMt.getFieldDate3(), categoryFieldMt.getFieldDate4(),
					categoryFieldMt.getFieldDate5(), categoryFieldMt.getFieldDate6(), categoryFieldMt.getFieldDate7(),
					categoryFieldMt.getFieldDate8(), categoryFieldMt.getFieldDate9(), categoryFieldMt.getFieldDate10(),
					categoryFieldMt.getFieldDate11(), categoryFieldMt.getFieldDate12(),
					categoryFieldMt.getFieldDate13(), categoryFieldMt.getFieldDate14(),
					categoryFieldMt.getFieldDate15(), categoryFieldMt.getFieldDate16(),
					categoryFieldMt.getFieldDate17(), categoryFieldMt.getFieldDate18(),
					categoryFieldMt.getFieldDate19(), categoryFieldMt.getFieldDate20(),
					categoryFieldMt.getFiledKeyUpdate1(), categoryFieldMt.getFiledKeyUpdate2(),
					categoryFieldMt.getFiledKeyUpdate3(), categoryFieldMt.getFiledKeyUpdate4(),
					categoryFieldMt.getFiledKeyUpdate5(), categoryFieldMt.getFiledKeyUpdate6(),
					categoryFieldMt.getFiledKeyUpdate7(), categoryFieldMt.getFiledKeyUpdate8(),
					categoryFieldMt.getFiledKeyUpdate9(), categoryFieldMt.getFiledKeyUpdate10(),
					categoryFieldMt.getFiledKeyUpdate11(), categoryFieldMt.getFiledKeyUpdate12(),
					categoryFieldMt.getFiledKeyUpdate13(), categoryFieldMt.getFiledKeyUpdate14(),
					categoryFieldMt.getFiledKeyUpdate15(), categoryFieldMt.getFiledKeyUpdate16(),
					categoryFieldMt.getFiledKeyUpdate17(), categoryFieldMt.getFiledKeyUpdate18(),
					categoryFieldMt.getFiledKeyUpdate19(), categoryFieldMt.getFiledKeyUpdate20(), screenRetentionPeriod,
					optManualSetting.getSuppleExplanation(), categoryFieldMt.getParentTblJpName(),
					categoryFieldMt.getHasParentTblFlg() != null ? categoryFieldMt.getHasParentTblFlg().value : 0,
					categoryFieldMt.getParentTblName(), categoryFieldMt.getFieldParent1(),
					categoryFieldMt.getFieldParent2(), categoryFieldMt.getFieldParent3(),
					categoryFieldMt.getFieldParent4(), categoryFieldMt.getFieldParent5(),
					categoryFieldMt.getFieldParent6(), categoryFieldMt.getFieldParent7(),
					categoryFieldMt.getFieldParent8(), categoryFieldMt.getFieldParent9(),
					categoryFieldMt.getFieldParent10(), 0);

			repoTableList.add(listtable);
		}

		return ResultState.NORMAL_END;
	}

	private ErrorResultHolder saveTargetData(String storeProcessingId, FileGeneratorContext generatorContext,
			ManualSetOfDataSave optManualSetting, List<TargetEmployees> targetEmployees) {
		// ?????????????????????????????????????????????????????????
		ErrorResultHolder result = null;
		List<String> targetEmployeesSid = targetEmployees.stream().map(c -> c.getSid()).collect(Collectors.toList());
		// ???????????????????????????????????????????????????????????????csv???????????????????????????
		result = generalCsv(generatorContext, storeProcessingId, targetEmployeesSid);

		if (result.getResultState() != ResultState.NORMAL_END) {
			return result;
		}

//		// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????NULL???????????????
//		if (optManualSetting.getIdentOfSurveyPre() == NotUseAtr.USE) {
//			repoTargetEmp.removeBusinessName(storeProcessingId);
//			targetEmployees = targetEmployees.stream().map(item -> {
//				item.setBusinessname(null);
//				return item;
//			}).collect(Collectors.toList());
//		}

		// ????????????????????????csv??????????????????????????????????????????
		result = generalEmployeesToCsv(generatorContext, targetEmployees);

		if (result.getResultState() != ResultState.NORMAL_END) {
			return result;
		}

		return result;
	}

	private ErrorResultHolder generalCsv(FileGeneratorContext generatorContext, String storeProcessingId,
			List<String> targetEmployeesSid) {
		try {
			ErrorResultHolder result = null;
			List<String> headerCsv = this.getTextHeaderCsv1();
			// Get data from Manual Setting table
			Map<String, Object> rowCsv = new HashMap<>();
			int offset = 0;
			List<String> categoryIds = new ArrayList<>();
			CsvReportWriter csv = generator.generate(generatorContext, FILE_NAME_CSV1 + CSV_EXTENSION, headerCsv,
					"UTF-8");

			while (true) {
				// ?????????????????????????????????????????????
				List<TableList> tableLists = repoTableList.getByOffsetAndNumber(storeProcessingId, offset,
						NUM_OF_TABLE_EACH_PROCESS);
				List<String> ids = tableLists.stream().map(TableList::getCategoryId).distinct()
						.collect(Collectors.toList());
				int i = 0;
				for (TableList tableList : tableLists) {
					rowCsv = getDataTableListeCsv(rowCsv, headerCsv, tableList);
					csv.writeALine(rowCsv);
					i++;
					// Add Table to CSV Auto
					result = generalDataTableCsvAuto(generatorContext, storeProcessingId, tableList,
							targetEmployeesSid, i);
					if (result.getResultState() != ResultState.NORMAL_END) {
						return result;
					}
					// ?????????????????????????????????????????????????????????????????????
					// ?????????????????????????????????????????????????????????????????????
					String categoryId = tableList.getCategoryId();
					if (!categoryIds.contains(categoryId)) {
						categoryIds.add(categoryId);
						if (!repoDataSto.increaseCategoryCount(storeProcessingId)) {
							return new ErrorResultHolder(ResultState.INTERRUPTION);
						}
					}
				}
				offset += NUM_OF_TABLE_EACH_PROCESS;
				// ????????????????????????????????????????????????
				if (tableLists.size() < NUM_OF_TABLE_EACH_PROCESS) {
					csv.destroy();
					break;
				}
			}

			return new ErrorResultHolder(ResultState.NORMAL_END);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResultHolder(targetEmployeesSid.get(0), e, ResultState.ABNORMAL_END);
		}

	}

	private Map<String, Object> getDataTableListeCsv(Map<String, Object> dataSourceCsv, List<String> headerCsv,
			TableList tableList) {
		Map<String, Object> rowCsv = new HashMap<>();
		rowCsv.put(headerCsv.get(0), tableList.getDataStorageProcessingId());
		rowCsv.put(headerCsv.get(1), tableList.getSystemType().value);
		rowCsv.put(headerCsv.get(2), tableList.getSaveForm());
		rowCsv.put(headerCsv.get(3), tableList.getPatternCode());
		rowCsv.put(headerCsv.get(4), tableList.getSaveSetName());
		rowCsv.put(headerCsv.get(5), tableList.getSupplementaryExplanation().orElse(""));
		rowCsv.put(headerCsv.get(6), tableList.getCategoryId());
		rowCsv.put(headerCsv.get(7), tableList.getCategoryName());
		rowCsv.put(headerCsv.get(8), tableList.getRetentionPeriodCls().value);
		rowCsv.put(headerCsv.get(9), tableList.getStorageRangeSaved().value);
		rowCsv.put(headerCsv.get(10), tableList.getScreenRetentionPeriod().orElse(""));
		rowCsv.put(headerCsv.get(11), tableList.getReferenceYear().orElse(""));
		rowCsv.put(headerCsv.get(12), tableList.getReferenceMonth().orElse(""));
		rowCsv.put(headerCsv.get(13), tableList.getSurveyPreservation().value);
		rowCsv.put(headerCsv.get(14), tableList.getAnotherComCls().value);
		rowCsv.put(headerCsv.get(15), tableList.getTableNo());
		rowCsv.put(headerCsv.get(16), tableList.getTableJapaneseName());
		rowCsv.put(headerCsv.get(17), tableList.getTableEnglishName());
		rowCsv.put(headerCsv.get(18), tableList.getHistoryCls().value);
		rowCsv.put(headerCsv.get(19), tableList.getHasParentTblFlg().value);
		rowCsv.put(headerCsv.get(20), tableList.getParentTblJpName().orElse(""));
		rowCsv.put(headerCsv.get(21), tableList.getParentTblName().orElse(""));
		rowCsv.put(headerCsv.get(22), tableList.getFieldParent1().orElse(""));
		rowCsv.put(headerCsv.get(23), tableList.getFieldParent2().orElse(""));
		rowCsv.put(headerCsv.get(24), tableList.getFieldParent3().orElse(""));
		rowCsv.put(headerCsv.get(25), tableList.getFieldParent4().orElse(""));
		rowCsv.put(headerCsv.get(26), tableList.getFieldParent5().orElse(""));
		rowCsv.put(headerCsv.get(27), tableList.getFieldParent6().orElse(""));
		rowCsv.put(headerCsv.get(28), tableList.getFieldParent7().orElse(""));
		rowCsv.put(headerCsv.get(29), tableList.getFieldParent8().orElse(""));
		rowCsv.put(headerCsv.get(30), tableList.getFieldParent9().orElse(""));
		rowCsv.put(headerCsv.get(31), tableList.getFieldParent10().orElse(""));
		rowCsv.put(headerCsv.get(32), tableList.getFieldChild1().orElse(""));
		rowCsv.put(headerCsv.get(33), tableList.getFieldChild2().orElse(""));
		rowCsv.put(headerCsv.get(34), tableList.getFieldChild3().orElse(""));
		rowCsv.put(headerCsv.get(35), tableList.getFieldChild4().orElse(""));
		rowCsv.put(headerCsv.get(36), tableList.getFieldChild5().orElse(""));
		rowCsv.put(headerCsv.get(37), tableList.getFieldChild6().orElse(""));
		rowCsv.put(headerCsv.get(38), tableList.getFieldChild7().orElse(""));
		rowCsv.put(headerCsv.get(39), tableList.getFieldChild8().orElse(""));
		rowCsv.put(headerCsv.get(40), tableList.getFieldChild9().orElse(""));
		rowCsv.put(headerCsv.get(41), tableList.getFieldChild10().orElse(""));
		rowCsv.put(headerCsv.get(42), tableList.getFieldAcqCid().orElse(""));
		rowCsv.put(headerCsv.get(43), tableList.getFieldAcqEmployeeId().orElse(""));
		rowCsv.put(headerCsv.get(44), tableList.getFieldAcqDateTime().orElse(""));
		rowCsv.put(headerCsv.get(45), tableList.getFieldAcqStartDate().orElse(""));
		rowCsv.put(headerCsv.get(46), tableList.getFieldAcqEndDate().orElse(""));
		rowCsv.put(headerCsv.get(47), tableList.getDefaultCondKeyQuery().orElse(""));
		rowCsv.put(headerCsv.get(48), tableList.getFieldKeyQuery1().orElse(""));
		rowCsv.put(headerCsv.get(49), tableList.getFieldKeyQuery2().orElse(""));
		rowCsv.put(headerCsv.get(50), tableList.getFieldKeyQuery3().orElse(""));
		rowCsv.put(headerCsv.get(51), tableList.getFieldKeyQuery4().orElse(""));
		rowCsv.put(headerCsv.get(52), tableList.getFieldKeyQuery5().orElse(""));
		rowCsv.put(headerCsv.get(53), tableList.getFieldKeyQuery6().orElse(""));
		rowCsv.put(headerCsv.get(54), tableList.getFieldKeyQuery7().orElse(""));
		rowCsv.put(headerCsv.get(55), tableList.getFieldKeyQuery8().orElse(""));
		rowCsv.put(headerCsv.get(56), tableList.getFieldKeyQuery9().orElse(""));
		rowCsv.put(headerCsv.get(57), tableList.getFieldKeyQuery10().orElse(""));
		rowCsv.put(headerCsv.get(58), tableList.getClsKeyQuery1().orElse(""));
		rowCsv.put(headerCsv.get(59), tableList.getClsKeyQuery2().orElse(""));
		rowCsv.put(headerCsv.get(60), tableList.getClsKeyQuery3().orElse(""));
		rowCsv.put(headerCsv.get(61), tableList.getClsKeyQuery4().orElse(""));
		rowCsv.put(headerCsv.get(62), tableList.getClsKeyQuery5().orElse(""));
		rowCsv.put(headerCsv.get(63), tableList.getClsKeyQuery6().orElse(""));
		rowCsv.put(headerCsv.get(64), tableList.getClsKeyQuery7().orElse(""));
		rowCsv.put(headerCsv.get(65), tableList.getClsKeyQuery8().orElse(""));
		rowCsv.put(headerCsv.get(66), tableList.getClsKeyQuery9().orElse(""));
		rowCsv.put(headerCsv.get(67), tableList.getClsKeyQuery10().orElse(""));
		rowCsv.put(headerCsv.get(68), tableList.getFiledKeyUpdate1().orElse(""));
		rowCsv.put(headerCsv.get(69), tableList.getFiledKeyUpdate2().orElse(""));
		rowCsv.put(headerCsv.get(70), tableList.getFiledKeyUpdate3().orElse(""));
		rowCsv.put(headerCsv.get(71), tableList.getFiledKeyUpdate4().orElse(""));
		rowCsv.put(headerCsv.get(72), tableList.getFiledKeyUpdate5().orElse(""));
		rowCsv.put(headerCsv.get(73), tableList.getFiledKeyUpdate6().orElse(""));
		rowCsv.put(headerCsv.get(74), tableList.getFiledKeyUpdate7().orElse(""));
		rowCsv.put(headerCsv.get(75), tableList.getFiledKeyUpdate8().orElse(""));
		rowCsv.put(headerCsv.get(76), tableList.getFiledKeyUpdate9().orElse(""));
		rowCsv.put(headerCsv.get(77), tableList.getFiledKeyUpdate10().orElse(""));
		rowCsv.put(headerCsv.get(78), tableList.getFiledKeyUpdate11().orElse(""));
		rowCsv.put(headerCsv.get(79), tableList.getFiledKeyUpdate12().orElse(""));
		rowCsv.put(headerCsv.get(80), tableList.getFiledKeyUpdate13().orElse(""));
		rowCsv.put(headerCsv.get(81), tableList.getFiledKeyUpdate14().orElse(""));
		rowCsv.put(headerCsv.get(82), tableList.getFiledKeyUpdate15().orElse(""));
		rowCsv.put(headerCsv.get(83), tableList.getFiledKeyUpdate16().orElse(""));
		rowCsv.put(headerCsv.get(84), tableList.getFiledKeyUpdate17().orElse(""));
		rowCsv.put(headerCsv.get(85), tableList.getFiledKeyUpdate18().orElse(""));
		rowCsv.put(headerCsv.get(86), tableList.getFiledKeyUpdate19().orElse(""));
		rowCsv.put(headerCsv.get(87), tableList.getFiledKeyUpdate20().orElse(""));
		rowCsv.put(headerCsv.get(88), tableList.getFieldDate1().orElse(""));
		rowCsv.put(headerCsv.get(89), tableList.getFieldDate2().orElse(""));
		rowCsv.put(headerCsv.get(90), tableList.getFieldDate3().orElse(""));
		rowCsv.put(headerCsv.get(91), tableList.getFieldDate4().orElse(""));
		rowCsv.put(headerCsv.get(92), tableList.getFieldDate5().orElse(""));
		rowCsv.put(headerCsv.get(93), tableList.getFieldDate6().orElse(""));
		rowCsv.put(headerCsv.get(94), tableList.getFieldDate7().orElse(""));
		rowCsv.put(headerCsv.get(95), tableList.getFieldDate8().orElse(""));
		rowCsv.put(headerCsv.get(96), tableList.getFieldDate9().orElse(""));
		rowCsv.put(headerCsv.get(97), tableList.getFieldDate10().orElse(""));
		rowCsv.put(headerCsv.get(98), tableList.getFieldDate11().orElse(""));
		rowCsv.put(headerCsv.get(99), tableList.getFieldDate12().orElse(""));
		rowCsv.put(headerCsv.get(100), tableList.getFieldDate13().orElse(""));
		rowCsv.put(headerCsv.get(101), tableList.getFieldDate14().orElse(""));
		rowCsv.put(headerCsv.get(102), tableList.getFieldDate15().orElse(""));
		rowCsv.put(headerCsv.get(103), tableList.getFieldDate16().orElse(""));
		rowCsv.put(headerCsv.get(104), tableList.getFieldDate17().orElse(""));
		rowCsv.put(headerCsv.get(105), tableList.getFieldDate18().orElse(""));
		rowCsv.put(headerCsv.get(106), tableList.getFieldDate19().orElse(""));
		rowCsv.put(headerCsv.get(107), tableList.getFieldDate20().orElse(""));
		rowCsv.put(headerCsv.get(108), tableList.getSaveDateFrom().orElse(""));
		rowCsv.put(headerCsv.get(109), tableList.getSaveDateTo().orElse(""));
		rowCsv.put(headerCsv.get(110), tableList.getCompressedFileName());
		rowCsv.put(headerCsv.get(111), tableList.getInternalFileName());
		rowCsv.put(headerCsv.get(112), tableList.getDataRecoveryProcessId().orElse(""));
		rowCsv.put(headerCsv.get(113), tableList.getCanNotBeOld().orElse(null));
		rowCsv.put(headerCsv.get(114), tableList.getSelectionTargetForRes().orElse(null));

		return rowCsv;
	}

	private ErrorResultHolder generalEmployeesToCsv(FileGeneratorContext generatorContext,
			List<TargetEmployees> targetEmployees) {
		try {
			List<String> headerCsv2 = this.getTextHeaderCsv2();
			CsvReportWriter csv = generator.generate(generatorContext, FILE_NAME_CSV2 + CSV_EXTENSION, headerCsv2,
					"UTF-8");
			for (TargetEmployees targetEmp : targetEmployees) {
				Map<String, Object> rowCsv2 = new HashMap<>();
				rowCsv2.put(headerCsv2.get(0), targetEmp.getSid());
				rowCsv2.put(headerCsv2.get(1), targetEmp.getScd());
				rowCsv2.put(headerCsv2.get(2),
						targetEmp.getBusinessname() != null ? CommonKeyCrypt.encrypt(targetEmp.getBusinessname().v())
								: "");
				csv.writeALine(rowCsv2);
			}

			csv.destroy();

			return new ErrorResultHolder(ResultState.NORMAL_END);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResultHolder(targetEmployees.get(0).getSid(), e, ResultState.ABNORMAL_END);
		}

	}

	private ErrorResultHolder generalDataTableCsvAuto(FileGeneratorContext generatorContext, String storeProcessingId,
			TableList tableList, List<String> targetEmployeesSid, int index) {
		try {
			// ?????????????????????????????????????????????????????????????????????????????????????????????
			Optional<DataStorageMng> dataStorageMng = repoDataSto.getDataStorageMngById(storeProcessingId);

			// Check interrupt
			if (dataStorageMng.isPresent() && dataStorageMng.get().getDoNotInterrupt() == NotUseAtr.USE) {
				return new ErrorResultHolder(ResultState.INTERRUPTION);
			}

			List<String> headerCsv3 = this.getTextHeaderCsv3(tableList.getTableEnglishName());

			// get data from table
			getData(tableList, targetEmployeesSid, index, headerCsv3, generatorContext);

			return new ErrorResultHolder(ResultState.NORMAL_END);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResultHolder(targetEmployeesSid.get(0), e, ResultState.ABNORMAL_END);
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void getData(TableList tableList, List<String> targetEmployeesSid, int index, List<String> headerCsv3,
			FileGeneratorContext generatorContext) {
		long startTime = System.nanoTime();
		System.out.println("============ Table Running : " + index + " == " + tableList.getTableEnglishName());
		repoTableList.getDataDynamic(tableList, targetEmployeesSid, headerCsv3, generatorContext);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // ms;
		System.out.println(
				"============ Table Done :" + index + " == " + tableList.getTableEnglishName() + " === " + duration);
	}

	private ResultState evaluateNormalEnd(String storeProcessingId, FileGeneratorContext generatorContext,
			ManualSetOfDataSave optManualSetting, int totalTargetEmployees, String outCompressedFileName) {
		// ?????????????????????????????????????????????????????????????????????
		repoDataSto.update(storeProcessingId, OperatingCondition.INPROGRESS);

		try {
			// ????????????????????????????????????????????????????????????
			ApplicationTemporaryFilesContainer applicationTemporaryFilesContainer = applicationTemporaryFileFactory
					.createContainer();
			NotUseAtr passwordAvailability = optManualSetting.getPasswordAvailability();
			String fileName = AppContexts.user().companyId() + optManualSetting.getSaveSetName()
					+ GeneralDateTime.now().toString("yyyyMMddHHmmss") + ZIP_EXTENSION;
			if (passwordAvailability == NotUseAtr.NOT_USE) {
				applicationTemporaryFilesContainer.zipWithName(generatorContext, fileName, false);
			}
			if (passwordAvailability == NotUseAtr.USE) {
				String password = optManualSetting.getCompressedPassword().v();
				applicationTemporaryFilesContainer.zipWithName(generatorContext, fileName, password, false);
			}

			applicationTemporaryFilesContainer.removeContainer();
			// step ?????????????????????????????????????????????????????????????????????
			Optional<ResultOfSaving> resultOfSaving = repoResultSaving.getResultOfSavingById(storeProcessingId);
			resultOfSaving.ifPresent(result -> {
				String cid = result.getCid();
				int sizeOfList = result.getListResultLogSavings().size();
				int logNumber = 0;
				if (sizeOfList > 0) {
					logNumber = result.getListResultLogSavings().get(sizeOfList - 1).getLogNumber() + 1;
				}
				// ????????????
				GeneralDateTime logTime = GeneralDateTime.now();
				String logContent = TextResource.localize("CMF003_628", fileName);
				String errorEmployeeId = "";
				GeneralDate errorDate = null;
				String errorContent = "";
				String contractCd = AppContexts.user().contractCode();
				ResultLogSaving resultLogSaving = ResultLogSaving.createFromJavatype(logNumber, contractCd,
						storeProcessingId, cid, logTime, logContent, errorEmployeeId, errorDate, errorContent);
				List<ResultLogSaving> listResultLogSavings = result.getListResultLogSavings();
				listResultLogSavings.add(resultLogSaving);
				result.setListResultLogSavings(listResultLogSavings);
				// ??????????????????
				String ipAddress = AppContexts.requestedWebApi().getRequestIpAddress();
				String pcName = AppContexts.requestedWebApi().getRequestPcName();
				String account = AppContexts.windowsAccount().getUserName();
				LoginInfo loginInfo = new LoginInfo(ipAddress, pcName, account);
				result.setLoginInfo(loginInfo);
				result.setCompressedPassword(result.getCompressedPassword().map(FileCompressionPassword::v)
						.map(CommonKeyCrypt::decrypt).map(FileCompressionPassword::new));
				repoResultSaving.update(result);
			});
		} catch (Exception e) {
			// step ?????????????????????????????????????????????????????????????????????
			Optional<ResultOfSaving> resultOfSaving = repoResultSaving.getResultOfSavingById(storeProcessingId);
			resultOfSaving.ifPresent(result -> {
				String cid = result.getCid();
				int sizeOfList = result.getListResultLogSavings().size();
				int logNumber = 0;
				if (sizeOfList > 0) {
					logNumber = result.getListResultLogSavings().get(sizeOfList - 1).getLogNumber() + 1;
				}
				// ????????????
				GeneralDateTime logTime = GeneralDateTime.now();
				String logContent = TextResource.localize("CMF003_629");
				String errorEmployeeId = "";
				GeneralDate errorDate = null;
				String errorContent = e.getMessage();
				String contractCd = AppContexts.user().contractCode();
				ResultLogSaving resultLogSaving = ResultLogSaving.createFromJavatype(logNumber, contractCd,
						storeProcessingId, cid, logTime, logContent, errorEmployeeId, errorDate, errorContent);
				List<ResultLogSaving> listResultLogSavings = result.getListResultLogSavings();
				listResultLogSavings.add(resultLogSaving);
				result.setListResultLogSavings(listResultLogSavings);
				// ??????????????????
				String ipAddress = AppContexts.requestedWebApi().getRequestIpAddress();
				String pcName = AppContexts.requestedWebApi().getRequestPcName();
				String account = AppContexts.windowsAccount().getUserName();
				LoginInfo loginInfo = new LoginInfo(ipAddress, pcName, account);
				result.setLoginInfo(loginInfo);
				result.setCompressedPassword(result.getCompressedPassword().map(FileCompressionPassword::v)
						.map(CommonKeyCrypt::decrypt).map(FileCompressionPassword::new));
				repoResultSaving.update(result);
			});
			e.printStackTrace();
			return evaluateAbnormalEndForError(storeProcessingId, totalTargetEmployees);
		}

		// ?????????????????????????????????????????????????????????????????????
		repoDataSto.update(storeProcessingId, OperatingCondition.DONE);

		// ????????????????????????????????????????????????????????????????????????
		String fileId = generatorContext.getTaskId();
		repoResultSaving.update(storeProcessingId, totalTargetEmployees, SaveStatus.SUCCESS, fileId, NotUseAtr.NOT_USE,
				outCompressedFileName);
		return ResultState.NORMAL_END;
	}

	private ResultState evaluateAbnormalEndForError(String storeProcessingId, int totalTargetEmployees) {
		// ?????????????????????????????????????????????????????????????????????
		repoDataSto.update(storeProcessingId, OperatingCondition.ABNORMAL_TERMINATION);

		// ????????????????????????????????????????????????????????????????????????
		repoResultSaving.update(storeProcessingId, Optional.ofNullable(totalTargetEmployees),
				Optional.of(SaveStatus.FAILURE));

		return ResultState.ABNORMAL_END;
	}

	private ResultState evaluateAbnormalEndForException(String practitioner, String storeProcessingId,
			int totalTargetEmployees, Exception e) {
		// step ?????????????????????????????????????????????????????????????????????
		Optional<ResultOfSaving> resultOfSaving = repoResultSaving.getResultOfSavingById(storeProcessingId);
		resultOfSaving.ifPresent(result -> {
			String cid = result.getCid();
			int sizeOfList = result.getListResultLogSavings().size();
			int logNumber = 0;
			if (sizeOfList > 0) {
				logNumber = result.getListResultLogSavings().get(sizeOfList - 1).getLogNumber() + 1;
			}
			GeneralDateTime logTime = GeneralDateTime.now();
			String logContent = TextResource.localize("CMF003_626");
			String errorEmployeeId = practitioner;
			GeneralDate errorDate = GeneralDate.today();
			String errorContent = e != null ? StringUtil.cutOffAsLengthHalf(e.getMessage(), 2000) : null;
			String contractCd = AppContexts.user().contractCode();
			ResultLogSaving resultLogSaving = ResultLogSaving.createFromJavatype(logNumber, contractCd,
					storeProcessingId, cid, logTime, logContent, errorEmployeeId, errorDate, errorContent);
			List<ResultLogSaving> listResultLogSavings = result.getListResultLogSavings();
			listResultLogSavings.add(resultLogSaving);
			result.setListResultLogSavings(listResultLogSavings);
			result.setCompressedPassword(result.getCompressedPassword().map(FileCompressionPassword::v)
					.map(CommonKeyCrypt::decrypt).map(FileCompressionPassword::new));
			repoResultSaving.update(result);
		});
		// ?????????????????????????????????????????????????????????????????????
		repoDataSto.update(storeProcessingId, OperatingCondition.ABNORMAL_TERMINATION);

		// ????????????????????????????????????????????????????????????????????????
		repoResultSaving.update(storeProcessingId, Optional.ofNullable(totalTargetEmployees),
				Optional.of(SaveStatus.FAILURE));

		return ResultState.ABNORMAL_END;
	}

	private ResultState evaluateAbnormalEnd(String storeProcessingId, int totalTargetEmployees) {
		// ?????????????????????????????????????????????????????????????????????
		repoDataSto.update(storeProcessingId, OperatingCondition.ABNORMAL_TERMINATION);

		// ????????????????????????????????????????????????????????????????????????
		repoResultSaving.update(storeProcessingId, Optional.ofNullable(totalTargetEmployees),
				Optional.of(SaveStatus.FAILURE));

		return ResultState.ABNORMAL_END;
	}

	private ResultState evaluateInterruption(String storeProcessingId, Integer totalTargetEmployees) {
		// step ?????????????????????????????????????????????????????????????????????
		Optional<ResultOfSaving> resultOfSaving = repoResultSaving.getResultOfSavingById(storeProcessingId);
		resultOfSaving.ifPresent(result -> {
			String cid = result.getCid();
			int sizeOfList = result.getListResultLogSavings().size();
			int logNumber = 0;
			if (sizeOfList > 0) {
				logNumber = result.getListResultLogSavings().get(sizeOfList - 1).getLogNumber() + 1;
			}
			GeneralDateTime logTime = GeneralDateTime.now();
			String logContent = TextResource.localize("CMF003_627");
			String errorEmployeeId = "";
			GeneralDate errorDate = null;
			String errorContent = "";
			String contractCd = AppContexts.user().contractCode();
			ResultLogSaving resultLogSaving = ResultLogSaving.createFromJavatype(logNumber, contractCd,
					storeProcessingId, cid, logTime, logContent, errorEmployeeId, errorDate, errorContent);
			List<ResultLogSaving> listResultLogSavings = result.getListResultLogSavings();
			listResultLogSavings.add(resultLogSaving);
			result.setListResultLogSavings(listResultLogSavings);
			result.setCompressedPassword(result.getCompressedPassword().map(FileCompressionPassword::v)
					.map(CommonKeyCrypt::decrypt).map(FileCompressionPassword::new));
			repoResultSaving.update(result);
		});
		// ?????????????????????????????????????????????????????????????????????
		repoDataSto.update(storeProcessingId, OperatingCondition.INTERRUPTION_END);

		// ????????????????????????????????????????????????????????????????????????
		repoResultSaving.update(storeProcessingId, Optional.ofNullable(totalTargetEmployees),
				Optional.of(SaveStatus.INTERRUPTION));

		return ResultState.INTERRUPTION;
	}

	private List<String> getTextHeaderCsv1() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_TABLE) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

	private List<String> getTextHeaderCsv2() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV2) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

	private List<String> getTextHeaderCsv3(String tableName) {
		List<String> columnNames = repoTableList.getAllColumnName(tableName);
		List<String> columnFix = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV3) {
			columnFix.add(TextResource.localize(nameId));
		}
		columnFix.addAll(columnNames);
		return columnFix;
	}

}
