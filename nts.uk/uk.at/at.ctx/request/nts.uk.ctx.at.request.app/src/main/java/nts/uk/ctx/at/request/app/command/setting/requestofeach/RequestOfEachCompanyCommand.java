package nts.uk.ctx.at.request.app.command.setting.requestofeach;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompany;

@Data
@AllArgsConstructor
public class RequestOfEachCompanyCommand {
	/**会社ID*/
	public String companyId;	
	/**
	 * 申請時の承認者の選択
	 */
	public int selectOfApproversFlg;
		
	public List<RequestAppDetailSettingCommand> comAppConfigDetails;
	
	private RequestOfEachCompany toDomain(String companyId) {
		List<RequestAppDetailSetting> list = new ArrayList<>();
		if(!this.getComAppConfigDetails().isEmpty()){
			for(RequestAppDetailSettingCommand item: this.getComAppConfigDetails()){
				list.add(item.toDomainDetail(item.getCompanyId()));
			}
		}
		RequestOfEachCompany request = RequestOfEachCompany.createFromJavaType(this.getSelectOfApproversFlg(), list);
		return request;
	}
}
