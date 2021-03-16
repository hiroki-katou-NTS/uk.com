package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.mastercheck;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItemRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.mastercheck.KrcmtMasterCheckFixedExtractItem;

@Stateless
public class JpaMasterCheckFixedExtractItemRepository extends JpaRepository
	implements MasterCheckFixedExtractItemRepository{

	private static final String SELECT_FROM_FIXED_MASTER_CHECK_ITEM = "SELECT c FROM KrcmtMasterCheckFixedExtractItem c ";
	private static final String SELECT_FROM_FIXED_MASTER_CHECK_ITEM_BY_NO = "SELECT c FROM KrcmtMasterCheckFixedExtractItem c "
			+ " WHERE c.no In :nos";
	
	@Override
	public List<MasterCheckFixedExtractItem> getAllFixedMasterCheckItem() {
		List<MasterCheckFixedExtractItem> data = 
				this.queryProxy().query(SELECT_FROM_FIXED_MASTER_CHECK_ITEM, KrcmtMasterCheckFixedExtractItem.class)
				.getList(c -> c.toDomain());
		return data;
	}

	@Override
	public List<MasterCheckFixedExtractItem> getFixedMasterCheckByNo(List<Integer> lstItemNo) {
		List<MasterCheckFixedExtractItem> data = 
				this.queryProxy().query(SELECT_FROM_FIXED_MASTER_CHECK_ITEM_BY_NO, KrcmtMasterCheckFixedExtractItem.class)
				.setParameter("nos", lstItemNo)
				.getList(c -> c.toDomain());
		return data;
	}


}
