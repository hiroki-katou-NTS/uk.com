package nts.uk.cnv.dom.td.alteration;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import lombok.val;
import nts.arc.error.BusinessException;
import static nts.uk.cnv.dom.td.alteration.Helper.*;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;

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
			
			val newColumn = new ColumnDesign("id3", "COL3", "列3", Column.Type.varchar(1), Dummy.COMMENT, 2);
			val newColumns = Table.baseColumns();
			newColumns.add(newColumn);
			
			val altered = Table.builder()
					.columns(newColumns)
					.build();
			val expected = Alter.create(new AddColumn(newColumn));
			
			val actual = Alter.test(Table.BASE, altered).get();
			assertThat(actual.isSameAs(expected)).isTrue();
		}
	}


}
