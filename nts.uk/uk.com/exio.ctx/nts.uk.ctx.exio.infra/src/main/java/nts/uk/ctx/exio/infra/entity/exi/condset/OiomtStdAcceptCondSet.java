package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 受入条件設定（定型）
 */

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_ACCEPT_COND_SET")
public class OiomtStdAcceptCondSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtStdAcceptCondSetPk stdAcceptCondSetPk;

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
	 * 受入モード
	 */
	@Basic(optional = false)
	@Column(name = "ACCEPT_MODE")
	public int acceptMode;

	/**
	 * 外部受入条件名称
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_NAME")
	public String conditionSetName;

	/**
	 * チェック完了
	 */
	@Basic(optional = false)
	@Column(name = "CHECK_COMPLETED")
	public int checkCompleted;

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

	public OiomtStdAcceptCondSet(String companyId, int systemType, String conditionSettingCode, String conditionSetName,
			int deleteExistData, int acceptMode, int checkCompleted, String categoryId, Integer csvDataLineNumber,
			Integer csvDataStartLine, Integer deleteExtDataMethod) {
		super();
		this.stdAcceptCondSetPk = new OiomtStdAcceptCondSetPk(companyId, systemType, conditionSettingCode);
		this.categoryId = categoryId;
		this.csvDataLineNumber = csvDataLineNumber;
		this.deleteExistData = deleteExistData;
		this.csvDataStartLine = csvDataStartLine;
		this.acceptMode = acceptMode;
		this.conditionSetName = conditionSetName;
		this.checkCompleted = checkCompleted;
		this.deleteExtDataMethod = deleteExtDataMethod;
	}

	public static OiomtStdAcceptCondSet domainToEntity(StdAcceptCondSet domain) {
		return new OiomtStdAcceptCondSet(domain.getCid(), domain.getSystemType(), domain.getConditionSetCd().v(),
				domain.getConditionSetName().v(), domain.getDeleteExistData(), domain.getAcceptMode().value,
				domain.getCheckCompleted(), domain.getCategoryId().isPresent() ? domain.getCategoryId().get() : null,
				domain.getCsvDataLineNumber().isPresent() ? domain.getCsvDataLineNumber().get().v() : null,
				domain.getCsvDataStartLine().isPresent() ? domain.getCsvDataStartLine().get().v() : null,
				domain.getDeleteExtDataMethod().isPresent() ? domain.getDeleteExtDataMethod().get().value : null);
	}

	public static StdAcceptCondSet entityToDomain(OiomtStdAcceptCondSet entity) {
		return new StdAcceptCondSet(entity.stdAcceptCondSetPk.cid, entity.stdAcceptCondSetPk.systemType,
				entity.stdAcceptCondSetPk.conditionSetCd, entity.conditionSetName, entity.deleteExistData,
				entity.acceptMode, entity.checkCompleted, entity.categoryId, entity.csvDataLineNumber,
				entity.csvDataStartLine, entity.deleteExtDataMethod);
	}
}
