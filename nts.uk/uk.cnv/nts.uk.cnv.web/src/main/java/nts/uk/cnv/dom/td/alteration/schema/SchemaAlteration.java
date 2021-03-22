package nts.uk.cnv.dom.td.alteration.schema;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.dom.td.schema.TableIdentity;

/**
 * スキーマoruta
 */
@Value
public class SchemaAlteration {

	/** orutaID */
	String alterId;
	
	/** 日時 */
	GeneralDateTime datetime;
	
	/** 対象テーブル */
	String targetTableId;
	
	/** 操作 */
	SchemaAlterationOperation operation;
	
	/** 変更後のテーブル名 */
	Optional<String> newTableName;
	
	public static List<SchemaAlteration> create(Alteration alteration) {
		
		List<SchemaAlteration> results = new ArrayList<>();
		
		for (val content : alteration.getContents()) {
			
			SchemaAlterationOperation operation;
			Optional<String> newTableName = Optional.empty();
			
			if (content instanceof AddTable) {
				operation = SchemaAlterationOperation.CREATE_TABLE;
				newTableName = Optional.of(((AddTable) content).getTableDesign().getName().v());
			}
			
			else if (content instanceof ChangeTableName) {
				operation = SchemaAlterationOperation.RENAME_TABLE;
				newTableName = Optional.of(((ChangeTableName) content).getTableName());
			}
			
			else if (content instanceof RemoveTable) {
				operation = SchemaAlterationOperation.DROP_TABLE;
			}
			
			else {
				continue;
			}
			
			results.add(new SchemaAlteration(
					alteration.getAlterId(),
					alteration.getCreatedAt(),
					alteration.getTableId(),
					operation,
					newTableName));
		}
		
		return results;
	}
	
	/**
	 * 適用する
	 * @param targetTables
	 * @return
	 */
	public List<TableIdentity> apply(List<TableIdentity> targetTables) {
		
		switch (operation) {
		case CREATE_TABLE:
			return addThis(targetTables);
		case DROP_TABLE:
			return removeThis(targetTables);
		case RENAME_TABLE:
			return addThis(removeThis(targetTables));
		default:
			throw new RuntimeException("unknown: " + operation);
		}
	}

	private List<TableIdentity> removeThis(List<TableIdentity> targetTables) {
		return targetTables.stream()
				.filter(t -> t.getTableId().equals(targetTableId))
				.collect(toList());
	}

	private List<TableIdentity> addThis(List<TableIdentity> targetTables) {
		val result = new ArrayList<>(targetTables);
		result.add(createTableIdentity());
		return result;
	}
	
	private TableIdentity createTableIdentity() {
		return new TableIdentity(targetTableId, newTableName.get());
	}
}
