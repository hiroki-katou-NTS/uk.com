package nts.uk.ctx.pereg.app.command.process.checkdata;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.employee.category.EmpCtgFinder;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidChkCtgRepository;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class CheckDataEmpCommandHandler extends AsyncCommandHandler<CheckDataFromUI>{

	@Inject
	private RegulationInfoEmployeeRepository regulationInfoEmployeeRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepo;

	@Inject
	private PerInfoValidChkCtgRepository perInfoCheckCtgRepo;

	@Inject
	private PerInfoItemDefRepositoty perInfotemDfRepo;

	@Inject
	private PeregProcessor peregProcessor;

	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	@Inject
	private EmployeeFinderCtg employeeFinderCtg;

	@Inject
	private LayoutingProcessor layoutingProcessor;

	@Inject
	I18NResourcesForUK ukResouce;
	
	@Inject
	private EmpCtgFinder empCtgFinder;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject CheckDataEmployeeServices checkdataServices;
	
	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	
	
	@Override
	protected void handle(CommandHandlerContext<CheckDataFromUI> context) {
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
		CheckDataFromUI param = context.getCommand();
		
		// check 画面の選択状態をチェックする (Check trạng thái chọn màn hình) 
		validateDataUI(asyncContext.getCommand());
		
		this.checkdataServices.manager(param, asyncContext);
		dataSetter.setData("endTime", GeneralDateTime.now().toString());
	}
	
	// check 画面の選択状態をチェックする (Check trạng thái chọn màn hình) 
	private void validateDataUI(CheckDataFromUI query){
		if(!query.isPerInfoCheck() && !query.isMasterCheck()){
			throw new BusinessException(new RawErrorMessage("Msg_929"));
		}
		
		if(!query.isPerInfoCheck() && query.isMasterCheck() && !query.isBonusMngCheck()
			&& !query.isDailyPerforMngCheck() && !query.isMonthCalCheck()
			&& !query.isMonthPerforMngCheck() && !query.isPayRollMngCheck()
			&& !query.isScheduleMngCheck() && !query.isYearlyMngCheck()){
			throw new BusinessException(new RawErrorMessage("Msg_929"));
		}
	}
}
