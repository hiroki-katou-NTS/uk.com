package nts.uk.ctx.exio.dom.exo.condset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
<<<<<<< HEAD
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutCtgItem;
=======
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
>>>>>>> 12f74b76bd70d3bd0992a471a28bb9d938ee01b7
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeListRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.ConditionSettingCode;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StdOutputCondSetService {
	
	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;
	
	@Inject 
	private StandardOutputItemRepository stdOutputItemRepository;
	
	@Inject
	private OutCndDetailItemRepository outCndDetailItemRepository;
	
	@Inject
	private OutCndDetailRepository outCndDetailRepository;
	
	@Inject
	private SearchCodeListRepository searchCodeListRepository;
	
	@Inject
	private StandardOutputItemOrderRepository standardOutputItemOrderRepository;
	
	@Inject
	private OutCndDetailRepository stdOutCndDetailRepository;
	

	@Inject
	private AcquisitionExOutCtgItem mAcquisitionExOutCtgItem;
=======
    @Inject
    private AcquisitionExOutSetting mAcquisitionExOutSetting;
    


	
	
	//Screen T
	public Map<String, String> excuteCopy(String copyDestinationCode,String destinationName, String conditionSetCd, boolean overwite){
		Map<String, String> resultExvuteCopy = new HashMap<>();
		String cid = AppContexts.user().companyId();
		Optional<StdOutputCondSet> stdOutputCondSet = stdOutputCondSetRepository.getStdOutputCondSetById(cid,conditionSetCd);
		if(stdOutputCondSet.isPresent()){
			if(overwite){
				resultExvuteCopy.put("result", "OK");
				resultExvuteCopy.put("overwrite", "TO");
			}else{
				throw new BusinessException("Msg_3");
			}
		}else{
			resultExvuteCopy.put("result", "OK");
			resultExvuteCopy.put("overwrite", "DONOT");
		}
		resultExvuteCopy.put("copyDestinationCode", copyDestinationCode);
		resultExvuteCopy.put("destinatioName", destinationName);
	return resultExvuteCopy;
	}
	//******
	
	
	public void registerOutputSet(String screenMode , String standType, StdOutputCondSet stdOutputCondSet, boolean checkAutoExecution, List<StandardOutputItemOrder> stdOutItemOrder){
		if (outputSetRegisConfir(screenMode, standType, stdOutputCondSet.getCid(), checkAutoExecution)) {
			updateOutputCndSet(stdOutputCondSet,screenMode);
		} else {
			throw new BusinessException("Msg_677");
		}
	}
	
	//アルゴリズム「外部出力設定登録確認」を実行する
	private boolean outputSetRegisConfir(String screenMode , String standType, String cId, boolean checkAutoExecution){
		if (screenMode.equals("register")) {
			if (standType.equals("fixedForm")) {
  				if (checkExistCid(cId)){
 					return false;
  				} else if (checkAutoExecution) {
  					return true;
  					} else {
  						return false;
  					}
			}
		}
		return true;
	}
	
	private boolean checkExistCid(String cid){
		Optional<StdOutputCondSet> stdOutputCondSet = stdOutputCondSetRepository.getStdOutputCondSetByCid(cid);
		if (stdOutputCondSet.isPresent()){
			return true;
		}
		return false;
	}
	
	//外部出力登録条件設定
	private void updateOutputCndSet(StdOutputCondSet stdOutputCondSet, String screenMode){
		if (screenMode.equals("register")){
			stdOutputCondSetRepository.add(stdOutputCondSet);
		}
		if (screenMode.equals("update")) {
			stdOutputCondSetRepository.update(stdOutputCondSet);
		}
	}
	
	//外部出力設定複写実行
	private void outputSettingCopy(String cndSetCode, int standType, StdOutputCondSet copyParams){
		
		String cId = copyParams.getCid();
		Optional<OutCndDetail> outCndDetail = null;
		List<SearchCodeList> searchCodeList = null;
		
		//外部出力取得項目一覧_定型
		List<StandardOutputItem> listStdOutputItem = outputAcquisitionItemList(cId, cndSetCode);
		
		//外部出力取得項目並順一覧_定型
		List<StandardOutputItemOrder> listStdOutputItemOrder = outputAcquisitionItemOrderList(cId, cndSetCode);
		
		//外部出力取得条件一覧
		outputAcquisitionConditionList(cndSetCode, outCndDetail, searchCodeList);
		
		//取得内容の項目を複写先用の情報に変更する
		changeContent(listStdOutputItem, cndSetCode);
		
		//外部出力設定複写実行登録
		copyExecutionRegistration(copyParams, standType, listStdOutputItem, listStdOutputItemOrder, outCndDetail, searchCodeList);
		
		
	}
	
	//外部出力取得項目並順一覧_定型
	private List<StandardOutputItemOrder> outputAcquisitionItemOrderList(String cId, String cndSetCode){
		return standardOutputItemOrderRepository.getStandardOutputItemOrderByCidAndSetCd(cId, cndSetCode);	
	}
	
	//外部出力設定複製
	public void copy( int standType, String cndSetCode, StdOutputCondSet copyParams ){
		if (standType == StandardAttr.STANDARD.value) {
			outputSettingCopy(cndSetCode, standType, copyParams);
		}
	
	}
	
	//外部出力取得項目一覧_定型
	private List<StandardOutputItem> outputAcquisitionItemList(String cId, String cndSetCode){
		return stdOutputItemRepository.getStdOutItemByCidAndSetCd(cId, cndSetCode);	
	}
	
	//外部出力取得条件一覧
	private void outputAcquisitionConditionList(String conditionSettingCd, Optional<OutCndDetail> outCndDetail, List<SearchCodeList> searchCodeList){
		outCndDetail = outCndDetailRepository.getOutCndDetailByCode(conditionSettingCd);
		List<OutCndDetailItem> listOutCndDetailItem = outCndDetailItemRepository.getOutCndDetailItemByCode(conditionSettingCd);
		for (OutCndDetailItem outCndDetailItem : listOutCndDetailItem) {
			searchCodeList = searchCodeListRepository.getSearchCodeByCateIdAndCateNo(outCndDetailItem.getCategoryId(), outCndDetailItem.getCategoryItemNo().v());
			for (SearchCodeList searchCode : searchCodeList) {
				mAcquisitionExOutSetting.getExOutCond(searchCode.getSearchCode().v(), false);
			}
		}
		
	}
	
	//外部出力設定複写実行登録
	private void copyExecutionRegistration(StdOutputCondSet copyParams, int standType, List<StandardOutputItem> listStdOutputItem,
			List<StandardOutputItemOrder> listStdOutputItemOrder, Optional<OutCndDetail> outCndDetail, List<SearchCodeList> searchCodeList){
		String screenMode = "register";
		
		//外部出力登録条件設定
		updateOutputCndSet(copyParams, screenMode);
		
		//外部出力登録出力項目
		registrationOutputItem(listStdOutputItem);
		
		//外部出力登録条件詳細
		registrationCndDetail(outCndDetail, searchCodeList);
	}
	
	//取得内容の項目を複写先用の情報に変更する
	private void changeContent(List<StandardOutputItem> listStdOutputItem, String cndSetCode){
		for (StandardOutputItem standardOutputItem : listStdOutputItem) {
			standardOutputItem.setConditionSettingCode(new ConditionSettingCode(cndSetCode));
		}
	}
	
	//外部出力登録出力項目_定型
	private void registrationOutputItem(List<StandardOutputItem> listStdOutputItem){
		for (StandardOutputItem standardOutputItem : listStdOutputItem) {
			stdOutputItemRepository.add(standardOutputItem);
		}
	}
	
	//外部出力登録条件詳細
	private void registrationCndDetail(Optional<OutCndDetail> outCndDetail, List<SearchCodeList> searchCodeList){
		if (outCndDetail.isPresent()) {
			stdOutCndDetailRepository.add(outCndDetail.get());
		}
		for (SearchCodeList searchCode : searchCodeList) {
			searchCodeListRepository.add(searchCode);
		}
	}

	//取得した項目から、データ型が「在職区分」ものは除外する
	private List<CtgItemData> filterCtgItemByDataType(List<CtgItemData> listData){
		for(CtgItemData temp : listData){
			if(temp.getDataType() == DataType.ATWORK ){
				listData.remove(temp);
			}
		}
		return listData;
		
	}

    public List<StdOutputCondSet> getListStandardOutputItem(List<StdOutputCondSet> data){
        String userID = AppContexts.user().userId();
        
        for(StdOutputCondSet temp: data){
            if (mAcquisitionExOutSetting.getExOutItemList(temp.getConditionSetCode().toString(),userID,temp.getItemOutputName().toString(),true,true).isEmpty()){
                data.remove(temp);
            }
        }
        
        return data;
        
    }
   

}
