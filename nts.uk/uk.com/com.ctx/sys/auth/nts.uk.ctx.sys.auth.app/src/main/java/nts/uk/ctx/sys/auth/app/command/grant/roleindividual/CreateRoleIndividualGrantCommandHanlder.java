package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
@Transactional
public class CreateRoleIndividualGrantCommandHanlder extends CommandHandler<CreateRoleIndividualGrantCommand> {

	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepo;
	
	@Inject
	private UserRepository userRepo;
	

	@Override
	protected void handle(CommandHandlerContext<CreateRoleIndividualGrantCommand> context) {
		CreateRoleIndividualGrantCommand command = context.getCommand();
		Optional<RoleIndividualGrant> roleIndividualGrant = roleIndividualGrantRepo.findRoleIndividualGrant(command.getUserID(), command.getCompanyID(),command.getRoleType());
		/////////////
		if (roleIndividualGrant.isPresent()){
			throw new BusinessException("Msg_3");
		}     
        if(command.getUserID()==null){
        	throw new BusinessException("Msg_218");
        }
        //ドメインモデル「ロール個人別付与」を新規登録する
        //Register a domain model "Role individual grant" 
        roleIndividualGrantRepo.add(command.toDomain());
        
        if(command.isSetRoleAdminFlag()==true){
        	
        	RoleIndividualGrant roleIndiGrantSys =new RoleIndividualGrant(
        			command.getUserID(),
        			command.getRoleID(),
        			command.getDecisionCompanyID(),
        			//roleType
        			command.getRoleType(),
        			new DatePeriod(command.getStartValidPeriod(), command.getEndValidPeriod()));
        	//ドメインモデル「ロール個人別付与」を新規登録する
        	//Register a domain model "Role individual grant"
        	// param companyID = decisionCompanyID() 
        	
        	 roleIndividualGrantRepo.add(roleIndiGrantSys);
        }
       Optional<User> user 	= userRepo.getByLoginId(command.getUserID());
       /////////////////////TODO /////////////////////////////////////////////////////////////////
        
}
}
