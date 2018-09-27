package nts.uk.ctx.at.schedule.app.command.shift.workpairpattern;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.ComPattern;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.ComPatternItem;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.ComWorkPairSet;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkPairPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkplacePattern;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkplacePatternItem;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkplaceWorkPairSet;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class InsertWorkPairPatternCommandHandler extends CommandHandler<InsertPatternCommand> {

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private WorkPairPatternRepository repo;

	@Override
	protected void handle(CommandHandlerContext<InsertPatternCommand> context) {
		InsertPatternCommand patternCommand = context.getCommand();
		// 制約のチェックを行う(check quy ước cảu domain)
		// groupUsageAtr = 0 is 使用区分
		if(patternCommand.getGroupUsageAtr() == 0 && CollectionUtil.isEmpty(patternCommand.getListInsertPatternItemCommand())){
			throw new BusinessException("Msg_510");
		}
		
		// 制約のチェックを行う(check quy ước cảu domain)
		patternCommand.getListInsertPatternItemCommand().stream().forEach(patternItemCommand -> {
			patternItemCommand.getListInsertWorkPairSetCommand().stream().forEach(workPairSetCommand -> {
				basicScheduleService.checkPairWorkTypeWorkTime(workPairSetCommand.getWorkTypeCode(),
						workPairSetCommand.getWorkTimeCode());
			});
		});
		
		// 親画面パラメータ.勤務ペアパターン単位選択をチェックする(check parameter 勤務ペアパターン単位選択)
		if (StringUtil.isNullOrEmpty(patternCommand.getWorkplaceId(), true)) {
			String companyId = AppContexts.user().companyId();
			// convert to domain
			ComPattern comPattern = ComPattern.convertFromJavaType(companyId, patternCommand.getGroupNo(),
					patternCommand.getGroupName(), patternCommand.getGroupUsageAtr(), patternCommand.getNote(),
					patternCommand.getListInsertPatternItemCommand().stream().map(x -> {
						return ComPatternItem.convertFromJavaType(companyId, patternCommand.getGroupNo(),
								x.getPatternNo(), x.getPatternName(),
								x.getListInsertWorkPairSetCommand().stream().map(y -> {
									return ComWorkPairSet.convertFromJavaType(companyId, patternCommand.getGroupNo(),
											x.getPatternNo(), y.getPairNo(), y.getWorkTypeCode(), y.getWorkTimeCode());
								}).collect(Collectors.toList()));
					}).collect(Collectors.toList()));

			Optional<ComPattern> comPatternObject = repo.findComPatternById(companyId, patternCommand.getGroupNo());
			if (comPatternObject.isPresent()) {
				repo.updateComWorkPairPattern(comPattern);
			} else {
				repo.addComWorkPairPattern(comPattern);
			}

		} else {
			String workplaceId = patternCommand.getWorkplaceId();
			// convert to domain
			WorkplacePattern workplacePattern = WorkplacePattern.convertFromJavaType(workplaceId,
					patternCommand.getGroupNo(), patternCommand.getGroupName(), patternCommand.getGroupUsageAtr(),
					patternCommand.getNote(), patternCommand.getListInsertPatternItemCommand().stream().map(x -> {
						return WorkplacePatternItem.convertFromJavaType(workplaceId, patternCommand.getGroupNo(),
								x.getPatternNo(), x.getPatternName(),
								x.getListInsertWorkPairSetCommand().stream().map(y -> {
									return WorkplaceWorkPairSet.convertFromJavaType(workplaceId,
											patternCommand.getGroupNo(), x.getPatternNo(), y.getPairNo(),
											y.getWorkTypeCode(), y.getWorkTimeCode());
								}).collect(Collectors.toList()));
					}).collect(Collectors.toList()));

			Optional<WorkplacePattern> workplacePatternObject = repo.findWorkplacePatternById(workplaceId,
					patternCommand.getGroupNo());
			if (workplacePatternObject.isPresent()) {
				repo.updateWorkplaceWorkPairPattern(workplacePattern);
			} else {
				repo.addWorkplaceWorkPairPattern(workplacePattern);
			}
		}
	}
}
