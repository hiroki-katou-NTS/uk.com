package nts.uk.ctx.exio.dom.exi.item;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;

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
		condSet.updateWhenSettingItems(conditionSetting.getCategoryId().get(),
				conditionSetting.getCsvDataLineNumber().get().v(), conditionSetting.getCsvDataStartLine().get().v());
		acceptItemRepo.removeAll(conditionSetting.getCid(), conditionSetting.getSystemType().value,
				conditionSetting.getConditionSetCd().v());
		acceptItemRepo.addList(listItem);
		condSet.updateCheckComplete(NotUseAtr.NOT_USE.value);
		acceptCondRepo.update(condSet);
	}

	@Override
	public void registerAndReturn(List<StdAcceptItem> listItem, StdAcceptCondSet conditionSetting) {
		StdAcceptCondSet condSet = acceptCondRepo.getStdAcceptCondSetById(conditionSetting.getCid(),
				conditionSetting.getSystemType().value, conditionSetting.getConditionSetCd().v()).get();
		condSet.updateWhenSettingItems(conditionSetting.getCategoryId().get(),
				conditionSetting.getCsvDataLineNumber().get().v(), conditionSetting.getCsvDataStartLine().get().v());
		acceptCondRepo.update(condSet);
		acceptItemRepo.removeAll(conditionSetting.getCid(), conditionSetting.getSystemType().value,
				conditionSetting.getConditionSetCd().v());
		acceptItemRepo.addList(listItem);
		inputCheck(listItem, condSet);
	}

	private void inputCheck(List<StdAcceptItem> listItem, StdAcceptCondSet condSet) {
		List<String> errorList = new ArrayList<>();
		for (StdAcceptItem item : listItem) {
			if (!item.getCsvItemName().isPresent()) {
				// add msg 902
				errorList.add(TextResource.localize("Msg_902", "" + item.getAcceptItemNumber()));
			}
			if (!item.getDataFormatSetting().isPresent()) {
				// add msg 903
				errorList.add(TextResource.localize("Msg_903", "" + item.getAcceptItemNumber()));
			}
		}
		if (errorList.isEmpty()) {
			condSet.updateCheckComplete(NotUseAtr.USE.value);
			acceptCondRepo.update(condSet);
		} else {
			for (String s: errorList) {
				System.out.println(s);
			}
			throw new BusinessException("Msg_904");
		}
	}

}
