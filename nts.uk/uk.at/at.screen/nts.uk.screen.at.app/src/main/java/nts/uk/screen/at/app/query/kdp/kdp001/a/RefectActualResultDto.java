package nts.uk.screen.at.app.query.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;

@AllArgsConstructor
@Getter
/**
 * 
 * @author sonnlb
 *
 *         実績への反映内容
 */
public class RefectActualResultDto {
	/**
	 * 応援カード番号
	 */
	private final String cardNumberSupport;

	/**
	 * 打刻場所コード 勤務場所コード old
	 */
	private final String workLocationCD;

	/**
	 * 就業時間帯コード
	 */
	private final String workTimeCode;

	public static RefectActualResultDto fromDomain(RefectActualResult domain) {

		return new RefectActualResultDto(
				(domain.getWorkInforStamp().isPresent() && domain.getWorkInforStamp().get().getCardNumberSupport().isPresent()) ? domain.getWorkInforStamp().get().getCardNumberSupport().get().toString() : null,
				(domain.getWorkInforStamp().isPresent() && domain.getWorkInforStamp().get().getWorkLocationCD().isPresent()) ? domain.getWorkInforStamp().get().getWorkLocationCD().get().toString() : null,
				domain.getWorkTimeCode().map(x -> x != null ? x.v() : null).orElse(null));
	}
}
