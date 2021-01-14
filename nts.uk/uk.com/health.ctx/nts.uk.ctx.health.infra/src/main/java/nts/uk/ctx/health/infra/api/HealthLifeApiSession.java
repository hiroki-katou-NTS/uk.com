package nts.uk.ctx.health.infra.api;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.gul.util.value.MutableValue;
import nts.gul.web.communicate.DefaultNtsHttpClient;
import nts.gul.web.communicate.HttpMethod;
import nts.gul.web.communicate.NtsHttpClient;
import nts.gul.web.communicate.typedapi.RequestDefine;
import nts.gul.web.communicate.typedapi.ResponseDefine;
import nts.gul.web.communicate.typedapi.TypedWebAPI;
import nts.uk.ctx.health.dom.linkage.HealthLifeApiLinkage;
import nts.uk.shr.com.context.AppContexts;

/**
 * ヘルスライフ�WebAPIを呼び出すため�セヂ�ョンを確立す�
 * HLのWebAPIは、事前に認証・ログインのAPIを呼んだ上で、セヂ�ョンCookieやCSRFト�クンを取得し�
 * それをリクエスト�都度送らなければならなぁ�ぁ�仕様�
 * こ�クラスはそれら�事前準備を隠蔽するためのも��
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class HealthLifeApiSession {

//	@Inject
//	private HealthLifeApiLinkageRepository linkageRepo;
	
	public Context begin(String companyId) {
		
		val user = AppContexts.user();
		if (!user.companyId().equals(companyId)) {
			// 現時点ではログイン外�会社の処�未実�
			// 忦�ならここでDBアクセスしてッ�ントコードと会社コードを取得す�
			throw new RuntimeException("ログイン中の会社以外を挮�できません。指定� + companyId + ", ログイン + user.companyId());
		}
		
//		val linkage = linkageRepo.find(user.contractCode()).get();
//		
//		return beginSession(linkage, user.companyCode());
		return null;
	}

	private static Context beginSession(HealthLifeApiLinkage linkage, String companyCode) {
		
		val httpClient = DefaultNtsHttpClient.createDefault();
		
		val authen = httpClient.fetch(Authenticate.api(linkage), Authenticate.Request.of(linkage));
		val login = httpClient.fetch(Login.api(linkage), Login.Request.of(authen));
		
		int healthLifeCompanyCode = Integer.parseInt(companyCode);
		
		return new Context(linkage, httpClient, login.getCsrfToken(), healthLifeCompanyCode);
	}
	
	@RequiredArgsConstructor
	public static class Context {
		
		private final HealthLifeApiLinkage linkage;
		
		private final NtsHttpClient httpClient;
		
		private final String csrfToken;
		
		/** HL側の会社コード�整数値 */
		@Getter
		private final int healthLifeCompanyCode;
		
		public <Q, S> S post(String path, Q requestEntity, Class<S> responseClass) {
			
			@SuppressWarnings("unchecked")
			val api = new TypedWebAPI<Q, S>(
					linkage.getUriOf("webapi/op/domain/gateway/externallinkage/uklinkage/authenticate"),
					(RequestDefine<Q>) RequestDefine.json(requestEntity.getClass(), HttpMethod.POST),
					ResponseDefine.json(responseClass));
			
			val response = new MutableValue<S>();
			
			httpClient.request(api, c -> c
					.entity(requestEntity)
					.header("X-CSRF-TOKEN", csrfToken)
					.succeeded(res -> {
						response.set(res);
					})
					.failed(f -> {
						f.throwException();
					}));
			
			return response.get();
		}
		
	}
	
	private static class Authenticate {
		
		private static TypedWebAPI<Request, Response> api(HealthLifeApiLinkage linkage) {
			return new TypedWebAPI<>(
					linkage.getUriOf("webapi/op/domain/gateway/externallinkage/uklinkage/authenticate"),
					RequestDefine.json(Request.class, HttpMethod.POST),
					ResponseDefine.json(Response.class));
		}
		
		@Value
		private static class Request {
			private String contractCode;
			private String linkageId;
			private String linkagePassword;
			
			public static Request of(HealthLifeApiLinkage linkage) {
				// 契紂�ードと連携IDは同一値
				return new Request(
						linkage.getTargetContractCode(),
						linkage.getTargetContractCode(),
						linkage.getPassword());
			}
		}
		
		@Data
		private static class Response {
			private String ticket;
			private String loginWebService;
		}
	}
	
	private static class Login {

		private static TypedWebAPI<Request, Response> api(HealthLifeApiLinkage linkage) {
			return new TypedWebAPI<>(
					linkage.getUriOf("webapi/op/domain/gateway/externallinkage/login"),
					RequestDefine.json(Request.class, HttpMethod.POST),
					ResponseDefine.json(Response.class));
		}
		@Value
		private static class Request {
			private String ticket;
			
			public static Request of(Authenticate.Response auth) {
				return new Request(auth.getTicket());
			}
		}
		
		@Data
		private static class Response {
			private String csrfToken;
		}
	}
}
