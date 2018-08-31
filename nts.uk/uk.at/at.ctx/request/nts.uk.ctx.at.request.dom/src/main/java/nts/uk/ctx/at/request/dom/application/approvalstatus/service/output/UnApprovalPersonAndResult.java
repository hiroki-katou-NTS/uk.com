package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author Anh.BD
 *
 */

public class UnApprovalPersonAndResult {
	//・未承認者＜社員ID＞(リスト)
	List<String> listUnAppPerson;
	
	//・結果（あり/なし)
	boolean result;
	
	public List<String> getListUnAppPerson() {
		return listUnAppPerson;
	}
	
	public void setListUnAppPerson(List<String> listUnAppPerson) {
		this.listUnAppPerson = listUnAppPerson;
	}
	
	public boolean isResult() {
		return result;
	}
	
	public void setResult(boolean result) 
	{
		this.result = result;
	}
	
	public UnApprovalPersonAndResult(List<String> listUnAppPerson, boolean result) {
		super();
		this.listUnAppPerson = listUnAppPerson;
		this.result = result;
	}
	

}
