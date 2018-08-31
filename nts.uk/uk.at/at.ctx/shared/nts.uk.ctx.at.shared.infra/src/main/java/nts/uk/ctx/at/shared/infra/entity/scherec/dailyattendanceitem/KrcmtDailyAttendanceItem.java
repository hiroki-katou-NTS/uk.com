package nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAI_ATTENDANCE_ITEM")
public class KrcmtDailyAttendanceItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtDailyAttendanceItemPK krcmtDailyAttendanceItemPK;

	/* 勤怠項目名称 */
	@Column(name = "ATTENDANCE_ITEM_NAME")
	public String attendanceItemName;
	
	/* 表示番号 */
	@Column(name = "DISPLAY_NUMBER")
	public BigDecimal displayNumber;
	
	/* ユーザーが値を変更できる */
	@Column(name = "USER_CAN_SET")
	public BigDecimal userCanSet;
	
	/* 名称の改行位置 */
	@Column(name = "LINE_BREAK_POSITION")
	public BigDecimal nameLineFeedPosition;
	
	/* 属性 */
	@Column(name = "DAILY_ATTENDANCE_ATR")
	public BigDecimal dailyAttendanceAtr;
	
	/* Group */
	@Column(name = "TYPE_OF_MASTER")
	public BigDecimal typeOfMaster;
	
	@Column(name = "PRIMITIVE_VALUE")
	public BigDecimal primitiveValue;

	@Override
	protected Object getKey() {
		return this.krcmtDailyAttendanceItemPK;
	}
}
