package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SspmtResultOfLogPK implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	
	/**
	 *ログ登録日時
	 */
	@Basic(optional=false)
	@Column(name = "LOG_NUMBER")
    public int logNumber;
	
	/**
	 * データ保存処理ID
	 */
	@Basic(optional=false)
	@Column(name = "STORE_PROCESSING_ID")
    public String processingId;
	
}
