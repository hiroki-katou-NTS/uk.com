package nts.uk.ctx.office.app.command.equipment.data;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.data.ActualItemUsageValue;
import nts.uk.ctx.office.dom.equipment.data.EquipmentData;
import nts.uk.ctx.office.dom.equipment.data.EquipmentDataRepository;
import nts.uk.ctx.office.dom.equipment.data.EquipmentUsageCreationResultTemp;
import nts.uk.ctx.office.dom.equipment.data.ItemData.Require;
import nts.uk.ctx.office.dom.equipment.data.RequireImpl;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.APP.利用実績の更新をする.利用実績の更新をする
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateEquipmentDataCommandHandler extends CommandHandler<EquipmentDataCommand> {

	@Inject
	private EquipmentDataRepository equipmentDataRepository;

	@Override
	protected void handle(CommandHandlerContext<EquipmentDataCommand> context) {
		String cid = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();

		Require require = new RequireImpl();
		EquipmentDataCommand command = context.getCommand();
		// 1.get(設備コード、ログイン社員ID、利用日、入力日)
		Optional<EquipmentData> optEquipmentData = this.equipmentDataRepository.findByUsageInfo(cid,
				command.getEquipmentCode(), command.getUseDate(), sid, command.getInputDate());
		optEquipmentData.ifPresent(equipmentData -> {
			Map<EquipmentItemNo, ActualItemUsageValue> itemDataMap = command.getItemDatas().stream()
					.collect(Collectors.toMap(data -> new EquipmentItemNo(data.getItemNo()),
							data -> new ActualItemUsageValue(data.getActualValue())));
			// 2.変更登録(require, 項目データList)
			EquipmentUsageCreationResultTemp temp = equipmentData.updateItemDatas(require, cid, itemDataMap);
			// 3.[設備利用実績作成Temp.エラーList.isEmpty]
			if (temp.getErrorMap().isEmpty()) {
				// persist(設備利用実績作成Temp.設備利用実績データ)
				temp.getEquipmentData().ifPresent(this.equipmentDataRepository::update);
				return;
			}
			BundledBusinessException ex = BundledBusinessException.newInstance();
			temp.getErrorMap().forEach((k,v) -> ex.addMessage(v));
			throw ex;
		});
	}

}
