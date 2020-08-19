package nts.uk.ctx.at.record.dom.reservation.bento;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.loginuser.SelectedLanguage;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(JMockit.class)
public class BentoUpdateServiceTest {

	@Injectable
	BentoUpdateService.Require require;

	@Mocked
	AppContexts appContexts;

	/**
	 * Init Appcontext
	 */
	@Before
	public void init(){
		new Expectations() {{
			AppContexts.user();
			result = new LoginUserContext() {
				public boolean hasLoggedIn() {
					return false;
				}

				@Override
				public boolean isEmployee() {
					return false;
				}

				@Override
				public String userId() {
					return null;
				}

				@Override
				public String personId() {
					return null;
				}

				@Override
				public String contractCode() {
					return null;
				}

				@Override
				public String companyId() {
					return "CID";
				}

				@Override
				public String companyCode() {
					return null;
				}

				@Override
				public String employeeId() {
					return null;
				}

				@Override
				public String employeeCode() {
					return null;
				}

				@Override
				public LoginUserRoles roles() {
					return null;
				}

				@Override
				public SelectedLanguage language() {
					return null;
				}
			};
		}};
	}


	@Test
	public void register_1() {

		GeneralDate date = GeneralDate.max();
		Bento bento = new Bento(1,new BentoName("bentoName"),new BentoAmount(1),
				new BentoAmount(2), new BentoReservationUnitName("string"),true,true, Optional.empty());

		// Mock up
		new Expectations() {{
			require.getBentoMenu("CID",date);
			result = null;
		}};

		NtsAssert.atomTask(
				() -> BentoUpdateService.update(
						require,bento ),
				any -> require.register(any.get(),any.get())
		);
	}

}
