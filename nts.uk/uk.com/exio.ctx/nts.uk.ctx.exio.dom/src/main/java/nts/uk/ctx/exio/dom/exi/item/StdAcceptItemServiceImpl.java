package nts.uk.ctx.exio.dom.exi.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

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
		acceptCondRepo.getStdAcceptCondSetById(conditionSetting.getCid(), conditionSetting.getSystemType().value,
				conditionSetting.getConditionSetCd().v()).ifPresent(condSet -> {
					Integer lineNumber [] = {null}, startLine [] = {null}, characterCode [] = {null};
					conditionSetting.getCsvDataLineNumber().ifPresent(savedlineNumber -> {
						lineNumber[0] = savedlineNumber.v();
					});
					conditionSetting.getCsvDataStartLine().ifPresent(savedStartLine ->{
						startLine[0] = savedStartLine.v();
					});
					conditionSetting.getCharacterCode().ifPresent(savedCharacterCode ->{
						characterCode[0] = savedCharacterCode.value;
					});
					condSet.updateWhenSettingItems(conditionSetting.getCategoryId().orElse(null),
							lineNumber[0],
							startLine[0],
							characterCode[0]);
					acceptItemRepo.removeAll(conditionSetting.getCid(), conditionSetting.getSystemType().value,
							conditionSetting.getConditionSetCd().v());
					acceptItemRepo.addList(listItem);
					condSet.updateCheckComplete(NotUseAtr.NOT_USE.value);
					acceptCondRepo.updateFromD(condSet);
				});
	}

	@Override
	public void registerAndReturn(List<Pair<StdAcceptItem, String>> listItem, StdAcceptCondSet conditionSetting) {
		StdAcceptCondSet condSet = acceptCondRepo.getStdAcceptCondSetById(conditionSetting.getCid(),
				conditionSetting.getSystemType().value, conditionSetting.getConditionSetCd().v()).get();
		condSet.updateWhenSettingItems(conditionSetting.getCategoryId().get(),
				conditionSetting.getCsvDataLineNumber().get().v(), 
				conditionSetting.getCsvDataStartLine().get().v(),
				conditionSetting.getCharacterCode().get().value);
		acceptCondRepo.updateFromD(condSet);
		acceptItemRepo.removeAll(conditionSetting.getCid(), conditionSetting.getSystemType().value,
				conditionSetting.getConditionSetCd().v());
		acceptItemRepo.addList(listItem.stream().map(i -> i.getLeft()).collect(Collectors.toList()));
		inputCheck(listItem, condSet);
	}

	private void inputCheck(List<Pair<StdAcceptItem, String>> listItem, StdAcceptCondSet condSet) {
		List<String> errorList = new ArrayList<>();
		for (Pair<StdAcceptItem, String> item : listItem) {
			if (!item.getLeft().getCsvItemName().isPresent()) {
				// add msg 902
				errorList.add(TextResource.localize("Msg_902", item.getRight()));
			}
			if (!item.getLeft().getDataFormatSetting().isPresent()) {
				// add msg 903
				errorList.add(TextResource.localize("Msg_903", item.getRight()));
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
