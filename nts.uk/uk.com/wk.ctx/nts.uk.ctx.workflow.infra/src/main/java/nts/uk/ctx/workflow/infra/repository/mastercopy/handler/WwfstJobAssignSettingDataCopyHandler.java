package nts.uk.ctx.workflow.infra.repository.mastercopy.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.commons.lang3.SerializationUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.mastercompy.CopyMethod;
import nts.uk.ctx.workflow.dom.mastercompy.DataCopyHandler;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.setting.WwfstJobAssignSetting;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WwfstJobAssignSettingDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
	/** The Constant SELECT_BY_CID. */
	private static final String SELECT_BY_CID = "SELECT e FROM WwfstJobAssignSetting WHERE e.companyId = :cid";
	
	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyCd the company cd
	 */
	public WwfstJobAssignSettingDataCopyHandler(CopyMethod copyMethod, String companyId) {
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
		List<WwfstJobAssignSetting> zeroCidEntities = this.findAllByCid(zeroCid);
		List<WwfstJobAssignSetting> oldDatas = this.findAllByCid(companyId);
		switch (copyMethod) {
			case REPLACE_ALL:
				// Delete all old data
				if(oldDatas!=null) this.commandProxy().removeAll(oldDatas);
				this.getEntityManager().flush();
			case ADD_NEW:
				// Insert Data
				List<WwfstJobAssignSetting> dataCopy = new ArrayList<>();
				zeroCidEntities.stream().forEach(e-> {
					WwfstJobAssignSetting cloneEntity = SerializationUtils.clone(e);
					cloneEntity.setCompanyId(companyId);
					dataCopy.add(cloneEntity);
				});
				List<WwfstJobAssignSetting> addEntites = dataCopy;
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
	public List<WwfstJobAssignSetting> findAllByCid(String cid){
		return this.queryProxy().query(SELECT_BY_CID, WwfstJobAssignSetting.class).setParameter("cid", cid).getList();
	}

}
