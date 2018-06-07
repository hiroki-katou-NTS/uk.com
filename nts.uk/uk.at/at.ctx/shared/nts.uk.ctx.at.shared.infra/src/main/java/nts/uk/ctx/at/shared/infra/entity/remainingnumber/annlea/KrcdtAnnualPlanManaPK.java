package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
/**
 * KEY 計画年休管理データ
 * @author do_dt
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnnualPlanManaPK implements Serializable{
	/** 社員ID	 */
	@Column(name = "SID")
	public String sId;
	/** 勤務種類コード	 */
	@Column(name = "WORK_TYPE_CD")
	public String workTypeCd;
	/** 年月日	 */
	@Column(name = "YMD")
	public GeneralDate ymd;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
