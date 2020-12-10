package nts.uk.ctx.at.request.pub.setting.applicationreason;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ThanhNX
 *
 *         申請定型理由 export
 */
@Data
@AllArgsConstructor
public class ApplicationReasonExport {

	/**
	 * 会社Iｄ
	 */
	public String companyId;
	/**
	 * 申請種類
	 */
	public Integer appType;

	/** 理由コード - 定型理由 */
	public List<Pair<Integer ,String>> reasonTemp;
}
