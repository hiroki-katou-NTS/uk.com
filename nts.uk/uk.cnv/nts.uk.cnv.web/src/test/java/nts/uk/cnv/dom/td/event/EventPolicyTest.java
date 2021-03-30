package nts.uk.cnv.dom.td.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.error.BusinessException;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.dom.td.event.accept.AcceptEvent;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEvent;
import nts.uk.cnv.dom.td.event.order.OrderEvent;

public class EventPolicyTest {

	static class Dummy {
		private static String featureId = "featurefeature";
		private static String targetTableId 	= "tttttttttt";
		private static AlterationSummary notOrderedAlter = new AlterationSummary("tgtAlterId_1", GeneralDateTime.now(), Dummy.targetTableId, DevelopmentStatus.NOT_ORDER, new AlterationMetaData("a", "a"), Dummy.featureId);
		private static AlterationSummary orderedAlter = new AlterationSummary("tgtAlterId_2", GeneralDateTime.now(), Dummy.targetTableId, DevelopmentStatus.ORDERED, new AlterationMetaData("a", "a"), Dummy.featureId);
		private static AlterationSummary deliveredAlter = new AlterationSummary("tgtAlterId_3", GeneralDateTime.now(), Dummy.targetTableId, DevelopmentStatus.DELIVERED, new AlterationMetaData("a", "a"), Dummy.featureId);
		private static AlterationSummary acceptedAlter = new AlterationSummary("tgtAlterId_4", GeneralDateTime.now(), Dummy.targetTableId, DevelopmentStatus.ACCEPTED, new AlterationMetaData("a", "a"), Dummy.featureId);
		private static List<AlterationSummary> targetAlters = Arrays.asList(notOrderedAlter, orderedAlter, deliveredAlter, acceptedAlter);
		private static EventId eventId = new EventId("eventevent");
		private static List<OrderEvent> orderEvents = Arrays.asList(new OrderEvent(eventId, null));
		private static List<DeliveryEvent> deliveryEvents = Arrays.asList(new DeliveryEvent(eventId, null));
		private static List<AcceptEvent> acceptEvents = Arrays.asList(new AcceptEvent(eventId, null));
	}

	@Test
	public void 不正な初期化() {
		
		assertThatThrownBy(() -> new EventPolicy(DevelopmentStatus.NOT_ORDER))
		.isInstanceOfSatisfying(BusinessException.class, e -> {
			assertThat(e.getMessage()).isEqualTo(DevelopmentStatus.first().strStatus + "のイベントは発行できません。");
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void 発注_正常(
			@Injectable EventPolicy.Require require) {

		EventPolicy ep = new EventPolicy(DevelopmentStatus.ORDERED);
		
		new Expectations() {{
			require.getOrderEventByAlter((List<String>)any);
			result = Collections.emptyList();
		}};
		
		List<EventId> result = NtsAssert.Invoke.privateMethod(ep, "getEventIds", 
				require, 
				Dummy.targetAlters);
		
		assertThat(result.size()).isEqualTo(0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void 発注_異常(
			@Injectable EventPolicy.Require require) {

		EventPolicy ep = new EventPolicy(DevelopmentStatus.ORDERED);
		
		new Expectations() {{
			require.getOrderEventByAlter((List<String>)any);
			result = Dummy.orderEvents;
		}};
		
		List<EventId> result = NtsAssert.Invoke.privateMethod(ep, "getEventIds", 
				require, 
				Dummy.targetAlters);

		assertThat(result.size()).isNotEqualTo(0);
		assertThat(result.get(0).getId()).isEqualTo(Dummy.eventId.getId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void 納品_正常(
			@Injectable EventPolicy.Require require) {

		EventPolicy ep = new EventPolicy(DevelopmentStatus.DELIVERED);
		
		new Expectations() {{
			require.getDeliveryEventByAlter((List<String>)any);
			result = Collections.emptyList();
		}};
		
		List<EventId> result = NtsAssert.Invoke.privateMethod(ep, "getEventIds", 
				require, 
				Dummy.targetAlters);
		
		assertThat(result.size()).isEqualTo(0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void 納品_異常(
			@Injectable EventPolicy.Require require) {

		EventPolicy ep = new EventPolicy(DevelopmentStatus.DELIVERED);
		
		new Expectations() {{
			require.getDeliveryEventByAlter((List<String>)any);
			result = Dummy.deliveryEvents;
		}};
		
		List<EventId> result = NtsAssert.Invoke.privateMethod(ep, "getEventIds", 
				require, 
				Dummy.targetAlters);

		assertThat(result.size()).isNotEqualTo(0);
		assertThat(result.get(0).getId()).isEqualTo(Dummy.eventId.getId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void 検収_正常(
			@Injectable EventPolicy.Require require) {

		EventPolicy ep = new EventPolicy(DevelopmentStatus.ACCEPTED);
		
		new Expectations() {{
			require.getAcceptEventByAlter((List<String>)any);
			result = Collections.emptyList();
		}};
		
		List<EventId> result = NtsAssert.Invoke.privateMethod(ep, "getEventIds", 
				require, 
				Dummy.targetAlters);
		
		assertThat(result.size()).isEqualTo(0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void 検収_異常(
			@Injectable EventPolicy.Require require) {

		EventPolicy ep = new EventPolicy(DevelopmentStatus.ACCEPTED);
		
		new Expectations() {{
			require.getAcceptEventByAlter((List<String>)any);
			result = Dummy.acceptEvents;
		}};
		
		List<EventId> result = NtsAssert.Invoke.privateMethod(ep, "getEventIds", 
				require, 
				Dummy.targetAlters);

		assertThat(result.size()).isNotEqualTo(0);
		assertThat(result.get(0).getId()).isEqualTo(Dummy.eventId.getId());
	}
}
