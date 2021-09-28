package nts.uk.ctx.office.app.command.equipment.data;

import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.equipment.CheckValidUseDateDomainService;
import nts.uk.ctx.office.dom.equipment.CheckValidUseDateRequireImpl;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.achievement.repo.EquipmentRecordItemSettingRepository;
import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassificationCode;
import nts.uk.ctx.office.dom.equipment.data.ActualItemUsageValue;
import nts.uk.ctx.office.dom.equipment.data.EquipmentData;
import nts.uk.ctx.office.dom.equipment.data.EquipmentDataRepository;
import nts.uk.ctx.office.dom.equipment.data.EquipmentUsageCreationResultTemp;
import nts.uk.ctx.office.dom.equipment.data.ItemData.Require;
import nts.uk.ctx.office.dom.equipment.data.ItemDataRequireImpl;
import nts.uk.ctx.office.dom.equipment.information.EquipmentCode;
import nts.uk.ctx.office.dom.equipment.information.EquipmentInformationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.APP.利用実績の新規登録をする.利用実績の新規登録をする
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class InsertEquipmentDataCommandHandler extends CommandHandler<EquipmentDataCommand> {

	@Inject
	private EquipmentInformationRepository equipmentInformationRepository;

	@Inject
	private EquipmentDataRepository equipmentDataRepository;

	@Inject
	private EquipmentRecordItemSettingRepository equipmentRecordItemSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<EquipmentDataCommand> context) {
		Require require = new ItemDataRequireImpl(equipmentRecordItemSettingRepository);
		EquipmentDataCommand command = context.getCommand();
		EquipmentClassificationCode equipmentClsCode = new EquipmentClassificationCode(
				command.getEquipmentClassificationCode());
		EquipmentCode equipmentCode = new EquipmentCode(command.getEquipmentCode());

		// 1.有効期限内か(require, 設備コード, 利用日)
		boolean isUseDateValid = this.validateUseDate(equipmentCode, command.getUseDate());
		// 2.[有効期限内か＝false]
		if (!isUseDateValid) {
			throw new BusinessException("Msg_2233");
		}
		Map<EquipmentItemNo, ActualItemUsageValue> itemDataMap = command.getItemDatas().stream()
				.collect(Collectors.toMap(data -> new EquipmentItemNo(data.getItemNo()),
						data -> new ActualItemUsageValue(data.getActualValue())));
		// 3.新規登録(設備利用実績データ)
		EquipmentUsageCreationResultTemp temp = EquipmentData.createData(require, AppContexts.user().companyId(),
				equipmentClsCode, equipmentCode, command.getSid(), command.getUseDate(), itemDataMap);
		// 4.[設備利用実績作成Temp.エラーList.isEmpty]
		if (temp.getErrorMap().isEmpty()) {
			// persist(設備利用実績作成Temp.設備利用実績データ)
			temp.getEquipmentData().ifPresent(this.equipmentDataRepository::insert);
			return;
		}
		BundledBusinessException ex = BundledBusinessException.newInstance();
		temp.getErrorMap().forEach((k, v) -> ex.addMessage(v));
		throw ex;
	}

	private boolean validateUseDate(EquipmentCode equipmentCode, GeneralDate useDate) {
		String cid = AppContexts.user().companyId();
		CheckValidUseDateDomainService.Require require = new CheckValidUseDateRequireImpl(
				equipmentInformationRepository);
		return CheckValidUseDateDomainService.validate(require, cid, equipmentCode, useDate);
	}
}
