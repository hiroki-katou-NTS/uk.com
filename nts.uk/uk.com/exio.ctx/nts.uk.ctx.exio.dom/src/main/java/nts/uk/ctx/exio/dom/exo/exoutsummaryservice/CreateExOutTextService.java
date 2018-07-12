package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
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
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeListRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class CreateExOutTextService extends ExportService<Object> {
	
	@Inject
	private OutCndDetailItemRepository outCndDetailItemRepo;
	
	@Inject
	private SearchCodeListRepository searchCodeListRepo;

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

	@Override
	protected void handle(ExportServiceContext<Object> context) {
		ExOutSetting domain = (ExOutSetting) context.getQuery();
		executeServerExOutManual(domain, context.getGeneratorContext());
	}

	public void executeServerExOutManual(ExOutSetting exOutSetting, FileGeneratorContext generatorContext) {
		Map<String, Object> settingResult = getServerExOutSetting(exOutSetting);
		initExOutLogInformation(exOutSetting);
		serverExOutExecution(exOutSetting, settingResult);
	}
	
	private Map<String, Object> getServerExOutSetting(ExOutSetting exOutSetting) {
		Map<String, Object> settingResult = new HashMap<String, Object>();
		List<StdOutputCondSet> stdOutputCondSetList = acquisitionExOutSetting.getExOutSetting(null, true, exOutSetting.getConditionSetCd());
		Optional<StdOutputCondSet> stdOutputCondSet = (stdOutputCondSetList.size() > 0) ? Optional.of(stdOutputCondSetList.get(0)) : Optional.empty();
		
		List<StandardOutputItem> standardOutputItemList = acquisitionExOutSetting.getExOutItemList(exOutSetting.getConditionSetCd(), null, "", true, true);
		Set<CtgItemData> ctgItemDataList = new HashSet<CtgItemData>();
		
		//TODO Ai siêu thì tối ưu giúp đoạn này
		for (StandardOutputItem standardOutputItem : standardOutputItemList) {
			for(CategoryItem categoryItem : standardOutputItem.getCategoryItems()) {
				// TODO chờ sửa primative value của domain CategoryItem
				ctgItemDataRepo.getCtgItemDataByIdAndDisplayClass(categoryItem.getCategoryId().v().toString(), 
						categoryItem.getCategoryItemNo().v(), 1).ifPresent(item -> ctgItemDataList.add(item));
			}
		}
		
		settingResult.put("stdOutputCondSet", stdOutputCondSet);
		settingResult.put("standardOutputItemList", standardOutputItemList);
		return settingResult;
	}
	
	// アルゴリズム「外部出力取得条件一覧」を実行する with type = fixed form (standard)
	private List<CtgItemDataCustom> getExOutCond(String code) {
		List<OutCndDetailItem> outCndDetailItemList = outCndDetailItemRepo.getOutCndDetailItemByCode(code);
		List<CtgItemDataCustom> ctgItemDataCustomList = new ArrayList<CtgItemDataCustom>();
		List<SearchCodeList> searchCodeList;
		Optional<CtgItemData> ctgItemData;
		StringBuilder cond = new StringBuilder();

		for (OutCndDetailItem outCndDetailItem : outCndDetailItemList) {
			searchCodeList = searchCodeListRepo.getSearchCodeByCateIdAndCateNo(
					outCndDetailItem.getCategoryId(), outCndDetailItem.getCategoryItemNo().v());
			ctgItemData = ctgItemDataRepo.getCtgItemDataById(outCndDetailItem.getCategoryId(),
					outCndDetailItem.getCategoryItemNo().v());
			cond.setLength(0);
			
			if(ctgItemData.isPresent()) {
				continue;
			}
			
			if(ctgItemData.get().getSearchValueCd().isPresent() && "with".equals(ctgItemData.get().getSearchValueCd().get().toLowerCase())) {
				for (SearchCodeList searchCodeItem: searchCodeList) {
					cond.append(", ");
					cond.append(searchCodeItem.getSearchCode());
	
					if ((ctgItemData.get().getDataType() == DataType.CHARACTER) || (ctgItemData.get().getDataType() == DataType.DATE) 
							|| (ctgItemData.get().getDataType() == DataType.TIME)) {
						cond.append("'");
						cond.append(searchCodeItem.getSearchCode());
						cond.append("'");
					} else {
						cond.append(searchCodeItem.getSearchCode());
					}
				}
			}
			
			ctgItemDataCustomList.add(new CtgItemDataCustom(ctgItemData.get().getItemName(), cond.toString()));
		}

		//TODO Làm xong thì xem lại
		return ctgItemDataCustomList;
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
	private void serverExOutExecution(ExOutSetting exOutSetting, Map<String, Object> settingResult) {
		
		String processingId = exOutSetting.getProcessingId();
		//TODO get lại setting name
		String fileName = exOutSetting.getConditionSetCd() + "settingName" + processingId;
		
		exOutOpMngRepo.getExOutOpMngById(processingId).ifPresent(exOutOpMng -> {
			exOutOpMng.setOpCond(ExIoOperationState.EXPORTING);
			
			// TODO chờ QA
			if("type" == "data") {
				exOutOpMng.setProUnit(I18NText.getText("#CMF002_527"));
				exOutOpMng.setProCnt(0);
				exOutOpMng.setTotalProCnt(exOutSetting.getSidList().size());
			} else {
				exOutOpMng.setProUnit(I18NText.getText("#CMF002_528"));
			}
			
			exOutOpMngRepo.update(exOutOpMng);
		});
		
		// TODO chờ QA
		if("type" == "data") {
			StdOutputCondSet stdOutputCondSet = (StdOutputCondSet) settingResult.get("stdOutputCondSet");
			serverExOutTypeData(exOutSetting, stdOutputCondSet, fileName);
		} else {
			
		}
	}
	
	//サーバ外部出力タイプデータ系
	private void serverExOutTypeData(ExOutSetting exOutSetting, StdOutputCondSet stdOutputCondSet, String fileName) {
		//サーバ外部出力ファイル項目ヘッダ
		if(stdOutputCondSet.getConditionOutputName() == NotUseAtr.USE) {
			
		}
		
		if(stdOutputCondSet.getItemOutputName() == NotUseAtr.USE) {
			
		}
	}
}
