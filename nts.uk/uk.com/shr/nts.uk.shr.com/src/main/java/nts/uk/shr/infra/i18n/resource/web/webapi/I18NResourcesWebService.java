package nts.uk.shr.infra.i18n.resource.web.webapi;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.i18n.custom.IInternationalization;
import nts.arc.layer.app.command.JavaTypeResult;

@Path("i18n/resources")
@Produces("text/javascript")
public class I18NResourcesWebService {

	@Inject
	private IInternationalization i18n;
	
	@POST
	@Path("rawcontent/{resourceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public JavaTypeResult<String> getRawContent(@PathParam("resourceId") String resourceId){
		return new JavaTypeResult<String>(i18n.getRawMessage(resourceId).orElse(resourceId));
	}
	
//	@GET
//	@Path("screen")
//	public Response getSystemResource(@Context Request request) {
//		CacheControl cacheControl = new CacheControl();
//		cacheControl.setNoCache(true);
//		cacheControl.setMaxAge(YEAR);
//		cacheControl.setPrivate(false);
//
//		EntityTag eTag = new EntityTag(createEtag());
//		ResponseBuilder responseBuilder = request.evaluatePreconditions(eTag);
//		if (responseBuilder == null) {
//			responseBuilder = Response.ok(initialResource());
//			responseBuilder.tag(eTag);
//		}
//
//		responseBuilder.cacheControl(cacheControl);
//
//		return responseBuilder.build();
//	}
//
//	private String createEtag() {
//		// tag's format companycode_languagecode_programid_version
//		StringBuilder builder = new StringBuilder();
//		builder.append(SystemProperties.companyCode);
//		builder.append("_");
//		builder.append(currentLanguage.getSessionLocale().getLanguage());
//		builder.append("_");
//		builder.append(SystemProperties.programId);
//		builder.append("_");
//		builder.append(currentLanguage.getVersion());
//		return builder.toString();
//	}
//
//	private String initialResource() {
//		StringBuilder builder = new StringBuilder();
//		String messageObject = createJsObject(
//				i18n.getResourceForProgram(SystemProperties.programId).get(ResourceType.MESSAGE));
//		String codeNameObject = createJsObject(
//				i18n.getResourceForProgram(SystemProperties.programId).get(ResourceType.CODE_NAME));
//		builder.append("var systemLanguage='");
//		builder.append(currentLanguage.getSessionLocale().getLanguage());
//		builder.append("';");
//		builder.append("var names=");
//		builder.append(codeNameObject);
//		builder.append(";");
//		builder.append("var messages=");
//		builder.append(messageObject);
//		return builder.toString();
//	}
//
//	private String createJsObject(Map<String, String> resource) {
//		StringBuilder builder = new StringBuilder();
//		builder.append("{");
//
//		builder.append(resource.entrySet().stream().map(e -> e.getKey() + ":\"" + e.getValue() + "\"")
//				.collect(Collectors.joining(",")));
//
//		builder.append("}");
//		return builder.toString();
//	}
}
