package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

@Getter
@AllArgsConstructor
public class InterimMngParamCommon {

	/**
	 * 会社ID
	 */
	private String cid;

	/** 社員ID */
	private String sid;

	/** 集計開始日, 集計終了日 */
	private DatePeriod dateData;

	/** モード : True: 月次か, False: その他か */
	private boolean mode;

	/** 画面表示日 */
	private GeneralDate screenDisplayDate;

	/** 上書きフラグ: True: 上書き, False: 未上書き */
	private boolean replaceChk;
	
	/** 上書き用の暫定残数管理データ */
	/** 上書き用の暫定休出代休紐付け管理 */
	private List<InterimRemain> interimMng;

	/**
	 * 作成元区分
	 */
	private Optional<CreateAtr> creatorAtr;
	/**
	 * 対象期間
	 */
	private Optional<DatePeriod> processDate;
}
