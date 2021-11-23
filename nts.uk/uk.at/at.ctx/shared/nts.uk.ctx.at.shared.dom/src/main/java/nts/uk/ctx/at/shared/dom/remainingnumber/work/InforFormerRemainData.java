package nts.uk.ctx.at.shared.dom.remainingnumber.work;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
/**
 * 残数作成元情報
 * @author do_dt
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class InforFormerRemainData {
	/** 社員ID */
	private String sid;
	/** 年月日 */
	private GeneralDate ymd;
	/** 時間代休を利用する */
	private boolean dayOffTimeIsUse;
	/** 勤務種類別 */
	private Optional<WorkTypeRemainInfor> workTypeRemain;
	/** 時間休暇 */
	private List<VacationTimeInfor> vactionTime;
	/** 代休振替 */
	private Optional<DayoffTranferInfor> dayOffTranfer;
	/** 会社別休暇管理設定 */
	private CompanyHolidayMngSetting companyHolidaySetting;
	/** 雇用別休暇管理設定 */
	private EmploymentHolidayMngSetting employmentHolidaySetting;
	
	// inv-1
	private void validate() {
		if (workTypeRemain.isPresent() || dayOffTranfer.isPresent() || !vactionTime.isEmpty())
			return;
		throw new BusinessException("InforFormerRemainData validate");
	}
	/**
	 * 分類を指定して発生使用明細を取得する
	 * @param inforData
	 * @param workTypeClass
	 * @return
	 */
	public Optional<OccurrenceUseDetail> getOccurrenceUseDetail(WorkTypeClassification workTypeClass) {
		
		if(!this.getWorkTypeRemain().isPresent())
			return Optional.empty();
		
		//勤務種類別残数情報をチェックする
		return this.getWorkTypeRemain().get().getOccurrenceDetailData().stream()
				.filter(od -> od.getWorkTypeAtr().equals(workTypeClass)&& od.isUseAtr() && od.getDays() > 0).findFirst();
	}
	
	//日数単位の作成元区分を取得する
	public Optional<CreateAtr> getCreateAtr() {
		validate();
		if(this.getWorkTypeRemain().isPresent())
			return Optional.of(this.getWorkTypeRemain().get().getCreateData());
		if(this.getDayOffTranfer().isPresent())
			return  Optional.of(this.getDayOffTranfer().get().getCreateAtr());
		
		return Optional.empty();
	}
}
