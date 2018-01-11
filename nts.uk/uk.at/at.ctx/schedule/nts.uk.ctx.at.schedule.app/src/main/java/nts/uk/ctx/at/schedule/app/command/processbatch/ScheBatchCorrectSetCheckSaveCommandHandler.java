/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ScheBatchCorrectSetCheckSaveCommandHandler.
 */
@Stateless
public class ScheBatchCorrectSetCheckSaveCommandHandler extends CommandHandler<ScheBatchCorrectSetCheckSaveCommand>{

	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	/** The work time repository. */
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	/** The basic schedule service. */
	@Inject BasicScheduleService basicScheduleService;
	
	/** The Constant DEFAULT_CODE. */
	private static final String DEFAULT_CODE = "000";
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	public void handle(CommandHandlerContext<ScheBatchCorrectSetCheckSaveCommand> context) {
		
		// get login user 
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command 
		ScheBatchCorrectSetCheckSaveCommand command = context.getCommand();
		
		// check null or default work type code => message 10
		if(this.checkDefaultCode(command.getWorktypeCode())){
			throw new BusinessException("Msg_10");
		}
		
		// check empty employee id => message 559
		if(CollectionUtil.isEmpty(command.getEmployeeIds())){
			throw new BusinessException("Msg_559");
		}
		
		// check work type master
		this.checkWorkTypeMaster(companyId, command.getWorktypeCode());
		
		// check work time master
		this.checkWorkTimeMater(companyId, command.getWorktimeCode());
		
		// check pair work work type and work time
		this.basicScheduleService.checkPairWorkTypeWorkTime(command.getWorktypeCode(), command.getWorktimeCode());
	}

	/**
	 * Check default code.
	 *
	 * @param code the code
	 * @return true, if successful
	 */
	private boolean checkDefaultCode(String code) {
		return (DEFAULT_CODE.equals(code) || StringUtil.isNullOrEmpty(code, false));
	}
	
	/**
	 * Check work type master.
	 *
	 * @param worktypeCode the worktype code
	 */
	// 勤務種類のマスタチェック
	private void checkWorkTypeMaster(String companyId, String worktypeCode) {

		// call repository find work type by code
		Optional<WorkType> optionalWorkType = this.workTypeRepository.findByPK(companyId, worktypeCode);

		// check work type not exist
		if (!optionalWorkType.isPresent()) {
			throw new BusinessException("Msg_436");
		}

		// work type exist => check deprecate of work type is 廃止する
		if (optionalWorkType.isPresent()
				&& optionalWorkType.get().getDeprecate() == DeprecateClassification.Deprecated) {
			throw new BusinessException("Msg_468");
		}
	}
	
	/**
	 * Check work time mater.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	private void checkWorkTimeMater(String companyId, String workTimeCode) {

		// check default work time code
		if (this.checkDefaultCode(workTimeCode)) {
			return;
		}

		// call repository find work time by code
		Optional<WorkTimeSetting> optionalWorkTime = this.workTimeRepository.findByCode(companyId, workTimeCode);

		// check work time not exits
		if (!optionalWorkTime.isPresent()) {
			throw new BusinessException("Msg_437");
		}

		// work time exits => check display attr not display
		if (optionalWorkTime.isPresent() && optionalWorkTime.get().getAbolishAtr().value == AbolishAtr.ABOLISH.value) {
			throw new BusinessException("Msg_469");
		}
	}
}
