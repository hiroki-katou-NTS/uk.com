package nts.uk.ctx.at.request.app.find.application.gobackdirectly;


import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;


/**
 * パラメータ
 * UKDesign.UniversalK.就業.KAF_申請.KAF009_直行直帰の申請.A:直行直帰の申請（新規）.ユースケース
 * Refactor4 
 * @author hoangnd
 *
 */
@Data
public class ParamStart {
	// ・会社ID
	private String companyId;
	// ・申請者リスト
	private List<String> sids;
	// ・申請対象日リスト
	private List<String> dates;
	// ・申請表示情報　
	private AppDispInfoStartupDto appDispInfoStartupOutput;

}
