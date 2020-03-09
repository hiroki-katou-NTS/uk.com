package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * VO : 実績への反映内容
 * @author tutk
 *
 */
@Value
public class RefectActualResult implements DomainValue {

	/**
	 * 応援カード番号
	 */
	private final Optional<String> cardNumberSupport;
	
	/**
	 * 打刻場所コード
	 */
	private final Optional<WorkLocationCD> workLocationCD;
	
	/**
	 * 就業時間帯コード
	 */
	private final Optional<WorkTimeCode> workTimeCode;
	
	/**
	 * 時間外の申告
	 */
	private final Optional<OvertimeDeclaration> overtimeDeclaration;

	public RefectActualResult(String cardNumberSupport, WorkLocationCD workLocationCD,
			WorkTimeCode workTimeCode,OvertimeDeclaration overtimeDeclaration) {
		super();
		this.cardNumberSupport = Optional.ofNullable(cardNumberSupport);
		this.workLocationCD = Optional.ofNullable(workLocationCD);
		this.workTimeCode = Optional.ofNullable(workTimeCode);
		this.overtimeDeclaration = Optional.ofNullable(overtimeDeclaration);
	}
	
	
}
