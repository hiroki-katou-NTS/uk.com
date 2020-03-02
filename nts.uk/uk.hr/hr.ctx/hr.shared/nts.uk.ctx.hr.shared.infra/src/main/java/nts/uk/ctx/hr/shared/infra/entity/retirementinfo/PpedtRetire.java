package nts.uk.ctx.hr.shared.infra.entity.retirementinfo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "PPEDT_RETIRE")
public class PpedtRetire extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 履歴ID
	@EmbeddedId
	public PeedtRetirePk key;
	// 退職予定日
	@Column(name = "J_OUT_PLANS_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate jOutPlansDate;
	// 社員ID
	@Column(name = "SID")
	public String employeeID;
	// 退職理由区分１ ID
	@Column(name = "J_OUT_RIYU_KBN1_ID")
	public String jOutRiyuKbn1ID;
	// 退職理由区分１ CD
	@Column(name = "J_OUT_RIYU_KBN1_CD")
	public String jOutRiyuKbn1CD;
	// 退職理由区分１ 名称
	@Column(name = "J_OUT_RIYU_KBN1_NAME")
	public String jOutRiyuKbn1Name;
	// 退職理由区分２ ID
	@Column(name = "J_OUT_RIYU_KBN2_ID")
	public String jOutRiyuKbn2ID;
	// 退職理由区分２ CD
	@Column(name = "J_OUT_RIYU_KBN2_CD")
	public String jOutRiyuKbn2CD;
	// 退職理由区分２ name
	@Column(name = "J_OUT_RIYU_KBN2_NAME")
	public String jOutRiyuKbn2Name;
	// 退職の備考
	@Column(name = "J_OUT_BIKO")
	public String jOutBiko;
	// 解雇予告日
	@Column(name = "J_KAIKO_YOKOKU_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate jKaikoYokokuDate;
	// 解雇予告手当支給日
	@Column(name = "J_KAIKO_YOKOKU_TEATE_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate jKaikoYokokuTeateDate;
	// 解雇の事由・手続き
	@Column(name = "J_KAIKO_JIYU")
	public String jKaikoJiyu;
	// 解雇の理由１_チェック
	@Column(name = "J_KAIKO_RIYU1_CHECK")
	public Integer jKaikoRiyuCheck1;
	// 解雇の理由１
	@Column(name = "J_KAIKO_RIYU1")
	public String jKaikoRiyu1;
	// 解雇の理由2_チェック
	@Column(name = "J_KAIKO_RIYU2_CHECK")
	public Integer jKaikoRiyuCheck2;
	// 解雇の理由2
	@Column(name = "J_KAIKO_RIYU2")
	public String jKaikoRiyu2;
	// 解雇の理由3_チェック
	@Column(name = "J_KAIKO_RIYU3_CHECK")
	public Integer jKaikoRiyuCheck3;
	// 解雇の理由3
	@Column(name = "J_KAIKO_RIYU3")
	public String jKaikoRiyu3;
	// 解雇の理由4_チェック
	@Column(name = "J_KAIKO_RIYU4_CHECK")
	public Integer jKaikoRiyuCheck4;
	// 解雇の理由4
	@Column(name = "J_KAIKO_RIYU4")
	public String jKaikoRiyu4;
	// 解雇の理由5_チェック
	@Column(name = "J_KAIKO_RIYU5_CHECK")
	public Integer jKaikoRiyuCheck5;
	// 解雇の理由5
	@Column(name = "J_KAIKO_RIYU5")
	public String jKaikoRiyu5;
	// 解雇の理由6_チェック
	@Column(name = "J_KAIKO_RIYU6_CHECK")
	public Integer jKaikoRiyuCheck6;
	// 解雇の理由6
	@Column(name = "J_KAIKO_RIYU6")
	public String jKaikoRiyu6;
	// 解雇の理由7_チェック
	@Column(name = "J_KAIKO_RIYU7_CHECK")
	public Integer jKaikoRiyuCheck7;
	// 解雇の理由7
	@Column(name = "J_KAIKO_RIYU7")
	public String jKaikoRiyu7;
	// 解雇の理由8_チェック
	@Column(name = "J_KAIKO_RIYU8_CHECK")
	public Integer jKaikoRiyuCheck8;
	// 解雇の理由8
	@Column(name = "J_KAIKO_RIYU8")
	public String jKaikoRiyu8;
	// 解雇の理由9_チェック
	@Column(name = "J_KAIKO_RIYU9_CHECK")
	public Integer jKaikoRiyuCheck9;
	// 解雇の理由9
	@Column(name = "J_KAIKO_RIYU9")
	public String jKaikoRiyu9;
	// 解雇の理由１0_チェック
	@Column(name = "J_KAIKO_RIYU10_CHECK")
	public Integer jKaikoRiyuCheck10;
	// 解雇の理由１0
	@Column(name = "J_KAIKO_RIYU10")
	public String jKaikoRiyu10;

	@Override
	protected Object getKey() {
		return key;
	}
}
