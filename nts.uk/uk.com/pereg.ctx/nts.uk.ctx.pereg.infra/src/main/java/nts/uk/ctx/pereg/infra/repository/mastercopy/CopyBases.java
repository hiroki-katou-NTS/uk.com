package nts.uk.ctx.pereg.infra.repository.mastercopy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtgCommon;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtItemCommon;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.CopyContext;

/**
 * テナント作成時にコピーする基本テーブルのコピー処理
 */
@RequiredArgsConstructor
public class CopyBases {
	
	private static final Map<String, JpaEntityMapper<? extends CopiedOnTenantCreated>> TABLES = new HashMap<>();
	static {
		TABLES.put("PPEMT_CTG_COMMON", PpemtCtgCommon.MAPPER);
		TABLES.put("PPEMT_ITEM_COMMON", PpemtItemCommon.MAPPER);
	}

	public static void execute(CopyContext ctx) {
		
		for (val table : TABLES.entrySet()) {
			String tableName = table.getKey();
			val mapper = table.getValue();
			
			String select = "select * from " + tableName + " where CONTRACT_CD = @cd";
			List<? extends CopiedOnTenantCreated> entities = ctx.jdbc(select).paramString("cd", ctx.contractCode.source)
					.getList(rec -> mapper.toEntity(rec));
			
			entities.forEach(e -> e.changeContractCode(ctx.contractCode.target));
			
			ctx.command.insertAll(entities);
		}
	}
}
