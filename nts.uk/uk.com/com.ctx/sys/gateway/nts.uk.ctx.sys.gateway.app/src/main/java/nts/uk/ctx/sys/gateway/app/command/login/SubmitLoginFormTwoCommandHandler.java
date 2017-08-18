/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeCodeSettingAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeCodeSettingDto;
import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeDto;
import nts.uk.ctx.sys.gateway.dom.login.EmployCodeEditType;
import nts.uk.ctx.sys.gateway.dom.login.User;
import nts.uk.ctx.sys.gateway.dom.login.UserRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SubmitLoginFormTwoCommandHandler.
 */
@Stateless
public class SubmitLoginFormTwoCommandHandler extends CommandHandler<SubmitLoginFormTwoCommand> {

	/** The user repository. */
	@Inject
	UserRepository userRepository;

	/** The employee code setting repo. */
	@Inject
	EmployeeCodeSettingAdapter employeeCodeSettingAdapter;

	/** The employee adapter. */
	@Inject
	EmployeeAdapter employeeAdapter;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SubmitLoginFormTwoCommand> context) {

		SubmitLoginFormTwoCommand command = context.getCommand();
		String companyCode = command.getCompanyCode();
		String employeeCode = command.getEmployeeCode();
		String password = command.getPassword();
		String companyId = AppContexts.user().contractCode() + "-" + companyCode;
		// check validate input
		this.checkInput(command);

		// TODO edit employee code
		employeeCode = this.employeeCodeEdit(employeeCode, companyId);
		// TODO get domain 社員
		EmployeeDto em = this.getEmployee(companyId, employeeCode);
		// TODO get User by associatedPersonId
		User user = this.getUser(em.getEmployeeId().toString());// TODO
		// check password
		this.compareHashPassword(user, password);
		// check time limit
		this.checkLimitTime(user);
	}

	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SubmitLoginFormTwoCommand command) {

		// check input company code
		if (command.getCompanyCode() == null) {
			throw new BusinessException("#Msg_311");
		}
		// check input employee code
		if (command.getEmployeeCode() == null) {
			throw new BusinessException("#Msg_312");
		}
		// check input password
		if (command.getPassword() == null) {
			throw new BusinessException("#Msg_310");
		}
	}

	/**
	 * Employee code edit.
	 *
	 * @param employeeCode the employee code
	 * @param companyId the company id
	 * @return the string
	 */
	private String employeeCodeEdit(String employeeCode, String companyId) {
		Optional<EmployeeCodeSettingDto> findEmployeeCodeSetting = employeeCodeSettingAdapter.getbyCompanyId(companyId);
		if (findEmployeeCodeSetting.isPresent()) {
			EmployeeCodeSettingDto employeeCodeSetting = findEmployeeCodeSetting.get();
			EmployCodeEditType editType = employeeCodeSetting.getEditType();
			Integer addNumberDigit = employeeCodeSetting.getNumberDigit();
			if (employeeCodeSetting.getNumberDigit() == employeeCode.length()) {
				// not edit employeeCode
				return employeeCode;
			} else {
				switch (editType) {
				case ZeroBefore:
					employeeCode = StringUtils.leftPad(employeeCode, addNumberDigit, "0");
					break;
				case ZeroAfter:
					employeeCode = StringUtils.rightPad(employeeCode, addNumberDigit, "0");
					break;
				case SpaceBefore:
					employeeCode = StringUtils.leftPad(employeeCode, addNumberDigit);
					break;
				case SpaceAfter:
					employeeCode = StringUtils.rightPad(employeeCode, addNumberDigit);
					break;
				default:
					break;
				}
				return employeeCode;
			}
		} else {
			return employeeCode;
		}
	}

	/**
	 * Gets the employee.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @return the employee
	 */
	private EmployeeDto getEmployee(String companyId, String employeeCode) {
		Optional<EmployeeDto> em = employeeAdapter.getByEmployeeCode(companyId, employeeCode);
		if (em.isPresent()) {
			return em.get();
		} else {
			throw new BusinessException("#Msg_301");
		}
	}

	/**
	 * Gets the user.
	 *
	 * @param associatedPersonId the associated person id
	 * @return the user
	 */
	private User getUser(String associatedPersonId) {
		Optional<User> user = userRepository.getByAssociatedPersonId(associatedPersonId);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new BusinessException("#Msg_301");
		}
	}

	/**
	 * Compare hash password.
	 *
	 * @param user the user
	 * @param password the password
	 */
	private void compareHashPassword(User user, String password) {
		// TODO compare hash string here
		if (!user.getPassword().v().equals(password)) {
			throw new BusinessException("#Msg_302");
		}
	}

	/**
	 * Check limit time.
	 *
	 * @param user the user
	 */
	private void checkLimitTime(User user) {
		if (user.getExpirationDate().before(GeneralDate.today())) {
			throw new BusinessException("#Msg_316");
		}
	}
}
