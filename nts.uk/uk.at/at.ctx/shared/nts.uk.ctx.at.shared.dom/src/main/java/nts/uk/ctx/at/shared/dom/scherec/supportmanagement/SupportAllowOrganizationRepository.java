package nts.uk.ctx.at.shared.dom.scherec.supportmanagement;

import java.util.List;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface SupportAllowOrganizationRepository {

	/**
	 * insert
	 * @param cid 会社ID
	 * @param supportAllowOrg 応援許可する組織
	 */
	void insert( String cid, SupportAllowOrganization supportAllowOrg );
	
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
	 * *get
	 * @param cid 会社ID
	 * @param targetOrg 対象組織情報
	 * @return
	 */
	List<SupportAllowOrganization> getSupportAllowOrganizationByTargetOrg( String cid, TargetOrgIdenInfor targetOrg );

	/**
	 * exists
	 * @param cid 会社ID
	 * @param targetOrg 対象組織情報
	 * @return
	 */
	boolean exists( String cid, TargetOrgIdenInfor targetOrg );
	
	/**
	 * insertAll
	 * @param cid 会社ID
	 * @param supportAllowOrgs 応援許可する組織リスト
	 */
	void insertAll( String cid, List<SupportAllowOrganization> supportAllowOrgs);

}
