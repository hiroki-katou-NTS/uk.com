/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.setting.matrix.personinfomatrixitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixData;
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
	
	private static final String SELECT_DATA_INFO = String.join(" ", "SELECT",
			"pii.PER_INFO_ITEM_DEFINITION_ID, pii.ITEM_CD, pii.ITEM_NAME, pim.REGULATION_ATR, pio.DISPORDER",
			  "FROM [OOTSUKATEST].[dbo].[PPEMT_PER_INFO_ITEM] pii",
			  "LEFT JOIN [OOTSUKATEST].[dbo].[PPEST_PERSON_INFO_MATRIX] pim",
			  "ON pii.PER_INFO_ITEM_DEFINITION_ID = pim.PERSON_INFO_ITEM_ID",
			  "AND pii.PER_INFO_CTG_ID = pim.PERSON_INFO_CATEGORY_ID",
			  "LEFT JOIN [OOTSUKATEST].[dbo].[PPEMT_PER_INFO_ITEM_ORDER] pio",
			  "ON pio.PER_INFO_CTG_ID = pii.PER_INFO_CTG_ID",
			  "AND pio.PER_INFO_ITEM_DEFINITION_ID = pii.PER_INFO_ITEM_DEFINITION_ID",
			  "WHERE pii.PER_INFO_CTG_ID = ?",
			  "AND pii.ABOLITION_ATR = 0");
			
	
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


	/* (non-Javadoc)
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo#findInfoData(java.lang.String, int)
	 */
	@Override
	public List<PersonInfoMatrixData> findInfoData(String pInfoCategoryID, int isAbolition) {
		List<PersonInfoMatrixData> data = new ArrayList<>();
		if(pInfoCategoryID.isEmpty()){
			return new ArrayList<>();
		}

		@SuppressWarnings("unchecked")
		List<Object[]> result = this.getEntityManager().createNativeQuery(SELECT_DATA_INFO)
		.setParameter(1, pInfoCategoryID)
		.getResultList();
		
		data.addAll(result.stream().map(m -> {
			return new PersonInfoMatrixData (
					m[0] !=null ? m[0].toString() : "",//perInfoItemDefID
					m[1] !=null ? m[1].toString() : "", //itemCD
					m[2] !=null ? m[2].toString() : "", //itemName
					m[3] !=null ? m[3].hashCode() : '0', //regulationATR
					m[4] !=null ? m[4].hashCode() : '0'//dispOrder
														);
		}).collect(Collectors.toList()));
	
		return data;
	}
	
	

}
