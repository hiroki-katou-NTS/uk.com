package nts.uk.cnv.dom.td.alteration;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.cnv.dom.td.alteration.Helper.Alter;
import nts.uk.cnv.dom.td.alteration.Helper.Column;
import nts.uk.cnv.dom.td.alteration.Helper.Dummy;
import nts.uk.cnv.dom.td.alteration.Helper.Table;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnComment;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnJpName;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnName;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnType;
import nts.uk.cnv.dom.td.alteration.content.column.RemoveColumn;
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangePK;
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangeUnique;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;

@RunWith(Enclosed.class)
public class AlterationTest {

	public static class AlterTable {

		@Test
		public void エラー_featureIdが空文字列() {

			assertThatThrownBy(() -> Alteration.alter("", null, Table.BASE, Table.BASE))
				.isInstanceOfSatisfying(BusinessException.class, e -> {
					assertThat(e.getMessage()).isEqualTo("Featureを指定してください");
				});
		}

		@Test
		public void 変更が無いのでempty() {

			val actual = Alter.test(Table.BASE, Table.BASE);
			assertThat(actual.isPresent()).isFalse();
		}

		@Test
		public void changeTableName() {

			val altered = Table.builder()
					.tableName("HOGE")
					.build();

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new ChangeTableName("HOGE")));
		}

		@Test
		public void changeTableJpName() {

			val altered = Table.builder()
					.tableJpName("ほげ")
					.build();

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new ChangeTableJpName("ほげ")));
		}

		@Test
		public void addColumn() {

			val newColumn = new ColumnDesign("id3", "COL3", "列3", Column.Type.varchar(1), Dummy.COMMENT, 2);
			val newColumns = Table.baseColumns();
			newColumns.add(newColumn);

			val altered = Table.builder()
					.columns(newColumns)
					.build();

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new AddColumn(newColumn)));
		}

		@Test
		public void changeColumnName() {

			val base = Table.baseColumns().get(0);
			val newColumn1 = new ColumnDesign(
					base.getId(),
					"COL1-NEW",
					base.getJpName(),
					base.getType(),
					base.getComment(),
					base.getDispOrder());
			val newColumns = Arrays.asList(newColumn1,  Table.baseColumns().get(1));

			val altered = Table.builder()
					.columns(newColumns)
					.build();

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new ChangeColumnName(base.getId(), newColumn1.getName())));
		}

		@Test
		public void changeColumnJpName() {

			val base = Table.baseColumns().get(0);
			val newColumn1 = new ColumnDesign(
					base.getId(),
					base.getName(),
					"新しい名前",
					base.getType(),
					base.getComment(),
					base.getDispOrder());
			val newColumns = Arrays.asList(newColumn1,  Table.baseColumns().get(1));

			val altered = Table.builder()
					.columns(newColumns)
					.build();

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new ChangeColumnJpName(base.getId(), newColumn1.getJpName())));
		}

		@Test
		public void changeColumnComment() {

			val base = Table.baseColumns().get(0);
			val newColumn1 = new ColumnDesign(
					base.getId(),
					base.getName(),
					base.getJpName(),
					base.getType(),
					"新しいコメント",
					base.getDispOrder());
			val newColumns = Arrays.asList(newColumn1,  Table.baseColumns().get(1));

			val altered = Table.builder()
					.columns(newColumns)
					.build();

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new ChangeColumnComment(base.getId(), newColumn1.getComment())));
		}

		@Test
		public void changeColumnType() {

			val base = Table.baseColumns().get(0);
			val newColumn1 = new ColumnDesign(
					base.getId(),
					base.getName(),
					base.getJpName(),
					Column.Type.varchar(100),
					base.getComment(),
					base.getDispOrder());
			val newColumns = Arrays.asList(newColumn1,  Table.baseColumns().get(1));

			val altered = Table.builder()
					.columns(newColumns)
					.build();

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new ChangeColumnType(base.getId(), Column.Type.varchar(100))));
		}

		@Test
		public void removeColumn() {

			val newColumns = Arrays.asList(Table.baseColumns().get(0));

			val altered = Table.builder()
					.columns(newColumns)
					.build();

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new RemoveColumn(Table.baseColumns().get(1).getId())));
		}

		@Test
		public void changePK() {

			val altered = Table.create(
					Table.BASE.getName(),
					Table.BASE.getJpName(),
					Table.BASE.getColumns(),
					Table.Constraints.create(
							Table.Constraints.createPK("id1", "id2"),
							Table.BASE.getConstraints().getUniqueConstraints().get(0)));

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new ChangePK(Arrays.asList("id1", "id2"), false)));
		}

		@Test
		public void removeUk() {
			val baseConstraints = Table.BASE.getConstraints();
			val altered = Table.create(
					Table.BASE.getName(),
					Table.BASE.getJpName(),
					Table.BASE.getColumns(),
					Table.Constraints.create(baseConstraints.getPrimaryKey()));

			val baseUk = baseConstraints.getUniqueConstraints().get(0);
			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					Alter.create(new ChangeUnique(baseUk.getSuffix(), baseUk.getColumnIds(), baseUk.isClustered(), true)));
		}

		@Test
		public void 色々変更した() {

			val altered = Table.create(
					new TableName("NEW_NAME"),
					"新しい名前",
					Table.BASE.getColumns().stream().skip(1).collect(toList()),
					Table.BASE.getConstraints());

			val contents = Arrays.asList(
					new ChangeTableName("NEW_NAME"),
					new ChangeTableJpName("新しい名前"),
					new RemoveColumn("id1"));
			val expectedAlter = Alter.create(contents);

			assertEquals(
					Alter.test(Table.BASE, altered).get(),
					expectedAlter);
		}
	}

	static void assertEquals(Alteration actual, Alteration expected) {

		assertThat(actual.getTableId()).isEqualTo(expected.getTableId());
		assertThat(actual.getFeatureId()).isEqualTo(expected.getFeatureId());
		assertThat(actual.getMetaData()).isEqualTo(expected.getMetaData());

		val expectedContents = new AlterationContent[expected.getContents().size()];
		expected.getContents().toArray(expectedContents);

		assertThat(actual.getContents()).containsExactly(expectedContents);
	}
}
