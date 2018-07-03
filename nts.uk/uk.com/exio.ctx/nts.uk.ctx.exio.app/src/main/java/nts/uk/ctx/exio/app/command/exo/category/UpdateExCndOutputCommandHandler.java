package nts.uk.ctx.exio.app.command.exo.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.category.Association;
import nts.uk.ctx.exio.dom.exo.category.CategoryId;
import nts.uk.ctx.exio.dom.exo.category.Conditions;
import nts.uk.ctx.exio.dom.exo.category.ExCndOutput;
import nts.uk.ctx.exio.dom.exo.category.ExCndOutputRepository;
import nts.uk.ctx.exio.dom.exo.category.Form1;
import nts.uk.ctx.exio.dom.exo.category.Form2;
import nts.uk.ctx.exio.dom.exo.category.MainTable;
import nts.uk.ctx.exio.dom.exo.category.PhysicalProjectName;


@Stateless
@Transactional
public class UpdateExCndOutputCommandHandler extends CommandHandler<ExCndOutputCommand> {

	@Inject
	private ExCndOutputRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExCndOutputCommand> context) {
		ExCndOutputCommand updateCommand = context.getCommand();
		repository.update(new ExCndOutput(new CategoryId(updateCommand.getCategoryId()),
				new MainTable(updateCommand.getMainTable()),
				new Form1(updateCommand.getForm1()),
				new Form2(updateCommand.getForm2()),
				new Conditions(updateCommand.getConditions()),
				new PhysicalProjectName(updateCommand.getOutCondItemName1()),	
				new PhysicalProjectName(updateCommand.getOutCondItemName2()),
				new PhysicalProjectName(updateCommand.getOutCondItemName3()),
				new PhysicalProjectName(updateCommand.getOutCondItemName4()),
				new PhysicalProjectName(updateCommand.getOutCondItemName5()),
				new PhysicalProjectName(updateCommand.getOutCondItemName6()),
				new PhysicalProjectName(updateCommand.getOutCondItemName7()),
				new PhysicalProjectName(updateCommand.getOutCondItemName8()),
				new PhysicalProjectName(updateCommand.getOutCondItemName9()),
				new PhysicalProjectName(updateCommand.getOutCondItemName10()),				
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation1(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation2(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation3(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation4(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation5(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation6(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation7(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation8(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation9(),Association.class) ,
				EnumAdaptor.valueOf(updateCommand.getOutCondAssociation10(),Association.class)));

	}
}
