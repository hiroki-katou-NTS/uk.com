package nts.uk.ctx.at.request.infra.entity.application.workchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 勤務変更申請
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KRQDT_APP_WORK_CHANGE")
public class KrqdtAppWorkChange extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KrqdtAppWorkChangePk appWorkChangePk;

	/**
	 * 勤務種類コード
	 */
	@Basic(optional = false)
	@Column(name = "WORK_TYPE_CD")
	public String workTypeCd;
	/**
	 * 就業時間帯コード
	 */
	@Basic(optional = false)
	@Column(name = "WORK_TIME_CD")
	public String workTimeCd;

	/**
	 * 直行区分
	 */
	@Basic(optional = false)
	@Column(name = "GO_WORK_ATR")
	public Integer goWorkAtr;

	/**
	 * 直行区分
	 */
	@Basic(optional = false)
	@Column(name = "BACK_HOME_ATR")
	public Integer backHomeAtr;

	/**
	 * 勤務時間開始1
	 */
	@Basic(optional = true)
	@Column(name = "WORK_TIME_START1")
	public Integer workTimeStart1;

	/**
	 * 勤務時間終了1
	 */
	@Basic(optional = true)
	@Column(name = "WORK_TIME_END1")
	public Integer workTimeEnd1;

	/**
	 * 勤務時間開始2
	 */
	@Basic(optional = true)
	@Column(name = "WORK_TIME_START2")
	public Integer workTimeStart2;

	/**
	 * 勤務時間終了2
	 */
	@Basic(optional = true)
	@Column(name = "WORK_TIME_END2")
	public Integer workTimeEnd2;

	// このMAPPERの定義が重要！
	public static final JpaEntityMapper<KrqdtAppWorkChange> MAPPER = new JpaEntityMapper<>(KrqdtAppWorkChange.class);

	
	public AppWorkChange toDomain() {
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<TimeZoneWithWorkNo>();
		TimeZoneWithWorkNo timeZoneWithWorkNo1 = new TimeZoneWithWorkNo(1, workTimeStart1, workTimeEnd1);
		TimeZoneWithWorkNo timeZoneWithWorkNo2 = new TimeZoneWithWorkNo(2, workTimeStart2, workTimeEnd2);
		timeZoneWithWorkNoLst.add(timeZoneWithWorkNo1);
		timeZoneWithWorkNoLst.add(timeZoneWithWorkNo2);
		return new AppWorkChange(
				EnumAdaptor.valueOf(goWorkAtr, NotUseAtr.class),
				EnumAdaptor.valueOf(backHomeAtr, NotUseAtr.class),
				Optional.ofNullable(new WorkTypeCode(workTypeCd)),
				Optional.ofNullable(new WorkTimeCode(workTimeCd)),
				timeZoneWithWorkNoLst,
				null);
		
	}
	@Override
	protected Object getKey() {
		return appWorkChangePk;
	}

}
