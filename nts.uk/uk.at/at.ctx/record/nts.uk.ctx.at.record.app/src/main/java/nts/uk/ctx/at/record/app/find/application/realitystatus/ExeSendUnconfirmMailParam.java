package nts.uk.ctx.at.record.app.find.application.realitystatus;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 *
 */
@Value
public class ExeSendUnconfirmMailParam {
	/**
	 * 送信区分（本人/日次/月次）
	 */
	private int type;
	/**
	 * 職場一覧
	 */
	private List<WkpIdMailCheckParam> listWkp;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private List<String> listEmpCd;
	private Integer closureID;
}
