package nts.uk.ctx.at.shared.dom.remainingnumber.work;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
/**
 * 残数作成元情報
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InforFormerRemainData {
	/**社員ID	 */
	private String sid;
	/**	年月日 */
	private GeneralDate ymd;
	/**	時間代休を利用する */
	private boolean dayOffTimeIsUse;
	/**	勤務種類別 */
	private List<WorkTypeRemainInfor> workTypeRemain;
	/**	時間休暇 */
	private Optional<VacationTimeInfor> vactionTime;
	/**	代休振替 */
	private List<DayoffTranferInfor> dayOffTranfer;
	/**
	 * 会社別休暇管理設定
	 */
	private CompanyHolidayMngSetting companyHolidaySetting;
	/**
	 * 雇用別休暇管理設定
	 */
	private EmploymentHolidayMngSetting employmentHolidaySetting;
	/**
	 * 分類を指定して発生使用明細を取得する
	 * @param inforData
	 * @param workTypeClass
	 * @return
	 */
	public Optional<OccurrenceUseDetail> getOccurrenceUseDetail(WorkTypeClassification workTypeClass) {
		//勤務種類別残数情報をチェックする
		Optional<WorkTypeRemainInfor> optWorkTypeRemainInfor = this.getWorkTypeRemainInfor(workTypeClass);
		if(!optWorkTypeRemainInfor.isPresent()) {
			return Optional.empty();
		}
		List<OccurrenceUseDetail> lstOccurrenceUseDetail = optWorkTypeRemainInfor.get().getOccurrenceDetailData();
		List<OccurrenceUseDetail> lstTmp = lstOccurrenceUseDetail.stream()
				.filter(a ->a.getWorkTypeAtr() == workTypeClass && a.isUseAtr() && a.getDays() > 0)
				.collect(Collectors.toList());
		if(!lstTmp.isEmpty()) {
			return Optional.of(lstTmp.get(0));
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<WorkTypeRemainInfor> getWorkTypeRemainInfor(WorkTypeClassification workTypeClass) {
		List<WorkTypeRemainInfor> lstWorkTypeRemainInfor = this.getWorkTypeRemain().stream()
				.filter(x -> x.getWorkTypeClass() == workTypeClass).collect(Collectors.toList());
		if(lstWorkTypeRemainInfor.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(lstWorkTypeRemainInfor.get(0));
	}
}
