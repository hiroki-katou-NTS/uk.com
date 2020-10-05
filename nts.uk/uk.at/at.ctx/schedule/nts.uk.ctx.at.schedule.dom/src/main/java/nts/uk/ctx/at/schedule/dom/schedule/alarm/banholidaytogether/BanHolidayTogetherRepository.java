package nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface BanHolidayTogetherRepository {
	/**
	 * insert (会社ID, 同日休日禁止 )
	 * @param banHdTogether
	 */
	void insert(String cid, BanHolidayTogether banHdTogether);
	
	/**
	 * update (会社ID, 同日休日禁止 )
	 * @param banHdTogether
	 */
	void update(String cid, BanHolidayTogether banHdTogether);
	
	/**
	 * delete (会社ID, 対象組織識別情報, 同日休日禁止コード)			
	 * @param banHdTogether
	 */
	void delete(String cid, TargetOrgIdenInfor targetInfo, BanHolidayTogetherCode code);
	
	/**
	 * getAll ( 会社ID, 対象組織識別情報 )
	 * @param cid
	 * @param targetOrg
	 * @return
	 */
	List<BanHolidayTogether> getAll(String cid, TargetOrgIdenInfor targetOrg);
	
	/**
	 * get ( 会社ID, 対象組織識別情報, 同日休日禁止コード )
	 * @param cid
	 * @param targetOrg
	 * @param code
	 * @return
	 */
	Optional<BanHolidayTogether> get(String cid, TargetOrgIdenInfor targetOrg, BanHolidayTogetherCode code);
	
	/**
	 * exists ( 会社ID, 対象組織識別情報, 同日休日禁止コード )
	 * @param cid
	 * @param targetOrg
	 * @param code
	 * @return
	 */
	boolean exists(String cid, TargetOrgIdenInfor targetOrg, BanHolidayTogetherCode code);
	
	
	
}
