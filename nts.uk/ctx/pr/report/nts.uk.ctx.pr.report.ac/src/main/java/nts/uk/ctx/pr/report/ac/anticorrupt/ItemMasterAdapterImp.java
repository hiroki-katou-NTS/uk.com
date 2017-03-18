package nts.uk.ctx.pr.report.ac.anticorrupt;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.finder.itemmaster.ItemMasterPub;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingItemMaster;
import nts.uk.ctx.pr.report.dom.payment.comparing.ItemCode;
import nts.uk.ctx.pr.report.dom.payment.comparing.ItemMasterAdapter;
import nts.uk.ctx.pr.report.dom.payment.comparing.ItemName;

@Stateless
public class ItemMasterAdapterImp implements ItemMasterAdapter {

	@Inject
	private ItemMasterPub itemMasterPub;

	@Override
	public List<ComparingItemMaster> findItemMasterByCatergory(int categoryAtr) {
		return this.itemMasterPub.find_SEL_3(categoryAtr).stream().map(
				item -> new ComparingItemMaster(new ItemCode(item.getItemCode()), new ItemName(item.getItemAbName())))
				.collect(Collectors.toList());
	}

}
