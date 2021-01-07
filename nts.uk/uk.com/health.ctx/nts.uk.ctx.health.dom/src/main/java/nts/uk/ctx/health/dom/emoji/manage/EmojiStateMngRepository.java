package nts.uk.ctx.health.dom.emoji.manage;

import java.util.Optional;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.NittsuSystem.Common (NtsCommons).ヘルスライフ.感情状態管理.感情状態管理.感情状態管理
 * 感情状態管理Repository							
 */
public interface EmojiStateMngRepository {
	/**
	 * [1] Insert(感情状態管理)
	 * 
	 * @param domain 感情状態管理
	 */
	public void insert(EmojiStateMng domain);

	/**
	 * [2] Update(感情状態管理)
	 * 
	 * @param domain 感情状態管理
	 */
	public void update(EmojiStateMng domain);

	/**
	 * [3] get
	 * 
	 * @param cid 会社ID
	 * @return EmojiStateMng 感情状態管理
	 */
	public Optional<EmojiStateMng> getByCid(String cid);
}
