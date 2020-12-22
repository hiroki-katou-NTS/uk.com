package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

/**
 * 集計所属情報
 * @author shuichu_ishida
 */
@Getter
public class AggregateAffiliationInfo implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 雇用コード */
	private EmploymentCode employmentCd;
	/** 職場ID */
	private WorkplaceId workplaceId; 
	/** 職位ID */
	private JobTitleId jobTitleId; 
	/** 分類コード */
	private ClassificationCode classCd; 
	/** 勤務種別コード */
	private Optional<BusinessTypeCode> businessTypeCd;
	
	/**
	 * コンストラクタ
	 */
	public AggregateAffiliationInfo(){
		
		this.employmentCd = new EmploymentCode("");
		this.workplaceId = new WorkplaceId("");
		this.jobTitleId = new JobTitleId("");
		this.classCd = new ClassificationCode("");
		this.businessTypeCd = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param employmentCd 雇用コード
	 * @param workplaceId 職場ID
	 * @param jobTitleId 職位ID
	 * @param classCd 分類コード
	 * @param businessTypeCd 勤務種別コード
	 * @return 月別実績の所属情報
	 */
	public static AggregateAffiliationInfo of(
			EmploymentCode employmentCd,
			WorkplaceId workplaceId,
			JobTitleId jobTitleId,
			ClassificationCode classCd,
			Optional<BusinessTypeCode> businessTypeCd){
		
		AggregateAffiliationInfo domain = new AggregateAffiliationInfo();
		domain.employmentCd = employmentCd;
		domain.workplaceId = workplaceId;
		domain.jobTitleId = jobTitleId;
		domain.classCd = classCd;
		domain.businessTypeCd = businessTypeCd;
		return domain;
	}
}
