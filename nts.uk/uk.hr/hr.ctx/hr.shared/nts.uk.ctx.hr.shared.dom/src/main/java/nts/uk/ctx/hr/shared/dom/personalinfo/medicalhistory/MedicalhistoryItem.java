package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.math.BigDecimal;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
	
/**
 * @author laitv domain 受診履歴
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Stateless
public class MedicalhistoryItem extends AggregateRoot {

	// 会社ID
	String cid;

	// 社員ID
	String sid;

	// 履歴ID
	String hisId;

	// 受診区分
	String visitNote;

	// 検診異常疾病内容
	String abnormIllness;

	// 受診履歴備考
	String consultationRemarks;

	// 定期健康診断年度
	BigDecimal healthCheckupYear;

	// 既往症・現症
	String pastAndPresent;

	// 身長
	BigDecimal height;

	// 体重
	BigDecimal weight;

	// 標準体重
	BigDecimal standWeight;

	// 肥満度BMI
	BigDecimal obesityBMI;

	// 視力（右）
	BigDecimal visionRight;

	// 視力（左）
	BigDecimal visionLeft;

	// 視力検査区分
	String visionTestNote;

	// 聴力1000HZ（右）
	String hear1Right;

	// 聴力1000HZ（左）
	String hear1Left;

	// 聴力4000HZ（右）
	String hear4Right;

	// 聴力4000HZ（左）
	String hear4Left;

	// 聴力検査区分
	String hearingTestCls;

	// 血圧（高）
	BigDecimal bloodPressureHigh;

	// 血圧（低）
	BigDecimal bloodPressureLow;

	// 血圧検査区分
	String bloodPressureNote;

	// 心電図
	String electroHeart;

	// 心電図検査区分
	String inSpecElectroHeartNote;

	// 総コレステロール
	BigDecimal totalCholesterol;

	// 中性脂肪
	BigDecimal neutralFat;

	// ＨＤＬコレステロール
	BigDecimal hdlCholesterol;

	// 血中脂質検査区分
	String bloodLipidTestNote;

	// Ｘ線（胸部）
	String xLineChest;

	// Ｘ線（胃部）
	String xRayStomach;

	// Ｘ線検査区分
	String xRayInspecNote;

	// ＧＯＴ
	BigDecimal got;

	// ＧＰＴ
	BigDecimal gpt;

	// γ－ＧＴＰ
	BigDecimal ggtp;

	// 尿検査区分
	String urineTestNote;

	// ヘマトクリット値
	BigDecimal hematocritValue;

	// 肝機能検査区分
	String liverFunctionTestNote;

	// 血糖値
	BigDecimal bloodSuger;

	// 血色素量
	BigDecimal hemoglobinAmount;

	// 尿糖
	String urineSugar;

	// 赤血球数
	BigDecimal numberRedBloodCells;

	// 白血球数
	BigDecimal whiteBloodCellCount;

	// 血糖検査区分
	String bloodSugerNote;

	// 尿酸値
	BigDecimal uricAcidLevel;

	// 貧血検査区分
	String anemiaTestNote;

	// 大腸検査区分
	String colonTestNote;

	// 尿蛋白
	String urinaryProtein;

	// 婦人検査区分
	String womanTestNote;

	// 潜血
	String occultBlood;

	// クレアチニン
	BigDecimal creatinine;

	// 総合結果
	String resultNote;
}
