//package nts.uk.ctx.at.request.ac.workflow.agent;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.enums.EnumAdaptor;
//import nts.arc.time.GeneralDate;
//import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentRequestAdapter;
//import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentImport;
//import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgentApplicationType;
//import nts.uk.ctx.workflow.pub.agent.AgentPub;
//import nts.uk.ctx.workflow.pub.agent.AgentPubDto;
//
//@Stateless
//public class AgentAdapterImp implements AgentRequestAdapter{
//	
//	@Inject
//	private AgentPub agentPub;
//
//	@Override
//	public List<AgentImport> find(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
//		List<AgentPubDto> empDto = agentPub.find(companyId, employeeId, startDate, endDate);
//		List<AgentImport> lstEmployees = new ArrayList<>();
//		for(AgentPubDto agentPubDto:empDto) {
//			AgentImport agentDto = new AgentImport();
//			agentDto.setEmployeeId(agentPubDto.getEmployeeId());
//			agentDto.setRequestId(agentPubDto.getRequestId());
//			agentDto.setStartDate(agentPubDto.getStartDate());
//			agentDto.setEndDate(agentPubDto.getEndDate());
//			agentDto.setAgentSid1(agentPubDto.getAgentSid1());
//			agentDto.setAgentAppType1(EnumAdaptor.valueOf( agentPubDto.getAgentAppType1(), AgentApplicationType.class));
//			agentDto.setAgentSid2(agentPubDto.getAgentSid2());
//			agentDto.setAgentAppType2(EnumAdaptor.valueOf( agentPubDto.getAgentAppType2(), AgentApplicationType.class));
//			agentDto.setAgentSid3(agentPubDto.getAgentSid3());
//			agentDto.setAgentAppType3(EnumAdaptor.valueOf( agentPubDto.getAgentAppType3(), AgentApplicationType.class));
//			agentDto.setAgentSid4(agentPubDto.getAgentSid4());
//			agentDto.setAgentAppType4(EnumAdaptor.valueOf( agentPubDto.getAgentAppType4(), AgentApplicationType.class));
//			lstEmployees.add(agentDto);
//		}
//		
//		return lstEmployees;
//	}
//
//	@Override
//	public List<AgentImport> findAll(String companyId, List<String> employeeId, GeneralDate startDate,
//			GeneralDate endDate) {
//		List<AgentImport> lstEmployees = new ArrayList<>();
//		for(String emp :employeeId) {
//			List<AgentPubDto> empDto = agentPub.find(companyId, emp, startDate, endDate);
//			for(AgentPubDto agentPubDto:empDto) {
//				AgentImport agentDto = new AgentImport();
//				agentDto.setEmployeeId(agentPubDto.getEmployeeId());
//				agentDto.setRequestId(agentPubDto.getRequestId());
//				agentDto.setStartDate(agentPubDto.getStartDate());
//				agentDto.setEndDate(agentPubDto.getEndDate());
//				agentDto.setAgentSid1(agentPubDto.getAgentSid1());
//				agentDto.setAgentAppType1(EnumAdaptor.valueOf( agentPubDto.getAgentAppType1(), AgentApplicationType.class));
//				agentDto.setAgentSid2(agentPubDto.getAgentSid2());
//				agentDto.setAgentAppType2(EnumAdaptor.valueOf( agentPubDto.getAgentAppType2(), AgentApplicationType.class));
//				agentDto.setAgentSid3(agentPubDto.getAgentSid3());
//				agentDto.setAgentAppType3(EnumAdaptor.valueOf( agentPubDto.getAgentAppType3(), AgentApplicationType.class));
//				agentDto.setAgentSid4(agentPubDto.getAgentSid4());
//				agentDto.setAgentAppType4(EnumAdaptor.valueOf( agentPubDto.getAgentAppType4(), AgentApplicationType.class));
//				lstEmployees.add(agentDto);
//			}
//		}
//		return lstEmployees;
//	}
//}
