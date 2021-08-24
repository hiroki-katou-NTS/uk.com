package nts.uk.ctx.office.app.query.equipment.classificationmaster;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassification;
import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassificationRepository;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備分類マスタ.APP.設備分類一覧を取得する
 * @author NWS-DungDV
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EquipmentClassificationListQuery {
	
	@Inject
	private EquipmentClassificationRepository equipmentClsRepo;
	
	public List<EquipmentClassification> get(String contractCd) {
		return this.equipmentClsRepo.getAll(contractCd);
	}
}
