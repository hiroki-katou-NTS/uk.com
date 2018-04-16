package nts.uk.ctx.sys.gateway.dom.mail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
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

	public String obtainApplicationEmbeddedUrl(String appId, int appType, int prePostAtr, String employeeId) {
		String loginId = AppContexts.user().employeeCode();
		return this.registerEmbeddedForApp(appId, appType, prePostAtr, loginId, employeeId);
	}

	@Override
	public String registerEmbeddedForApp(String appId, int appType, int prePostAtr, String loginId, String employeeId) {
		EmbeddedUrlScreenID embeddedUrlScreenID = this.getEmbeddedUrlRequestScreenID(appType, prePostAtr);
		List<UrlTaskIncre> taskInce = new ArrayList<>();
		taskInce.add(UrlTaskIncre.createFromJavaType(null, null, null, appId, appId));
		return this.embeddedUrlInfoRegis(embeddedUrlScreenID.getProgramId(), embeddedUrlScreenID.getDestinationId(), 1, 1, employeeId, loginId, taskInce);
	}

	@Override
	public String embeddedUrlInfoRegis(String programId, String screenId, int periodCls, int numOfPeriod,
			String employeeId, String loginId, List<UrlTaskIncre> taskIncidental) {
		if (loginId.isEmpty() && employeeId.isEmpty()) {
			return null;
		} else {
			String cid = AppContexts.user().companyId();
			// Request list 313
			String sLoginId = "s-logincd";
			String scd = "000001";
			if (!Strings.isBlank(sLoginId)){
				scd = sLoginId;
			}
			String contractCd = AppContexts.user().contractCode();
			GeneralDateTime issueDate = GeneralDateTime.now();
			GeneralDateTime startDate = GeneralDateTime.now();
			GeneralDateTime expiredDate = this.getEmbeddedUrlExpriredDate(startDate, periodCls, numOfPeriod);
			UrlExecInfo urlInfo = this.updateEmbeddedUrl(cid, contractCd, loginId, scd, sLoginId, programId, screenId, issueDate, expiredDate, taskIncidental);
			if (!Objects.isNull(urlInfo)){
				return (programId + "" + screenId+"/"+ urlInfo.getEmbeddedId());
			}
			return null;
		}
	}

	@Override
	public EmbeddedUrlScreenID getEmbeddedUrlRequestScreenID(int appType, int prePostAtr) {
		ApplicationType appTypeEnum = EnumAdaptor.valueOf(appType, ApplicationType.class);
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
				PrePostInitialAtr prePostAtrEnum = EnumAdaptor.valueOf(prePostAtr, PrePostInitialAtr.class);
				switch (prePostAtrEnum) {
					case PRE: {
						return new EmbeddedUrlScreenID("KAF004", "E");
					}
					case POST: {
						return new EmbeddedUrlScreenID("KAF004", "F");
					}
					case NO_CHOISE: {
						return new EmbeddedUrlScreenID("", "");
					}
				}
			}
			case BREAK_TIME_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF010", "B");
			}
			case COMPLEMENT_LEAVE_APPLICATION: {
				return new EmbeddedUrlScreenID("KAF011", "B");
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
		return new EmbeddedUrlScreenID("", "");
	}

	@Override
	public GeneralDateTime getEmbeddedUrlExpriredDate(GeneralDateTime startDate, int periodCls, int numOfPeriod) {
		PeriodClassification periodClsEnum = PeriodClassification.values()[periodCls];
		switch (periodClsEnum) {
			case YEAR: {
				// TODO
			}
			case MONTH: {
				GeneralDateTime expiredDate = startDate.addMonths(numOfPeriod);
				expiredDate = expiredDate.addSeconds(- expiredDate.seconds() - 1);
				return expiredDate;
			}
			case DAY: {
				// TODO
			}
			case HOUR: {
				// TODO
			}
			case MINUTE: {
				// TODO
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
				x.setVersion(0);
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
