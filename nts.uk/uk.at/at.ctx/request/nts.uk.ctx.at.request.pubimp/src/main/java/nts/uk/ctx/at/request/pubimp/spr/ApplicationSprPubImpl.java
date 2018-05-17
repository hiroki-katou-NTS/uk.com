package nts.uk.ctx.at.request.pubimp.spr;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.pub.spr.ApplicationSprPub;
import nts.uk.ctx.at.request.pub.spr.export.AppOverTimeSprExport;
import nts.uk.ctx.at.request.pub.spr.export.ApplicationSpr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApplicationSprPubImpl implements ApplicationSprPub {
	
	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Inject
	private ApplicationRepository_New applicationRepository_New;

	@Override
	public Optional<AppOverTimeSprExport> getAppOvertimeByDate(GeneralDate appDate, String employeeID, Integer overTimeAtr) {
		List<Application_New> listApplication = applicationRepository_New.getApplicationBySIDs(Arrays.asList(employeeID), appDate, appDate);
		List<Application_New> listPreApp = listApplication.stream()
				.filter(x -> x.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION)&&x.getPrePostAtr().equals(PrePostAtr.PREDICT))
				.collect(Collectors.toList());
		List<AppOverTimeSprExport> resultList = listPreApp.stream()
			.filter(x -> {
				Optional<AppOverTime> opAppOverTime = overtimeRepository.getAppOvertime(x.getCompanyID(), x.getAppID());
				if(!opAppOverTime.isPresent()){
					return false;
				}
				AppOverTime appOverTime = opAppOverTime.get();
				if(appOverTime.getOverTimeAtr().value==overTimeAtr){
					return true;
				}
				return false;
			}).sorted(Comparator.comparing((Application_New x) -> {return x.getInputDate();}).reversed())
			.map(x -> new AppOverTimeSprExport(
					x.getAppID(), 
					x.getReflectionInformation().getStateReflectionReal().value, 
					overTimeAtr
			)).collect(Collectors.toList());
		if(CollectionUtil.isEmpty(resultList)){
			return Optional.empty();
		}
		return Optional.of(resultList.get(0));
	}

	@Override
	public Optional<ApplicationSpr> getAppByID(String companyID, String appID) {
		return applicationRepository_New.findByID(companyID, appID)
				.map(x -> new ApplicationSpr(
						x.getAppID(), 
						x.getAppType().value));
	}

}
