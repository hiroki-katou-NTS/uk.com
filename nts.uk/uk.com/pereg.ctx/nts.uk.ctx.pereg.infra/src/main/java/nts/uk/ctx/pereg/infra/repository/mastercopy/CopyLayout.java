package nts.uk.ctx.pereg.infra.repository.mastercopy;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtLayoutNewEntry;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemCls;
import nts.uk.ctx.pereg.infra.entity.layout.cls.definition.PpemtLayoutItemClsDf;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.CopyContext;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.IdContainer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CopyLayout {

	private final CopyContext ctx;
	
	private final IdContainer ids;
	
	public static void execute(CopyContext copyContext, IdContainer idContainer) {
		new CopyLayout(copyContext, idContainer).execute();
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
		
		val layoutIdGenerator = new IdContainer.IdGenerator();
		
		val copiedPpemtLayoutNewEntry = findAllPpemtLayoutNewEntry(ctx.companyId.source).stream()
				.map(s -> s.copy(ctx.companyId.target, layoutIdGenerator))
				.collect(toList());
		
		val layoutIds = layoutIdGenerator.fix();

		val copiedPpemtLayoutItemCls = findAllPpemtLayoutItemCls(layoutIds.getAllIdsSource()).stream()
				.map(s -> s.copy(layoutIds, ids))
				.collect(toList());

		val copiedPpemtLayoutItemClsDf = findAllPpemtLayoutItemClsDf(layoutIds.getAllIdsSource()).stream()
				.map(s -> s.copy(layoutIds, ids))
				.collect(toList());

		ctx.command.insertAll(copiedPpemtLayoutNewEntry);
		ctx.command.insertAll(copiedPpemtLayoutItemCls);
		ctx.command.insertAll(copiedPpemtLayoutItemClsDf);
	}
	
	private boolean existsData(String companyId) {
		return !findAllPpemtLayoutNewEntry(companyId).isEmpty();
	}
	
	private void deleteAll(String companyId) {
		
		val targetLayoutIds = findAllPpemtLayoutNewEntry(companyId).stream()
				.map(e -> e.ppemtNewLayoutPk.layoutId)
				.collect(toList());
		
		if (targetLayoutIds.isEmpty()) return;
		
		val tables = new String[] {
				"PPEMT_LAYOUT_NEW_ENTRY",
				"PPEMT_LAYOUT_ITEM_CLS",
				"PPEMT_LAYOUT_ITEM_CLS_DF"
		};
		
		for (String table : tables) {
			String deleteSql = "delete from " + table + " where LAYOUT_ID in @layout";
			ctx.jdbc.query(deleteSql).paramString("layout", targetLayoutIds).execute();
		}
	}
	
	private List<PpemtLayoutNewEntry> findAllPpemtLayoutNewEntry(String companyId) {
		
		String sql = "select * from PPEMT_LAYOUT_NEW_ENTRY where CID = @cid";
		
		return ctx.jdbc(sql)
				.paramString("cid", companyId)
				.getList(rec -> PpemtLayoutNewEntry.MAPPER.toEntity(rec));
	}
	
	private List<PpemtLayoutItemCls> findAllPpemtLayoutItemCls(List<String> layoutIds) {
		return findAllByLayoutIds("PPEMT_LAYOUT_ITEM_CLS", layoutIds, PpemtLayoutItemCls.MAPPER);
	}
	
	private List<PpemtLayoutItemClsDf> findAllPpemtLayoutItemClsDf(List<String> layoutIds) {
		return findAllByLayoutIds("PPEMT_LAYOUT_ITEM_CLS_DF", layoutIds, PpemtLayoutItemClsDf.MAPPER);
	}
	
	private <E> List<E> findAllByLayoutIds(String tableName, List<String> layoutIds, JpaEntityMapper<E> mapper) {
		
		String sql = "select * from " + tableName + " where LAYOUT_ID in @layout";
		
		return ctx.jdbc(sql).paramString("layout", layoutIds)
				.getList(rec -> mapper.toEntity(rec));
	}
}
