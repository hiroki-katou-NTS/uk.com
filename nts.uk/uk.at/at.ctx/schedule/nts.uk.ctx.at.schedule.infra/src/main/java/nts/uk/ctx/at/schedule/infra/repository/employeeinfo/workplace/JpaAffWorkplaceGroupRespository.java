package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.AffWorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.AffWorkplaceGroupRespository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroup;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.workplace.BsympAffWorkPlaceGroup;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.workplace.BsymtWorkplaceGroup;

@Stateless
public class JpaAffWorkplaceGroupRespository extends JpaRepository implements AffWorkplaceGroupRespository {

	private static final String SELECT = "SELECT c FROM BsympAffWorkPlaceGroup c ";

	private static final String SELECT_BY_CID = SELECT + " WHERE c.pk.CID = :CID";

	private static final String SELECT_BY_CID_CODE_WID = SELECT_BY_CID + " AND c.pk.WKPGRPID = :WKPGRPID";

	private static final String SELECT_BY_CID_CODE_WPID = SELECT_BY_CID_CODE_WID + " AND c.pk.WPID = :WPID";

	private static final String SELECT_BY_CID_WPID = SELECT_BY_CID + " AND c.pk.WPID = :WPID";

	private static final String SELECT_BY_CID_LIST_WPID = SELECT_BY_CID + " AND c.pk.WPID IN :lstWPID";

	private static final String SELECT_BY_LIST_ID = SELECT_BY_CID_CODE_WID + " AND c.pk.WPID IN :lstWPID";

	private static final String SELECT_BY_DATE_COMPANY = "SELECT c.WPID FROM BsympAffWorkPlaceGroup c "
			+ " LEFT JOIN BsymtWorkplaceGroup wpg ON c.pk.WKPGRPID = wpg.pk.WKPGRPID" + " WHERE wpg.pk.CID = :CID"
			+ " AND wpg.pk.WKPGRPID = :WKPGRPID" + " ORDER BY wpg.pk.WKPGRPCode ASC, c.pk.WPID ASC";

	private static final String SELECT_WPID_BY_CODE = "SELECT c.WPID FROM BsympAffWorkPlaceGroup c "
			+ " LEFT JOIN BsymtWorkplaceGroup wpg ON c.pk.WKPGRPID = wpg.pk.WKPGRPID" + " WHERE wpg.pk.CID = :CID"
			+ " AND wpg.pk.WKPGRPCode = :WKPGRPCode" + " ORDER BY wpg.pk.WKPGRPCode ASC, c.pk.WPID ASC";

	private static final String SELECT_WORKPLACE_GROUP = "SELECT wpg FROM BsymtWorkplaceGroup c "
			+ " LEFT JOIN BsympAffWorkPlaceGroup atc ON wpg.pk.WKPGRPID = atc.pk.WKPGRPID" + " WHERE wpg.pk.CID = :CID"
			+ " AND atc.pk.WPID = :WPID";

	private static final String CHECK_WORKPLACE_GROUP = "SELECT c.WPID FROM BsympAffWorkPlaceGroup c "
			+ " LEFT JOIN BsymtWorkplaceGroup wpg ON c.pk.WKPGRPID = wpg.pk.WKPGRPID" + " WHERE wpg.pk.CID = :CID"
			+ " AND c.pk.WPID = :WPID";

	/**
	 * insert ( 職場グループ所属情報 )
	 * 
	 * @param affWorkplaceGroup
	 */
	@Override
	public void insert(AffWorkplaceGroup affWorkplaceGroup) {
		this.commandProxy().insert(affWorkplaceGroup);
	}

	/**
	 * update ( 職場グループ所属情報 )
	 * 
	 * @param affWorkplaceGroup
	 */
	@Override
	public void update(AffWorkplaceGroup affWorkplaceGroup) {
	}

	/**
	 * delete ( 会社ID, 職場グループID, 職場ID )
	 * 
	 * @param CID
	 * @param WKPGRPID
	 * @param WKPID
	 */
	@Override
	public void deleteAll(String CID, String WKPGRPID, String WKPID) {
		Optional<AffWorkplaceGroup> entity = this.getByID(CID, WKPGRPID, WKPID);
		if (entity.isPresent())
			this.commandProxy().remove(entity);
	}

	/**
	 * delete ( 会社ID, 職場グループID )
	 * 
	 * @param CID
	 * @param WKPGRPID
	 */
	@Override
	public void deleteByWKPGRPID(String CID, String WKPGRPID) {
		Optional<AffWorkplaceGroup> entity = this.queryProxy()
				.query(SELECT_BY_CID_CODE_WID, BsympAffWorkPlaceGroup.class).setParameter("companyId", CID)
				.setParameter("WKPGRPID", WKPGRPID).getSingle(c -> c.toDomain());
		if (entity.isPresent())
			this.commandProxy().remove(entity);
	}

	/**
	 * delete ( 会社ID, 職場ID )
	 * 
	 * @param CID
	 * @param WKPID
	 */
	@Override
	public void deleteByWKPID(String CID, String WKPID) {
		Optional<AffWorkplaceGroup> entity = this.getByWKPID(CID, WKPID);
		if (entity.isPresent())
			this.commandProxy().remove(entity);
	}

	/**
	 * get ( 会社ID, 職場グループID, 職場ID )
	 * 
	 * @param CID
	 * @param WKPGRPID
	 * @param WKPID
	 * @return Optional<職場グループ所属情報>
	 */
	@Override
	public Optional<AffWorkplaceGroup> getByID(String CID, String WKPGRPID, String WKPID) {
		return this.queryProxy().query(SELECT_BY_CID_CODE_WPID, BsympAffWorkPlaceGroup.class)
				.setParameter("companyId", CID).setParameter("WKPGRPID", WKPGRPID).setParameter("WKPID", WKPID)
				.getSingle(c -> c.toDomain());
	}

	/**
	 * get ( 会社ID, 職場グループID, List<職場ID> )
	 * 
	 * @param CID
	 * @param WKPGRPID
	 * @param WKPID
	 * @return
	 */
	@Override
	public List<AffWorkplaceGroup> getByListID(String CID, String WKPGRPID, List<String> WKPID) {
		Set<String> lstWKPID = WKPID.stream().map(x -> x).collect(Collectors.toSet());
		return this.queryProxy().query(SELECT_BY_LIST_ID, BsympAffWorkPlaceGroup.class).setParameter("CID", CID)
				.setParameter("WKPGRPID", WKPGRPID).setParameter("lstWPID", lstWKPID).getList(c -> c.toDomain());
	}

	/**
	 * exists ( 会社ID, 職場グループID, 職場ID )
	 * 
	 * @param CID
	 * @param WKPGRPID
	 * @param WKPID
	 * @return
	 */
	@Override
	public boolean checkExists(String CID, String WKPGRPID, String WKPID) {
		Optional<AffWorkplaceGroup> optional = this.getByID(CID, WKPGRPID, WKPID);
		return optional.isPresent();
	}

	/**
	 * get ( 会社ID, 職場ID )
	 * 
	 * @param CID
	 * @param WKPID
	 * @return Optional<職場グループ所属情報>
	 */
	@Override
	public Optional<AffWorkplaceGroup> getByWKPID(String CID, String WKPID) {
		return this.queryProxy().query(SELECT_BY_CID_WPID, BsympAffWorkPlaceGroup.class).setParameter("companyId", CID)
				.setParameter("WKPID", WKPID).getSingle(c -> c.toDomain());
	}

	/**
	 * get ( 会社ID, List<職場ID> )
	 * 
	 * @param CID
	 * @param WKPID
	 * @return
	 */
	@Override
	public List<AffWorkplaceGroup> getByListWKPID(String CID, List<String> WKPID) {
		Set<String> lstWKPID = WKPID.stream().map(x -> x).collect(Collectors.toSet());
		return this.queryProxy().query(SELECT_BY_CID_LIST_WPID, BsympAffWorkPlaceGroup.class).setParameter("CID", CID)
				.setParameter("lstWPID", lstWKPID).getList(c -> c.toDomain());
	}

	/**
	 * exists ( 会社ID, 職場ID )
	 * 
	 * @param CID
	 * @param WKPID
	 * @return
	 */
	@Override
	public boolean checkExistsWKPID(String CID, String WKPID) {
		Optional<AffWorkplaceGroup> optional = this.getByWKPID(CID, WKPID);
		return optional.isPresent();
	}

	/**
	 * getAll ( 会社ID )
	 * 
	 * @param CID
	 * @return
	 */
	@Override
	public Optional<AffWorkplaceGroup> getAll(String CID) {
		return this.queryProxy().query(SELECT_BY_CID, BsympAffWorkPlaceGroup.class).setParameter("companyId", CID)
				.getSingle(c -> c.toDomain());
	}

	/**
	 * 職場グループに所属する職場を取得する
	 * 
	 * @param CID
	 * @param WKPGRPID
	 * @return List<職場ID>
	 */
	@Override
	public List<String> getWKPID(String CID, String WKPGRPID) {
		List<String> resultList = new ArrayList<>();
			resultList.addAll(this.queryProxy().query(SELECT_BY_DATE_COMPANY, String.class).setParameter("CID", CID)
					.setParameter("WKPGRPID", WKPGRPID).getList());
		return resultList;
	}

	/**
	 * 職場グループに所属する職場を取得する
	 * 
	 * @param CID
	 * @param WKPGRPCode
	 * @return
	 */
	@Override
	public List<String> getWKPIDbyCD(String CID, String WKPGRPCode) {
		List<String> resultList = new ArrayList<>();
		resultList.addAll(this.queryProxy().query(SELECT_WPID_BY_CODE, String.class).setParameter("CID", CID)
				.setParameter("WKPGRPCode", WKPGRPCode).getList());
		return resultList;
	}

	/**
	 * 職場が所属する職場グループを取得する
	 * 
	 * @param CID
	 * @param WKPID
	 * @return
	 */
	@Override
	public Optional<WorkplaceGroup> getWorkplaceGroup(String CID, String WKPID) {
		return this.queryProxy().query(SELECT_WORKPLACE_GROUP, BsymtWorkplaceGroup.class).setParameter("CID", CID)
				.setParameter("WKPID", WKPID).getSingle(c -> c.toDomain());
	}

	/**
	 * 職場が職場グループに所属しているか
	 * 
	 * @param CID
	 * @param WKPGRPID
	 * @return
	 */
	@Override
	public boolean checkWorkplace(String CID, String WKPGRPID) {
		Optional<AffWorkplaceGroup> affWorkplaceGroup = this.queryProxy()
				.query(CHECK_WORKPLACE_GROUP, BsympAffWorkPlaceGroup.class).setParameter("CID", CID)
				.setParameter("WKPGRPID", WKPGRPID).getSingle(c -> c.toDomain());
		// TODO Auto-generated method stub
		return affWorkplaceGroup.isPresent();
	}

}
