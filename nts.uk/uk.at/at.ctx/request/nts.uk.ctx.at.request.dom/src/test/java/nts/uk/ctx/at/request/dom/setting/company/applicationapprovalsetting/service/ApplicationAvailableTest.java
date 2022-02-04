package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;

@RunWith(JMockit.class)
public class ApplicationAvailableTest {

	@Injectable
	private ApplicationAvailable.Require require;

	private String cid = "cid";
	private String sid = "sid";
	private GeneralDate date = GeneralDate.today();
	private ApplicationTypeShare targetApp = EnumAdaptor.valueOf(1, ApplicationTypeShare.class);
	private ApprovalFunctionSet dataTest = new ApprovalFunctionSet(new ArrayList<>());
	private ApplicationUseSetting itemSuccess = ApplicationUseSetting.createNew(1, 1, "dummy");
	private ApplicationUseSetting itemFail = ApplicationUseSetting.createNew(0, 2, "dummy");
	private ApplicationUseSetting itemNotUser = ApplicationUseSetting.createNew(0, 1, "dummy");
	private ApplicationSetting appSetting = new ApplicationSetting(cid, null, new ArrayList<>(), new ArrayList<>(),
											new ArrayList<>(), null, new ArrayList<>(), null);

	// if $申請承認設定.isPresent()
	@Test
	public void test1() {

		new Expectations() {
			{
				require.findByCompanyId(anyString);
				result = Optional.empty();

				require.getApprovalFunctionSet(cid, sid, date, targetApp);
				result = null;
			}
		};

		boolean result = ApplicationAvailable.get(require, cid, sid, date, targetApp);

		assertThat(result).isFalse();
	}

	// end result = false
	@Test
	public void test2() {

		new Expectations() {
			{
				require.findByCompanyId(anyString);
				result = Optional.empty();

				require.getApprovalFunctionSet(cid, sid, date, targetApp);
				result = dataTest;
			}
		};

		boolean result = ApplicationAvailable.get(require, cid, sid, date, targetApp);

		assertThat(result).isFalse();
	}

	// end result = true
	// All item Success
	@Test
	public void test3() {

		dataTest.getAppUseSetLst().add(itemSuccess);
		dataTest.getAppUseSetLst().add(itemSuccess);

		new Expectations() {
			{
				require.findByCompanyId(anyString);
				result = Optional.empty();

				require.getApprovalFunctionSet(cid, sid, date, targetApp);
				result = dataTest;
			}
		};

		boolean result = ApplicationAvailable.get(require, cid, sid, date, targetApp);

		assertThat(result).isTrue();
	}

	// end result = true
	// All item Fail
	@Test
	public void test4() {

		dataTest.getAppUseSetLst().add(itemFail);
		dataTest.getAppUseSetLst().add(itemFail);

		new Expectations() {
			{
				require.findByCompanyId(anyString);
				result = Optional.empty();

				require.getApprovalFunctionSet(cid, sid, date, targetApp);
				result = dataTest;
			}
		};

		boolean result = ApplicationAvailable.get(require, cid, sid, date, targetApp);

		assertThat(result).isFalse();
	}

	// end result = true
	// 1 item Fail
	// 1 item Scuccess
	@Test
	public void test5() {

		dataTest.getAppUseSetLst().add(itemFail);
		dataTest.getAppUseSetLst().add(itemSuccess);

		new Expectations() {
			{
				require.findByCompanyId(anyString);
				result = Optional.empty();

				require.getApprovalFunctionSet(cid, sid, date, targetApp);
				result = dataTest;
			}
		};

		boolean result = ApplicationAvailable.get(require, cid, sid, date, targetApp);

		assertThat(result).isTrue();
	}

	// if $申請設定.isPresent()
	// end result = true
	// 1 item Fail
	// 1 item Scuccess
	@Test
	public void test6() {

		dataTest.getAppUseSetLst().add(itemFail);
		dataTest.getAppUseSetLst().add(itemSuccess);

		new Expectations() {
			{
				require.findByCompanyId(anyString);
				result = Optional.of(appSetting);

				require.getApprovalFunctionSet(cid, sid, date, targetApp);
				result = dataTest;
			}
		};

		boolean result = ApplicationAvailable.get(require, cid, sid, date, targetApp);

		assertThat(result).isTrue();
	}
	
	// if $申請設定.isPresent()
		// end result = true
		// 1 item Fail
		// 1 item Not user
		@Test
		public void test7() {

			dataTest.getAppUseSetLst().add(itemFail);
			dataTest.getAppUseSetLst().add(itemNotUser);

			new Expectations() {
				{
					require.findByCompanyId(anyString);
					result = Optional.of(appSetting);

					require.getApprovalFunctionSet(cid, sid, date, targetApp);
					result = dataTest;
				}
			};

			boolean result = ApplicationAvailable.get(require, cid, sid, date, targetApp);

			assertThat(result).isFalse();
		}

}
