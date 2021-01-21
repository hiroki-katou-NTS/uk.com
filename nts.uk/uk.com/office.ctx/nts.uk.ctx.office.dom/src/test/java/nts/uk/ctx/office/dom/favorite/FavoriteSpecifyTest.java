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

	public final FavoriteSpecifyDto mockDto = new FavoriteSpecifyDto("favoriteName", "creatorId", null, 0,
			Collections.emptyList(), 0);

	@Test
	public void getters() {
		// when
		FavoriteSpecify domain = FavoriteSpecify.createFromMemento(mockDto);

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
		FavoriteSpecify domain = FavoriteSpecify.createFromMemento(mockDto);

		// when
		domain.setMemento(nullDto);

		// then
		NtsAssert.invokeGetters(domain);
	}

	//targetSelection = AFFILIATION_WORKPLACE
	@Test
	public void passingTargetInfoNameTest1() {
		//given
		FavoriteSpecifyDto mockDto = new FavoriteSpecifyDto("favoriteName", "creatorId", null, 1, // 1 is AFFILIATION_WORKPLACE
				Collections.emptyList(), 0);
		FavoriteSpecify domain = FavoriteSpecify.createFromMemento(mockDto);

		//when
		val list = domain.passingTargetInfoName(require);
		
		//then
		assertThat(list).isEmpty();
	}

	//targetSelection = WORKPLACE
	@Test
	public void passingTargetInfoNameTest2() {
		//given
		FavoriteSpecifyDto mockDto = new FavoriteSpecifyDto("favoriteName", "creatorId", null, 0, //0 is WORKPLACE
				Arrays.asList("string"), 0);
		FavoriteSpecify domain = FavoriteSpecify.createFromMemento(mockDto);
		WorkplaceInforImport info = new WorkplaceInforImport(
				"workplaceId",
				"hierarchyCode",
				"workplaceCode",
				"workplaceName",
				"workplaceDisplayName",
				"workplaceGenericName",
				"workplaceExternalCode"
				);
		Map<String, WorkplaceInforImport> resultMap = new HashMap<>();
		resultMap.put("key",info);
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
}
