package nts.uk.ctx.sys.portal.app.command.webmenu.jobtitletying;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTying;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTyingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateJobTitleTyingCommandHandler extends CommandHandler<UpdateJobTitleTyingCommand> {
	@Inject
	private JobTitleTyingRepository jobTitleTyingRepository;

	/**
	 * remove and insert job title tying
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateJobTitleTyingCommand> context) {
		String companyId = AppContexts.user().companyId();
		List<JobTitleTyingCommand> jobTitleTyings = context.getCommand().getJobTitleTyings();

		List<String> listJobId = jobTitleTyings.stream().map(x -> {
			return x.getJobId();
		}).collect(Collectors.toList());

		List<JobTitleTying> listJobTitleTying = jobTitleTyings.stream().map(c -> {
			return JobTitleTying.createFromJavaType(companyId, c.getJobId(), c.getWebMenuCode());
		}).collect(Collectors.toList());

		if (listJobTitleTying.size() == 0) {
			return;
		}
		jobTitleTyingRepository.removeByListJobId(companyId, listJobId);
		 jobTitleTyingRepository.insertMenuCode(listJobTitleTying);
	}
}
