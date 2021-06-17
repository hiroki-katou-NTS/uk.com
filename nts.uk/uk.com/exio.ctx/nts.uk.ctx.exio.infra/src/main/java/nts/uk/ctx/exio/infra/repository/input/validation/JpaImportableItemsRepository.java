package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.ArrayList;
import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

public class JpaImportableItemsRepository extends JpaRepository implements ImportableItemsRepository{

	@Override
	public List<ImportableItem> get(String companyId, ImportingGroupId groupId) {
		
		//実テーブル作るまで保留。↓は書き終わった
//		String sql = "select * "
//						+ "from OIOCT_IMPORTABLE_ITEM "
//						+ "where CID = @cid "
//						+ "and GROUP_ID = @groupId";
//		return new NtsStatement(sql, this.jdbcProxy())
//				.paramString("cid", companyId)
//				.paramInt("groupId", groupId)
//				.getList(rec -> OioctImportableItem.MAPPER.toEntity(rec).toDomain());
		return new ArrayList<ImportableItem>();
	}

}
