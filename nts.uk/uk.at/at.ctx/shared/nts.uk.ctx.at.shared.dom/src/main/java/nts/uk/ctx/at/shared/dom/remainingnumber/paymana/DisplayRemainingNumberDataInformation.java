package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

/**
 * UKDesign.UniversalK.就業.KDM_残数管理 (Quản lý số dư).KDM001_残数管理データの登録 (Đăng ký dữ
 * liệu quản lý số dư) .アルゴリズム（残数管理データ登録共通） Thuật toán (common đăng ký data quản
 * lý số còn lại).表示残数データ情報パラメータ.表示残数データ情報パラメータ
 * 
 * @author Hieutt
 *
 */
@Data
@Builder
public class DisplayRemainingNumberDataInformation {

	// 使用期限
	private Integer expirationDate;

	// 使用期限
	private String dispExpiredDate;

	// 残数データ
	private List<RemainInfoDto> remainingData;

	// 残数合計
	private Double totalRemainingNumber;

	// 社員ID
	private String employeeId;

	// 締め終了日
	private GeneralDate endDate;

	// 締め開始日
	private GeneralDate startDate;

	private Integer closureId;

	private ClosureEmployment closureEmploy;

	private SWkpHistImport wkHistory;

	private SEmpHistoryImport sempHistoryImport;

	private String employeeCode;

	private String employeeName;
}
