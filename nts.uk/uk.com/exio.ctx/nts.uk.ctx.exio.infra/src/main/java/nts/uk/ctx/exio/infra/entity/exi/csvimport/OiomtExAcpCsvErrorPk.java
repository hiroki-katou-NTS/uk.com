package nts.uk.ctx.exio.infra.entity.exi.csvimport;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
@Data
public class OiomtExAcpCsvErrorPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;
	
	/**
	 * 非同期タスクID
	 */
	@Basic(optional = false)
	@Column(name = "ASYN_TASK_ID")
	public String asynTaskId;
	
	/**
	 * 受入行番号
	 */
	@Basic(optional = false)
	@Column(name = "LINE_NUMBER")
	public int lineNumber;
	
	/**
	 * カテゴリ項目NO
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_NO")
	public int itemNo;
}
