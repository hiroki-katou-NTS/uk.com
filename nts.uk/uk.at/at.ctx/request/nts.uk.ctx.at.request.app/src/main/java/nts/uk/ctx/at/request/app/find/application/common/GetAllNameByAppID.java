package nts.uk.ctx.at.request.app.find.application.common;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;

@Stateless
public class GetAllNameByAppID {
	
	@Inject 
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private GetAllDataAppPhaseFrame getAllDataAppPhaseFrame;
	
	public List<String> getAllNameByAppID(String appID){
 		String name1 = employeeAdapter.getEmployeeName("90000000-0000-0000-0000-000000000001");
//		List<String> listName = new  ArrayList<>();
//		OutputGetAllDataApp dataApp = getAllDataAppPhaseFrame.getAllDataAppPhaseFrame(appID);
//		for(OutputPhaseAndFrame outputPhaseAndFrame:dataApp.getListOutputPhaseAndFrame()) {
//			//get list ApproveAccepted
//			List<ApproveAcceptedDto> listApproveAcceptedDto = outputPhaseAndFrame.getListApproveAcceptedDto();
//			
//			for(int i =1;i<=5;i++) {
//				String employeeName = "";
//				for(ApproveAcceptedDto approveAcceptedDto :listApproveAcceptedDto) {
//					if(approveAcceptedDto.getDispOrder() == i) {
//						String str ="";
//						if(!approveAcceptedDto.getApproverSID().isEmpty() && employeeName !="") {
//							str =",";
//						}
//						employeeName += str + approveAcceptedDto.getApproverSID();
//					}
//				}
//				//add name in listName
//				listName.add(employeeName);
//				
//			}
//		}
		
		
		
		return null;
	}
}
