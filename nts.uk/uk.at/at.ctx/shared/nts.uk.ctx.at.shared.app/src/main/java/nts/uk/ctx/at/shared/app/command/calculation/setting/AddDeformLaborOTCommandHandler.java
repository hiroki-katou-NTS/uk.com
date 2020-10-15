package nts.uk.ctx.at.shared.app.command.calculation.setting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOT;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOTRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author yennh
 *
 */
@Stateless
public class AddDeformLaborOTCommandHandler extends CommandHandler<AddDeformLaborOTCommand> {
	@Inject
	private DeformLaborOTRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddDeformLaborOTCommand> context) {
		AddDeformLaborOTCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// convert to domain
		DeformLaborOT deformLaborOT = command.toDomain(companyId);
		deformLaborOT.validate();
		Optional<DeformLaborOT> optional = this.repository.findByCId(companyId);

		if (optional.isPresent()) {
			// update deform labor Overtime
			this.repository.update(deformLaborOT);
		} else {
			// add deform labor overtime
			this.repository.add(deformLaborOT);
		}
	}
}
