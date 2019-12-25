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
	
	/**職位ID*/
	private String jobId;
	/**
	 * 社員ID
	 */
	private String sid;
	/** 承認フェーズ順序 */
	private int phaseOrder;
	/** 確定者 */
	private boolean isConfirmPerson;
	/** 承認者順序 */
	private int approverOrder;

	private String name;
	/**承認者指定区分*/
	private int approvalAtr;
	
	private String representerSID;
	
	private String representerName;
	
	private List<String> approverSIDList;
	
	private List<String> approverNameList;
	
	private List<String> representerSIDList;
	
	private List<String> representerNameList;

	public ApproverInfoImport(String jobId,String sid, int phaseOrder, boolean isConfirmPerson, int approverOrder,int approvalAtr) {
		super();
		this.jobId = jobId;
		this.sid = sid;
		this.phaseOrder = phaseOrder;
		this.isConfirmPerson = isConfirmPerson;
		this.approverOrder = approverOrder;
		this.approvalAtr = approvalAtr;
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
