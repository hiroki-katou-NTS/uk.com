package nts.uk.ctx.workflow.dom.agent;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.agent.output.ApprovalAgencyInfoOutput;
import nts.uk.ctx.workflow.dom.agent.output.ApproverRepresenterOutput;
import nts.uk.ctx.workflow.dom.agent.output.RepresenterInformationOutput;

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
	public ApprovalAgencyInfoOutput getApprovalAgencyInformation(String companyID, List<String> approverList) {
		GeneralDate today = GeneralDate.today();
		boolean outputFlag = true;
		List<String> outputListRepresenterSID = new ArrayList<>();
		List<ApproverRepresenterOutput> outputListApproverAndRepresenterSID = new ArrayList<>();
		
		// neu list nguoi xác nhận = null
		if (CollectionUtil.isEmpty(approverList)) {
			outputFlag = false;
			return new ApprovalAgencyInfoOutput(outputListApproverAndRepresenterSID, outputListRepresenterSID, outputFlag);
		}
		
		// ドメインモデル「代行承認」を取得する(lấy thông tin domain 「代行承認」)
		List<Agent> agents = agentRepository.find(companyID, approverList, today);

		// duyệt list người xác nhận
		for (String approveItem : approverList) {
			if(CollectionUtil.isEmpty(agents)) {
				// add nguoi xac nhan vao
				ApproverRepresenterOutput obj = new ApproverRepresenterOutput(approveItem, RepresenterInformationOutput.noneInformation());
				outputListApproverAndRepresenterSID.add(obj);
				outputFlag = false;
				continue;
			}
			// duyệt list lấy được trong domain Agent
			for (Agent agentAdapterDto : agents) {
				// nếu người xác nhận có trong list Agent
				if (approveItem.equals(agentAdapterDto.getEmployeeId())) {
					// ktra xem AgentAppType = No_Settings hay k
					if (Strings.isBlank(agentAdapterDto.getAgentSid1()) || agentAdapterDto.getAgentAppType1().equals(AgentAppType.NO_SETTINGS)) {
						// if(agentAdapterDto.getAgentAppType1() == null
						// AgentApplicationType.NO_SETTINGS ) {
						// add nguoi xac nhan vao
						ApproverRepresenterOutput obj = new ApproverRepresenterOutput(approveItem, RepresenterInformationOutput.noneInformation());
						outputListApproverAndRepresenterSID.add(obj);
						outputFlag = false;
						continue;
					}
					// ktra xem AgentAppType = PATH hay k
					//if(agentAdapterDto.getAgentAppType1() != null
					if (Strings.isNotBlank(agentAdapterDto.getAgentSid1()) || agentAdapterDto.getAgentAppType1().equals(AgentAppType.PATH)) {
						ApproverRepresenterOutput obj = new ApproverRepresenterOutput(approveItem, RepresenterInformationOutput.pathInformation());
						outputListApproverAndRepresenterSID.add(obj);
						continue;
					}
					// ktra xem AgentAppType = SUBSTITUTE_DESIGNATION hay k
					//if(agentAdapterDto.getAgentAppType1() != null
					if (Strings.isNotBlank(agentAdapterDto.getAgentSid1()) ||agentAdapterDto.getAgentAppType1().equals(AgentAppType.SUBSTITUTE_DESIGNATION)) {
						ApproverRepresenterOutput obj = new ApproverRepresenterOutput(approveItem,
								RepresenterInformationOutput.representerInformation(agentAdapterDto.getAgentSid1()));
						outputListApproverAndRepresenterSID.add(obj);
						// add data in list representerSID
						outputListRepresenterSID.add(agentAdapterDto.getAgentSid1());
						outputFlag = false;
						continue;
					}
				}
			}
		}
		return new ApprovalAgencyInfoOutput(outputListApproverAndRepresenterSID, outputListRepresenterSID, outputFlag);
	}

	@Override
	public List<Agent> getApprovalAgencyInfoByPeriod(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		List<Agent> newAgents = new ArrayList<>();
		
		// ドメインモデル「代行承認」を取得する(lấy thông tin domain 「代行承認」)
		List<Agent> agents = agentRepository.findByCid(companyId);
		
		// 就業承認．基本設定．承認代行者 =パラメータ．社員ID OR 給与承認．基本設定．承認代行者 =パラメータ．社員ID OR 人事承認．基本設定．承認代行者 =パラメータ．社員ID OR 経理承認．基本設定．承認代行者 =パラメータ．社員ID
		for (Agent item : agents) {
			if(item.getAgentSid1().equals(employeeId) || item.getAgentSid2().equals(employeeId) || item.getAgentSid3().equals(employeeId) || item.getAgentSid4().equals(employeeId)) {
				if(item.getStartDate().beforeOrEquals(startDate) && item.getEndDate().afterOrEquals(endDate)) {
					newAgents.add(item);
				}
			}
		}
		
		return newAgents;
	}

}
