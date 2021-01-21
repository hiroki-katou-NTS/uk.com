package nts.uk.ctx.pereg.infra.repository.mastercopy;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.PpemtPInfoItemGroup;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.definition.PpemtPInfoItemGroupDf;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.CopyContext;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.IdContainer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CopyGroupItem {

	private final CopyContext ctx;
	
	private final IdContainer ids;

	public static void execute(CopyContext copyContext, IdContainer idContainer) {
		new CopyGroupItem(copyContext, idContainer).execute();
	}
	
	private void execute() {
		
		switch (ctx.copyMethodOnConflict) {
		case DO_NOTHING:
			// 何もしない
			return;
			
		case REPLACE_ALL:
			// 既存データを全削除
			deleteAll(ctx.companyId.target);
			break;
			
		case ADD_NEW_ONLY:
			// 既存データがあれば何もしない
			if (existsData(ctx.companyId.target)) {
				return;
			}
			break;
		default:
			throw new RuntimeException("unknown: " + ctx.copyMethodOnConflict);
		}
		
		// コピー実行
		executeCopy();
	}
	
	/**
	 * コピー実行
	 */
	private void executeCopy() {
		
		val groupItemIdGenerator = new IdContainer.IdGenerator();
		
		val copiedPpemtPInfoItemGroup = findAllPpemtPInfoItemGroup(ctx.companyId.source).stream()
				.map(s -> s.copy(ctx.companyId.target, groupItemIdGenerator))
				.collect(toList());
		
		val groupItemIds = groupItemIdGenerator.fix();
		
		val copiedPpemtPInfoItemGroupDf = findAllPpemtPInfoItemGroupDf(ctx.companyId.source).stream()
				.map(s -> s.copy(ctx.companyId.target, groupItemIds, ids))
				.collect(toList());

		ctx.command.insertAll(copiedPpemtPInfoItemGroup);
		ctx.command.insertAll(copiedPpemtPInfoItemGroupDf);
	}
	
	private void deleteAll(String companyId) {
		
		val tables = new String[] {
				"PPEMT_GROUP_ITEM",
				"PPEMT_GROUP_ITEM_DF"
		};
		
		for (String table : tables) {
			String sql = "delete from " + table + " where CID = @cid";
			ctx.jdbc(sql).paramString("cid", companyId).execute();
		}
	}
	
	private boolean existsData(String companyId) {
		return !findAllPpemtPInfoItemGroup(companyId).isEmpty();
	}
	
	private List<PpemtPInfoItemGroup> findAllPpemtPInfoItemGroup(String companyId) {
		return findAll("PPEMT_GROUP_ITEM", companyId, PpemtPInfoItemGroup.MAPPER);
	}
	
	private List<PpemtPInfoItemGroupDf> findAllPpemtPInfoItemGroupDf(String companyId) {
		return findAll("PPEMT_GROUP_ITEM_DF", companyId, PpemtPInfoItemGroupDf.MAPPER);
	}
	
	private <E> List<E> findAll(String tableName, String companyId, JpaEntityMapper<E> mapper) {
		
		String sql = "select * from " + tableName + " where CID = @cid";
		return ctx.jdbc(sql).paramString("cid", companyId).getList(rec -> mapper.toEntity(rec));
	}
}
