package nts.uk.ctx.at.request.infra.repository.application;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;

public interface FindAppCommonForNR<T extends Application> {

	// 申請者 と申請日から申請を取得する
	public List<T> findWithSidDate(String companyId, String sid, GeneralDate date);

	// 申請を取得する
	public List<T> findWithSidDateApptype(String companyId, String sid, GeneralDate date, GeneralDateTime inputDate,
			PrePostAtr prePostAtr);

	// 申請者 と期間から申請を取得する
	public List<T> findWithSidDatePeriod(String companyId, String sid, DatePeriod period);

	public default Optional<Application> findAppId(List<Application> lstApp, String appId) {
		return lstApp.stream().filter(x -> x.getAppID().equals(appId)).findFirst();
	}
}
