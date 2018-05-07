/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMtRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

/**
 * @author nam.lh
 *
 */
@Stateless
public class ManualSetOfDataSaveService {
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

	public void serverManualSaveProcessing(String storeProcessingId) {
		// ドメインモデル「データ保存の保存結果」へ書き出す
		List<ResultOfSavingDto> resultSaving = repoResultSaving.getResultOfSavingById(storeProcessingId);
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
		List<ManualSetOfDataSave> manualSetOfDataSave = repoMalSetOfSave.getManualSetOfDataSaveById(storeProcessingId);
		// Optional<TargetEmployees> targetEmployees =
		// repoTargetEmp.getTargetEmployeesListById(storeProcessingId);
		if (manualSetOfDataSave.isEmpty()) {
			// Get list category from
			List<TargetCategoryDto> targetCategories = repoTargetCat.getTargetCategoryListById(storeProcessingId);
			List<String> categoryIds = targetCategories.stream().map(x -> {
				return x.getCategoryId();
			}).collect(Collectors.toList());
			List<Category> categorys = repoCategory.getCategoryByListId(categoryIds);
			List<CategoryFieldMt> categoryFieldMts = repoCateField.getCategoryFieldMtByListId(categoryIds);

			// update domain 「データ保存動作管理」 Data storage operation management
			storeProcessingId = domain.getStoreProcessingId();
			categoryTotalCount = categorys.size();
			categoryCount = 1;
			operatingCondition = OperatingCondition.SAVING;
			repoDataSto.update(storeProcessingId, categoryTotalCount, categoryCount, operatingCondition);

			// Execute algorithm "Save target data"
			// Create TEMP folder
			File nameFoderTemp = new File("C:\\Temp");
			if (!nameFoderTemp.exists()) {
				if (nameFoderTemp.mkdir()) {
					System.out.println("Directory temp is created!");
				} else {
					System.out.println("Failed to create directory temp!");
				}
			}

			// Add Table to CSV
			generalCsv(storeProcessingId);

			// Add Table to CSV2
			generalCsv2(storeProcessingId);
		}

	}

	private void generalCsv(String storeProcessingId) {
		List<ResultOfSavingDto> resultSaving = repoResultSaving.getResultOfSavingById(storeProcessingId);
		//List<ManualSetOfDataSave> otpMal = Arrays.asList(optManualSetting.get());
		List<String> header = this.getTextHeader();
		// Get data from Manual Setting table
		List<Map<String, Object>> dataSourceCsv = new ArrayList<>();
		for (ResultOfSavingDto result : resultSaving) {
			Map<String, Object> rowCsv = new HashMap<>();
			rowCsv.put(header.get(0), result.getStoreProcessingId());
			rowCsv.put(header.get(1), result.getSaveForm());
			rowCsv.put(header.get(2), result.getSaveSetCode());
			rowCsv.put(header.get(3), result.getSaveName());
			dataSourceCsv.add(rowCsv);
		}
	}

	private void generalCsv2(String storeProcessingId) {
		List<TargetEmployeesDto> targetEmployees = repoTargetEmp.getTargetEmployeesListById(storeProcessingId);
		// List<TargetEmployees> otpTarEmp =
		// Arrays.asList(targetEmployees.get());
		// Add Table to CSV2
		List<String> header = this.getTextHeaderCSV2();
		List<Map<String, Object>> dataSourceCsv2 = new ArrayList<>();
		for (TargetEmployeesDto targetEmp : targetEmployees) {
			Map<String, Object> rowCsv2 = new HashMap<>();
			rowCsv2.put(header.get(0), targetEmp.getSid());
			rowCsv2.put(header.get(1), targetEmp.getScd());
			rowCsv2.put(header.get(2), targetEmp.getBusinessname());
			dataSourceCsv2.add(rowCsv2);
		}
		CSVFileData fileData = new CSVFileData(PGID + "対象社員" + AppContexts.user().companyCode() + FILE_EXTENSION,
				header, dataSourceCsv2);
		FileGeneratorContext generatorContext = new FileGeneratorContext();
		generator.generate(generatorContext, fileData);
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

	private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV3 = Arrays.asList("ヘッダ部", "INS_DATE", "INS_CCD",
			"INS_SCD", "INS_PG", "UPD_DATE", "UPD_CCD", "UPD_SCD", "UPD_PG", "EXCLUS_VER", "CID", "MANAGE_ATR",
			"PERMIT_ATR", "PRIORITY_TYPE");

	private static final String PGID = "CMF003";

	private static final String FILE_EXTENSION = ".csv";

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
	
	private List<String> getTextHeaderCSV3() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV3) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

}
