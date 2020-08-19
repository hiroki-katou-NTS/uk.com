package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.GetBentoMenuEachLocationSerrvice;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.*;

import static nts.arc.time.GeneralDate.today;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class GetBentoMenuEachLoactionServiceTest {

	@Injectable
	GetBentoMenuEachLocationSerrvice.Require require;

	@Test
	public void getBentoMenu_ByCid() {
		String cid = "cid";
		String historyId = "historyID";
		OperationDistinction operationDistinction = OperationDistinction.BY_LOCATION;
		Optional<WorkLocationCode> workLocationCode = Optional.empty();

		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),false,true,
				Optional.of(new WorkLocationCode("WORK01")));

		val bentoReservationSet = new BentoReservationSetting(
				null, OperationDistinction.BY_COMPANY, null,null);

		BentoMenu bentoMenu = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		new Expectations() {{
			require.getBentoReservationSet(cid);
			result = bentoReservationSet;

			require.getBentoMenu(cid,historyId,Optional.empty());
			result = bentoMenu;
		}};

		val menu = GetBentoMenuEachLocationSerrvice.getMenu(require,cid,historyId,operationDistinction,workLocationCode);
		assertThat(menu.size()).isEqualTo(1);

	}

	@Test
	public void getBentoMenu_ByWrkCd() {
		String cid = "cid";
		String historyId = "historyID";
		OperationDistinction operationDistinction = OperationDistinction.BY_LOCATION;
		Optional<WorkLocationCode> workLocationCode = Optional.empty();

		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),false,true,
				Optional.of(new WorkLocationCode("WORK01")));

		val bentoReservationSet = new BentoReservationSetting(
				null, OperationDistinction.BY_LOCATION, null,null);

		BentoMenu bentoMenu = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		new Expectations() {{
			require.getBentoReservationSet(cid);
			result = bentoReservationSet;

			require.getBentoMenu(cid,historyId,workLocationCode);
			result = bentoMenu;
		}};
		val menu = GetBentoMenuEachLocationSerrvice.getMenu(require,cid,historyId,operationDistinction,workLocationCode);
		assertThat(menu.size()).isEqualTo(1);

	}

	@Test
	public void getBentoMenu_ByWrkCd_1() {
		String cid = "cid";
		String historyId = "historyID";
		OperationDistinction operationDistinction = OperationDistinction.BY_LOCATION;
		Optional<WorkLocationCode> workLocationCode = Optional.of(new WorkLocationCode("WORK01"));

		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),false,true,
				workLocationCode);

		val bentoReservationSet = new BentoReservationSetting(
				null, OperationDistinction.BY_LOCATION, null,null);

		BentoMenu bentoMenu = new BentoMenu(
				historyId,
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		new Expectations() {{
			require.getBentoReservationSet(cid);
			result = bentoReservationSet;

			require.getBentoMenu(cid,historyId,workLocationCode);
			result = bentoMenu;
		}};

		val menu = GetBentoMenuEachLocationSerrvice.getMenu(require,cid,historyId,operationDistinction,workLocationCode);
		assertThat(menu.size()).isEqualTo(1);

	}
}
