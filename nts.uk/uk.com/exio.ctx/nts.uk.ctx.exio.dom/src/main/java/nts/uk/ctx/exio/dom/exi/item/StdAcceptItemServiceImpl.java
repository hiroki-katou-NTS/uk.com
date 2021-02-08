package nts.uk.ctx.exio.dom.exi.item;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author HungTT
 */
@Stateless
public class StdAcceptItemServiceImpl implements StdAcceptItemService {

	@Inject
	private StdAcceptItemRepository acceptItemRepo;

	@Inject
	private StdAcceptCondSetRepository acceptCondRepo;

	@Override
	public void register(List<StdAcceptItem> listItem, StdAcceptCondSet conditionSetting) {
		Optional<StdAcceptCondSet> currentDomain = this.acceptCondRepo.getStdAcceptCondSetById(conditionSetting.getCompanyId(),
																							   conditionSetting.getSystemType().value,
																							   conditionSetting.getConditionSetCode().v());
		if (currentDomain.isPresent()) {
			StdAcceptCondSet updateDomain = currentDomain.get().updateDomain(conditionSetting);
			this.acceptItemRepo.removeAll(conditionSetting.getCompanyId(),
										  conditionSetting.getSystemType().value,
										  conditionSetting.getConditionSetCode().v());
			this.acceptItemRepo.addList(listItem);
			updateDomain.setCheckCompleted(NotUseAtr.NOT_USE);
			this.acceptCondRepo.updateFromD(updateDomain);
		}
	}

	@Override
	public void registerAndReturn(List<Pair<StdAcceptItem, String>> listItem, StdAcceptCondSet conditionSetting) {
		Optional<StdAcceptCondSet> currentDomain = this.acceptCondRepo.getStdAcceptCondSetById(conditionSetting.getCompanyId(),
																							   conditionSetting.getSystemType().value,
																							   conditionSetting.getConditionSetCode().v());
		if (currentDomain.isPresent()) {
			StdAcceptCondSet updateDomain = currentDomain.get().updateDomain(conditionSetting);
			this.acceptCondRepo.updateFromD(updateDomain);
			this.acceptItemRepo.removeAll(conditionSetting.getCompanyId(),
										  conditionSetting.getSystemType().value,
										  conditionSetting.getConditionSetCode().v());
			this.acceptItemRepo.addList(listItem.stream().map(Pair::getLeft).collect(Collectors.toList()));
			inputCheck(listItem, updateDomain);
		}
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
			condSet.setCheckCompleted(NotUseAtr.USE);
			this.acceptCondRepo.updateFromD(condSet);
		} else {
			String msg = TextResource.localize("Msg_904");
			for (String s : errorList) {
				msg = msg + "\n\t" + s;
			}
			RawErrorMessage errorMsg = new RawErrorMessage(msg);
			throw new BusinessException(errorMsg);
		}
	}

}
