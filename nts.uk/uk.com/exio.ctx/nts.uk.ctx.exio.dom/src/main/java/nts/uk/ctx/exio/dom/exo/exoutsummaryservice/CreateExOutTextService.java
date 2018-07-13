package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import nts.arc.i18n.I18NText;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
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
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
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
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
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
	
	private final static String GET_ASSOCIATION = "getOutCondAssociation";
	private final static String GET_ITEM_NAME = "getOutCondItemName";

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
	
	private ExOutSettingResult getServerExOutSetting(ExOutSetting exOutSetting) {
		List<StdOutputCondSet> stdOutputCondSetList = acquisitionExOutSetting.getExOutSetting(null, true, exOutSetting.getConditionSetCd());
		StdOutputCondSet stdOutputCondSet = (stdOutputCondSetList.size() > 0) ? stdOutputCondSetList.get(0) : null;
		Map<String, Object> condResult = acquisitionExOutSetting.getExOutCond(exOutSetting.getConditionSetCd(), true);
		List<StandardOutputItem> standardOutputItemList = acquisitionExOutSetting.getExOutItemList(exOutSetting.getConditionSetCd(), null, "", true, true);
		Set<CtgItemData> ctgItemDataList = new HashSet<CtgItemData>();
		OutCndDetailItem outCndDetailItem = (OutCndDetailItem) condResult.get("outCndDetailItem");
		String condSql = (String) condResult.get("condSql");
		
		//TODO Ai siêu thì tối ưu giúp đoạn này
		for (StandardOutputItem standardOutputItem : standardOutputItemList) {
			for(CategoryItem categoryItem : standardOutputItem.getCategoryItems()) {
				ctgItemDataRepo.getCtgItemDataByIdAndDisplayClass(categoryItem.getCategoryId().v(), 
						categoryItem.getCategoryItemNo().v(), 1).ifPresent(item -> ctgItemDataList.add(item));
			}
		}
		
		Optional<ExOutCtg> exOutCtg = exOutCtgRepo.getExOutCtgByIdAndCtgSetting(stdOutputCondSet.getCategoryId().v());
		Optional<ExCndOutput> exCndOutput = exCndOutputRepo.getExCndOutputById(stdOutputCondSet.getCategoryId().v());
		
		return new ExOutSettingResult(stdOutputCondSet, outCndDetailItem, exOutCtg, exCndOutput, standardOutputItemList, ctgItemDataList, condSql);
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
		List<StandardOutputItem> standardOutputItemList =settingResult.getStandardOutputItemList();
		
		//サーバ外部出力ファイル項目ヘッダ
		if(stdOutputCondSet != null && (stdOutputCondSet.getConditionOutputName() == NotUseAtr.USE)) {
			header.add(stdOutputCondSet.getConditionSetName().v());
		}
		if(stdOutputCondSet != null && (stdOutputCondSet.getItemOutputName() == NotUseAtr.USE)) {
			for(StandardOutputItem standardOutputItem : standardOutputItemList) {
				header.add(standardOutputItem.getOutputItemName().v());
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
			
			for(StandardOutputItem standardOutputItem : standardOutputItemList) {
				
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
		
		Set<CtgItemData> ctgItemDataList = settingResult.getCtgItemDataList();
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
			
			if(exCndOutput.get().getConditions().v().length() > 0) {
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
			}
			
			String value = "";
			String value1 = "";
			String value2 = "";
			String operator = "";
			String searchCodeListCond = settingResult.getCondSql();
			
			OutCndDetailItem outCndDetailItem = settingResult.getOutCndDetailItem();
			//Todo refactor switch case
			if(outCndDetailItem != null) {
				
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
		//TODO ko biet co dung ko :)
		sql.setLength(sql.length() - 4);
		sql.append(" order by");
		
		if(isdataType) {
			sql.append(" sid ");
		} else {
			for(String keyOrder : keyOrderList) {
				sql.append(keyOrder);
				sql.append(", ");
			}
			//TODO ko biet co dung ko :)
			sql.setLength(sql.length() - 2);
		}
		
		sql.append(" asc;");
		
		return sql.toString();
	}
	
	private void createWhereCondition(StringBuilder temp, String table, String key, String operation, String value ) {
		temp.append(table);
		temp.append(".");
		temp.append(key);
		temp.append(operation);
		temp.append(value);
		temp.append(" and ");
	}
}
