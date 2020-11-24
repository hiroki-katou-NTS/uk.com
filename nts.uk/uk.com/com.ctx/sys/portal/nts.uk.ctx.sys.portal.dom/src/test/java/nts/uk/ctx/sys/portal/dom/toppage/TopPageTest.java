package nts.uk.ctx.sys.portal.dom.toppage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;

public class TopPageTest {
	@Mocked
	private static TopPageDto mockDto = TopPageDto.builder()
		.cid("cid")
		.topPageCode("topPageCode")
		.topPageName("topPageName")
		.layoutDisp(0)
		.build();
	
	
	@Test
	public void createFromMementoAndGetMemento() {
		//When
		ToppageNew domain = ToppageNew.createFromMemento(mockDto);
		//Then
		assertThat(domain.getCid()).isEqualTo(mockDto.getCid());
		assertThat(domain.getTopPageCode().v()).isEqualTo(mockDto.getTopPageCode());
		assertThat(domain.getTopPageName().v()).isEqualTo(mockDto.getTopPageName());
		assertThat(domain.getLayoutDisp().value).isEqualTo(mockDto.getLayoutDisp().intValue());
	}
	
	@Test
	public void testSetMemeto() {
		//Given
		TopPageDto dto = TopPageDto.builder().build();
		ToppageNew domain = ToppageNew.createFromMemento(mockDto);
		
		//When
		domain.setMemento(dto);
		
		//Then
		assertThat(domain.getCid()).isEqualTo(mockDto.getCid());
		assertThat(domain.getTopPageCode().v()).isEqualTo(mockDto.getTopPageCode());
		assertThat(domain.getTopPageName().v()).isEqualTo(mockDto.getTopPageName());
		assertThat(domain.getLayoutDisp().value).isEqualTo(mockDto.getLayoutDisp().intValue());
	}
	
	@Test
	public void getters() {
		//When
		ToppageNew domain = ToppageNew.createFromMemento(mockDto);
		// then
		NtsAssert.invokeGetters(domain);
	}
	
	@Test
	public void gettersNull() {
		//given
		TopPageDto mockDtoNull = TopPageDto.builder().build();
		//when
		ToppageNew domain = new ToppageNew();
		domain.getMemento(mockDtoNull);
		//then
		NtsAssert.invokeGetters(domain);
	}
}
