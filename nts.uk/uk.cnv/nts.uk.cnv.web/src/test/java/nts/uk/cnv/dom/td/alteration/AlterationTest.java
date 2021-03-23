package nts.uk.cnv.dom.td.alteration;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;

@RunWith(Enclosed.class)
public class AlterationTest {

	public static class AlterTable {
		
		@Test
		public void noFeature() {
			
			assertThatThrownBy(() -> Alteration.alter("", null, Table.BASE, Table.BASE))
				.isInstanceOfSatisfying(BusinessException.class, e -> {
					assertThat(e.getMessage()).isEqualTo("Featureを指定してください");
				});
		}
		
		@Test
		public void noAlters() {
			
			val actual = Alter.test(Table.BASE, Table.BASE);
			assertThat(actual.isPresent()).isFalse();
		}
		
		@Test
		public void changeTableName() {
			
			val altered = Table.builder()
					.tableName("HOGE")
					.build();
			val expected = Alter.create(new ChangeTableName("HOGE"));
			
			val actual = Alter.test(Table.BASE, altered).get();
			assertThat(actual.isSameAs(expected)).isTrue();
		}
		
		@Test
		public void changeTableJpName() {

			val altered = Table.builder()
					.tableJpName("ほげ")
					.build();
			val expected = Alter.create(new ChangeTableJpName("ほげ"));
			
			val actual = Alter.test(Table.BASE, altered).get();
			assertThat(actual.isSameAs(expected)).isTrue();
		}
		
		@Test
		public void addColumn() {
			
			val newColumns = Table.baseColumns();
			newColumns.add(new ColumnDesign("id3", "COL3", "列3", Column.Type.varchar(1), Dummy.COMMENT, 2));
			
			val altered = Table.builder()
					.columns(newColumns)
					.build();
			val expected = Alter.create(new AddColumn(new ColumnDesign("id3", "COL3", "列3", Column.Type.varchar(1), Dummy.COMMENT, 2)));
			
			val actual = Alter.test(Table.BASE, altered).get();
			assertThat(actual.isSameAs(expected)).isTrue();
		}
	}


	static class Alter {
		
		static Optional<Alteration> test(TableDesign base, TableDesign altered) {
			return Alteration.alter(Dummy.FEATURE_ID, Dummy.META, base, altered);
		}
		
		static Alteration create(AlterationContent content) {
			return create(Arrays.asList(content));
		}
		
		static Alteration create(List<AlterationContent> contents) {
			return new Alteration(
					Dummy.ALTER_ID,
					Dummy.FEATURE_ID,
					GeneralDateTime.FAKED_NOW,
					Table.BASE.getId(),
					Dummy.META,
					contents);
		}
		
		
	}
	
	static class Table {
		
		static final TableDesign BASE = create(Dummy.TABLE_NAME, Dummy.TABLE_JP_NAME, baseColumns(), baseConstraints());
		
		static TableDesign create(
				TableName tableName,
				String tableJpName,
				List<ColumnDesign> columns,
				TableConstraints constraints) {
			
			return new TableDesign(
					Dummy.TABLE_ID,
					tableName,
					tableJpName,
					columns,
					constraints);
		}
		
		static List<ColumnDesign> baseColumns() {
			return new ArrayList<>(Arrays.asList(
					new ColumnDesign("id1", "COL1", "列1", Column.Type.varchar(1), Dummy.COMMENT, 0),
					new ColumnDesign("id2", "COL2", "列2", Column.Type.real(3, 2), Dummy.COMMENT, 1)
					));
		}
		
		static TableConstraints baseConstraints() {
			return new TableConstraints(
					new PrimaryKey(Arrays.asList("id1"), false),
					Collections.emptyList(),
					Collections.emptyList());
		}
		
		static Builder builder() {
			return new Builder();
		}
		
		static class Builder {
			TableName tableName = BASE.getName();
			String tableJpName = BASE.getJpName();
			List<ColumnDesign> columns = BASE.getColumns();
			TableConstraints constraints = BASE.getConstraints();

			public Builder tableName(String tableName) {
				this.tableName = new TableName(tableName);
				return this;
			}
			
			public Builder tableJpName(String tableJpName) {
				this.tableJpName = tableJpName;
				return this;
			}
			
			public Builder columns(List<ColumnDesign> columns) {
				this.columns = columns;
				return this;
			}
			
			public Builder constraints(TableConstraints constraints) {
				this.constraints = constraints;
				return this;
			}
			
			public TableDesign build() {
				return create(tableName, tableJpName, columns, constraints);
			}
		}
	}
	
	static class Column {
		static class Type {
			static DefineColumnType varchar(int length) {
				return new DefineColumnType(DataType.VARCHAR, length, 0, false, "", "");
			}
			
			static DefineColumnType real(int length, int scale) {
				return new DefineColumnType(DataType.REAL, length, scale, false, "", "");
			}
		}
	}
	
	static class Dummy {
		static final String ALTER_ID = "oruta";
		static final String TABLE_ID = "table";
		static final TableName TABLE_NAME = new TableName("ABCDE_TABLE");
		static final String TABLE_JP_NAME = "テーブル";
		static final String COMMENT = "comment";
		static final String FEATURE_ID = "feature";
		static final AlterationMetaData META = new AlterationMetaData("user", COMMENT);
	}
}
