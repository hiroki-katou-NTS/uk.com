package nts.uk.ctx.at.request.infra.repository.setting.company.emailset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSet;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.emailset.Division;
import nts.uk.ctx.at.request.dom.setting.company.emailset.EmailContent;
import nts.uk.ctx.at.request.dom.setting.company.emailset.EmailSubject;
import nts.uk.ctx.at.request.dom.setting.company.emailset.EmailText;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.ctx.at.request.infra.entity.setting.company.emailset.KrqmtAppMail;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppEmailSetRepository extends JpaRepository implements AppEmailSetRepository {

	@Override
	public AppEmailSet findByDivision(Division division) {
		String companyID = AppContexts.user().companyId();
		String sql = "select * from KRQMT_APP_MAIL where CID = @companyID";
		Optional<AppEmailSet> appEmailSet = new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.getSingle(rec -> {
					String subject = Strings.EMPTY;
					String content = Strings.EMPTY;
					Optional<EmailSubject> opEmailSubject = Optional.empty();
					Optional<EmailText> opEmailText = Optional.empty();
					switch (division) {
					case APPLICATION_APPROVAL:
						subject = rec.getString("SUBJECT_APPROVAL");
						content = rec.getString("CONTENT_APPROVAL");
						break;
					case REMAND:
						subject = rec.getString("SUBJECT_REMAND");
						content = rec.getString("CONTENT_REMAND");
						break;
					case OVERTIME_INSTRUCTION:
						subject = rec.getString("SUBJECT_OT_INSTRUCTION");
						content = rec.getString("CONTENT_OT_INSTRUCTION");
						break;
					case HOLIDAY_WORK_INSTRUCTION:
						subject = rec.getString("SUBJECT_HD_INSTRUCTION");
						content = rec.getString("CONTENT_HD_INSTRUCTION");
						break;
					default:
						break;
					}
					if(Strings.isNotBlank(subject)) {
						opEmailSubject = Optional.of(new EmailSubject(subject));
					}
					if(Strings.isNotBlank(content)) {
						opEmailText = Optional.of(new EmailText(content));
					}
					List<EmailContent> emailContentLst = Arrays.asList(new EmailContent(division, opEmailSubject, opEmailText));
					return new AppEmailSet(
							companyID, 
							EnumAdaptor.valueOf(rec.getInt("URL_EMBEDDED"), NotUseAtr.class), 
							emailContentLst);
				});
		return appEmailSet.get();
	}

	@Override
	public AppEmailSet findByCID(String companyID) {
		return this.queryProxy().find(companyID, KrqmtAppMail.class).map(KrqmtAppMail::toDomain).orElse(null);
	}

	@Override
	public void save(AppEmailSet domain) {
		KrqmtAppMail entity = this.queryProxy().find(domain.getCompanyID(), KrqmtAppMail.class).orElse(null);
		if (entity == null) {
			this.commandProxy().insert(KrqmtAppMail.fromDomain(domain));
		} else {
			entity.update(domain);
			this.commandProxy().update(entity);
		}
	}

}
