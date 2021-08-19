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
	public Optional<ImportableItem> get(ImportingDomainId domainId, int itemNo) {
		
		String sql = "select * from XIMCT_IMPORTABLE_ITEM"
						+ " where DOMAIN_ID = @domain"
						+ " and ITEM_NO = @item";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramInt("domain", domainId.value)
				.paramInt("item", itemNo)
				.getSingle(rec -> XimctImportableItem.MAPPER.toEntity(rec).toDomain());
	}

	@Override
	public List<ImportableItem> get(ImportingDomainId domainId) {
		
		String sql = "select * "
						+ "from XIMCT_IMPORTABLE_ITEM "
						+ "where DOMAIN_ID = @domainId";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramInt("domainId", domainId.value)
				.getList(rec -> XimctImportableItem.MAPPER.toEntity(rec).toDomain());
	}

}
