package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.infra.entity.input.validation.XimctImportableItem;

@Stateless
public class JpaImportableItemsRepository extends JpaRepository implements ImportableItemsRepository{

	@Override
	public Optional<ImportableItem> get(ImportingDomainId groupId, int itemNo) {
		
		String sql = "select * from XIMCT_IMPORTABLE_ITEM"
						+ " where GROUP_ID = @group"
						+ " and ITEM_NO = @item";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramInt("group", groupId.value)
				.paramInt("item", itemNo)
				.getSingle(rec -> XimctImportableItem.MAPPER.toEntity(rec).toDomain());
	}

	@Override
	public List<ImportableItem> get(ImportingDomainId groupId) {
		
		String sql = "select * "
						+ "from XIMCT_IMPORTABLE_ITEM "
						+ "where GROUP_ID = @groupId";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramInt("groupId", groupId.value)
				.getList(rec -> XimctImportableItem.MAPPER.toEntity(rec).toDomain());
	}

}
