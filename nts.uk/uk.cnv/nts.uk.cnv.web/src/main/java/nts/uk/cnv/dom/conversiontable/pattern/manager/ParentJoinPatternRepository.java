package nts.uk.cnv.dom.conversiontable.pattern.manager;

import java.util.List;

import nts.uk.cnv.dom.conversiontable.pattern.ParentJoinPattern;
import nts.uk.cnv.dom.service.ConversionInfo;

public interface ParentJoinPatternRepository {

	List<ParentJoinPattern> get(ConversionInfo info, String category, String name);

}
