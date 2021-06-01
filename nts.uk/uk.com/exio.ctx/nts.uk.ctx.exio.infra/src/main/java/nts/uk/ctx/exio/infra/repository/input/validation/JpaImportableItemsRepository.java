package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.ImportableItemsRepository;
import nts.uk.ctx.exio.infra.entity.input.validation.OioctImportableItem;

public class JpaImportableItemsRepository extends JpaRepository implements ImportableItemsRepository{

	@Override
	public List<ImportableItem> get(String companyId, int groupId) {
		
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
