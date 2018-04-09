package nts.uk.ctx.sys.gateway.dom.mail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.mail.UrlExecInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.url.EmbeddedUrlScreenID;
import nts.uk.shr.com.url.RegisterEmbededURL;
import nts.uk.shr.com.url.UrlExecInfo;
import nts.uk.shr.com.url.UrlTaskIncre;
/**
 * @author hiep.ld
 *
 */
@Stateless
public class RegisterEmbededURLImpl implements RegisterEmbededURL {

	@Inject
	private UrlExecInfoRepository urlExcecInfoRepo;

	@Override
	public UrlExecInfo obtainApplicationEmbeddedUrl(String appId, int appType, int prePostAtr, String employeeId) {
		String loginId = AppContexts.user().employeeId();
		String uuID = UUID.randomUUID().toString();
		String a = "1";
		return this.registerEmbeddedForApp(appId, appType, prePostAtr, loginId, employeeId);
	}

	@Override
	public UrlExecInfo registerEmbeddedForApp(String appId, int appType, int prePostAtr, String loginId, String employeeId) {
		EmbeddedUrlScreenID embeddedUrlScreenID = this.getEmbeddedUrlRequestScreenID(appType, prePostAtr);
		List<UrlTaskIncre> taskInce = new ArrayList<>();
		taskInce.add(UrlTaskIncre.createFromJavaType(null, null, null, appId, appId));
		return this.embeddedUrlInfoRegis(embeddedUrlScreenID.getProgramId(), embeddedUrlScreenID.getDestinationId(), 1, 1, employeeId, loginId, taskInce);
	}

	@Override
	public UrlExecInfo embeddedUrlInfoRegis(String programId, String screenId, int periodCls, int numOfPeriod,
			String employeeId, String loginId, List<UrlTaskIncre> taskIncidental) {
		if (loginId.isEmpty() && employeeId.isEmpty()) {
			return null;
		} else {
			String cid = AppContexts.user().companyId();
			// Request list 313
			String scd = "nothinghere";
			String contractCd = AppContexts.user().contractCode();
			GeneralDateTime issueDate = GeneralDateTime.now();
			GeneralDateTime startDate = GeneralDateTime.now();
			GeneralDateTime expiredDate = this.getEmbeddedUrlExpriredDate(startDate, periodCls, numOfPeriod);
			return this.updateEmbeddedUrl(cid, contractCd, loginId, scd, employeeId, programId, screenId, issueDate, expiredDate, taskIncidental);
		}
	}

	@Override
	public EmbeddedUrlScreenID getEmbeddedUrlRequestScreenID(int appType, int prePostAtr) {
		ApplicationType appTypeEnum = ApplicationType.values()[appType];
		switch (appTypeEnum) {
			case STAMP_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF002", "C");
			}
			case OVER_TIME_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF005", "B");
			}
			case ABSENCE_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF006", "B");
			}
			case WORK_CHANGE_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF007", "B");
			}
			case GO_RETURN_DIRECTLY_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF009", "B");
			}
			case EARLY_LEAVE_CANCEL_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF004", "E");
			}
			case COMPLEMENT_LEAVE_APPLICATION: {
				PrePostInitialAtr prePostAtrEnum = PrePostInitialAtr.values()[prePostAtr];
				switch (prePostAtrEnum) {
					case PRE: {
						return new EmbeddedUrlScreenID("KAF004", "E");
					}
				}
				return new EmbeddedUrlScreenID("KAF004", "F");
			}
			case BREAK_TIME_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF010", "B");
			}
			case ANNUAL_HOLIDAY_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF012", "B");
			}
			case LONG_BUSINESS_TRIP_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF008", "D");
			}
			case BUSINESS_TRIP_APPLICATION_OFFICE_HELPER: {
				return new EmbeddedUrlScreenID("KAF008", "E");
			}
		}
		return null;
	}

	@Override
	public GeneralDateTime getEmbeddedUrlExpriredDate(GeneralDateTime startDate, int periodCls, int numOfPeriod) {
		PeriodClassification periodClsEnum = PeriodClassification.values()[periodCls];
		switch (periodClsEnum) {
			case YEAR: {
				// TO DO
			}
			case MONTH: {
				GeneralDateTime expiredDate = startDate.addMonths(numOfPeriod);
				expiredDate = expiredDate.addSeconds(- expiredDate.seconds() - 1);
				return expiredDate;
			}
			case DAY: {
				// TO DO
			}
			case HOUR: {
				// TO DO
			}
			case MINUTE: {
				// TO DO
			}
		}
		return null;
	}

	@Override
	public UrlExecInfo updateEmbeddedUrl(String cid, String contractCd, String loginId, String scd, String sid,
			String programId, String screenId, GeneralDateTime issueDate, GeneralDateTime expiredDate,
			List<UrlTaskIncre> taskIncre) {
		String embeddedId = UUID.randomUUID().toString();
		try{
			taskIncre.forEach(x -> {
				x.setCid(cid);
				x.setEmbeddedId(embeddedId);
				x.setTaskIncreId(UUID.randomUUID().toString());
			});
			UrlExecInfo url = UrlExecInfo.createFromJavaType(embeddedId, cid, programId, loginId, contractCd, expiredDate, issueDate, screenId, sid, scd, taskIncre);
			this.urlExcecInfoRepo.add(url);
			return url;
		} catch (Exception e) {
			return null;
		}
	}
	

}
