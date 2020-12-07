package nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfndtTrSignalAbNormalPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 契約コード
	 */
	@Column(name = "CONTRACT_CD")
	public String contractCode;
	
	/**
	 * 就業情報端末コード
	 */
	@Column(name = "TIMERECORDER_CD")
	public int timeRecordCode;
	
	/**
	 * 前回通信成功日時
	 */
	@Column(name = "PRE_TIME_SUCCESS_DATE")
	public GeneralDateTime preTimeSuccDate;
}
