package nts.uk.ctx.at.request.dom.mail.service;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.mail.service.after.PeriodClassification;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.PrePostInitialAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hiep.ld
 *
 */
@Stateless
public class RegisterEmbededURLImpl implements RegisterEmbededURL {

	@Inject
	private ApplicationRepository_New appRepo;

	@Override
	public String obtainApplicationEmbeddedUrl(String appId, int appType, int prePostAtr, List<String> employeeId) {
		String loginId = AppContexts.user().employeeId();
		return null;
	}

	@Override
	public String registerEmbeddedForApp(String appId, int appType, int prePostAtr, String loginId, List<String> employeeId) {
		EmbeddedUrlScreenID embeddedUrlScreenID = this.getEmbeddedUrlRequestScreenID(appType, prePostAtr);
		//Map<String, value> ;
		return null;
	}

	@Override
	public String embeddedUrlInfoRegis(String programId, String screenId, int periodCls, int numOfPeriod,
			String employeeId, String loginId, Map<String, String> taskIncidental) {
		if (loginId.isEmpty() && employeeId.isEmpty()) {
			return null;
		} else {
			// Request list 313
			GeneralDate issueDate = GeneralDate.today();
			GeneralDateTime startDate = GeneralDateTime.now();
			GeneralDateTime expiredDateTime = this.getEmbeddedUrlExpriredDate(startDate, periodCls, numOfPeriod);
			// TO DO 
			// WAIT DOMAIN
			return ("Random URL");
		}
	}

	@Override
	public EmbeddedUrlScreenID getEmbeddedUrlRequestScreenID(int appType, int prePostAtr) {
		ApplicationType appTypeEnum = ApplicationType.values()[appType];
		switch (appTypeEnum) {
		case STAMP_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 002", "C");
		}
		case OVER_TIME_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 005", "B");
		}
		case ABSENCE_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 006", "B");
		}
		case WORK_CHANGE_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 007", "B");
		}
		case GO_RETURN_DIRECTLY_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 009", "B");
		}
		case EARLY_LEAVE_CANCEL_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 004", "E");
		}
		case COMPLEMENT_LEAVE_APPLICATION: {
			PrePostInitialAtr prePostAtrEnum = PrePostInitialAtr.values()[prePostAtr];
			switch (prePostAtrEnum) {
			case PRE: {
				return new EmbeddedUrlScreenID("KAF 004", "E");
			}
			}
			return new EmbeddedUrlScreenID("KAF 004", "F");
		}
		case BREAK_TIME_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 010", "B");
		}
		case ANNUAL_HOLIDAY_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 012", "B");
		}
		case LONG_BUSINESS_TRIP_APPLICATION: {
			return new EmbeddedUrlScreenID("KAF 008", "D");
		}
		case BUSINESS_TRIP_APPLICATION_OFFICE_HELPER: {
			return new EmbeddedUrlScreenID("KAF 008", "E");
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
}
