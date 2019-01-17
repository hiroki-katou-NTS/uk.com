/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.setting.matrix.personinfomatrixitem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixData;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItem;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo;
import nts.uk.ctx.pereg.infra.entity.person.setting.matrix.personinfomatrixitem.PpestPersonInfoMatrixItem;
import nts.uk.ctx.pereg.infra.entity.person.setting.matrix.personinfomatrixitem.PpestPersonInfoMatrixItemPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hieult
 *
 */
@Stateless
public class JpaPersonInfoMatrixItem extends JpaRepository implements PersonInfoMatrixItemRepo {

	private static final String SELECT_BY_KEY = "SELECT c FROM PpestPersonInfoMatrixItem c WHERE c.ppestPersonInfoMatrixItemPK.pInfoCategoryID = :pInfoCategoryID"
			+ " AND c.pInfoDefiID = :pInfoDefiID";

	private static final String SELECT_BY_CATEGORY_ID = "SELECT c FROM PpestPersonInfoMatrixItem c WHERE c.ppestPersonInfoMatrixItemPK.pInfoCategoryID = :pInfoCategoryID";

	private static final String SELECT_DATA_INFO = String.join(" ",
			"SELECT pii.PER_INFO_ITEM_DEFINITION_ID, pii.ITEM_CD, icm.ITEM_PARENT_CD, pii.ITEM_NAME, pim.REGULATION_ATR, pio.DISPORDER, pim.COLUMN_WIDTH, pii.REQUIRED_ATR",
			"FROM [dbo].[PPEMT_PER_INFO_ITEM] pii",
			"LEFT JOIN [dbo].[PPEMT_PER_INFO_CTG] ctg", "ON pii.PER_INFO_CTG_ID = ctg.PER_INFO_CTG_ID",
			"LEFT JOIN [dbo].[PPEMT_PER_INFO_CTG_CM] ctm",
			"ON ctg.CATEGORY_CD = ctm.CATEGORY_CD AND ctg.CID = ?",
			"LEFT JOIN [dbo].[PPEMT_PER_INFO_ITEM_CM] icm",
			"ON icm.CATEGORY_CD = ctg.CATEGORY_CD AND icm.ITEM_CD = pii.ITEM_CD",
			"LEFT JOIN [dbo].[PPEST_PERSON_INFO_MATRIX] pim",
			"ON pii.PER_INFO_ITEM_DEFINITION_ID = pim.PERSON_INFO_ITEM_ID",
			"AND pii.PER_INFO_CTG_ID = pim.PERSON_INFO_CATEGORY_ID",
			"LEFT JOIN [dbo].[PPEMT_PER_INFO_ITEM_ORDER] pio",
			"ON pio.PER_INFO_CTG_ID = pii.PER_INFO_CTG_ID",
			"AND pio.PER_INFO_ITEM_DEFINITION_ID = pii.PER_INFO_ITEM_DEFINITION_ID", "WHERE pii.PER_INFO_CTG_ID = ?",
			"AND pii.ABOLITION_ATR = 0");

	@Override
	public Optional<PersonInfoMatrixItem> findbyKey(String pInfoCategoryID, String pInfoDefiID) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_BY_KEY, PpestPersonInfoMatrixItem.class)
				.setParameter("pInfoCategoryID", pInfoCategoryID).setParameter("pInfoDefiID", pInfoDefiID)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void update(PersonInfoMatrixItem newSetting) {
		PpestPersonInfoMatrixItem newEntity = PpestPersonInfoMatrixItem.toEntity(newSetting);
		PpestPersonInfoMatrixItem updateEntity = this.queryProxy()
				.find(newEntity.ppestPersonInfoMatrixItemPK, PpestPersonInfoMatrixItem.class).get();
		
		updateEntity.columnWidth = newEntity.columnWidth;
		updateEntity.regulationATR = newEntity.regulationATR;
		
		this.commandProxy().update(updateEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.
	 * PersonInfoMatrixItemRepo#findByCategoryID(java.lang.String)
	 */
	@Override
	public List<PersonInfoMatrixItem> findByCategoryID(String pInfoCategoryID) {

		return this.queryProxy().query(SELECT_BY_CATEGORY_ID, PpestPersonInfoMatrixItem.class)
				.setParameter("pInfoCategoryID", pInfoCategoryID).getList(c -> c.toDomain());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.
	 * PersonInfoMatrixItemRepo#insert(nts.uk.ctx.pereg.dom.person.setting.matrix.
	 * personinfomatrixitem.PersonInfoMatrixItem)
	 */
	@Override
	public void save(PersonInfoMatrixItem newSetting) {
		PpestPersonInfoMatrixItem entity = toEntity(newSetting);
		PpestPersonInfoMatrixItemPK imiKey = entity.ppestPersonInfoMatrixItemPK;

		Optional<PpestPersonInfoMatrixItem> updateEntity = this.queryProxy().find(imiKey,
				PpestPersonInfoMatrixItem.class);

		if (!updateEntity.isPresent()) {
			commandProxy().insert(entity);
		} else {
			PpestPersonInfoMatrixItem _update = updateEntity.get();

			_update.columnWidth = entity.columnWidth;
			_update.regulationATR = entity.regulationATR;

			commandProxy().update(_update);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.
	 * PersonInfoMatrixItemRepo#insertAll(java.util.List)
	 */
	@Override
	public void insertAll(List<PersonInfoMatrixItem> listNewSetting) {
		listNewSetting.stream().forEach(domain -> save(domain));
	}

	private PpestPersonInfoMatrixItem toEntity(PersonInfoMatrixItem domain) {
		PpestPersonInfoMatrixItemPK pk = new PpestPersonInfoMatrixItemPK(domain.getPInfoCategoryID(),
				domain.getPInfoItemDefiID());
		return new PpestPersonInfoMatrixItem(pk, domain.getColumnWidth(), domain.getRegulationATR().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.
	 * PersonInfoMatrixItemRepo#findInfoData(java.lang.String, int)
	 */
	@Override
	public List<PersonInfoMatrixData> findInfoData(String pInfoCategoryID) {
		String cid = AppContexts.user().companyId();
		if (pInfoCategoryID.isEmpty()) {
			return new ArrayList<>();
		}

		@SuppressWarnings("unchecked")
		List<Object[]> result = this.getEntityManager()
			.createNativeQuery(SELECT_DATA_INFO)
				.setParameter(1, cid)
				.setParameter(2, pInfoCategoryID)
			.getResultList();

		List<PersonInfoMatrixData> returnData = result.stream().map(m -> new PersonInfoMatrixData(
				m[0] != null ? (String) m[0] : "", // perInfoItemDefID
				m[1] != null ? (String) m[1] : "", // itemCD
				m[2] != null ? (String) m[2] : "", // itemParentCD
				m[3] != null ? (String) m[3] : "", // itemName
				m[4] != null ? ((BigDecimal) m[4]).intValue() == 1 : false, // regulationATR
				m[5] != null ? ((BigDecimal) m[5]).intValue() : 0, // dispOrder
				m[6] != null ? ((BigDecimal) m[6]).intValue() : 100, // width
				m[7] != null ? ((BigDecimal) m[7]).intValue() == 1 : false // required
		)).sorted((o1, o2) -> o1.getDispOrder() - o2.getDispOrder()).collect(Collectors.toList());
		
		return updateRegular(returnData);
	}
	
	/**
	 * Update regular (first load) for all required item
	 * @param items
	 * @return
	 */
	private List<PersonInfoMatrixData> updateRegular(List<PersonInfoMatrixData> items) {
		items.stream().forEach(f -> {
			if (!f.isRegulationAtr()) {
				if (!f.getItemParentCD().isEmpty()) {
					f.setRegulationAtr(getRegular(items, f.getItemParentCD()));
				} else {
					f.setRegulationAtr(f.isRequired());
				}
			}
		});

		return items;
	}

	/**
	 * get RegularAtr from parent or grandParent;
	 * @param items
	 * @param itemCode
	 * @return
	 */
	private boolean getRegular(List<PersonInfoMatrixData> items, String itemCode) {
		Optional<PersonInfoMatrixData> item = items.stream()
				.filter(f -> f.getItemCD().equals(itemCode) || f.getItemParentCD().equals(itemCode)).findFirst();

		if (item.isPresent()) {
			PersonInfoMatrixData _item = item.get();
			
			if(_item.isRequired()) {
				return true;
			} else if (_item.getItemParentCD().equals(itemCode)) {
				return getRegular(items, _item.getItemParentCD());
			} else {
				return _item.isRegulationAtr();
			}
		}

		return false;
	}
}
