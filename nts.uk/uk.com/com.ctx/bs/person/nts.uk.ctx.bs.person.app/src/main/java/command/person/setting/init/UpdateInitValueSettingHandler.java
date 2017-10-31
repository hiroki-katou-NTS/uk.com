package command.person.setting.init;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import command.person.setting.init.item.UpdateItemInitValueSettingCommand;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.IntValue;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.ReferenceMethodType;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.StringValue;

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

		if (!ctgSetting.isPresent()) {
			PerInfoInitValSetCtg ctgDomain = PerInfoInitValSetCtg.createFromJavaType(command.getSettingId(),
					command.getPerInfoCtgId());
			this.ctgRepo.add(ctgDomain);
		}

		List<UpdateItemInitValueSettingCommand> itemCommand = command.getItemLst();
		List<UpdateItemInitValueSettingCommand> itemNoSetting = itemCommand.stream()
				.filter(c -> (c.getSelectedRuleCode() == 2)).collect(Collectors.toList());
		if (itemNoSetting.size() == 0) {
			throw new BusinessException("Msg_454");
		} else {

			itemCommand.stream().forEach(c -> {
				PerInfoInitValueSetItem item = new PerInfoInitValueSetItem();

				Optional<PerInfoInitValueSetItem> itemExist = this.itemRepo.getDetailItem(command.getSettingId(),
						command.getPerInfoCtgId(), c.getPerInfoItemDefId());
				if (itemExist.isPresent()) {

					if (c.getSelectedRuleCode() == 2) {
						if (c.getDataType() == 0) {
							itemExist.get().setStringValue(new StringValue(c.getStringValue()));
							itemExist.get().setRefMethodType(EnumAdaptor
									.valueOf(Integer.valueOf(c.getSelectedRuleCode()), ReferenceMethodType.class));
							this.itemRepo.update(itemExist.get());

						} else if (c.getDataType() == 1) {
							itemExist.get().setRefMethodType(EnumAdaptor
									.valueOf(Integer.valueOf(c.getSelectedRuleCode()), ReferenceMethodType.class));
							itemExist.get().setIntValue(new IntValue(new BigDecimal(c.getIntValue())));
							itemExist.get().setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
							this.itemRepo.update(itemExist.get());

						} else if (c.getDataType() == 2) {
							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode(),
									c.getSaveDataType(), c.getDateVal());
							this.itemRepo.update(item);

						} else if (c.getDataType() == 3) {
							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode(),
									c.getSaveDataType(), c.getTimepoint());
							this.itemRepo.update(item);
						} else if (c.getDataType() == 4) {
							// PerInfoInitValueSetItem item =
							// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
							// command.getSettingId(), command.getPerInfoCtgId(),
							// c.getRefMethodType(),
							// c.getSaveDataType(), c.getTimepoint());
							// this.itemRepo.addItem(item);
						} else if (c.getDataType() == 5) {
							// item =
							// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
							// command.getSettingId(), command.getPerInfoCtgId(),
							// c.getRefMethodType(),
							// c.getSaveDataType(), c.getStringValue());

						}
					} else {

						item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
								command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode());
						this.itemRepo.update(item);

					}

				} else {
					if (c.getSelectedRuleCode() == 2) {
						if (c.getDataType() == 0) {
							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(),
									Integer.valueOf(c.getSelectedRuleCode()), c.getSaveDataType(), c.getStringValue());
							this.itemRepo.addItem(item);

						} else if (c.getDataType() == 1) {
							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode(),
									c.getSaveDataType(), Integer.valueOf(c.getIntValue()));
							this.itemRepo.addItem(item);

						} else if (c.getDataType() == 2) {
							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode(),
									c.getSaveDataType(), c.getDateVal());
							this.itemRepo.addItem(item);

						} else if (c.getDataType() == 3) {
							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode(),
									c.getSaveDataType(), c.getTimepoint());
							this.itemRepo.addItem(item);
						} else if (c.getDataType() == 4) {
							// PerInfoInitValueSetItem item =
							// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
							// command.getSettingId(), command.getPerInfoCtgId(),
							// c.getRefMethodType(),
							// c.getSaveDataType(), c.getTimepoint());
							// this.itemRepo.addItem(item);
						} else if (c.getDataType() == 5) {
							// item =
							// PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
							// command.getSettingId(), command.getPerInfoCtgId(),
							// c.getRefMethodType(),
							// c.getSaveDataType(), c.getStringValue());

						}

					} else {

						item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
								command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode());
						this.itemRepo.addItem(item);

					}

				}

			});

		}

	}

}
