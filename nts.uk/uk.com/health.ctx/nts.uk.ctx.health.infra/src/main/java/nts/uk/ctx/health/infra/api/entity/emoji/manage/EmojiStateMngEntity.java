package nts.uk.ctx.health.infra.api.entity.emoji.manage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.health.dom.emoji.manage.EmijiName;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateDetail;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMng;
import nts.uk.ctx.health.dom.emoji.manage.EmojiType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
 * UKDesign.データベース.ER図.ヘルスライフ.感情状態管理.感情状態管理.HHLMT_MOOD_MGT
 * 感情状態管理
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "HHLMT_MOOD_MGT")
public class EmojiStateMngEntity extends UkJpaEntity
		implements EmojiStateMng.MementoGetter, EmojiStateMng.MementoSetter, Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	// column 排他バージョン
	@Column(name = "EXCLUS_VER")
	private long version;

	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// Embedded primary key 会社ID
	@EmbeddedId
	private EmojiStateMngEntityPK pk;

	// column 管理区分
	@NotNull
	@Column(name = "MGT_ATR")
	private Integer manageEmojiState;

	// column どんより名称
	@NotNull
	@Column(name = "WEARY_MD_NAME")
	private String wearyMoodName;

	// column ゆううつ名称
	@NotNull
	@Column(name = "SAD_MD_NAME")
	private String sadMoodName;

	// column 普通名称
	@NotNull
	@Column(name = "AVERAGE_MD_NAME")
	private String averageMoodName;

	// column ぼちぼち名称
	@NotNull
	@Column(name = "GOOD_MD_NAME")
	private String goodMoodName;

	// column いい感じ名称
	@NotNull
	@Column(name = "HAPPY_MD_NAME")
	private String happyMoodName;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setCid(String cid) {
		if (this.pk == null) {
			this.pk = new EmojiStateMngEntityPK();
		}
		this.pk.setCid(cid);
	}

	@Override
	public void setEmojiStateSetting(EmojiStateDetail emojiStateSetting) {
		this.manageEmojiState = emojiStateSetting.getEmojiType().value;
		switch (emojiStateSetting.getEmojiType()) {
		case WEARY:
			this.wearyMoodName = emojiStateSetting.getEmijiName().v();
			break;
		case SAD:
			this.sadMoodName = emojiStateSetting.getEmijiName().v();
			break;
		case AVERAGE:
			this.averageMoodName = emojiStateSetting.getEmijiName().v();
			break;
		case GOOD:
			this.goodMoodName = emojiStateSetting.getEmijiName().v();
			break;
		case HAPPY:
			this.happyMoodName = emojiStateSetting.getEmijiName().v();
			break;
		default:
			break;
		}
	}

	@Override
	public String getCid() {
		return this.pk.getCid();
	}

	@Override
	public EmojiStateDetail getEmojiStateSetting() {
		String emijiName = "";
		EmojiType emojiType = EnumAdaptor.valueOf(this.manageEmojiState, EmojiType.class);
		switch (emojiType) {
		case WEARY:
			emijiName = this.wearyMoodName;
			break;
		case SAD:
			emijiName = this.sadMoodName;
			break;
		case AVERAGE:
			emijiName = this.averageMoodName;
			break;
		case GOOD:
			emijiName = this.goodMoodName;
			break;
		case HAPPY:
			emijiName = this.happyMoodName;
			break;
		default:
			break;
		}
		return EmojiStateDetail.builder()
				.emijiName(new EmijiName(emijiName))
				.emojiType(emojiType)
				.build();
	}
}
