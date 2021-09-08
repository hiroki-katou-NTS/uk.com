package nts.uk.ctx.at.request.app.find.dialog.employmentsystem.holidaysubstitute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.LeaveOccurrDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnoffsetNumSeqVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 残数確認情報を調整 UKDesign.UniversalK.就業.KDL_ダイアログ.残数確認ダイアログ共通.代休・振休.アルゴリズム.残数確認情報を調整
 * 
 * @author phongtq
 *
 */

@Stateless
public class GetRemainNumberConfirmInfo {
	// List＜逐次休暇の紐付け情報＞, List＜逐次発生の休暇明細一覧＞
	public RemainNumberConfirmInfo getRemainNumberConfirmInfo(List<SeqVacationAssociationInfo> lstSeqVacation,
			VacationDetails vacationDetails) {
		// List＜逐次発生の休暇明細一覧＞

		List<AccumulationAbsenceDetail> lstAcctAbsenDetail = vacationDetails.getLstAcctAbsenDetail();
		// Input．List＜逐次発生の休暇明細＞をソートする
		lstAcctAbsenDetail.sort((a, b) -> {
			if (a.getDateOccur().isUnknownDate() == false) {
				return (b.getDateOccur().isUnknownDate() == false) ? 0 : -1;
			}
			if (b.getDateOccur().isUnknownDate() == false) {
				return 1;
			}

			if (a.getDateOccur().getDayoffDate().isPresent() && b.getDateOccur().getDayoffDate().isPresent())
				return a.getDateOccur().getDayoffDate().get().compareTo(b.getDateOccur().getDayoffDate().get());

			return 0;
		});

		// List<残数詳細情報>を作成
		List<RemainNumberDetailedInfo> detailedInfos = new ArrayList<>();

		// List<消化日>を作成
		List<String> lstDigestionDate = new ArrayList<>();

		// 1ヶ月以内期限切れ数 ＝ ０
		String expiredWithinMonth = "0" + TextResource.localize("KDL005_47");

		// 期限の一番近い日 ＝ Empty
		String dayCloseDeadline = "";

		for (AccumulationAbsenceDetail detail : lstAcctAbsenDetail) {
			// 残数詳細情報を作成
			RemainNumberDetailedInfo detailedInfo = new RemainNumberDetailedInfo("", "", "", "", "", "", "", "", "");

			boolean checkDate = false;

			if (detail.getDateOccur().getDayoffDate().isPresent())
				checkDate = lstDigestionDate.contains(detail.getDateOccur().getDayoffDate().get().toString());
			// Trueの場合
			if (detail.getOccurrentClass() == OccurrenceDigClass.DIGESTION && checkDate == true) {

			} else {
				// 以外の場合
				if (detail.getOccurrentClass() == OccurrenceDigClass.DIGESTION) {

					// ループ中の逐次発生の休暇明細一覧．年月日．日付不明 ＝＝ True
					if (detail.getDateOccur().isUnknownDate()) {
						// 残数詳細情報．消化日をセットする - 消化日
						detailedInfo.setDigestionDate(TextResource.localize("KDL005_52"));
					} else {

						// ループ中の逐次発生の休暇明細一覧．年月日．年月日 ＞ システム日付
						if (detail.getDateOccur().getDayoffDate().get().after(GeneralDate.today())) {
							// 残数詳細情報．消化日状況をセットする - 消化日状況
							detailedInfo.setDigestionDateStatus(TextResource.localize("KDL005_40"));
						}

						// 残数詳細情報．消化日をセットする - 消化日
						detailedInfo.setDigestionDate(TextResource.localize("KDL005_41",
								detail.getDateOccur().getDayoffDate().get().toString(), // ループ中の逐次発生の休暇明細一覧．年月日．年月日
								String.valueOf(detail.getDateOccur().getDayoffDate().get().dayOfWeek()))); // ループ中の逐次発生の休暇明細一覧．年月日．年月日の曜日
					}

					// ループ中の逐次発生の休暇明細一覧．発生数．時間 ！＝ Empty
					if (detail.getNumberOccurren().getTime().isPresent()) {
						// 残数詳細情報．消化数をセットする
						String minu = String.valueOf(detail.getNumberOccurren().getTime().get().minute()).length() > 1
								? String.valueOf(detail.getNumberOccurren().getTime().get().minute())
								: 0 + String.valueOf(detail.getNumberOccurren().getTime().get().minute());
						String hoursMinu = String.valueOf(detail.getNumberOccurren().getTime().get().hour()) + ":"
								+ minu;
						detailedInfo.setDigestionCount(hoursMinu);
					} else {
						detailedInfo.setDigestionCount(
								TextResource.localize("KDL005_27", detail.getNumberOccurren().getDay().toString())); // ループ中の逐次発生の休暇明細一覧．発生数．日数
					}
				} else {// 発生の場合

					// ループ中の逐次発生の休暇明細一覧．年月日．日付不明 ＝＝ True
					if (detail.getDateOccur().isUnknownDate()) {
						// 残数詳細情報．発生日をセットする - 発生日
						detailedInfo.setAccrualDate(TextResource.localize("KDL005_52"));
					} else {

						// ループ中の逐次発生の休暇明細一覧．年月日．年月日 ＞ システム日付
						if (detail.getDateOccur().getDayoffDate().get().after(GeneralDate.today())) {
							// 残数詳細情報．発生日状況をセットする - 発生日状況
							detailedInfo.setOccurrenceDateStatus(TextResource.localize("KDL005_40"));
						}
						String textDay = "";
						switch (detail.getDateOccur().getDayoffDate().get().dayOfWeek()) {
						case 1:
							textDay = "(月)";
							break;
						case 2:
							textDay = "(火)";
							break;
						case 3:
							textDay = "(水)";
							break;
						case 4:
							textDay = "(木)";
							break;
						case 5:
							textDay = "(金)";
							break;
						case 6:
							textDay = "(土)";
							break;	
						default:
							textDay = "(日)";
							break;
						}

						// 残数詳細情報．発生日をセットする - 発生日
						detailedInfo.setAccrualDate(TextResource.localize("KDL005_41",
								detail.getDateOccur().getDayoffDate().get().toString(), // ループ中の逐次発生の休暇明細一覧．年月日．年月日
								textDay)); // ループ中の逐次発生の休暇明細一覧．年月日．年月日の曜日;
					}
					// ループ中の逐次発生の休暇明細一覧．発生数．時間 ！＝ Empty
					if (detail.getNumberOccurren().getTime().isPresent()) {
						// 残数詳細情報．発生数をセットする
						String minu = String.valueOf(detail.getNumberOccurren().getTime().get().minute()).length() > 1
								? String.valueOf(detail.getNumberOccurren().getTime().get().minute())
								: 0 + String.valueOf(detail.getNumberOccurren().getTime().get().minute());
						String hoursMinu = String.valueOf(detail.getNumberOccurren().getTime().get().hour()) + ":"
								+ minu;
						detailedInfo.setNumberOccurrences(hoursMinu);

					} else {
						detailedInfo.setNumberOccurrences(
								TextResource.localize("KDL005_27", detail.getNumberOccurren().getDay().toString())); // ループ中の逐次発生の休暇明細一覧．発生数．日数
					}
					// ループ中の逐次発生の休暇明細．休暇発生明細
					LeaveOccurrDetail occurrDetail = (LeaveOccurrDetail) detail; // get LeaveOccurrDetail extends AccumulationAbsenceDetail
					// 残数詳細情報．消化状況をセットする
					// ループ中の逐次発生の休暇明細一覧．休暇発生明細．消化区分 ＝ 消化済
					if (occurrDetail.getDigestionCate() == DigestionAtr.USED) {
						detailedInfo.setDigestionStatus(TextResource.localize("KDL005_44"));
					}

					// ループ中の逐次発生の休暇明細一覧．休暇発生明細．消化区分 ＝ 未消化
					if (occurrDetail.getDigestionCate() == DigestionAtr.UNUSED) {
						// ループ中の逐次発生の休暇明細一覧．休暇発生明細．期限日 ＜ システム日付
						if (occurrDetail.getDeadline().before(GeneralDate.today())) {
							detailedInfo.setDigestionStatus(TextResource.localize("KDL005_42"));
						} else {
							// ループ中の逐次発生の休暇明細一覧．休暇発生明細．期限日 > システム日付
							String text = detail.getUnbalanceNumber().getTime().isPresent()
									? detail.getUnbalanceNumber().getTime() + ""
									: detail.getUnbalanceNumber().getDay() + TextResource.localize("KDL005_47");

							detailedInfo.setDigestionStatus(TextResource.localize("KDL005_51", text));
						}
					}

					// 残数詳細情報．期限日状況をセットする - 期限日状況
					// Input．逐次発生の休暇明細一覧．未相殺の合計を取得する() を呼び出す - can hoi lai
					UnoffsetNumSeqVacation numSeqVacation = vacationDetails.getTotalUnoffset();

					// Input．逐次発生の休暇明細一覧．時系列にソートする() を呼び出す
					List<AccumulationAbsenceDetail> lstAcctAbsenSort = vacationDetails.sortAccAbsDetailASC();

					// 逐次発生の休暇明細一覧．発生消化区分 ＝＝ 発生
					// AND 逐次発生の休暇明細一覧．休暇発生明細．消化区分　＝＝　未消化
					// AND 逐次発生の休暇明細一覧．年月日．日付不明 ＝＝ False
					Optional<AccumulationAbsenceDetail> acctAbsenFil = lstAcctAbsenSort.stream()
							.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE
									&& ((LeaveOccurrDetail) x).getDigestionCate() == DigestionAtr.UNUSED
									&& !x.getDateOccur().isUnknownDate())
							.findFirst();

					if (acctAbsenFil.isPresent()) {
						// 探した逐次発生の休暇明細一覧．休暇発生明細
						LeaveOccurrDetail occurrDetailFil = (LeaveOccurrDetail) acctAbsenFil.get(); // get LeaveOccurrDetail extends AccumulationAbsenceDetail
						// 探した逐次発生の休暇明細一覧．休暇発生明細．期限日＝＝ループ中の逐次発生の休暇明細．休暇発生明細．期限日
						if (occurrDetailFil.getDeadline().equals(occurrDetail.getDeadline())) {
							detailedInfo.setDueDateStatus(TextResource.localize("KDL005_45"));
						}
					}

					// 残数詳細情報．期限日状況をセットする
					detailedInfo.setDeadline(occurrDetail.getDeadline().toString());

					// 逐次休暇の紐付け情報を絞り込む
					Optional<SeqVacationAssociationInfo> seqVacationFil = lstSeqVacation.stream()
							.filter(x -> detail.getDateOccur().getDayoffDate().isPresent()
									&& x.getOutbreakDay().equals(detail.getDateOccur().getDayoffDate().get()))
							.findFirst();

					// 残数詳細情報の消化をセットする
					if (seqVacationFil.isPresent()) {
						// 残数詳細情報．消化日状態
						detailedInfo.setDigestionDateStatus(TextResource.localize("KDL005_49"));
						// 残数詳細情報．消化数
						detailedInfo.setDigestionCount(
								TextResource.localize("KDL005_27", seqVacationFil.get().getDayNumberUsed().toString())); // 絞り込んだ逐次休暇の紐付け情報．使用日数
						// 残数詳細情報．消化日
						detailedInfo.setDigestionDate(
								TextResource.localize("KDL005_41", seqVacationFil.get().getDateOfUse().toString(),
										seqVacationFil.get().getDateOfUse().dayOfWeek() + ""));

						lstDigestionDate.add(seqVacationFil.get().getOutbreakDay().toString());
					}
				}
			}
			// List<残数詳細情報>に作成した残数詳細情報を追加
			detailedInfos.add(detailedInfo);
		}
		// Input．逐次発生の休暇明細一覧．指定した期間内に未消化となる情報を取得するを呼び出す
		DatePeriod dateperiod = new DatePeriod(GeneralDate.today().addMonths(-1), GeneralDate.today());
		List<AccumulationAbsenceDetail> undigestInfoInPeriod = vacationDetails.getUndigestInfoInPeriod(dateperiod);
		VacationDetails vacationDetailsNew = new VacationDetails(undigestInfoInPeriod);
		// 1ヶ月以内期限切れ数をセットする
		UnoffsetNumSeqVacation totalUnoffset = vacationDetailsNew.getTotalUnoffset();
		if (totalUnoffset.getDays() != null) {
			expiredWithinMonth = totalUnoffset.getDays() + TextResource.localize("KDL005_47");
		}

		if (totalUnoffset.getRemainTime() != null) {
			expiredWithinMonth = totalUnoffset.getRemainTime() + "";
		}

		// 期限の一番近い日をセットする
		List<AccumulationAbsenceDetail> accAbsDetail = vacationDetails.sortAccAbsDetailASC();
		if (!accAbsDetail.isEmpty()) {

			Optional<AccumulationAbsenceDetail> undigestInfoFil = accAbsDetail.stream().filter(x -> {
				return x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE && // 逐次発生の休暇明細一覧．発生消化区分 ＝＝ 発生
				((LeaveOccurrDetail) x).getDigestionCate() == DigestionAtr.UNUSED && // 逐次発生の休暇明細一覧．発生数．消化区分 ＝＝ 未消化 -
				!x.getDateOccur().isUnknownDate();
			}) // 逐次発生の休暇明細一覧．年月日．日付不明 ＝＝ False
					.findFirst();
			if (undigestInfoFil.isPresent()) {
				String text = "";
				LeaveOccurrDetail occurrDetailNew = (LeaveOccurrDetail) undigestInfoFil.get();
				// dayCloseDeadline;
				text = TextResource.localize("KDL005_41", occurrDetailNew.getDeadline().toString(),
						occurrDetailNew.getDeadline().dayOfWeek() + "");
				// 探した逐次発生の休暇明細一覧．発生数．時間数 ！＝ Empty
				if (undigestInfoFil.get().getNumberOccurren().getTime().isPresent()) {
					// 探した逐次発生の休暇明細一覧．発生数．時間数 - 探した逐次発生の休暇明細一覧．未相殺数．時間数
					Integer time = undigestInfoFil.get().getNumberOccurren().getTime().get().v()
							- undigestInfoFil.get().getUnbalanceNumber().getTime().get().v();
					String minu = String.valueOf(time % 60).length() > 1 ? String.valueOf(time % 60)
							: 0 + String.valueOf(time % 60);
					text = text + String.valueOf(time / 60) + ":" + minu;
					// 探した逐次発生の休暇明細一覧．発生数．日数 - 探した逐次発生の休暇明細一覧．未相殺数．日数
					dayCloseDeadline = text;
				}
			}
		}

		RemainNumberConfirmInfo confirmInfo = new RemainNumberConfirmInfo(detailedInfos, expiredWithinMonth,
				dayCloseDeadline);
		return confirmInfo;
	}
}
