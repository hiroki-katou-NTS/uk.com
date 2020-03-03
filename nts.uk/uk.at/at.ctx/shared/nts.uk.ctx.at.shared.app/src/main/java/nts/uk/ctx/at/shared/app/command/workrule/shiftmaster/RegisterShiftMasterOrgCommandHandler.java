package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;

/**
 * @author anhdt 職場で使うシフトマスタを登録する
 */
@Stateless
public class RegisterShiftMasterOrgCommandHandler extends CommandHandler<RegisterShiftMasterOrgCommand> {

	@Inject
	private ShiftMasterOrgRepository shiftMasterOrgRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterShiftMasterOrgCommand> context) {
		ShiftMasterOrganization request = context.getCommand().toDomain();
		Optional<ShiftMasterOrganization> exist = shiftMasterOrgRepo.getByTargetOrg(request.getCompanyId(), request.getTargetOrg());
		if(exist.isPresent()) {
			ShiftMasterOrganization domain = exist.get();
			domain.change(request.getListShiftMaterCode());
			shiftMasterOrgRepo.update(domain);
		} else {
			shiftMasterOrgRepo.insert(request);
		}
	}
}
