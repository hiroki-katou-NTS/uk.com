package nts.uk.ctx.office.dom.equipment;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.office.dom.equipment.CheckValidUseDateDomainService.Require;
import nts.uk.ctx.office.dom.equipment.information.EquipmentInformation;
import nts.uk.ctx.office.dom.equipment.information.EquipmentInformationRepository;

@AllArgsConstructor
public class CheckValidUseDateRequireImpl implements Require {
	
	private EquipmentInformationRepository equipmentInformationRepository;

	@Override
	public Optional<EquipmentInformation> getEquipmentInfo(String cid, String equipmentCode) {
		return this.equipmentInformationRepository.findByPk(cid, equipmentCode);
	}
}
