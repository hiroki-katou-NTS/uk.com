package nts.uk.ctx.sys.auth.app.command.grant.rolesetjob;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitle;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetCode;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class RegisterRoleSetGrantedJobTitleCommandHandler extends CommandHandler<RoleSetGrantedJobTitleCommand> {

	@Inject
	private RoleSetGrantedJobTitleRepository roleSetJobRepo;

	@Override
	protected void handle(CommandHandlerContext<RoleSetGrantedJobTitleCommand> context) {
		String cid = AppContexts.user().companyId();
		val command = context.getCommand();
		List<RoleSetGrantedJobTitle> grantedJobTitles = roleSetJobRepo.getByCompanyId(cid);
		List<RoleSetGrantedJobTitleDetailCommand> details = command.getDetails();
		if(grantedJobTitles.isEmpty()){
			details.forEach(e->{
				RoleSetGrantedJobTitle domain = new RoleSetGrantedJobTitle(
						cid,
						e.getJobTitleId(),
						new RoleSetCode(e.getRoleSetCd())
				);
				roleSetJobRepo.insert(domain);
			});
		}else {
			for (val item : details) {
				val itemOld = grantedJobTitles.stream()
						.filter(e->e.getJobTitleId().equals(item.getJobTitleId()))
						.collect(Collectors.toList());
				itemOld.forEach(e->{
					e.setRoleSetCd(new RoleSetCode(item.getRoleSetCd()));
					roleSetJobRepo.update(e);
				});
				if(itemOld.isEmpty()){
					roleSetJobRepo.insert(new RoleSetGrantedJobTitle(
							cid,
							item.getJobTitleId(),
							new RoleSetCode(item.getRoleSetCd())));
				}
			}
		}
	}
}
