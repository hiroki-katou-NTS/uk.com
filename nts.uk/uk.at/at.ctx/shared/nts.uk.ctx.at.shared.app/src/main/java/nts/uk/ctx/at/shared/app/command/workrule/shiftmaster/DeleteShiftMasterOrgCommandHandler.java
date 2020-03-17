package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 職場で使うシフトマスタを削除する
 */
@Stateless
public class DeleteShiftMasterOrgCommandHandler extends CommandHandler<DeleteShiftMasterOrgCommand> {

	@Inject
	private ShiftMasterOrgRepository shiftMasterOrgRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteShiftMasterOrgCommand> context) {
		String companyId = AppContexts.user().companyId();
		TargetOrgIdenInfor target = context.getCommand().toTarget();
		Optional<ShiftMasterOrganization> exist = shiftMasterOrgRepo.getByTargetOrg(companyId, target);
		if(exist.isPresent()) {
			ShiftMasterOrganization domain = exist.get();
			shiftMasterOrgRepo.delete(domain.getCompanyId(), domain.getTargetOrg());
		} else {
			throw new BusinessException("Msg_85");
		}
	}
}
