package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ScheduleDailyTableInkanRowTest {	
	
	@Test
	public void getters() {
		
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6")
				);
		
		ScheduleDailyTableInkanRow result = new ScheduleDailyTableInkanRow(NotUseAtr.USE, titleList);
		
		NtsAssert.invokeGetters(result);
	}
	
	@Test
	public void testGetInkanRowTitle_empty() {
		
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6")
				);
		
		ScheduleDailyTableInkanRow scheInkanRow = new ScheduleDailyTableInkanRow(NotUseAtr.NOT_USE, titleList);
		
		//actual
		List<ScheduleDailyTableInkanTitle> result = scheInkanRow.getInkanRowTitle();
		
		// Assert
		assertThat( result ).isEmpty();
	}
	
	@Test
	public void testGetInkanRowTitle_not_empty() {
		
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6")
				);
		
		ScheduleDailyTableInkanRow scheInkanRow = new ScheduleDailyTableInkanRow(NotUseAtr.USE, titleList);
		
		// Act
		List<ScheduleDailyTableInkanTitle> result = scheInkanRow.getInkanRowTitle();
		
		// Assert
		assertThat( result )
				.extracting( d -> d.v() )
				.containsExactly( "a1", "a2", "a3", "a4", "a5", "a6" );
	}
	
	@Test
	public void testClone() {
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6")
				);
		
		ScheduleDailyTableInkanRow scheInkanRow = new ScheduleDailyTableInkanRow(NotUseAtr.USE, titleList);
		
		// Act
		ScheduleDailyTableInkanRow result = scheInkanRow.clone();
		
		// Assert
		assertThat( result.getNotUseAtr() ).isEqualTo( NotUseAtr.USE );
		assertThat( result.getTitleList() )
				.extracting( d -> d.v() )
				.containsExactly( "a1", "a2", "a3", "a4", "a5", "a6" );
	}
}
