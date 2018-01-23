package nts.uk.ctx.at.request.app.command.setting.workplace;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.workplace.SelectionFlg;

@Data
@AllArgsConstructor
public class RequestOfEachCompanyCommand {
	/**会社ID*/
	public String companyId;	
		
	public List<ApprovalFunctionSettingCommand> comAppConfigDetails;
	
	public RequestOfEachCompany toDomain(String companyId) {
		return new RequestOfEachCompany(
				companyId, 
				SelectionFlg.SELECTION, 
				comAppConfigDetails.stream().map(x -> x.toDomainDetail()).collect(Collectors.toList()));
	}
}
