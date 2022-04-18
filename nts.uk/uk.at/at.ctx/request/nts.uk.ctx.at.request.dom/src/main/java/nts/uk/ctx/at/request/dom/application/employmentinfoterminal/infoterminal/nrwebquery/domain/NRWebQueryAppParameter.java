package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.text.SimpleDateFormat;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;

/**
 * @author thanh_nx
 *
 *         NRWeb照会申請パラメータークエリ
 */
@Getter
public class NRWebQueryAppParameter {

	// date
	// 申請対象年月日
	//yyyymmdd
	private Optional<String> dateString;

	// kbn
	// 申請種類
	private Optional<ApplicationType> kbn;

	// jikbn
	// 事前事後区分
	private Optional<PrePostAtr> jikbn;

	// ndate
	// 秒まで指定 
	//yyyymmddhhmmss
	private Optional<String> ndate;

	public NRWebQueryAppParameter(Optional<String> date, Optional<ApplicationType> kbn, Optional<PrePostAtr> jikbn,
			Optional<String> ndate) {
		this.dateString = date;
		this.kbn = kbn;
		this.jikbn = jikbn;
		this.ndate = ndate;
	}

	// [1] 引数チェック
	public ApplicationArgument argumentCheck(ApplicationType appType) {
		if (!this.kbn.isPresent() && !this.jikbn.isPresent() && !this.ndate.isPresent()
				&& this.dateString.map(x -> x.length()).orElse(0) == 8) {
			return ApplicationArgument.PT1;
		} else if (this.kbn.isPresent() && this.jikbn.isPresent() && this.ndate.isPresent()
				&& this.dateString.map(x -> x.length()).orElse(0) == 8) {
			if (appType == kbn.get())
				return ApplicationArgument.PT2;
			else
				return ApplicationArgument.PT4;
		} else if (!this.kbn.isPresent() && !this.jikbn.isPresent() && !this.ndate.isPresent()
				&& this.dateString.map(x -> x.length()).orElse(0) == 6) {
			return ApplicationArgument.PT3;
		} else {
			return ApplicationArgument.PT4;
		}

	}
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public Optional<GeneralDate> getDate() {
		if (!this.dateString.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(GeneralDate.fromString(this.dateString.get(), DATE_FORMAT.toPattern()));
	}

	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	public Optional<GeneralDateTime> getDateTime() {
		if (!this.ndate.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(GeneralDateTime.fromString(this.ndate.get(), DATE_TIME_FORMAT.toPattern()));
	}
}
