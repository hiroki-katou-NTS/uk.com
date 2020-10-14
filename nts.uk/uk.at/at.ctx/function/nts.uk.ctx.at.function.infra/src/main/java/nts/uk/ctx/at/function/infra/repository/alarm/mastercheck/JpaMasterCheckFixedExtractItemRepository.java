package nts.uk.ctx.at.function.infra.repository.alarm.mastercheck;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractItem;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractItemRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck.KrcmtMasterCheckFixedExtractItem;

@Stateless
public class JpaMasterCheckFixedExtractItemRepository extends JpaRepository
	implements MasterCheckFixedExtractItemRepository{

	private static final String SELECT_FROM_FIXED_MASTER_CHECK_ITEM = "SELECT c FROM KrcmtMasterCheckFixedExtractItem c ";
	
	@Override
	public List<MasterCheckFixedExtractItem> getAllFixedMasterCheckItem() {
		List<MasterCheckFixedExtractItem> data = 
				this.queryProxy().query(SELECT_FROM_FIXED_MASTER_CHECK_ITEM, KrcmtMasterCheckFixedExtractItem.class)
				.getList(c -> c.toDomain());
		return data;
	}


}
