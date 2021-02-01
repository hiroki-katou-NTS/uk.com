package nts.uk.ctx.office.dom.favorite;

import java.util.List;
import java.util.Optional;
import nts.arc.time.GeneralDateTime;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.お気に入りの指定
 */
public interface FavoriteSpecifyRepository {
	/**
	 * [1] insert(お気に入りの指定)
	 * 
	 * @param domain お気に入りの指定
	 */
	public void insert(FavoriteSpecify domain);
	
	public void insertAll(List<FavoriteSpecify> domains);

	/**
	 * [2] update(お気に入りの指定)
	 * 
	 * @param domain お気に入りの指定
	 */
	public void update(FavoriteSpecify domain);
	
	public void updateAll(List<FavoriteSpecify> domains);

	/**
	 * [3] delete(お気に入りの指定)
	 * 
	 * @param domain お気に入りの指定
	 */
	public void delete(FavoriteSpecify domain);

	/**
	 * [4]取得する
	 * 
	 * @param sid
	 * @return List<FavoriteSpecify> List<お気に入りの指定>
	 */
	public List<FavoriteSpecify> getBySid(String sid);

	/**
	 * [5]取得する
	 * 
	 * @param sid
	 * @param date
	 * @return Optional<FavoriteSpecify> Optional<お気に入りの指定>
	 */
	public Optional<FavoriteSpecify> getBySidAndDate(String sid, GeneralDateTime date);
}
