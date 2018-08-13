package nts.uk.ctx.sys.portal.infra.repository.mastercopy.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.commons.lang3.SerializationUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.portal.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.sys.portal.infra.entity.mypage.setting.CcgmtPartItemSet;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CcgmtPartItemSetDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
	/** The Constant SELECT_BY_CID. */
	private static final String SELECT_BY_CID = "SELECT e FROM CcgmtPartItemSet WHERE e.ccgmtPartItemSetPK.cid = :cid";
	
	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyCd the company cd
	 */
	public CcgmtPartItemSetDataCopyHandler(CopyMethod copyMethod, String companyId) {
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		//Get all company zero data
		String zeroCid = AppContexts.user().zeroCompanyIdInContract();
		List<CcgmtPartItemSet> zeroCidEntities = this.findAllByCid(zeroCid);
		List<CcgmtPartItemSet> oldDatas = this.findAllByCid(companyId);
		switch (copyMethod) {
			case REPLACE_ALL:
				// Delete all old data
				if(oldDatas!=null) this.commandProxy().removeAll(oldDatas);
				this.getEntityManager().flush();
			case ADD_NEW:
				// Insert Data
				List<CcgmtPartItemSet> dataCopy = new ArrayList<>();
				zeroCidEntities.stream().forEach(e-> {
					CcgmtPartItemSet cloneEntity = SerializationUtils.clone(e);
					cloneEntity.getCcgmtPartItemSetPK().setCid(companyId);
					dataCopy.add(cloneEntity);
				});
				List<CcgmtPartItemSet> addEntites = dataCopy;
				addEntites = dataCopy.stream()
		                .filter(item -> !oldDatas.contains(item))
		                .collect(Collectors.toList());
				this.commandProxy().insertAll(addEntites);
			case DO_NOTHING:
				// Do nothing
			default: 
				break;
		}
	}
	
	/**
	 * Find all by cid.
	 *
	 * @param cid the cid
	 * @return the list
	 */
	public List<CcgmtPartItemSet> findAllByCid(String cid){
		return this.queryProxy().query(SELECT_BY_CID, CcgmtPartItemSet.class).setParameter("cid", cid).getList();
	}

}
