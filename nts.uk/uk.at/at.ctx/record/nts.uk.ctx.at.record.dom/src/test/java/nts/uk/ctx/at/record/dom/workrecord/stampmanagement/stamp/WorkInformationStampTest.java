/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

/**
 * @author laitv
 *
 */
@RunWith(JMockit.class)
public class WorkInformationStampTest {
	
	@Injectable
	private SupportCardRepository supportCardRepo;
	
	@Injectable
	private EmpInfoTerminalRepository empInfoTerminalRepo;
	
	@Test
	public void getters() {
		WorkInformationStamp workInformationStamp = WorkInformationStampHelper.getStampDefault();
		NtsAssert.invokeGetters(workInformationStamp);
	}
	
	
	// ============================================================================
	// case workplaceID.isPresent() = true && cardNumberSupport.isPresent() = true
	@Test
	public void testFucGetWorkInformation1() {
		String cid = "cid";//dummy
		WorkInformationStamp workInformationStamp = WorkInformationStampHelper.getStampDefault();
		WorkInformationTemporary rs= workInformationStamp.getWorkInformation(supportCardRepo, empInfoTerminalRepo, cid); 
		assertThat(rs.getWorkplaceID().get().toString()).isEqualTo(workInformationStamp.getWorkplaceID().get().toString());
		assertThat(rs.getWorkLocationCD().get().toString()).isEqualTo(workInformationStamp.getWorkLocationCD().get().toString());
	}
	
	// case workplaceID.isPresent() = false && cardNumberSupport.isPresent() = false,  workLocationCD.isPresent()  = false
	@Test
	public void testFucGetWorkInformation2() {
		String cid = "cid";//dummy
		WorkInformationStamp workInformationStamp = new WorkInformationStamp(
				Optional.empty(), 
				Optional.of(new EmpInfoTerminalCode("emCD")), 
				Optional.empty(), 
				Optional.empty());
		WorkInformationTemporary workInformationTemporary = new WorkInformationTemporary(Optional.of("workplaceID"), Optional.of(new WorkLocationCD("wkCD")));
		
		new MockUp<WorkInformationStamp>() {
			@Mock
			public WorkInformationTemporary getWorkInformationWithEmploymentInfoTerminal(EmpInfoTerminalRepository empInfoTerminalRepo) {
				return workInformationTemporary;
			}
			
			@Mock
			public void correctWorkplaceIdWithSupportCardInformation(SupportCardRepository supportCardRepo,WorkInformationTemporary workInformationTemporary, String cid) {}
		};
		
		WorkInformationTemporary rs= workInformationStamp.getWorkInformation(supportCardRepo, empInfoTerminalRepo, cid); 
		assertThat(rs.getWorkplaceID().get().toString()).isEqualTo(workInformationTemporary.getWorkplaceID().get().toString());
		assertThat(rs.getWorkLocationCD().get().toString()).isEqualTo(workInformationTemporary.getWorkLocationCD().get().toString());
	}
	
	// case workplaceID.isPresent() = false && cardNumberSupport.isPresent() = false,  workLocationCD.isPresent()  = true
	@Test
	public void testFucGetWorkInformation3() {
		String cid = "cid";//dummy
		WorkInformationStamp workInformationStamp = new WorkInformationStamp(
				Optional.empty(), 
				Optional.of(new EmpInfoTerminalCode("emCD")), 
				Optional.of(new WorkLocationCD("workLocationCD")), 
				Optional.empty());
		WorkInformationTemporary workInformationTemporary = new WorkInformationTemporary(Optional.of("workplaceID"), Optional.of(new WorkLocationCD("wkCD")));
		
		new MockUp<WorkInformationStamp>() {
			@Mock
			public WorkInformationTemporary getWorkInformationWithEmploymentInfoTerminal(EmpInfoTerminalRepository empInfoTerminalRepo) {
				return workInformationTemporary;
			}
			
			@Mock
			public void correctWorkplaceIdWithSupportCardInformation(SupportCardRepository supportCardRepo,WorkInformationTemporary workInformationTemporary, String cid) {}
		};
		
		WorkInformationTemporary rs = workInformationStamp.getWorkInformation(supportCardRepo, empInfoTerminalRepo, cid); 
		assertThat(rs.getWorkplaceID().get().toString()).isEqualTo(workInformationTemporary.getWorkplaceID().get().toString());
		assertThat(rs.getWorkLocationCD().get().toString()).isEqualTo(workInformationTemporary.getWorkLocationCD().get().toString());
	}

	// case workplaceID.isPresent() = true && cardNumberSupport.isPresent() = false, workLocationCD.isPresent() = true, workplaceID.isPresent() = true
	@Test
	public void testFucGetWorkInformation4() {
		String cid = "cid";// dummy
		WorkInformationStamp workInformationStamp = new WorkInformationStamp(
				Optional.of("workPlaceId"), 
				Optional.of(new EmpInfoTerminalCode("emCD")), 
				Optional.of(new WorkLocationCD("workLocationCD")), 
				Optional.empty());
		WorkInformationTemporary workInformationTemporary = new WorkInformationTemporary(Optional.of("workPlaceId"),
				Optional.of(new WorkLocationCD("workLocationCD")));

		new MockUp<WorkInformationStamp>() {
			@Mock
			public WorkInformationTemporary getWorkInformationWithEmploymentInfoTerminal(
					EmpInfoTerminalRepository empInfoTerminalRepo) {
				return workInformationTemporary;
			}

			@Mock
			public void correctWorkplaceIdWithSupportCardInformation(SupportCardRepository supportCardRepo,
					WorkInformationTemporary workInformationTemporary, String cid) {
			}
		};

		WorkInformationTemporary rs = workInformationStamp.getWorkInformation(supportCardRepo, empInfoTerminalRepo,
				cid);
		assertThat(rs.getWorkplaceID().get().toString())
				.isEqualTo(workInformationTemporary.getWorkplaceID().get().toString());
		assertThat(rs.getWorkLocationCD().get().toString())
				.isEqualTo(workInformationTemporary.getWorkLocationCD().get().toString());
	}
	
	// case workplaceID.isPresent() = true && cardNumberSupport.isPresent() =false, 
	// workLocationCD.isPresent() = true
	// EmpInfoTerminalCode.isPresent() = false
	@Test
	public void testFucGetWorkInformation5() {
		String cid = "cid";// dummy
		WorkInformationStamp workInformationStamp = new WorkInformationStamp(
				Optional.of("workPlaceId"), 
				Optional.empty(), 
				Optional.of(new WorkLocationCD("workLocationCD")), 
				Optional.empty());
		
		WorkInformationTemporary workInformationTemporary = new WorkInformationTemporary(Optional.empty(), Optional.empty());
		
		new MockUp<WorkInformationStamp>() {
			@Mock
			public WorkInformationTemporary getWorkInformationWithEmploymentInfoTerminal(
					EmpInfoTerminalRepository empInfoTerminalRepo) {
				return workInformationTemporary;
			}

			@Mock
			public void correctWorkplaceIdWithSupportCardInformation(SupportCardRepository supportCardRepo,
					WorkInformationTemporary workInformationTemporary, String cid) {
			}
		};
		
		WorkInformationTemporary rs = workInformationStamp.getWorkInformation(supportCardRepo, empInfoTerminalRepo, cid);
		assertThat(rs.getWorkplaceID().get().toString()).isEqualTo(workInformationStamp.getWorkplaceID().get().toString());
		assertThat(rs.getWorkLocationCD().get().toString()).isEqualTo(workInformationStamp.getWorkLocationCD().get().toString());
	}
	
	
	// ============================================================================
	
	// test func getWorkInformationWithEmploymentInfoTerminal
	// case empInfoTerCode.isPresent() = false
	@Test
	public void testFucGetWorkInformationWithEmploymentInfoTerminal1() {
		WorkInformationStamp workInformationStamp = new WorkInformationStamp(
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty());
		WorkInformationTemporary rs = workInformationStamp.getWorkInformationWithEmploymentInfoTerminal(empInfoTerminalRepo);
		assertThat(rs.getWorkplaceID()).isEqualTo(Optional.empty());
		assertThat(rs.getWorkLocationCD()).isEqualTo(Optional.empty());
	}
	
	// case empInfoTerCode.isPresent() = true , empInfoTerminal = Optional.empty
	@Test
	public void testFucGetWorkInformationWithEmploymentInfoTerminal2() {
		WorkInformationStamp workInformationStamp = WorkInformationStampHelper.getStampDefault();
		WorkInformationTemporary rs = workInformationStamp.getWorkInformationWithEmploymentInfoTerminal(empInfoTerminalRepo);
		assertThat(rs.getWorkplaceID()).isEqualTo(Optional.empty());
		assertThat(rs.getWorkLocationCD()).isEqualTo(Optional.empty());
	}
	
	// case empInfoTerCode.isPresent() = true , 
	// empInfoTerminal != Optional.empty, empInfoTerminal.get().getCreateStampInfo() == null
	@Test
	public void testFucGetWorkInformationWithEmploymentInfoTerminal3() {
		WorkInformationStamp workInformationStamp = WorkInformationStampHelper.getStampDefault();
		Optional<EmpInfoTerminal> empInfoTerminal = Optional.of(EmpInfoTerminalHelper.getEmpInfoTerminalDefault2());
		new Expectations() {
			{
				empInfoTerminalRepo.getEmpInfoTerminal(new EmpInfoTerminalCode("emCD"));
				result = empInfoTerminal;
			}
		};
		WorkInformationTemporary rs = workInformationStamp
				.getWorkInformationWithEmploymentInfoTerminal(empInfoTerminalRepo);
		assertThat(rs.getWorkplaceID()).isEqualTo(Optional.empty());
		assertThat(rs.getWorkLocationCD()).isEqualTo(Optional.empty());
	}
	
	
	// case empInfoTerCode.isPresent() = true , empInfoTerminal != Optional.empty
	@Test
	public void testFucGetWorkInformationWithEmploymentInfoTerminal4() {
		WorkInformationStamp workInformationStamp = WorkInformationStampHelper.getStampDefault();
		Optional<EmpInfoTerminal> empInfoTerminal = Optional.of(EmpInfoTerminalHelper.getEmpInfoTerminalDefault());
		new Expectations() {
			{
				empInfoTerminalRepo.getEmpInfoTerminal(new EmpInfoTerminalCode("emCD"));
				result = empInfoTerminal;
			}
		};
		WorkInformationTemporary rs = workInformationStamp.getWorkInformationWithEmploymentInfoTerminal(empInfoTerminalRepo);
		assertThat(rs.getWorkplaceID().get().toString()).isEqualTo("WID");
		assertThat(rs.getWorkLocationCD().get().toString()).isEqualTo("WCD");
	}
	// =======================================
	
	
	// test func correctWorkplaceIdWithSupportCardInformation
	// case cardNumberSupport.isPresent() = false
	@Test
	public void testFuccorrectWorkplaceIdWithSupportCardInformation1() {
		WorkInformationStamp workInformationStamp = new WorkInformationStamp(
				Optional.empty(), 
				Optional.of(new EmpInfoTerminalCode("emCD")), 
				Optional.empty(), 
				Optional.empty());
		WorkInformationTemporary workTempo = new WorkInformationTemporary(Optional.of("workplaceIDTempo"), Optional.empty());
		String cid = "cid";
		workInformationStamp.correctWorkplaceIdWithSupportCardInformation(supportCardRepo, workTempo, cid);
		assertThat(workTempo.getWorkplaceID().get().equals("workplaceIDTempo"));
	}
	
	// case cardNumberSupport.isPresent() = true
	// Optional<SupportCard> supportCard = empty
	@Test
	public void testFuccorrectWorkplaceIdWithSupportCardInformation2() {
		WorkInformationStamp workInformationStamp = WorkInformationStampHelper.getStampDefault();
		WorkInformationTemporary workTempo = new WorkInformationTemporary(Optional.of("workplaceIDTempo"), Optional.empty());
		String cid = "cid";
		
		//Optional<SupportCard> supportCard = Optional.of(new SupportCard("cid", new SupportCardNumber(9999), "workplaceId"));
		new Expectations() {
			{
				supportCardRepo.get("cid", "9999");
				//result = supportCard;
			}
		};
		workInformationStamp.correctWorkplaceIdWithSupportCardInformation(supportCardRepo, workTempo, cid);
		assertThat(workTempo.getWorkplaceID().get().equals("workplaceIDTempo"));
	}
	
	// case cardNumberSupport.isPresent() = true
	// Optional<SupportCard> supportCard = empty
	@Test
	public void testFuccorrectWorkplaceIdWithSupportCardInformation3() {
		WorkInformationStamp workInformationStamp = WorkInformationStampHelper.getStampDefault();
		WorkInformationTemporary workTempo = new WorkInformationTemporary(Optional.of("workplaceIDTempo"), Optional.empty());
		String cid = "cid";

		Optional<SupportCard> supportCard = Optional.of(new SupportCard("cid", new SupportCardNumber("9999"), "workplaceId"));
		new Expectations() {
			{
				supportCardRepo.get("cid", "9999");
				result = supportCard;
			}
		};
		workInformationStamp.correctWorkplaceIdWithSupportCardInformation(supportCardRepo, workTempo, cid);
		assertThat(workTempo.getWorkplaceID().get().toString()).isEqualTo("workplaceId");
	}

}



