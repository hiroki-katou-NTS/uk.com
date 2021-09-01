//package nts.uk.cnv.dom.service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import mockit.Expectations;
//import mockit.Injectable;
//import mockit.Tested;
//import mockit.Verifications;
//import mockit.integration.junit4.JMockit;
//import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
//import nts.uk.cnv.core.dom.conversionsql.Join;
//import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
//import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
//import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
//import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
//import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
//import nts.uk.cnv.core.dom.conversiontable.pattern.manager.AdditionalConversionCode;
//import nts.uk.cnv.core.dom.conversiontable.pattern.manager.ParentJoinPatternManager;
//import nts.uk.cnv.dom.conversiontable.ConversionTableTestHelper;
//
//@RunWith(JMockit.class)
//public class CreateConversionCodeServiceTest {
//	@Tested
//	CreateConversionCodeService target;
//
//	@Injectable
//	CreateConversionCodeService.Require require;
//
//	@Injectable
//	ParentJoinPatternManager manager;
//
//	@Test
//	public void test_methodCall() {
//		ConversionInfo info = new ConversionInfo(
//				DatabaseType.sqlserver,
//				"KINJIROU", "dbo", "UK", "dbo", "UK_CNV", "dbo", "000000000000",
//				ConversionCodeType.INSERT);
//		List<String> dummyCategories = Arrays.asList("", "2_PERSON", "3_WEBMENU");
//		List<String> dummyTables = Arrays.asList("TABLE_1", "TABLE_2");
//		List<ConversionRecord> dummyRecords = new ArrayList<>();
//		dummyRecords.add(new ConversionRecord("1_COMPANY", "TABLE_1", 1, "guidxxxxxxxxx", "レコードの説明", true));
//
//		Optional<ConversionTable> dummyConversionTable =
//				Optional.of(ConversionTableTestHelper.create_emptyDummy());
//
//		AdditionalConversionCode dummyAdditionalConversionCode = new AdditionalConversionCode("<dummyPreProcessingSql>","<dummyPostProcessingSql>", new HashMap<>());
//
//		new Expectations() {{
//			require.getCategoryPriorities();
//			result = dummyCategories;
//
//			require.getCategoryTables((String) any);
//			result = dummyTables;
//
//			require.getRecords((String) any, (String) any);
//			result = dummyRecords;
//
//			require.getConversionTable(info, (String) any, (String) any, 1, null, true);
//			result = dummyConversionTable;
//
//			manager.createAdditionalConversionCode(info, (String) any, dummyConversionTable.get(),
//					new Join(info.getTargetTable(""), JoinAtr.InnerJoin, new ArrayList<>()));
//			result = dummyAdditionalConversionCode;
//		}};
//
//		String result = target.create(require, info);
//
//		new Verifications() {{
//			require.getCategoryPriorities();
//			times = 1;
//			require.getCategoryTables(dummyCategories.get(0));
//			times = 1;
//			require.getRecords(dummyCategories.get(0), (String) any);
//			times = 2;
//			require.getConversionTable(info, dummyCategories.get(0), (String) any, 1, null, true);
//			times = 2;
//
//			require.getCategoryTables(dummyCategories.get(1));
//			times = 1;
//			require.getRecords(dummyCategories.get(1), (String) any);
//			times = 2;
//			require.getConversionTable(info, dummyCategories.get(1), (String) any, 1, null, true);
//			times = 2;
//
//			require.getCategoryTables(dummyCategories.get(2));
//			times = 1;
//			require.getRecords(dummyCategories.get(2), (String) any);
//			times = 2;
//			require.getConversionTable(info, dummyCategories.get(2), (String) any, 1, null, true);
//			times = 2;
//		}};
//
//		Assert.assertTrue(!result.isEmpty());
//	}
//
//	@Test
//	public void test_sqlServer_simple() {
//		ConversionInfo info = new ConversionInfo(
//				DatabaseType.sqlserver,
//				"KINJIROU", "dbo", "UK", "dbo", "UK_CNV", "dbo", "000000000000",
//				ConversionCodeType.INSERT);
//		List<ConversionRecord> dummyRecords = new ArrayList<>();
//		dummyRecords.add(new ConversionRecord("1_COMPANY", "TABLE_1", 1, "guidxxxxxxxxx", "レコードの説明", true));
//
//		new Expectations() {{
//			require.getCategoryPriorities();
//			result = Arrays.asList("1_COMPANY");
//			require.getCategoryTables("1_COMPANY");
//			result = Arrays.asList("TABLE_1");
//			require.getRecords("1_COMPANY", "TABLE_1");
//			result = dummyRecords;
//			result = Optional.of(ConversionTableTestHelper.create_companyDummy(info));
//		}};
//
//		String result = target.create(require, info);
//
//		Assert.assertTrue(!result.isEmpty());
//	}
//
//}
