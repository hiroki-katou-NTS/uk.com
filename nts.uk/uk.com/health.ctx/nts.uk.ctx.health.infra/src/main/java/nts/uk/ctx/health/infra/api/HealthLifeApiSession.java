package nts.uk.ctx.health.infra.api;

import java.util.function.Function;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

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
import nts.uk.ctx.health.dom.federate.HealthLifeApiFederation;
import nts.uk.ctx.health.dom.federate.HealthLifeApiFederationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class HealthLifeApiSession {

	@Inject
	private HealthLifeApiFederationRepository federationRepo;
	
	public SessionContext begin(String companyId) {
		
		val user = AppContexts.user();
		if (!user.companyId().equals(companyId)) {
			// 現時点ではログイン外の会社の処理は未実装
			// 必要ならここでDBアクセスしてテナントコードと会社コードを取得する
			throw new RuntimeException("ログイン中の会社以外を指定できません。指定：" + companyId + ", ログイン：" + user.companyId());
		}
		
		val fed = federationRepo.find(user.contractCode()).get();
		
		val httpClient = DefaultNtsHttpClient.createDefault();
		
		val authen = httpClient.fetch(Authenticate.api(fed), Authenticate.Request.of(fed));
		val login = httpClient.fetch(Login.api(fed), Login.Request.of(authen));
		
		int healthLifeCompanyCode = Integer.parseInt(user.companyCode());
		
		return new SessionContext(fed, httpClient, login.getCsrfToken(), healthLifeCompanyCode);
	}
	
	private static class Authenticate {
		
		private static TypedWebAPI<Request, Response> api(HealthLifeApiFederation fed) {
			return new TypedWebAPI<>(
					fed.getUriOf("webapi/op/domain/gateway/externallinkage/uklinkage/authenticate"),
					RequestDefine.json(Request.class, HttpMethod.POST),
					ResponseDefine.json(Response.class));
		}
		
		@Value
		private static class Request {
			private String contractCode;
			private String linkageId;
			private String linkagePassword;
			
			public static Request of(HealthLifeApiFederation fed) {
				// 契約コードと連携IDは同一値となった
				return new Request(fed.getTargetContractCode(), fed.getTargetContractCode(), fed.getPassword());
			}
		}
		
		@Data
		private static class Response {
			private String ticket;
		}
	}
	
	private static class Login {

		private static TypedWebAPI<Request, Response> api(HealthLifeApiFederation fed) {
			return new TypedWebAPI<>(
					fed.getUriOf("webapi/op/domain/gateway/externallinkage/login"),
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
	
	@RequiredArgsConstructor
	public static class SessionContext {
		
		private final HealthLifeApiFederation fed;
		
		private final NtsHttpClient httpClient;
		
		private final String csrfToken;
		
		@Getter
		private final int healthLifeCompanyCode;
		
		public <Q, S> S post(String path, Q requestEntity, Class<S> responseClass) {
			
			@SuppressWarnings("unchecked")
			val api = new TypedWebAPI<Q, S>(
					fed.getUriOf("webapi/op/domain/gateway/externallinkage/uklinkage/authenticate"),
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
}
