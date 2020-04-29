package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author phongtq
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcctStampLayoutDetailPk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/** 運用方法 (0: 共有利用, 1: 個人利用) */
	@Column(name = "OPERATION_METHOD")
	public int operationMethod;
	
	/** ページNO */
	@Column(name = "PAGE_NO")
	public int pageNo;
	
	/** ボタン位置NO */
	@Column(name = "BUTTON_POSITION_NO")
	public int buttonPositionNo;
}