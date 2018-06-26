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
	private Optional<WorkTypeRemainInfor> workTypeRemain;
	/**	時間休暇 */
	private Optional<VacationTimeInfor> vactionTime;
	/**	代休振替 */
	private Optional<DayoffTranferInfor> dayOffTranfer;
	/**
	 * 分類を指定して発生使用明細を取得する
	 * @param inforData
	 * @param workTypeClass
	 * @return
	 */
	public Optional<OccurrenceUseDetail> getOccurrenceUseDetail(InforFormerRemainData inforData, WorkTypeClassification workTypeClass) {
		if(inforData == null) {
			return Optional.empty();	
		}
		OccurrenceUseDetail outputData = new OccurrenceUseDetail();
		//勤務種類別残数情報をチェックする
		if(inforData.getWorkTypeRemain().isPresent()) {
			WorkTypeRemainInfor x = inforData.getWorkTypeRemain().get();
			List<OccurrenceUseDetail> lstOccurrenceUseDetail = x.getOccurrenceDetailData();
			List<OccurrenceUseDetail> lstTmp = lstOccurrenceUseDetail.stream()
					.filter(a ->a.getWorkTypeAtr() == workTypeClass && a.isUseAtr() && a.getDays() > 0)
					.collect(Collectors.toList());
			if(!lstTmp.isEmpty()) {
				outputData = lstTmp.get(0);
			}
		}
		if(outputData == null) {
			return Optional.empty();	
		} else {
			return Optional.of(outputData);
		}
		
		
	}
}
