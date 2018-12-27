package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.dom.toppagesetting.CategorySetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Update/Insert data in table TOPPAGE_PERSON_SET and TOPPAGE_SET
 * 
 * @author sonnh1
 *
 */
@Stateless
@Transactional
public class UpdateTopPagePersonSetCommandHandler extends CommandHandler<TopPagePersonSetCommandBase> {

	@Inject
	private TopPagePersonSetRepository topPagePersonSetRepo;

	@Inject
	private TopPageSettingRepository topPageSettingRepo;

	@Override
	protected void handle(CommandHandlerContext<TopPagePersonSetCommandBase> context) {
		String companyId = AppContexts.user().companyId();
		TopPagePersonSetCommandBase command = context.getCommand();
		TopPagePersonSet topPagePersonSet = command.toDomain(companyId);
		if(command.getCtgSet() == CategorySetting.NOT_DIVIDE.value && StringUtil.isNullOrEmpty(command.getTopMenuCode(), true)){
			throw new BusinessException("Msg_86");
		}
		if (command.getCtgSet() == CategorySetting.DIVIDE.value && StringUtil.isNullOrEmpty(command.getLoginMenuCode(), true) && StringUtil.isNullOrEmpty(command.getTopMenuCode(), true)) {
			throw new BusinessException("Msg_86");
		}
		// Check for existing data in the TOPPAGE_PERSON_SET table
		if (topPagePersonSetRepo.getbyCode(companyId, command.getSId()).isPresent()) {
			topPagePersonSetRepo.update(topPagePersonSet);
		} else {
			topPagePersonSetRepo.add(topPagePersonSet);
		}

		// insert/update category setting in to table TOPPAGE_SET
		Optional<TopPageSetting> topPageSetting = topPageSettingRepo.findByCId(companyId);
		if (topPageSetting.isPresent()) {
			topPageSettingRepo.update(TopPageSetting.createFromJavaType(companyId, command.getCtgSet()));
		} else {
			topPageSettingRepo.insert(TopPageSetting.createFromJavaType(companyId, command.getCtgSet()));
		}
	}

}
