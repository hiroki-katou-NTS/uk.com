package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.category.Association;
import nts.uk.ctx.exio.dom.exo.category.CategorySetting;
import nts.uk.ctx.exio.dom.exo.category.ExCndOutput;
import nts.uk.ctx.exio.dom.exo.category.ExCndOutputRepository;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtgRepository;
import nts.uk.ctx.exio.dom.exo.category.PhysicalProjectName;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetail;
import nts.uk.ctx.exio.dom.exo.cdconvert.ConvertCode;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvert;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.AwDataFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.DateFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.InstantTimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.NumberDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.TimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatCharacterDigit;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatDecimalDigit;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatFixedValueOperation;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatIntegerDigit;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatNullReplacement;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataTypeFixedValue;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateOutputFormat;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DecimalDivision;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DecimalPointClassification;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DecimalSelection;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DelimiterSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.init.EditSpace;
import nts.uk.ctx.exio.dom.exo.dataformat.init.FixedLengthEditingMethod;
import nts.uk.ctx.exio.dom.exo.dataformat.init.FixedValueOperationSymbol;
import nts.uk.ctx.exio.dom.exo.dataformat.init.HourMinuteClassification;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NextDayOutputMethod;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.PreviousDayOutputMethod;
import nts.uk.ctx.exio.dom.exo.dataformat.init.Rounding;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.execlog.ExecutionForm;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLogRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ProcessingClassification;
import nts.uk.ctx.exio.dom.exo.execlog.ResultStatus;
import nts.uk.ctx.exio.dom.exo.execlog.StandardClassification;
import nts.uk.ctx.exio.dom.exo.execlog.UploadFileName;
import nts.uk.ctx.exio.dom.exo.executionlog.ExIoOperationState;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.ConditionSymbol;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.OperationSymbol;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

@Stateless
public class CreateExOutTextService extends ExportService<Object> {

	@Inject
	private CSVReportGenerator generator;

	@Inject
	private CtgItemDataRepository ctgItemDataRepo;

	@Inject
	private ExOutOpMngRepository exOutOpMngRepo;

	@Inject
	private ExterOutExecLogRepository exterOutExecLogRepo;

	@Inject
	private ExternalOutLogRepository externalOutLogRepo;

	@Inject
	private AcquisitionExOutSetting acquisitionExOutSetting;

	@Inject
	private ExOutCtgRepository exOutCtgRepo;

	@Inject
	private ExCndOutputRepository exCndOutputRepo;

	@Inject
	private StandardOutputItemRepository stdOutItemRepo;

	@Inject
	private StandardOutputItemOrderRepository stdOutItemOrderRepo;

	@Inject
	private OutputCodeConvertRepository outputCodeConvertRepo;

	@Inject
	private StatusOfEmploymentAdapter statusOfEmploymentAdapter;

	@Inject
	private DataFormatSettingRepository dataFormatSettingRepo;

	private final static String GET_ASSOCIATION = "getOutCondAssociation";
	private final static String GET_ITEM_NAME = "getOutCondItemName";
	private final static String USE_NULL_VALUE_ON = "on";
	private final static String USE_NULL_VALUE_OFF = "off";
	private final static String RESULT_OK = "ok";
	private final static String RESULT_NG = "ng";
	private final static String RESULT_STATE = "state";
	private final static String RESULT_VALUE = "value";
	private final static String ERROR_MESS = "ErrorMessage";
	private final static String ITEM_VALUE = "itemValue";
	private final static String USE_NULL_VALUE = "useNullValue";
	private final static String LINE_DATA_CSV = "lineDataCSV";
	private final static String yyyyMMdd = "yyyyMMdd";

	@Override
	protected void handle(ExportServiceContext<Object> context) {
		ExOutSetting domain = (ExOutSetting) context.getQuery();
		executeServerExOutManual(domain, context.getGeneratorContext());
	}

	public void executeServerExOutManual(ExOutSetting exOutSetting, FileGeneratorContext generatorContext) {
		ExOutSettingResult settingResult = getServerExOutSetting(exOutSetting);
		if(settingResult == null) {
			finishFaultWhenStart(exOutSetting.getProcessingId());
			return;
		}
		initExOutLogInformation(exOutSetting);
		serverExOutExecution(generatorContext, exOutSetting, settingResult);
	}

	// サーバ外部出力設定取得
	private ExOutSettingResult getServerExOutSetting(ExOutSetting exOutSetting) {
		List<StdOutputCondSet> stdOutputCondSetList = acquisitionExOutSetting.getExOutSetting(null,
				StandardAtr.STANDARD, exOutSetting.getConditionSetCd());
		
		if(stdOutputCondSetList.size() == 0 ) return null;
		StdOutputCondSet stdOutputCondSet = stdOutputCondSetList.get(0);
		
		List<OutCndDetailItem> outCndDetailItemList = acquisitionExOutSetting
				.getExOutCond(exOutSetting.getConditionSetCd(), null, StandardAtr.STANDARD, true, null);
		List<OutputItemCustom> outputItemCustomList = getExOutItemList(exOutSetting.getConditionSetCd(), null, "",
				StandardAtr.STANDARD, true);
		List<CtgItemData> ctgItemDataList = new ArrayList<CtgItemData>();

		for (OutputItemCustom outputItemCustom : outputItemCustomList) {
			ctgItemDataList.addAll(outputItemCustom.getCtgItemDataList());
		}

		Optional<ExOutCtg> exOutCtg = exOutCtgRepo.getExOutCtgByIdAndCtgSetting(stdOutputCondSet.getCategoryId().v());
		Optional<ExCndOutput> exCndOutput = exCndOutputRepo.getExCndOutputById(stdOutputCondSet.getCategoryId().v());

		return new ExOutSettingResult(stdOutputCondSet, outCndDetailItemList, exOutCtg, exCndOutput,
				outputItemCustomList, ctgItemDataList);
	}
	
	private void finishFaultWhenStart(String processingId) {
		ExOutOpMng exOutOpMng = new ExOutOpMng(processingId, 0, 0, 0, NotUseAtr.NOT_USE.value, "",
				ExIoOperationState.FAULT_FINISH.value);
		exOutOpMngRepo.add(exOutOpMng);
	}

	// サーバ外部出力ログ情報初期値
	private void initExOutLogInformation(ExOutSetting exOutSetting) {
		String cid = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();

		ExOutOpMng exOutOpMng = new ExOutOpMng(exOutSetting.getProcessingId(), 0, 0, 0, NotUseAtr.NOT_USE.value, "",
				ExIoOperationState.PERPAKING.value);

		ExterOutExecLog exterOutExecLog = new ExterOutExecLog(cid, exOutSetting.getProcessingId(), null, 0, 0, null,
				null, NotUseAtr.USE.value, null, exOutSetting.getCategoryId(), null, null, GeneralDateTime.now(),
				StandardClassification.STANDARD.value, ExecutionForm.MANUAL_EXECUTION.value, sid,
				exOutSetting.getReferenceDate(), exOutSetting.getEndDate(), exOutSetting.getStartDate(),
				exOutSetting.getConditionSetCd(), null, null);

		ExternalOutLog externalOutLog = new ExternalOutLog(cid, exOutSetting.getProcessingId(), null, null, null, null,
				null, GeneralDateTime.now(), 0, 0, ProcessingClassification.START_PROCESSING.value);

		exOutOpMngRepo.add(exOutOpMng);
		exterOutExecLogRepo.add(exterOutExecLog);
		externalOutLogRepo.add(externalOutLog);
	}

	// サーバ外部出力実行
	private void serverExOutExecution(FileGeneratorContext generatorContext, ExOutSetting exOutSetting,
			ExOutSettingResult settingResult) {

		String processingId = exOutSetting.getProcessingId();
		ExIoOperationState state;
		Optional<ExOutCtg> exOutCtg = settingResult.getExOutCtg();
		StdOutputCondSet stdOutputCondSet = settingResult.getStdOutputCondSet();
		String settingName = "";
		if (stdOutputCondSet != null)
			settingName = stdOutputCondSet.getConditionSetName().v();
		String fileName = exOutSetting.getConditionSetCd() + settingName + processingId;

		Optional<ExOutOpMng> exOutOpMngOptional = exOutOpMngRepo.getExOutOpMngById(processingId);
		if (!exOutOpMngOptional.isPresent()) {
			state = ExIoOperationState.FAULT_FINISH;
			createOutputLogInfoEnd(generatorContext, processingId, state, fileName);
			return;
		}

		ExOutOpMng exOutOpMng = exOutOpMngOptional.get();
		exOutOpMng.setOpCond(ExIoOperationState.EXPORTING);
		if ((stdOutputCondSet == null) || !exOutCtg.isPresent()) {
			state = ExIoOperationState.FAULT_FINISH;
			createOutputLogInfoEnd(generatorContext, processingId, state, fileName);
			return;
		}

		if (exOutCtg.get().getCategorySet() == CategorySetting.DATA_TYPE) {
			exOutOpMng.setProUnit(I18NText.getText("#CMF002_527"));
			exOutOpMng.setProCnt(0);
			exOutOpMng.setTotalProCnt(exOutSetting.getSidList().size());
		} else {
			exOutOpMng.setProUnit(I18NText.getText("#CMF002_528"));
		}

		exOutOpMngRepo.update(exOutOpMng);

		CategorySetting type = exOutCtg.get().getCategorySet();
		state = serverExOutTypeDataOrMaster(type, generatorContext, exOutSetting, settingResult, fileName);

		createOutputLogInfoEnd(generatorContext, processingId, state, fileName);
	}

	// サーバ外部出力ログ情報終了値
	private void createOutputLogInfoEnd(FileGeneratorContext generatorContext, String processingId,
			ExIoOperationState operationState, String fileName) {
		String cid = AppContexts.user().companyId();
		Optional<ExOutOpMng> exOutOpMng = exOutOpMngRepo.getExOutOpMngById(processingId);

		if (!exOutOpMng.isPresent())
			return;
		exOutOpMng.get().setOpCond(operationState);
		exOutOpMngRepo.update(exOutOpMng.get());

		ExternalOutLog externalOutLog = new ExternalOutLog();
		externalOutLog.setCompanyId(cid);
		externalOutLog.setOutputProcessId(processingId);
		externalOutLog.setErrorContent(Optional.empty());
		externalOutLog.setErrorTargetValue(Optional.empty());
		externalOutLog.setErrorDate(Optional.empty());
		externalOutLog.setErrorEmployee(Optional.empty());
		externalOutLog.setErrorItem(Optional.empty());
		externalOutLog.setLogRegisterDateTime(GeneralDateTime.now());
		externalOutLog.setLogSequenceNumber(exOutOpMng.get().getErrCnt() + 1);
		externalOutLog.setProcessCount(exOutOpMng.get().getProCnt());
		externalOutLog.setProcessContent(ProcessingClassification.END_PROCESSING);
		externalOutLogRepo.add(externalOutLog);

		ResultStatus statusEnd;
		switch (operationState) {
		case INTER_FINISH:
			statusEnd = ResultStatus.INTERRUPTION;
			break;
		case EXPORT_FINISH:
			statusEnd = ResultStatus.SUCCESS;
			break;
		default:
			statusEnd = ResultStatus.FAILURE;
			break;
		}
		String fileId = generatorContext.getTaskId();
		Optional<ExterOutExecLog> exterOutExecLogOptional = exterOutExecLogRepo.getExterOutExecLogById(cid,
				processingId);
		if (!exterOutExecLogOptional.isPresent())
			return;
		ExterOutExecLog exterOutExecLog = exterOutExecLogOptional.get();
		exterOutExecLog.setProcessEndDateTime(Optional.of(GeneralDateTime.now()));
		exterOutExecLog.setFileId(Optional.of(fileId));
		exterOutExecLog.setFileName(Optional.of(new UploadFileName(fileName)));
		exterOutExecLog.setTotalCount(exOutOpMng.get().getProCnt());
		exterOutExecLog.setTotalErrorCount(exOutOpMng.get().getErrCnt());
		exterOutExecLog.setProcessUnit(Optional.of(exOutOpMng.get().getProUnit()));
		exterOutExecLog.setResultStatus(Optional.of(statusEnd));
		if (statusEnd == ResultStatus.SUCCESS)
			exterOutExecLog.setDeleteFile(NotUseAtr.NOT_USE);
		exterOutExecLogRepo.update(exterOutExecLog);

	}

	@SuppressWarnings("unchecked")
	private ExIoOperationState serverExOutTypeDataOrMaster(CategorySetting type, FileGeneratorContext generatorContext,
			ExOutSetting exOutSetting, ExOutSettingResult settingResult, String fileName) {
		String loginSid = AppContexts.user().employeeId();
		List<String> header = new ArrayList<>();
		List<Map<String, Object>> csvData = new ArrayList<>();
		StdOutputCondSet stdOutputCondSet = (StdOutputCondSet) settingResult.getStdOutputCondSet();
		List<OutputItemCustom> outputItemCustomList = settingResult.getOutputItemCustomList();
		Map<String, Object> lineDataResult;
		Map<String, Object> lineDataCSV;
		String stateResult;

		// サーバ外部出力ファイル項目ヘッダ
		if (stdOutputCondSet != null && (stdOutputCondSet.getConditionOutputName() == NotUseAtr.USE)) {
			header.add(stdOutputCondSet.getConditionSetName().v());
		}

		String sql;
		List<List<String>> data;

		// サーバ外部出力タイプデータ系
		if (type == CategorySetting.DATA_TYPE) {
			for (String sid : exOutSetting.getSidList()) {
				ExIoOperationState checkResult = checkInterruptAndIncreaseProCnt(exOutSetting.getProcessingId());
				if ((checkResult == ExIoOperationState.FAULT_FINISH)
						|| (checkResult == ExIoOperationState.INTER_FINISH)) return checkResult;
				
				sql = getExOutDataSQL(sid, true, exOutSetting, settingResult);
				data = exOutCtgRepo.getData(sql);

				for (List<String> lineData : data) {
					lineDataResult = fileLineDataCreation(exOutSetting.getProcessingId(), lineData,
							outputItemCustomList, sid);
					stateResult = (String) lineDataResult.get(RESULT_STATE);
					lineDataCSV = (Map<String, Object>) lineDataResult.get(LINE_DATA_CSV);
					if (RESULT_OK.equals(stateResult) && (lineDataCSV != null))
						csvData.add(lineDataCSV);
				}
			}
			// サーバ外部出力タイプマスター系
		} else {
			sql = getExOutDataSQL(loginSid, true, exOutSetting, settingResult);
			data = exOutCtgRepo.getData(sql);

			for (List<String> lineData : data) {
				ExIoOperationState checkResult = checkInterruptAndIncreaseProCnt(exOutSetting.getProcessingId());
				if ((checkResult == ExIoOperationState.FAULT_FINISH)
						|| (checkResult == ExIoOperationState.INTER_FINISH)) return checkResult;
				
				lineDataResult = fileLineDataCreation(exOutSetting.getProcessingId(), lineData, outputItemCustomList,
						loginSid);
				stateResult = (String) lineDataResult.get(RESULT_STATE);
				lineDataCSV = (Map<String, Object>) lineDataResult.get(LINE_DATA_CSV);
				if (RESULT_OK.equals(stateResult) && (lineDataCSV != null))
					csvData.add(lineDataCSV);
			}
		}

		CSVFileData fileData = new CSVFileData(fileName, header, csvData);
		generator.generate(generatorContext, fileData);

		return ExIoOperationState.EXPORT_FINISH;
	}

	public ExIoOperationState checkInterruptAndIncreaseProCnt(String processingId) {
		ExIoOperationState result = ExIoOperationState.EXPORTING;
		Optional<ExOutOpMng> exOutOpMng = exOutOpMngRepo.getExOutOpMngById(processingId);
		if (!exOutOpMng.isPresent()) {
			return ExIoOperationState.FAULT_FINISH;
		}

		if (exOutOpMng.get().getDoNotInterrupt() == NotUseAtr.USE) {
			return ExIoOperationState.INTER_FINISH;
		}

		exOutOpMng.get().setProCnt(exOutOpMng.get().getProCnt() + 1);
		exOutOpMngRepo.update(exOutOpMng.get());

		return result;
	}

	// サーバ外部出力データ取得SQL
	@SuppressWarnings("unchecked")
	private String getExOutDataSQL(String sid, boolean isdataType, ExOutSetting exOutSetting,
			ExOutSettingResult settingResult) {
		String cid = AppContexts.user().companyId();
		StringBuilder sql = new StringBuilder();
		String sidAlias = null;
		List<String> keyOrderList = new ArrayList<String>();
		sql.append("select");

		List<CtgItemData> ctgItemDataList = settingResult.getCtgItemDataList();
		for (CtgItemData ctgItemData : ctgItemDataList) {
			sql.append(ctgItemData.getTblAlias());
			sql.append(".");
			sql.append(ctgItemData.getFieldName());
			sql.append(" ");
		}

		sql.append("from ");

		Optional<ExCndOutput> exCndOutput = settingResult.getExCndOutput();
		if(exCndOutput.isPresent()) {
			ExCndOutput item = exCndOutput.get();
			sql.append(item.getForm1().v());
			if (StringUtils.isNotBlank(item.getForm1().v()) && StringUtils.isNotBlank(item.getForm2().v()))
				sql.append(", ");
			sql.append(item.getForm2().v());
			sql.append(" ");
			sql.append("where ");

			boolean isDate = false;
			boolean isOutDate = false;
			String startDateItemName = "";
			String endDateItemName = "";
			Method getAssociation;
			Method getItemName;
			Optional<Association> asssociation;
			Optional<PhysicalProjectName> itemName;
			try {
				for (int i = 1; i <= 10; i++) {
					getAssociation = ExCndOutput.class.getMethod(GET_ASSOCIATION + i);
					getItemName = ExCndOutput.class.getMethod(GET_ITEM_NAME + i);

					asssociation = (Optional<Association>) getAssociation.invoke(null);
					itemName = (Optional<PhysicalProjectName>) getItemName.invoke(null);

					if (!asssociation.isPresent() || !itemName.isPresent()) {
						continue;
					}

					if (asssociation.get() == Association.CCD) {
						createWhereCondition(sql, exCndOutput.get().getMainTable().v(), itemName.get().v(), "=", cid);
					} else if (asssociation.get() == Association.ECD) {
						sidAlias = exCndOutput.get().getMainTable().v() + "." + itemName.get().v();
						createWhereCondition(sql, exCndOutput.get().getMainTable().v(), itemName.get().v(), "=", sid);
					} else if (asssociation.get() == Association.DATE) {
						if (!isDate) {
							isDate = true;
							startDateItemName = itemName.get().v();
						} else {
							isOutDate = true;
							endDateItemName = itemName.get().v();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (isOutDate) {
				createWhereCondition(sql, exCndOutput.get().getMainTable().v(), startDateItemName, " <= ",
						"'" + exOutSetting.getEndDate().toString() + "'");
				createWhereCondition(sql, exCndOutput.get().getMainTable().v(), endDateItemName, " >= ",
						"'" + exOutSetting.getStartDate().toString() + "'");
			} else if (isDate) {
				createWhereCondition(sql, exCndOutput.get().getMainTable().v(), startDateItemName, " >= ",
						"'" + exOutSetting.getStartDate().toString() + "'");
				createWhereCondition(sql, exCndOutput.get().getMainTable().v(), startDateItemName, " <= ",
						"'" + exOutSetting.getEndDate().toString() + "'");
			}

			if (exCndOutput.get().getConditions().v().length() > 0) {
				sql.append(exCndOutput.get().getConditions().v());
				sql.append(" and ");
			}

			String value = "";
			String value1 = "";
			String value2 = "";
			String operator = "";
			String searchCodeListCond;

			List<OutCndDetailItem> outCndDetailItemList = settingResult.getOutCndDetailItem();
			for (OutCndDetailItem outCndDetailItem : outCndDetailItemList) {
				searchCodeListCond = (outCndDetailItem.getJoinedSearchCodeList() != null) ? 
						outCndDetailItem.getJoinedSearchCodeList() : "";
				switch (outCndDetailItem.getConditionSymbol()) {
				case CONTAIN:
					operator = " like ";
					break;
				case BETWEEN:
					break;
				case IS:
					operator = " = ";
					break;
				case IS_NOT:
					operator = " <> ";
					break;
				case GREATER:
					operator = " > ";
					break;
				case LESS:
					operator = " < ";
					break;
				case GREATER_OR_EQUAL:
					operator = " >= ";
					break;
				case LESS_OR_EQUAL:
					operator = " <= ";
					break;
				case IN:
					operator = " in ";
					break;
				case NOT_IN:
					operator = " not in ";
					break;

				default:
					break;
				}

				for (CtgItemData ctgItemData : ctgItemDataList) {
					switch (ctgItemData.getDataType()) {
					case NUMERIC:
						value = outCndDetailItem.getSearchNum().map(i -> i.v().toString()).orElse("");
						value1 = outCndDetailItem.getSearchNumStartVal().map(i -> i.v().toString()).orElse("");
						value2 = outCndDetailItem.getSearchNumEndVal().map(i -> i.v().toString()).orElse("");
						break;
					case CHARACTER:
						if (outCndDetailItem.getConditionSymbol() != ConditionSymbol.CONTAIN) {
							value = "'" + outCndDetailItem.getSearchChar().map(i -> i.v()).orElse("") + "'";
							value1 = "'" + outCndDetailItem.getSearchCharStartVal().map(i -> i.v()).orElse("") + "'";
							value2 = "'" + outCndDetailItem.getSearchCharEndVal().map(i -> i.v()).orElse("") + "'";
						} else {
							value = "'%" + outCndDetailItem.getSearchChar().map(i -> i.v()).orElse("") + "%'";
							value1 = "'%" + outCndDetailItem.getSearchCharStartVal().map(i -> i.v()).orElse("") + "%'";
							value2 = "'%" + outCndDetailItem.getSearchCharEndVal().map(i -> i.v()).orElse("") + "%'";
						}
						break;
					case DATE:
						value = "'" + outCndDetailItem.getSearchDate().map(i -> i.toString(yyyyMMdd)).orElse("") + "'";
						value1 = "'" + outCndDetailItem.getSearchDateStart().map(i -> i.toString(yyyyMMdd)).orElse("") + "'";
						value2 = "'" + outCndDetailItem.getSearchDateEnd().map(i -> i.toString(yyyyMMdd)).orElse("") + "'";
						break;
					case TIME:
						value = outCndDetailItem.getSearchClock().map(i -> i.v().toString()).orElse("");
						value1 = outCndDetailItem.getSearchClockStartVal().map(i -> i.v().toString()).orElse("");
						value2 = outCndDetailItem.getSearchClockEndVal().map(i -> i.v().toString()).orElse("");
						break;
					case INS_TIME:
						value = outCndDetailItem.getSearchTime().map(i -> i.v().toString()).orElse("");
						value1 = outCndDetailItem.getSearchTimeStartVal().map(i -> i.v().toString()).orElse("");
						value2 = outCndDetailItem.getSearchTimeEndVal().map(i -> i.v().toString()).orElse("");
						break;

					default:
						break;
					}

					if ((outCndDetailItem.getConditionSymbol() == ConditionSymbol.IN)
							|| (outCndDetailItem.getConditionSymbol() == ConditionSymbol.NOT_IN))
						value = "(" + searchCodeListCond + ")";

					if (outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
						createWhereCondition(sql, ctgItemData.getTblAlias(), ctgItemData.getFieldName(), " >= ",
								value1);
						createWhereCondition(sql, ctgItemData.getTblAlias(), ctgItemData.getFieldName(), " <= ",
								value2);
					} else {
						createWhereCondition(sql, ctgItemData.getTblAlias(), ctgItemData.getFieldName(), operator,
								value);
					}

					ctgItemData.getPrimarykeyClassfication().ifPresent(primaryKey -> {
						if (primaryKey == NotUseAtr.USE) {
							keyOrderList.add(ctgItemData.getTblAlias() + "." + ctgItemData.getFieldName());
						}
					});
				}
			}
		}

		sql.setLength(sql.length() - 4);

		if (isdataType) {
			if(sidAlias != null) {
				sql.append(" order by ");
				sql.append(sidAlias);
				sql.append(" asc;");
			}
		} else {
			if(!keyOrderList.isEmpty()) {
				sql.append(" order by ");
				
				for (String keyOrder : keyOrderList) {
					sql.append(keyOrder);
					sql.append(", ");
				}
	
				sql.setLength(sql.length() - 2);
				sql.append(" asc;");
			}
		}

		return sql.toString();
	}

	// サーバ外部出力ファイル行データ作成
	private Map<String, Object> fileLineDataCreation(String processingId, List<String> lineData,
			List<OutputItemCustom> outputItemCustomList, String sid) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> lineDataCSV = new HashMap<String, Object>();
		String targetValue = "";
		NotUseAtr isfixedValue = NotUseAtr.NOT_USE;
		String fixedValue = "";
		boolean isSetNull = false;
		String nullValueReplace = "";
		DataFormatSetting dataFormatSetting;
		Map<String, String> fileItemDataCreationResult;
		Map<String, String> fileItemDataCheckedResult;

		String useNullValue;
		String resultState;
		String errorMess;

		for (OutputItemCustom outputItemCustom : outputItemCustomList) {
			int index = 0;
			dataFormatSetting = outputItemCustom.getDataFormatSetting();

			isfixedValue = dataFormatSetting.getFixedValue();
			fixedValue = dataFormatSetting.getValueOfFixedValue().map(item -> item.v()).orElse("");
			isSetNull = (dataFormatSetting.getNullValueReplace() == NotUseAtr.USE);
			nullValueReplace = dataFormatSetting.getValueOfNullValueReplace().map(item -> item.v()).orElse("");

			if (isfixedValue == NotUseAtr.USE) {
				targetValue = fixedValue;
				lineDataCSV.put(outputItemCustom.getStandardOutputItem().getOutputItemName().v(), targetValue);
				index += outputItemCustom.getCtgItemDataList().size();
				continue;
			}

			fileItemDataCreationResult = fileItemDataCreation(lineData, outputItemCustom, isSetNull, nullValueReplace,
					index);
			targetValue = fileItemDataCreationResult.get(ITEM_VALUE);
			useNullValue = fileItemDataCreationResult.get(USE_NULL_VALUE);

			if (useNullValue == USE_NULL_VALUE_ON) {
				lineDataCSV.put(outputItemCustom.getStandardOutputItem().getOutputItemName().v(), targetValue);
				index += outputItemCustom.getCtgItemDataList().size();
				continue;
			}

			fileItemDataCheckedResult = checkOutputFileType(targetValue,
					outputItemCustom.getStandardOutputItem().getItemType(), outputItemCustom.getDataFormatSetting(),
					sid);
			resultState = fileItemDataCheckedResult.get(RESULT_STATE);
			errorMess = fileItemDataCheckedResult.get(ERROR_MESS);
			targetValue = fileItemDataCheckedResult.get(RESULT_VALUE);

			if (RESULT_NG.equals(resultState)) {
				createOutputLogError(processingId, errorMess, targetValue, sid,
						outputItemCustom.getStandardOutputItem().getOutputItemName().v());
				result.put(RESULT_STATE, RESULT_NG);
				result.put(LINE_DATA_CSV, lineDataCSV);
				return lineDataCSV;
			}

			lineDataCSV.put(outputItemCustom.getStandardOutputItem().getOutputItemName().v(), targetValue);
			index += outputItemCustom.getCtgItemDataList().size();
		}

		result.put(RESULT_STATE, RESULT_OK);
		result.put(LINE_DATA_CSV, lineDataCSV);
		return lineDataCSV;
	}

	private void createOutputLogError(String processingId, String errorContent, String targetValue, String sid,
			String errorItem) {
		String cid = AppContexts.user().companyId();
		Optional<ExOutOpMng> exOutOpMng = exOutOpMngRepo.getExOutOpMngById(processingId);

		if (!exOutOpMng.isPresent())
			return;
		exOutOpMng.get().setOpCond(ExIoOperationState.EXPORTING);
		exOutOpMng.get().setErrCnt(exOutOpMng.get().getErrCnt() + 1);
		exOutOpMngRepo.update(exOutOpMng.get());

		ExternalOutLog externalOutLog = new ExternalOutLog();
		externalOutLog.setCompanyId(cid);
		externalOutLog.setOutputProcessId(processingId);
		externalOutLog.setErrorContent(Optional.of(errorContent));
		externalOutLog.setErrorTargetValue(Optional.of(targetValue));
		// in the case of dateType, never error so it always empty
		externalOutLog.setErrorDate(Optional.empty());
		externalOutLog.setErrorEmployee(Optional.of(sid));
		externalOutLog.setErrorItem(Optional.of(errorItem));
		externalOutLog.setLogRegisterDateTime(GeneralDateTime.now());
		externalOutLog.setLogSequenceNumber(exOutOpMng.get().getErrCnt());
		externalOutLog.setProcessCount(exOutOpMng.get().getProCnt());
		externalOutLog.setProcessContent(ProcessingClassification.ERROR);

		externalOutLogRepo.add(externalOutLog);
	}

	// サーバ外部出力ファイル項目作成
	private Map<String, String> fileItemDataCreation(List<String> lineData, OutputItemCustom outputItemCustom,
			boolean isSetNull, String nullValueReplace, int index) {
		String itemValue = "";
		String value;
		Map<String, String> result = new HashMap<String, String>();

		if (outputItemCustom.getStandardOutputItem().getCategoryItems() == null) {
			result.put(ITEM_VALUE, nullValueReplace);
			result.put(USE_NULL_VALUE, USE_NULL_VALUE_ON);
			return result;
		}

		for (int i = 0; i < outputItemCustom.getStandardOutputItem().getCategoryItems().size(); i++) {
			value = lineData.get(index);
			index++;

			if (StringUtils.isEmpty(value)) {
				if (isSetNull)
					value = nullValueReplace;
				result.put(ITEM_VALUE, value);
				result.put(USE_NULL_VALUE, USE_NULL_VALUE_ON);
				return result;
			}

			if (i == 0) {
				itemValue = value;
				continue;
			}

			if (outputItemCustom.getStandardOutputItem().getItemType() != ItemType.NUMERIC) {
				itemValue += value;
				continue;
			}

			Optional<OperationSymbol> operationSymbol = outputItemCustom.getStandardOutputItem().getCategoryItems()
					.get(i).getOperationSymbol();
			if (!operationSymbol.isPresent())
				continue;
			if (operationSymbol.get() == OperationSymbol.PLUS) {
				itemValue = String.valueOf((Double.parseDouble(itemValue)) + Double.parseDouble(value));
			} else if (operationSymbol.get() == OperationSymbol.MINUS) {
				itemValue = String.valueOf(Double.parseDouble(itemValue) - Double.parseDouble(value));
			}
		}

		result.put(ITEM_VALUE, itemValue);
		result.put(USE_NULL_VALUE, USE_NULL_VALUE_OFF);

		return result;
	}

	private void createWhereCondition(StringBuilder temp, String table, String key, String operation, String value) {
		temp.append(table);
		temp.append(".");
		temp.append(key);
		temp.append(operation);
		temp.append(value);
		temp.append(" and ");
	}

	// 外部出力取得項目一覧 only for this file
	private List<OutputItemCustom> getExOutItemList(String condSetCd, String userID, String outItemCd,
			StandardAtr standardType, boolean isAcquisitionMode) {
		String cid = AppContexts.user().companyId();
		List<StandardOutputItem> stdOutItemList = new ArrayList<StandardOutputItem>();
		List<StandardOutputItemOrder> stdOutItemOrder = new ArrayList<StandardOutputItemOrder>();

		if (standardType == StandardAtr.STANDARD) {
			if (StringUtils.isEmpty(outItemCd)) {
				stdOutItemList.addAll(stdOutItemRepo.getStdOutItemByCidAndSetCd(cid, condSetCd));
				stdOutItemOrder.addAll(stdOutItemOrderRepo.getStandardOutputItemOrderByCidAndSetCd(cid, condSetCd));
			} else {
				stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd).ifPresent(item -> stdOutItemRepo.add(item));
				stdOutItemOrderRepo.getStandardOutputItemOrderById(cid, outItemCd, condSetCd)
						.ifPresent(item -> stdOutItemOrder.add(item));
			}
		} else {
			// type user pending
		}

		// 出力項目(リスト)を出力項目並び順(リスト)に従って並び替える
		stdOutItemList.sort(new Comparator<StandardOutputItem>() {
			@Override
			public int compare(StandardOutputItem outputItem1, StandardOutputItem outputItem2) {
				List<StandardOutputItemOrder> order1 = stdOutItemOrder.stream()
						.filter(order -> order.getCid().equals(outputItem1.getCid())
								&& order.getConditionSettingCode().equals(outputItem1.getConditionSettingCode())
								&& order.getOutputItemCode().equals(outputItem1.getOutputItemCode()))
						.collect(Collectors.toList());

				List<StandardOutputItemOrder> order2 = stdOutItemOrder.stream()
						.filter(order -> order.getCid().equals(outputItem2.getCid())
								&& order.getConditionSettingCode().equals(outputItem2.getConditionSettingCode())
								&& order.getOutputItemCode().equals(outputItem2.getOutputItemCode()))
						.collect(Collectors.toList());

				if (order1.size() == 0) {
					if (order2.size() == 0)
						return 0;
					return -1;
				} else {
					if (order2.size() == 0)
						return 1;
					return order1.get(0).getDisplayOrder() > order1.get(0).getDisplayOrder() ? 1 : -1;
				}
			}
		});

		DataFormatSetting dataFormatSetting;
		OutputItemCustom outputItemCustom;
		List<OutputItemCustom> outputItemCustomList = new ArrayList<>();

		if (!isAcquisitionMode)
			return outputItemCustomList;

		NumberDataFmSet numberDataFmSetFixed = getNumberDataFmSetFixed();
		ChacDataFmSet chacDataFmSetFixed = getChacDataFmSetFixed();
		DateFormatSet dateFormatSetFixed = getDateFormatSetFixed();
		TimeDataFmSet timeDataFmSetFixed = getTimeDataFmSetFixed();
		InTimeDataFmSet inTimeDataFmSetFixed = getInTimeDataFmSetFixed();
		AwDataFormatSet awDataFormatSetFixed = getAwDataFormatSetFixed();
		for (StandardOutputItem stdOutItem : stdOutItemList) {
			switch (stdOutItem.getItemType()) {
			case NUMERIC:
				Optional<NumberDataFmSetting> numberDataFmSetting = stdOutItemRepo.getNumberDataFmSettingByID(
						stdOutItem.getCid(), stdOutItem.getConditionSettingCode().v(),
						stdOutItem.getOutputItemCode().v());
				if (numberDataFmSetting.isPresent()) {
					dataFormatSetting = numberDataFmSetting.get();
					break;
				}

				Optional<NumberDataFmSet> numberDataFmSet = dataFormatSettingRepo.getNumberDataFmSetById(cid);
				if (numberDataFmSet.isPresent()) {
					dataFormatSetting = numberDataFmSet.get();
					break;
				}
				dataFormatSetting = numberDataFmSetFixed;
				break;
			case CHARACTER:
				Optional<CharacterDataFmSetting> characterDataFmSetting = stdOutItemRepo.getCharacterDataFmSettingByID(
						stdOutItem.getCid(), stdOutItem.getConditionSettingCode().v(),
						stdOutItem.getOutputItemCode().v());
				if (characterDataFmSetting.isPresent()) {
					dataFormatSetting = characterDataFmSetting.get();
					break;
				}

				Optional<ChacDataFmSet> chacDataFmSet = dataFormatSettingRepo.getChacDataFmSetById(cid);
				if (chacDataFmSet.isPresent()) {
					dataFormatSetting = chacDataFmSet.get();
					break;
				}

				dataFormatSetting = chacDataFmSetFixed;
				break;
			case DATE:
				Optional<DateFormatSetting> dateFormatSetting = stdOutItemRepo.getDateFormatSettingByID(
						stdOutItem.getCid(), stdOutItem.getConditionSettingCode().v(),
						stdOutItem.getOutputItemCode().v());
				if (dateFormatSetting.isPresent()) {
					dataFormatSetting = dateFormatSetting.get();
					break;
				}

				Optional<DateFormatSet> dateFormatSet = dataFormatSettingRepo.getDateFormatSetById(cid);
				if (dateFormatSet.isPresent()) {
					dataFormatSetting = dateFormatSet.get();
					break;
				}

				dataFormatSetting = dateFormatSetFixed;
				break;
			case TIME:
				Optional<TimeDataFmSetting> timeDataFmSetting = stdOutItemRepo.getTimeDataFmSettingByID(
						stdOutItem.getCid(), stdOutItem.getConditionSettingCode().v(),
						stdOutItem.getOutputItemCode().v());
				if (timeDataFmSetting.isPresent()) {
					dataFormatSetting = timeDataFmSetting.get();
					break;
				}

				Optional<TimeDataFmSet> timeDataFmSet = dataFormatSettingRepo.getTimeDataFmSetByCid(cid);
				if (timeDataFmSet.isPresent()) {
					dataFormatSetting = timeDataFmSet.get();
					break;
				}

				dataFormatSetting = timeDataFmSetFixed;
				break;
			case INS_TIME:
				Optional<InstantTimeDataFmSetting> instantTimeDataFmSetting = stdOutItemRepo
						.getInstantTimeDataFmSettingByID(stdOutItem.getCid(), stdOutItem.getConditionSettingCode().v(),
								stdOutItem.getOutputItemCode().v());
				if (instantTimeDataFmSetting.isPresent()) {
					dataFormatSetting = instantTimeDataFmSetting.get();
					break;
				}

				Optional<InTimeDataFmSet> inTimeDataFmSet = dataFormatSettingRepo.getInTimeDataFmSetByCid(cid);
				if (inTimeDataFmSet.isPresent()) {
					dataFormatSetting = inTimeDataFmSet.get();
					break;
				}

				dataFormatSetting = inTimeDataFmSetFixed;
				break;
			case AT_WORK_CLS:
				Optional<AwDataFormatSetting> awDataFormatSetting = stdOutItemRepo.getAwDataFormatSettingByID(
						stdOutItem.getCid(), stdOutItem.getConditionSettingCode().v(),
						stdOutItem.getOutputItemCode().v());
				if (awDataFormatSetting.isPresent()) {
					dataFormatSetting = awDataFormatSetting.get();
					break;
				}

				Optional<AwDataFormatSet> awDataFormatSet = dataFormatSettingRepo.getAwDataFormatSetById(cid);
				if (awDataFormatSet.isPresent()) {
					dataFormatSetting = awDataFormatSet.get();
					break;
				}

				dataFormatSetting = awDataFormatSetFixed;
				break;
			default:
				dataFormatSetting = null;
				break;
			}

			List<CtgItemData> ctgItemDataList = new ArrayList<CtgItemData>();
			for (CategoryItem categoryItem : stdOutItem.getCategoryItems()) {
				ctgItemDataRepo.getCtgItemDataByIdAndDisplayClass(categoryItem.getCategoryId().v(),
						categoryItem.getItemNo().v(), 1).ifPresent(item -> ctgItemDataList.add(item));
			}

			outputItemCustom = new OutputItemCustom();
			outputItemCustom.setStandardOutputItem(stdOutItem);
			outputItemCustom.setDataFormatSetting(dataFormatSetting);
			outputItemCustom.setCtgItemDataList(ctgItemDataList);
			outputItemCustomList.add(outputItemCustom);
		}

		return outputItemCustomList;
	}

	private NumberDataFmSet getNumberDataFmSetFixed() {
		String cid = AppContexts.user().companyId();
		NotUseAtr nullValueReplace = NotUseAtr.NOT_USE;
		Optional<DataFormatNullReplacement> valueOfNullValueReplace = Optional.empty();
		NotUseAtr outputMinusAsZero = NotUseAtr.NOT_USE;
		NotUseAtr fixedValue = NotUseAtr.NOT_USE;
		Optional<DataTypeFixedValue> valueOfFixedValue = Optional.empty();
		NotUseAtr fixedValueOperation = NotUseAtr.NOT_USE;
		Optional<DataFormatFixedValueOperation> fixedCalculationValue = Optional.empty();
		FixedValueOperationSymbol fixedValueOperationSymbol = FixedValueOperationSymbol.PLUS;
		NotUseAtr fixedLengthOutput = NotUseAtr.NOT_USE;
		Optional<DataFormatIntegerDigit> fixedLengthIntegerDigit = Optional.empty();
		FixedLengthEditingMethod fixedLengthEditingMethod = FixedLengthEditingMethod.BEFORE_ZERO;
		Optional<DataFormatDecimalDigit> decimalDigit = Optional.empty();
		DecimalPointClassification decimalPointClassification = DecimalPointClassification.OUT_PUT;
		Rounding decimalFraction = Rounding.TRUNCATION;
		DecimalDivision formatSelection = DecimalDivision.DECIMAL;

		return new NumberDataFmSet(ItemType.NUMERIC, cid, nullValueReplace, valueOfNullValueReplace, outputMinusAsZero,
				fixedValue, valueOfFixedValue, fixedValueOperation, fixedCalculationValue, fixedValueOperationSymbol,
				fixedLengthOutput, fixedLengthIntegerDigit, fixedLengthEditingMethod, decimalDigit,
				decimalPointClassification, decimalFraction, formatSelection);

	}

	private ChacDataFmSet getChacDataFmSetFixed() {
		String cid = AppContexts.user().companyId();
		NotUseAtr nullValueReplace = NotUseAtr.NOT_USE;
		Optional<DataFormatNullReplacement> valueOfNullValueReplace = Optional.empty();
		NotUseAtr cdEditting = NotUseAtr.NOT_USE;
		NotUseAtr fixedValue = NotUseAtr.NOT_USE;
		FixedLengthEditingMethod cdEdittingMethod = FixedLengthEditingMethod.BEFORE_ZERO;
		Optional<DataFormatCharacterDigit> cdEditDigit = Optional.empty();
		Optional<ConvertCode> convertCode = Optional.empty();
		EditSpace spaceEditting = EditSpace.DO_NOT_DELETE;
		NotUseAtr effectDigitLength = NotUseAtr.NOT_USE;
		Optional<DataFormatCharacterDigit> startDigit = Optional.empty();
		Optional<DataFormatCharacterDigit> endDigit = Optional.empty();
		Optional<DataTypeFixedValue> valueOfFixedValue = Optional.empty();

		return new ChacDataFmSet(ItemType.CHARACTER, cid, nullValueReplace, valueOfNullValueReplace, cdEditting,
				fixedValue, cdEdittingMethod, cdEditDigit, convertCode, spaceEditting, effectDigitLength, startDigit,
				endDigit, valueOfFixedValue);
	}

	private DateFormatSet getDateFormatSetFixed() {
		String cid = AppContexts.user().companyId();
		NotUseAtr nullValueSubstitution = NotUseAtr.NOT_USE;
		NotUseAtr fixedValue = NotUseAtr.NOT_USE;
		Optional<DataTypeFixedValue> valueOfFixedValue = Optional.empty();
		Optional<DataFormatNullReplacement> valueOfNullValueSubs = Optional.empty();
		DateOutputFormat formatSelection = DateOutputFormat.YYYY_MM_DD;

		return new DateFormatSet(ItemType.DATE, cid, nullValueSubstitution, fixedValue, valueOfFixedValue,
				valueOfNullValueSubs, formatSelection);
	}

	private TimeDataFmSet getTimeDataFmSetFixed() {
		String cid = AppContexts.user().companyId();
		NotUseAtr nullValueSubs = NotUseAtr.NOT_USE;
		NotUseAtr outputMinusAsZero = NotUseAtr.NOT_USE;
		NotUseAtr fixedValue = NotUseAtr.NOT_USE;
		Optional<DataTypeFixedValue> valueOfFixedValue = Optional.empty();
		NotUseAtr fixedLengthOutput = NotUseAtr.NOT_USE;
		Optional<DataFormatIntegerDigit> fixedLongIntegerDigit = Optional.empty();
		FixedLengthEditingMethod fixedLengthEditingMothod = FixedLengthEditingMethod.BEFORE_ZERO;
		DelimiterSetting delimiterSetting = DelimiterSetting.SEPARATE_BY_DECIMAL;
		HourMinuteClassification selectHourMinute = HourMinuteClassification.HOUR_AND_MINUTE;
		Optional<DataFormatDecimalDigit> minuteFractionDigit = Optional.empty();
		DecimalSelection decimalSelection = DecimalSelection.DECIMAL;
		FixedValueOperationSymbol fixedValueOperationSymbol = FixedValueOperationSymbol.PLUS;
		NotUseAtr fixedValueOperation = NotUseAtr.NOT_USE;
		Optional<DataFormatFixedValueOperation> fixedCalculationValue = Optional.empty();
		Optional<DataFormatNullReplacement> valueOfNullValueSubs = Optional.empty();
		Rounding minuteFractionDigitProcessCls = Rounding.TRUNCATION;

		return new TimeDataFmSet(ItemType.TIME, cid, nullValueSubs, outputMinusAsZero, fixedValue, valueOfFixedValue,
				fixedLengthOutput, fixedLongIntegerDigit, fixedLengthEditingMothod, delimiterSetting, selectHourMinute,
				minuteFractionDigit, decimalSelection, fixedValueOperationSymbol, fixedValueOperation,
				fixedCalculationValue, valueOfNullValueSubs, minuteFractionDigitProcessCls);
	}

	private InTimeDataFmSet getInTimeDataFmSetFixed() {
		String cid = AppContexts.user().companyId();
		NotUseAtr nullValueSubs = NotUseAtr.NOT_USE;
		Optional<DataFormatNullReplacement> valueOfNullValueSubs = Optional.empty();
		NotUseAtr outputMinusAsZero = NotUseAtr.NOT_USE;
		NotUseAtr fixedValue = NotUseAtr.NOT_USE;
		Optional<DataTypeFixedValue> valueOfFixedValue = Optional.empty();
		HourMinuteClassification timeSeletion = HourMinuteClassification.HOUR_AND_MINUTE;
		NotUseAtr fixedLengthOutput = NotUseAtr.NOT_USE;
		Optional<DataFormatIntegerDigit> fixedLongIntegerDigit = Optional.empty();
		FixedLengthEditingMethod fixedLengthEditingMothod = FixedLengthEditingMethod.BEFORE_ZERO;
		DelimiterSetting delimiterSetting = DelimiterSetting.SEPARATE_BY_COLON;
		PreviousDayOutputMethod prevDayOutputMethod = PreviousDayOutputMethod.FORMAT24HOUR;
		NextDayOutputMethod nextDayOutputMethod = NextDayOutputMethod.OUT_PUT_24HOUR;
		Optional<DataFormatDecimalDigit> minuteFractionDigit = Optional.empty();
		DecimalSelection decimalSelection = DecimalSelection.HEXA_DECIMAL;
		Rounding minuteFractionDigitProcessCls = Rounding.TRUNCATION;

		return new InTimeDataFmSet(ItemType.INS_TIME, cid, nullValueSubs, valueOfNullValueSubs, outputMinusAsZero,
				fixedValue, valueOfFixedValue, timeSeletion, fixedLengthOutput, fixedLongIntegerDigit,
				fixedLengthEditingMothod, delimiterSetting, prevDayOutputMethod, nextDayOutputMethod,
				minuteFractionDigit, decimalSelection, minuteFractionDigitProcessCls);
	}

	private AwDataFormatSet getAwDataFormatSetFixed() {
		String cid = AppContexts.user().companyId();
		Optional<DataTypeFixedValue> closedOutput = Optional.empty();
		Optional<DataTypeFixedValue> absenceOutput = Optional.empty();
		NotUseAtr fixedValue = NotUseAtr.NOT_USE;
		Optional<DataTypeFixedValue> valueOfFixedValue = Optional.empty();
		Optional<DataTypeFixedValue> atWorkOutput = Optional.empty();
		Optional<DataTypeFixedValue> retirementOutput = Optional.empty();

		return new AwDataFormatSet(ItemType.AT_WORK_CLS, cid, closedOutput, absenceOutput, fixedValue,
				valueOfFixedValue, atWorkOutput, retirementOutput);
	}

	// サーバ外部出力ファイル型チェック
	private Map<String, String> checkOutputFileType(String itemValue, ItemType itemType,
			DataFormatSetting dataFormatSetting, String sid) {
		Map<String, String> result = new HashMap<String, String>();

		switch (itemType) {
		case NUMERIC:
			result = checkNumericType(itemValue, (NumberDataFmSet) dataFormatSetting);
			break;
		case TIME:
			result = checkTimeType(itemValue, (TimeDataFmSet) dataFormatSetting);
			break;
		case CHARACTER:
			result = checkCharType(itemValue, (ChacDataFmSet) dataFormatSetting);
			break;
		case INS_TIME:
			result = checkTimeOfDayType(itemValue, (InTimeDataFmSet) dataFormatSetting);
			break;
		case DATE:
			result = checkDateType(itemValue, (DateFormatSet) dataFormatSetting);
			break;
		case AT_WORK_CLS:
			result = checkOfficeType(itemValue, (AwDataFormatSet) dataFormatSetting, sid);
			break;
		default:
			break;
		}

		return result;
	}

	// サーバ外部出力ファイル型チェック数値型
	private Map<String, String> checkNumericType(String itemValue, NumberDataFmSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue;
		BigDecimal decimaValue = new BigDecimal(itemValue);

		if ((setting.getFixedValue() == NotUseAtr.USE) && setting.getFixedCalculationValue().isPresent()) {
			if (setting.getFixedValueOperationSymbol() == FixedValueOperationSymbol.MINUS) {
				decimaValue.subtract(setting.getFixedCalculationValue().get().v());
			} else if (setting.getFixedValueOperationSymbol() == FixedValueOperationSymbol.PLUS) {
				decimaValue.add(setting.getFixedCalculationValue().get().v());
			}
		}

		decimaValue = ((setting.getOutputMinusAsZero() == NotUseAtr.USE) && decimaValue.doubleValue() < 0)
				? BigDecimal.valueOf(0.0) : decimaValue;

		int precision = ((setting.getFormatSelection() == DecimalDivision.DECIMAL)
				&& setting.getDecimalDigit().isPresent()) ? setting.getDecimalDigit().get().v() : 0;
		roundDecimal(decimaValue, precision, setting.getDecimalFraction());

		targetValue = (setting.getDecimalPointClassification() == DecimalPointClassification.OUT_PUT)
				? decimaValue.toString() : decimaValue.toString().replace(".", "");

		if ((setting.getFixedLengthOutput() == NotUseAtr.USE) && setting.getFixedLengthIntegerDigit().isPresent()
				&& (targetValue.length() < setting.getFixedLengthIntegerDigit().get().v())) {
			targetValue = fixlengthData(targetValue, setting.getFixedLengthIntegerDigit().get().v(),
					setting.getFixedLengthEditingMethod());
		}

		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);
		return result;
	}

	// サーバ外部出力ファイル型チェック時間型
	private Map<String, String> checkTimeType(String itemValue, TimeDataFmSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue;
		BigDecimal decimaValue = new BigDecimal(itemValue);

		if ((setting.getFixedValue() == NotUseAtr.USE) && setting.getFixedCalculationValue().isPresent()) {
			if (setting.getFixedValueOperationSymbol() == FixedValueOperationSymbol.MINUS) {
				decimaValue.subtract(setting.getFixedCalculationValue().get().v());
			} else if (setting.getFixedValueOperationSymbol() == FixedValueOperationSymbol.PLUS) {
				decimaValue.add(setting.getFixedCalculationValue().get().v());
			}
		}

		if (setting.getSelectHourMinute() == HourMinuteClassification.HOUR_AND_MINUTE) {
			if (setting.getDecimalSelection() == DecimalSelection.DECIMAL) {
				decimaValue = decimaValue.divide(BigDecimal.valueOf(60.0));
			} else if (setting.getDecimalSelection() == DecimalSelection.HEXA_DECIMAL) {
				BigDecimal intValue = decimaValue.divideToIntegralValue(BigDecimal.valueOf(60.00));
				BigDecimal remainValue = decimaValue.subtract(intValue.multiply(BigDecimal.valueOf(60.00)));
				decimaValue = intValue.add(remainValue.divide(BigDecimal.valueOf(100.00)));
			}
		}

		if (setting.getDecimalSelection() == DecimalSelection.DECIMAL) {
			int precision = setting.getMinuteFractionDigit().isPresent() ? setting.getMinuteFractionDigit().get().v()
					: 0;
			roundDecimal(decimaValue, precision, setting.getMinuteFractionDigitProcessCls());
		}

		decimaValue = ((setting.getOutputMinusAsZero() == NotUseAtr.USE) && (decimaValue.doubleValue() < 0))
				? BigDecimal.valueOf(0.0) : decimaValue;

		targetValue = decimaValue.toString();
		if (setting.getDelimiterSetting() == DelimiterSetting.NO_DELIMITER) {
			targetValue = targetValue.replace(".", "");
		} else if (setting.getDelimiterSetting() == DelimiterSetting.SEPARATE_BY_COLON) {
			targetValue = targetValue.replace(".", ":");
		}

		if ((setting.getFixedLengthOutput() == NotUseAtr.USE) && setting.getFixedLongIntegerDigit().isPresent()
				&& (targetValue.length() < setting.getFixedLongIntegerDigit().get().v())) {
			targetValue = fixlengthData(targetValue, setting.getFixedLongIntegerDigit().get().v(),
					setting.getFixedLengthEditingMothod());
		}

		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);

		return result;
	}

	// サーバ外部出力ファイル型チェック文字型
	private Map<String, String> checkCharType(String itemValue, ChacDataFmSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue = itemValue;
		String cid = AppContexts.user().companyId();
		boolean inConvertCode = false;

		if (setting.getEffectDigitLength() == NotUseAtr.USE) {
			// TODO cắt chữ nhờ kiban làm
		}

		if (setting.getConvertCode().isPresent() && outputCodeConvertRepo
				.getOutputCodeConvertById(cid, setting.getConvertCode().get().v()).isPresent()) {
			Optional<OutputCodeConvert> codeConvert = outputCodeConvertRepo.getOutputCodeConvertById(cid,
					setting.getConvertCode().get().v());
			for (CdConvertDetail convertDetail : codeConvert.get().getListCdConvertDetails()) {
				if (targetValue.equals(convertDetail.getSystemCd())) {
					targetValue = convertDetail.getOutputItem().isPresent() ? convertDetail.getOutputItem().get() : "";
					inConvertCode = true;
					break;
				}
			}

			if (!inConvertCode && (codeConvert.get().getAcceptWithoutSetting() == NotUseAtr.NOT_USE)) {
				state = RESULT_NG;
				errorMess = "mes-678";

				result.put(RESULT_STATE, state);
				result.put(ERROR_MESS, errorMess);
				result.put(RESULT_VALUE, targetValue);

				return result;
			}
		}

		if (setting.getSpaceEditting() == EditSpace.DELETE_SPACE_AFTER) {
			targetValue.replaceAll("\\s+$", "");
		} else if (setting.getSpaceEditting() == EditSpace.DELETE_SPACE_BEFORE) {
			targetValue.replaceAll("^\\s+", "");
		}

		if ((setting.getCdEditting() == NotUseAtr.USE) && setting.getCdEditDigit().isPresent()
				&& (targetValue.length() < setting.getCdEditDigit().get().v())) {
			targetValue = fixlengthData(targetValue, setting.getCdEditDigit().get().v(), setting.getCdEdittingMethod());
		}

		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);

		return result;
	}

	// サーバ外部出力ファイル型チェック時刻型
	private Map<String, String> checkTimeOfDayType(String itemValue, InTimeDataFmSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue;
		BigDecimal decimaValue = new BigDecimal(itemValue);

		if (setting.getTimeSeletion() == HourMinuteClassification.HOUR_AND_MINUTE) {
			if (setting.getDecimalSelection() == DecimalSelection.DECIMAL) {
				decimaValue = decimaValue.divide(BigDecimal.valueOf(60.00));
			} else if (setting.getDecimalSelection() == DecimalSelection.HEXA_DECIMAL) {
				BigDecimal intValue = decimaValue.divideToIntegralValue(BigDecimal.valueOf(60.00));
				BigDecimal remainValue = decimaValue.subtract(intValue.multiply(BigDecimal.valueOf(60.00)));
				decimaValue = intValue.add(remainValue.divide(BigDecimal.valueOf(100.00)));
			}
		}

		if (setting.getDecimalSelection() == DecimalSelection.DECIMAL) {
			int precision = setting.getMinuteFractionDigit().isPresent() ? setting.getMinuteFractionDigit().get().v()
					: 0;
			roundDecimal(decimaValue, precision, setting.getMinuteFractionDigitProcessCls());
		}

		decimaValue = ((setting.getOutputMinusAsZero() == NotUseAtr.USE) && (decimaValue.doubleValue() < 0))
				? BigDecimal.valueOf(0.0) : decimaValue;

		if ((Double.valueOf(itemValue) > 1440)
				&& (setting.getTimeSeletion() == HourMinuteClassification.HOUR_AND_MINUTE)
				&& (setting.getNextDayOutputMethod() == NextDayOutputMethod.OUT_PUT_24HOUR)) {
			decimaValue.subtract(new BigDecimal(24.00));
		}

		if ((decimaValue.doubleValue() < 0)
				&& (setting.getTimeSeletion() == HourMinuteClassification.HOUR_AND_MINUTE)) {
			if (setting.getPrevDayOutputMethod() == PreviousDayOutputMethod.FORMAT0H00) {
				decimaValue = new BigDecimal(0.00);
			} else if (setting.getPrevDayOutputMethod() == PreviousDayOutputMethod.FORMAT24HOUR) {
				decimaValue.add(new BigDecimal(24.00));
			}
		}

		targetValue = decimaValue.toString();
		if (setting.getDelimiterSetting() == DelimiterSetting.NO_DELIMITER) {
			targetValue = targetValue.replace(".", "");
		} else if (setting.getDelimiterSetting() == DelimiterSetting.SEPARATE_BY_COLON) {
			targetValue = targetValue.replace(".", ":");
		}

		if ((setting.getFixedLengthOutput() == NotUseAtr.USE) && setting.getFixedLongIntegerDigit().isPresent()
				&& (targetValue.length() < setting.getFixedLongIntegerDigit().get().v())) {
			targetValue = fixlengthData(targetValue, setting.getFixedLongIntegerDigit().get().v(),
					setting.getFixedLengthEditingMothod());
		}

		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);

		return result;
	}

	// サーバ外部出力ファイル型チェック日付型
	private Map<String, String> checkDateType(String itemValue, DateFormatSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue = "";
		// TODO
		GeneralDate date = GeneralDate.fromString(itemValue, "w");
		DateOutputFormat formatDate = setting.getFormatSelection();

		if (formatDate == DateOutputFormat.DAY_OF_WEEK) {
			targetValue = date.toString("w");
		} else if (formatDate == DateOutputFormat.YY_MM_DD || formatDate == DateOutputFormat.YYMMDD
				|| formatDate == DateOutputFormat.YYYY_MM_DD || formatDate == DateOutputFormat.YYYYMMDD) {
			targetValue = date.toString(formatDate.name());
		} else if (formatDate == DateOutputFormat.JJYY_MM_DD || formatDate == DateOutputFormat.JJYYMMDD) {
			// TODO
		}

		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);

		return result;
	}

	// サーバ外部出力ファイル型チェック在職区分型
	private Map<String, String> checkOfficeType(String itemValue, AwDataFormatSet setting, String sid) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue = "";
		StatusOfEmployment status;
		// TODO
		GeneralDate date = GeneralDate.fromString(itemValue, "w");

		Optional<StatusOfEmploymentResult> statusOfEmployment = statusOfEmploymentAdapter.getStatusOfEmployment(sid,
				date);
		if (statusOfEmployment.isPresent()) {
			status = EnumAdaptor.valueOf(statusOfEmployment.get().getStatusOfEmployment(), StatusOfEmployment.class);
			switch (status) {
			case INCUMBENT:
				targetValue = setting.getClosedOutput().isPresent() ? setting.getAtWorkOutput().get().v() : "";
				break;
			case LEAVE_OF_ABSENCE:
				targetValue = setting.getClosedOutput().isPresent() ? setting.getAbsenceOutput().get().v() : "";
				break;
			case RETIREMENT:
				targetValue = setting.getClosedOutput().isPresent() ? setting.getRetirementOutput().get().v() : "";
				break;
			case HOLIDAY:
				targetValue = setting.getClosedOutput().isPresent() ? setting.getClosedOutput().get().v() : "";
				break;

			default:
				break;
			}
		} else {
			state = RESULT_NG;
			errorMess = "";
		}

		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);

		return result;
	}

	private void roundDecimal(BigDecimal decimaValue, int precision, Rounding rounding) {
		RoundingMode roundingMode;

		switch (rounding) {
		case DOWN_4_UP_5:
			roundingMode = RoundingMode.HALF_UP;
			break;
		case TRUNCATION:
			roundingMode = RoundingMode.DOWN;
			break;
		case ROUND_UP:
			roundingMode = RoundingMode.UP;
			break;

		default:
			roundingMode = RoundingMode.HALF_UP;
			break;
		}

		decimaValue.round(new MathContext(precision, roundingMode));
	}

	private String fixlengthData(String data, int fixLength, FixedLengthEditingMethod method) {
		StringBuilder result = new StringBuilder(data);
		StringBuilder addString = new StringBuilder("");

		switch (method) {
		case AFTER_ZERO:
			for (int i = 0; i < (fixLength - data.length()); i++) {
				addString.append("0");
			}
			result.append(addString);
			break;
		case AFTER_SPACE:
			for (int i = 0; i < (fixLength - data.length()); i++) {
				addString.append(" ");
			}
			result.append(addString);
			break;
		case BEFORE_ZERO:
			for (int i = 0; i < (fixLength - data.length()); i++) {
				addString.append("0");
			}
			result = addString.append(result);
			break;
		case BEFORE_SPACE:
			for (int i = 0; i < (fixLength - data.length()); i++) {
				addString.append(" ");
			}
			result = addString.append(result);
			break;

		default:
			break;
		}

		return result.toString();
	}
}
