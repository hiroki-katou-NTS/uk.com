package nts.uk.ctx.exio.infra.entity.exi.execlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外部受入エラーログ
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EXAC_ERROR_LOG")
public class OiomtExacErrorLog extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExacErrorLogPk exacErrorLogPk;

	/**
	 * CSVエラー項目名
	 */
	@Basic(optional = true)
	@Column(name = "CSV_ERROR_ITEM_NAME")
	public String csvErrorItemName;

	/**
	 * CSV受入値
	 */
	@Basic(optional = true)
	@Column(name = "CSV_ACCEPTED_VALUE")
	public String csvAcceptedValue;

	/**
	 * エラー内容
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_CONTENTS")
	public String errorContents;

	/**
	 * レコード番号
	 */
	@Basic(optional = false)
	@Column(name = "RECORD_NUMBER")
	public int recordNumber;

	/**
	 * ログ登録日時
	 */
	@Basic(optional = false)
	@Column(name = "LOG_REG_DATE_TIME")
	public GeneralDateTime logRegDateTime;

	/**
	 * 項目名
	 */
	@Basic(optional = true)
	@Column(name = "ITEM_NAME")
	public String itemName;

	/**
	 * エラー発生区分
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_ATR")
	public int errorAtr;

	@Override
	protected Object getKey() {
		return exacErrorLogPk;
	}
}
