package nts.uk.ctx.exio.dom.exi.condset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;

@Stateless
public class StdAcceptCondSetServiceImpl implements StdAcceptCondSetService {
	@Inject
	private StdAcceptCondSetRepository repository;
	
	@Inject 
	private StdAcceptItemRepository acceptItemRepository;

	/**
	 * コピーの作成
	 * 複製ダイアログで指定したコピー先「コード・名称」で登録（または更新）する。
	 */
	@Override
	public void copyConditionSetting(StdAcceptCondSetCopyParam param) {
		//Destination AcceptItem data
		List<StdAcceptItem> destAcceptItemList = null;
		//Destination Condition setting data
		StdAcceptCondSet desCondSet = null;
		
		// Get source condition setting
		Optional<StdAcceptCondSet> sourceCondSetOp = repository.getStdAcceptCondSetById(param.getCId(),
				param.getSystemType(), param.getSourceCondSetCode());
		if (!sourceCondSetOp.isPresent())
			return;
		//get source  accept item list data
		List<StdAcceptItem> sourceAcceptItemList = acceptItemRepository.getListStdAcceptItems(param.getCId(), param.getSystemType(), param.getSourceCondSetCode());
		
		// make destination condition setting data.
		desCondSet = sourceCondSetOp.get();
		// make destination AcceptItem data.
		if(!CollectionUtil.isEmpty(sourceAcceptItemList)){
			destAcceptItemList = sourceAcceptItemList.stream().map(item -> {
				StdAcceptItem destAcceptItem = new StdAcceptItem(
						param.getCId(), 
						new AcceptanceConditionCode(param.getDestCondSetCode()), 
						item.getAcceptItemNumber(),
						EnumAdaptor.valueOf(param.getSystemType(), SystemType.class), 
						item.getCsvItemNumber(), 
						item.getCsvItemName(), 
						item.getItemType(), 
						item.getCategoryItemNo(),
						item.getAcceptScreenConditionSetting(),
						item.getDataFormatSetting());
				return destAcceptItem;
			}).collect(Collectors.toList());
		}
		
		//Check override
		Optional<StdAcceptCondSet> existCondSet = repository.getStdAcceptCondSetById(param.getCId(), param.getSystemType(), param.getDestCondSetCode());
		//Override 
		if (param.isOverride() && existCondSet.isPresent()) {
			//ドメインモデル「受入条件設定（定型）」へ更新する
			repository.update(desCondSet);
			//ドメインモデル「受入項目（定型）」へのデリートインサート
			//Delete current acceptItem data
			acceptItemRepository.removeAll(param.getCId(), param.getSystemType(), param.getDestCondSetCode());			
			//Register 受入項目（定型）
			if(CollectionUtil.isEmpty(destAcceptItemList)){ return;}
			//Copy AcceptItem
			for (StdAcceptItem stdAcceptItem : destAcceptItemList) {		
				acceptItemRepository.add(stdAcceptItem);
			}
			
		}else{
			//ドメインモデル「受入条件設定（定型）」へ登録する
			repository.add(desCondSet);
			
			if(CollectionUtil.isEmpty(destAcceptItemList)){ return;}			
			//Register 受入項目（定型）
			for (StdAcceptItem stdAcceptItem : destAcceptItemList) {
				acceptItemRepository.add(stdAcceptItem);
			}
		}
	}
	
	/**
	 * アルゴリズム「受入設定の削除」を実行する
	 */
	@Override
	public void deleteConditionSetting(String cid, int sysType, String conditionSetCd) {
		//ドメインモデル「受入条件設定」（定型）から削除を行う
		//(Delete from the domain model "acceptance condition setting" (fixed form))
		repository.remove(cid, sysType, conditionSetCd);
		
		//受入項目（定型）から削除を行う
		//(Delete from acceptance item (fixed form))
		acceptItemRepository.removeAll(cid, sysType, conditionSetCd);
		//TODO for next version
		//ドメインモデル「外部受入の自動設定」
		//(Domain model "Automatic setting of external acceptance")
	}

	/**
	 * アルゴリズム「受入設定の登録」を実行する
	 */
	@Override
	public void registerConditionSetting(StdAcceptCondSet domain) {
		//TODO for next version
		//サイドバーの選択状態を判別する
		//(Determine the sidebar selection status)
		
		//1件（同一キーのデータがある）
		if (this.repository.isSettingCodeExist(domain.getCompanyId(), domain.getSystemType().value, domain.getConditionSetCode().v())){
			//エラーメッセージ表示　Msg_3	
			throw new BusinessException("Msg_3");
		}
		//画面の「システム種類」「条件コード」でドメインモデル「受入条件設定（定型）」に登録する
		//(Register to the domain model "acceptance condition setting (fixed form)" with "system type" "condition code" on the screen)
		this.repository.add(domain);
	}
}
