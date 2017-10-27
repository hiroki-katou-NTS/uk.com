package nts.uk.ctx.workflow.dom.agent;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.agent.output.ApprovalAgencyInfoOutput;
import nts.uk.ctx.workflow.dom.agent.output.ApproverRepresenterOutput;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class ApprovalAgencyInfoServiceImpl implements ApprovalAgencyInfoService {

	@Inject
	private AgentRepository agentRepository;

	@Override
	public ApprovalAgencyInfoOutput getApprovalAgencyInformation(String companyID, List<String> approver) {

		GeneralDate today = GeneralDate.today();

		boolean outputFlag = true;
		List<String> outputListRepresenterSID = new ArrayList<>();
		List<ApproverRepresenterOutput> outputListApproverAndRepresenterSID = new ArrayList<>();
		// neu list nguoi xác nhận = null
		if (approver.size() == 0) {
			return new ApprovalAgencyInfoOutput(outputListApproverAndRepresenterSID, outputListRepresenterSID,
					outputFlag);
		}
		// ドメインモデル「代行承認」を取得する(lấy thông tin domain 「代行承認」)
		List<Agent> agents = agentRepository.find(companyID, approver, today);

		// duyệt list người xác nhận
		for (String approveItem : approver) {
			// duyệt list lấy được trong domain Agent
			for (Agent agentAdapterDto : agents) {
				if(CollectionUtil.isEmpty(agents)) {
					outputFlag = false;
					// add nguoi xac nhan vao
					ApproverRepresenterOutput obj = new ApproverRepresenterOutput(approveItem, "Empty");
					outputListApproverAndRepresenterSID.add(obj);
				}
				// nếu người xác nhận có trong list Agent
				if (approveItem.equals(agentAdapterDto.getEmployeeId())) {
					// ktra xem AgentAppType = No_Settings hay k
					if (agentAdapterDto.getAgentSid1() == null
							|| agentAdapterDto.getAgentAppType1() == AgentAppType.NO_SETTINGS) {
						// if(agentAdapterDto.getAgentAppType1() == null
						// AgentApplicationType.NO_SETTINGS ) {
						outputFlag = false;
						// add nguoi xac nhan vao
						ApproverRepresenterOutput obj = new ApproverRepresenterOutput(approveItem, "Empty");
						outputListApproverAndRepresenterSID.add(obj);

					}
					// ktra xem AgentAppType = PATH hay k
					//if(agentAdapterDto.getAgentAppType1() != null
					if (agentAdapterDto.getAgentSid1() != null
							|| agentAdapterDto.getAgentAppType1() == AgentAppType.PATH) {
						ApproverRepresenterOutput obj = new ApproverRepresenterOutput(approveItem, "Pass");
						outputListApproverAndRepresenterSID.add(obj);
					}
					// ktra xem AgentAppType = SUBSTITUTE_DESIGNATION hay k
					//if(agentAdapterDto.getAgentAppType1() != null
					if (agentAdapterDto.getAgentSid1() != null
							||agentAdapterDto.getAgentAppType1() == AgentAppType.SUBSTITUTE_DESIGNATION) {
						outputFlag = false;
						ApproverRepresenterOutput obj = new ApproverRepresenterOutput(approveItem,
								agentAdapterDto.getAgentSid1());
						outputListApproverAndRepresenterSID.add(obj);
						// add data in list representerSID
						outputListRepresenterSID.add(agentAdapterDto.getAgentSid1());
					}

				}
			}

		}
		return new ApprovalAgencyInfoOutput(outputListApproverAndRepresenterSID, outputListRepresenterSID,
				outputFlag);
	}

}
