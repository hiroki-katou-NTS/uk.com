package nts.uk.cnv.dom.td.alteration;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.Helper.Table;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

@RunWith(Enclosed.class)
public class SaveAlterationTest {
	
	public static class AlterTable {

		@Test
		public void エラー_他の人が同時に修正した(
				@Injectable SaveAlteration.RequireAlterTable require) {
			
			new Expectations() {{
				require.getSchemaSnapshotLatest();
				result = Optional.empty();
				
				require.getAlterationsOfTable(anyString, (DevelopmentProgress) any);
				result = Arrays.asList(Alter.create("alt-2"));
			}};
			
			AtomTask atomTask = SaveAlteration.alterTable(require, "feature", null, "alt-1", Table.BASE);
			
			assertThatThrownBy(() -> {
				atomTask.run();
			}).isInstanceOfSatisfying(BusinessException.class, e -> {
				assertThat(e.getMessage()).isEqualTo("他の人がこのテーブルを変更したため、処理が失敗しました。画面をリロードしてやり直してください。");
			});
		}
		
		@Test
		public void エラー_他のfeatureで変更されてる(
				@Injectable SaveAlteration.RequireAlterTable require) {
			
			new Expectations() {{
				require.getSchemaSnapshotLatest();
				result = Optional.empty();
				
				require.getAlterationsOfTable(anyString, (DevelopmentProgress) any);
				result = Arrays.asList(Alter.create("alt-1", "feature-X"));
			}};
			
			AtomTask atomTask = SaveAlteration.alterTable(require, "feature-Y", null, "alt-1", Table.BASE);
			
			assertThatThrownBy(() -> {
				atomTask.run();
			}).isInstanceOfSatisfying(BusinessException.class, e -> {
				assertThat(e.getMessage()).isEqualTo("他のFeatureに未検収のorutaがあるため、このテーブルを変更できません。");
			});
		}
		
	}
	
	public static class DropTable {
		
		@Test
		public void エラー_他の人が同時に修正した(
				@Injectable SaveAlteration.RequireAlterTable require) {
			
			new Expectations() {{
				require.getAlterationsOfTable(anyString, (DevelopmentProgress) any);
				result = Arrays.asList(Alter.create("alt-2"));
			}};
			
			AtomTask atomTask = SaveAlteration.dropTable(require, "feature", null, "alt-1", null);
			
			assertThatThrownBy(() -> {
				atomTask.run();
			}).isInstanceOfSatisfying(BusinessException.class, e -> {
				assertThat(e.getMessage()).isEqualTo("他の人がこのテーブルを変更したため、処理が失敗しました。画面をリロードしてやり直してください。");
			});
		}
		
		@Test
		public void エラー_他のfeatureで変更されてる(
				@Injectable SaveAlteration.RequireAlterTable require) {
			
			new Expectations() {{
				require.getAlterationsOfTable(anyString, (DevelopmentProgress) any);
				result = Arrays.asList(Alter.create("alt-1", "feature-X"));
			}};
			
			AtomTask atomTask = SaveAlteration.dropTable(require, "feature-Y", null, "alt-1", null);
			
			assertThatThrownBy(() -> {
				atomTask.run();
			}).isInstanceOfSatisfying(BusinessException.class, e -> {
				assertThat(e.getMessage()).isEqualTo("他のFeatureに未検収のorutaがあるため、このテーブルを変更できません。");
			});
		}
	}
	
	static class Alter {
		static Alteration create(String alterId) {
			return create(alterId, "feature");
		}
		
		static Alteration create(String alterId, String featureId) {
			return new Alteration(alterId, featureId, null, null, null, null);
		}
	}
}
