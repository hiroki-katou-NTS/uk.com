/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.setting.matrix.personinfomatrixitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItem;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo;
import nts.uk.ctx.pereg.infra.entity.person.setting.matrix.personinfomatrixitem.PpestPersonInfoMatrixItem;
import nts.uk.ctx.pereg.infra.entity.person.setting.matrix.personinfomatrixitem.PpestPersonInfoMatrixItemPK;

/**
 * @author hieult
 *
 */
@Stateless
public class JpaPersonInfoMatrixItem extends JpaRepository implements PersonInfoMatrixItemRepo {

	private static final String SELECT_BY_KEY = "SELECT c FROM PpestPersonInfoMatrixItem c WHERE c.ppestPersonInfoMatrixItemPK.pInfoCategoryID = :pInfoCategoryID"
			+ " AND c.pInfoDefiID = :pInfoDefiID";
	
	private static final String SELECT_BY_CATEGORY_ID= "SELECT c FROM PpestPersonInfoMatrixItem c WHERE c.ppestPersonInfoMatrixItemPK.pInfoCategoryID = :pInfoCategoryID";
			
	
	@Override
	public Optional<PersonInfoMatrixItem> findbyKey(String pInfoCategoryID, String pInfoDefiID) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_BY_KEY, PpestPersonInfoMatrixItem.class)
				.setParameter("pInfoCategoryID", pInfoCategoryID)
				.setParameter("pInfoDefiID", pInfoDefiID)
				.getSingle(c -> c.toDomain());
	}

	
	@Override
	public void update(PersonInfoMatrixItem newSetting) {
		
		PpestPersonInfoMatrixItem newEntity = PpestPersonInfoMatrixItem.toEntity(newSetting);
		PpestPersonInfoMatrixItem updateEntity = this.queryProxy().find(newEntity.ppestPersonInfoMatrixItemPK,PpestPersonInfoMatrixItem.class).get();
			updateEntity.columnWidth = newEntity.columnWidth;
			updateEntity.regulationATR   = newEntity.regulationATR;
			this.commandProxy().update(updateEntity);
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo#findByCategoryID(java.lang.String)
	 */
	@Override
	public List<PersonInfoMatrixItem> findByCategoryID(String pInfoCategoryID) {
		
		return this.queryProxy().query(SELECT_BY_CATEGORY_ID, PpestPersonInfoMatrixItem.class)
				.setParameter("pInfoCategoryID", pInfoCategoryID)
				.getList(c -> c.toDomain());
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo#insert(nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItem)
	 */
	@Override
	public void insert(PersonInfoMatrixItem newSetting) {
		PpestPersonInfoMatrixItem newEntity = PpestPersonInfoMatrixItem.toEntity(newSetting);
		Optional<PpestPersonInfoMatrixItem> updateEntity = this.queryProxy().find(newEntity.ppestPersonInfoMatrixItemPK,PpestPersonInfoMatrixItem.class);
		if (!updateEntity.isPresent()) {
			this.commandProxy().insert(PpestPersonInfoMatrixItem.toEntity(newSetting));
		} else {
			this.commandProxy().update(updateEntity);
		}
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo#insertAll(java.util.List)
	 */
	@Override
	public void insertAll(List<PersonInfoMatrixItem> listNewSetting) {
		List<PpestPersonInfoMatrixItem> listEntity = listNewSetting.stream().map( c -> toEntity(c)).collect(Collectors.toList());
		commandProxy().insertAll(listEntity);
	}

	private PpestPersonInfoMatrixItem toEntity(PersonInfoMatrixItem domain) {
		PpestPersonInfoMatrixItemPK pk = new PpestPersonInfoMatrixItemPK(domain.getPInfoCategoryID(),domain.getPInfoItemDefiID());
		return new PpestPersonInfoMatrixItem(pk,
				domain.getColumnWidth(),
				domain.getRegulationATR().value
				);
	}
	
	

}
