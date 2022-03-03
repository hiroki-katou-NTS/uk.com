package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodEditingStateRepository;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class RemoveOptionalAggrPeriodCommandHandler extends CommandHandler<RemoveOptionalAggrPeriodCommand>{

	@Inject
	private AnyAggrPeriodRepository repository;

	@Inject
	private AggrPeriodTargetRepository aggrPeriodTargetRepository;
	@Inject
	private AggrPeriodExcutionRepository aggrPeriodExcutionRepo;

	@Inject
	private AttendanceTimeOfAnyPeriodRepository attendanceTimeOfAnyPeriodRepository;

	@Inject
	private AnyPeriodEditingStateRepository anyPeriodEditingStateRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveOptionalAggrPeriodCommand> context) {
		String companyId = AppContexts.user().companyId();
		// get command
		RemoveOptionalAggrPeriodCommand command = context.getCommand();
		
		if(!this.repository.findOneByCompanyIdAndFrameCode(companyId, command.getAggrFrameCode()).isPresent()){
			throw new BusinessException(new RawErrorMessage("対象データがありません。"));
		}
		// EA4209
		deletionOfaggreDataForAnyPeriod(command.getAggrFrameCode(),companyId);
		// delete process
		repository.deleteAnyAggrPeriod(companyId, command.getAggrFrameCode());
	
	}

	/**
	 * B:任意期間集計データの削除
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void deletionOfaggreDataForAnyPeriod(String aggrFrameCode,String companyId){
		//ドメインモデル「任意期間集計実行ログ」を取得する
		List<AggrPeriodExcution> periodExcutionList =  this.aggrPeriodExcutionRepo.findAggrCode(companyId, aggrFrameCode);
		//ドメインモデル「任意期間集計対象者」を取得する
		if(!periodExcutionList.isEmpty()){
			List<String> aggrIds = periodExcutionList.stream().map(AggrPeriodExcution::getAggrId).collect(Collectors.toList());
			List<AggrPeriodTarget> periodTargetList = aggrPeriodTargetRepository.findAll(aggrIds);
			List<String> sids = periodTargetList.stream().map(AggrPeriodTarget::getEmployeeId)
					.distinct().collect(Collectors.toList());
			//ドメインモデル「任意期間別実績の勤怠時間」を削除する
			attendanceTimeOfAnyPeriodRepository.remove(sids,aggrFrameCode);
			//ドメインモデル「任意期間修正の編集状態」を削除する
		}
		anyPeriodEditingStateRepository.deleteByCidAndCode(companyId,aggrFrameCode);

	}

}
