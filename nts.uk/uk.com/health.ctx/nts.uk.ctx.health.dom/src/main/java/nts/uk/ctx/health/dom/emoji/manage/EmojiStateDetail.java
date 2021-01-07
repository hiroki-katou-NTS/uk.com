package nts.uk.ctx.health.dom.emoji.manage;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.NittsuSystem.Common (NtsCommons).ヘルスライフ.感情状態管理.感情状態管理.感情状態詳細
 */

@Getter
@Builder
public class EmojiStateDetail extends DomainObject {

	// 感情名称
	private EmijiName emijiName;

	//感情種類
	private EmojiType emojiType;
}
