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
		acceptItemRepo.removeAll(conditionSetting);
		acceptItemRepo.addList(listItem);
		conditionSetting.updateCheckCompleted(NotUseAtr.NOT_USE.value);
		acceptCondRepo.update(conditionSetting);
	}
	
	
}
