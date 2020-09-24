package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * データ保存のパターン設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "SSPMT_DATASTO_PATTERN_SET")
public class SspmtDataStoragePatternSetting extends UkJpaEntity implements Serializable, DataStoragePatternSetting.MementoGetter,
																		   DataStoragePatternSetting.MementoSetter {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public SspmtDataStoragePatternSettingPk sspmtDataStoragePatternSettingPK;
	
	/**
	 * パターン名
	 */
	@Basic(optional = false)
	@Column(name = "PATTERN_NAME")
	public String patternName;
	
	/**
	 * 調査用保存の識別
	 */
	@Basic(optional = false)
	@Column(name = "IDEN_SURVEY_ARCH")
	public int idenSurveyArch;
	
	/**
	 * パスワード有無
	 */
	@Basic(optional = false)
	@Column(name = "WITHOUT_PASSWORD")
	public boolean withoutPassword;
	
	/**
	 * 日次参照年
	 */
	@Basic(optional = true)
	@Column(name = "DAILY_REFER_YEAR")
	public Integer dailyReferYear;
	
	/**
	 * 日次参照月
	 */
	@Basic(optional = true)
	@Column(name = "DAILY_REFER_MONTH")
	public Integer dailyReferMonth;
	
	/**
	 * 月次参照年
	 */
	@Basic(optional = true)
	@Column(name = "MONTH_REFER_YEAR")
	public Integer monthlyReferYear;
	
	/**
	 * 月次参照月
	 */
	@Basic(optional = true)
	@Column(name = "MONTH_REFER_MONTH")
	public Integer monthlyReferMonth;
	
	/**
	 * 年次参照月
	 */
	@Basic(optional = true)
	@Column(name = "ANNUAL_REFER_MONTH")
	public Integer annualReferMonth;
	
	/**
	 * パターン圧縮パスワード
	 */
	@Basic(optional = true)
	@Column(name = "PATTERN_COMPRE_PASS")
	public String patternCompressionPwd;
	
	/**
	 * パターン補足説明
	 */
	@Basic(optional = true)
	@Column(name = "PATTERN_SUPLE_EXPLAN")
	public String patternSuppleExplanation;
	
	@Override
	protected Object getKey() {
		return sspmtDataStoragePatternSettingPK;
	}

	@Override
	public void setCategoryId(String categoryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSystemType(int systemType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWithoutPassword(int withoutPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPatternCode(String patternCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPatternClassification(int patternClassification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContractCode(String contractCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCategoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSystemType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWithoutPassword() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPatternCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPatternClassfication() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContractCode() {
		// TODO Auto-generated method stub
		return null;
	}
}