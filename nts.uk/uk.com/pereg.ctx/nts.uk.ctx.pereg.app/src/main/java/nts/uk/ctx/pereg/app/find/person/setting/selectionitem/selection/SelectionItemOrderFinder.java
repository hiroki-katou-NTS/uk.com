package nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;

/**
 * 
 * @author tuannv
 *
 */

@Stateless
public class SelectionItemOrderFinder {

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRpo;

	
}
