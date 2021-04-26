package nts.uk.cnv.dom.td.schema.snapshot;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnComment;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnJpName;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnName;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnType;
import nts.uk.cnv.dom.td.alteration.content.column.RemoveColumn;
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangePK;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.snapshot.Healper.Alter;
import nts.uk.cnv.dom.td.schema.snapshot.Healper.Column;
import nts.uk.cnv.dom.td.schema.snapshot.Healper.Table;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;

@RunWith(Enclosed.class)
public class TableSnapshotTest {

	public static class Apply {
		
		@Test
		public void addTable() {
			val base = TableSnapshot.empty();
			val alter = Alter.create(new AddTable(Table.BASE));
			val actual = base.apply(Arrays.asList(alter)).get();
			assertSameDesign(actual, Table.BASE);
		}
		
		@Test
		public void addTable_既に存在するのでエラー() {
			val base = new TableSnapshot(null, Table.BASE);
			val alter = Alter.create(new AddTable(Table.BASE));
			
			assertThatThrownBy(() -> {
				base.apply(Arrays.asList(alter));
			}).isInstanceOf(BusinessException.class);
		}
		
		@Test
		public void removeTable() {
			val base = new TableSnapshot(null, Table.BASE);
			val alter = Alter.create(new RemoveTable());
			val actual = base.apply(Arrays.asList(alter));
			
			assertThat(actual.isPresent()).isFalse();
		}
		
		@Test
		public void removeTable_既に存在しないのでエラー() {
			val base = TableSnapshot.empty();
			val alter = Alter.create(new RemoveTable());

			assertThatThrownBy(() -> {
				base.apply(Arrays.asList(alter));
			}).isInstanceOf(BusinessException.class);
		}

		@Test
		public void changeTableName() {
			val actual = createApplied(new ChangeTableName("NEW_NAME"));
			val expected = Table.builder().tableName("NEW_NAME").build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void changeTableJpName() {
			val actual = createApplied(new ChangeTableJpName("新しい名前"));
			val expected = Table.builder().tableJpName("新しい名前").build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void addColumn() {
			val newColumn = Column.create("id3", "COL3", "列3", Column.Type.varchar(20), "come", 2);
			TableDesign actual = createApplied(new AddColumn(newColumn));
			TableDesign expected = Table.builder()
					.columns(Arrays.asList(
							Table.BASE.getColumns().get(0),
							Table.BASE.getColumns().get(1),
							newColumn))
					.build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void removeColumn() {
			TableDesign actual = createApplied(new RemoveColumn("id2"));
			TableDesign expected = Table.builder()
					.columns(Arrays.asList(
							Table.BASE.getColumns().get(0)))
					.build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void removeColumn_主キーは削除できないのでエラー() {
			val base = new TableSnapshot(null, Table.BASE);
			val alter = Alter.create(new RemoveColumn("id1"));
			
			assertThatThrownBy(() -> {
				base.apply(Arrays.asList(alter));
			}).isInstanceOf(BusinessException.class);
		}
		
		@Test
		public void removeColumn_存在しない列は削除できないのでエラー() {
			val base = new TableSnapshot(null, Table.BASE);
			val alter = Alter.create(new RemoveColumn("id???"));
			
			assertThatThrownBy(() -> {
				base.apply(Arrays.asList(alter));
			}).isInstanceOf(BusinessException.class);
		}
		
		@Test
		public void changeColumnName() {
			ColumnDesign base = Table.BASE.getColumns().get(1);
			TableDesign actual = createApplied(new ChangeColumnName(base.getId(), "NEW_NAME"));
			TableDesign expected = Table.builder()
					.columns(Arrays.asList(
							Table.BASE.getColumns().get(0),
							Column.builder(base).columnName("NEW_NAME").build()))
					.build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void changeColumnName_存在しない列なのでエラー() {
			assertThatThrownBy(() -> {
				createApplied(new ChangeColumnName("???", "NEW_NAME"));
			}).isInstanceOf(BusinessException.class);
		}
		
		@Test
		public void changeColumnJpName() {
			ColumnDesign base = Table.BASE.getColumns().get(1);
			TableDesign actual = createApplied(new ChangeColumnJpName(base.getId(), "新しい名前"));
			TableDesign expected = Table.builder()
					.columns(Arrays.asList(
							Table.BASE.getColumns().get(0),
							Column.builder(base).columnJpName("新しい名前").build()))
					.build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void changeColumnJpName_存在しない列なのでエラー() {
			assertThatThrownBy(() -> {
				createApplied(new ChangeColumnJpName("???", "新しい名前"));
			}).isInstanceOf(BusinessException.class);
		}
		
		@Test
		public void changeColumnComment() {
			ColumnDesign base = Table.BASE.getColumns().get(1);
			TableDesign actual = createApplied(new ChangeColumnComment(base.getId(), "新しいコメント"));
			TableDesign expected = Table.builder()
					.columns(Arrays.asList(
							Table.BASE.getColumns().get(0),
							Column.builder(base).comment("新しいコメント").build()))
					.build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void changeColumnComment_存在しない列なのでエラー() {
			assertThatThrownBy(() -> {
				createApplied(new ChangeColumnComment("???", "新しいコメント"));
			}).isInstanceOf(BusinessException.class);
		}
		
		@Test
		public void changeColumnType() {
			ColumnDesign base = Table.BASE.getColumns().get(1);
			TableDesign actual = createApplied(new ChangeColumnType(base.getId(), Column.Type.varchar(25)));
			TableDesign expected = Table.builder()
					.columns(Arrays.asList(
							Table.BASE.getColumns().get(0),
							Column.builder(base).type(Column.Type.varchar(25)).build()))
					.build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void changeColumnType_存在しない列なのでエラー() {
			assertThatThrownBy(() -> {
				createApplied(new ChangeColumnType("???", Column.Type.varchar(25)));
			}).isInstanceOf(BusinessException.class);
		}
		
		@Test
		public void changePK() {
			val actual = createApplied(new ChangePK(Arrays.asList("id1", "id2"), true));
			val expected = Table.builder()
					.constraints(new TableConstraints(
							new PrimaryKey(Arrays.asList("id1", "id2"), true),
							Collections.emptyList(),
							Collections.emptyList()))
					.build();
			assertSameDesign(actual, expected);
		}
		
		@Test
		public void changePK_存在しない列なのでエラー() {
			assertThatThrownBy(() -> {
				createApplied(new ChangePK(Arrays.asList("???"), true));
			}).isInstanceOf(BusinessException.class);
		}
		
		@Test
		public void テーブル名変更して列追加して追加した列の名前変更() {
			TableDesign actual = createApplied(Arrays.asList(
					new ChangeTableName("NEW_NAME"),
					new AddColumn(Column.create("id3", "COL3", "列3", Column.Type.varchar(5), "comment", 3)),
					new ChangeColumnName("id3", "COL3_NEW")
					));
			TableDesign expected = Table.builder()
					.tableName("NEW_NAME")
					.columns(Arrays.asList(
							Table.BASE.getColumns().get(0),
							Table.BASE.getColumns().get(1),
							Column.create("id3", "COL3_NEW", "列3", Column.Type.varchar(5), "comment", 3)
							))
					.build();
			assertSameDesign(actual, expected);
		}
	}

	static TableProspect createApplied(AlterationContent content) {
		return createApplied(Arrays.asList(content));
	}
	
	static TableProspect createApplied(List<AlterationContent> contents) {
		val base = new TableSnapshot(null, Table.BASE);
		val alter = Alter.create(contents);
		return base.apply(Arrays.asList(alter)).get();
	}

	static void assertSameDesign(TableDesign prospect, TableDesign design) {
		assertThat(new TableDesign(prospect)).isEqualTo(new TableDesign(design));
	}
}
