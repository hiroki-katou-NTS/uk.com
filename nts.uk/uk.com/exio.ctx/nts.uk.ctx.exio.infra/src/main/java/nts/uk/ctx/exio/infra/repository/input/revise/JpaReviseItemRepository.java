package nts.uk.ctx.exio.infra.repository.input.revise;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.revise.ReviseItemRepository;
import nts.uk.ctx.exio.infra.entity.input.revise.XimmtReviseItem;

public class JpaReviseItemRepository extends JpaRepository implements ReviseItemRepository {

	@Override
	public Optional<ReviseItem> get(String companyId, int settingCode, int importItemNumber) {
		
		String sql = " select f "
				+ " from XimmtReviseItem f"
				+ " where f.pk.companyId =:companyID "
				+ " and f.pk.settingCode =:settingCD "
				+ " and f.pk.itemNo =:importItemNO ";
		
		return this.queryProxy().query(sql, XimmtReviseItem.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode)
				.setParameter("importItemNO", importItemNumber)
				.getSingle(rec -> rec.toDomain());
	}
}
