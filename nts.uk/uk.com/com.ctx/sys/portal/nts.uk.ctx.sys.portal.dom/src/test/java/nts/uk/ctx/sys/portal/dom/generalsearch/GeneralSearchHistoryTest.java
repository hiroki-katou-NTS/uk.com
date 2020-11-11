package nts.uk.ctx.sys.portal.dom.generalsearch;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.sys.portal.dom.GeneralSearchHelper.GeneralSearchHistoryHelper;

public class GeneralSearchHistoryTest {

	private static GeneralSearchHistoryDto mockDto = GeneralSearchHistoryHelper.getMockDto();
	
	@Test
	public void getters() {
		//When
		GeneralSearchHistory domain = GeneralSearchHistory.createFromMemento(mockDto);
		//Then
		NtsAssert.invokeGetters(domain);
	}
	
	@Test
	public void setMemento() {
		//Given
		GeneralSearchHistoryDto dto = GeneralSearchHistoryDto.builder().build();
		GeneralSearchHistory domain = GeneralSearchHistory.createFromMemento(GeneralSearchHistoryTest.mockDto);
		//When
		domain.setMemento(dto);
		
		//Then
		NtsAssert.invokeGetters(domain);
	}
	
	@Test
	public void getMementoNull() {
		//Given
		GeneralSearchHistoryDto dtoNull = GeneralSearchHistoryDto.builder().build();
		
		//When
		GeneralSearchHistory domain = GeneralSearchHistory.createFromMemento(dtoNull);
		
		//Then
		NtsAssert.invokeGetters(domain);
	}
}
