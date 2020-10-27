package nts.uk.ctx.at.request.infra.entity.application.overtime;

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
public class KrqdtAppOvertimeInputPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	private String cid;

	@Column(name = "APP_ID")
	private String appId;

	@Column(name = "ATTENDANCE_ID")
	private int attendanceId;

	@Column(name = "FRAME_NO")
	private int frameNo;

	@Column(name = "TIME_ITEM_TYPE_ATR")
	private int timeItemTypeAtr;
}
