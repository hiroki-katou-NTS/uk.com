package nts.uk.ctx.exio.infra.entity.exi.csvimport;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * workエラーログ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_ACP_CSV_ERROR")
public class OiomtExAcpCsvError extends UkJpaEntity implements Serializable {
	
	@EmbeddedId
	public OiomtExAcpCsvErrorPk pk;
	/**
	 * 画面表示項目名
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_NAME")
	public String itemName;
	
	/**
	 * CSV受入値
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_VALUE")
	public String itemValue;
	
	/**
	 * エラー内容
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_CONTENT")
	public String errorContent;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}
	

}
