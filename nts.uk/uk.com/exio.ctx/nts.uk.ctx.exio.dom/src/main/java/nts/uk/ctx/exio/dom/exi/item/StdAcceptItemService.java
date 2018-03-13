package nts.uk.ctx.exio.dom.exi.item;

import java.util.List;

import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

/**
 * 
 * @author HungTT
 *
 */
public interface StdAcceptItemService {

	void register(List<StdAcceptItem> listItem, StdAcceptCondSet conditionSetting);
	
}
