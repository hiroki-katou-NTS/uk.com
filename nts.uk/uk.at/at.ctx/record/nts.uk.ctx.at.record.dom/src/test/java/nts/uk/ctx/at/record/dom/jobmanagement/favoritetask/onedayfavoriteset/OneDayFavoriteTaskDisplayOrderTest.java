package nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteDisplayOrder;

/**
 * 
 * @author tutt
 *
 */
public class OneDayFavoriteTaskDisplayOrderTest {


	String employeeId = "employeeId";

	@Test
	public void test01() {

		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();
		displayOrders.add(new FavoriteDisplayOrder("favId", 1));

		NtsAssert.invokeGetters(new OneDayFavoriteTaskDisplayOrder(employeeId, displayOrders));
	}

	@Test
	public void testAdd() {
		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();
		displayOrders.add(new FavoriteDisplayOrder("favId", 1));

		OneDayFavoriteTaskDisplayOrder order = new OneDayFavoriteTaskDisplayOrder(employeeId, displayOrders);

		order.add("favId1");

		assertThat(order.getDisplayOrders().size()).isEqualTo(2);
	}

	@Test
	public void test02() {

		OneDayFavoriteTaskDisplayOrder addOrder = new OneDayFavoriteTaskDisplayOrder(employeeId, "favId1");

		assertThat(addOrder.getDisplayOrders().size()).isEqualTo(1);
	}
	
	@Test
	public void testDelete1() {

		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();
		displayOrders.add(new FavoriteDisplayOrder("favId", 1));
		
		OneDayFavoriteTaskDisplayOrder order = new OneDayFavoriteTaskDisplayOrder(employeeId, displayOrders);
		order.delete("favId");
		
		assertThat(order.getDisplayOrders().size()).isEqualTo(0);
	}
	
	@Test
	public void testDelete2() {

		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();
		displayOrders.add(new FavoriteDisplayOrder("favId1", 1));
		displayOrders.add(new FavoriteDisplayOrder("favId2", 2));
		displayOrders.add(new FavoriteDisplayOrder("favId3", 3));
		
		OneDayFavoriteTaskDisplayOrder order = new OneDayFavoriteTaskDisplayOrder("employeeId1", displayOrders);
		order.delete("favId2");
		
		assertThat(order.getDisplayOrders().size()).isEqualTo(2);
	}
	
	@Test
	public void testDelete3() {

		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();
		displayOrders.add(new FavoriteDisplayOrder("favId1", 1));
		displayOrders.add(new FavoriteDisplayOrder("favId2", 2));
		displayOrders.add(new FavoriteDisplayOrder("favId3", 3));
		
		OneDayFavoriteTaskDisplayOrder order = new OneDayFavoriteTaskDisplayOrder("employeeId2", displayOrders);
		order.delete("favId4");
		
		assertThat(order.getDisplayOrders().size()).isEqualTo(3);
	}
	
	@Test
	public void testChangeOrder1() {

		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();
		displayOrders.add(new FavoriteDisplayOrder("favId1", 1));
		displayOrders.add(new FavoriteDisplayOrder("favId2", 2));
		displayOrders.add(new FavoriteDisplayOrder("favId3", 3));
		displayOrders.add(new FavoriteDisplayOrder("favId4", 4));
		
		OneDayFavoriteTaskDisplayOrder order = new OneDayFavoriteTaskDisplayOrder("employeeId3", displayOrders);
		order.changeOrder("favId4", 1, 3);
		
		assertThat(order.getDisplayOrders().stream().filter(f -> f.getFavId() == "favId4").findAny().get().getOrder()).isEqualTo(3);
	}
	
	@Test
	public void testChangeOrder2() {

		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();
		displayOrders.add(new FavoriteDisplayOrder("favId1", 1));
		displayOrders.add(new FavoriteDisplayOrder("favId2", 2));
		displayOrders.add(new FavoriteDisplayOrder("favId3", 3));
		displayOrders.add(new FavoriteDisplayOrder("favId4", 4));
		
		OneDayFavoriteTaskDisplayOrder order = new OneDayFavoriteTaskDisplayOrder("employeeId4", displayOrders);
		order.changeOrder("favId4", 1, 1);
		
		assertThat(order.getDisplayOrders().stream().filter(f -> f.getFavId() == "favId4").findAny().get().getOrder()).isEqualTo(4);
	}
	
	@Test
	public void testChangeOrder3() {

		List<FavoriteDisplayOrder> displayOrders = new ArrayList<>();
		displayOrders.add(new FavoriteDisplayOrder("favId1", 1));
		displayOrders.add(new FavoriteDisplayOrder("favId2", 2));
		displayOrders.add(new FavoriteDisplayOrder("favId3", 3));
		displayOrders.add(new FavoriteDisplayOrder("favId4", 4));
		
		OneDayFavoriteTaskDisplayOrder order = new OneDayFavoriteTaskDisplayOrder("employeeId5", displayOrders);
		order.changeOrder("favId4", 3, 2);
		
		assertThat(order.getDisplayOrders().stream().filter(f -> f.getFavId() == "favId4").findAny().get().getOrder()).isEqualTo(2);
	}
}
