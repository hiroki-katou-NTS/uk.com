package nts.uk.ctx.pereg.app.command.person.setting.init;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.person.setting.init.item.UpdateItemInitValueSettingCommand;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateType;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.item.ReferenceMethodType;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;

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
		List<UpdateItemInitValueSettingCommand> itemSetting = itemCommand.stream()
				.filter(c -> (c.getSelectedRuleCode() != ReferenceMethodType.NOSETTING.value))
				.collect(Collectors.toList());
		BundledBusinessException exceptions = BundledBusinessException.newInstance();
		exceptions = this.businessList(itemSetting);
		// Has error, throws message
		if (exceptions.cloneExceptions().size() > 0) {
			exceptions.throwExceptions();
		}
		// update name of setting
		if (setting.isPresent()) {
			this.settingRepo.updateName(command.getSettingId(), command.getSettingName());
		}
		// nếu category chưa được thiết lập
		if (command.isSetting()) {
			if (itemSetting.size() == 0 || itemSetting == null) {
				this.ctgRepo.delete(command.getPerInfoCtgId(), command.getSettingId());
				itemCommand.stream().forEach(c -> {
					Optional<PerInfoInitValueSetItem> itemCheck = this.itemRepo.getDetailItem(command.getSettingId(),
							command.getPerInfoCtgId(), c.getPerInfoItemDefId());
					if (itemCheck.isPresent()) {
						this.itemRepo.delete(c.getPerInfoItemDefId(), command.getPerInfoCtgId(),
								command.getSettingId());
					}
				});

			} else {
				this.processItem(command);
			}
		} else {
			// add category in init category
			if (!ctgSetting.isPresent()) {
				PerInfoInitValSetCtg ctgDomain = PerInfoInitValSetCtg.createFromJavaType(command.getSettingId(),
						command.getPerInfoCtgId());
				this.ctgRepo.add(ctgDomain);
			}
			if (itemSetting.size() == 0) {
				throw new BusinessException("Msg_454");
			} else {
				this.processItem(command);
			}

		}

	}

	private void processItem(UpdateInitValueSettingCommand command) {
		List<UpdateItemInitValueSettingCommand> itemCommand = command.getItemLst();
		itemCommand.stream().forEach(c -> {
			PerInfoInitValueSetItem item = new PerInfoInitValueSetItem();
			this.saveDataTypeCase(c, command, item);
		});

	}

	private GeneralDate compareDate(UpdateItemInitValueSettingCommand c, GeneralDate date) {
		if (c.getDateType() == DateType.YEARMONTHDAY.value) {
			date = GeneralDate.fromString(c.getDateVal(), "yyyy/MM/dd");
		} else if (c.getDateType() == DateType.YEARMONTH.value) {
			date =   GeneralDate.fromString(c.getDateVal() + "/01", "yyyy/MM/dd");
		} else if (c.getDateType() == DateType.YEAR.value) {
			date =  GeneralDate.fromString(c.getDateVal() + "/01/01", "yyyy/MM/dd");
		}
		return date;
	}

	private PerInfoInitValueSetItem createItem(UpdateItemInitValueSettingCommand c,
			UpdateInitValueSettingCommand command, PerInfoInitValueSetItem item) {
		if (c.getDataType() == DataTypeValue.STRING.value) {
			item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(), command.getSettingId(),
					command.getPerInfoCtgId(), ReferenceMethodType.FIXEDVALUE.value, DataTypeValue.STRING.value,
					c.getStringValue());

		} else if (c.getDataType() == DataTypeValue.NUMERIC.value) {
			item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(), command.getSettingId(),
					command.getPerInfoCtgId(), ReferenceMethodType.FIXEDVALUE.value, DataTypeValue.NUMERIC.value,
					c.getNumberValue());
		} else if (c.getDataType() == DataTypeValue.DATE.value) {
			GeneralDate date = null;
			
			date = this.compareDate(c, date);
			if(date != null) {
			item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(), command.getSettingId(),
					command.getPerInfoCtgId(), ReferenceMethodType.FIXEDVALUE.value, DataTypeValue.DATE.value, date);
			}

		} else if (c.getDataType() == DataTypeValue.TIME.value || c.getDataType() == DataTypeValue.TIMEPOINT.value) {
			// time , time point
			item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(), command.getSettingId(),
					command.getPerInfoCtgId(), ReferenceMethodType.FIXEDVALUE.value, SaveDataType.NUMBERIC.value,
					new BigDecimal(c.getIntValue()));

		} else if (c.getDataType() == DataTypeValue.SELECTION.value
				|| c.getDataType() == DataTypeValue.SELECTION_RADIO.value
				|| c.getDataType() == DataTypeValue.SELECTION_BUTTON.value) {
			// selection
			item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(), command.getSettingId(),
					command.getPerInfoCtgId(), ReferenceMethodType.FIXEDVALUE.value, DataTypeValue.STRING.value,
					c.getSelectionId());
		}
		return item;

	}

	private void saveDataTypeCase(UpdateItemInitValueSettingCommand c, UpdateInitValueSettingCommand command,
			PerInfoInitValueSetItem item) {
		if (c.getSelectedRuleCode() == ReferenceMethodType.FIXEDVALUE.value) {
			item = this.createItem(c, command, item);
			if (item.getSettingId() != null) {
				this.itemRepo.update(item);
			}
		} else if (c.getSelectedRuleCode() > ReferenceMethodType.FIXEDVALUE.value) {
			item = PerInfoInitValueSetItem.convertFromJavaType(c.getPerInfoItemDefId(), command.getSettingId(),
					command.getPerInfoCtgId(), c.getSelectedRuleCode());
			this.itemRepo.update(item);
		} else if (c.getSelectedRuleCode() == ReferenceMethodType.NOSETTING.value) {
			Optional<PerInfoInitValueSetItem> itemCheck = this.itemRepo.getDetailItem(command.getSettingId(),
					command.getPerInfoCtgId(), c.getPerInfoItemDefId());
			if (itemCheck.isPresent()) {
				this.itemRepo.delete(c.getPerInfoItemDefId(), command.getPerInfoCtgId(), command.getSettingId());
			}
		}

	}

	private BundledBusinessException businessList(List<UpdateItemInitValueSettingCommand> itemSetting) {
		BundledBusinessException exceptions = BundledBusinessException.newInstance();
		for (UpdateItemInitValueSettingCommand c : itemSetting) {
			if (c.getSelectedRuleCode() == ReferenceMethodType.FIXEDVALUE.value) {
				if (c.getDataType() == DataTypeValue.STRING.value
						&& (c.getStringValue().equals(null) || c.getStringValue().equals(""))) {
					BusinessException message = new BusinessException("Msg_824", c.getItemName());
					message.setSuppliment("NameID", c.getItemName());
					exceptions.addMessage(message);
				} else if (c.getDataType() == DataTypeValue.NUMERIC.value && c.getNumberValue() == null) {
					BusinessException message = new BusinessException("Msg_824", c.getItemName());
					message.setSuppliment("NameID", c.getItemName());
					exceptions.addMessage(message);
				} else if (c.getDataType() == DataTypeValue.DATE.value && (c.getDateVal() == null)) {
					BusinessException message = new BusinessException("Msg_824", c.getItemName());
					message.setSuppliment("NameID", c.getItemName());
					exceptions.addMessage(message);
				} else if (c.getDataType() == DataTypeValue.TIME.value && c.getTime() == null) {
					BusinessException message = new BusinessException("Msg_824", c.getItemName());
					message.setSuppliment("NameID", c.getItemName());
					exceptions.addMessage(message);
				} else if (c.getDataType() == DataTypeValue.TIMEPOINT.value && c.getTime() == null) {
					BusinessException message = new BusinessException("Msg_824", c.getItemName());
					message.setSuppliment("NameID", c.getItemName());
					exceptions.addMessage(message);
				}
			}
		}
		return exceptions;

	}

}
