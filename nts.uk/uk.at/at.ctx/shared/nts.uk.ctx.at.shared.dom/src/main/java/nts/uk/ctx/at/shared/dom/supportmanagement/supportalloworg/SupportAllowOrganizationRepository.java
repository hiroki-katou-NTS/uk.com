package nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg;

import java.util.List;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface SupportAllowOrganizationRepository {

	/**
	 * insertAll
	 * @param cid 会社ID
	 * @param supportAllowOrgs 応援許可する組織リスト
	 */
	void insertAll( String cid, List<SupportAllowOrganization> supportAllowOrgs);
	
	/**
	 * update
	 * @param cid 会社ID
	 * @param supportAllowOrg 応援許可する組織
	 */
	void update( String cid, SupportAllowOrganization supportAllowOrg );
	
	/**
	 * delete
	 * @param cid 会社ID
	 * @param targetOrg 対象組織情報
	 */
	void delete( String cid, TargetOrgIdenInfor targetOrg );
	
	/**
	 * 対象組織を指定して応援許可する組織を取得する
	 * @param cid 会社ID
	 * @param targetOrg 対象組織情報
	 * @return
	 */
	List<SupportAllowOrganization> getListByTargetOrg( String cid, TargetOrgIdenInfor targetOrg );
	
	/**
	 * 応援可能組織を指定して応援許可する組織を取得する
	 * @param cid 会社ID
	 * @param supportableOrg 応援可能組織
	 * @return
	 */
	List<SupportAllowOrganization> getListBySupportableOrg( String cid, TargetOrgIdenInfor supportableOrg );

	/**
	 * exists
	 * @param cid 会社ID
	 * @param targetOrg 対象組織情報
	 * @return
	 */
	boolean exists( String cid, TargetOrgIdenInfor targetOrg );

    /**
     * 対象組織を指定して応援許可する組織を取得する
     * @param cid 会社ID
     * @return
     */
    List<SupportAllowOrganization> getByCid( String cid);
	
}
