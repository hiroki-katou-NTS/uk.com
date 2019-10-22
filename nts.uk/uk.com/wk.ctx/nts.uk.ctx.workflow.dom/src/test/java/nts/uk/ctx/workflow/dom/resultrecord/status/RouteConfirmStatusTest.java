package nts.uk.ctx.workflow.dom.resultrecord.status;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import lombok.val;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.resultrecord.AppFrameConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppFrameInstance;
import nts.uk.ctx.workflow.dom.resultrecord.AppPhaseConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppPhaseInstance;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstance;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.resultrecord.ApprovalActionByEmp;
import nts.uk.ctx.workflow.dom.service.resultrecord.ApproverEmpState;
import nts.uk.ctx.workflow.dom.service.resultrecord.ReleaseDivision;

public class RouteConfirmStatusTest {

	@Test
	public void singlePhase_0() {
		// not approved
		val status = RouteConfirmStatus.create(
				confirm(phase(Collections.emptyList())),
				instance(phase(ApprovalForm.EVERYONE_APPROVED, instanceFrames("A"))));

		assertStatus(
				status.getStatusFor("A", new ArrayList<>()),
				ApproverEmpState.PHASE_DURING,
				ReleaseDivision.NOT_RELEASE,
				ApprovalActionByEmp.APPROVAL_REQUIRE);
	}
	
	@Test
	public void singlePhase_1() {
		// approver A: approved
		val status = RouteConfirmStatus.create(
				confirm(phase(confirmFrames("A"))),
				instance(phase(ApprovalForm.EVERYONE_APPROVED, instanceFrames("A"))));

		assertStatus(
				status.getStatusFor("A", new ArrayList<>()),
				ApproverEmpState.COMPLETE,
				ReleaseDivision.RELEASE,
				ApprovalActionByEmp.APPROVALED);
	}
	
	@Test
	public void singlePhase_2() {
		// approver A and B:
		//   A approved
		//   B not approved
		val status = RouteConfirmStatus.create(
				confirm(phase(confirmFrames("A"))),
				instance(phase(ApprovalForm.EVERYONE_APPROVED, instanceFrames("A", "B"))));

		assertStatus(
				status.getStatusFor("A", new ArrayList<>()),
				ApproverEmpState.PHASE_DURING,
				ReleaseDivision.RELEASE,
				ApprovalActionByEmp.APPROVALED);
		
		assertStatus(
				status.getStatusFor("B", new ArrayList<>()),
				ApproverEmpState.PHASE_DURING,
				ReleaseDivision.NOT_RELEASE,
				ApprovalActionByEmp.APPROVAL_REQUIRE);
	}
	
	private static void assertStatus(
			RouteConfirmStatusForOneApprover result,
			ApproverEmpState state,
			ReleaseDivision release,
			ApprovalActionByEmp action) {

		assertThat(result.getState(), is(state));
		assertThat(result.getApprovalStatus().getReleaseAtr(), is(release));
		assertThat(result.getApprovalStatus().getApprovalAction(), is(action));
	}
	
	private static List<AppPhaseConfirm> phase(List<AppFrameConfirm> frames) {
		return Arrays.asList(
				new AppPhaseConfirm(1, ApprovalBehaviorAtr.UNAPPROVED, frames));
	}
	
	private static List<AppPhaseInstance> phase(ApprovalForm approvalForm, List<AppFrameInstance> frames) {
		return Arrays.asList(
				new AppPhaseInstance(1, approvalForm, frames));
	}
	
	private static AppRootConfirm confirm(List<AppPhaseConfirm> phases) {
		return new AppRootConfirm(
				null,
				null,
				null,
				null,
				RecordRootType.CONFIRM_WORK_BY_DAY,
				phases,
				null,
				null,
				null);
	}
	
	private static AppRootInstance instance(List<AppPhaseInstance> phases) {
		return new AppRootInstance(
				null,
				null,
				null,
				null,
				RecordRootType.CONFIRM_WORK_BY_DAY,
				phases);
	}
	
	private static AppFrameInstance instanceFrame(int frameNo, String approverId) {
		List<String> approver = new ArrayList<>();
		approver.add(approverId);
		return new AppFrameInstance(frameNo, false, approver);
	}
	
	private static AppFrameConfirm confirmFrame(int frameNo, String approverId) {
		return new AppFrameConfirm(frameNo, Optional.of(approverId), Optional.empty(), null);
	}
	
	private static List<AppFrameInstance> instanceFrames(String... approverIds) {
		return frames((i, id) -> instanceFrame(i, id), approverIds);
	}
	
	private static List<AppFrameConfirm> confirmFrames(String... approverIds) {
		return frames((i, id) -> confirmFrame(i, id), approverIds);
	}
	
	private static <F> List<F> frames(BiFunction<Integer, String, F> create, String... approverIds) {
		List<F> frames = new ArrayList<>();
		for (int i = 0; i < approverIds.length; i++) {
			if (approverIds[i] == null) continue;
			frames.add(create.apply(i, approverIds[i]));
		}
		return frames;
	}
}
