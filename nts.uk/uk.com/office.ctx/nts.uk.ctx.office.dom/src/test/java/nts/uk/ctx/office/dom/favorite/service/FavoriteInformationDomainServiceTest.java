package nts.uk.ctx.office.dom.favorite.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
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
	private List<FavoriteSpecify> resultList = new ArrayList<>();
	
	@Mocked
	private FavoriteSpecify favoriteSpecify = FavoriteSpecifyDomainServiceTestHelper.mockFavoriteSpecify();
	
	@Before
	public void beforeTest() {
		resultList.add(favoriteSpecify);
	}
	
	@After
	public void afterTest() {
		resultList.clear();
	}

	/**
	 * Test DS お気に入り情報を取得する
	 * WorkplaceId input is empty
	 * $お気に入りListがある
	 */
	@Test
	public void test1() {
		new Expectations() {
			{
				favoriteInfoRequire.getBySid("mock-sid");
				result = resultList;
			}
		};
		FavoriteInformationDomainService ds = new FavoriteInformationDomainService();
		val res = ds.get(favoriteInfoRequire, "mock-sid");
		assertThat(res).isNotEmpty();
		assertThat(res.get(favoriteSpecify)).isNotNull();
	}
	
	/**
	 * Test DS お気に入り情報を取得する
	 * WorkplaceId input is empty
	 * $お気に入りListがない
	 */
	@Test
	public void test2() {
		new Expectations() {
			{
				favoriteInfoRequire.getBySid("mock-sid");
				result = Collections.emptyList();
			}
		};
		FavoriteInformationDomainService ds = new FavoriteInformationDomainService();
		val res = ds.get(favoriteInfoRequire, "mock-sid");
		assertThat(res).isEmpty();
		assertThat(res.get(favoriteSpecify)).isNull();
	}
	
}
