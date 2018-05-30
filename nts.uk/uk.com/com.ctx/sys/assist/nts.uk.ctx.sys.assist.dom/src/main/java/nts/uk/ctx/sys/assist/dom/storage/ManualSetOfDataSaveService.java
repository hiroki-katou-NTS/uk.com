/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import com.google.common.base.Strings;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMtRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.EntityTypeUtil;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

/**
 * @author nam.lh
 *
 */
@Stateless
public class ManualSetOfDataSaveService extends ExportService<Object> {
	@Inject
	private ResultOfSavingRepository repoResultSaving;
	@Inject
	private DataStorageMngRepository repoDataSto;
	@Inject
	private ManualSetOfDataSaveRepository repoMalSetOfSave;
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
	@Inject
	private SaveTargetCsvRepository csvRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<Object> context) {
		generalCsvAuto(context.getGeneratorContext());
		//String storeProcessingId = context.getQuery().toString();
		//serverManualSaveProcessing(storeProcessingId, context.getGeneratorContext());
	}

	public void serverManualSaveProcessing(String storeProcessingId, FileGeneratorContext generatorContext) {

		// ドメインモデル「データ保存動作管理」を登録する ( Save data to Data storage operation
		// management )
		int categoryTotalCount = 0;
		int categoryCount = 0;
		int errorCount = 0;
		OperatingCondition operatingCondition = OperatingCondition.INPREPARATION;
		NotUseAtr doNotInterrupt = NotUseAtr.NOT_USE;
		DataStorageMng domain = new DataStorageMng(storeProcessingId, doNotInterrupt, categoryCount, categoryTotalCount,
				errorCount, operatingCondition);

		repoDataSto.add(domain);

		// Selection of target table and condition setting
		Optional<ManualSetOfDataSave> optManualSetting = repoMalSetOfSave.getManualSetOfDataSaveById(storeProcessingId);
		// Optional<TargetEmployees> targetEmployees =
		// repoTargetEmp.getTargetEmployeesListById(storeProcessingId);
		if (optManualSetting.isPresent()) {
			// ドメインモデル「データ保存の保存結果」へ書き出す ( Save data to Save result of data
			// saving)
			String cid = optManualSetting.get().getCid();
			int systemType = optManualSetting.get().getSystemType().value;
			String practitioner = optManualSetting.get().getPractitioner();
			int saveForm = 0;
			String saveSetCode = null;
			String saveName = optManualSetting.get().getSaveSetName().v();
			int saveForInvest = optManualSetting.get().getIdentOfSurveyPre().value;
			GeneralDateTime saveStartDatetime = GeneralDateTime.now();

			// Todo : value affer is Optional
			int fileSize = 0;
			String saveFileName = null;
			GeneralDateTime saveEndDatetime = null;
			int deletedFiles = 0;
			String compressedPassword = null;
			int targetNumberPeople = 0;
			int saveStatus = 0;
			String fileId = null;

			ResultOfSaving data = new ResultOfSaving(storeProcessingId, cid, systemType, fileSize, saveSetCode,
					saveFileName, saveName, saveForm, saveEndDatetime, saveStartDatetime, deletedFiles,
					compressedPassword, practitioner, targetNumberPeople, saveStatus, saveForInvest, fileId);
			repoResultSaving.add(data);

			// Get list category from
			List<TargetCategory> targetCategories = repoTargetCat.getTargetCategoryListById(storeProcessingId);
			List<String> categoryIds = targetCategories.stream().map(x -> {
				return x.getCategoryId();
			}).collect(Collectors.toList());
			List<Category> categorys = repoCategory.getCategoryByListId(categoryIds);
			List<CategoryFieldMt> categoryFieldMts = repoCateField.getCategoryFieldMtByListId(categoryIds);
			// TODO insert to table list

			for (CategoryFieldMt categoryFieldMt : categoryFieldMts) {
				String categoryName = "";
				String storageRangeSaved = "";
				TimeStore retentionPeriodCls = null;
				int anotherComCls = 0;
				DatePeriod screenRetentionPeriod = null;
				GeneralDate saveDateFrom = null;
				GeneralDate saveDateTo = null;
				int surveyPreservation = optManualSetting.get().getIdentOfSurveyPre().value;
				String compressedFileName = "";
				Optional<Category> category = categorys.stream()
						.filter(c -> c.getCategoryId().equals(categoryFieldMt.getCategoryId())).findFirst();
				if (category.isPresent()) {
					categoryName = category.get().getCategoryName().v();
					storageRangeSaved = category.get().getStorageRangeSaved().toString();
					retentionPeriodCls = category.get().getTimeStore();
					anotherComCls = category.get().getOtherCompanyCls().value;
				}

				switch (retentionPeriodCls) {
				case DAILY:
					saveDateFrom = optManualSetting.get().getDaySaveStartDate();
					saveDateTo = optManualSetting.get().getDaySaveEndDate();
					screenRetentionPeriod = new DatePeriod(saveDateFrom, saveDateTo);
					break;
				case MONTHLY:
					saveDateFrom = optManualSetting.get().getMonthSaveStartDate();
					saveDateTo = optManualSetting.get().getMonthSaveEndDate();
					screenRetentionPeriod = new DatePeriod(saveDateFrom, saveDateTo);
					break;
				case ANNUAL:
					saveDateFrom = GeneralDate.ymd(optManualSetting.get().getStartYear().v(), 1, 1);
					saveDateTo = GeneralDate.ymd(optManualSetting.get().getEndYear().v(), 12, 31);
					screenRetentionPeriod = new DatePeriod(saveDateFrom, saveDateTo);
					break;
				default:
					break;
				}
				// B42
				String datetimenow = LocalDateTime.now().toString();
				SaveSetName savename = optManualSetting.get().getSaveSetName();
				compressedFileName = storeProcessingId + savename.toString() + datetimenow;

				String internalFileName = storeProcessingId + categoryName + categoryFieldMt.getTableJapanName();

				TableList listtable = new TableList(categoryFieldMt.getCategoryId(), categoryName, storeProcessingId,
						"", categoryFieldMt.getTableNo(), categoryFieldMt.getTableJapanName(), "",
						categoryFieldMt.getFieldAcqCid(), categoryFieldMt.getFieldAcqDateTime(),
						categoryFieldMt.getFieldAcqEmployeeId(), categoryFieldMt.getFieldAcqEndDate(),
						categoryFieldMt.getFieldAcqStartDate(), "", optManualSetting.get().getSaveSetName().toString(),
						"", "0", saveDateFrom, saveDateTo, storageRangeSaved, retentionPeriodCls.value,
						internalFileName, anotherComCls, "", "", compressedFileName, categoryFieldMt.getFieldChild1(),
						categoryFieldMt.getFieldChild2(), categoryFieldMt.getFieldChild3(),
						categoryFieldMt.getFieldChild4(), categoryFieldMt.getFieldChild5(),
						categoryFieldMt.getFieldChild6(), categoryFieldMt.getFieldChild7(),
						categoryFieldMt.getFieldChild8(), categoryFieldMt.getFieldChild9(),
						categoryFieldMt.getFieldChild10(), categoryFieldMt.getHistoryCls().value, "", "",
						categoryFieldMt.getClsKeyQuery1().toString(), categoryFieldMt.getClsKeyQuery2().toString(),
						categoryFieldMt.getClsKeyQuery3().toString(), categoryFieldMt.getClsKeyQuery4().toString(),
						categoryFieldMt.getClsKeyQuery5().toString(), categoryFieldMt.getClsKeyQuery6().toString(),
						categoryFieldMt.getClsKeyQuery7().toString(), categoryFieldMt.getClsKeyQuery8().toString(),
						categoryFieldMt.getClsKeyQuery9().toString(), categoryFieldMt.getClsKeyQuery10().toString(),
						categoryFieldMt.getFieldKeyQuery1(), categoryFieldMt.getFieldKeyQuery2(),
						categoryFieldMt.getFieldKeyQuery3(), categoryFieldMt.getFieldKeyQuery4(),
						categoryFieldMt.getFieldKeyQuery5(), categoryFieldMt.getFieldKeyQuery6(),
						categoryFieldMt.getFieldKeyQuery7(), categoryFieldMt.getFieldKeyQuery8(),
						categoryFieldMt.getFieldKeyQuery9(), categoryFieldMt.getFieldKeyQuery10(),
						categoryFieldMt.getDefaultCondKeyQuery(), categoryFieldMt.getFieldDate1(),
						categoryFieldMt.getFieldDate2(), categoryFieldMt.getFieldDate3(),
						categoryFieldMt.getFieldDate4(), categoryFieldMt.getFieldDate5(),
						categoryFieldMt.getFieldDate6(), categoryFieldMt.getFieldDate7(),
						categoryFieldMt.getFieldDate8(), categoryFieldMt.getFieldDate9(),
						categoryFieldMt.getFieldDate10(), categoryFieldMt.getFieldDate11(),
						categoryFieldMt.getFieldDate12(), categoryFieldMt.getFieldDate13(),
						categoryFieldMt.getFieldDate14(), categoryFieldMt.getFieldDate15(),
						categoryFieldMt.getFieldDate16(), categoryFieldMt.getFieldDate17(),
						categoryFieldMt.getFieldDate18(), categoryFieldMt.getFieldDate19(),
						categoryFieldMt.getFieldDate20(), categoryFieldMt.getFiledKeyUpdate1(),
						categoryFieldMt.getFiledKeyUpdate2(), categoryFieldMt.getFiledKeyUpdate3(),
						categoryFieldMt.getFiledKeyUpdate4(), categoryFieldMt.getFiledKeyUpdate5(),
						categoryFieldMt.getFiledKeyUpdate6(), categoryFieldMt.getFiledKeyUpdate7(),
						categoryFieldMt.getFiledKeyUpdate8(), categoryFieldMt.getFiledKeyUpdate9(),
						categoryFieldMt.getFiledKeyUpdate10(), categoryFieldMt.getFiledKeyUpdate11(),
						categoryFieldMt.getFiledKeyUpdate12(), categoryFieldMt.getFiledKeyUpdate13(),
						categoryFieldMt.getFiledKeyUpdate14(), categoryFieldMt.getFiledKeyUpdate15(),
						categoryFieldMt.getFiledKeyUpdate16(), categoryFieldMt.getFiledKeyUpdate17(),
						categoryFieldMt.getFiledKeyUpdate18(), categoryFieldMt.getFiledKeyUpdate19(),
						categoryFieldMt.getFiledKeyUpdate20(), "", optManualSetting.get().getSuppleExplanation(),
						categoryFieldMt.getParentTblJpName(), categoryFieldMt.getHasParentTable().value,
						categoryFieldMt.getParentTblName(), categoryFieldMt.getFieldParent1(),
						categoryFieldMt.getFieldParent2(), categoryFieldMt.getFieldParent3(),
						categoryFieldMt.getFieldParent4(), categoryFieldMt.getFieldParent5(),
						categoryFieldMt.getFieldParent6(), categoryFieldMt.getFieldParent7(),
						categoryFieldMt.getFieldParent8(), categoryFieldMt.getFieldParent9(),
						categoryFieldMt.getFieldParent10(), surveyPreservation);

				repoTableList.add(listtable);
			}

			// end

			// update domain 「データ保存動作管理」 Data storage operation management
			storeProcessingId = domain.getStoreProcessingId();
			categoryTotalCount = categorys.size();
			categoryCount = 1;
			operatingCondition = OperatingCondition.SAVING;
			repoDataSto.update(storeProcessingId, categoryTotalCount, categoryCount, operatingCondition);

			// Execute algorithm "Save target data"

			/***/
			ApplicationTemporaryFilesContainer applicationTemporaryFilesContainer = applicationTemporaryFileFactory
					.createContainer();

			// Get
			List<SaveTargetCsv> csvTarRepo = commonCsv(storeProcessingId);

			// Add Table to CSV
			generalCsv(csvTarRepo, generatorContext);

			// Add Table to CSV2
			generalCsv2(storeProcessingId, generatorContext);

			// Add Table to CSV Auto
			// generalCsvAuto(csvTarRepo, generatorContext, categoryIds);

			// Add folder temp to zip
			// applicationTemporaryFilesContainer.addFile(generatorContext);

			int passwordAvailability = optManualSetting.get().getPasswordAvailability().value;

			if (passwordAvailability == 0) {
				applicationTemporaryFilesContainer.zipWithName(generatorContext, "test");
			}
			if (passwordAvailability == 1) {
				String password = optManualSetting.get().getCompressedPassword().v();
				applicationTemporaryFilesContainer.zipWithName(generatorContext, "test",
						CommonKeyCrypt.encrypt(password));
			}

			Path compressedFile = applicationTemporaryFilesContainer.getPath();
			applicationTemporaryFilesContainer.removeContainer();

			/** finally */
			// OutputStream zippedFile =
			// applicationTemporaryFilesContainer.zip("pass");
			// applicationTemporaryFilesContainer.removeContainer();
		}

	}

	private List<SaveTargetCsv> commonCsv(String storeProcessingId) {
		List<SaveTargetCsv> csvTarRepoCommon = csvRepo.getSaveTargetCsvById(storeProcessingId);
		return csvTarRepoCommon;
	}

	private void generalCsv(List<SaveTargetCsv> csvTarRepo, FileGeneratorContext generatorContext) {

		List<String> headerCsv = this.getTextHeader();
		// Get data from Manual Setting table
		List<Map<String, Object>> dataSourceCsv = new ArrayList<>();
		for (SaveTargetCsv dataTarget : csvTarRepo) {
			Map<String, Object> rowCsv = new HashMap<>();
			rowCsv.put(headerCsv.get(0), dataTarget.getStoreProcessingId());
			rowCsv.put(headerCsv.get(1), dataTarget.getSaveForm());
			rowCsv.put(headerCsv.get(2), dataTarget.getSaveSetCode());
			rowCsv.put(headerCsv.get(3), dataTarget.getSaveName());
			rowCsv.put(headerCsv.get(4), dataTarget.getSuppleExplanation());
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

			dataSourceCsv.add(rowCsv);
		}

		CSVFileData fileData = new CSVFileData(FILE_NAME_CSV1 + FILE_EXTENSION, headerCsv, dataSourceCsv);
		// FileGeneratorContext generatorContext = new FileGeneratorContext();
		generator.generate(generatorContext, fileData);

	}

	private void generalCsv2(String storeProcessingId, FileGeneratorContext generatorContext) {
		List<TargetEmployees> targetEmployees = repoTargetEmp.getTargetEmployeesListById(storeProcessingId);
		// Add Table to CSV2
		List<String> headerCsv2 = this.getTextHeaderCSV2();
		List<Map<String, Object>> dataSourceCsv2 = new ArrayList<>();
		for (TargetEmployees targetEmp : targetEmployees) {
			Map<String, Object> rowCsv2 = new HashMap<>();
			rowCsv2.put(headerCsv2.get(0), targetEmp.getSid());
			rowCsv2.put(headerCsv2.get(1), targetEmp.getScd());
			rowCsv2.put(headerCsv2.get(2), CommonKeyCrypt.encrypt(targetEmp.getBusinessname().v()));
			dataSourceCsv2.add(rowCsv2);
		}

		CSVFileData fileData = new CSVFileData(FILE_NAME_CSV2 + FILE_EXTENSION, headerCsv2, dataSourceCsv2);

		// FileGeneratorContext generatorContext = new FileGeneratorContext();

		generator.generate(generatorContext, fileData);
		// applicationTemporaryFilesContainer.addFile(generatorContext);

	}

	private void generalCsvAuto(FileGeneratorContext generatorContext) {
		List<TableList> tableLists = repoTableList.getAllTableList();
		for (TableList tableList : tableLists) {
			List<?> listObject = repoTableList.getDataDynamic(tableList);

			// Add Table to CSV Auto
			List<String> headerCsv = this.getTextHeaderCsv3(tableList);
			List<Map<String, Object>> dataSourceCsv = new ArrayList<>();
			for (Object object : listObject) {
				Map<String, Object> rowCsv = new HashMap<>();

				for (String key : headerCsv) {
					String fieldName = repoTableList.getFieldForColumnName(object.getClass(), key);
					Object resultObj = null;
					if (!Strings.isNullOrEmpty(fieldName)) {
						resultObj = object;
						for (String name : fieldName.split("\\.")) {
							resultObj = getValueByPropertyName(resultObj, name);
						}
					}
					rowCsv.put(key, resultObj);
				}

				dataSourceCsv.add(rowCsv);
			}

			CSVFileData fileData = new CSVFileData(AppContexts.user().companyCode() + tableList.getCategoryName()
					+ tableList.getTableJapaneseName() + FILE_EXTENSION, headerCsv, dataSourceCsv);

			generator.generate(generatorContext, fileData);

		}
		
		ApplicationTemporaryFilesContainer applicationTemporaryFilesContainer 
			= applicationTemporaryFileFactory.createContainer();
		Path path = applicationTemporaryFilesContainer.zipWithName(generatorContext, "abc.zip");
		
		applicationTemporaryFilesContainer.removeContainer();
		path.toFile();
	}

	private Object getValueByPropertyName(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final List<String> LST_NAME_ID_HEADER_TABLE = Arrays.asList("CMF003_500", "CMF003_501", "CMF003_502",
			"CMF003_503", "CMF003_504", "CMF003_505", "CMF003_506", "CMF003_507", "CMF003_508", "CMF003_509",
			"CMF003_510", "CMF003_511", "CMF003_512", "CMF003_513", "CMF003_514", "CMF003_515", "CMF003_516",
			"CMF003_517", "CMF003_518", "CMF003_519", "CMF003_520", "CMF003_521", "CMF003_522", "CMF003_523",
			"CMF003_524", "CMF003_525", "CMF003_526", "CMF003_527", "CMF003_528", "CMF003_529", "CMF003_530",
			"CMF003_531", "CMF003_532", "CMF003_533", "CMF003_534", "CMF003_535", "CMF003_536", "CMF003_537",
			"CMF003_538", "CMF003_539", "CMF003_540", "CMF003_541", "CMF003_542", "CMF003_543", "CMF003_544",
			"CMF003_545", "CMF003_546", "CMF003_547", "CMF003_548", "CMF003_549", "CMF003_550", "CMF003_551",
			"CMF003_552", "CMF003_553", "CMF003_554", "CMF003_555", "CMF003_556", "CMF003_557", "CMF003_558",
			"CMF003_559", "CMF003_560", "CMF003_561", "CMF003_562", "CMF003_563", "CMF003_564", "CMF003_565",
			"CMF003_566", "CMF003_567", "CMF003_568", "CMF003_569", "CMF003_570", "CMF003_571", "CMF003_572",
			"CMF003_573", "CMF003_574", "CMF003_575", "CMF003_576", "CMF003_577", "CMF003_578", "CMF003_579",
			"CMF003_580", "CMF003_581", "CMF003_582", "CMF003_583", "CMF003_584");

	private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV2 = Arrays.asList("ヘッダ部", "SID", "SCD",
			"BUSINESS_NAME");

	/*
	 * private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV3 =
	 * Arrays.asList("ヘッダ部", "INS_DATE", "INS_CCD", "INS_SCD", "INS_PG",
	 * "UPD_DATE", "UPD_CCD", "UPD_SCD", "UPD_PG", "EXCLUS_VER", "CID",
	 * "MANAGE_ATR", "PERMIT_ATR", "PRIORITY_TYPE");
	 */

	private static final String PGID = "CMF003";

	private static final String FILE_EXTENSION = ".csv";
	private static final String FILE_NAME_CSV1 = "保存対象テーブル一覧";
	private static final String FILE_NAME_CSV2 = "対象社員";

	private List<String> getTextHeader() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_TABLE) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

	private List<String> getTextHeaderCSV2() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV2) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

	private List<String> getTextHeaderCsv3(TableList tableList) {
		List<String> columnNames = EntityTypeUtil.getAllColumnNames(tableList.getTableEnglishName());
		List<String> columnFix = new ArrayList<>(
				Arrays.asList("H_CID", "H_SID", "H_DATE", "H_DATE_START", "H_DATE_END"));
		List<String> columnDynamic = new ArrayList<>();
		for (String columnName : columnNames) {
			if (tableList.getFieldAcqCid().equals(columnName)) {
				columnFix.set(0, columnName);
			} else if (tableList.getFieldAcqEmployeeId().equals(columnName)) {
				columnFix.set(1, columnName);
			} else if (tableList.getFieldAcqDateTime().equals(columnName)) {
				columnFix.set(2, columnName);
			} else if (tableList.getFieldAcqStartDate().equals(columnName)) {
				columnFix.set(3, columnName);
			} else if (tableList.getFieldAcqEndDate().equals(columnName)) {
				columnFix.set(4, columnName);
			} else {
				columnDynamic.add(columnName);
			}
		}
		columnFix.addAll(columnDynamic);
		return columnFix;
	}

	private List<String> getTextHeaderCsv3(Class<?> type) {
		List<String> lstHeader = new ArrayList<>();
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(EmbeddedId.class)) {
				Class<?> pk = field.getType();
				for (Field fieldPk : pk.getDeclaredFields()) {
					Column columnPk = fieldPk.getDeclaredAnnotation(Column.class);
					if (columnPk != null)
						lstHeader.add(columnPk.name());
				}
			}
			Column column = field.getDeclaredAnnotation(Column.class);
			if (column != null)
				lstHeader.add(column.name());
		}

		return lstHeader;
	}

	/*
	 * private List<String> getTextHeaderCSV3() { List<String> lstHeader = new
	 * ArrayList<>(); for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV3) {
	 * lstHeader.add(TextResource.localize(nameId)); } return lstHeader; }
	 */
}
