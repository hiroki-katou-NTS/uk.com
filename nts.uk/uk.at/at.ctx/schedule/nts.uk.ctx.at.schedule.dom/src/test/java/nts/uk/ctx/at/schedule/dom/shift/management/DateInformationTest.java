package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation.Require;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
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
	 * 	require.祝日が存在するか(年月日) == false
	 *  if 対象組織.単位 != 職場
	 *  require.会社行事を取得する(年月日) is empty
	 *  require.会社の特定日設定を取得する(年月日) is empty
	 */
	@Test
	public void testCreate_1() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");
		GeneralDate today = GeneralDate.today();
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = false;
				
				require.findCompanyEventByPK(today);
				
				require.getComSpecByDate(today);
			}
		};
		
		DateInformation dateInformation = DateInformation.create(require, today, targetOrgIdenInfor);
		
		assertFalse(dateInformation.getOptWorkplaceEventName().isPresent());
		assertTrue(dateInformation.getListSpecDayNameWorkplace().isEmpty());
		
		assertFalse(dateInformation.isSpecificDay());
		assertFalse(dateInformation.getOptCompanyEventName().isPresent());
		assertTrue(dateInformation.getListSpecDayNameCompany().isEmpty());
		
		assertFalse(dateInformation.isHoliday());
		assertSame(today, dateInformation.getYmd());
		assertSame(today.dayOfWeekEnum(), dateInformation.getYmd().dayOfWeekEnum());
	}
	
	/**
	 * 	require.祝日が存在するか(年月日) == true
	 *  if 対象組織.単位 != 職場
	 *  require.会社行事を取得する(年月日) is empty
	 *  require.会社の特定日設定を取得する(年月日) is empty
	 */
	@Test
	public void testCreate_2() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");;
		GeneralDate today = GeneralDate.today();
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = true;
				
				require.findCompanyEventByPK(today);
				
				require.getComSpecByDate(today);
			}
		};
		
		DateInformation dateInformation = DateInformation.create(require, today, targetOrgIdenInfor);
		
		assertFalse(dateInformation.getOptWorkplaceEventName().isPresent());
		assertTrue(dateInformation.getListSpecDayNameWorkplace().isEmpty());
		
		assertFalse(dateInformation.isSpecificDay());
		assertFalse(dateInformation.getOptCompanyEventName().isPresent());
		assertTrue(dateInformation.getListSpecDayNameCompany().isEmpty());
		
		assertTrue(dateInformation.isHoliday());
		assertSame(today, dateInformation.getYmd());
		assertSame(today.dayOfWeekEnum(), dateInformation.getYmd().dayOfWeekEnum());
	}
	
	/**
	 * 
	 *  if 対象組織.単位 != 職場
	 *  require.会社行事を取得する(年月日) not empty
	 *  require.会社の特定日設定を取得する(年月日) not empty
	 *  require.特定日項目リストを取得する($全社特定日設定.特定日項目リスト) is empty
	 */
	@Test
	public void testCreate_3() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		GeneralDate today = GeneralDate.today();
		CompanyEvent companyEvent = DateInformationHelper.getCompanyEventDefault();
		List<CompanySpecificDateItem> listCompanySpecificDateItem = DateInformationHelper.getListDefaultByNumberItem(2);
		List<SpecificDateItemNo> listNo =listCompanySpecificDateItem.stream().map(c->c.getSpecificDateItemNo()).collect(Collectors.toList());
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = true;
				
				require.findCompanyEventByPK(today);
				result = Optional.of(companyEvent);
				
				require.getComSpecByDate(today);
				result = listCompanySpecificDateItem;
				
				require.getSpecifiDateByListCode(listNo);
			}
		};
		
		DateInformation dateInformation = DateInformation.create(require, today, targetOrgIdenInfor);
		
		assertTrue(dateInformation.isSpecificDay());
		assertSame(dateInformation.getOptCompanyEventName().get(),companyEvent.getEventName());
		assertTrue(dateInformation.getListSpecDayNameCompany().isEmpty());
		
		assertTrue(dateInformation.isHoliday());
		assertSame(today, dateInformation.getYmd());
		assertSame(today.dayOfWeekEnum(), dateInformation.getYmd().dayOfWeekEnum());
	}
	
	/**
	 *  if 対象組織.単位 != 職場
	 *  require.会社行事を取得する(年月日) not empty
	 *  require.会社の特定日設定を取得する(年月日) not empty
	 *  require.特定日項目リストを取得する($全社特定日設定.特定日項目リスト) not empty
	 */
	@Test
	public void testCreate_4() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		GeneralDate today = GeneralDate.today();
		CompanyEvent companyEvent = DateInformationHelper.getCompanyEventDefault();
		List<CompanySpecificDateItem> listCompanySpecificDateItem = DateInformationHelper.getListDefaultByNumberItem(2);
		List<SpecificDateItemNo> listNo =listCompanySpecificDateItem.stream().map(c->c.getSpecificDateItemNo()).collect(Collectors.toList());
		List<SpecificDateItem> listSpecificDateItem = DateInformationHelper.getListSpecificDateItemByNumberItem(2);
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = true;
				
				require.findCompanyEventByPK(today);
				result = Optional.of(companyEvent);
				
				require.getComSpecByDate(today);
				result = listCompanySpecificDateItem;
				
				require.getSpecifiDateByListCode(listNo);
				result = listSpecificDateItem;
			}
		};
		
		DateInformation dateInformation = DateInformation.create(require, today, targetOrgIdenInfor);
		
		assertTrue(dateInformation.isSpecificDay());
		assertSame(dateInformation.getOptCompanyEventName().get(),companyEvent.getEventName());
		assertFalse(dateInformation.getListSpecDayNameCompany().isEmpty());
		
		assertThat(dateInformation.getListSpecDayNameCompany())
		.extracting(d->d.v())
		.containsExactly(
				listSpecificDateItem.get(0).getSpecificName().v(),
				listSpecificDateItem.get(1).getSpecificName().v());
		
		assertTrue(dateInformation.isHoliday());
		assertSame(today, dateInformation.getYmd());
		assertSame(today.dayOfWeekEnum(), dateInformation.getYmd().dayOfWeekEnum());
	}

	/**
	 *  if 対象組織.単位 == 職場
	 *  require.職場行事を取得する(対象組織.職場ID, 年月日) is empty
	 */
	@Test
	public void testCreate_5() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");;
		GeneralDate today = GeneralDate.today();
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = true;
				
				require.findByPK(anyString, today);
				
			}
		};
		
		DateInformation dateInformation = DateInformation.create(require, today, targetOrgIdenInfor);
		
		assertFalse(dateInformation.isSpecificDay());
		assertFalse(dateInformation.getOptWorkplaceEventName().isPresent());
		assertTrue(dateInformation.getListSpecDayNameWorkplace().isEmpty());
		
		assertTrue(dateInformation.isHoliday());
		assertSame(today, dateInformation.getYmd());
		assertSame(today.dayOfWeekEnum(), dateInformation.getYmd().dayOfWeekEnum());
	}
	
	/**
	 *  if 対象組織.単位 == 職場
	 *  require.職場行事を取得する(対象組織.職場ID, 年月日) not empty
	 *  require.職場の特定日設定を取得する(対象組織.職場ID, 年月日) is empty
	 */
	@Test
	public void testCreate_6() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");;
		GeneralDate today = GeneralDate.today();
		WorkplaceEvent workplaceEvent = WorkplaceEvent.createFromJavaType("workplaceId", GeneralDate.today(), "eventName");
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = true;
				
				require.findByPK(anyString, today);
				result = Optional.of(workplaceEvent);
				
				require.getWorkplaceSpecByDate(anyString, today);
			}
		};
		
		DateInformation dateInformation = DateInformation.create(require, today, targetOrgIdenInfor);
		
		assertFalse(dateInformation.isSpecificDay());
		assertSame(dateInformation.getOptWorkplaceEventName().get(), workplaceEvent.getEventName());
		assertTrue(dateInformation.getListSpecDayNameWorkplace().isEmpty());
		
		assertTrue(dateInformation.isHoliday());
		assertSame(today, dateInformation.getYmd());
		assertSame(today.dayOfWeekEnum(), dateInformation.getYmd().dayOfWeekEnum());
	}
	
	/**
	 *  if 対象組織.単位 == 職場
	 *  require.職場行事を取得する(対象組織.職場ID, 年月日) not empty
	 *  require.職場の特定日設定を取得する(対象組織.職場ID, 年月日) not empty
	 *   require.特定日項目リストを取得する($職場特定日設定.特定日項目リスト)	is empty
	 */
	@Test
	public void testCreate_7() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");
		GeneralDate today = GeneralDate.today();
		WorkplaceEvent workplaceEvent = WorkplaceEvent.createFromJavaType("workplaceId", GeneralDate.today(), "eventName");
		List<WorkplaceSpecificDateItem> listWorkplaceSpecificDateItem =  DateInformationHelper.getListWorkplaceSpecificDateItemByNumber(2);
		List<SpecificDateItemNo> listSpecificDateItemNo = listWorkplaceSpecificDateItem.stream().map(c->c.getSpecificDateItemNo()).collect(Collectors.toList());
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = true;
				
				require.findByPK(anyString, today);
				result = Optional.of(workplaceEvent);
				
				require.getWorkplaceSpecByDate(anyString, today);
				result = listWorkplaceSpecificDateItem;
				
				require.getSpecifiDateByListCode(listSpecificDateItemNo);
			}
		};
		
		DateInformation dateInformation = DateInformation.create(require, today, targetOrgIdenInfor);
		
		assertTrue(dateInformation.isSpecificDay());
		assertTrue(dateInformation.getListSpecDayNameWorkplace().isEmpty());
		
		assertTrue(dateInformation.isHoliday());
		assertSame(today, dateInformation.getYmd());
		assertSame(today.dayOfWeekEnum(), dateInformation.getYmd().dayOfWeekEnum());
	}
	
	/**
	 *  if 対象組織.単位 == 職場
	 *  require.職場行事を取得する(対象組織.職場ID, 年月日) not empty
	 *  require.職場の特定日設定を取得する(対象組織.職場ID, 年月日) not empty
	 *   require.特定日項目リストを取得する($職場特定日設定.特定日項目リスト)	not empty
	 */
	@Test
	public void testCreate_8() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");;
		GeneralDate today = GeneralDate.today();
		WorkplaceEvent workplaceEvent = WorkplaceEvent.createFromJavaType("workplaceId", GeneralDate.today(), "eventName");
		List<WorkplaceSpecificDateItem> listWorkplaceSpecificDateItem =  DateInformationHelper.getListWorkplaceSpecificDateItemByNumber(2);
		List<SpecificDateItemNo> listSpecificDateItemNo = listWorkplaceSpecificDateItem.stream().map(c->c.getSpecificDateItemNo()).collect(Collectors.toList());
		List<SpecificDateItem> listSpecificDateItem = DateInformationHelper.getListSpecificDateItemByNumberItem(2);
		new Expectations() {
			{
				require.getHolidaysByDate(today);
				result = true;
				
				require.findByPK(anyString, today);
				result = Optional.of(workplaceEvent);
				
				require.getWorkplaceSpecByDate(anyString, today);
				result = listWorkplaceSpecificDateItem;
				
				require.getSpecifiDateByListCode(listSpecificDateItemNo);
				result = listSpecificDateItem;
			}
		};
		
		DateInformation dateInformation = DateInformation.create(require, today, targetOrgIdenInfor);
		
		assertTrue(dateInformation.isSpecificDay());
		assertFalse(dateInformation.getListSpecDayNameWorkplace().isEmpty());
		
		assertThat(dateInformation.getListSpecDayNameWorkplace())
		.extracting(d->d.v())
		.containsExactly(
				listSpecificDateItem.get(0).getSpecificName().v(),
				listSpecificDateItem.get(1).getSpecificName().v());
		
		assertTrue(dateInformation.isHoliday());
		assertSame(today, dateInformation.getYmd());
		assertSame(today.dayOfWeekEnum(), dateInformation.getYmd().dayOfWeekEnum());
	}
	

}
