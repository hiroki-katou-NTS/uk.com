package nts.uk.shr.com.communicate.batch;

import java.net.URI;
import java.util.Optional;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.val;
import nts.gul.web.communicate.DefaultNtsHttpClient;
import nts.gul.web.communicate.typedapi.RequestDefine;
import nts.gul.web.communicate.typedapi.ResponseDefine;
import nts.gul.web.communicate.typedapi.TypedCommunication;
import nts.gul.web.communicate.typedapi.TypedWebAPI;
import nts.uk.shr.com.communicate.PathToWebApi;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;
import nts.uk.shr.com.system.config.InitializeWhenDeploy;
import nts.uk.shr.com.system.config.SystemConfiguration;

@ApplicationScoped
public class DefaultBatchServer implements BatchServer, InitializeWhenDeploy {
	
	@Inject
	private LoginUserContextManager userContext;
	
	@Inject
	private SystemConfiguration system;

	private Optional<String> serverAddress;
	
	@Override
	public void initialize() {
		this.serverAddress = this.system.getBatchServerAddress();
	}
	
	@Override
	public boolean exists() {
		return this.serverAddress.isPresent();
	}
	
	@Override
	public <Q, S> TypedWebAPI<Q, S> webApi(
			PathToWebApi path,
			RequestDefine<Q> requestDefine,
			ResponseDefine<S> responseDefine) {
		
		String serverAddr = this.serverAddress
				.orElseThrow(() -> new RuntimeException("バッチサーバのアドレスが設定されていません。"));
		
		URI uriToWebApi = URI.create("http://" + serverAddr + "/" + path.createPath());
		
		return new TypedWebAPI<>(uriToWebApi, requestDefine, responseDefine); 
	}

	@Override
	public <Q, S> void request(TypedWebAPI<Q, S> api, Consumer<TypedCommunication<Q, S>> communicationBuilder) {
		
		// LoginUserContextは、呼び出し元の状態を引き継ぎたいので、RequestHeaderとして送る
		api.getRequestDefine().customHeader(
				BatchServer.CUSTOM_HEADER_USER_CONTEXT,
				this.userContext.toBase64());
		
		val client = DefaultNtsHttpClient.createDefault();
		client.request(api, communicationBuilder);
	}

}
