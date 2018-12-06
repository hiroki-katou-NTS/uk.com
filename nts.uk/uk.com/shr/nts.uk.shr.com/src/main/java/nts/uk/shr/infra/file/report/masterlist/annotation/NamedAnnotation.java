package nts.uk.shr.infra.file.report.masterlist.annotation;

import javax.enterprise.util.AnnotationLiteral;

public class NamedAnnotation extends AnnotationLiteral<DomainID> implements DomainID {

	private static final long serialVersionUID = 1L;
	
	private final String value;

    public NamedAnnotation(final String value) {
        this.value = value;
    }

    public String value() {
       return value;
   }
}