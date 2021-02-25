package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author chungnt
 *
 */

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcdtTopAlStampPk implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 対象社員ID
	 */
	@Basic(optional = false)
	@Column(name = "SID_TGT")
	public String sidTgt;
	
	/**
	 *  実行完了日時
	 *   */
	@Basic (optional = false)
	@Column (name = "FINISH_TIME")
	public GeneralDateTime finishDateTime;

}
