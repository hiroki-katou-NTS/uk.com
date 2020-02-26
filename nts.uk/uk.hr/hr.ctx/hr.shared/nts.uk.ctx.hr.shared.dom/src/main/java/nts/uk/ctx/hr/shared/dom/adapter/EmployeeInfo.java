/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeInfo {
	
	public String inputPid;//入力者個人ID
	public String inputSid;//入力者社員ID
	public String inputScd; //入力者社員CD
	public String inputBussinessName; //入力者表示氏名
	
	public String appliPerId;//申請者個人ID
	public String appliPerSid;//申請者社員ID
	public String appliPerScd;//申請者社員CD
	public String appliPerBussinessName;//申請者表示氏名
	
	public String appDevId ;//申請者部門ID
	public String appDevCd ;//申請者部門CD
	public String appDevName ;//申請者部門名
	
	public String appPosId ;//申請者職位ID
	public String appPosCd ;//申請者職位CD
	public String appPosName ;//申請者職位名

}
