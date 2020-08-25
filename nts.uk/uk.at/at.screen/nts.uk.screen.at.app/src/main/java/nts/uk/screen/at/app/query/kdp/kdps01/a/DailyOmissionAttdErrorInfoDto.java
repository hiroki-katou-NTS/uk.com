package nts.uk.screen.at.app.query.kdp.kdps01.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.DailyAttdErrorInfo;

/**
 * 日別勤怠エラー情報
 * 
 * @author sonnlb
 * 
 *         hàng hịn , chuẩn cấu trúc domain
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyOmissionAttdErrorInfoDto {

	/**
	 * エラー種類
	 */

	private int checkErrorType;
	/**
	 * 促すメッセージ
	 */

	private PromptingMessageDto promptingMessage;
	/**
	 * 最後エラー発生日
	 */

	private GeneralDate lastDateError;
	/**
	 * 促す申請一覧
	 */

	private List<Integer> listRequired;

	public static DailyOmissionAttdErrorInfoDto fromDomain(DailyAttdErrorInfo domain) {

		return new DailyOmissionAttdErrorInfoDto(domain.getCheckErrorType().value,
				PromptingMessageDto.fromDomain(domain.getPromptingMessage()), domain.getLastDateError(),
				domain.getListRequired());
	}

}
