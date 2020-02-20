package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

import lombok.Data;
//import lombok.Getter;

/**
 * 承認者IDリスト
 * 
 * @author vunv
 *
 */
@Data
public class ApproverInfoImport {
	
	/**承認者Gコード*/
	private String jobGCD;
	/**社員ID*/
	private String sid;
	/** 確定者 */
	private boolean isConfirmPerson;
	/** 承認者順序 */
	private int approverOrder;

	private String name;
	
	private String representerSID;
	
	private String representerName;
	
	private List<String> approverSIDList;
	
	private List<String> approverNameList;
	
	private List<String> representerSIDList;
	
	private List<String> representerNameList;

	public ApproverInfoImport(String jobGCD, String sid, boolean isConfirmPerson, int approverOrder) {
		super();
		this.jobGCD = jobGCD;
		this.sid = sid;
		this.isConfirmPerson = isConfirmPerson;
		this.approverOrder = approverOrder;
		this.approverSIDList = new ArrayList<String>();
		this.approverNameList = new ArrayList<String>();
		this.representerSIDList = new ArrayList<String>();
		this.representerNameList = new ArrayList<String>();
	}

	public void addEmployeeName(String name) {
		this.name = name;
	}
	
	public void addRepresenterSID(String representerSID) {
		this.representerSID = representerSID;
	}
	
	public void addRepresenterName(String representerName) {
		this.representerName = representerName;
	}
}
