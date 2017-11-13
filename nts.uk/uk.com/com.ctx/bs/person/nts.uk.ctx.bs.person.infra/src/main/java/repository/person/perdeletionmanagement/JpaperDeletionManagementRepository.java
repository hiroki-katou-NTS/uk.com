package repository.person.perdeletionmanagement;

import javax.ejb.Stateless;

import entity.person.perdeletionmanagement.Bsymtperinfdeletemng;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.deletemanagement.PersonDeleteManagement;
import nts.uk.ctx.bs.person.dom.person.perdeletionmanagement.IperDeletionManagementRepository;
@Stateless
public class JpaperDeletionManagementRepository extends JpaRepository implements IperDeletionManagementRepository {
	
	/** 
	 * Convert from domain to entity
	 * @param perDeletion
	 * @return
	 */
	private Bsymtperinfdeletemng toEntity(PersonDeleteManagement perDeletion){
		return new Bsymtperinfdeletemng(perDeletion.getRecordId(), perDeletion.getPersonInfoCtgId());
	}
	@Override
	public void addPerDeletionManagemanagement(PersonDeleteManagement perDeletion) {
		this.commandProxy().insert(toEntity(perDeletion));
	}

}
