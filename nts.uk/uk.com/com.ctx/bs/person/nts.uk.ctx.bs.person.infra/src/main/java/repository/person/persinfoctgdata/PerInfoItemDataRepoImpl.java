/**
 * 
 */
package repository.person.persinfoctgdata;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.personinfoctgdata.PpemtPerInfoItemData;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;

/**
 * @author danpv
 *
 */
@Stateless
public class PerInfoItemDataRepoImpl extends JpaRepository implements PerInfoItemDataRepository {

	private static final String GET_BY_RID = "select idata from PpemtPerInfoItemData idata"
			+ " where idata.primaryKey.recordId = :recordId";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.
	 * PerInfoItemDataRepository#getAllInfoItem(java.lang.String)
	 */
	@Override
	public List<PersonInfoItemData> getAllInfoItem(String categoryCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonInfoItemData> getAllInfoItemByRecordId(String recordId) {
		List<PpemtPerInfoItemData> datas = this.queryProxy().query(GET_BY_RID, PpemtPerInfoItemData.class).getList();
		return datas.stream()
				.map(ent -> PersonInfoItemData.createFromJavaType(ent.primaryKey.perInfoDefId, ent.primaryKey.recordId,
						ent.saveDataAtr, ent.stringVal, BigDecimal.valueOf(ent.intVal), ent.dateVal))
				.collect(Collectors.toList());
	}
	private PpemtPerInfoItemData toEntity(PersonInfoItemData domain){
		return new PpemtPerInfoItemData();
	}
	/**
	 * Add item data
	 * @param domain
	 */
	@Override
	public void addItemData(PersonInfoItemData domain) {
		// TODO Auto-generated method stub
		
	}

}
