package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * tam thoi /output cua 4
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApplicationStatus {
	private int notReflectNumber;
	private int waitReflectNumber;
	private int reflectNumber;
	private int waitCancelNumber;
	private int cancelNumber;
	private int remandNumner;
	private int denialNumber;
}
