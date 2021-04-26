package nts.uk.cnv.dom.td.schema.snapshot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnComment;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

public class CreateTableSnapshotTest {

	@Injectable
	private CreateShapshot.Require require;

	@Injectable
	private CreateShapshot createShapShot;
	
	@Test
	//適用するalter1 snapshot1
	public void oneToOne() {
		List<Alteration> alters = Arrays.asList(Healper.Alteration.BASE);
		List<TableSnapshot> snap = Arrays.asList(Healper.TableSnapshot.BASE);
		new Expectations() {
			{
				require.getTablesLatest();
				result = snap;
			}
		};
		
		val result = CreateTableSnapshot.create(require,alters);
		
		TableDesign expect = Healper.TableSnapshot.BASE.apply(alters).get();
		assertThat(result.get(0)).isEqualTo(expect);
	}
	
	@Test
	//適用するalter1 snapshot2
	public void oneAlterManySnap(){
		List<Alteration> alters = Arrays.asList(Healper.Alteration.BASE);
		TableDesign  target = new TableSnapshot("TARGET", Healper.Table.builder().tableId("table").build());

		TableSnapshot tSnapshot = new TableSnapshot(Healper.Dummy.TABLE_SNAPSHOT_ID,target);
		TableSnapshot nottSnapshot = new TableSnapshot("NOT_TARGET",  Healper.Table.builder().tableId("NT").build()); 
		List<TableSnapshot> snap = Arrays.asList(tSnapshot, nottSnapshot);
		
		new Expectations() {
			{
				require.getTablesLatest();
				result = snap;
			}
		};
		
		val result = CreateTableSnapshot.create(require,alters);
		
		val applyAlter = tSnapshot.apply(alters).get();
		val notApplyAlter = nottSnapshot;
		result.forEach(snapshot ->{
			TableDesign expect;
			if(snapshot.getId() == "table") {
				expect = applyAlter;
			}
			else if(snapshot.getId() == "NT") {
				expect = notApplyAlter;
			}
			else {
				throw new RuntimeException("予想外です。");
			}
			assertThat(snapshot).isEqualTo(expect);
		});
	}
	
	
	@Test
	//適用するalter2 snapshot1
	public void manyToOne(){
		List<Alteration> alters = Arrays.asList(
				Healper.Alteration.BASE,
				Healper.Alteration.builder().changeContens(new ChangeColumnComment(
						Healper.Dummy.COLUMN_ID,
						"こめんと"))
					.build()
				);
		List<TableSnapshot> snap = Arrays.asList(Healper.TableSnapshot.BASE);
		new Expectations() {
			{
				require.getTablesLatest();
				result = snap;
			}
		};
		
		val result = CreateTableSnapshot.create(require,alters);
		
		TableDesign expect = Healper.TableSnapshot.BASE.apply(alters).get();
		assertThat(result.stream().findFirst().get()).isEqualTo(expect);
	}
	
	@Test
	//適用しないalter1 snapshot1
	public void notApply(){
		List<Alteration> alters = Arrays.asList(Healper.Alteration.builder().changeAlterId("アルターID").build());
		List<TableSnapshot> snap = Arrays.asList(Healper.TableSnapshot.BASE);
		new Expectations() {{
				require.getTablesLatest();
				result = snap;
		}};
		
		val result = CreateTableSnapshot.create(require,alters);
		
		TableDesign expect = Healper.TableSnapshot.BASE.apply(alters).get();
		assertThat(result.stream().findFirst().get()).isEqualTo(expect);
	}

}
