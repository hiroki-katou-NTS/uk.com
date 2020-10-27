package nts.uk.ctx.at.request.infra.entity.application.holidaywork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppHdWorkTimePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	private String cid;

	@Column(name = "APP_ID")
	private String appId;

	/**
	 * 勤怠種類
	 */
	@Column(name = "ATTENDANCE_TYPE")
	private int attendanceType;

	/**
	 * 枠NO
	 */
	@Column(name = "FRAME_NO")
	private int frameNo;

}
