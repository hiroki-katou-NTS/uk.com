package nts.uk.ctx.exio.app.command.exo.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.category.*;
import nts.arc.enums.EnumAdaptor;;

@Stateless
@Transactional
public class AddExCndOutputCommandHandler extends CommandHandler<ExCndOutputCommand> {

	@Inject
	private ExCndOutputRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExCndOutputCommand> context) {
		ExCndOutputCommand addCommand = context.getCommand();
		repository.add(new ExCndOutput(new CategoryId(addCommand.getCategoryId()),
				new MainTable(addCommand.getMainTable()),
				new Form1(addCommand.getForm1()),
				new Form2(addCommand.getForm2()),
				new Conditions(addCommand.getConditions()),
				new PhysicalProjectName(addCommand.getOutCondItemName1()),	
				new PhysicalProjectName(addCommand.getOutCondItemName2()),
				new PhysicalProjectName(addCommand.getOutCondItemName3()),
				new PhysicalProjectName(addCommand.getOutCondItemName4()),
				new PhysicalProjectName(addCommand.getOutCondItemName5()),
				new PhysicalProjectName(addCommand.getOutCondItemName6()),
				new PhysicalProjectName(addCommand.getOutCondItemName7()),
				new PhysicalProjectName(addCommand.getOutCondItemName8()),
				new PhysicalProjectName(addCommand.getOutCondItemName9()),
				new PhysicalProjectName(addCommand.getOutCondItemName10()),				
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation1(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation2(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation3(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation4(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation5(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation6(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation7(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation8(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation9(),Association.class) ,
				EnumAdaptor.valueOf(addCommand.getOutCondAssociation10(),Association.class)));
			
		

	}
}
