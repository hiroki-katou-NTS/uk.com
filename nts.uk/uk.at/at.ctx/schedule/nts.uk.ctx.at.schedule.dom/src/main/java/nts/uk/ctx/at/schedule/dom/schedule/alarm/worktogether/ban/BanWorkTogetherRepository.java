package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 同時出勤禁止Repository
 * @author hiroko_miura
 *
 */
public interface BanWorkTogetherRepository {
	
	/**
	 * insert ( 同時出勤禁止 )
	 * @param simulAttBan
	 * @param companyId
	 */
	void insert (String companyId, BanWorkTogether simulAttBan);
	
	/**
	 * update ( 同時出勤禁止 )
	 * @param companyId
	 * @param simulAttBan
	 */
	void update (String companyId, BanWorkTogether simulAttBan);
	
	/**
	 * delete ( 会社ID, 対象組織識別情報 )
	 * @param companyId
	 * @param targetOrg
	 */
	void delete (String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code);
	
	/**
	 * *getAll ( 会社ID, 対象組織識別情報 )
	 * @param companyId
	 * @param targetOrg
	 * @return
	 */
	List<BanWorkTogether> getAll (String companyId, TargetOrgIdenInfor targetOrg);
	
	/**
	 * get ( 会社ID, 対象組織識別情報, 同時出勤禁止コード )
	 * @param companyId
	 * @param targetOrg
	 * @param code
	 * @return
	 */
	Optional<BanWorkTogether> get (String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code);
	
	/**
	 * exists ( 会社ID, 対象組織識別情報, 同時出勤禁止コード )
	 * @param companyId
	 * @param targetOrg
	 * @param code
	 * @return
	 */
	boolean exists (String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code);
}
