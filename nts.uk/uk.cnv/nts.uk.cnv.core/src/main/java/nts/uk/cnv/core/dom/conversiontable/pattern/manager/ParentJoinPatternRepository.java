package nts.uk.cnv.core.dom.conversiontable.pattern.manager;

import java.util.List;

import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.pattern.ParentJoinPattern;


public interface ParentJoinPatternRepository {

	List<ParentJoinPattern> get(ConversionInfo info, String category, String name);

}
