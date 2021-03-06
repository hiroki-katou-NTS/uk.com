package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodEditingStateRepository;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author phongtq
 */
@Stateless
public class RemoveOptionalAggrPeriodCommandHandler extends CommandHandler<RemoveOptionalAggrPeriodCommand> {

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

        if (!this.repository.findOneByCompanyIdAndFrameCode(companyId, command.getAggrFrameCode()).isPresent()) {
            throw new BusinessException(new RawErrorMessage("????????????????????????????????????"));
        }
        // EA4209
        deletionOfaggreDataForAnyPeriod(command.getAggrFrameCode(), companyId);
        // delete process
        repository.deleteAnyAggrPeriod(companyId, command.getAggrFrameCode());

    }

    /**
     * B:????????????????????????????????????
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void deletionOfaggreDataForAnyPeriod(String aggrFrameCode, String companyId) {
        //????????????????????????????????????????????????????????????????????????
        List<AggrPeriodExcution> periodExcutionList = this.aggrPeriodExcutionRepo.findAggrCode(companyId, aggrFrameCode);
        //?????????????????????????????????????????????????????????????????????
        if (!periodExcutionList.isEmpty()) {
            List<String> aggrIds = periodExcutionList.stream().map(AggrPeriodExcution::getAggrId).collect(Collectors.toList());
            List<AggrPeriodTarget> periodTargetList = aggrPeriodTargetRepository.findAll(aggrIds);
            List<String> sids = periodTargetList.stream().map(AggrPeriodTarget::getEmployeeId)
                    .distinct().collect(Collectors.toList());
            //??????????????????????????????????????????????????????????????????????????????
            attendanceTimeOfAnyPeriodRepository.remove(sids, aggrFrameCode);
            //???????????????????????????????????????????????????????????????????????????
        }
        anyPeriodEditingStateRepository.deleteByCidAndCode(companyId, aggrFrameCode);

    }

}
