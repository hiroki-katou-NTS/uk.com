package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.i18n.I18NText;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;

@RunWith(JMockit.class)
public class DisplayInfoOrganizationTest {
	@Test
	public void getters() {
		DisplayInfoOrganization data = new DisplayInfoOrganization("designation", "code", "name", "displayName", "genericTerm");
		NtsAssert.invokeGetters(data);
	}
	
	@Test
	public void testCreateDisplayInforWorkplaceGroup() {
		WorkplaceGroupImport workplaceGroupImport = new WorkplaceGroupImport("workplaceGroupId", "workplaceGroupCode", "workplaceGroupName", 0);
		
		String getTextCom_WorkplaceGroup = "Com_WorkplaceGroup";
		new MockUp<I18NText>() {
			@Mock
			public String getText(String resourceId, String... params) {
				return "Com_WorkplaceGroup";
			}
		};
		DisplayInfoOrganization data = DisplayInfoOrganization.createDisplayInforWorkplaceGroup(workplaceGroupImport);
		assertThat(data.getCode()).isEqualTo(workplaceGroupImport.getWorkplaceGroupCode());
		assertThat(data.getName()).isEqualTo(workplaceGroupImport.getWorkplaceGroupName());
		assertThat(data.getDisplayName()).isEqualTo(workplaceGroupImport.getWorkplaceGroupName());
		assertThat(data.getGenericTerm()).isEqualTo(workplaceGroupImport.getWorkplaceGroupName());
		assertThat(data.getDesignation()).isEqualTo(getTextCom_WorkplaceGroup);
	}
	
	@Test
	public void testCreateWorkplaceDisplayInformation() {
		WorkplaceInfo workplaceInfo = new WorkplaceInfo("workplaceId", Optional.of("workplaceCd"),
				Optional.of("workplaceName"), Optional.of("outsideWkpCd"), Optional.of("wkpGenericName"),
				Optional.of("wkpDisplayName"), Optional.of("tierCd"));
		
		String getTextCom_Workplace = "Com_Workplace";
		new MockUp<I18NText>() {
			@Mock
			public String getText(String resourceId, String... params) {
				return "Com_Workplace";
			}
		};
		DisplayInfoOrganization data = DisplayInfoOrganization.createWorkplaceDisplayInformation(workplaceInfo);
		assertThat(data.getDesignation()).isEqualTo(getTextCom_Workplace);
		assertThat(data.getCode()).isEqualTo(workplaceInfo.getWorkplaceCd().get());
		assertThat(data.getName()).isEqualTo(workplaceInfo.getWorkplaceName().get());
		assertThat(data.getDisplayName()).isEqualTo(workplaceInfo.getWkpDisplayName().get());
		assertThat(data.getGenericTerm()).isEqualTo(workplaceInfo.getWkpGenericName().get());
	}

}
