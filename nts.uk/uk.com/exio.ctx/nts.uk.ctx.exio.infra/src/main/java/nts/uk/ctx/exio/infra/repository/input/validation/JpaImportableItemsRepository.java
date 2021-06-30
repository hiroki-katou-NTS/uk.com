package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.infra.entity.input.validation.XimctImportableItem;

public class JpaImportableItemsRepository extends JpaRepository implements ImportableItemsRepository{

	@Override
	public List<ImportableItem> get(ImportingGroupId groupId) {
		
		String sql = "select * "
						+ "from XIMCT_IMPORTABLE_ITEM "
						+ "where GROUP_ID = @groupId";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramInt("groupId", groupId.value)
				.getList(rec -> XimctImportableItem.MAPPER.toEntity(rec).toDomain());
	}

}
