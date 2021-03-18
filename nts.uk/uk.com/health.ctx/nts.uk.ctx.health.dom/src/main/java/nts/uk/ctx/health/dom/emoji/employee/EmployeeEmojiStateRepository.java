package nts.uk.ctx.health.dom.emoji.employee;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.NittsuSystem.Common (NtsCommons).ヘルスライフ.感情状態管理.感情状態管理.社員の感情状態
 * 社員の感情状態Repository
 */
public interface EmployeeEmojiStateRepository {
	/**
	 * [1] Insert(社員の感情状態)
	 * 
	 * @param domain 社員の感情状態
	 */
	public void insert(EmployeeEmojiState domain);

	/**
	 * [2] Update(社員の感情状態)
	 * 
	 * @param domain 社員の感情状態
	 */
	public void update(EmployeeEmojiState domain);

	/**
	 * [3] get
	 * 
	 * @param sid  社員ID
	 * @param date 年月日
	 * @return Optional<EmployeeEmojiState> Optional<社員の感情状態>
	 * 
	 */
	public Optional<EmployeeEmojiState> getBySidAndDate(String sid, GeneralDate date);

	/**
	 * [4] get
	 * 
	 * @param sids List<社員ID>
	 * @param date 年月日
	 * @return List<EmployeeEmojiState> List<社員の感情状態>
	 */
	public List<EmployeeEmojiState> getByListSidAndDate(List<String> sids, GeneralDate date);

	/**
	 * [5] get
	 * 
	 * @param sids   List<社員ID>
	 * @param period 期間
	 * @return List<EmployeeEmojiState> List<社員の感情状態>
	 */
	public List<EmployeeEmojiState> getByListSidAndPeriod(List<String> sids, DatePeriod period);
}
