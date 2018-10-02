package nts.uk.shr.infra.web.request;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.RequestInfo;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.com.context.loginuser.role.DefaultLoginUserRoles;
import nts.uk.shr.com.security.audittrail.UserInfoAdaptorForLog;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.basic.LoginInformation;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.start.StartPageLog;
import nts.uk.shr.com.security.audittrail.start.StartPageLogStorageRepository;
import nts.uk.shr.infra.application.auth.WindowsAccount;
import nts.uk.shr.infra.web.util.FilterConst;
import nts.uk.shr.infra.web.util.FilterHelper;

public class StartPageLogWriter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		writeLog(request);
		
		chain.doFilter(request, response);
	}

	private void writeLog(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		LoginUserContext context = AppContexts.user();
		RequestInfo requseted = AppContexts.requestedWebApi();
		WindowsAccount windowsAccount = AppContexts.windowsAccount();
		String requestPagePath = httpRequest.getRequestURL().toString();
		
		boolean isStartFromMenu = isStartFromMenu(httpRequest);
		
		ScreenIdentifier targetPg = ScreenIdentifier.create(requestPagePath, httpRequest.getQueryString());
		
		if(StringUtil.isNullOrEmpty(targetPg.getProgramId(), true) || FilterHelper.isLoginPage(requestPagePath)){
			return;
		}
		
		LogBasicInformation basic = new LogBasicInformation(
				IdentifierUtil.randomUniqueId(), 
				getValue(context, c -> c.companyId()),
				UserInfo.employee(
						getValue(context, c -> c.userId()), 
						getValue(context, c -> {
							if(c.employeeId() == null){
								return c.userId();
							}
							return c.employeeId();
						}),
						getValue(context, c -> {
							UserInfoAdaptorForLog userAdapter = CDI.current().select(UserInfoAdaptorForLog.class).get();
							if(context.isEmployee()){
								return userAdapter.findByEmployeeId(c.employeeId()).getUserName();
							}
							return userAdapter.findByUserId(c.userId()).getUserName();
						})), 
				new LoginInformation(
						getValue(requseted, c -> c.getRequestIpAddress()),
						getValue(requseted, c -> c.getRequestPcName()), 
						getValue(windowsAccount, c -> c.getUserName())),
				GeneralDateTime.now(), 
				getValue(context, c -> {
					return getValue(c.roles(), role -> DefaultLoginUserRoles.cloneFrom(role));
				}), targetPg, Optional.empty());
		
		saveLog(initLog(httpRequest, basic, isStartFromMenu));
	}
	
	private boolean isStartFromMenu(HttpServletRequest httpRequest){
		if(httpRequest.getCookies() == null){
			return false;
		}
		return Stream.of(httpRequest.getCookies()).filter(c -> c.getName().equals(FilterConst.JUMP_FROM_MENU)).findFirst().isPresent();
	}

	private StartPageLog initLog(HttpServletRequest httpRequest, LogBasicInformation basic, boolean requestedFromMenu) {
		
		if(requestedFromMenu){
			return StartPageLog.specialStarted(basic);
		}
		
		return StartPageLog.pageStarted(getReferered(httpRequest), basic);
	}

	@Override
	public void destroy() {
	}

	private void saveLog(StartPageLog log) {
		StartPageLogStorageRepository logStorage = CDI.current().select(StartPageLogStorageRepository.class).get();
		
		logStorage.save(log);
	}

	private ScreenIdentifier getReferered(HttpServletRequest r) {
		String refereredPath = r.getHeader(FilterConst.REFERED_REQUEST);
		
		if(StringUtil.isNullOrEmpty(refereredPath, true)){
			return null;
		}
		
		return ScreenIdentifier.create(refereredPath);
	}
	
	private <U, T> T getValue(U source, Function<U, T> getter){
		if(source != null){
			return getter.apply(source);
		}
		
		return null;
	}

}

