package nts.uk.ctx.at.shared.dom.supportmanagement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 社員の応援情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.社員の応援情報
 * @author dan_pv
 */
@Value
public class SupportInfoOfEmployee {

	/**
	 * 社員ID
	 */
	private final EmployeeId employeeId;
	
	/**
	 * 年月日
	 */
	private final GeneralDate date;
	
	/**
	 * 所属組織
	 */
	private final TargetOrgIdenInfor affiliationOrg;
	
	/**
	 * 応援形式
	 */
	private final Optional<SupportType> supportType;
	
	/**
	 * 応援先リスト
	 */
	private final List<TargetOrgIdenInfor> recipientList;
	
	/**
	 * 応援しない
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @param affiliationOrg 所属情報
	 * @return
	 */
	public static SupportInfoOfEmployee createWithoutSupport(
			EmployeeId employeeId,
			GeneralDate date,
			TargetOrgIdenInfor affiliationOrg) {
		
		return new SupportInfoOfEmployee(employeeId, date, affiliationOrg,
				Optional.empty(), Collections.emptyList());
	}
	
	/**
	 * 終日応援する
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @param affiliationOrg 所属情報
	 * @param recipient 応援先
	 * @return
	 */
	public static SupportInfoOfEmployee createWithAllDaySupport (
			EmployeeId employeeId,
			GeneralDate date,
			TargetOrgIdenInfor affiliationOrg,
			TargetOrgIdenInfor recipient) {
		
		return new SupportInfoOfEmployee(employeeId, date, affiliationOrg, 
				Optional.of(SupportType.ALLDAY), Arrays.asList(recipient));
	}
	
	/**
	 * 時間帯で応援する
	 * @param employeeId
	 * @param date
	 * @param affiliationOrg
	 * @param recipientList
	 * @return
	 */
	public static SupportInfoOfEmployee createWithTimezoneSupport (
			EmployeeId employeeId,
			GeneralDate date,
			TargetOrgIdenInfor affiliationOrg,
			List<TargetOrgIdenInfor> recipientList) {
		
		return new SupportInfoOfEmployee(employeeId, date, affiliationOrg, 
				Optional.of(SupportType.TIMEZONE), recipientList);
	}
	
	/**
	 * 応援状況を取得する
	 * @param baseOrg 基準となる組織
	 * @return
	 */
	public SupportStatus getSupportStatus(TargetOrgIdenInfor baseOrg) {
		
		if ( this.isAffiliatePerson(baseOrg ) ) {
			
			if ( this.doTheyGoToSupport(baseOrg) ) {
				return this.supportType.get() == SupportType.ALLDAY ? 
						SupportStatus.GO_ALLDAY : SupportStatus.GO_TIMEZONE;
			} else {
				return SupportStatus.DO_NOT_GO;
			}
			
		} else {
			
			if ( this.doTheyCometoSupport(baseOrg) ) {
				return this.supportType.get() == SupportType.ALLDAY ?
						SupportStatus.COME_ALLDAY : SupportStatus.COME_TIMEZONE;
			} else {
				return SupportStatus.DO_NOT_COME;
			}
			
		}
	}
	
	/**
	 * 応援をするか
	 * @return
	 */
	public boolean doTheySupport() {
		
		return this.supportType.isPresent();
	}
	
	/**
	 * 所属者か
	 * @param baseOrg 基準となる組織
	 * @return
	 */
	public boolean isAffiliatePerson(TargetOrgIdenInfor baseOrg) {
		
		return this.affiliationOrg.equals(baseOrg);
	}
	
	/**
	 * 応援に行くか
	 * @param baseOrg 基準となる組織
	 * @return
	 */
	public boolean doTheyGoToSupport(TargetOrgIdenInfor baseOrg) {
		
		if ( ! this.isAffiliatePerson(baseOrg) ) {
			return false;
		}
		
		return this.recipientList.stream()
				.anyMatch(recipient -> !recipient.equals(this.affiliationOrg));
	}
	
	/**
	 * 応援に来るか
	 * @param baseOrg 基準となる組織
	 * @return
	 */
	public boolean doTheyCometoSupport(TargetOrgIdenInfor baseOrg) {
		
		if ( this.isAffiliatePerson(baseOrg) ) {
			return false;
		}
		
		return this.recipientList.stream()
				.anyMatch(recipient -> recipient.equals(baseOrg));
		
	}
	
}
