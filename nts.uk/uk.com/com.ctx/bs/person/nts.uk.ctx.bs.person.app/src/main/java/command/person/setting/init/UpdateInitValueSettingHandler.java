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
import nts.arc.layer.ws.json.serializer.GeneralDateDeserializer;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.IntValue;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.ReferenceMethodType;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.StringValue;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class UpdateInitValueSettingHandler
 * 
 * @author lanlt
 *
 */
@Stateless
public class UpdateInitValueSettingHandler extends CommandHandler<UpdateInitValueSettingCommand> {

	@Inject
	private PerInfoInitValueSettingRepository settingRepo;
	@Inject
	private PerInfoInitValSetCtgRepository ctgRepo;

	@Inject
	private PerInfoInitValueSetItemRepository itemRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateInitValueSettingCommand> context) {

		UpdateInitValueSettingCommand command = context.getCommand();

		Optional<PerInfoInitValueSetting> setting = this.settingRepo.getDetailInitValSetting(command.getSettingId());

		Optional<PerInfoInitValSetCtg> ctgSetting = this.ctgRepo.getDetailInitValSetCtg(command.getSettingId(),
				command.getPerInfoCtgId());

		// add or update init item
		List<UpdateItemInitValueSettingCommand> itemCommand = command.getItemLst();

		List<UpdateItemInitValueSettingCommand> itemNoSetting = itemCommand.stream()
				.filter(c -> (c.getSelectedRuleCode() == 2)).collect(Collectors.toList());

		// update name of setting
		if (setting.isPresent()) {
			this.settingRepo.updateName(command.getSettingId(), command.getSettingName());
		}

		// nếu category chưa được thiết lập

		if (command.isSetting()) {

			if (itemNoSetting.size() == 0 || itemNoSetting == null) {
				itemCommand.stream().forEach(c -> {
					PerInfoInitValueSetItem item = new PerInfoInitValueSetItem();

					Optional<PerInfoInitValueSetItem> itemExist = this.itemRepo.getDetailItem(command.getSettingId(),
							command.getPerInfoCtgId(), c.getPerInfoItemDefId());
					if (itemExist.isPresent()) {

						item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
								command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode());
						this.itemRepo.update(item);

					} else {

						item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
								command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode());
						this.itemRepo.addItem(item);

					}

				});

			} else {
				itemCommand.stream().forEach(c -> {
					PerInfoInitValueSetItem item = new PerInfoInitValueSetItem();

					Optional<PerInfoInitValueSetItem> itemExist = this.itemRepo.getDetailItem(command.getSettingId(),
							command.getPerInfoCtgId(), c.getPerInfoItemDefId());
					if (itemExist.isPresent()) {

						if (c.getSelectedRuleCode() == 2) {
							if (c.getDataType() == 0 || c.getDataType() == 1) {
								itemExist.get().setStringValue(new StringValue(c.getStringValue()));
								itemExist.get().setRefMethodType(EnumAdaptor
										.valueOf(Integer.valueOf(c.getSelectedRuleCode()), ReferenceMethodType.class));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(1, SaveDataType.class));
								this.itemRepo.update(itemExist.get());

							} else if (c.getDataType() == 2) {
								itemExist.get().setRefMethodType(EnumAdaptor
										.valueOf(Integer.valueOf(c.getSelectedRuleCode()), ReferenceMethodType.class));
								itemExist.get().setIntValue(new IntValue(c.getNumberValue()));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								this.itemRepo.update(itemExist.get());

							} else if (c.getDataType() == 3) {
								if (c.getDateType() == 1) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode(),
											3, GeneralDate.fromString(c.getDateVal(), "yyyy/MM/dd"));
								} else if (c.getDateType() == 2) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode(),
											3, GeneralDate.fromString(c.getDateVal() + "/01", "yyyy/MM/dd"));

								} else if (c.getDateType() == 3) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode(),
											3, GeneralDate.fromString(c.getDateVal() + "/01/01", "yyyy/MM/dd"));

								}
								this.itemRepo.update(item);

							} else if (c.getDataType() == 4) {

								// time item

								itemExist.get().setRefMethodType(EnumAdaptor
										.valueOf(Integer.valueOf(c.getSelectedRuleCode()), ReferenceMethodType.class));
								itemExist.get().setIntValue(new IntValue(c.getTime()));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								this.itemRepo.update(itemExist.get());

							} else if (c.getDataType() == 5) {

								// time point
								itemExist.get().setRefMethodType(EnumAdaptor
										.valueOf(Integer.valueOf(c.getSelectedRuleCode()), ReferenceMethodType.class));
								itemExist.get().setIntValue(new IntValue(c.getTime()));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								this.itemRepo.update(itemExist.get());

							} else if (c.getDataType() == 6) {
								itemExist.get().setRefMethodType(EnumAdaptor
										.valueOf(Integer.valueOf(c.getSelectedRuleCode()), ReferenceMethodType.class));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(1, SaveDataType.class));
								itemExist.get().setStringValue(new StringValue(c.getSelectionId()));
								this.itemRepo.update(itemExist.get());

							}
						} else if (c.getSelectedRuleCode() > 2) {

							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode());
							this.itemRepo.update(item);

						}

					} else {
						if (c.getSelectedRuleCode() == 2) {
							if (c.getDataType() == 0 || c.getDataType() == 1) {
								item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
										command.getSettingId(), command.getPerInfoCtgId(),
										2, 1, c.getStringValue());
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 2) {
								item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
										command.getSettingId(), command.getPerInfoCtgId(), 2, 2,
										c.getNumberValue());
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 3) {
								if (c.getDateType() == 1) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), 2,
											3, GeneralDate.fromString(c.getDateVal(), "yyyy/MM/dd"));
								} else if (c.getDateType() == 2) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), 2,
											3, GeneralDate.fromString(c.getDateVal() + "/01", "yyyy/MM/dd"));

								} else if (c.getDateType() == 3) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), 2,
											3, GeneralDate.fromString(c.getDateVal() + "/01/01", "yyyy/MM/dd"));

								}
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 4) {
								// time item
								item.setPerInfoCtgId(command.getPerInfoCtgId());
								item.setPerInfoItemDefId(c.getPerInfoItemDefId());
								item.setRefMethodType(EnumAdaptor.valueOf(2, ReferenceMethodType.class));
								item.setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								item.setIntValue(new IntValue(c.getTime()));
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 5) {
								// time point
								item.setPerInfoCtgId(command.getPerInfoCtgId());
								item.setPerInfoItemDefId(c.getPerInfoItemDefId());
								item.setRefMethodType(EnumAdaptor.valueOf(2, ReferenceMethodType.class));
								item.setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								item.setIntValue(new IntValue(c.getTime()));
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 6) {
								// selection
								item.setPerInfoCtgId(command.getPerInfoCtgId());
								item.setPerInfoItemDefId(c.getPerInfoItemDefId());
								item.setRefMethodType(EnumAdaptor.valueOf(2, ReferenceMethodType.class));
								item.setSaveDataType(EnumAdaptor.valueOf(1, SaveDataType.class));
								item.setStringValue(new StringValue(c.getSelectionId()));

							}
						} else if (c.getSelectedRuleCode() > 2) {

							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode());
							this.itemRepo.addItem(item);

						}

					}

				});

			}

		} else {
			// add category in init category
			if (!ctgSetting.isPresent()) {
				PerInfoInitValSetCtg ctgDomain = PerInfoInitValSetCtg.createFromJavaType(command.getSettingId(),
						command.getPerInfoCtgId());
				this.ctgRepo.add(ctgDomain);
			}

			if (itemNoSetting.size() == 0) {
				throw new BusinessException("Msg_454");
			} else {

				itemCommand.stream().forEach(c -> {
					PerInfoInitValueSetItem item = new PerInfoInitValueSetItem();

					Optional<PerInfoInitValueSetItem> itemExist = this.itemRepo.getDetailItem(command.getSettingId(),
							command.getPerInfoCtgId(), c.getPerInfoItemDefId());
					if (itemExist.isPresent()) {

						if (c.getSelectedRuleCode() == 2) {
							if (c.getDataType() == 0 || c.getDataType() == 1) {
								itemExist.get().setStringValue(new StringValue(c.getStringValue()));
								itemExist.get().setRefMethodType(EnumAdaptor.valueOf(2, ReferenceMethodType.class));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(1, SaveDataType.class));
								this.itemRepo.update(itemExist.get());

							} else if (c.getDataType() == 2) {
								itemExist.get().setRefMethodType(EnumAdaptor.valueOf(2, ReferenceMethodType.class));
								itemExist.get().setIntValue(new IntValue(c.getNumberValue()));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								this.itemRepo.update(itemExist.get());

							} else if (c.getDataType() == 3) {
								if (c.getDateType() == 1) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), 2,
											3, GeneralDate.fromString(c.getDateVal(), "yyyy/MM/dd"));
								} else if (c.getDateType() == 2) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), 2,
											3, GeneralDate.fromString(c.getDateVal() + "/01", "yyyy/MM/dd"));

								} else if (c.getDateType() == 3) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(),2,
											3, GeneralDate.fromString(c.getDateVal() + "/01/01", "yyyy/MM/dd"));

								}
								this.itemRepo.update(item);

							} else if (c.getDataType() == 4) {

								// time item

								itemExist.get().setRefMethodType(EnumAdaptor.valueOf(2, ReferenceMethodType.class));
								itemExist.get().setIntValue(new IntValue(c.getTime()));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								this.itemRepo.update(itemExist.get());

							} else if (c.getDataType() == 5) {

								// time point
								itemExist.get().setRefMethodType(EnumAdaptor.valueOf(2, ReferenceMethodType.class));
								itemExist.get().setIntValue(new IntValue(c.getTime()));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								this.itemRepo.update(itemExist.get());

							} else if (c.getDataType() == 6) {
								itemExist.get().setRefMethodType(EnumAdaptor.valueOf(2, ReferenceMethodType.class));
								itemExist.get().setSaveDataType(EnumAdaptor.valueOf(1, SaveDataType.class));
								itemExist.get().setStringValue(new StringValue(c.getSelectionId()));
								this.itemRepo.update(itemExist.get());

							}
						} else if (c.getSelectedRuleCode() > 2) {

							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode());
							this.itemRepo.update(item);

						}

					} else {
						if (c.getSelectedRuleCode() == 2) {
							if (c.getDataType() == 0 || c.getDataType() == 1) {
								item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
										command.getSettingId(), command.getPerInfoCtgId(),
										2 , 1, c.getStringValue());
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 2) {
								item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
										command.getSettingId(), command.getPerInfoCtgId(), 2, 2,
										c.getNumberValue());
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 3) {
								if (c.getDateType() == 1) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(),2,
											3, GeneralDate.fromString(c.getDateVal(), "yyyy/MM/dd"));
								} else if (c.getDateType() == 2) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), 2,
											3, GeneralDate.fromString(c.getDateVal() + "/01", "yyyy/MM/dd"));

								} else if (c.getDateType() == 3) {
									item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
											command.getSettingId(), command.getPerInfoCtgId(), 2,
											3, GeneralDate.fromString(c.getDateVal() + "/01/01", "yyyy/MM/dd"));

								}
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 4) {
								// time item
								item.setSettingId(command.getSettingId());
								item.setPerInfoCtgId(command.getPerInfoCtgId());
								item.setPerInfoItemDefId(c.getPerInfoItemDefId());
								item.setRefMethodType(EnumAdaptor.valueOf(2,
										ReferenceMethodType.class));
								item.setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								item.setIntValue(new IntValue(c.getTime()));
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 5) {
								// time point
								item.setSettingId(command.getSettingId());
								item.setPerInfoCtgId(command.getPerInfoCtgId());
								item.setPerInfoItemDefId(c.getPerInfoItemDefId());
								item.setRefMethodType(EnumAdaptor.valueOf(2,ReferenceMethodType.class));
								item.setSaveDataType(EnumAdaptor.valueOf(2, SaveDataType.class));
								item.setIntValue(new IntValue(c.getTime()));
								this.itemRepo.addItem(item);

							} else if (c.getDataType() == 6) {
								// selection
								item.setSettingId(command.getSettingId());
								item.setPerInfoCtgId(command.getPerInfoCtgId());
								item.setPerInfoItemDefId(c.getPerInfoItemDefId());
								item.setRefMethodType(EnumAdaptor.valueOf(2,ReferenceMethodType.class));
								item.setSaveDataType(EnumAdaptor.valueOf(1, SaveDataType.class));
								item.setStringValue(new StringValue(c.getSelectionId()));
								this.itemRepo.update(item);

							}
						} else if (c.getSelectedRuleCode() > 2){

							item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(),
									command.getSettingId(), command.getPerInfoCtgId(), c.getSelectedRuleCode());
							this.itemRepo.addItem(item);

						}

					}

				});

			}

		}

	}

}
