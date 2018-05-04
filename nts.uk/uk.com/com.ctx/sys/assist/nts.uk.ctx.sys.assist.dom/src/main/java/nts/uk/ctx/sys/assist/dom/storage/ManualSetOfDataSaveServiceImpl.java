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
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
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
public class ManualSetOfDataSaveServiceImpl implements ManualSetOfDataSaveService {
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
	private TargetEmployeesRepository repoTargetEmp;
	@Inject
	private CSVReportGenerator generator;

	@Override
	public void serverManualSaveProcessing(String storeProcessingId) {
		// ドメインモデル「データ保存の保存結果」へ書き出す
		Optional<ResultOfSaving> otpResultSaving = repoResultSaving.getResultOfSavingById(storeProcessingId);
		// ドメインモデル「データ保存動作管理」を登録する ( Save data to Data storage operation
		// management )
		int categoryTotalCount = 0;
		int categoryCount = 0;
		int errorCount = 0;
		OperatingCondition operatingCondition = EnumAdaptor.valueOf(0, OperatingCondition.class);
		NotUseAtr doNotInterrupt = EnumAdaptor.valueOf(0, NotUseAtr.class);
		DataStorageMng domain = new DataStorageMng(storeProcessingId, doNotInterrupt, categoryCount, categoryTotalCount,
				errorCount, operatingCondition);

		repoDataSto.add(domain);

		// Selection of target table and condition setting
		Optional<ManualSetOfDataSave> optManualSetting = repoMalSetOfSave.getManualSetOfDataSaveById(storeProcessingId);
		//Optional<TargetEmployees> targetEmployees = repoTargetEmp.getTargetEmployeesListById(storeProcessingId);
		if (optManualSetting.isPresent()) {
			//List<TargetEmployees> otpTarEmp = Arrays.asList(targetEmployees.get());
			List<ManualSetOfDataSave> otpMal = Arrays.asList(optManualSetting.get());
			List<TargetCategory> targetCategories = optManualSetting.get().getCategory();
			List<String> categoryIds = targetCategories.stream().map(x -> {
				return x.getCategoryId();
			}).collect(Collectors.toList());
			List<Category> categorys = repoCategory.getCategoryByListId(categoryIds);
			List<CategoryFieldMt> categoryFieldMts = repoCateField.getCategoryFieldMtByListId(categoryIds);

			// update domain 「データ保存動作管理」 Data storage operation management
			storeProcessingId = domain.getStoreProcessingId();
			categoryTotalCount = categorys.size();
			categoryCount = 1;
			operatingCondition = EnumAdaptor.valueOf(1, OperatingCondition.class);
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
			List<String> header = this.getTextHeader();
			// Get data from Manual Setting table
			Map<String, Object> row = new HashMap<>();
			row.put(header.get(0), otpMal.get(1));

			// Add Table to CSV2
			generalCsv2(storeProcessingId);
		}

	}

	private void generalCsv2(String storeProcessingId) {
		Optional<TargetEmployees> targetEmployees = repoTargetEmp.getTargetEmployeesListById(storeProcessingId);
		List<TargetEmployees> otpTarEmp = Arrays.asList(targetEmployees.get());
		// Add Table to CSV2
		List<String> header = this.getTextHeader();
		List<Map<String, Object>> dataSourceCsv2 = new ArrayList<>();
		for (TargetEmployees targetEmp : otpTarEmp) {
			Map<String, Object> rowCsv2 = new HashMap<>();
			rowCsv2.put(header.get(0), targetEmp.getSid());
			rowCsv2.put(header.get(1), targetEmp.getScd());
			rowCsv2.put(header.get(2), targetEmp.getBusinessname());
			dataSourceCsv2.add(rowCsv2);
		}
		CSVFileData fileData = new CSVFileData(PGID + "対象社員" + AppContexts.user().companyCode() + FILE_EXTENSION,
				header, dataSourceCsv2);
		FileGeneratorContext generatorContext = null;
		generator.generate(generatorContext, fileData);
	}

	private static final List<String> LST_NAME_ID_HEADER_TABLE = Arrays.asList("CMF003_1", "CMF003_2", "CMF003_3",
			"CMF003_4", "CMF003_5");

	private static final String PGID = "CMF003";

	private static final String FILE_EXTENSION = ".csv";

	private List<String> getTextHeader() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER_TABLE) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

}
