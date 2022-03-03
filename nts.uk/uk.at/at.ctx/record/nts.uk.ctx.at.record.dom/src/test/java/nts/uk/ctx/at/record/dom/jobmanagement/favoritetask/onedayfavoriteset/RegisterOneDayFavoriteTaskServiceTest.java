package nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteDisplayOrder;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskName;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.RegisterOneDayFavoriteTaskService.Require;

/**
 * 
 * @author tutt
 *
 */
@RunWith(JMockit.class)
public class RegisterOneDayFavoriteTaskServiceTest {

	@Injectable
	private Require require;

	@Test
	public void test1() {

		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();

		displayOrders.add(new FavoriteDisplayOrder("favId1", 1));
		displayOrders.add(new FavoriteDisplayOrder("favId2", 2));
		Optional<OneDayFavoriteTaskDisplayOrder> optdisplayOrder = Optional
				.of(new OneDayFavoriteTaskDisplayOrder("employeeId", displayOrders));

		new Expectations() {
			{
				require.get(anyString);
				result = optdisplayOrder;

				require.insert((OneDayFavoriteSet) any);
				times = 1;

				require.insert((OneDayFavoriteTaskDisplayOrder) any);
				times = 0;

				require.update((OneDayFavoriteTaskDisplayOrder) any);
				times = 1;
			}
		};

		AtomTask result = RegisterOneDayFavoriteTaskService.add(require, "employeeId", new FavoriteTaskName("name"),
				new ArrayList<>());

		result.run();

	}

	@Test
	public void test2() {

		AtomTask result = RegisterOneDayFavoriteTaskService.add(require, "employeeId", new FavoriteTaskName("name"),
				new ArrayList<>());

		new Expectations() {
			{
				require.insert((OneDayFavoriteSet) any);
				times = 1;

				require.insert((OneDayFavoriteTaskDisplayOrder) any);
				times = 1;

				require.update((OneDayFavoriteTaskDisplayOrder) any);
				times = 0;

			}
		};

		result.run();

	}
}
