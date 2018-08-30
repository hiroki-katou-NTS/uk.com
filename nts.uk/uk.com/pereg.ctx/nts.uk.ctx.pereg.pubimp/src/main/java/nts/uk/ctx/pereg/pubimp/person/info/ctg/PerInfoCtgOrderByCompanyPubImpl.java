package nts.uk.ctx.pereg.pubimp.person.info.ctg;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.pub.person.info.ctg.IPerInfoCtgOrderByCompanyPub;

@Stateless
public class PerInfoCtgOrderByCompanyPubImpl implements IPerInfoCtgOrderByCompanyPub {

	@Inject
	private PerInfoCtgByCompanyRepositoty repo;
	
	@Override
	public HashMap<Integer, HashMap<String, Integer>> getOrderList(List<String> categoryIds,
			List<String> itemDefinitionIds) {
		// TODO Auto-generated method stub
		return repo.getOrderList(categoryIds, itemDefinitionIds);
	}
}
