package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
	
/**
 * @author laitv domain 受診履歴
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Stateless
public class MedicalhistoryItem extends AggregateRoot {

	@Inject
	private MedicalhistoryRepository repo;
	
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

	ListMedicalhistory listMedicalhistory;

	// 受診履歴の取得
	public ListMedicalhistory getListMedicalhistoryItem(List<String> listSID, GeneralDateTime baseDate) {
		
		List<MedicalhistoryItem> listDomain = repo.getListMedicalhistoryItem(listSID, baseDate);

		this.listMedicalhistory = new ListMedicalhistory(listDomain, listSID);

		return this.listMedicalhistory;
	}

	// 受診履歴の取得
	public MedicalhistoryItem getMedicalhistoryItem(String sId, GeneralDateTime baseDate) {
		Optional<MedicalhistoryItem> domainOpt = this.listMedicalhistory.listMedicalhistoryItem
				.stream().filter(e -> e.sid.equals(sId)).findFirst();
		if (domainOpt.isPresent()) {
			return domainOpt.get();
		} else {
			List<MedicalhistoryItem> domain = repo
					.getListMedicalhistoryItem(Arrays.asList(sId), baseDate);
			if (domain.isEmpty()) {
				return null;
			}
			return domain.get(0);
		}
	}

	public MedicalhistoryItem(String cid, String sid, String hisId, String visitNote, String abnormIllness,
			String consultationRemarks, BigDecimal healthCheckupYear, String pastAndPresent, BigDecimal height,
			BigDecimal weight, BigDecimal standWeight, BigDecimal obesityBMI, BigDecimal visionRight,
			BigDecimal visionLeft, String visionTestNote, String hear1Right, String hear1Left, String hear4Right,
			String hear4Left, String hearingTestCls, BigDecimal bloodPressureHigh, BigDecimal bloodPressureLow,
			String bloodPressureNote, String electroHeart, String inSpecElectroHeartNote, BigDecimal totalCholesterol,
			BigDecimal neutralFat, BigDecimal hdlCholesterol, String bloodLipidTestNote, String xLineChest,
			String xRayStomach, String xRayInspecNote, BigDecimal got, BigDecimal gpt, BigDecimal ggtp,
			String urineTestNote, BigDecimal hematocritValue, String liverFunctionTestNote, BigDecimal bloodSuger,
			BigDecimal hemoglobinAmount, String urineSugar, BigDecimal numberRedBloodCells,
			BigDecimal whiteBloodCellCount, String bloodSugerNote, BigDecimal uricAcidLevel, String anemiaTestNote,
			String colonTestNote, String urinaryProtein, String womanTestNote, String occultBlood,
			BigDecimal creatinine, String resultNote) {
		super();
		this.cid = cid;
		this.sid = sid;
		this.hisId = hisId;
		this.visitNote = visitNote;
		this.abnormIllness = abnormIllness;
		this.consultationRemarks = consultationRemarks;
		this.healthCheckupYear = healthCheckupYear;
		this.pastAndPresent = pastAndPresent;
		this.height = height;
		this.weight = weight;
		this.standWeight = standWeight;
		this.obesityBMI = obesityBMI;
		this.visionRight = visionRight;
		this.visionLeft = visionLeft;
		this.visionTestNote = visionTestNote;
		this.hear1Right = hear1Right;
		this.hear1Left = hear1Left;
		this.hear4Right = hear4Right;
		this.hear4Left = hear4Left;
		this.hearingTestCls = hearingTestCls;
		this.bloodPressureHigh = bloodPressureHigh;
		this.bloodPressureLow = bloodPressureLow;
		this.bloodPressureNote = bloodPressureNote;
		this.electroHeart = electroHeart;
		this.inSpecElectroHeartNote = inSpecElectroHeartNote;
		this.totalCholesterol = totalCholesterol;
		this.neutralFat = neutralFat;
		this.hdlCholesterol = hdlCholesterol;
		this.bloodLipidTestNote = bloodLipidTestNote;
		this.xLineChest = xLineChest;
		this.xRayStomach = xRayStomach;
		this.xRayInspecNote = xRayInspecNote;
		this.got = got;
		this.gpt = gpt;
		this.ggtp = ggtp;
		this.urineTestNote = urineTestNote;
		this.hematocritValue = hematocritValue;
		this.liverFunctionTestNote = liverFunctionTestNote;
		this.bloodSuger = bloodSuger;
		this.hemoglobinAmount = hemoglobinAmount;
		this.urineSugar = urineSugar;
		this.numberRedBloodCells = numberRedBloodCells;
		this.whiteBloodCellCount = whiteBloodCellCount;
		this.bloodSugerNote = bloodSugerNote;
		this.uricAcidLevel = uricAcidLevel;
		this.anemiaTestNote = anemiaTestNote;
		this.colonTestNote = colonTestNote;
		this.urinaryProtein = urinaryProtein;
		this.womanTestNote = womanTestNote;
		this.occultBlood = occultBlood;
		this.creatinine = creatinine;
		this.resultNote = resultNote;
	}
}
