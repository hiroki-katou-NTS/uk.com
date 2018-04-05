package nts.uk.ctx.exio.dom.exi.item;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

/**
 * 
 * @author HungTT
 *
 */
public interface StdAcceptItemService {

	void register(List<StdAcceptItem> listItem, StdAcceptCondSet conditionSetting);
	
	void registerAndReturn(List<Pair<StdAcceptItem, String>> listItem, StdAcceptCondSet conditionSetting);
	
}
