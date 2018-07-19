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

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
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
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvert;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSetting;
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
import nts.uk.ctx.exio.dom.exo.execlog.StandardClassification;
import nts.uk.ctx.exio.dom.exo.executionlog.ExIoOperationState;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.ConditionSymbol;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.ItemType;
import nts.uk.ctx.exio.dom.exo.outputitem.OperationSymbol;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class CreateExOutTextService extends ExportService<Object> {
	
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
	
	private final static String GET_ASSOCIATION = "getOutCondAssociation";
	private final static String GET_ITEM_NAME = "getOutCondItemName";
	private final static String USE_NULL_VALUE_ON = "on";
	private final static String USE_NULL_VALUE_OFF = "off";
	private final static String RESULT_OK = "ok";
	private final static String RESULT_NG = "ng";
	private final static String RESULT_STATE = "state";
	private final static String RESULT_VALUE = "value";
	private final static String ERROR_MESS = "ErrorMessage";

	@Override
	protected void handle(ExportServiceContext<Object> context) {
		ExOutSetting domain = (ExOutSetting) context.getQuery();
		executeServerExOutManual(domain, context.getGeneratorContext());
	}

	public void executeServerExOutManual(ExOutSetting exOutSetting, FileGeneratorContext generatorContext) {
		ExOutSettingResult settingResult = getServerExOutSetting(exOutSetting);
		initExOutLogInformation(exOutSetting);
		serverExOutExecution(exOutSetting, settingResult);
	}
	
	
	//サーバ外部出力設定取得
	private ExOutSettingResult getServerExOutSetting(ExOutSetting exOutSetting) {
		List<StdOutputCondSet> stdOutputCondSetList = acquisitionExOutSetting.getExOutSetting(null, true, exOutSetting.getConditionSetCd());
		StdOutputCondSet stdOutputCondSet = (stdOutputCondSetList.size() > 0) ? stdOutputCondSetList.get(0) : null;
		List<OutCndDetailItem> outCndDetailItemList = acquisitionExOutSetting.getExOutCond(exOutSetting.getConditionSetCd(), true);
		List<OutputItemCustom> outputItemCustomList = getExOutItemList(exOutSetting.getConditionSetCd(), null, "", true, true);
		List<CtgItemData> ctgItemDataList = new ArrayList<CtgItemData>();
		
		for (OutputItemCustom outputItemCustom : outputItemCustomList) {
			ctgItemDataList.addAll(outputItemCustom.getCtgItemDataList());
		}
		
		Optional<ExOutCtg> exOutCtg = exOutCtgRepo.getExOutCtgByIdAndCtgSetting(stdOutputCondSet.getCategoryId().v());
		Optional<ExCndOutput> exCndOutput = exCndOutputRepo.getExCndOutputById(stdOutputCondSet.getCategoryId().v());
		
		return new ExOutSettingResult(stdOutputCondSet, outCndDetailItemList, exOutCtg, exCndOutput, outputItemCustomList, ctgItemDataList);
	}
	
	//サーバ外部出力ログ情報初期値
	private void initExOutLogInformation(ExOutSetting exOutSetting) {
		String cid = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();
		
		ExOutOpMng exOutOpMng = new ExOutOpMng(exOutSetting.getProcessingId(), 0, 0, 0, NotUseAtr.NOT_USE.value, null, 
				ExIoOperationState.PERPAKING.value);
		
		//TODO roleType?
		ExterOutExecLog exterOutExecLog = new ExterOutExecLog(cid, exOutSetting.getProcessingId(), null, 0, 0, null, null, 
				NotUseAtr.USE.value, null, null, null, null, GeneralDateTime.now(), StandardClassification.STANDARD.value, 
				ExecutionForm.MANUAL_EXECUTION.value, sid, exOutSetting.getReferenceDate(), exOutSetting.getEndDate(), 
				exOutSetting.getStartDate(), exOutSetting.getConditionSetCd(), null, null);
				
		ExternalOutLog externalOutLog = new ExternalOutLog(cid, exOutSetting.getProcessingId(), null, null, null, null, null, 
				GeneralDateTime.now(), 0, 0, ProcessingClassification.START_PROCESSING.value);
		
		exOutOpMngRepo.add(exOutOpMng);
		exterOutExecLogRepo.add(exterOutExecLog);
		externalOutLogRepo.add(externalOutLog);
	}
	
	//サーバ外部出力実行
	private void serverExOutExecution(ExOutSetting exOutSetting, ExOutSettingResult settingResult) {
		
		String processingId = exOutSetting.getProcessingId();
		ExIoOperationState state;
		Optional<ExOutCtg> exOutCtg = settingResult.getExOutCtg();
		StdOutputCondSet stdOutputCondSet = settingResult.getStdOutputCondSet();
		String settingName = "";
		if (stdOutputCondSet != null) settingName = stdOutputCondSet.getConditionSetName().v();
		String fileName = exOutSetting.getConditionSetCd() + settingName + processingId;
		
		exOutOpMngRepo.getExOutOpMngById(processingId).ifPresent(exOutOpMng -> {
			exOutOpMng.setOpCond(ExIoOperationState.EXPORTING);
			
			if((stdOutputCondSet == null) || !exOutCtg.isPresent()) {
				//TODO break
			}
			
			if(exOutCtg.get().getCategorySet() == CategorySetting.DATA_TYPE) {
				exOutOpMng.setProUnit(I18NText.getText("#CMF002_527"));
				exOutOpMng.setProCnt(0);
				exOutOpMng.setTotalProCnt(exOutSetting.getSidList().size());
			} else {
				exOutOpMng.setProUnit(I18NText.getText("#CMF002_528"));
			}
			
			exOutOpMngRepo.update(exOutOpMng);
		});
		
		if(exOutCtg.get().getCategorySet() == CategorySetting.DATA_TYPE) {
			state = serverExOutTypeData(exOutSetting, settingResult, fileName);
		} else {
			//TODO type master
		}
		
		//TODO set end log
	}
	
	//サーバ外部出力タイプデータ系
	private ExIoOperationState serverExOutTypeData(ExOutSetting exOutSetting, ExOutSettingResult settingResult, String fileName) {
		List<String> header = new ArrayList<>();
		StdOutputCondSet stdOutputCondSet = (StdOutputCondSet) settingResult.getStdOutputCondSet();
		List<OutputItemCustom> outputItemCustomList =settingResult.getOutputItemCustomList();
		
		//サーバ外部出力ファイル項目ヘッダ
		if(stdOutputCondSet != null && (stdOutputCondSet.getConditionOutputName() == NotUseAtr.USE)) {
			header.add(stdOutputCondSet.getConditionSetName().v());
		}
		if(stdOutputCondSet != null && (stdOutputCondSet.getItemOutputName() == NotUseAtr.USE)) {
			for(OutputItemCustom outputItemCustom : outputItemCustomList) {
				header.add(outputItemCustom.getStandardOutputItem().getOutputItemName().v());
			}
		}
		
		for (String sid : exOutSetting.getSidList()) {
			Optional<ExOutOpMng> exOutOpMng = exOutOpMngRepo.getExOutOpMngById(exOutSetting.getProcessingId());
		
			if(!exOutOpMng.isPresent()) {
				return ExIoOperationState.FAULT_FINISH;
			}
			
			if(exOutOpMng.get().getDoNotInterrupt() == NotUseAtr.USE) {
				return ExIoOperationState.INTER_FINISH;
			}
			
			exOutOpMng.get().setProCnt(exOutOpMng.get().getProCnt() + 1);
			exOutOpMngRepo.update(exOutOpMng.get());
			
			String sql = getExOutDataSQL(sid, true, exOutSetting, settingResult);
			List<List<String>> data = exOutCtgRepo.getData(sql);
			
			for (List<String> lineData : data) {
				fileLineDataCreation(lineData, outputItemCustomList);
			}
			
			
		}
		
		return ExIoOperationState.EXPORT_FINISH;
	}
	
	//サーバ外部出力データ取得SQL
	@SuppressWarnings("unchecked")
	private String getExOutDataSQL(String sid, boolean isdataType, ExOutSetting exOutSetting, ExOutSettingResult settingResult) {
		String cid = AppContexts.user().companyId();
		StringBuilder sql = new StringBuilder();
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
		exCndOutput.ifPresent(item -> {
			sql.append(item.getForm1().v());
			sql.append(" ");
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
				for(int i = 1; i <= 10; i++) {
					getAssociation = ExCndOutput.class.getMethod(GET_ASSOCIATION + i);
					getItemName = ExCndOutput.class.getMethod(GET_ITEM_NAME + i);
					
					asssociation = (Optional<Association>) getAssociation.invoke(null);
					itemName = (Optional<PhysicalProjectName>) getItemName.invoke(null);
					
					if(!asssociation.isPresent() || !itemName.isPresent()) {
						continue;
					}
					
					if(asssociation.get() == Association.CCD) {
						createWhereCondition(sql, exCndOutput.get().getMainTable().v(), itemName.get().v(), "=", cid);
					} else if(asssociation.get() == Association.ECD) {
						createWhereCondition(sql, exCndOutput.get().getMainTable().v(), itemName.get().v(), "=", sid);
					} else if(asssociation.get() == Association.DATE) {
						if(!isDate) {
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
			
			if(isOutDate) {
				createWhereCondition(sql, exCndOutput.get().getMainTable().v(), startDateItemName, " <= ", "'" + exOutSetting.getEndDate().toString() + "'");
				createWhereCondition(sql, exCndOutput.get().getMainTable().v(), endDateItemName, " >= ", "'" + exOutSetting.getStartDate().toString() + "'");
			} else if(isDate) {
				createWhereCondition(sql, exCndOutput.get().getMainTable().v(), startDateItemName, " >= ", "'" + exOutSetting.getStartDate().toString() + "'");
				createWhereCondition(sql, exCndOutput.get().getMainTable().v(), startDateItemName, " <= ", "'" + exOutSetting.getEndDate().toString() + "'");
			}
			
			if(exCndOutput.get().getConditions().v().length() > 0) {
				sql.append(exCndOutput.get().getConditions().v());
				sql.append(" and ");
			}
			
			String value = "";
			String value1 = "";
			String value2 = "";
			String operator = "";
			String searchCodeListCond;
			
			List<OutCndDetailItem> outCndDetailItemList = settingResult.getOutCndDetailItem();
			//TODO làm đẹp chỗ này
			for(OutCndDetailItem outCndDetailItem : outCndDetailItemList) {
				searchCodeListCond = outCndDetailItem.getJoinedSearchCodeList();
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
						value = outCndDetailItem.getSearchNum().get().v().toString();
						value1 = outCndDetailItem.getSearchNumStartVal().get().v().toString();
						value2 = outCndDetailItem.getSearchNumEndVal().get().v().toString();
						break;
					case CHARACTER:
						if(outCndDetailItem.getConditionSymbol() == ConditionSymbol.CONTAIN) {
							value = "'" + outCndDetailItem.getSearchChar().get().v() + "'";
							value1 = "'" + outCndDetailItem.getSearchCharStartVal().get().v() + "'";
							value2 = "'" + outCndDetailItem.getSearchCharEndVal().get().v() + "'";
						} else {
							value = "'%" + outCndDetailItem.getSearchChar().get().v() + "%'";
							value1 = "'%" + outCndDetailItem.getSearchCharStartVal().get().v() + "%'";
							value2 = "'%" + outCndDetailItem.getSearchCharEndVal().get().v() + "%'";
						}
						break;
					case DATE:
						value = "'" + outCndDetailItem.getSearchDate().get().toString("yyyyMMdd") + "'";
						value1 = "'" + outCndDetailItem.getSearchDateStart().get().toString("yyyyMMdd") + "'";
						value2 = "'"  + outCndDetailItem.getSearchDateEnd().get().toString("yyyyMMdd") + "'";
						break;
					case TIME:
						value = outCndDetailItem.getSearchClock().get().v().toString();
						value1 = outCndDetailItem.getSearchClockStartVal().get().v().toString();
						value2 = outCndDetailItem.getSearchClockEndVal().get().v().toString();
						break;
					case INS_TIME:
						value = outCndDetailItem.getSearchTime().get().v().toString();
						value1 = outCndDetailItem.getSearchTimeStartVal().get().v().toString();
						value2 = outCndDetailItem.getSearchTimeEndVal().get().v().toString();
						break;
						
					default:
						break;
					}
					
					if((outCndDetailItem.getConditionSymbol() == ConditionSymbol.IN) || (outCndDetailItem.getConditionSymbol() == ConditionSymbol.NOT_IN))
							value = "(" + searchCodeListCond + ")";
					
					if(outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
						createWhereCondition(sql, ctgItemData.getTblAlias(), ctgItemData.getFieldName(), " >= ", value1);
						createWhereCondition(sql, ctgItemData.getTblAlias(), ctgItemData.getFieldName(), " <= ", value2);
					} else {
						createWhereCondition(sql, ctgItemData.getTblAlias(), ctgItemData.getFieldName(), operator, value);
					}
					
					ctgItemData.getPrimarykeyClassfication().ifPresent(primaryKey -> {
						if(primaryKey == NotUseAtr.USE) {
							keyOrderList.add(ctgItemData.getTblAlias() + "." + ctgItemData.getFieldName());
						}
					});
				}
			}
		});
		
		sql.setLength(sql.length() - 4);
		sql.append(" order by");
		
		if(isdataType) {
			sql.append(" sid ");
		} else {
			for(String keyOrder : keyOrderList) {
				sql.append(keyOrder);
				sql.append(", ");
			}
			
			sql.setLength(sql.length() - 2);
		}
		
		sql.append(" asc;");
		
		return sql.toString();
	}
	
	//サーバ外部出力ファイル行データ作成
	private void fileLineDataCreation(List<String> lineData, List<OutputItemCustom> outputItemCustomList) {
		
		String targetValue = "";
		boolean isfixedValue = false;
		String fixedValue = "";
		boolean isSetNull = false;
		String nullValueReplace = "";
		Map<String, String> fileDataCreationResult;
		String itemValue;
		String useNullValue;
		
		for(OutputItemCustom outputItemCustom : outputItemCustomList) {
			int index = 0;
			
			//TODO isfixedValue, fixedValue, isSetNull, nullValueReplace switch case?
			if(!isfixedValue) {
				fileDataCreationResult = fileDataCreation(lineData, outputItemCustom, isSetNull, nullValueReplace, index);
				itemValue = fileDataCreationResult.get("itemValue");
				useNullValue = fileDataCreationResult.get("useNullValue");
				
				if(useNullValue == USE_NULL_VALUE_OFF) {
					//TODO
				}
			} else {
				targetValue = fixedValue;
			}
			
			index += outputItemCustom.getCtgItemDataList().size();
		}
	}
	
	//サーバ外部出力ファイル項目作成
	private Map<String, String> fileDataCreation(List<String> lineData, OutputItemCustom outputItemCustom, boolean isSetNull, String nullValueReplace,int index) {
		String itemValue = "";
		String value;
		Map<String, String> result = new HashMap<String, String>();
		
		if(outputItemCustom.getStandardOutputItem().getCategoryItems() == null) {
			result.put("itemValue", nullValueReplace);
			result.put("useNullValue", USE_NULL_VALUE_ON);
			return result;
		}
		
		for(int i = 0; i < outputItemCustom.getStandardOutputItem().getCategoryItems().size(); i++) {
			value = lineData.get(index);
			index++;
			
			if((value == null) || (value == "")) {
				if(isSetNull) value = nullValueReplace;
				result.put("itemValue", value);
				result.put("useNullValue", USE_NULL_VALUE_ON);
				return result;
			}
			
			if(i == 0) {
				itemValue = value;
			} else {
				if(outputItemCustom.getStandardOutputItem().getItemType() == ItemType.NUMERIC) {
					if(outputItemCustom.getStandardOutputItem().getCategoryItems().get(i).getOperationSymbol() == OperationSymbol.PLUS ) {
						itemValue = String.valueOf((Double.parseDouble(itemValue)) + Double.parseDouble(value));
					} else if(outputItemCustom.getStandardOutputItem().getCategoryItems().get(i).getOperationSymbol() == OperationSymbol.MINUS) {
						itemValue = String.valueOf(Double.parseDouble(itemValue) - Double.parseDouble(value));
					}
				}
				else {
					itemValue += value;
				}
			}
		}
		
		result.put("itemValue", itemValue);
		result.put("useNullValue", USE_NULL_VALUE_OFF);
		
		return result;
	}
	
	private void createWhereCondition(StringBuilder temp, String table, String key, String operation, String value ) {
		temp.append(table);
		temp.append(".");
		temp.append(key);
		temp.append(operation);
		temp.append(value);
		temp.append(" and ");
	}
	
	// アルゴリズム「外部出力取得項目一覧」を実行する only for this file
	private List<OutputItemCustom> getExOutItemList(String condSetCd, String userID, String outItemCd, boolean isStandardType, boolean  isAcquisitionMode) {
		String cid = AppContexts.user().companyId();
		List<StandardOutputItem> stdOutItemList = new ArrayList<StandardOutputItem>();
		List<StandardOutputItemOrder> stdOutItemOrder = new ArrayList<StandardOutputItemOrder>();

		if(isStandardType) {
			if (outItemCd == null || outItemCd.equals("")) {
				stdOutItemList.addAll(stdOutItemRepo.getStdOutItemByCidAndSetCd(cid, condSetCd));
				stdOutItemOrder.addAll(stdOutItemOrderRepo.getStandardOutputItemOrderByCidAndSetCd(cid, condSetCd));
			} else {
				stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd).ifPresent(item -> stdOutItemRepo.add(item));
				stdOutItemOrderRepo.getStandardOutputItemOrderById(cid, outItemCd, condSetCd).ifPresent(item -> stdOutItemOrder.add(item));
			}
		} else {
			// type user pending
		}

		// 出力項目(リスト)を出力項目並び順(リスト)に従って並び替える
		stdOutItemList.sort(new Comparator<StandardOutputItem>() {
			@Override
			public int compare(StandardOutputItem outputItem1, StandardOutputItem outputItem2) {
				List<StandardOutputItemOrder> order1 = stdOutItemOrder.stream().filter(order -> 
					order.getCid().equals(outputItem1.getCid()) &&  order.getConditionSettingCode().equals(outputItem1.getConditionSettingCode()) 
							&& order.getOutputItemCode().equals(outputItem1.getOutputItemCode())
				).collect(Collectors.toList());
				
				List<StandardOutputItemOrder> order2 = stdOutItemOrder.stream().filter(order -> 
					order.getCid().equals(outputItem2.getCid()) &&  order.getConditionSettingCode().equals(outputItem2.getConditionSettingCode()) 
							&& order.getOutputItemCode().equals(outputItem2.getOutputItemCode())
				).collect(Collectors.toList());
				
				if(order1.size() == 0) {
					if(order2.size() == 0) return 0;
					return -1;
				} else {
					if(order2.size() == 0) return 1;
					return order1.get(0).getDisplayOrder() > order1.get(0).getDisplayOrder() ? 1 : -1;
				}
			}
		});
		
		DataFormatSetting dataFormatSetting;
		OutputItemCustom outputItemCustom;
		List<OutputItemCustom> outputItemCustomList = new ArrayList<>();
		if(isAcquisitionMode) {
			for (StandardOutputItem stdOutItem : stdOutItemList) {
				dataFormatSetting = new DataFormatSetting(0);
				switch (stdOutItem.getItemType()) {
				case NUMERIC:
					//TODO
					break;
					
				case CHARACTER:
					
					break;
					
				case DATE:
	
					break;
	
				case TIME:
	
					break;
	
				case INS_TIME:
	
					break;
					
				case AT_WORK_CLS:
					
					break;

				default:
					break;
				}
				
				List<CtgItemData> ctgItemDataList = new ArrayList<CtgItemData>();
				for(CategoryItem categoryItem : stdOutItem.getCategoryItems()) {
					ctgItemDataRepo.getCtgItemDataByIdAndDisplayClass(categoryItem.getCategoryId().v(), 
							categoryItem.getCategoryItemNo().v(), 1).ifPresent(item -> ctgItemDataList.add(item));
				}
				
				outputItemCustom = new OutputItemCustom();
				outputItemCustom.setStandardOutputItem(stdOutItem);
				outputItemCustom.setDataFormatSetting(dataFormatSetting);
				outputItemCustom.setCtgItemDataList(ctgItemDataList);
				outputItemCustomList.add(outputItemCustom);
			}
		}

		return outputItemCustomList;
	}
	
	//サーバ外部出力ファイル型チェック
	private Map<String, String> checkOutputFileType(String itemValue, ItemType itemType, String conditionSettingType) {
		Map<String, String> result = new HashMap<String, String>();
		
		switch (itemType) {
		case NUMERIC:
			//TODO
			break;
		case TIME:
			
			break;
			
		case CHARACTER:
	
			break;
	
		case INS_TIME:
	
			break;
	
		case DATE:
	
			break;
			
		case AT_WORK_CLS:
			
			break;

		default:
			break;
		}
		
		return result;
	}

	//サーバ外部出力ファイル型チェック数値型
	private Map<String, String> checkNumericType(String itemValue, NumberDataFmSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue;
		BigDecimal decimaValue = new BigDecimal(itemValue);
		
		if((setting.getFixedValue() == NotUseAtr.USE) && setting.getFixedCalculationValue().isPresent()) {
			if(setting.getFixedValueOperationSymbol() == FixedValueOperationSymbol.MINUS) {
				decimaValue.subtract(setting.getFixedCalculationValue().get().v());
			} else if(setting.getFixedValueOperationSymbol() == FixedValueOperationSymbol.PLUS) {
				decimaValue.add(setting.getFixedCalculationValue().get().v());
			}
		}
		
		decimaValue = ((setting.getOutputMinusAsZero() == NotUseAtr.USE) && decimaValue.doubleValue() < 0) ? 
				BigDecimal.valueOf(0.0) : decimaValue;
		
		int precision = ((setting.getFormatSelection() == DecimalDivision.DECIMAL) && setting.getDecimalDigit().isPresent()) ? 
				setting.getDecimalDigit().get().v() : 0;
		roundDecimal(decimaValue, precision, setting.getDecimalFraction());
		
		targetValue = (setting.getDecimalPointClassification() == DecimalPointClassification.OUT_PUT) ? 
				decimaValue.toString() : decimaValue.toString().replace(".", "");
				
		if((setting.getFixedLengthOutput() == NotUseAtr.USE) && setting.getFixedLengthIntegerDigit().isPresent()
				&& (targetValue.length() < setting.getFixedLengthIntegerDigit().get().v())) {
			targetValue = fixlengthData(targetValue, setting.getFixedLengthIntegerDigit().get().v(), setting.getFixedLengthEditingMethod());
		}
			
		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);
		return result;
	}
	
	//サーバ外部出力ファイル型チェック時間型
	private Map<String, String> checkTimeType(String itemValue, TimeDataFmSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue;
		BigDecimal decimaValue = new BigDecimal(itemValue);
		
		if((setting.getFixedValue() == NotUseAtr.USE) && setting.getFixedCalculationValue().isPresent()) {
			if(setting.getFixedValueOperationSymbol() == FixedValueOperationSymbol.MINUS) {
				decimaValue.subtract(setting.getFixedCalculationValue().get().v());
			} else if(setting.getFixedValueOperationSymbol() == FixedValueOperationSymbol.PLUS) {
				decimaValue.add(setting.getFixedCalculationValue().get().v());
			}
		}
		
		if(setting.getSelectHourMinute() == HourMinuteClassification.HOUR_AND_MINUTE) {
			if(setting.getDecimalSelection() == DecimalSelection.DECIMAL) {
				decimaValue = decimaValue.divide(BigDecimal.valueOf(60.0));
			} else if(setting.getDecimalSelection() == DecimalSelection.HEXA_DECIMAL) {
				BigDecimal intValue = decimaValue.divideToIntegralValue(BigDecimal.valueOf(60.00));
				BigDecimal remainValue = decimaValue.subtract(intValue.multiply(BigDecimal.valueOf(60.00)));
				decimaValue = intValue.add(remainValue.divide(BigDecimal.valueOf(100.00)));
			}
		}
		
		if(setting.getDecimalSelection() == DecimalSelection.DECIMAL) {
			int precision = setting.getMinuteFractionDigit().isPresent() ? 
					setting.getMinuteFractionDigit().get().v() : 0;
			roundDecimal(decimaValue, precision, setting.getMinuteFractionDigitProcessCls());
		}
		
		decimaValue = ((setting.getOutputMinusAsZero() == NotUseAtr.USE) && (decimaValue.doubleValue() < 0)) ? 
				BigDecimal.valueOf(0.0) : decimaValue;
				
		targetValue = decimaValue.toString();
		if(setting.getDelimiterSetting() == DelimiterSetting.NO_DELIMITER) {
			targetValue = targetValue.replace(".", "");
		} else if(setting.getDelimiterSetting() == DelimiterSetting.SEPARATE_BY_COLON) {
			targetValue = targetValue.replace(".", ":");
		}
		
		if((setting.getFixedLengthOutput() == NotUseAtr.USE) && setting.getFixedLongIntegerDigit().isPresent()
				&& (targetValue.length() < setting.getFixedLongIntegerDigit().get().v())) {
			targetValue = fixlengthData(targetValue, setting.getFixedLongIntegerDigit().get().v(), setting.getFixedLengthEditingMothod());
		}
		
		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);
		
		return result;
	}
	
	//サーバ外部出力ファイル型チェック文字型
	private Map<String, String> checkCharType(String itemValue, ChacDataFmSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue = itemValue;
		String cid = AppContexts.user().companyId();
		boolean inConvertCode = false;
		
		if(setting.getEffectDigitLength() == NotUseAtr.USE) {
			//TODO cắt chữ nhờ kiban làm
		}
		
		if(setting.getConvertCode().isPresent()) {
			Optional<OutputCodeConvert> codeConvert = outputCodeConvertRepo.getOutputCodeConvertById(cid, setting.getConvertCode().get().v());
			if(codeConvert.isPresent()) {
				for(CdConvertDetail convertDetail : codeConvert.get().getListCdConvertDetails()) {
					if(targetValue.equals(convertDetail.getSystemCd())) {
						targetValue = convertDetail.getOutputItem().isPresent() ? convertDetail.getOutputItem().get() : "";
						inConvertCode = true;
						break;
					}
				}
				
				if(!inConvertCode && (codeConvert.get().getAcceptWithoutSetting() == NotUseAtr.NOT_USE)) {
					state = RESULT_NG;
					errorMess = "mes-678";
					
					result.put(RESULT_STATE, state);
					result.put(ERROR_MESS, errorMess);
					result.put(RESULT_VALUE, targetValue);
					
					return result;
				}
			}
		};
		
		if(setting.getSpaceEditting() == EditSpace.DELETE_SPACE_AFTER) {
			targetValue.replaceAll("\\s+$", "");
		} else if(setting.getSpaceEditting() == EditSpace.DELETE_SPACE_BEFORE) {
			targetValue.replaceAll("^\\s+", "");
		}
		
		if((setting.getCdEditting() == NotUseAtr.USE) && setting.getCdEditDigit().isPresent()
				&& (targetValue.length() < setting.getCdEditDigit().get().v())) {
			targetValue = fixlengthData(targetValue, setting.getCdEditDigit().get().v(), setting.getCdEdittingMethod());
		}
		
		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);
		
		return result;
	}
	
	//サーバ外部出力ファイル型チェック時刻型
	private Map<String, String> checkTimeOfDayType(String itemValue, InTimeDataFmSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue;
		BigDecimal decimaValue = new BigDecimal(itemValue);
		
		if(setting.getTimeSeletion() == HourMinuteClassification.HOUR_AND_MINUTE) {
			if(setting.getDecimalSelection() == DecimalSelection.DECIMAL) {
				decimaValue = decimaValue.divide(BigDecimal.valueOf(60.00));
			} else if(setting.getDecimalSelection() == DecimalSelection.HEXA_DECIMAL) {
				BigDecimal intValue = decimaValue.divideToIntegralValue(BigDecimal.valueOf(60.00));
				BigDecimal remainValue = decimaValue.subtract(intValue.multiply(BigDecimal.valueOf(60.00)));
				decimaValue = intValue.add(remainValue.divide(BigDecimal.valueOf(100.00)));
			}
		}
		
		if(setting.getDecimalSelection() == DecimalSelection.DECIMAL) {
			int precision = setting.getMinuteFractionDigit().isPresent() ? 
					setting.getMinuteFractionDigit().get().v() : 0;
			roundDecimal(decimaValue, precision, setting.getMinuteFractionDigitProcessCls());
		}
		
		decimaValue = ((setting.getOutputMinusAsZero() == NotUseAtr.USE) && (decimaValue.doubleValue() < 0)) ? 
				BigDecimal.valueOf(0.0) : decimaValue;
				
		if((Double.valueOf(itemValue) > 1440) && (setting.getTimeSeletion() == HourMinuteClassification.HOUR_AND_MINUTE)
				&& (setting.getNextDayOutputMethod() == NextDayOutputMethod.OUT_PUT_24HOUR)) {
			decimaValue.subtract(new BigDecimal(24.00));
		}
		
		if((decimaValue.doubleValue() < 0) && (setting.getTimeSeletion() == HourMinuteClassification.HOUR_AND_MINUTE)) {
			if(setting.getPrevDayOutputMethod() == PreviousDayOutputMethod.FORMAT0H00) {
				decimaValue = new BigDecimal(0.00);
			} else if(setting.getPrevDayOutputMethod() == PreviousDayOutputMethod.FORMAT24HOUR) {
				decimaValue.add(new BigDecimal(24.00));
			}
		}
		
		targetValue = decimaValue.toString();
		if(setting.getDelimiterSetting() == DelimiterSetting.NO_DELIMITER) {
			targetValue = targetValue.replace(".", "");
		} else if(setting.getDelimiterSetting() == DelimiterSetting.SEPARATE_BY_COLON) {
			targetValue = targetValue.replace(".", ":");
		}
		
		if((setting.getFixedLengthOutput() == NotUseAtr.USE) && setting.getFixedLongIntegerDigit().isPresent()
				&& (targetValue.length() < setting.getFixedLongIntegerDigit().get().v())) {
			targetValue = fixlengthData(targetValue, setting.getFixedLongIntegerDigit().get().v(), setting.getFixedLengthEditingMothod());
		}
		
		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);
		
		return result;
	}
	
	//サーバ外部出力ファイル型チェック日付型
	private Map<String, String> checkDateType(String itemValue, DateFormatSet setting) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue = "";
		//TODO
		GeneralDate date = GeneralDate.fromString(itemValue, "w");
		DateOutputFormat formatDate = setting.getFormatSelection();
		
		if(formatDate == DateOutputFormat.DAY_OF_WEEK) {
			targetValue = date.toString("w");
		} else if(formatDate == DateOutputFormat.DAY_OF_WEEK || formatDate == DateOutputFormat.DAY_OF_WEEK || 
				formatDate == DateOutputFormat.DAY_OF_WEEK || formatDate == DateOutputFormat.DAY_OF_WEEK ) {
			targetValue = date.toString( formatDate.name());
		} else if(formatDate == DateOutputFormat.JJYY_MM_DD || formatDate == DateOutputFormat.JJYYMMDD) {
			List<String> eraId = setting.getJapCalendarSymbol().stream().map(x ->{
				return x.getEraId();
			}).collect(Collectors.toList());
			
			
		}
		
		result.put(RESULT_STATE, state);
		result.put(ERROR_MESS, errorMess);
		result.put(RESULT_VALUE, targetValue);
		
		return result;
	}
	
	//サーバ外部出力ファイル型チェック在職区分型
	private Map<String, String> checkOfficeType(String itemValue, AwDataFormatSet setting, String sid) {
		Map<String, String> result = new HashMap<String, String>();
		String state = RESULT_OK;
		String errorMess = "";
		String targetValue = "";
		StatusOfEmployment status;
		//TODO
		GeneralDate date = GeneralDate.fromString(itemValue, "w");;
		
		Optional<StatusOfEmploymentResult> statusOfEmployment = statusOfEmploymentAdapter.getStatusOfEmployment(sid, date);
		if(statusOfEmployment.isPresent()) {
			status = EnumAdaptor.valueOf(statusOfEmployment.get().getStatusOfEmployment(), StatusOfEmployment.class);
			switch (status) {
			case INCUMBENT:
				//TODO ??????????????????????????? Mai QA
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
			for(int i = 0; i < (fixLength - data.length()); i++) {
				addString.append("0");
			}
			result.append(addString);	
			break;
		case AFTER_SPACE:
			for(int i = 0; i < (fixLength - data.length()); i++) {
				addString.append(" ");
			}
			result.append(addString);
			break;
		case BEFORE_ZERO:
			for(int i = 0; i < (fixLength - data.length()); i++) {
				addString.append("0");
			}
			result = addString.append(result);
			break;
		case BEFORE_SPACE:
			for(int i = 0; i < (fixLength - data.length()); i++) {
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
