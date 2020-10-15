package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

public class PublicManagementShiftTableTest {

	@Test
	public void getters() {
		PublicManagementShiftTable data = new PublicManagementShiftTable(
				TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId"),
				GeneralDate.today(), Optional.empty());
		NtsAssert.invokeGetters(data);
	}

	/**
	 * 編集開始日 is empty
	 */
	@Test
	public void testCreatePublicManagementShiftTable() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId");
		GeneralDate endDatePublicationPeriod = GeneralDate.today();
		Optional<GeneralDate> optEditStartDate = Optional.empty();
		PublicManagementShiftTable data = PublicManagementShiftTable.createPublicManagementShiftTable(
					targetOrgIdenInfor, 
					endDatePublicationPeriod, 
					optEditStartDate);
		assertThat(data.getTargetOrgIdenInfor()).isEqualTo(targetOrgIdenInfor);
		assertThat(data.getEndDatePublicationPeriod()).isEqualTo(endDatePublicationPeriod);
		assertThat(data.getOptEditStartDate()).isEqualTo(optEditStartDate);
	}

	/**
	 * 編集開始日 is empty 編集開始日 after 公開期間の終了日
	 */
	@Test
	public void testCreatePublicManagementShiftTable_1() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId");
		GeneralDate endDatePublicationPeriod = GeneralDate.today();
		Optional<GeneralDate> optEditStartDate = Optional.of(GeneralDate.today().addDays(1));
		NtsAssert.systemError(() -> { PublicManagementShiftTable.createPublicManagementShiftTable(
					targetOrgIdenInfor,
					endDatePublicationPeriod,
					optEditStartDate);});
	}
	
	/**
	 * TargetOrganizationUnit.WORKPLACE
	 * 編集開始日 is empty 編集開始日 before 公開期間の終了日
	 */
	@Test
	public void testCreatePublicManagementShiftTable_2() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId");
		GeneralDate endDatePublicationPeriod = GeneralDate.today();
		Optional<GeneralDate> optEditStartDate = Optional.of(GeneralDate.today().addDays(-1));
		PublicManagementShiftTable data = PublicManagementShiftTable.createPublicManagementShiftTable(targetOrgIdenInfor, endDatePublicationPeriod,
				optEditStartDate);
		assertThat(data.getOptEditStartDate().isPresent()).isTrue();
		assertThat(data.getTargetOrgIdenInfor()).isEqualTo(targetOrgIdenInfor);
		assertThat(data.getEndDatePublicationPeriod()).isEqualTo(endDatePublicationPeriod);
		assertThat(data.getOptEditStartDate()).isEqualTo(optEditStartDate);
	}
	
	/**
	 * TargetOrganizationUnit.WORKPLACE_GROUP
	 * 編集開始日 is empty 編集開始日 before 公開期間の終了日
	 */
	@Test
	public void testCreatePublicManagementShiftTable_3() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		GeneralDate endDatePublicationPeriod = GeneralDate.today();
		Optional<GeneralDate> optEditStartDate = Optional.of(GeneralDate.today().addDays(-1));
		PublicManagementShiftTable data = PublicManagementShiftTable.createPublicManagementShiftTable(targetOrgIdenInfor, endDatePublicationPeriod,
				optEditStartDate);
		assertThat(data.getOptEditStartDate().isPresent()).isTrue();
		assertThat(data.getTargetOrgIdenInfor()).isEqualTo(targetOrgIdenInfor);
		assertThat(data.getEndDatePublicationPeriod()).isEqualTo(endDatePublicationPeriod);
		assertThat(data.getOptEditStartDate()).isEqualTo(optEditStartDate);
	}

}
