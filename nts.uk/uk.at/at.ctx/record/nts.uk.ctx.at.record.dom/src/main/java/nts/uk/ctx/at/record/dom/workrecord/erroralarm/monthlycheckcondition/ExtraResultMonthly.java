package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;

/**
 * 月別実績の抽出条件
 * @author tutk
 *
 */
@Getter
public class ExtraResultMonthly extends AggregateRoot {
	/**ID*/
	private String errorAlarmCheckID;
	/**並び順*/
	private int sortBy;
	/**名称*/
	private NameAlarmExtractionCondition nameAlarmExtraCon;
	/**使用区分*/
	private boolean useAtr;
	/**チェック項目の種類*/
	private TypeMonCheckItem typeCheckItem;
	/**メッセージの表示方法*/
	private HowDisplayMessage howDisplayMessage;
	/**表示メッセージ*/
	private Optional<MessageDisplay> displayMessage;
	/**月次のチェック条件*/
	private Optional<AttendanceItemCondition> checkConMonthly;
	
	public ExtraResultMonthly(String errorAlarmCheckID, int sortBy, NameAlarmExtractionCondition nameAlarmExtraCon, boolean useAtr, TypeMonCheckItem typeCheckItem, HowDisplayMessage howDisplayMessage,
			MessageDisplay displayMessage, AttendanceItemCondition checkConMonthly) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.sortBy = sortBy;
		this.nameAlarmExtraCon = nameAlarmExtraCon;
		this.useAtr = useAtr;
		this.typeCheckItem = typeCheckItem;
		this.howDisplayMessage = howDisplayMessage;
		this.displayMessage = Optional.ofNullable(displayMessage);
		this.checkConMonthly = Optional.ofNullable(checkConMonthly);
	}
	
	
}
