package nts.uk.ctx.exio.infra.repository.input.canonicalize.existing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.workspace.TemporaryTable;

/**
 * AnyRecordToDelete用一時テーブルのレイアウト
 */
@RequiredArgsConstructor
public class LayoutAnyRecordToDelete {
	
	private final JdbcProxy jdbcProxy;
	private final ExecutionContext context;
	
	private static final String TABLE_NAME = TemporaryTable.PREFIX + "ANY_RECORD_TO_DELETE";
	
	private String tableName() {
		return TABLE_NAME + "_" + context.getCompanyId().replace("-", "");
	}
	
	public void createTable() {
		
		TemporaryTable.dropTable(jdbcProxy, tableName());
		
		String sql = "create table " + tableName() + "("
				+ " ID char(36) not null,"
				+ " ITEM_NO decimal(5) not null,"
				+ " VALUE varchar(1000) null"
				+ ");";
		
		jdbcProxy.query(sql).execute();
	}
	
	public void insert(AnyRecordToDelete record) {
		
		String id = IdentifierUtil.randomUniqueId();
		
		record.getPrimaryKeys().forEach(key -> {
			
			String sql = "insert into " + tableName() + " values ("
					+ "'" + id + "'"
					+ ", " + key.getItemNo()
					+ ", '" + key.getValue().asString() + "'"
					+ ")";
			
			jdbcProxy.query(sql).execute();
		});
	}
	
	public List<AnyRecordToDelete> findAll() {
		
		String sql 	= " select *"
					+ " from " + tableName();
		
		List<Entity> allEntitys = this.jdbcProxy.query(sql)
				.getList(rec -> {
					return new Entity(
						rec.getString("ID"), 
						rec.getInt("ITEM_NO"), 
						rec.getString("VALUE"));
				});
		
		return assembleDeletes(allEntitys);
	}
	
	public List<AnyRecordToDelete> findAllWhere(int keyItemNo, String keyValue){
		
		String sqlByKey = " select *"
					+ " from " + tableName()
					+ " where ITEM_NO = @itemNO"
					+ " and VALUE = @value";
		
		List<String> ids = this.jdbcProxy.query(sqlByKey)
				.paramInt("itemNO", keyItemNo)
				.paramString("value", keyValue)
				.getList(rec -> {
					return rec.getString("ID");
				});
		
		String sqlByID = " select *"
				+ " from " + tableName()
				+ " where ID in @ids";
		
		List<Entity> allEntitys = new ArrayList<>();
		CollectionUtil.split(ids, 1000, subIdList -> {
			allEntitys.addAll(
				this.jdbcProxy.query(sqlByID)
					.paramString("ids", subIdList)
					.getList(rec -> {
						return new Entity(
								rec.getString("ID"), 
								rec.getInt("ITEM_NO"), 
								rec.getString("VALUE"));
					}));
		});
		
		return assembleDeletes(allEntitys);
	}
	
	private List<AnyRecordToDelete> assembleDeletes(List<Entity> allEntitys){
		val entitysMap = allEntitys
					.stream()
					.collect(Collectors.groupingBy(Entity::getId));
		val result = new ArrayList<AnyRecordToDelete>();
		for(List<Entity> entitys : entitysMap.values()) {
			result.add(toDomain(entitys));
		}
		return result;
	}
	
	private AnyRecordToDelete toDomain(List<Entity> entitys){
		val result = AnyRecordToDelete.create(context);
		
		entitys.forEach(entity -> {
			result.addKey(entity.itemNo, StringifiedValue.of(entity.value));
		});
		return result;
	}
	
	@AllArgsConstructor
	@Getter
	private class Entity{
		private final String id;
		private final int itemNo;
		private final String value;
	}
}
