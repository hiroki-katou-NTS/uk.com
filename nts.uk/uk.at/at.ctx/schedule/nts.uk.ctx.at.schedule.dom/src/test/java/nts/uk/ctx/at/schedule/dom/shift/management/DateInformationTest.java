package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation.Require;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
@RunWith(JMockit.class)
public class DateInformationTest {
	
	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		DateInformation dateInformation = DateInformationHelper.DUMMY;
		NtsAssert.invokeGetters(dateInformation);
	}
	/**
	 * 	require.祝日を取得する(年月日) == empty
	 *  if 対象組織.単位 != 職場
	 *  require.会社行事を取得する(年月日) is empty
	 *  require.会社の特定日設定を取得する(年月日) is empty
	 */
	@Test
	public void testCreate_1() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");
		val today = GeneralDate.today();
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				
				require.findCompanyEventByPK(today);
				
				require.getComSpecByDate(today);
			}
		};
		
		DateInformation dateInfo = DateInformation.create(require, today, targetOrg);
		
		assertThat(dateInfo.getOptWorkplaceEventName()).isEmpty();
		assertThat(dateInfo.getListSpecDayNameWorkplace()).isEmpty();
		
		assertThat(dateInfo.isSpecificDay()).isFalse();
		assertThat(dateInfo.getOptCompanyEventName()).isEmpty();
		assertThat(dateInfo.getListSpecDayNameCompany()).isEmpty();
		
		assertThat(today).isEqualTo(dateInfo.getYmd());
		assertThat(today.dayOfWeekEnum()).isEqualTo(dateInfo.getYmd().dayOfWeekEnum());
		assertThat(dateInfo.isHoliday()).isFalse();
		/** ver2 */
		assertThat(dateInfo.isHoliday()).isFalse();
		assertThat(dateInfo.getHolidayName()).isEmpty();
	}
	
	/**
	 * 	require.祝日が存在するか(年月日) != empty
	 *  if 対象組織.単位 != 職場
	 *  require.会社行事を取得する(年月日) is empty
	 *  require.会社の特定日設定を取得する(年月日) is empty
	 */
	@Test
	public void testCreate_2() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");;
		val today = GeneralDate.today();
		val pubHoliday =  PublicHoliday.createFromJavaType("cid", today, "holidayName");
		
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = Optional.of(pubHoliday);
				
				require.findCompanyEventByPK(today);
				
				require.getComSpecByDate(today);
			}
		};
		
		DateInformation dateInfo = DateInformation.create(require, today, targetOrg);
		
		assertThat(dateInfo.getOptWorkplaceEventName()).isEmpty();
		assertThat(dateInfo.getListSpecDayNameWorkplace()).isEmpty();;
		
		assertThat(dateInfo.isSpecificDay()).isFalse();
		assertThat(dateInfo.getOptCompanyEventName()).isEmpty();
		assertThat(dateInfo.getListSpecDayNameCompany()).isEmpty();
		
		assertThat(today).isEqualTo(dateInfo.getYmd());
		assertThat(today.dayOfWeekEnum()).isEqualTo(dateInfo.getYmd().dayOfWeekEnum());
		assertThat(dateInfo.isHoliday()).isTrue();
		/** ver2 */
		assertThat(dateInfo.getHolidayName().isPresent()).isTrue();
		assertThat(dateInfo.getHolidayName().get().v()).isEqualTo("holidayName");
		
	}
	
	/**
	 *  require.祝日を取得する(年月日) not empty
	 *  if 対象組織.単位 != 職場
	 *  require.会社行事を取得する(年月日) not empty
	 *  require.会社の特定日設定を取得する(年月日) not empty
	 *  require.特定日項目リストを取得する($全社特定日設定.特定日項目リスト) is empty
	 */
	@Test
	public void testCreate_3() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		val today = GeneralDate.today();
		val companyEvent = DateInformationHelper.getCompanyEventDefault();
		val copanySpecificDateItems = DateInformationHelper.getListDefaultByNumberItem(2);
		val specDateItemNos = copanySpecificDateItems.stream().map(c->c.getSpecificDateItemNo()).collect(Collectors.toList());
		val pubHoliday =  PublicHoliday.createFromJavaType("cid", today, "holidayName");
		
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = Optional.of(pubHoliday);
				
				require.findCompanyEventByPK(today);
				result = Optional.of(companyEvent);
				
				require.getComSpecByDate(today);
				result = copanySpecificDateItems;
				
				require.getSpecifiDateByListCode(specDateItemNos);
			}
		};
		
		DateInformation dateInfo = DateInformation.create(require, today, targetOrg);
		
		assertThat(dateInfo.isSpecificDay()).isTrue();
		assertThat(dateInfo.getOptCompanyEventName().get()).isEqualTo(companyEvent.getEventName());
		assertThat(dateInfo.getListSpecDayNameCompany()).isEmpty();;
		
		assertThat(today).isEqualTo(dateInfo.getYmd());
		assertThat(today.dayOfWeekEnum()).isEqualTo(dateInfo.getYmd().dayOfWeekEnum());
		assertThat(dateInfo.isHoliday()).isTrue();
		/** ver2 */
		assertThat(dateInfo.getHolidayName().isPresent()).isTrue();
		assertThat(dateInfo.getHolidayName().get().v()).isEqualTo("holidayName");
	}
	
	/**
	 *  require.祝日を取得する(年月日) not empty
	 *  if 対象組織.単位 != 職場
	 *  require.会社行事を取得する(年月日) not empty
	 *  require.会社の特定日設定を取得する(年月日) not empty
	 *  require.特定日項目リストを取得する($全社特定日設定.特定日項目リスト) not empty
	 */
	@Test
	public void testCreate_4() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		val today = GeneralDate.today();
		val companyEvent = DateInformationHelper.getCompanyEventDefault();
		val companyDateItems = DateInformationHelper.getListDefaultByNumberItem(2);
		val itemNos =companyDateItems.stream().map(c->c.getSpecificDateItemNo()).collect(Collectors.toList());
		val dateItems = DateInformationHelper.getListSpecificDateItemByNumberItem(2);
		val pubHoliday =  PublicHoliday.createFromJavaType("cid", today, "holidayName");
		
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = Optional.of(pubHoliday);
				
				require.findCompanyEventByPK(today);
				result = Optional.of(companyEvent);
				
				require.getComSpecByDate(today);
				result = companyDateItems;
				
				require.getSpecifiDateByListCode(itemNos);
				result = dateItems;
			}
		};
		
		DateInformation dateInfo = DateInformation.create(require, today, targetOrg);
		
		assertThat(dateInfo.isSpecificDay()).isTrue();
		assertThat(dateInfo.getOptCompanyEventName().get()).isEqualTo(companyEvent.getEventName());
		assertThat(dateInfo.getListSpecDayNameCompany().isEmpty()).isFalse();
		
		assertThat(dateInfo.getListSpecDayNameCompany())
		.extracting(d->d.v())
		.containsExactly(
				dateItems.get(0).getSpecificName().v(),
				dateItems.get(1).getSpecificName().v());
		
		
		assertThat(today).isEqualTo(dateInfo.getYmd());
		assertThat(today.dayOfWeekEnum()).isEqualTo(dateInfo.getYmd().dayOfWeekEnum());
		assertThat(dateInfo.isHoliday()).isTrue();
		/** ver2 */
		assertThat(dateInfo.getHolidayName().isPresent()).isTrue();
		assertThat(dateInfo.getHolidayName().get().v()).isEqualTo("holidayName");
		
	}

	/**
	 *  require.祝日を取得する(年月日) not empty
	 *  if 対象組織.単位 == 職場
	 *  require.職場行事を取得する(対象組織.職場ID, 年月日) is empty
	 */
	@Test
	public void testCreate_5() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");;
		val today = GeneralDate.today();
		val pubHoliday =  PublicHoliday.createFromJavaType("cid", today, "holidayName");
		
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = Optional.of(pubHoliday);
				
				require.findByPK(anyString, today);
				
			}
		};
		
		DateInformation dateInfo = DateInformation.create(require, today, targetOrg);
		
		assertThat(dateInfo.isSpecificDay()).isFalse();
		assertThat(dateInfo.getOptWorkplaceEventName()).isEmpty();
		assertThat(dateInfo.getListSpecDayNameWorkplace()).isEmpty();
		
		assertThat(today).isEqualTo(dateInfo.getYmd());
		assertThat(today.dayOfWeekEnum()).isEqualTo(dateInfo.getYmd().dayOfWeekEnum());
		assertThat(dateInfo.isHoliday()).isTrue();
		/** ver2 */
		assertThat(dateInfo.getHolidayName().isPresent()).isTrue();
		assertThat(dateInfo.getHolidayName().get().v()).isEqualTo("holidayName");
	}
	
	/**
	 *  require.祝日を取得する(年月日) not empty
	 *  if 対象組織.単位 == 職場
	 *  require.職場行事を取得する(対象組織.職場ID, 年月日) not empty
	 *  require.職場の特定日設定を取得する(対象組織.職場ID, 年月日) is empty
	 */
	@Test
	public void testCreate_6() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");;
		val today = GeneralDate.today();
		val workplaceEvent = WorkplaceEvent.createFromJavaType("workplaceId", GeneralDate.today(), "eventName");
		val pubHoliday =  PublicHoliday.createFromJavaType("cid", today, "holidayName");
		
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = Optional.of(pubHoliday);
				
				require.findByPK(anyString, today);
				result = Optional.of(workplaceEvent);
				
				require.getWorkplaceSpecByDate(anyString, today);
			}
		};
		
		DateInformation dateInfo = DateInformation.create(require, today, targetOrg);
		
		assertThat(dateInfo.isSpecificDay()).isFalse();
		assertThat(dateInfo.getOptWorkplaceEventName().get()).isEqualTo(workplaceEvent.getEventName());
		assertThat(dateInfo.getListSpecDayNameWorkplace()).isEmpty();
		
		assertThat(dateInfo.isHoliday()).isTrue();
		assertThat(dateInfo.getHolidayName().isPresent()).isTrue();
		assertThat(dateInfo.getHolidayName().get().v()).isEqualTo("holidayName");
		assertThat(today).isEqualTo(dateInfo.getYmd());
		assertThat(today.dayOfWeekEnum()).isEqualTo(dateInfo.getYmd().dayOfWeekEnum());
	}
	
	/**
	 *  require.祝日を取得する(年月日) not empty
	 *  if 対象組織.単位 == 職場
	 *  require.職場行事を取得する(対象組織.職場ID, 年月日) not empty
	 *  require.職場の特定日設定を取得する(対象組織.職場ID, 年月日) not empty
	 *   require.特定日項目リストを取得する($職場特定日設定.特定日項目リスト)	is empty
	 */
	@Test
	public void testCreate_7() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");
		val today = GeneralDate.today();
		val workplaceEvent = WorkplaceEvent.createFromJavaType("workplaceId", GeneralDate.today(), "eventName");
		val wkpSpecificDateItems =  DateInformationHelper.getListWorkplaceSpecificDateItemByNumber(2);
		val dateItemNos = wkpSpecificDateItems.stream().map(c->c.getSpecificDateItemNo()).collect(Collectors.toList());
		val pubHoliday =  PublicHoliday.createFromJavaType("cid", today, "holidayName");
		
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = Optional.of(pubHoliday);
				
				require.findByPK(anyString, today);
				result = Optional.of(workplaceEvent);
				
				require.getWorkplaceSpecByDate(anyString, today);
				result = wkpSpecificDateItems;
				
				require.getSpecifiDateByListCode(dateItemNos);
			}
		};
		
		DateInformation dateInfo = DateInformation.create(require, today, targetOrg);
		
		assertThat(dateInfo.isSpecificDay()).isTrue();
		assertThat(dateInfo.getListSpecDayNameWorkplace()).isEmpty();
		
		assertThat(dateInfo.isHoliday()).isTrue();
		assertThat(dateInfo.getHolidayName().isPresent()).isTrue();
		assertThat(dateInfo.getHolidayName().get().v()).isEqualTo("holidayName");
		assertThat(today).isEqualTo(dateInfo.getYmd());
		assertThat(today.dayOfWeekEnum()).isEqualTo(dateInfo.getYmd().dayOfWeekEnum());
	}
	
	/**
	 *  if 対象組織.単位 == 職場
	 *  require.職場行事を取得する(対象組織.職場ID, 年月日) not empty
	 *  require.職場の特定日設定を取得する(対象組織.職場ID, 年月日) not empty
	 *   require.特定日項目リストを取得する($職場特定日設定.特定日項目リスト)	not empty
	 */
	@Test
	public void testCreate_8() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");;
		val today = GeneralDate.today();
		val workplaceEvent = WorkplaceEvent.createFromJavaType("workplaceId", GeneralDate.today(), "eventName");
		val wkpSpecificDateItems =  DateInformationHelper.getListWorkplaceSpecificDateItemByNumber(2);
		val dateItemNos = wkpSpecificDateItems.stream().map(c->c.getSpecificDateItemNo()).collect(Collectors.toList());
		val dateItems = DateInformationHelper.getListSpecificDateItemByNumberItem(2);
		val pubHoliday =  PublicHoliday.createFromJavaType("cid", today, "holidayName");
		
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = Optional.of(pubHoliday);
				
				require.findByPK(anyString, today);
				result = Optional.of(workplaceEvent);
				
				require.getWorkplaceSpecByDate(anyString, today);
				result = wkpSpecificDateItems;
				
				require.getSpecifiDateByListCode(dateItemNos);
				result = dateItems;
			}
		};
		
		DateInformation dateInfo = DateInformation.create(require, today, targetOrg);
		
		assertThat(dateInfo.isSpecificDay()).isTrue();
		assertThat(dateInfo.getListSpecDayNameWorkplace().isEmpty()).isFalse();
		
		assertThat(dateInfo.getListSpecDayNameWorkplace())
		.extracting(d->d.v())
		.containsExactly(
				dateItems.get(0).getSpecificName().v(),
				dateItems.get(1).getSpecificName().v());
		
		assertThat(dateInfo.isHoliday()).isTrue();
		assertThat(dateInfo.getHolidayName().isPresent()).isTrue();
		assertThat(dateInfo.getHolidayName().get().v()).isEqualTo("holidayName");
		assertThat(today).isEqualTo(dateInfo.getYmd());
		assertThat(today.dayOfWeekEnum()).isEqualTo(dateInfo.getYmd().dayOfWeekEnum());
	}
}
