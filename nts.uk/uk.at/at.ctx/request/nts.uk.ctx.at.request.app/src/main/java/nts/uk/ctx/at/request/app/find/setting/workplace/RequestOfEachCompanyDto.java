package nts.uk.ctx.at.request.app.find.setting.workplace;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOfEachCompanyDto {
	public String companyID;
	public int selectionFlg;
	public List<ApprovalFunctionSettingDto> approvalFunctionSettingDtoLst;
}
