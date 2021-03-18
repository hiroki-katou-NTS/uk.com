package nts.uk.ctx.office.dom.favorite;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify.Require;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforImport;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class FavoriteSpecifyTest {
	
	@Injectable
	private Require require;


	@Test
	public void getters() {
		// when
		FavoriteSpecify domain = FavoriteSpecifyTestHelper.mockFavoriteSpecify(0, Collections.emptyList());

		// then 
		NtsAssert.invokeGetters(domain);
	}

	/**
	 * Vì team chúng tôi thiết kế domain theo cơ chế get/set memento, thế nên trong
	 * domain sẽ có 3 hàm phát sinh (createFromMemento, getMemento, setMemento)
	 * Chính vì thế, để đảm bảo coverage, chúng tôi phải test cả 3 hàm này.
	 * 
	 * get /set mementoメカニズムに従ってドメインを設計しているため、ドメインには3つの生成関数（createFromMemento、getMemento、setMemento）があります。
	 * 0カバレッジのために、3つの機能すべてをテストする必要があります。
	 */
	@Test
	public void setMemento() {
		// given
		FavoriteSpecifyDto nullDto = new FavoriteSpecifyDto();
		FavoriteSpecify domain = FavoriteSpecifyTestHelper.mockFavoriteSpecify(0, Collections.emptyList());

		// when
		domain.setMemento(nullDto);

		// then
		NtsAssert.invokeGetters(domain);
	}

	//targetSelection = AFFILIATION_WORKPLACE
	@Test
	public void passingTargetInfoNameTest1() {
		//given
		FavoriteSpecify domain = FavoriteSpecifyTestHelper.mockFavoriteSpecify(1, Collections.emptyList()); // 1 is AFFILIATION_WORKPLACE

		//when
		val list = domain.passingTargetInfoName(require);
		
		//then
		assertThat(list).isEmpty();
	}

	//targetSelection = WORKPLACE
	//職場Mapが NOT Empty
	@Test
	public void passingTargetInfoNameTest2() {
		//given
		FavoriteSpecify domain = FavoriteSpecifyTestHelper.mockFavoriteSpecify(0, Arrays.asList("string")); //0 is WORKPLACE

		Map<String, WorkplaceInforImport> resultMap = FavoriteSpecifyTestHelper.mockRequireGetWrkspDispName();

		new Expectations() {
			{
				require.getWrkspDispName(Arrays.asList("string"), (GeneralDate) any);
				result = resultMap;
			}
		};
		
		//when
		val list = domain.passingTargetInfoName(require);
		
		//then
		assertThat(list).isNotEmpty();
	}
	
	//targetSelection = WORKPLACE
	//職場Mapが Empty
	@Test
	public void passingTargetInfoNameTest3() {
		//given
		FavoriteSpecify domain = FavoriteSpecifyTestHelper.mockFavoriteSpecify(0, Arrays.asList("string")); //0 is WORKPLACE

		Map<String, WorkplaceInforImport> resultMap = new HashMap<>();

		new Expectations() {
			{
				require.getWrkspDispName(Arrays.asList("string"), (GeneralDate) any);
				result = resultMap;
			}
		};
		
		//when
		val list = domain.passingTargetInfoName(require);
		
		//then
		assertThat(list).isEmpty();
	}
}
