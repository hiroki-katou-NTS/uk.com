package nts.uk.ctx.pereg.infra.repository.mastercopy;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtItemDateRange;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtItem;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtItemSort;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.CopyContext;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.IdContainer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CopyItem {

	private final CopyContext ctx;
	
	private final IdContainer.IdsMap categoryIds;
	
	public static IdContainer execute(CopyContext copyContext, IdContainer.IdsMap categoryIds) {
		return new CopyItem(copyContext, categoryIds).execute();
	}
	
	private IdContainer execute() {
		
		// 「削除新規」以外は何もしない仕様
		if (ctx.copyMethodOnConflict != CopyMethodOnConflict.REPLACE_ALL) {
			return new IdContainer(categoryIds, new IdContainer.IdsMap());
		}
		
		// 既存データを全て削除
		deleteAll(categoryIds.getAllIdsCopied());

		// コピー実行
		return executeCopy();
	}

	private IdContainer executeCopy() {
		
		// ITEM系のテーブルはCIDを持たないので、CategoryIDを使って参照、コピーする
		val sourceCategoryIds = categoryIds.getAllIdsSource();
		
		val itemIdGenerator = new IdContainer.IdGenerator();
		
		val copiedPpemtItem = findAllPpemtItem(sourceCategoryIds).stream()
				.map(s -> s.copy(categoryIds, itemIdGenerator))
				.collect(toList());
		
		val idContainer = new IdContainer(categoryIds, itemIdGenerator.fix());
		
		val copiedPpemtItemSort = findAllPpemtItemSort(sourceCategoryIds).stream()
				.map(s -> s.copy(idContainer))
				.collect(toList());
		
		val copiedPpemtItemDateRange = findAllPpemtItemDateRange(sourceCategoryIds).stream()
				.map(s -> s.copy(idContainer))
				.collect(toList());

		ctx.command.insertAll(copiedPpemtItem);
		ctx.command.insertAll(copiedPpemtItemSort);
		ctx.command.insertAll(copiedPpemtItemDateRange);
		return idContainer;
	}
	
	private List<PpemtItem> findAllPpemtItem(List<String> categoryIds) {
		return findAll("PPEMT_ITEM", categoryIds, PpemtItem.MAPPER);
	}
	
	private List<PpemtItemSort> findAllPpemtItemSort(List<String> categoryIds) {
		return findAll("PPEMT_ITEM_SORT", categoryIds, PpemtItemSort.MAPPER);
	}
	
	private List<PpemtItemDateRange> findAllPpemtItemDateRange(List<String> categoryIds) {
		return findAll("PPEMT_ITEM_DATE_RANGE", categoryIds, PpemtItemDateRange.MAPPER);
	}
	
	private <E> List<E> findAll(String tableName, List<String> categoryIds, JpaEntityMapper<E> mapper) {

		String sql = "select * from " + tableName + " where PER_INFO_CTG_ID in @ctg";
		
		return ctx.jdbc(sql).paramString("ctg", categoryIds)
				.getList(rec -> mapper.toEntity(rec));
	}
	
	private void deleteAll(List<String> categoryIds) {
		
		val tables = new String[] { "PPEMT_ITEM", "PPEMT_ITEM_SORT", "PPEMT_ITEM_DATE_RANGE" };
		
		for (String table : tables) {
			String deleteSql = "delete from " + table + " where PER_INFO_CTG_ID in @ctg";
			ctx.jdbc.query(deleteSql).paramString("ctg", categoryIds).execute();
		}
	}
}
