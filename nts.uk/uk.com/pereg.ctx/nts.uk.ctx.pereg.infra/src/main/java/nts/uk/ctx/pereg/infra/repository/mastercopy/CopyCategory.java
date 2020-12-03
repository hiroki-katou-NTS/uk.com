package nts.uk.ctx.pereg.infra.repository.mastercopy;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtgSort;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.CopyContext;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.IdContainer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CopyCategory {

	private final CopyContext ctx;
	
	public static IdContainer.IdsMap execute(CopyContext copyContext) {
		return new CopyCategory(copyContext).execute();
	}
	
	private IdContainer.IdsMap execute() {
		
		// 「削除新規」以外は何もしない仕様
		if (ctx.copyMethodOnConflict != CopyMethodOnConflict.REPLACE_ALL) {
			return new IdContainer.IdsMap();
		}

		// 既存データを全削除
		deleteAll(ctx.companyId.target);
		
		// コピー実行
		return executeCopy();
	}

	private IdContainer.IdsMap executeCopy() {
		
		val categoryIdGenerator = new IdContainer.IdGenerator();
		
		val copiedPpemtCtg = findAllPpemtCtg(ctx.companyId.source).stream()
				.map(s -> s.copy(ctx.companyId.target, categoryIdGenerator))
				.collect(toList());
		
		val categoryIds = categoryIdGenerator.fix();
		
		val copiedPpemtCtgSort = findAllPpemtCtgSort(ctx.companyId.source).stream()
				.map(s -> s.copy(ctx.companyId.target, categoryIds))
				.collect(toList());

		ctx.command.insertAll(copiedPpemtCtg);
		ctx.command.insertAll(copiedPpemtCtgSort);
		
		return categoryIds;
	}
	
	private List<PpemtCtg> findAllPpemtCtg(String companyId) {
		return findAll("PPEMT_CTG", companyId, PpemtCtg.MAPPER);
	}
	
	private List<PpemtCtgSort> findAllPpemtCtgSort(String companyId) {
		return findAll("PPEMT_CTG_SORT", companyId, PpemtCtgSort.MAPPER);
	}
	
	private <E> List<E> findAll(String tableName, String companyId, JpaEntityMapper<E> mapper) {

		String sql = "select * from " + tableName + " where CID = @cid";
		
		return ctx.jdbc(sql).paramString("cid", companyId)
				.getList(rec -> mapper.toEntity(rec));
	}
	
	private void deleteAll(String companyId) {
		
		val tables = new String[] { "PPEMT_CTG", "PPEMT_CTG_SORT" };
		
		for (String table : tables) {
			String deleteSql = "delete from " + table + " where CID = @cid";
			ctx.jdbc.query(deleteSql).paramString("cid", companyId).execute();
		}
	}
	
}
