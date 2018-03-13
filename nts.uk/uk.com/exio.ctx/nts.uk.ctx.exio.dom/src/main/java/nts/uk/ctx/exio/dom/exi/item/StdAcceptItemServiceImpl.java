package nts.uk.ctx.exio.dom.exi.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author HungTT
 *
 */
@Stateless
public class StdAcceptItemServiceImpl implements StdAcceptItemService {

	@Inject
	private StdAcceptItemRepository acceptItemRepo;

	@Inject
	private StdAcceptCondSetRepository acceptCondRepo;

	@Override
	public void register(List<StdAcceptItem> listItem, StdAcceptCondSet conditionSetting) {
		StdAcceptCondSet condSet = acceptCondRepo.getStdAcceptCondSetById(conditionSetting.getCid(),
				conditionSetting.getSystemType().value, conditionSetting.getConditionSetCd().v()).get();
		condSet.updateWhenSettingItems(NotUseAtr.NOT_USE.value, conditionSetting.getCategoryId().get(),
				conditionSetting.getCsvDataLineNumber().get().v(), conditionSetting.getCsvDataStartLine().get().v());
		acceptCondRepo.update(conditionSetting);
		acceptItemRepo.removeAll(conditionSetting.getCid(), conditionSetting.getSystemType().value,
				conditionSetting.getConditionSetCd().v());
		acceptItemRepo.addList(listItem);
	}

}
