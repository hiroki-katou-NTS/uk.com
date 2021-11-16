package nts.uk.ctx.exio.infra.entity.exi.csvimport;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 外部受入設定 (CSV内容　エラーなし）
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_ACP_CSV_CONTENT")
public class OiomtExAcpCsvContent extends UkJpaEntity implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public OiomtExAcpCsvContentPk pk;
	/**
	 * 削除条件
	 */
	@Basic(optional = false)
	@Column(name = "DELETE_COND_FLG")
	public int deleteCondFlg;
	
	/**
	 * 受入モード
	 */
	@Basic(optional = false)
	@Column(name = "ACCEPT_MODE")
	public int acceptMode;
	
	/**
	 * 処理日時
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_DATE")
	public GeneralDateTime processDate;
	
	/**
	 * テーブル名
	 */
	@Basic(optional = false)
	@Column(name = "TABLE_NAME")
	public String tableName;
	
	/**
	 * カラム名
	 */
	@Basic(optional = false)
	@Column(name = "COLUMN_NAME")
	public String columnName;
	
	/**
	 * 値
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_VALUE")
	public String itemValue;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}
	
}
