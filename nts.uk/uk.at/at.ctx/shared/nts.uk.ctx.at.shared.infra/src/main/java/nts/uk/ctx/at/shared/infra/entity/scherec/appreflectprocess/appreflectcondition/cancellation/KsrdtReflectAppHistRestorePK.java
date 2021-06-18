package nts.uk.ctx.at.shared.infra.entity.scherec.appreflectprocess.appreflectcondition.cancellation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KsrdtReflectAppHistRestorePK implements Serializable {

	private static final long serialVersionUID = 1L;

	// 社員ID
	@Column(name = "SID")
	public String sid;

	// 年月日
	@Column(name = "YMD")
	public GeneralDate date;

	// 申請ID
	@Column(name = "APP_ID")
	public String appId;

	// 予定実績区分
	@Column(name = "ATR")
	public int atr;

	// 反映時刻
	@Column(name = "REFLECT_TIME")
	public GeneralDateTime reflectTime;

	//勤怠項目ID
	@Column(name = "ATTENDANCE_ID")
	public int attendanceId;
}
