package nts.uk.ctx.exio.infra.entity.exi.condset;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 受入条件設定（定型）
 */
@Data
@Entity
@Table(name = "OIOMT_STD_ACCEPT_COND_SET")
@EqualsAndHashCode(callSuper = true)
public class OiomtStdAcceptCondSet extends UkJpaEntity
		implements StdAcceptCondSet.MementoGetter, StdAcceptCondSet.MementoSetter, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	private OiomtStdAcceptCondSetPk stdAcceptCondSetPk;

	/**
	 * 外部受入カテゴリID
	 */
	@Basic(optional = true)
	@Column(name = "CATEGORY_ID")
	private String categoryId;

	/**
	 * CSVデータの項目名行
	 */
	@Basic(optional = true)
	@Column(name = "CSV_DATA_LINE_NUMBER")
	private Integer csvDataLineNumber;

	/**
	 * 既存データの削除
	 */
	@Basic(optional = false)
	@Column(name = "DELETE_EXIST_DATA")
	private int deleteExistData;

	/**
	 * CSVデータの取込開始行
	 */
	@Basic(optional = true)
	@Column(name = "CSV_DATA_START_LINE")
	private Integer csvDataStartLine;

	/**
	 * 文字コード
	 */
	@Basic(optional = true)
	@Column(name = "CHARACTER_CODE")
	private Integer characterCode;

	/**
	 * 受入モード
	 */
	@Basic(optional = true)
	@Column(name = "ACCEPT_MODE")
	private Integer acceptMode;

	/**
	 * 外部受入条件名称
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_NAME")
	private String conditionSetName;

	/**
	 * チェック完了
	 */
	@Basic(optional = true)
	@Column(name = "CHECK_COMPLETED")
	private Integer checkCompleted;

	/**
	 * 既存データの削除方法
	 */
	@Basic(optional = true)
	@Column(name = "DELETE_EXT_DATA_METHOD")
	private Integer deleteExtDataMethod;

	/**
	 * Gets primary key of entity.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.stdAcceptCondSetPk;
	}

	/**
	 * No args constructor.
	 */
	protected OiomtStdAcceptCondSet() {
	}

	/**
	 * Creates new entity from domain and memento.
	 *
	 * @param domain the domain require <code>not null</code>
	 */
	public OiomtStdAcceptCondSet(@NonNull StdAcceptCondSet domain) {
		domain.setMemento(this);
	}

	/**
	 * Sets company id.
	 *
	 * @param companyId the company id
	 */
	@Override
	public void setCompanyId(String companyId) {
		if (this.stdAcceptCondSetPk == null) {
			this.stdAcceptCondSetPk = new OiomtStdAcceptCondSetPk();
		}
		this.stdAcceptCondSetPk.cid = companyId;
	}

	/**
	 * Sets condition set code.
	 *
	 * @param conditionSetCode the condition set code
	 */
	@Override
	public void setConditionSetCode(String conditionSetCode) {
		if (this.stdAcceptCondSetPk == null) {
			this.stdAcceptCondSetPk = new OiomtStdAcceptCondSetPk();
		}
		this.stdAcceptCondSetPk.conditionSetCd = conditionSetCode;
	}

	/**
	 * Sets csv data item line number.
	 *
	 * @param csvDataItemLineNumber the csv data item line number
	 */
	@Override
	public void setCsvDataItemLineNumber(Integer csvDataItemLineNumber) {
		this.csvDataLineNumber = csvDataItemLineNumber;
	}

	/**
	 * Sets system type.
	 *
	 * @param systemType the system type
	 */
	@Override
	public void setSystemType(int systemType) {
		if (this.stdAcceptCondSetPk == null) {
			this.stdAcceptCondSetPk = new OiomtStdAcceptCondSetPk();
		}
		this.stdAcceptCondSetPk.systemType = systemType;
	}

	/**
	 * Sets delete exist data.
	 *
	 * @param deleteExistData the delete exist data
	 */
	@Override
	public void setDeleteExistData(int deleteExistData) {
		this.deleteExistData = deleteExistData;
	}

	/**
	 * Sets delete exist data method.
	 *
	 * @param deleteExistDataMethod the delete exist data method
	 */
	@Override
	public void setDeleteExistDataMethod(Integer deleteExistDataMethod) {
		this.deleteExtDataMethod = deleteExistDataMethod;
	}

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return this.stdAcceptCondSetPk.cid;
	}

	/**
	 * Gets condition set code.
	 *
	 * @return the condition set code
	 */
	@Override
	public String getConditionSetCode() {
		return this.stdAcceptCondSetPk.conditionSetCd;
	}

	/**
	 * Gets csv data item line number.
	 *
	 * @return the csv data item line number
	 */
	@Override
	public Integer getCsvDataItemLineNumber() {
		return this.csvDataLineNumber;
	}

	/**
	 * Gets system type.
	 *
	 * @return the system type
	 */
	@Override
	public int getSystemType() {
		return this.stdAcceptCondSetPk.systemType;
	}

	/**
	 * Gets delete exist data method.
	 *
	 * @return the delete exist data method
	 */
	@Override
	public Integer getDeleteExistDataMethod() {
		return this.deleteExtDataMethod;
	}

}
