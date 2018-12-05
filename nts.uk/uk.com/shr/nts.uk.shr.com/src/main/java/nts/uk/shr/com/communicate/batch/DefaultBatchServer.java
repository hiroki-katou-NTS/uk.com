package nts.uk.shr.com.communicate.batch;

import java.net.URI;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import nts.gul.web.communicate.typedapi.RequestDefine;
import nts.gul.web.communicate.typedapi.ResponseDefine;
import nts.gul.web.communicate.typedapi.TypedWebAPI;
import nts.uk.shr.com.communicate.PathToWebApi;
import nts.uk.shr.com.system.config.InitializeWhenDeploy;
import nts.uk.shr.com.system.config.SystemConfiguration;

@ApplicationScoped
public class DefaultBatchServer implements BatchServer, InitializeWhenDeploy {
	
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

}
