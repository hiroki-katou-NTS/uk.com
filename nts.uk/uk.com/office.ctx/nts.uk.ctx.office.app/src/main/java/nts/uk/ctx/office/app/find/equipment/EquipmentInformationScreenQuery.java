package nts.uk.ctx.office.app.find.equipment;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.office.dom.equipment.EquipmentInformation;
import nts.uk.ctx.office.dom.equipment.EquipmentInformationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.オフィス.OEM_設備マスタ.OEM002_設備の登録.A：設備の登録.メニュー別OCD.設備一覧を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EquipmentInformationScreenQuery {

	@Inject
	private EquipmentInformationRepository equipmentInformationRepository;
	
	//TODO: Inject EqCls
	
	public EquipmentInformationStartupDto getStartupList() {
		// 1. get*(ログイン会社ID)
		List<EquipmentInformation> equipmentInformations = this.equipmentInformationRepository
				.findByCid(AppContexts.user().companyId());
		// 2. 設備情報. isPresent
		if (!equipmentInformations.isEmpty()) {
			// TODO: get(契約コード、List<設備分類コード>)
			
		}
		return new EquipmentInformationStartupDto(
				equipmentInformations.stream()
					.map(EquipmentInformationDto::fromDomain).collect(Collectors.toList()));
	}
}
