package command.person.currentaddress;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;

@Stateless
public class AddCurrentAddressCommandHandler extends CommandHandler<AddCurrentAddressCommand>
implements PeregAddCommandHandler<AddCurrentAddressCommand>{
	
	@Inject
	private CurrentAddressRepository currentAddressRepository;
	
	@Override
	public String targetCategoryId() {
		return "CS00003";
	}

	@Override
	public Class<?> commandClass() {
		return AddCurrentAddressCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<AddCurrentAddressCommand> context) {
		val command = context.getCommand();
		// Create new id
		String newId = IdentifierUtil.randomUniqueId();
		
		CurrentAddress currentAddress = CurrentAddress.createFromJavaType(newId, command.getPid(), command.getCountryId(), command.getPostalCode(),
				command.getPhoneNumber(), command.getPrefectures(), command.getHouseRent(), command.getStartDate(), command.getEndDate(),
				command.getAddress1(), command.getAddressKana1(), command.getAddress2(), command.getAddressKana2(), command.getHomeSituationType(),
				command.getCurrentAddressId(), command.getHouseType(), command.getNearestStation());
		
		// Add current address
		currentAddressRepository.addCurrentAddress(currentAddress);
	}

}
