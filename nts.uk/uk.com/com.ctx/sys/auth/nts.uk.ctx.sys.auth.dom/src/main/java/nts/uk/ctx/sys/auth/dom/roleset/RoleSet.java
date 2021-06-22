/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.auth.dom.role.RoleType;

/**
 * ロールセット
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.ロールセット.ロールセット
 * @author HieuNV
 */
@Getter
@AllArgsConstructor
public class RoleSet extends AggregateRoot {

    /** 会社ID */
    private String companyId;

    /** コード */
    private RoleSetCode roleSetCd;

    /** 名称*/
    private RoleSetName roleSetName;

    /** 承認権限*/
    private ApprovalAuthority approvalAuthority;
    
    /** 就業ロール */
    private Optional<String> employmentRoleId;

    /** 個人情報ロール */
    private Optional<String> personInfRoleId;

    /** 給与ロール */
    private Optional<String> salaryRoleId;
    
    /** 人事ロール */
    private Optional<String> hRRoleId;
    
    /** マイナンバーロール */
    private Optional<String> myNumberRoleId;

    /**  オフィスヘルパーロール */
    private Optional<String> officeHelperRoleId;
    
    /**
     * Instantiates a new role set.
     *
     * @param roleSetCd
     * @param companyId
     * @param roleSetName
     * @param approvalAuthority
     * @param officeHelperRoleId
     * @param myNumberRoleId
     * @param hRRoleId
     * @param personInfRoleId
     * @param employmentRoleId
     * @param salaryRoleId
     */
    public RoleSet(String roleSetCd
            , String companyId
            , String roleSetName
            , ApprovalAuthority approvalAuthority
            , String officeHelperRoleId
            , String myNumberRoleId
            , String hRRoleId
            , String personInfRoleId
            , String employmentRoleId
            , String salaryRoleId) {
        super();
        this.roleSetCd             = new RoleSetCode(roleSetCd);
        this.companyId             = companyId;
        this.roleSetName         = new RoleSetName(roleSetName);
        this.approvalAuthority     = approvalAuthority;
        this.officeHelperRoleId = Optional.of(officeHelperRoleId);
        this.myNumberRoleId     = Optional.of(myNumberRoleId);
        this.hRRoleId             = Optional.of(hRRoleId);
        this.personInfRoleId     = Optional.of(personInfRoleId);
        this.employmentRoleId     = Optional.of(employmentRoleId);
        this.salaryRoleId         = Optional.of(salaryRoleId);
    }
    
    /**
     * 作る
     * @param cid 会社ID
     * @param roleSetCd コード
     * @param roleSetName 名称
     * @param attendanceRoleId 就業ロール
     * @param personInfoRoleId 個人情報ロール	
     * @return
     */
    public static RoleSet create(String cid
    		,	String roleSetCd
    		,	String roleSetName
    		,	String attendanceRoleId
    		,	String personInfoRoleId) {
    	return new RoleSet(cid
    			,	new RoleSetCode(roleSetCd)
    			,	new RoleSetName(roleSetName)
    			,	ApprovalAuthority.HasRight //今、承認権限の値 = あり、今後　承認権限を削除するつもりです。
    			,	Optional.of(attendanceRoleId)
    			,	Optional.of(personInfoRoleId)
    			,	Optional.empty(),	Optional.empty()
    			,	Optional.empty(),	Optional.empty() );
    }

    /**
     * If has approval Authority right.
     *
     * @return true
     */
    public boolean hasApprovalAuthority() {
        return this.approvalAuthority == ApprovalAuthority.HasRight;
    }

    /**
     * If hasn't approval Authority right.
     *
     * @return true
     */
    public boolean hasntApprovalAuthority() {
        return this.approvalAuthority == ApprovalAuthority.HasntRight;
    }

    /**
     * set approval authority
     */
    public void setApprovalAuthority() {
        this.approvalAuthority = ApprovalAuthority.HasRight;
    }

    /**
     * Remove approval authority
     */
    public void removeApprovalAuthority() {
        this.approvalAuthority = ApprovalAuthority.HasntRight;
    }

    /**
     * remove value of PersonInfRole field
     */
    public void removePersonInfRole() {
        this.personInfRoleId = Optional.empty();
    }
    
    /**
     * remove value of PersonInfRole field
     */
    public void setEmploymentRoleId() {
        this.employmentRoleId = Optional.empty();
    }
    
    /** Get RoleID by RoleType */
    public String getRoleIDByRoleType(RoleType roleType) {
    	switch(roleType) {
	    	case EMPLOYMENT:
	    		return this.employmentRoleId.get();
	    	case SALARY:
	    		return this.salaryRoleId.get();
	    	case HUMAN_RESOURCE:
	    		return this.hRRoleId.get();
	    	case OFFICE_HELPER:
	    		return this.officeHelperRoleId.get();
	    	case MY_NUMBER:
	    		return this.myNumberRoleId.get();
	    	case PERSONAL_INFO:
	    		return this.personInfRoleId.get();
    		default:
    			return "";
    	}
    }
}
