/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;

/**
 * The Class AcquisitionRule.
 */
//休暇の取得ルール
@Getter
@EqualsAndHashCode(callSuper = false, of = { "companyId" })
public class AcquisitionRule extends DomainObject {

	/** The company id. */
	private String companyId;

	/** The setting classification. --- 取得する順番をチェックする */
	@Setter
	private SettingDistinct category;
	
	/**年休より優先する休暇*/
	@Setter
	private AnnualHoliday annualHoliday;
	
	

	/**
	 * Instantiates a new vacation acquisition rule.
	 *
	 * @param memento
	 *            the memento
	 */
	public AcquisitionRule(AcquisitionRuleGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
		this.category = memento.getCategory();
		this.annualHoliday = memento.getAnnualHoliday();
	

	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(AcquisitionRuleSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setCategory(this.category);
		memento.setAnnualHoliday(this.annualHoliday);
	
	}
	
	/**
	 * 時間休暇の優先順をチェックする
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.休暇.休暇の取得ルール.アルゴリズム.時間休暇の優先順をチェックする
	 * @param timsApplications 時間消化申請<List>
	 * @param time60HRemain 60H超休残時間
	 * @param subHdRemain 時間代休残数
	 * @param substituteLeaveManagement 代休の時間管理区分
	 * @param overtime60hManagement 60H超休の時間管理区分
	 */
	public void checkVacationPriority(List<TimeDigestApplicationShare> timeApplication, 
	        Integer time60HRemain, Integer subHdRemain, 
            ManageDistinct substituteLeaveManagement, ManageDistinct overtime60hManagement) {
	    if (time60HRemain == null) {
	        time60HRemain = 0;
	    }
	    if (subHdRemain == null) {
	        subHdRemain = 0;
	    }
	    
	    // 「@取得する順番をチェックする」を確認する
	    if (this.category.equals(SettingDistinct.NO)) {
	        return;
	    }
	    
	    int totalAnnualTime = 0;
	    int totalOver60H = 0;
	    int totalTimeOff = 0;
	    for(TimeDigestApplicationShare timeApplicationShare : timeApplication) {
	        totalAnnualTime += timeApplicationShare.getTimeAnnualLeave() != null ? timeApplicationShare.getTimeAnnualLeave().v() : 0;
	        totalOver60H += timeApplicationShare.getOvertime60H() != null ? timeApplicationShare.getOvertime60H().v() : 0;
	        totalTimeOff += timeApplicationShare.getTimeOff() != null ? timeApplicationShare.getTimeOff().v() : 0;
	    }
	    // 「@年休より優先する休暇．60H超休を優先」　と　INPUT「60H超休の時間管理区分」を確認する
	    if (annualHoliday.isSixtyHoursOverrideHoliday() && overtime60hManagement.equals(ManageDistinct.YES)) {
	        
	        // INPUT「時間消化申請」とINPUT「60H超休残数」を確認する
	        if (totalAnnualTime > 0 && time60HRemain > 0 && (time60HRemain - totalOver60H) > 0) {
	            throw new BusinessException("Msg_1687", "Com_ExsessHoliday", "Com_PaidHoliday", "Com_ExsessHoliday");
	        }
	    }
	    
	    // 「@年休より優先する休暇．代休を優先」　と　INPUT「代休の時間管理区分」を確認する
	    if (!annualHoliday.isPriorityPause() || substituteLeaveManagement.equals(ManageDistinct.NO)) {
	        return;
	    }
	    
	    // INPUT「時間消化申請」とINPUT「時間代休残数」を確認する
	    if (totalAnnualTime > 0 && subHdRemain > 0 && (subHdRemain - totalTimeOff) > 0) {
	        throw new BusinessException("Msg_1687", "Com_CompensationHoliday", "Com_PaidHoliday", "Com_CompensationHoliday");
	    }
	}
}
