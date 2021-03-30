package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

/**
 * 勤務種類別残数情報
 * 
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WorkTypeRemainInfor {
	/** 勤務種類コード */
	private String workTypeCode;
	/** * 勤務種類の分類 */
	private WorkTypeClassification workTypeClass;
	/** 作成元区分 */
	private CreateAtr createData;
	/** 発生使用明細 */
	private List<OccurrenceUseDetail> occurrenceDetailData = new ArrayList<>();
	/** 特休使用明細 */
	private List<SpecialHolidayUseDetail> speHolidayDetailData;
	/** 子の看護介護使用明細 */
	private List<CareUseDetail> childCareDetailData = new ArrayList<>();
}
