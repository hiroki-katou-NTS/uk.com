package nts.uk.ctx.exio.dom.exo.condset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.outcnddetail.ConditionSettingCd;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
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
	private OutCndDetailRepository stdOutCndDetailRepository;

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
		List<OutCndDetailItem> listOutCndDetailItem = outCndDetailItemRepository.getOutCndDetailItemByCidAndCode(cid, condSetCd);
		if (listOutCndDetailItem != null && !listOutCndDetailItem.isEmpty()){
			outCndDetailItemRepository.remove(listOutCndDetailItem);
		}
		if (listStandardOutputItem != null && !listStandardOutputItem.isEmpty()) {
			stdOutputItemRepository.remove(listStandardOutputItem);
		}
		
		if (listStandardOutputItemOrder != null && !listStandardOutputItemOrder.isEmpty()){
			standardOutputItemOrderRepository.remove(listStandardOutputItemOrder);
		}
		if(outCndDetail.isPresent()) {
			outCndDetailRepository.remove(cid, condSetCd);
		}
		stdOutputCondSetRepository.remove(cid, condSetCd);
	}

	// 外部出力設定登録確認
	private boolean outputSetRegisConfir(boolean isNewMode, int standType, String cId, int autoExecution, String cndSetCd) {
		if (isNewMode) {
			if (standType == StandardAtr.STANDARD.value) {
  				if (checkExist(cId, cndSetCd)){
  					throw new BusinessException("Msg_3");
  				} else if (autoExecution == NotUseAtr.NOT_USE.value) {
  					throw new BusinessException("Msg_677");
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

		// 外部出力取得項目一覧_定型
		List<StandardOutputItem> listStdOutputItem = outputAcquisitionItemList(cId, cndSetCode);

		// 外部出力取得項目並順一覧_定型
		List<StandardOutputItemOrder> listStdOutputItemOrder = outputAcquisitionItemOrderList(cId, cndSetCode);
		
		// 外部出力取得条件一覧
		outCndDetail = outCndDetailRepository.getOutCndDetailByCode(cndSetCode);
		List<OutCndDetailItem> listOutCndDetailItem = outputAcquisitionConditionList(cndSetCode);

		// 取得内容の項目を複写先用の情報に変更する
		changeContent(listStdOutputItem, copyParams.getConditionSetCode().v(), outCndDetail, listOutCndDetailItem, listStdOutputItemOrder);

		// 外部出力設定複写実行登録
		copyExecutionRegistration(copyParams, standType, listStdOutputItem, listStdOutputItemOrder, outCndDetail,
				listOutCndDetailItem);

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
	private List<OutCndDetailItem> outputAcquisitionConditionList(String conditionSettingCd) {
		return mAcquisitionExOutSetting.getExOutCond(conditionSettingCd, null, StandardAtr.STANDARD, false, null);
	}

	// 外部出力設定複写実行登録
	private void copyExecutionRegistration(StdOutputCondSet copyParams, int standType,
			List<StandardOutputItem> listStdOutputItem, List<StandardOutputItemOrder> listStdOutputItemOrder,
			Optional<OutCndDetail> outCndDetail, List<OutCndDetailItem> outCndDetailItem) {
		boolean isNewMode = true;

		// 外部出力登録条件設定
		updateOutputCndSet(copyParams, isNewMode, standType);

		// 外部出力登録出力項目
		registrationOutputItem(listStdOutputItem, listStdOutputItemOrder);

		// 外部出力登録条件詳細
		registrationCndDetail(outCndDetail, outCndDetailItem);
	}

	// 取得内容の項目を複写先用の情報に変更する
	private void changeContent(List<StandardOutputItem> listStdOutputItem, String cndSetCode, Optional<OutCndDetail> outCndDetail,
			List<OutCndDetailItem> listOutCndDetailItem, List<StandardOutputItemOrder> listStdOutputItemOrder) {
		if (outCndDetail.isPresent()) {
			outCndDetail.get().setConditionSettingCd(new ConditionSettingCd(cndSetCode));
		}
		for (StandardOutputItem standardOutputItem : listStdOutputItem) {
			standardOutputItem.setConditionSettingCode(new ConditionSettingCode(cndSetCode));
		}
		for (OutCndDetailItem outCndDetailItem : listOutCndDetailItem) {
			outCndDetailItem.setConditionSettingCd(new ConditionSettingCd(cndSetCode));
		}
		for (StandardOutputItemOrder stdOutputItemOrder : listStdOutputItemOrder) {
			stdOutputItemOrder.setConditionSettingCode(new ConditionSettingCode(cndSetCode));
		}
		
	}

	// 外部出力登録出力項目_定型
	private void registrationOutputItem(List<StandardOutputItem> listStdOutputItem, List<StandardOutputItemOrder> listStdOutputItemOrder) {
			if (listStdOutputItem != null && !listStdOutputItem.isEmpty()) {
				stdOutputItemRepository.add(listStdOutputItem);
			}
			if (listStdOutputItemOrder != null && !listStdOutputItemOrder.isEmpty())
			standardOutputItemOrderRepository.add(listStdOutputItemOrder);
		
	}

	// 外部出力登録条件詳細
	private void registrationCndDetail(Optional<OutCndDetail> outCndDetail, List<OutCndDetailItem> listOutCndDetailItem) {
		if (outCndDetail !=null && outCndDetail.isPresent()) {
			stdOutCndDetailRepository.add(outCndDetail.get());
		}
		if(listOutCndDetailItem != null && !listOutCndDetailItem.isEmpty()) {
			outCndDetailItemRepository.add(listOutCndDetailItem);
		}
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
			}
			if (data == null || data.isEmpty()) {
				throw new BusinessException("Msg_754");
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
