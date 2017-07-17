package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.toppagesetting.CategorySetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Update/Insert data to table TOPPAGE_JOB_SET & TOPPAGE_SET
 * 
 * @author sonnh1
 *
 */

@Stateless
@Transactional
public class UpdateTopPageJobSetCommandHandler extends CommandHandler<TopPageJobSetBase> {

	@Inject
	private TopPageSettingRepository topPageSettingRepo;

	@Inject
	private TopPageJobSetRepository topPageJobSetRepo;

	@Override
	protected void handle(CommandHandlerContext<TopPageJobSetBase> context) {
		String companyId = AppContexts.user().companyId();
		TopPageJobSetBase command = context.getCommand();
		int categorySet = command.ctgSet;

		List<UpdateTopPageJobSetCommand> updateTopPageJobSetCommand = command.listTopPageJobSet;

		// get list jobId from List UpdateTopPageSettingCommand
		List<String> listJobId = updateTopPageJobSetCommand.stream().map(x -> x.getJobId())
				.collect(Collectors.toList());

		// find data in TOPPAGE_JOB_SET base on companyId and list jobId
		List<TopPageJobSet> listTopPageJobSet = topPageJobSetRepo.findByListJobId(companyId, listJobId);
		Map<String, TopPageJobSet> topPageJobMap = listTopPageJobSet.stream()
				.collect(Collectors.toMap(TopPageJobSet::getJobId, x -> x));
		for (UpdateTopPageJobSetCommand updateTopPageSettingCommandObj : updateTopPageJobSetCommand) {
			TopPageJobSet topPageJobSet = topPageJobMap.get(updateTopPageSettingCommandObj.getJobId());
			TopPageJobSet topPageJobSetObj = updateTopPageSettingCommandObj.toDomain(companyId);

			if (categorySet == CategorySetting.DIVIDE.value) {
				if (topPageJobSet == null) {
					topPageJobSetRepo.add(topPageJobSetObj);
				} else {
					topPageJobSetRepo.update(topPageJobSetObj);
				}
			} else {
				if (topPageJobSet == null) {
					// if category = NOTDIVEDE, loginMenuCd = topMenuCd, system
					// = COMMON, MenuCls = TOPPAGE
					TopPageJobSet tpJobSet = new TopPageJobSet(companyId, topPageJobSetObj.getTopMenuCode(),
							topPageJobSetObj.getTopMenuCode(), topPageJobSetObj.getJobId(),
							topPageJobSetObj.getPersonPermissionSet(), System.COMMON, MenuClassification.TopPage);
					topPageJobSetRepo.add(tpJobSet);
				} else {
					topPageJobSetRepo.updateProperty(topPageJobSetObj);
				}
			}
		}

		// insert/update category setting in to table TOPPAGE_SET
		Optional<TopPageSetting> topPageSetting = topPageSettingRepo.findByCId(companyId);
		if (topPageSetting.isPresent()) {
			topPageSettingRepo.update(TopPageSetting.createFromJavaType(companyId, categorySet));
		} else {
			topPageSettingRepo.insert(TopPageSetting.createFromJavaType(companyId, categorySet));
		}
	}
}
