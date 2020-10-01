package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

/**
 * 	表示残数データ情報パラメータ
 * @author Hieutt
 *
 */
@Data
@Builder
public class DisplayRemainingNumberDataInformation {

	//	使用期限
	private Integer expirationDate;
	
	//	残数データ
	private List<RemainInfoDto> remainingData;
	
	//	残数合計
	private Double totalRemainingNumber;
	
	//	社員ID
	private String employeeId;
	
	//	締め終了日
	private GeneralDate endDate;
	
	//	締め開始日
	private GeneralDate startDate;
	
	private ClosureEmployment closureEmploy;
	
	private SWkpHistImport wkHistory;
	
	private SEmpHistoryImport sempHistoryImport;
}
