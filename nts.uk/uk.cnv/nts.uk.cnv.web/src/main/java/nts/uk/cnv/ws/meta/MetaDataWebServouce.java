package nts.uk.cnv.ws.meta;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.dom.td.tabledefinetype.DataType;

@Path("td/metadata")
@Produces(MediaType.APPLICATION_JSON)
public class MetaDataWebServouce {

	@GET
	@Path("datatypes")
	public List<DataType> dataTypes() {
		return Arrays.asList(DataType.values());
	}
}
