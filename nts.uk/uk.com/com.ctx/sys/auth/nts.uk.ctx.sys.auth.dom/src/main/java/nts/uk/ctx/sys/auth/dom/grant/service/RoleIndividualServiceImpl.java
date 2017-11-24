package nts.uk.ctx.sys.auth.dom.grant.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class RoleIndividualServiceImpl implements RoleIndividualService {

	@Inject
	private PersonAdapter personAdapter; 

	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepository;

	@Inject
	private UserRepository userRepository;

	@Override
	public boolean isExit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create(boolean setCompanyAdminRoleFlag, String decisionCompanyID, RoleIndividualGrant roleIndividualGrant) {
		if (roleIndividualGrant.getRoleType() == RoleType.SYSTEM_MANAGER) {
			
			setCompanyAdminRoleFlag = true;
		} else {
			setCompanyAdminRoleFlag = false;
			decisionCompanyID = null;

		}
		if (roleIndividualGrant.getUserId() == null) {
			throw new BusinessException("Msg_218");
		} else {
			// ドメインモデル「ロール個人別付与」を新規登録する
			// Register a domain model "Role individual grant"
			roleIndividualGrantRepository.add(roleIndividualGrant);
			if (setCompanyAdminRoleFlag = true) {
				RoleIndividualGrant roleIndividual = RoleIndividualGrant.createFromJavaType(roleIndividualGrant.getUserId(), roleIndividualGrant.getRoleId(), decisionCompanyID, roleIndividualGrant.getRoleType().value,
						roleIndividualGrant.getValidPeriod().start(), roleIndividualGrant.getValidPeriod().end());
			} 
                Optional<User> user =  userRepository.getByUserID(roleIndividualGrant.getUserId());
			
			if(user.get().isDefaultUser() == true){
				////////////////////////////////////////////////////////TODO Chờ QA ///////////////////////////////////////////////////////////
			}
		}
	}

	@Override
	public void update(RoleIndividualGrant roleIndividualGrant) {
		if (roleIndividualGrant.getUserId() == null) {
			throw new BusinessException("Msg_218");
		}
		
		if(checkSysAdmin(roleIndividualGrant.getUserId(), roleIndividualGrant.getValidPeriod()) ==false ){
			throw new BusinessException("Msg_330");
		}
		roleIndividualGrantRepository.update(roleIndividualGrant);

	}

	@Override
	public void deleteRoleIndividualGrant(String userID, String coppanyID, RoleType roleType, DatePeriod validPeriod) {
		if (roleType == RoleType.SYSTEM_MANAGER) {
			if (checkSysAdmin(userID, validPeriod) == true) {
				/*
				 * 有効なシステム管理者が存在しない期間が発生するため、削除できません。
				 * システム管理者ロールを引き継ぎ先のユーザーに割り当てた後、再度処理をしてください。 It can not be
				 * deleted because a valid system administrator does not exist
				 * for a period of time. Please assign the system administrator
				 * role to the user to take over and then try again.
				 */
				throw new BusinessException("Msg_331");
			} else {
				roleIndividualGrantRepository.remove(userID, coppanyID, roleType);
			}
		} else {
			roleIndividualGrantRepository.remove(userID, coppanyID, roleType);
		}

	}

	@Override
	public boolean checkSysAdmin(String userID, DatePeriod validPeriod) {
		// パラメータのユーザIDを元に、ドメインモデル「ロール個人別付与」を取得する
		//Based on the user ID of the parameter, obtain the domain model "Role individual grant"
		List<RoleIndividualGrant> listRoleIndividualGrant = roleIndividualGrantRepository.findByUserAndRole(userID, RoleType.SYSTEM_MANAGER);
		if(!listRoleIndividualGrant.isEmpty()){
			//パラメータ.有効期間From、パラメータ.有効期間Toをシステム管理者リストにセットする
			//Parameter. Validity period From, parameter. Set valid period To in the system administrator list
			List<String> sysAdmins = listRoleIndividualGrant.stream().filter(c -> c.getValidPeriod().equals(validPeriod)).map(c -> c.getUserId()).collect(Collectors.toList());
			/*
			条件：
			ロール種類＝システム管理者　And
			ユーザID＜＞パラメータ．ユーザID*/
			/*conditions:
			Role type = System administrator And
			User ID <> parameter. User ID*/
			List<RoleIndividualGrant> filteredListRole = listRoleIndividualGrant.stream().filter(c -> c.getUserId() != userID).collect(Collectors.toList());
			List<String> filteredListUserID = filteredListRole.stream().map(c -> c.getUserId()).collect(Collectors.toList());
			
			for (RoleIndividualGrant role: filteredListRole) {
				// ドメインモデル「ユーザ」を取得する
				User user = userRepository.getByUserID(userID).get();
				/////////////////////////TODO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if (role.getValidPeriod().end().beforeOrEquals(user.getExpirationDate())) {
					
				} else {
					return true;
				}
					
				
				// If 個人別付与．有効期間To＜＝ユーザ．有効期限
				// If Individual Grant. Effective period To <= user. expiration
				// date
			}
		}
		
		return false;
	}

	@Override
	public OutputSetAdminRole setAdminRole(boolean setCompanyAdminRoleFlag, String decisionCompanyID) {
		String outputdecisionCompanyID = null;
		if (setCompanyAdminRoleFlag == true) {
			setCompanyAdminRoleFlag = true;
			outputdecisionCompanyID = decisionCompanyID;
		} else {
			setCompanyAdminRoleFlag = false;
		}
		return new OutputSetAdminRole(setCompanyAdminRoleFlag, outputdecisionCompanyID);
	}

	@Override
	public List<RoleIndividualGrant> searchRole(RoleType roleType, String companyID) {
		// TODO Auto-generated method stub
		return null;
	}

	

	



}
