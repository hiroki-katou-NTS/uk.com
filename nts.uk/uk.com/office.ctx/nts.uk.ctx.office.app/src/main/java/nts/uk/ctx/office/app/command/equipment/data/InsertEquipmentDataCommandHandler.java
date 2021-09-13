package nts.uk.ctx.office.app.command.equipment.data;

import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassificationCode;
import nts.uk.ctx.office.dom.equipment.data.ActualItemUsageValue;
import nts.uk.ctx.office.dom.equipment.data.EquipmentData;
import nts.uk.ctx.office.dom.equipment.data.EquipmentDataRepository;
import nts.uk.ctx.office.dom.equipment.data.EquipmentUsageCreationResultTemp;
import nts.uk.ctx.office.dom.equipment.data.ItemData.Require;
import nts.uk.ctx.office.dom.equipment.data.RequireImpl;
import nts.uk.ctx.office.dom.equipment.information.EquipmentCode;
import nts.uk.shr.com.context.AppContexts;

public class InsertEquipmentDataCommandHandler extends CommandHandler<EquipmentDataCommand> {
	
	@Inject
	private EquipmentDataRepository equipmentDataRepository;

	@Override
	protected void handle(CommandHandlerContext<EquipmentDataCommand> context) {
		Require require = new RequireImpl();
		EquipmentDataCommand command = context.getCommand();
		EquipmentClassificationCode equipmentClsCode = new EquipmentClassificationCode(
				command.getEquipmentClassificationCode());
		EquipmentCode equipmentCode = new EquipmentCode(command.getEquipmentCode());
		Map<EquipmentItemNo, ActualItemUsageValue> itemDataMap = command.getItemDatas().stream()
				.collect(Collectors.toMap(data -> new EquipmentItemNo(data.getItemNo()),
						data -> new ActualItemUsageValue(data.getActualValue())));
		// 1.新規登録(設備利用実績データ)
		EquipmentUsageCreationResultTemp temp = EquipmentData.createData(require, AppContexts.user().companyId(),
				equipmentClsCode, equipmentCode, command.getSid(), command.getUseDate(), itemDataMap);
		// 2.[設備利用実績作成Temp.エラーList.isEmpty]
		if (temp.getErrorMap().isEmpty()) {
			// persist(設備利用実績作成Temp.設備利用実績データ)
			temp.getEquipmentData().ifPresent(this.equipmentDataRepository::insert);
			return;
		}
		BundledBusinessException ex = BundledBusinessException.newInstance();
		temp.getErrorMap().forEach((k,v) -> ex.addMessage(v));
		throw ex;
	}

}
