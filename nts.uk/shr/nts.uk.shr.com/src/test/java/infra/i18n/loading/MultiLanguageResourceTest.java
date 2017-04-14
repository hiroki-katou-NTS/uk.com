package infra.i18n.loading;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.i18n.custom.ICompanyResourceBundle;
import nts.arc.i18n.custom.ISessionLocale;
import nts.arc.i18n.custom.ISystemResourceBundle;
import nts.uk.shr.infra.i18n.loading.MultiLanguageResource;

@RunWith(JMockit.class)
public class MultiLanguageResourceTest {
	@Injectable
	ISystemResourceBundle system;
	@Injectable
	ICompanyResourceBundle company;
	@Injectable
	EntityManager entityManager;
	@Injectable
	ISessionLocale sessionLocale;

	Map<String, String> companyItem = new HashMap<>();
	Map<String, String> systemItem = new HashMap<>();
	Map<String, String> systemMessage = new HashMap<>();
	@Tested
	MultiLanguageResource resource;

	@Before
	public void init() {
		companyItem.put("department", "dept");
		companyItem.put("code", "code");
		companyItem.put("employee", "code");

		systemItem.put("QPP005_1", "自動計算による変更");
		systemItem.put("QPP005_2", "手入力による変更");
		systemItem.put("QPP005_3", "勤怠システム連動");
		systemItem.put("QPP005_4", "外部データ（CSV）取込み値");

		systemMessage.put("Msg_74", "マスタ{0}が存在しません。");
		systemMessage.put("Msg_75", "マスタ{0}{#department}{#code}{#xxxx}が存在しません。");
		systemMessage.put("Msg_76", "マスタ{1}{0}{#department}{#code}{#xxxx}が存在しません。");

	}

	@Test
	public void testGetItemName() {
		new Expectations() {
			{
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;

				company.getResource();
				result = companyItem;

				system.getCodeNameResource(Locale.JAPANESE);
				result = systemItem;

			}
		};

		Optional<String> result = resource.getItemName("department");
		Assert.assertEquals(companyItem.get("department"), result.get());

	}

	@Test
	public void testGetItemName2() {
		new Expectations() {
			{
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;

				company.getResource();
				result = companyItem;

				system.getCodeNameResource(Locale.JAPANESE);
				result = systemItem;

			}
		};

		Optional<String> result = resource.getItemName("x");
		Assert.assertEquals(false, result.isPresent());
	}

	@Test
	public void testGetItemName3() {
		new Expectations() {
			{
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;
				company.getResource();
				result = companyItem;

				system.getCodeNameResource(Locale.JAPANESE);
				result = systemItem;

			}
		};

		Optional<String> result = resource.getItemName("QPP005_1");
		Assert.assertEquals(systemItem.get("QPP005_1"), result.get());
	}

	@Test
	public void testGetMessage() {
		new Expectations() {
			{
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;
				system.getMessageResource(Locale.JAPANESE);
				result = systemMessage;
			}
		};
		Optional<String> x = resource.getMessage("Msg_74");
		Assert.assertEquals(systemMessage.get("Msg_74"), x.get());
	}

	@Test
	public void testGetMessage2() {
		new Expectations() {
			{
				company.getResource();
				result = companyItem;
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;
				system.getMessageResource(Locale.JAPANESE);
				result = systemMessage;

			}
		};
		Optional<String> x = resource.getMessage("Msg_75");
		Assert.assertEquals("マスタ{0}deptcode{#xxxx}が存在しません。", x.get());
	}

	@Test
	public void testGetMessage3() {
		new Expectations() {
			{
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;
				system.getCodeNameResource(Locale.JAPANESE);
				result = systemItem;

				system.getMessageResource(Locale.JAPANESE);
				result = systemMessage;

			}
		};
		Optional<String> x = resource.getMessage("Msg_75", "name");
		Assert.assertEquals("マスタname{#department}{#code}{#xxxx}が存在しません。", x.get());
	}

	@Test
	public void testGetMessage4() {
		new Expectations() {
			{
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;
				company.getResource();
				result = companyItem;

				system.getCodeNameResource(Locale.JAPANESE);
				result = systemItem;

				system.getMessageResource(Locale.JAPANESE);
				result = systemMessage;

			}
		};
		Optional<String> x = resource.getMessage("Msg_75");
		Assert.assertEquals("マスタ{0}deptcode{#xxxx}が存在しません。", x.get());
	}

	@Test
	public void testGetMessage5() {
		new Expectations() {
			{
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;
				company.getResource();
				result = companyItem;

				system.getCodeNameResource(Locale.JAPANESE);
				result = systemItem;

				system.getMessageResource(Locale.JAPANESE);
				result = systemMessage;

			}
		};
		Optional<String> x = resource.getMessage("Msg_75", "name");
		Assert.assertEquals("マスタnamedeptcode{#xxxx}が存在しません。", x.get());
	}

	@Test
	public void testGetMessage6() {
		new Expectations() {
			{
				sessionLocale.getSessionLocale();
				result = Locale.JAPANESE;
				company.getResource();
				result = companyItem;

				system.getCodeNameResource(Locale.JAPANESE);
				result = systemItem;

				system.getMessageResource(Locale.JAPANESE);
				result = systemMessage;

			}
		};
		Optional<String> x = resource.getMessage("Msg_76", "name", "full");

		Assert.assertEquals("マスタfullnamedeptcode{#xxxx}が存在しません。", x.get());
	}
}
