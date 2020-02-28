/**
 * 
 */
package nts.uk.ctx.hr.shared.infra.entity.medicalhistory;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author laitv
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEDT_JYUSIN_HIST_ITEM")
public class PpedtMedicalHisItem  extends UkJpaEntity implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpedtMedicalHisItemPk PpedtMedicalHisItemPk;

	// 会社ID
	@Basic(optional = true)
	@Column(name = "CID")
	public String cid;

	// 社員ID
	@Basic(optional = true)
	@Column(name = "SID")
	public String sid;

	// 受診区分
	@Basic(optional = true)
	@Column(name = "J_JYUSIN_KBN")
	public String visitNote;

	// 検診異常疾病内容
	@Basic(optional = true)
	@Column(name = "J_KENSIN_NAIYO")
	public String abnormIllness;

	// 受診履歴備考
	@Basic(optional = true)
	@Column(name = "J_JYUSIN_BIKO")
	public String consultationRemarks;

	// 定期健康診断年度
	@Basic(optional = true)
	@Column(name = "J_MEDICHEK_Y")
	public BigDecimal healthCheckupYear;

	// 既往症・現症
	@Basic(optional = true)
	@Column(name = "J_JYUSIN_KIOU")
	public String pastAndPresent;

	// 身長
	@Basic(optional = true)
	@Column(name = "J_HEIGHT")
	public BigDecimal height;

	// 体重
	@Basic(optional = true)
	@Column(name = "J_WEIGHT")
	public BigDecimal weight;

	// 標準体重
	@Basic(optional = true)
	@Column(name = "J_STANDARDWEIGHT")
	public BigDecimal standWeight;

	// 肥満度BMI
	@Basic(optional = true)
	@Column(name = "J_HIMAN_KBN")
	public BigDecimal obesityBMI;

	// 視力（右）
	@Basic(optional = true)
	@Column(name = "J_EYE_RIGHT")
	public BigDecimal visionRight;

	// 視力（左）
	@Basic(optional = true)
	@Column(name = "J_EYE_LEFT")
	public BigDecimal visionLeft;

	// 視力検査区分
	@Basic(optional = true)
	@Column(name = "J_EYECHEK_KBN")
	public String visionTestNote;

	// 聴力1000HZ（右）
	@Basic(optional = true)
	@Column(name = "J_HEAR1_RIGHT")
	public String hear1Right;

	// 聴力1000HZ（左）
	@Basic(optional = true)
	@Column(name = "J_HEAR1_LEFT")
	public String hear1Left;

	// 聴力4000HZ（右）
	@Basic(optional = true)
	@Column(name = "J_HEAR4_RIGHT")
	public String hear4Right;

	// 聴力4000HZ（左）
	@Basic(optional = true)
	@Column(name = "J_HEAR4_LEFT")
	public String hear4Left;

	// 聴力検査区分
	@Basic(optional = true)
	@Column(name = "J_HEAR_KBN")
	public String hearingTestCls;

	// 血圧（高）
	@Basic(optional = true)
	@Column(name = "J_KETUATU_HIGH")
	public BigDecimal bloodPressureHigh;

	// 血圧（低）
	@Basic(optional = true)
	@Column(name = "J_KETUATU_LOW")
	public BigDecimal bloodPressureLow;

	// 血圧検査区分
	@Basic(optional = true)
	@Column(name = "J_KETUATU_KBN")
	public String bloodPressureNote;

	// 心電図
	@Basic(optional = true)
	@Column(name = "J_HEART")
	public String electroHeart;

	// 心電図検査区分
	@Basic(optional = true)
	@Column(name = "J_HEART_KBN")
	public String inSpecElectroHeartNote;

	// 総コレステロール
	@Basic(optional = true)
	@Column(name = "J_CHOLESTEROL")
	public BigDecimal totalCholesterol;

	// 中性脂肪
	@Basic(optional = true)
	@Column(name = "J_SIBO")
	public BigDecimal neutralFat;

	// ＨＤＬコレステロール
	@Basic(optional = true)
	@Column(name = "J_HDL_CHOLESTEROL")
	public BigDecimal hdlCholesterol;

	// 血中脂質検査区分
	@Basic(optional = true)
	@Column(name = "J_SISITU_KBN")
	public String bloodLipidTestNote;

	// Ｘ線（胸部）
	@Basic(optional = true)
	@Column(name = "J_CHESTXRAY")
	public String xLineChest;

	// Ｘ線（胃部）
	@Basic(optional = true)
	@Column(name = "JSTOMACHXRAY")
	public String xRayStomach;

	// Ｘ線検査区分
	@Basic(optional = true)
	@Column(name = "J_XRAY_KBN")
	public String xRayInspecNote;

	// ＧＯＴ
	@Basic(optional = true)
	@Column(name = "J_GOT")
	public BigDecimal got;

	// ＧＰＴ
	@Basic(optional = true)
	@Column(name = "J_GTP")
	public BigDecimal gpt;

	// γ－ＧＴＰ
	@Basic(optional = true)
	@Column(name = "J_GGTP")
	public BigDecimal ggtp;

	// 尿検査区分
	@Basic(optional = true)
	@Column(name = "J_NYO_KBN")
	public String urineTestNote;

	// ヘマトクリット値
	@Basic(optional = true)
	@Column(name = "J_HEMAKURITO")
	public BigDecimal hematocritValue;

	// 肝機能検査区分
	@Basic(optional = true)
	@Column(name = "J_LIVER_KBN")
	public String liverFunctionTestNote;

	// 血糖値
	@Basic(optional = true)
	@Column(name = "J_BLOODSUGER")
	public BigDecimal bloodSuger;

	// 血色素量
	@Basic(optional = true)
	@Column(name = "J_BLSIKISO")
	public BigDecimal hemoglobinAmount;

	// 尿糖
	@Basic(optional = true)
	@Column(name = "J_NYOTO")
	public String urineSugar;

	// 赤血球数
	@Basic(optional = true)
	@Column(name = "J_REDKEKYU")
	public BigDecimal numberRedBloodCells;

	// 白血球数
	@Basic(optional = true)
	@Column(name = "J_WHITKEKYU")
	public BigDecimal whiteBloodCellCount;

	// 血糖検査区分
	@Basic(optional = true)
	@Column(name = "J_BLOODSUGER_KBN")
	public String bloodSugerNote;

	// 尿酸値
	@Basic(optional = true)
	@Column(name = "J_NYOSANCHI")
	public BigDecimal uricAcidLevel;

	// 貧血検査区分
	@Basic(optional = true)
	@Column(name = "J_ANEMIA_KBN")
	public String anemiaTestNote;

	// 大腸検査区分
	@Basic(optional = true)
	@Column(name = "J_COLON_KBN")
	public String colonTestNote;

	// 尿蛋白
	@Basic(optional = true)
	@Column(name = "J_NYOTANPAKU")
	public String urinaryProtein;

	// 婦人検査区分
	@Basic(optional = true)
	@Column(name = "J_WOMAN_KBN")
	public String womanTestNote;

	// 潜血
	@Basic(optional = true)
	@Column(name = "J_SENKETU")
	public String occultBlood;

	// クレアチニン
	@Basic(optional = true)
	@Column(name = "J_KUREACHINN")
	public BigDecimal creatinine;

	// 総合結果
	@Basic(optional = true)
	@Column(name = "J_RESULT")
	public String resultNote;

	@Override
	protected Object getKey() {
		return PpedtMedicalHisItemPk;
	}

}
