package nts.uk.ctx.office.dom.equipment.information;

import java.util.List;
import java.util.Optional;

public interface EquipmentInformationRepository {
	
	//[1] Insert(設備情報)																							
	void insert(EquipmentInformation domain);
	
	//[2] Update(設備情報)																							
	void update(EquipmentInformation domain);
	
	//[3] Delete(会社ID, 設備コード)																							
	void delete(String cid, String code);
	
	//[4] Get*																							
	List<EquipmentInformation> findByCid(String cid);
	
	//[5] Get																							
	Optional<EquipmentInformation> findByPk(String cid, String code);
}
