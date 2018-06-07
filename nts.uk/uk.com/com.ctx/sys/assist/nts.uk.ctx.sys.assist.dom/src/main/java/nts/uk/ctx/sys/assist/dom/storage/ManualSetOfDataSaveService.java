/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import java.lang.reflect.Field;
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
import nts.uk.shr.infra.data.entity.EntityTypeUtil;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

/**
 * @author nam.lh
 *
 */
@Stateless
public class ManualSetOfDataSaveService extends ExportService<Object> {

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

	private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV3 = Arrays.asList("H_CID", "H_SID", "H_DATE",
			"H_DATE_START", "H_DATE_END");

	private static final String CSV_EXTENSION = ".csv";
	private static final String ZIP_EXTENSION = ".zip";
	private static final String FILE_NAME_CSV1 = "保存対象テーブル一覧";
	private static final String FILE_NAME_CSV2 = "対象社員";
	private static final int NUM_OF_TABLE_EACH_PROCESS = 100;

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

	@Override
	protected void handle(ExportServiceContext<Object> context) {
		String storeProcessingId = context.getQuery().toString();
		serverManualSaveProcessing(storeProcessingId, context.getGeneratorContext());
	}

	public void serverManualSaveProcessing(String storeProcessingId, FileGeneratorContext generatorContext) {
		// Selection of target table and condition setting
		Optional<ManualSetOfDataSave> optManualSetting = repoMalSetOfSave.getManualSetOfDataSaveById(storeProcessingId);

		if (optManualSetting.isPresent()) {
			// ドメインモデル「データ保存の保存結果」へ書き出す
			String cid = optManualSetting.get().getCid();
			int systemType = optManualSetting.get().getSystemType().value;
			String practitioner = optManualSetting.get().getPractitioner();
			int saveForm = 0;
			String saveSetCode = null;
			String saveName = optManualSetting.get().getSaveSetName().v();
			int saveForInvest = optManualSetting.get().getIdentOfSurveyPre().value;
			GeneralDateTime saveStartDatetime = GeneralDateTime.now();

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

			// ドメインモデル「データ保存動作管理」を登録する
			repoDataSto.update(storeProcessingId, OperatingCondition.INPREPARATION);

			// アルゴリズム「対象テーブルの選定と条件設定」を実行
			int countCategories = selectTargetTable(storeProcessingId, optManualSetting.get());

			// update domain 「データ保存動作管理」 Data storage operation management
			repoDataSto.update(storeProcessingId, countCategories, 1, OperatingCondition.SAVING);

			// 対象社員のカウント件数を取り保持する
			List<TargetEmployees> targetEmployees = repoTargetEmp.getTargetEmployeesListById(storeProcessingId);

			// アルゴリズム「対象データの保存」を実行
			ResultState resultState = saveTargetData(storeProcessingId, generatorContext, optManualSetting.get(),
					targetEmployees);

			// 処理結果を判定
			switch (resultState) {
			case NORMAL_END:
				evaluateNormalEnd(storeProcessingId, generatorContext, optManualSetting.get(), targetEmployees);
				break;

			case ABNORMAL_END:
				evaluateAbnormalEnd(storeProcessingId, targetEmployees);
				break;

			case INTERRUPTION:
				evaluateInterruption(storeProcessingId, targetEmployees);
				break;
			}

		}

	}

	private int selectTargetTable(String storeProcessingId, ManualSetOfDataSave optManualSetting) {
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
			String screenRetentionPeriod = "";
			GeneralDate saveDateFrom = null;
			GeneralDate saveDateTo = null;
			int surveyPreservation = optManualSetting.getIdentOfSurveyPre().value;
			String compressedFileName = "";
			Optional<Category> category = categorys.stream()
					.filter(c -> c.getCategoryId().v().equals(categoryFieldMt.getCategoryId())).findFirst();
			if (category.isPresent()) {
				categoryName = category.get().getCategoryName().v();
				storageRangeSaved = category.get().getStorageRangeSaved().toString();
				retentionPeriodCls = category.get().getTimeStore();
				anotherComCls = category.get().getOtherCompanyCls() != null ? category.get().getOtherCompanyCls().value : null;
				
				switch (retentionPeriodCls) {
				case DAILY:
					saveDateFrom = optManualSetting.getDaySaveStartDate();
					saveDateTo = optManualSetting.getDaySaveEndDate();
					screenRetentionPeriod = saveDateFrom.toString("yyyy/MM/dd") + "～" + saveDateTo.toString("yyyy/MM/dd");
					break;
				case MONTHLY:
					saveDateFrom = optManualSetting.getMonthSaveStartDate();
					saveDateTo = optManualSetting.getMonthSaveEndDate();
					screenRetentionPeriod = saveDateFrom.toString("yyyy/MM") + "～" + saveDateTo.toString("yyyy/MM");
					break;
				case ANNUAL:
					saveDateFrom = GeneralDate.ymd(optManualSetting.getStartYear().v(), 1, 1);
					saveDateTo = GeneralDate.ymd(optManualSetting.getEndYear().v(), 12, 31);
					screenRetentionPeriod = saveDateFrom.toString("yyyy") + "～" + saveDateTo.toString("yyyy");
					break;
				default:
					break;
				}
				
			}
			String internalFileName = storeProcessingId + categoryName + categoryFieldMt.getTableJapanName();
			
			// B42
			String datetimenow = LocalDateTime.now().toString();
			SaveSetName savename = optManualSetting.getSaveSetName();
			compressedFileName = storeProcessingId + savename.toString() + datetimenow;

			

			TableList listtable = new TableList(categoryFieldMt.getCategoryId(), categoryName, storeProcessingId, "",
					categoryFieldMt.getTableNo(), categoryFieldMt.getTableJapanName(), categoryFieldMt.getTableEnglishName(),
					categoryFieldMt.getFieldAcqCid(), categoryFieldMt.getFieldAcqDateTime(),
					categoryFieldMt.getFieldAcqEmployeeId(), categoryFieldMt.getFieldAcqEndDate(),
					categoryFieldMt.getFieldAcqStartDate(), "", optManualSetting.getSaveSetName().toString(), "", "0",
					saveDateFrom, saveDateTo, storageRangeSaved,
					retentionPeriodCls != null ? retentionPeriodCls.value : null, internalFileName,
					anotherComCls, "", "", compressedFileName, categoryFieldMt.getFieldChild1(),
					categoryFieldMt.getFieldChild2(), categoryFieldMt.getFieldChild3(),
					categoryFieldMt.getFieldChild4(), categoryFieldMt.getFieldChild5(),
					categoryFieldMt.getFieldChild6(), categoryFieldMt.getFieldChild7(),
					categoryFieldMt.getFieldChild8(), categoryFieldMt.getFieldChild9(),
					categoryFieldMt.getFieldChild10(),
					categoryFieldMt.getHistoryCls() != null ? categoryFieldMt.getHistoryCls().value : null
					, "", "",
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
					categoryFieldMt.getHasParentTblFlg() != null ? categoryFieldMt.getHasParentTblFlg().value : 0 ,
					categoryFieldMt.getParentTblName(),
					categoryFieldMt.getFieldParent1(), categoryFieldMt.getFieldParent2(),
					categoryFieldMt.getFieldParent3(), categoryFieldMt.getFieldParent4(),
					categoryFieldMt.getFieldParent5(), categoryFieldMt.getFieldParent6(),
					categoryFieldMt.getFieldParent7(), categoryFieldMt.getFieldParent8(),
					categoryFieldMt.getFieldParent9(), categoryFieldMt.getFieldParent10(), surveyPreservation);

			repoTableList.add(listtable);
		}
		return categorys.size();
	}

	private ResultState saveTargetData(String storeProcessingId, FileGeneratorContext generatorContext,
			ManualSetOfDataSave optManualSetting, List<TargetEmployees> targetEmployees) {
		// アルゴリズム「対象データの保存」を実行
		ResultState resultState;

		List<SaveTargetCsv> listSaveTargetCsv = csvRepo.getSaveTargetCsvById(storeProcessingId);

		// テーブル一覧の内容をテンポラリーフォルダにcsvファイルで書き出す
		resultState = generalCsv(generatorContext, listSaveTargetCsv);

		if (resultState != ResultState.NORMAL_END) {
			return resultState;
		}

		// 「テーブル一覧」の調査保存の識別が「する」の場合、ドメインモデル「対象社員」のビジネスネームを全てNULLクリアする
		if (optManualSetting.getIdentOfSurveyPre() == NotUseAtr.USE) {
			repoTargetEmp.removeBusinessName(storeProcessingId);
		}

		// 対象社員の内容をcsvファイルに暗号化して書き出す
		resultState = generalCsv2(generatorContext, targetEmployees);

		if (resultState != ResultState.NORMAL_END) {
			return resultState;
		}

		// Add Table to CSV Auto
		resultState = generalCsvAuto(generatorContext, storeProcessingId);

		return resultState;
	}

	private ResultState generalCsv(FileGeneratorContext generatorContext, List<SaveTargetCsv> listSaveTargetCsv) {
		try {
			List<String> headerCsv = this.getTextHeader();
			// Get data from Manual Setting table
			List<Map<String, Object>> dataSourceCsv = new ArrayList<>();
			for (SaveTargetCsv dataTarget : listSaveTargetCsv) {
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

			CSVFileData fileData = new CSVFileData(FILE_NAME_CSV1 + CSV_EXTENSION, headerCsv, dataSourceCsv);
			generator.generate(generatorContext, fileData);
			return ResultState.NORMAL_END;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.ABNORMAL_END;
		}

	}

	private ResultState generalCsv2(FileGeneratorContext generatorContext, List<TargetEmployees> targetEmployees) {
		try {
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

			CSVFileData fileData = new CSVFileData(FILE_NAME_CSV2 + CSV_EXTENSION, headerCsv2, dataSourceCsv2);

			generator.generate(generatorContext, fileData);
			return ResultState.NORMAL_END;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.ABNORMAL_END;
		}

	}

	private ResultState generalCsvAuto(FileGeneratorContext generatorContext, String storeProcessingId) {
		try {
			int offset = 0;
			String categoryId = "";
			while (true) {
				// ドメインモデル「データ保存動作管理」を取得し「中断終了」を判別
				Optional<DataStorageMng> dataStorageMng = repoDataSto.getDataStorageMngById(storeProcessingId);
				if (dataStorageMng.isPresent()
						&& dataStorageMng.get().operatingCondition == OperatingCondition.INTERRUPTION_END) {
					return ResultState.INTERRUPTION;
				}

				// テーブル一覧の１行分を処理する
				List<TableList> tableLists = repoTableList.getByOffsetAndNumber(offset, NUM_OF_TABLE_EACH_PROCESS);
				for (TableList tableList : tableLists) {
					List<?> listObject = repoTableList.getDataDynamic(tableList);

					// Add Table to CSV Auto
					List<String> headerCsv = this.getTextHeaderCsv3(tableList);
					List<Map<String, Object>> dataSourceCsv = new ArrayList<>();
					for (Object object : listObject) {
						Map<String, Object> rowCsv = new HashMap<>();

						for (String key : headerCsv) {
							String header = key;
							if (key.equals(LST_NAME_ID_HEADER_TABLE_CSV3.get(0))
									&& !Strings.isNullOrEmpty(tableList.getFieldAcqCid())) {
								header = tableList.getFieldAcqCid();
							}
							if (key.equals(LST_NAME_ID_HEADER_TABLE_CSV3.get(1))
									&& !Strings.isNullOrEmpty(tableList.getFieldAcqEmployeeId())) {
								header = tableList.getFieldAcqEmployeeId();
							}
							if (key.equals(LST_NAME_ID_HEADER_TABLE_CSV3.get(2))
									&& !Strings.isNullOrEmpty(tableList.getFieldAcqDateTime())) {
								header = tableList.getFieldAcqDateTime();
							}
							if (key.equals(LST_NAME_ID_HEADER_TABLE_CSV3.get(3))
									&& !Strings.isNullOrEmpty(tableList.getFieldAcqStartDate())) {
								header = tableList.getFieldAcqStartDate();
							}
							if (key.equals(LST_NAME_ID_HEADER_TABLE_CSV3.get(4))
									&& !Strings.isNullOrEmpty(tableList.getFieldAcqEndDate())) {
								header = tableList.getFieldAcqEndDate();
							}
							String fieldName = repoTableList.getFieldForColumnName(object.getClass(), header);
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

					CSVFileData fileData = new CSVFileData(AppContexts.user().companyCode()
							+ tableList.getCategoryName() + tableList.getTableJapaneseName() + CSV_EXTENSION, headerCsv,
							dataSourceCsv);

					generator.generate(generatorContext, fileData);

					// テーブル一覧で次の処理行のカテゴリが異なる場合
					// ドメインモデル「データ保存動作管理」を更新する
					if (!tableList.getCategoryId().equals(categoryId)) {
						categoryId = tableList.getCategoryId();
						repoDataSto.increaseCategoryCount(storeProcessingId);
					}
				}

				offset += NUM_OF_TABLE_EACH_PROCESS;
				// テーブルをすべて書き出したか判定
				if (tableLists.size() < NUM_OF_TABLE_EACH_PROCESS)
					break;
			}

			return ResultState.NORMAL_END;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.ABNORMAL_END;
		}
	}

	private ResultState evaluateNormalEnd(String storeProcessingId, FileGeneratorContext generatorContext,
			ManualSetOfDataSave optManualSetting, List<TargetEmployees> targetEmployees) {
		// ドメインモデル「データ保存動作管理」を更新する
		repoDataSto.update(storeProcessingId, OperatingCondition.INPROGRESS);

		try {
			// アルゴリズム「結果ファイルの圧縮」を実行
			ApplicationTemporaryFilesContainer applicationTemporaryFilesContainer = applicationTemporaryFileFactory
					.createContainer();
			NotUseAtr passwordAvailability = optManualSetting.getPasswordAvailability();
			String fileName = AppContexts.user().companyCode() + ZIP_EXTENSION;
			if (passwordAvailability == NotUseAtr.NOT_USE) {
				applicationTemporaryFilesContainer.zipWithName(generatorContext, fileName);
			}
			if (passwordAvailability == NotUseAtr.USE) {
				String password = optManualSetting.getCompressedPassword().v();
				applicationTemporaryFilesContainer.zipWithName(generatorContext, fileName,
						CommonKeyCrypt.encrypt(password));
			}

			applicationTemporaryFilesContainer.removeContainer();
		} catch (Exception e) {
			e.printStackTrace();
			return evaluateAbnormalEnd(storeProcessingId, targetEmployees);
		}

		// ドメインモデル「データ保存動作管理」を更新する
		repoDataSto.update(storeProcessingId, OperatingCondition.DONE);

		// ドメインモデル「データ保存の保存結果」を書き出し
		String fileId = generatorContext.getTaskId();
		repoResultSaving.update(storeProcessingId, targetEmployees.size(), SaveStatus.SUCCESS, fileId,
				NotUseAtr.NOT_USE);

		return ResultState.NORMAL_END;
	}

	private ResultState evaluateAbnormalEnd(String storeProcessingId, List<TargetEmployees> targetEmployees) {
		// ドメインモデル「データ保存動作管理」を更新する
		repoDataSto.update(storeProcessingId, OperatingCondition.ABNORMAL_TERMINATION);
		
		// ドメインモデル「データ保存の保存結果」を書き出し
		repoResultSaving.update(storeProcessingId, targetEmployees.size(), SaveStatus.FAILURE);
		
		return ResultState.ABNORMAL_END;
	}
	
	private ResultState evaluateInterruption(String storeProcessingId, List<TargetEmployees> targetEmployees) {
		// ドメインモデル「データ保存動作管理」を更新する
		repoDataSto.update(storeProcessingId, OperatingCondition.INTERRUPTION_END);
		
		// ドメインモデル「データ保存の保存結果」を書き出し
		repoResultSaving.update(storeProcessingId, targetEmployees.size(), SaveStatus.INTERRUPTION);
		
		return ResultState.INTERRUPTION;
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
		List<String> columnFix = new ArrayList<>(LST_NAME_ID_HEADER_TABLE_CSV3);
		columnFix.addAll(columnNames);
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
