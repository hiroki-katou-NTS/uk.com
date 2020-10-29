package nts.uk.ctx.sys.portal.dom.generalsearch;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Mocked;
import nts.arc.time.GeneralDateTime;

public class GeneralSearchHistoryTest {

	@Mocked
	private static GeneralSearchHistoryDto mockDto = GeneralSearchHistoryDto.builder()
		.companyID("companyID")
		.contents("contents")
		.searchCategory(0)
		.searchDate(GeneralDateTime.now())
		.userID("userID")
		.build();
	
	@Test
	public void createFromMementoAndGetMemento() {
		//When
		GeneralSearchHistory domain = GeneralSearchHistory.createFromMemento(GeneralSearchHistoryTest.mockDto);
		
		//Then
		assertThat(domain.getCompanyID()).isEqualTo(GeneralSearchHistoryTest.mockDto.getCompanyID());
		assertThat(domain.getContents().v()).isEqualTo(GeneralSearchHistoryTest.mockDto.getContents());
		assertThat(domain.getSearchCategory().value).isEqualTo(GeneralSearchHistoryTest.mockDto.getSearchCategory());
		assertThat(domain.getSearchDate()).isEqualTo(GeneralSearchHistoryTest.mockDto.getSearchDate());
		assertThat(domain.getUserID()).isEqualTo(GeneralSearchHistoryTest.mockDto.getUserID());
	}
	
	@Test
	public void setMemento() {
		//Given
		GeneralSearchHistoryDto dto = GeneralSearchHistoryDto.builder().build();
		GeneralSearchHistory domain = GeneralSearchHistory.createFromMemento(GeneralSearchHistoryTest.mockDto);
		//When
		domain.setMemento(dto);
		
		//Then
		assertThat(domain.getCompanyID()).isEqualTo(GeneralSearchHistoryTest.mockDto.getCompanyID());
		assertThat(domain.getContents().v()).isEqualTo(GeneralSearchHistoryTest.mockDto.getContents());
		assertThat(domain.getSearchCategory().value).isEqualTo(GeneralSearchHistoryTest.mockDto.getSearchCategory());
		assertThat(domain.getSearchDate()).isEqualTo(GeneralSearchHistoryTest.mockDto.getSearchDate());
		assertThat(domain.getUserID()).isEqualTo(GeneralSearchHistoryTest.mockDto.getUserID());
	}
	
	@Test
	public void getMementoNull() {
		//Given
		GeneralSearchHistoryDto dtoNull = GeneralSearchHistoryDto.builder().build();
		
		//When
		GeneralSearchHistory domain = GeneralSearchHistory.createFromMemento(dtoNull);
		
		//Then
		assertThat(domain.getCompanyID()).isEqualTo(null);
		assertThat(domain.getContents().v()).isEqualTo("");
		assertThat(domain.getSearchCategory().value).isEqualTo(0);
		assertThat(domain.getSearchDate()).isEqualTo(null);
		assertThat(domain.getUserID()).isEqualTo(null);
	}
}
