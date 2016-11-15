package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;

public class JpaItemMasterRepository extends JpaRepository implements ItemMasterRepository {

	@Override
	public List<ItemMaster> findAll(String companyCode, int categoryType) {
		return null;
	}

	@Override
	public Optional<ItemMaster> find(String companyCode, int categoryType, String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
