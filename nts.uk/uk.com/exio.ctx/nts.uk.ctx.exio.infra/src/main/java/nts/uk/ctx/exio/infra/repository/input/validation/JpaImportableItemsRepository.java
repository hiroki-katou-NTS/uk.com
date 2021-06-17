package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.infra.entity.input.validation.OioctImportableItem;

public class JpaImportableItemsRepository extends JpaRepository implements ImportableItemsRepository{

	@Override
	public List<ImportableItem> get(String companyId, ImportingGroupId groupId) {
		
		String sql = "select * "
						+ "from OIOCT_IMPORTABLE_ITEM "
						+ "where CID = @cid "
						+ "and GROUP_ID = @groupId";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("cid", companyId)
				.paramInt("groupId", groupId.value)
				.getList(rec -> OioctImportableItem.MAPPER.toEntity(rec).toDomain());
	}

}
