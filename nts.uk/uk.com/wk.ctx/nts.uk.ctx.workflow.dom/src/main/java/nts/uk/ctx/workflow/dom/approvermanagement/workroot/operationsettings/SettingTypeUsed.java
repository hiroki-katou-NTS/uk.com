package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 利用する種類設定
 * @author NWS-DungDV
 */
@Getter
@AllArgsConstructor
public class SettingTypeUsed extends ValueObject {
	
	/** 承認ルート区分 */
	private EmploymentRootAtr employmentRootAtr;
	
	/** 申請種類 */
	private Optional<ApplicationType> applicationType;
	
	/** 確認ルート種類 */
	private Optional<ConfirmationRootType> confirmRootType;
	
	/** 利用する */
	private NotUseAtr notUseAtr;
	
	/**
	 * [C-1] 申請種類で作成する
	 * @param applicationType 申請種類
	 */
	public SettingTypeUsed(ApplicationType appType) {
		this.employmentRootAtr = EmploymentRootAtr.APPLICATION;
		this.applicationType = Optional.ofNullable(appType);
		this.confirmRootType = Optional.empty();
		this.notUseAtr = NotUseAtr.NOT_USE;
	}
	
	/**
	 * [C-2] 確認ルート種類で作成する	
	 * @param confirmRootType 確認ルート種類
	 */
	public SettingTypeUsed(ConfirmationRootType confirmType) {
		this.employmentRootAtr = EmploymentRootAtr.CONFIRMATION;
		this.applicationType = Optional.empty();
		this.confirmRootType = Optional.ofNullable(confirmType);
		this.notUseAtr = NotUseAtr.NOT_USE;
	}
	
	/**
	 * [S-1] 全て種類利用しないで作成する
	 * @return List<利用する種類設定>
	 */
	public static List<SettingTypeUsed> createWithoutUsingAttr() {
		// $利用する種類設定List　＝　List.Empty
		List<SettingTypeUsed> settingTypeUseds = new ArrayList<>();
		
		for (ApplicationType appType : ApplicationType.values()) {
			settingTypeUseds.add(new SettingTypeUsed(appType));
		}
		
		for (ConfirmationRootType confirmType : ConfirmationRootType.values()) {
			settingTypeUseds.add(new SettingTypeUsed(confirmType));
		}
		return settingTypeUseds;
	}
	
	/**
	 * [1] 申請種類が利用するか判断する
	 * return Optional<申請種類>	
	 */
	public Optional<ApplicationType> determineAppTypeIsUsed() {
		if (this.employmentRootAtr == EmploymentRootAtr.APPLICATION && this.notUseAtr == NotUseAtr.USE) {
			return this.applicationType;
		}
		return Optional.empty();
	}
	
	/**
	 * [2] 確認ルート種類が利用するか判断する	
	 * @return Optional<確認ルート種類>
	 */
	public Optional<ConfirmationRootType> determineConfirmRootTypeIsUsed() {
		if (this.employmentRootAtr == EmploymentRootAtr.CONFIRMATION && this.notUseAtr == NotUseAtr.USE) {
			return this.confirmRootType;
		}
		return Optional.empty();
	}
	
}
