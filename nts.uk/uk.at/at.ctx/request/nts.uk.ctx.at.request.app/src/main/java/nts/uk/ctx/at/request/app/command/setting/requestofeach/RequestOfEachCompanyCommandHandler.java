package nts.uk.ctx.at.request.app.command.setting.requestofeach;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * update request of each company
 * @author yennth
 */
@Stateless
public class RequestOfEachCompanyCommandHandler extends CommandHandler<RequestOfEachCompanyCommand>{
	@Inject
	private RequestOfEachCompanyRepository requestRep;

	@Override
	protected void handle(CommandHandlerContext<RequestOfEachCompanyCommand> context) {
		RequestOfEachCompanyCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<RequestOfEachCompany> requestCom = requestRep.getRequestByCompany(companyId);
		List<RequestAppDetailSetting> list = new ArrayList<>();
		if(!data.getComAppConfigDetails().isEmpty()){
			for (RequestAppDetailSettingCommand item : data.getComAppConfigDetails()){
				list.add(item.toDomainDetail(item.getCompanyId()));
			}
		}
		RequestOfEachCompany request = RequestOfEachCompany.createFromJavaType(data.getSelectOfApproversFlg(), list);
		request.validate();
		if(requestCom.isPresent()){
			requestRep.updateRequestOfEachCompany(request);
		}else{
			requestRep.insertRequestOfEachCompany(request);
		}
	}
	
}
