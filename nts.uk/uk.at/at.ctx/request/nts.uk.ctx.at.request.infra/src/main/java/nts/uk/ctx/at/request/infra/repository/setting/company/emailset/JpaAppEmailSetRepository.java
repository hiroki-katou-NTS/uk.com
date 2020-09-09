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
		String sql = "select * from KRQMT_APP_MAIL where CID = @companyID";
		Optional<AppEmailSet> appEmailSet = new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.getSingle(rec -> {
					List<EmailContent> emailContentLst = new ArrayList<>();
					Optional<EmailSubject> opEmailSubject1 = Optional.empty();
					Optional<EmailText> opEmailText1 = Optional.empty();
					String subject1 = rec.getString("SUBJECT_APPROVAL");
					if(Strings.isNotBlank(subject1)) {
						opEmailSubject1 = Optional.of(new EmailSubject(subject1));
					}
					String content1 = rec.getString("CONTENT_APPROVAL");
					if(Strings.isNotBlank(content1)) {
						opEmailText1 = Optional.of(new EmailText(content1));
					}
					emailContentLst.add(new EmailContent(Division.APPLICATION_APPROVAL, opEmailSubject1, opEmailText1));
					Optional<EmailSubject> opEmailSubject2 = Optional.empty();
					Optional<EmailText> opEmailText2 = Optional.empty();
					String subject2 = rec.getString("SUBJECT_REMAND");
					if(Strings.isNotBlank(subject2)) {
						opEmailSubject2 = Optional.of(new EmailSubject(subject2));
					}
					String content2 = rec.getString("CONTENT_REMAND");
					if(Strings.isNotBlank(content2)) {
						opEmailText2 = Optional.of(new EmailText(content2));
					}
					emailContentLst.add(new EmailContent(Division.REMAND, opEmailSubject2, opEmailText2));
					Optional<EmailSubject> opEmailSubject3 = Optional.empty();
					Optional<EmailText> opEmailText3 = Optional.empty();
					String subject3 = rec.getString("SUBJECT_OT_INSTRUCTION");
					if(Strings.isNotBlank(subject3)) {
						opEmailSubject3 = Optional.of(new EmailSubject(subject3));
					}
					String content3 = rec.getString("CONTENT_OT_INSTRUCTION");
					if(Strings.isNotBlank(content3)) {
						opEmailText3 = Optional.of(new EmailText(content3));
					}
					emailContentLst.add(new EmailContent(Division.OVERTIME_INSTRUCTION, opEmailSubject3, opEmailText3));
					Optional<EmailSubject> opEmailSubject4 = Optional.empty();
					Optional<EmailText> opEmailText4 = Optional.empty();
					String subject4 = rec.getString("SUBJECT_HD_INSTRUCTION");
					if(Strings.isNotBlank(subject4)) {
						opEmailSubject4 = Optional.of(new EmailSubject(subject4));
					}
					String content4 = rec.getString("CONTENT_HD_INSTRUCTION");
					if(Strings.isNotBlank(content4)) {
						opEmailText4 = Optional.of(new EmailText(content4));
					}
					emailContentLst.add(new EmailContent(Division.HOLIDAY_WORK_INSTRUCTION, opEmailSubject4, opEmailText4));
					return new AppEmailSet(
							companyID, 
							EnumAdaptor.valueOf(rec.getInt("URL_EMBEDDED"), NotUseAtr.class), 
							emailContentLst);
				});
		return appEmailSet.get();
	}
	
}
