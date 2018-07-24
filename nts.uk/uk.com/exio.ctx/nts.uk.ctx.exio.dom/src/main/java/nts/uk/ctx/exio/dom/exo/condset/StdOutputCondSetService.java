package nts.uk.ctx.exio.dom.exo.condset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataCndDetail;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutCtgItem;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeListRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.ConditionSettingCode;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class StdOutputCondSetService {

	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;

	@Inject
	private StandardOutputItemRepository stdOutputItemRepository;

	@Inject
	private OutCndDetailItemRepository outCndDetailItemRepository;
	
	@Inject
	private StandardOutputItemOrderRepository standardOutputItemOrderRepository;

	@Inject
	private OutCndDetailRepository outCndDetailRepository;

	@Inject
	private SearchCodeListRepository searchCodeListRepository;

	@Inject
	private OutCndDetailRepository stdOutCndDetailRepository;

	@Inject
	private AcquisitionExOutCtgItem mAcquisitionExOutCtgItem;

	@Inject
	private AcquisitionExOutSetting mAcquisitionExOutSetting;

	// Screen T
	public Map<String, String> excuteCopy(String copyDestinationCode, String destinationName, String conditionSetCd,
			boolean overwite) {
		Map<String, String> resultExvuteCopy = new HashMap<>();
		String cid = AppContexts.user().companyId();
		Optional<StdOutputCondSet> stdOutputCondSet = stdOutputCondSetRepository.getStdOutputCondSetById(cid,
				copyDestinationCode);
		if (stdOutputCondSet.isPresent()) {
			if (overwite) {
				resultExvuteCopy.put("result", "OK");
				resultExvuteCopy.put("overwrite", "TO");
			} else {
				throw new BusinessException("Msg_3");
			}
		} else {
			resultExvuteCopy.put("result", "OK");
			resultExvuteCopy.put("overwrite", "DONOT");
		}
		resultExvuteCopy.put("copyDestinationCode", copyDestinationCode);
		resultExvuteCopy.put("destinatioName", destinationName);
		return resultExvuteCopy;
	}
	// ******

	public void registerOutputSet(boolean isNewMode, int standType, StdOutputCondSet stdOutputCondSet,
			int autoExecution ) {
		if (outputSetRegisConfir(isNewMode, standType, stdOutputCondSet.getCid(), autoExecution, stdOutputCondSet.getConditionSetCode().v())) {
			updateOutputCndSet(stdOutputCondSet, isNewMode, standType);
		} else {
			throw new BusinessException("Msg_677");
		}
	}
	
	
	/**
	 * 外部出力設定削除実行
	 * @param cid
	 * @param condSetCd
	 */
	public void remove(String cid, String condSetCd){
		List<StandardOutputItem> listStandardOutputItem = stdOutputItemRepository.getStdOutItemByCidAndSetCd(cid, condSetCd);
		List<StandardOutputItemOrder> listStandardOutputItemOrder = standardOutputItemOrderRepository.getStandardOutputItemOrderByCidAndSetCd(cid, condSetCd);
		Optional<OutCndDetail> outCndDetail = outCndDetailRepository.getOutCndDetailById(cid, condSetCd);
		if (listStandardOutputItem != null && !listStandardOutputItem.isEmpty()) {
			stdOutputItemRepository.remove(listStandardOutputItem);
		}
		
		if (listStandardOutputItemOrder != null && !listStandardOutputItemOrder.isEmpty()){
			standardOutputItemOrderRepository.remove(listStandardOutputItemOrder);
		}
		if(outCndDetail != null && outCndDetail.isPresent()) {
			outCndDetailRepository.remove(cid, condSetCd);
		}
		stdOutputCondSetRepository.remove(cid, condSetCd);
	}

	// 外部出力設定登録確認
	private boolean outputSetRegisConfir(boolean isNewMode, int standType, String cId, int autoExecution, String cndSetCd) {
		if (isNewMode) {
			if (standType == StandardAtr.STANDARD.value) {
  				if (checkExist(cId, cndSetCd)){
 					return false;
  				} else if (autoExecution == NotUseAtr.NOT_USE.value) {
  						return false;
  					}
			}
		}
		return true;
	}

	private boolean checkExist(String cid, String cndSetCd) {
		Optional<StdOutputCondSet> stdOutputCondSet = stdOutputCondSetRepository.getStdOutputCondSetById(cid, cndSetCd);
		if (stdOutputCondSet.isPresent()) {
			return true;
		}
		return false;
	}
	
	//外部出力登録条件設定
	private void updateOutputCndSet(StdOutputCondSet stdOutputCondSet, boolean isNewMode, int standType){
		if (standType == StandardAtr.STANDARD.value) {
			if (isNewMode){
				stdOutputCondSetRepository.add(stdOutputCondSet);
			} else {
				stdOutputCondSetRepository.update(stdOutputCondSet);
			}
		}
	}

	// 外部出力設定複写実行
	private void outputSettingCopy(String cndSetCode, int standType, StdOutputCondSet copyParams) {

		String cId = copyParams.getCid();
		Optional<OutCndDetail> outCndDetail = Optional.empty();
		List<SearchCodeList> searchCodeList = null;

		// 外部出力取得項目一覧_定型
		List<StandardOutputItem> listStdOutputItem = outputAcquisitionItemList(cId, cndSetCode);

		// 外部出力取得項目並順一覧_定型
		List<StandardOutputItemOrder> listStdOutputItemOrder = outputAcquisitionItemOrderList(cId, cndSetCode);

		// 外部出力取得条件一覧
		outputAcquisitionConditionList(cndSetCode, outCndDetail, searchCodeList);

		// 取得内容の項目を複写先用の情報に変更する
		changeContent(listStdOutputItem, cndSetCode);

		// 外部出力設定複写実行登録
		copyExecutionRegistration(copyParams, standType, listStdOutputItem, listStdOutputItemOrder, outCndDetail,
				searchCodeList);

	}

	// 外部出力取得項目並順一覧_定型
	private List<StandardOutputItemOrder> outputAcquisitionItemOrderList(String cId, String cndSetCode) {
		return standardOutputItemOrderRepository.getStandardOutputItemOrderByCidAndSetCd(cId, cndSetCode);
	}
	
	//外部出力設定複製
	public void copy( int standType, String cndSetCode, StdOutputCondSet copyParams ){
		if (standType == StandardAtr.STANDARD.value) {
			outputSettingCopy(cndSetCode, standType, copyParams);
		}

	}

	// 外部出力取得項目一覧_定型
	private List<StandardOutputItem> outputAcquisitionItemList(String cId, String cndSetCode) {
		return stdOutputItemRepository.getStdOutItemByCidAndSetCd(cId, cndSetCode);
	}

	// 外部出力取得条件一覧
	private void outputAcquisitionConditionList(String conditionSettingCd, Optional<OutCndDetail> outCndDetail,
			List<SearchCodeList> searchCodeList) {
		outCndDetail = outCndDetailRepository.getOutCndDetailByCode(conditionSettingCd);
		List<OutCndDetailItem> listOutCndDetailItem = outCndDetailItemRepository
				.getOutCndDetailItemByCode(conditionSettingCd);
		for (OutCndDetailItem outCndDetailItem : listOutCndDetailItem) {
			searchCodeList = searchCodeListRepository.getSearchCodeByCateIdAndCateNo(outCndDetailItem.getCategoryId(),
					outCndDetailItem.getCategoryItemNo().v());
			for (SearchCodeList searchCode : searchCodeList) {
				mAcquisitionExOutSetting.getExOutCond(searchCode.getSearchCode().v(), null, StandardAtr.STANDARD, false, null);
			}
		}

	}

	// 外部出力設定複写実行登録
	private void copyExecutionRegistration(StdOutputCondSet copyParams, int standType,
			List<StandardOutputItem> listStdOutputItem, List<StandardOutputItemOrder> listStdOutputItemOrder,
			Optional<OutCndDetail> outCndDetail, List<SearchCodeList> searchCodeList) {
		boolean isNewMode = true;

		// 外部出力登録条件設定
		updateOutputCndSet(copyParams, isNewMode, standType);

		// 外部出力登録出力項目
		registrationOutputItem(listStdOutputItem);

		// 外部出力登録条件詳細
		registrationCndDetail(outCndDetail, searchCodeList);
	}

	// 取得内容の項目を複写先用の情報に変更する
	private void changeContent(List<StandardOutputItem> listStdOutputItem, String cndSetCode) {
		for (StandardOutputItem standardOutputItem : listStdOutputItem) {
			standardOutputItem.setConditionSettingCode(new ConditionSettingCode(cndSetCode));
		}
	}

	// 外部出力登録出力項目_定型
	private void registrationOutputItem(List<StandardOutputItem> listStdOutputItem) {
		for (StandardOutputItem standardOutputItem : listStdOutputItem) {
			stdOutputItemRepository.add(standardOutputItem);
		}
	}

	// 外部出力登録条件詳細
	private void registrationCndDetail(Optional<OutCndDetail> outCndDetail, List<SearchCodeList> searchCodeList) {
		if (outCndDetail !=null && outCndDetail.isPresent()) {
			stdOutCndDetailRepository.add(outCndDetail.get());
		}
		if(searchCodeList != null && !searchCodeList.isEmpty()) {
			for (SearchCodeList searchCode : searchCodeList) {
				searchCodeListRepository.add(searchCode);
			}
		}
	}

	// アルゴリズム「外部出力条件設定」を実行する
	public CtgItemDataCndDetail outputExCndList(int categoryId, int ctgItemNo) {
		List<OutCndDetailItem> dataCndItemDetail = mAcquisitionExOutSetting.getExOutCond(String.valueOf(categoryId),
				null, StandardAtr.STANDARD, false, null);
		List<CtgItemData> listData = mAcquisitionExOutCtgItem.getListExOutCtgItemData(categoryId, ctgItemNo);
		for (CtgItemData temp : listData) {
			if (temp.getDataType() == DataType.ATWORK) {
				listData.remove(temp);
			}
		}
		List<String> dataTableName = listData.stream().map(temp -> temp.getTableName()).collect(Collectors.toList());

		return new CtgItemDataCndDetail(listData, dataTableName, dataCndItemDetail);
	}

	// 起動する
	public List<StdOutputCondSet> getListStandardOutputItem(String cId, String cndSetCd) {
		List<StdOutputCondSet> data = stdOutputCondSetRepository.getStdOutputCondSetById(cId,
				Optional.ofNullable(cndSetCd));
		String userID = AppContexts.user().userId();

		for (StdOutputCondSet temp : data) {
			if (mAcquisitionExOutSetting.getExOutItemList(temp.getConditionSetCode().toString(), userID,
					temp.getItemOutputName().toString(), StandardAtr.STANDARD, true).isEmpty()) {
				data.remove(temp);
			}
			if (data.size() == 0) {
				throw new BusinessException("Msg_754");
			}
		}
		return data;
	}

	// 外部出力取得項目一覧
	public List<StandardOutputItem> outputAcquisitionItemList(String condSetCd, String userId, String outItemCd,
			StandardAtr standardType, boolean isAcquisitionMode) {
		return mAcquisitionExOutSetting.getExOutItemList(condSetCd, userId, outItemCd, standardType,
				isAcquisitionMode);
	}

}
