package nts.uk.ctx.exio.app.input.develop.workspace;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.gul.util.value.MutableValue;
import nts.gul.web.communicate.DefaultNtsHttpClient;
import nts.gul.web.communicate.HttpMethod;
import nts.gul.web.communicate.typedapi.RequestDefine;
import nts.gul.web.communicate.typedapi.ResponseDefine;
import nts.gul.web.communicate.typedapi.TypedWebAPI;
import nts.uk.ctx.exio.app.input.develop.workspace.oruta.detail.OrutaTable;
import nts.uk.ctx.exio.app.input.develop.workspace.oruta.detail.OrutaTableColumn;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainRepository;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ImportWorkspaces {
	
	private static final URI SERVER = URI.create("http://192.168.5.210:33322/nemunoki.oruta.web/webapi/");
	
	@Inject
	private ImportingDomainRepository domainRepo;
	
	@Inject
	private ImportableItemsRepository importableItemRepo;

	public String createCsvItems(ImportingDomainId domainId) {
		
		val client = DefaultNtsHttpClient.createDefault();

		val domain = domainRepo.find(domainId);
		
		val table = getTable(client, domain.getName()).get();
		
		val items = importableItemRepo.get(domainId);
		
		List<String> lines = new ArrayList<>();
		lines.add(OrutaTableColumn.toCsvHeaderXimctWorkspaceItem());
		lines.addAll(table.toCsvXimctWorkspaceItem(domainId, items));
		
		return String.join("\r\n", lines);
	}
	
	private static Optional<OrutaTable> getTable(DefaultNtsHttpClient client, String tableName) {

		MutableValue<OrutaTable> mutable = new MutableValue<>(null);
		
		val api = new TypedWebAPI<>(
				SERVER.resolve("tables/name/" + tableName + "/not-accepted"),
				RequestDefine.noEntity(HttpMethod.GET),
				ResponseDefine.json(OrutaTable.class).expectedStatusCode("200"));

		client.request(api, c -> c
				.succeeded(r -> {
					mutable.set(r);
				}));
		
		return mutable.optional();
	}
}
