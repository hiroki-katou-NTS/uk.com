package nts.uk.ctx.exio.dom.exi.item;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
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
		acceptCondRepo.updateFromD(condSet);
	}

	@Override
	public void registerAndReturn(List<StdAcceptItem> listItem, StdAcceptCondSet conditionSetting) {
		StdAcceptCondSet condSet = acceptCondRepo.getStdAcceptCondSetById(conditionSetting.getCid(),
				conditionSetting.getSystemType().value, conditionSetting.getConditionSetCd().v()).get();
		condSet.updateWhenSettingItems(conditionSetting.getCategoryId().get(),
				conditionSetting.getCsvDataLineNumber().get().v(), conditionSetting.getCsvDataStartLine().get().v());
		acceptCondRepo.updateFromD(condSet);
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
			acceptCondRepo.updateFromD(condSet);
		} else {
			String msg = TextResource.localize("Msg_904");
			for (String s: errorList) {
				msg = msg + "\n\t" + s;
			}
			RawErrorMessage errorMsg = new RawErrorMessage(msg);
			throw new BusinessException(errorMsg);
		}
	}

}
