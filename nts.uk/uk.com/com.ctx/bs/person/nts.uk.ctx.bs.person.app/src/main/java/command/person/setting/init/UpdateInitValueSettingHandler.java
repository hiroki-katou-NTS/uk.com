package command.person.setting.init;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import command.person.setting.init.item.UpdateItemInitValueSettingCommand;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;

/**
 * The class UpdateInitValueSettingHandler
 * 
 * @author lanlt
 *
 */
@Stateless
public class UpdateInitValueSettingHandler extends CommandHandler<UpdateInitValueSettingCommand> {

	@Inject
	private PerInfoInitValSetCtgRepository ctgRepo;

	@Inject
	private PerInfoInitValueSetItemRepository itemRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateInitValueSettingCommand> context) {
		UpdateInitValueSettingCommand command = context.getCommand();
		Optional<PerInfoInitValSetCtg> ctgSetting = this.ctgRepo.getDetailInitValSetCtg(command.getSettingId(),
				command.getPerInfoCtgId());
		if (ctgSetting.isPresent()) {

		} else {
			List<UpdateItemInitValueSettingCommand> itemCommand = command.getItemLst();
			List<UpdateItemInitValueSettingCommand> itemNoSetting = itemCommand.stream()
					.filter(c -> (c.getSelectedRuleCode().equals("2"))).collect(Collectors.toList());
			if (itemCommand.size() == itemNoSetting.size()) {
				throw new BusinessException("Msg_454");
			} else {
				PerInfoInitValSetCtg ctgDomain = PerInfoInitValSetCtg.createFromJavaType(command.getSettingId(),
						command.getPerInfoCtgId());
				this.ctgRepo.add(ctgDomain);
				itemCommand.stream().forEach(c -> {
					if (c.getDataType() == 0) {
						PerInfoInitValueSetItem item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
								command.getSettingId(), command.getPerInfoCtgId(), Integer.valueOf(c.getSelectedRuleCode()),
								c.getSaveDataType(), c.getStringValue());
						this.itemRepo.addItem(item);

					} else if (c.getDataType() == 1) {
						// item =
						// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
						// command.getSettingId(), command.getPerInfoCtgId(),
						// c.getRefMethodType(),
						// c.getSaveDataType(), c.getStringValue());

					} else if (c.getDataType() == 2) {
						// item =
						// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
						// command.getSettingId(), command.getPerInfoCtgId(),
						// c.getRefMethodType(),
						// c.getSaveDataType(), c.getStringValue());

					} else if (c.getDataType() == 3) {
						// item =
						// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
						// command.getSettingId(), command.getPerInfoCtgId(),
						// c.getRefMethodType(),
						// c.getSaveDataType(), c.getStringValue());

					} else if (c.getDataType() == 4) {
						// item =
						// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
						// command.getSettingId(), command.getPerInfoCtgId(),
						// c.getRefMethodType(),
						// c.getSaveDataType(), c.getStringValue());

					} else if (c.getDataType() == 5) {
						// item =
						// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
						// command.getSettingId(), command.getPerInfoCtgId(),
						// c.getRefMethodType(),
						// c.getSaveDataType(), c.getStringValue());

					}

				});

			}
		}

	}

}
