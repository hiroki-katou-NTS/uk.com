package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 受入条件設定（定型）
 */

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_AC_COND")
public class OiomtExAcCond extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExAcCondPk stdAcceptCondSetPk;

	/**
	 * 外部受入カテゴリID
	 */
	@Basic(optional = true)
	@Column(name = "CATEGORY_ID")
	public String categoryId;

	/**
	 * CSVデータの項目名行
	 */
	@Basic(optional = true)
	@Column(name = "CSV_DATA_LINE_NUMBER")
	public Integer csvDataLineNumber;

	/**
	 * 既存データの削除
	 */
	@Basic(optional = false)
	@Column(name = "DELETE_EXIST_DATA")
	public int deleteExistData;

	/**
	 * CSVデータの取込開始行
	 */
	@Basic(optional = true)
	@Column(name = "CSV_DATA_START_LINE")
	public Integer csvDataStartLine;

	/**
	 * 文字コード
	 */
	@Basic(optional = true)
	@Column(name = "CHARACTER_CODE")
	public Integer characterCode;
	
	/**
	 * 受入モード
	 */
	@Basic(optional = true)
	@Column(name = "ACCEPT_MODE")
	public Integer acceptMode;

	/**
	 * 外部受入条件名称
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_NAME")
	public String conditionSetName;

	/**
	 * チェック完了
	 */
	@Basic(optional = true)
	@Column(name = "CHECK_COMPLETED")
	public Integer checkCompleted;

	/**
	 * 既存データの削除方法
	 */
	@Basic(optional = true)
	@Column(name = "DELETE_EXT_DATA_METHOD")
	public Integer deleteExtDataMethod;

	@Override
	protected Object getKey() {
		return stdAcceptCondSetPk;
	}

	public OiomtExAcCond(String companyId, int systemType, String conditionSettingCode, String conditionSetName,
			int deleteExistData, Integer acceptMode, Integer checkCompleted, String categoryId,
			Integer csvDataLineNumber, Integer csvDataStartLine, Integer characterCode, Integer deleteExtDataMethod) {
		super();
		this.stdAcceptCondSetPk = new OiomtExAcCondPk(companyId, systemType, conditionSettingCode);
		this.categoryId = categoryId;
		this.csvDataLineNumber = csvDataLineNumber;
		this.deleteExistData = deleteExistData;
		this.csvDataStartLine = csvDataStartLine;
		this.characterCode = characterCode;
		this.acceptMode = acceptMode;
		this.conditionSetName = conditionSetName;
		this.checkCompleted = checkCompleted;
		this.deleteExtDataMethod = deleteExtDataMethod;
	}

	public static OiomtExAcCond domainToEntity(StdAcceptCondSet domain) {
		return new OiomtExAcCond(domain.getCid(), domain.getSystemType().value, domain.getConditionSetCd().v(),
				domain.getConditionSetName().v(), domain.getDeleteExistData().value,
				domain.getAcceptMode().isPresent() ? domain.getAcceptMode().get().value : null,
				domain.getCheckCompleted().isPresent() ? domain.getCheckCompleted().get().value : null,
				domain.getCategoryId().isPresent() ? domain.getCategoryId().get() : null,
				domain.getCsvDataLineNumber().isPresent() ? domain.getCsvDataLineNumber().get().v() : null,	
				domain.getCsvDataStartLine().isPresent() ? domain.getCsvDataStartLine().get().v() : null,
				domain.getCharacterCode().isPresent() ? domain.getCharacterCode().get().value : null,	
				domain.getDeleteExtDataMethod().isPresent() ? domain.getDeleteExtDataMethod().get().value : null);
	}

	public static StdAcceptCondSet entityToDomain(OiomtExAcCond entity) {
		return new StdAcceptCondSet(entity.stdAcceptCondSetPk.cid, entity.stdAcceptCondSetPk.systemType,
				entity.stdAcceptCondSetPk.conditionSetCd, entity.conditionSetName, entity.deleteExistData,
				entity.acceptMode, entity.checkCompleted, entity.categoryId, entity.csvDataLineNumber,
				entity.csvDataStartLine, entity.characterCode, entity.deleteExtDataMethod);
	}
}
