package nts.uk.ctx.exio.infra.repository.input.revise;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItemRepository;
import nts.uk.ctx.exio.infra.entity.input.revise.XimmtReviseItem;

@Stateless
public class JpaReviseItemRepository extends JpaRepository implements ReviseItemRepository {

	@Override
	public Optional<ReviseItem> get(String companyId, ExternalImportCode settingCode, int importItemNumber) {
		
		String sql 	= " select f "
					+ " from XimmtReviseItem f"
					+ " where f.pk.companyId =:companyID "
					+ " and f.pk.settingCode =:settingCD "
					+ " and f.pk.itemNo =:importItemNO ";
		
		return this.queryProxy().query(sql, XimmtReviseItem.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode.toString())
				.setParameter("importItemNO", importItemNumber)
				.getSingle(rec -> rec.toDomain());
	}
}
