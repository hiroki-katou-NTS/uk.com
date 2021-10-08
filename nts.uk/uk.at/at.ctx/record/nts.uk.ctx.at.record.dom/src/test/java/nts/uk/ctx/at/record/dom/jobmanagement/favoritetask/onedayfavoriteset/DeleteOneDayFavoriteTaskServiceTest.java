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
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteDisplayOrder;

/**
 * 
 * @author tutt
 *
 */
@RunWith(JMockit.class)
public class DeleteOneDayFavoriteTaskServiceTest {
	
	@Injectable
	private DeleteOneDayFavoriteTaskService.Require require;

	String employeeId = "dummy";
	String favoriteId = "dummy1";
	
	@Test
	public void test1() {
		List<FavoriteDisplayOrder> orders = new ArrayList<>();
		orders.add(new FavoriteDisplayOrder("dummy3", 3));
		orders.add(new FavoriteDisplayOrder("dummy2", 2));
		orders.add(new FavoriteDisplayOrder("dummy1", 1));

		// favoriteTaskDisplayOrder is present
		// displayOrders is not empty
		OneDayFavoriteTaskDisplayOrder object = new OneDayFavoriteTaskDisplayOrder(employeeId, orders);

		new Expectations() {
			{
				require.get(employeeId);
				result = Optional.of(object);
				
				require.delete(employeeId, favoriteId);
			}
		};
		DeleteOneDayFavoriteTaskService service = new DeleteOneDayFavoriteTaskService();
		AtomTask result = service.create(require, employeeId, favoriteId);
		NtsAssert.atomTask(() -> result, any -> require.update(object));
	}

	@Test
	public void test2() {
		
		List<FavoriteDisplayOrder> orders = new ArrayList<>();
		orders.add(new FavoriteDisplayOrder("dummy1", 1));
	
		// favoriteTaskDisplayOrder is present
		// displayOrders is empty
		OneDayFavoriteTaskDisplayOrder object1 = new OneDayFavoriteTaskDisplayOrder(employeeId, orders);

		new Expectations() {
			{
				require.get(employeeId);
				result = Optional.of(object1);
				
				require.delete(employeeId, favoriteId);
			}
		};
		DeleteOneDayFavoriteTaskService service = new DeleteOneDayFavoriteTaskService();
		AtomTask result = service.create(require, employeeId, favoriteId);
		NtsAssert.atomTask(() -> result, any -> require.delete(employeeId));
	}
	
	@Test
	public void test3() {

		// favoriteTaskDisplayOrder is not present
		// displayOrders is not empty

		new Expectations() {
			{
				require.get(employeeId);
				result = Optional.empty();
			}
		};
		DeleteOneDayFavoriteTaskService service = new DeleteOneDayFavoriteTaskService();
		AtomTask result = service.create(require, employeeId, favoriteId);
		NtsAssert.atomTask(() -> result, any -> require.delete(employeeId, favoriteId));
	}
}
