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
import nts.uk.ctx.bs.employee.dom.mastercopy.CopyMethod;
import nts.uk.ctx.bs.employee.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.sys.portal.infra.entity.mypage.setting.CcgmtMyPageSet;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CcgmtMyPageSetDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
	/** The Constant SELECT_BY_CID. */
	private static final String SELECT_BY_CID = "SELECT e FROM CcgmtMyPageSet WHERE e.cid = :cid";
	
	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyCd the company cd
	 */
	public CcgmtMyPageSetDataCopyHandler(CopyMethod copyMethod, String companyId) {
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
		List<CcgmtMyPageSet> zeroCidEntities = this.findAllByCid(zeroCid);
		List<CcgmtMyPageSet> oldDatas = this.findAllByCid(companyId);
		switch (copyMethod) {
			case REPLACE_ALL:
				// Delete all old data
				if(oldDatas!=null) this.commandProxy().removeAll(oldDatas);
				this.getEntityManager().flush();
			case ADD_NEW:
				// Insert Data
				List<CcgmtMyPageSet> dataCopy = new ArrayList<>();
				zeroCidEntities.stream().forEach(e-> {
					CcgmtMyPageSet cloneEntity = SerializationUtils.clone(e);
					cloneEntity.setCid(companyId);
					dataCopy.add(cloneEntity);
				});
				List<CcgmtMyPageSet> addEntites = dataCopy;
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
	public List<CcgmtMyPageSet> findAllByCid(String cid){
		return this.queryProxy().query(SELECT_BY_CID, CcgmtMyPageSet.class).setParameter("cid", cid).getList();
	}

}
