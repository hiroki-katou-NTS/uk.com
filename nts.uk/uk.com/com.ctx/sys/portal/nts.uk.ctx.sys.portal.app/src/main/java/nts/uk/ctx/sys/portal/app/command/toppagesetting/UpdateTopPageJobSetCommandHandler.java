package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Update/Insert data to table TOPPAGE_JOB_SET & TOPPAGE_SET
 * 
 * @author sonnh1
 *
 */

@Stateless
@Transactional
public class UpdateTopPageSettingCommandHandler extends CommandHandler<List<UpdateTopPageSettingCommand>> {

	@Inject
	TopPageJobSetRepository topPageJobSetRepo;

	@Override
	protected void handle(CommandHandlerContext<List<UpdateTopPageSettingCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<UpdateTopPageSettingCommand> command = context.getCommand();
		// get list jobId from List UpdateTopPageSettingCommand
		List<String> listJobId = command.stream().map(x -> x.getJobId()).collect(Collectors.toList());
		// find data in TOPPAGE_JOB_SET base on companyId and list jobId
		List<TopPageJobSet> listTopPageJobSet = topPageJobSetRepo.findByListJobId(companyId, listJobId);
		Map<String, TopPageJobSet> topPageJobMap = listTopPageJobSet.stream()
				.collect(Collectors.toMap(TopPageJobSet::getJobId, x -> x));
		for (UpdateTopPageSettingCommand updateTopPageSettingCommandObj : command) {
			TopPageJobSet topPageJobSet = topPageJobMap.get(updateTopPageSettingCommandObj.getJobId());
			TopPageJobSet TopPageJobSetObj = updateTopPageSettingCommandObj.toDomain(companyId);
			if (topPageJobSet == null) {
				topPageJobSetRepo.add(TopPageJobSetObj);
			} else {
				topPageJobSetRepo.update(TopPageJobSetObj);
			}
		}
	}
}
