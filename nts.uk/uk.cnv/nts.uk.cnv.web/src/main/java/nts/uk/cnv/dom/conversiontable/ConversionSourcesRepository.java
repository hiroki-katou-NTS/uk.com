package nts.uk.cnv.dom.conversiontable;

import java.util.List;

public interface ConversionSourcesRepository {

	ConversionSource get(String sourceId);

	List<ConversionSource> getByCategory(String category);

	String insert(ConversionSource source);

	void delete(String sourceId);

}
