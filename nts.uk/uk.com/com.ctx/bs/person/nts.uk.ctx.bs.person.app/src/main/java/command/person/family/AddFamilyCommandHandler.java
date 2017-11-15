package command.person.family;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;

@Stateless
public class AddFamilyCommandHandler extends CommandHandler<AddFamilyCommand> 
implements PeregAddCommandHandler<AddFamilyCommand>{
	@Inject 
	private FamilyRepository familyRepository;

	@Override
	public String targetCategoryId() {
		return "CS00004";
	}

	@Override
	public Class<?> commandClass() {
		return AddFamilyCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<AddFamilyCommand> context) {
		
		val command = context.getCommand();
		// Create new Id
		String newId = IdentifierUtil.randomUniqueId();
		
		Family family = Family.createFromJavaType(command.getBirthday(), command.getDeadDay(), command.getEntryDate(),
				command.getExpelledDate(), newId, command.getFullName(), command.getFullNameKana(), command.getNameMultiLangFull(),
				command.getNameMultiLangFullKana(), command.getNameRomajiFull(), command.getNameRomajiFullKana(), 
				command.getNationalityId(), command.getOccupationName(), command.getPersonId(), command.getRelationship(), 
				command.getSupportCareType(), command.getTokodekeName(), command.getTogSepDivisionType(), command.getWorkStudentType());
		
		// Add family
		familyRepository.addFamily(family);
	}
}
