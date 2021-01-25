package nts.uk.ctx.office.dom.favorite.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify.Require;
import nts.uk.ctx.office.dom.favorite.service.FavoriteInformationDomainService.FavoriteInformationRequire;

@RunWith(JMockit.class)
public class FavoriteInformationDomainServiceTest {
	@Injectable
	private FavoriteInformationRequire favoriteInfoRequire;
	
	@Injectable
	private Require require;
	
	@Mocked
	private List<FavoriteSpecify> resultMap = new ArrayList<>();
	
	@Mocked
	private FavoriteSpecify favoriteSpecify = FavoriteSpecificARTestHelper.mockFavoriteInfoAR();
	
	@Before
	public void beforeTest() {
		resultMap.add(favoriteSpecify);
	}
	
	@After
	public void afterTest() {
		resultMap.clear();
	}

	/**
	 * Test DS お気に入り情報を取得する
	 * WorkplaceId input is empty
	 */
	@Test
	public void test() {
		new Expectations() {
			{
				favoriteInfoRequire.getBySid("mock-sid");
				result = resultMap;
			}
		};
		FavoriteInformationDomainService ds = new FavoriteInformationDomainService();
		val res = ds.get(favoriteInfoRequire, "mock-sid");
		assertThat(res).isNotEmpty();
		assertThat(res.get(favoriteSpecify)).isNotNull();
	}
	
}
