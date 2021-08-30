package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

/**
 * existsやdeleteの対象となるドメインのテーブル行の識別子
 */
@Value
public class DomainDataId {

	private final String tableName;
	private final List<Key> keys;
	
	public DomainDataId changeTableName(String newName) {
		return new DomainDataId(newName, new ArrayList<>(keys));
	}
	
	public static Builder builder(String tableName, KeyValues keyValues) {
		return new Builder(tableName, keyValues);
	}
	
	@RequiredArgsConstructor
	public static class Builder {
		private final String tableName;
		private final KeyValues keyValues;
		private final List<Key> keys = new ArrayList<>();
		private int currentIndex = 0;
		
		public Builder addKey(DomainDataColumn key) {
			
			Object value;
			switch (key.getDataType()) {
			case STRING:
				value = keyValues.getString(currentIndex);
				break;
			case INT:
				value = keyValues.getInt(currentIndex);
				break;
			case REAL:
				value = keyValues.getBigDecimal(currentIndex);
				break;
			case DATE:
				value = keyValues.getGeneralDate(currentIndex);
				break;
			default:
				throw new RuntimeException("unknown: " + key);
			}
			
			this.keys.add(new Key(key, value));
			currentIndex+=1;
			
			return this;
		}
		
		public Builder key(String name, int value) {
			this.keys.add(new Key(name, DataType.INT, value));
			return this;
		}
		
		public Builder key(String name, BigDecimal value) {
			this.keys.add(new Key(name, DataType.REAL, value));
			return this;
		}
		
		public Builder key(String name, GeneralDate value) {
			this.keys.add(new Key(name, DataType.DATE, value));
			return this;
		}
		
		public DomainDataId build() {
			return new DomainDataId(tableName, keys);
		}
	}
	
	@RequiredArgsConstructor
	@ToString
	@Getter
	public static class Key {
		private final DomainDataColumn column;
		private final Object value;
		
		Key(String name, DataType type, Object value) {
			this(new DomainDataColumn(name, type), value);
		}
		
		public void setParam(NtsStatement statement, String paramName) {
			column.getDataType().setParam(statement, paramName, value);
		}
	}
}
