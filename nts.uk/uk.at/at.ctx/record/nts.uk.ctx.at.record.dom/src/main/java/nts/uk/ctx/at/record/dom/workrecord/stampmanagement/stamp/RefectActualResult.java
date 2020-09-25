package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * VO : 実績への反映内容
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.実績への反映内容
 * @author tutk
 *
 */
public class RefectActualResult implements DomainValue {

	/**
	 * 応援カード番号
	 */
	@Getter
	private final Optional<String> cardNumberSupport;
	
	/**
	 * 打刻場所コード
	 * 勤務場所コード old
	 */
	@Getter
	private final Optional<WorkLocationCD> workLocationCD;
	
	/**
	 * 就業時間帯コード
	 */
	@Getter
	private final Optional<WorkTimeCode> workTimeCode;
	
	/**
	 * 時間外の申告
	 */
	@Getter
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
