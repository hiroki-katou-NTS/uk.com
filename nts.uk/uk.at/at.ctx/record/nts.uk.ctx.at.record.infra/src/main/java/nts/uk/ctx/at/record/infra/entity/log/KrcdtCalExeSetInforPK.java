package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author hieult
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtCalExeSetInforPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 計算実行設定情報 */
	@Column(name = "CAL_EXECUTION_SET_INFO_ID")
	public String calExecutionSetInfoID;

}
