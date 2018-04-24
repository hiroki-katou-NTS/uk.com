package nts.uk.ctx.at.record.app.find.realitystatus;

import java.util.List;

import lombok.Value;

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
	private String startDate;
	private String endDate;
	private List<String> listEmpCd;
}
