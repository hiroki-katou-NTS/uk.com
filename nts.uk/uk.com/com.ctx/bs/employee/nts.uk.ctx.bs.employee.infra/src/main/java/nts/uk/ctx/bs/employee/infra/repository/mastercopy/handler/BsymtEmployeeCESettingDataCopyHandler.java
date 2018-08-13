package nts.uk.ctx.bs.employee.infra.repository.mastercopy.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.commons.lang3.SerializationUtils;

import entity.employeeinfo.setting.code.BsymtEmployeeCESetting;
import entity.employeeinfo.setting.code.BsymtEmployeeCESettingPk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.mastercopy.CopyMethod;
import nts.uk.ctx.bs.employee.dom.mastercopy.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BsymtEmployeeCESettingDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
	/** The Constant SELECT_BY_CID. */
	private static final String SELECT_BY_CID = "SELECT e FROM BsymtEmployeeCESetting WHERE e.companyId = :cid";
	
	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyCd the company cd
	 */
	public BsymtEmployeeCESettingDataCopyHandler(CopyMethod copyMethod, String companyId) {
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
		List<BsymtEmployeeCESetting> zeroCidEntities = this.findAllByCid(zeroCid);
		List<BsymtEmployeeCESetting> oldDatas = this.findAllByCid(companyId);
		switch (copyMethod) {
			case REPLACE_ALL:
				// Delete all old data
				if(oldDatas!=null) this.commandProxy().removeAll(oldDatas);
				this.getEntityManager().flush();
			case ADD_NEW:
				// Insert Data
				List<BsymtEmployeeCESetting> dataCopy = new ArrayList<>();
				zeroCidEntities.stream().forEach(e-> {
					BsymtEmployeeCESetting cloneEntity = SerializationUtils.clone(e);
					BsymtEmployeeCESettingPk pk = new BsymtEmployeeCESettingPk(companyId);
					cloneEntity.setBsymtEmployeeCESettingPk(pk);
					dataCopy.add(cloneEntity);
				});
				List<BsymtEmployeeCESetting> addEntites = dataCopy;
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
	public List<BsymtEmployeeCESetting> findAllByCid(String cid){
		return this.queryProxy().query(SELECT_BY_CID, BsymtEmployeeCESetting.class).setParameter("cid", cid).getList();
	}

}
